package main

import (
	"encoding/csv"
	"fmt"
	"log"
	"os"
	"regexp"
	"time"
)

// ヘッダの列を修正する場合、合わせてcolumn定数の数値も変更してください。
const (
	// ヘッダの名称設定
	ymdName      string = "年月日"
	punchInName  string = "出勤時刻"
	punchOutName string = "退勤時刻"

	// 配列のインデックス用数値設定
	dateColumn     int = 0 // "年月日"の列に合わせる
	punchInColumn  int = 1 // "出勤時刻"の列に合わせる
	punchOutColumn int = 2 // "退勤時刻"の列に合わせる
	columnInt      int = 3 // 列の総数に合わせる

	// 年月日フォーマット設定
	dateFormat string = "2006/01/02"
	// 時刻フォーマット設定
	timeFormat string = "15:04"
)

func main() {
	// 標準入力受け取り用変数
	var input string

	// 名前設定用変数
	var name string

	// 新規ファイル作成フラグ(true:新規ファイル作成 false:既存ファイル更新)
	var newFileFlag bool

	// 出退勤確認用フラグ(true:出勤打刻 false:退勤打刻)
	var punchFlag bool

	// 打刻結果保存用スライス
	var records [][]string

	// エラー処理用変数
	var err error

	// 現在時刻の取得
	var nowDate time.Time = time.Now()

	////////////////////////input
	// 名前入力処理
	for {
		fmt.Print("名前を入力してください：")
		fmt.Scan(&input)
		// 入力文字確認
		if nameCheck(input) {
			break
		}

		fmt.Println("名前は、15字以内の半角英数字で入力してください。")
	}
	// 名前設定
	name = input
	////////////////////////

	fmt.Println("---")

	////////////////////////input
	// 出退勤確認処理
	for {
		fmt.Print("出勤時刻と退勤時刻のどちらに打刻しますか？(1:出勤, 2:退勤)：")
		fmt.Scan(&input)
		// 入力数値確認
		if intCheck(input) {
			break
		}

		fmt.Println("[1]または[2]を入力してください。")
	}
	// 出退勤確認用フラグ操作
	if input == "1" {
		punchFlag = true
	} else if input == "2" {
		punchFlag = false
	}
	////////////////////////

	fmt.Println("---")

	////////////////////////file
	// ファイルの存在確認
	fileName := string(name + "_" + nowDate.Format("200601") + ".csv")
	_, check := os.Stat(fileName)
	if check == nil {
		// ファイルが存在する場合
		fmt.Println("打刻ファイル[", fileName, "]を確認しました。")
		fmt.Println("[", fileName, "]を開いていないことを確認してください。")
		newFileFlag = false
	} else {
		// ファイルが存在しない場合
		fmt.Println("今月の打刻ファイル[", fileName, "]が存在しないため、新規ファイルを作成します。")
		newFileFlag = true
	}
	////////////////////////

	////////////////////////record
	// records作成処理
	if newFileFlag {
		// 新規ファイル作成時
		records = makeNewFileRecords(nowDate, punchFlag)
	} else {
		// 既存ファイル更新時
		records, err = makeUpdateFileRecords(nowDate, punchFlag, fileName)
	}
	if err != nil {
		fmt.Println("ファイル処理中に問題が発生しました。処理を中断します。")
		log.Print(err)
		return
	}
	////////////////////////

	// 打刻最終確認
	if punchFlag {
		fmt.Println("打刻する時刻は、出勤時刻 ", nowDate.Format(dateFormat), nowDate.Format(timeFormat), "となります。")
	} else {
		fmt.Println("打刻する時刻は、退勤時刻 ", nowDate.Format(dateFormat), nowDate.Format(timeFormat), "となります。")
	}

	////////////////////////input
	for {
		fmt.Print("打刻しますか？(1:はい, 2:いいえ)：")
		fmt.Scan(&input)
		// 入力数値確認
		if intCheck(input) {
			break
		}

		fmt.Println("[1]または[2]を入力してください。")
	}
	if input == "2" {
		fmt.Println("打刻をキャンセルしました。")
		return
	}
	////////////////////////

	fmt.Println("---")

	//////////////////////file
	// 元ファイルのファイル名変更（バックアップ）
	if !newFileFlag {
		err := os.Rename("./"+fileName, "./old_"+fileName)
		if err != nil {
			fmt.Println("ファイル処理中に問題が発生しました。処理を中断します。")
			log.Print(err)
			return
		}
	}

	// ファイル生成
	createFile, err := os.Create("./" + fileName)
	if err != nil {
		fmt.Println("ファイル生成中に問題が発生しました。処理を中断します。")
		log.Print(err)
		return
	}
	defer createFile.Close()

	// データ書き込み
	writeCSV := csv.NewWriter(createFile)

	for _, record := range records {
		err := writeCSV.Write(record)
		if err != nil {
			fmt.Println("データ書き込み中に問題が発生しました。処理を中断します。")
			log.Print(err)
			return
		}
	}
	writeCSV.Flush()

	err = writeCSV.Error()
	if err != nil {
		fmt.Println("データ書き込み中に問題が発生しました。処理を中断します。")
		log.Print(err)
	}

	// バックアップした元データの削除
	if !newFileFlag {
		err := os.Remove("./old_" + fileName)
		if err != nil {
			fmt.Println("バックアップデータ削除中に問題が発生しました。処理を中断します。")
			log.Print(err)
			return
		}
	}
	////////////////////////

	fmt.Println("打刻が完了しました。処理を終了します。")
}

//////////////////////input
// 名前入力確認用関数
func nameCheck(input string) bool {
	nameCheckFlag, _ := regexp.MatchString("^[a-zA-Z0-9]{1,15}$", input)
	return nameCheckFlag
}

// 数値入力確認用関数
func intCheck(input string) bool {
	if input == "1" || input == "2" {
		return true
	}
	return false
}

//////////////////////

//////////////////////record
// 新規ファイル作成時のレコード作成関数
func makeNewFileRecords(nowDate time.Time, punchFlag bool) [][]string {
	// ヘッダの設定
	var newFileRecords [][]string = [][]string{{ymdName, punchInName, punchOutName}}

	// レコード作成処理
	if punchFlag {
		newFileRecords = append(newFileRecords, []string{nowDate.Format(dateFormat), nowDate.Format(timeFormat), ""})
	} else {
		newFileRecords = append(newFileRecords, []string{nowDate.Format(dateFormat), "", nowDate.Format(timeFormat)})
	}

	return newFileRecords
}

// 既存ファイル更新時のレコード作成関数
func makeUpdateFileRecords(nowDate time.Time, punchFlag bool, fileName string) ([][]string, error) {
	// csvファイル読み込み処理
	file, err := os.Open(fileName)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	readData := csv.NewReader(file)
	updateFileRecords, err := readData.ReadAll()
	if err != nil {
		return nil, err
	}

	// 最後列の読み取り
	lastLine := len(updateFileRecords) - 1

	// 既に打刻済みかどうかを確認する
	doneCheck(nowDate, updateFileRecords[lastLine], punchFlag)

	// records作成処理
	returnRecords := makeRecordsForUpdateFileRecords(updateFileRecords, nowDate, punchFlag, lastLine)

	return returnRecords, nil
}

// 打刻済み確認用関数
func doneCheck(nowDate time.Time, records []string, punchFlag bool) {
	if punchFlag {
		// 出勤の打刻予定の際に、本日の出勤時刻が既に打刻されている場合
		if records[dateColumn] == nowDate.Format(dateFormat) {
			if records[punchInColumn] != "" {
				fmt.Println("---<注意>---")
				fmt.Println("本日の出勤時刻が既に打刻済みです！(出勤時刻:", records[punchInColumn], ")")
			}
		}
	} else {
		// 退勤の打刻予定の際に、本日の退勤時刻が既に打刻されている場合
		if records[dateColumn] == nowDate.Format(dateFormat) {
			if records[punchOutColumn] != "" {
				fmt.Println("---<注意>---")
				fmt.Println("<注意>本日の退勤時刻が既に打刻済みです！(退勤時刻：", records[punchOutColumn], ")")
			}
		}
	}

	return
}

// レコード作成用関数
func makeRecordsForUpdateFileRecords(updateFileRecords [][]string, nowDate time.Time, punchFlag bool, lastLine int) [][]string {
	appendString := make([]string, columnInt)

	if updateFileRecords[lastLine][dateColumn] == nowDate.Format(dateFormat) {
		// 最終行が本日の日付の場合、その行を更新する
		if punchFlag {
			// 出勤の打刻
			appendString[dateColumn] = nowDate.Format(dateFormat)
			appendString[punchInColumn] = nowDate.Format(timeFormat)
			appendString[punchOutColumn] = updateFileRecords[lastLine][punchOutColumn]
		} else {
			// 退勤の打刻
			appendString[dateColumn] = nowDate.Format(dateFormat)
			appendString[punchInColumn] = updateFileRecords[lastLine][punchInColumn]
			appendString[punchOutColumn] = nowDate.Format(timeFormat)
		}
		updateFileRecords[lastLine] = appendString
	} else {
		// 最終行が本日の日付でない場合、本日の日付の行を追加する
		if punchFlag {
			// 出勤の打刻
			appendString[dateColumn] = nowDate.Format(dateFormat)
			appendString[punchInColumn] = nowDate.Format(timeFormat)
			appendString[punchOutColumn] = ""
		} else {
			// 退勤の打刻
			appendString[dateColumn] = nowDate.Format(dateFormat)
			appendString[punchInColumn] = ""
			appendString[punchOutColumn] = nowDate.Format(timeFormat)
		}
		updateFileRecords = append(updateFileRecords, appendString)
	}
	return updateFileRecords
}

//////////////////////record
