package process

import (
	"fmt"
	"log"
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

// 打刻結果保存用スライス作成処理
func MakeRecords(fileName string, newFileFlag bool, nowDate time.Time, punchFlag bool) ([][]string, error) {
	var records [][]string
	var err error
	if newFileFlag {
		// 新規ファイル作成時
		records = makeNewFileRecords(nowDate, punchFlag)
	} else {
		// 既存ファイル更新時
		records, err = makeUpdateFileRecords(nowDate, punchFlag, fileName)
		if err != nil {
			fmt.Println("ファイル処理中に問題が発生しました。")
			log.Print(err)
			return nil, err
		}
	}

	return records, err
}

// 新規ファイル作成時のレコード作成関数
func makeNewFileRecords(nowDate time.Time, punchFlag bool) [][]string {
	// ヘッダの設定
	var newFileRecords [][]string = [][]string{{ymdName, punchInName, punchOutName}}

	// 年月日レコード投入処理
	for t := time.Date(nowDate.Year(), nowDate.Month(), 1, 0, 0, 0, 0, time.Local); t.Month() == nowDate.Month(); t = t.AddDate(0, 0, 1) {
		newFileRecords = append(newFileRecords, []string{t.Format(dateFormat), "", ""})
	}

	// 打刻データ投入処理
	returnRecords := makePunchRecords(newFileRecords, nowDate, punchFlag)

	return returnRecords
}

// 既存ファイル更新時のレコード作成関数
func makeUpdateFileRecords(nowDate time.Time, punchFlag bool, fileName string) ([][]string, error) {
	// csvファイル読み込み処理
	updateFileRecords, err := readCsvFile(fileName)
	if err != nil {
		return nil, err
	}

	// 既に打刻済みかどうかを確認する
	for i, record := range updateFileRecords {
		if record[dateColumn] == nowDate.Format(dateFormat) {
			doneCheck(updateFileRecords[i], punchFlag)
		}
	}

	// records作成処理
	returnRecords := makePunchRecords(updateFileRecords, nowDate, punchFlag)

	return returnRecords, nil
}

// 打刻済み確認用関数
func doneCheck(records []string, punchFlag bool) {
	if punchFlag {
		// 出勤の打刻予定の際に、本日の出勤時刻が既に打刻されている場合
		if records[punchInColumn] != "" {
			fmt.Println("---<注意>---")
			fmt.Println("<注意>本日の出勤時刻が既に打刻済みです！(出勤時刻:", records[punchInColumn], ")")
		}
	} else {
		// 退勤の打刻予定の際に、本日の退勤時刻が既に打刻されている場合
		if records[punchOutColumn] != "" {
			fmt.Println("---<注意>---")
			fmt.Println("<注意>本日の退勤時刻が既に打刻済みです！(退勤時刻:", records[punchOutColumn], ")")
		}

	}
}

// 打刻データ投入処理
func makePunchRecords(records [][]string, nowDate time.Time, punchFlag bool) [][]string {
	for i, record := range records {
		// レコード内の年月日の一致する行に打刻する
		if record[dateColumn] == nowDate.Format(dateFormat) {
			updateRecord := make([]string, columnInt)
			if punchFlag {
				// 出勤の打刻
				updateRecord[dateColumn] = nowDate.Format(dateFormat)
				updateRecord[punchInColumn] = nowDate.Format(timeFormat)
				updateRecord[punchOutColumn] = records[i][punchOutColumn]
			} else {
				// 退勤の打刻
				updateRecord[dateColumn] = nowDate.Format(dateFormat)
				updateRecord[punchInColumn] = records[i][punchInColumn]
				updateRecord[punchOutColumn] = nowDate.Format(timeFormat)
			}
			records[i] = updateRecord
			break
		}
	}
	return records
}
