package main

import (
	"fmt"
	"time"

	"timecard/process"
)

func main() {
	// 名前設定用変数
	var name string
	// ファイル名設定用変数
	var fileName string
	// 新規ファイル作成フラグ(true:新規ファイル作成 false:既存ファイル更新)
	var newFileFlag bool
	// 出退勤確認用フラグ(true:出勤打刻 false:退勤打刻)
	var punchFlag bool
	// 最終確認用フラグ(false時、処理を終了する)
	var finalCheckFlag bool
	// 打刻結果保存用スライス
	var records [][]string
	// エラー処理用変数
	var err error

	// 現在時刻の取得
	var nowDate time.Time = time.Now()

	// 名前入力処理
	name = process.NameInput()

	fmt.Println("---")

	// 出退勤確認処理
	punchFlag = process.CheckPunchInOrPunchOut()

	fmt.Println("---")

	// ファイル名確認処理
	fileName, newFileFlag = process.SetFileName(name, nowDate)

	// records作成処理
	records, err = process.MakeRecords(fileName, newFileFlag, nowDate, punchFlag)
	if err != nil {
		fmt.Println("処理を中断します。")
		return
	}

	// 打刻するデータの表示
	if punchFlag {
		fmt.Println("打刻する時刻は、出勤時刻 ", nowDate.Format("2006/01/02 15:04"), "となります。")
	} else {
		fmt.Println("打刻する時刻は、退勤時刻 ", nowDate.Format("2006/01/02 15:04"), "となります。")
	}

	// 打刻最終確認処理
	finalCheckFlag = process.FinalCheck()
	if !finalCheckFlag {
		fmt.Println("打刻をキャンセルしました。")
		return
	}

	fmt.Println("---")

	// ファイル生成処理
	err = process.FileCreateProcess(records, fileName, newFileFlag)
	if err != nil {
		fmt.Println("処理を中断します。")
		return
	}

	fmt.Println("打刻が完了しました。処理を終了します。")
}
