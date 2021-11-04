package main

import (
	"encoding/csv"
	"errors"
	"fmt"
	"log"
	"os"
	"regexp"
	"time"
)

// ヘッダの列を修正する場合、合わせてcolumn定数の数値も変更してください。
const (
	headerColumn0  string = "年月日"
	headerColumn1  string = "出勤時刻"
	headerColumn2  string = "退勤時刻"
	dateColumn     int    = 0 // "年月日"の列に合わせる
	punchInColumn  int    = 1 // "出勤時刻"の列に合わせる
	punchOutColumn int    = 2 // "退勤時刻"の列に合わせる
)

func main() {
	// 標準入力受け取り用変数
	var input string

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

	// 名前入力処理
	for {
		fmt.Print("名前を入力してください：")
		fmt.Scan(&input)

		// 入力文字確認
		m, _ := regexp.MatchString("^[a-zA-Z0-9]{1,15}$", input)
		if m {
			break
		}
		fmt.Println("名前は、15字以内の半角英数字で入力してください。")
	}

	fmt.Println("---")

	// ファイルの存在確認
	fileName := string(input + "_" + nowDate.Format("200601") + ".csv")

	_, err = os.Stat(fileName)
	if err == nil {
		fmt.Println("打刻ファイル[", fileName, "]を確認しました。")
		fmt.Println("[", fileName, "]を開いていないことを確認してください。")
		newFileFlag = false
	} else {
		fmt.Println("今月の打刻ファイル[", fileName, "]が存在しないため、新規ファイルを作成します。")
		newFileFlag = true
	}

	fmt.Println("---")

	// 出退勤確認処理
	fmt.Println("打刻する時刻は", nowDate.Format("2006/01/02 15:04"), "です")
	for {
		fmt.Print("出勤時刻と退勤時刻のどちらに打刻しますか？(1:出勤, 2:退勤, 0:終了)：")
		fmt.Scan(&input)

		if input == "1" {
			punchFlag = true
			break
		} else if input == "2" {
			punchFlag = false
			break
		} else if input == "0" {
			fmt.Println("処理を終了しました。")
			return
		}
		fmt.Println("[1]または[2]または[0]を入力してください。")
	}

	// records作成処理
	if newFileFlag {
		records = newFileRecords(nowDate, punchFlag)
	} else {
		records = [][]string{{"b"}}
		// 既存ファイル内に打刻日のものが既に打刻されていた場合、打刻するかどうかを尋ねる予定
	}

	// 元ファイルをバックアップディレクトリに移動
	if !newFileFlag {
		err := os.Rename("./"+fileName, "./backup/"+fileName)
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
		err := os.Remove("./backup/" + fileName)
		if err != nil {
			fmt.Println("バックアップデータ削除中に問題が発生しました。処理を中断します。")
			log.Print(err)
			return
		}
	}

	fmt.Println("打刻が完了しました。処理を終了します。")
}

// 新規ファイル作成時のレコード作成関数
func newFileRecords(nowDate time.Time, punchFlag bool) [][]string {
	// ヘッダの設定
	var newFileRecords [][]string = [][]string{{headerColumn0, headerColumn1, headerColumn2}}

	// 打刻処理
	if punchFlag {
		newFileRecords = append(newFileRecords, []string{nowDate.Format("2006/01/02"), nowDate.Format("15:04"), ""})
	} else {
		newFileRecords = append(newFileRecords, []string{nowDate.Format("2006/01/02"), "", nowDate.Format("15:04")})
	}

	return newFileRecords
}

func updateFileRecords(nowDate time.Time, punchFlag bool, fileName string) ([][]string, error) {
	// csvファイル読み込み処理
	file, err := os.Open(fileName)
	if err != nil {
		fmt.Println("ファイル処理中に問題が発生しました。処理を中断します。")
		log.Print(err)
		return nil, err
	}
	defer file.Close()

	readData := csv.NewReader(file)
	updateFileRecords, err := readData.ReadAll()
	if err != nil {
		fmt.Println("ファイル処理中に問題が発生しました。処理を中断します。")
		log.Print(err)
		return nil, err
	}

	// 既に打刻済みかどうかを確認する
	doneFlag, doneQuestion := doneCheck(nowDate, updateFileRecords, punchFlag)

	if doneFlag {
		// 標準入力受け取り用変数
		var input string
		for {
			fmt.Print(doneQuestion, "打刻してもよろしいですか？(1:はい, 2:いいえ)：")
			fmt.Scan(&input)

			if input == "1" {
				break
			} else if input == "2" {
				fmt.Println("処理を終了しました。")
				return nil, errors.New("Process cancel") // errを一律で読み取るのであれば、この処理ではダメ。エラーではない中断なので、エラーとするべきではないはず。
			}
			fmt.Println("[1]または[2]を入力してください。")
		}
	}

	// records作成処理を記載予定
	return [][]string{{"途中"}}, nil
}

// 打刻済み確認用関数
func doneCheck(nowDate time.Time, records [][]string, punchFlag bool) (bool, string) {
	var doneFlag bool = false
	var doneQuestion string
	// 最後列の読み取り
	lastLine := len(records) - 1

	if punchFlag {
		// 出勤の打刻予定の際に、本日の出勤時刻が既に打刻されている場合
		if records[lastLine][dateColumn] == nowDate.Format("2006/01/02") {
			if records[lastLine][punchInColumn] != "" {
				doneFlag = true
				doneQuestion = "出勤時刻は既に打刻されています。"
			}
		}
	} else {
		// 出勤の打刻予定の際に、本日の出勤時刻が既に打刻されている場合
		if records[lastLine][dateColumn] == nowDate.Format("2006/01/02") {
			if records[lastLine][punchOutColumn] != "" {
				doneFlag = true
				doneQuestion = "退勤時刻は既に打刻されています。"
			}
		}
	}

	return doneFlag, doneQuestion
}
