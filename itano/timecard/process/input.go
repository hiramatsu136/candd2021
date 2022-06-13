package process

import (
	"bufio"
	"fmt"
	"os"
	"regexp"
)

// 名前入力処理
func NameInput() string {
	var inputData string
	for {
		fmt.Print("名前を入力してください：")
		// 入力データ取得
		inputData = stdin()
		// 名前入力確認
		if nameCheck(inputData) {
			break
		}

		fmt.Println("名前は、15字までの半角英数字で入力してください。")
	}
	return inputData
}

// 出退勤確認処理
func CheckPunchInOrPunchOut() bool {
	// 出退勤確認用フラグ
	var punchFlag bool
	var inputData string
	for {
		fmt.Print("出勤時刻と退勤時刻のどちらに打刻しますか？(1:出勤, 2:退勤)：")
		// 入力データ取得
		inputData = stdin()
		// 数値入力確認
		if intCheck(inputData) {
			break
		}

		fmt.Println("[1]または[2]を入力してください。")
	}

	// 出退勤確認用フラグ操作
	if inputData == "1" {
		punchFlag = true
	} else if inputData == "2" {
		punchFlag = false
	}

	return punchFlag
}

// 打刻最終確認処理
func FinalCheck() bool {
	// 最終確認用フラグ
	var finalCheckFlag bool
	var inputData string
	for {
		fmt.Print("打刻しますか？(1:はい, 2:いいえ)：")
		inputData = stdin()
		// 入力数値確認
		if intCheck(inputData) {
			break
		}

		fmt.Println("[1]または[2]を入力してください。")
	}

	// 最終確認用フラグ操作
	if inputData == "1" {
		finalCheckFlag = true
	} else if inputData == "2" {
		finalCheckFlag = false
	}
	return finalCheckFlag
}

// 名前入力確認用関数
func nameCheck(inputData string) bool {
	nameCheckFlag, _ := regexp.MatchString("^[a-zA-Z0-9]{1,15}$", inputData)
	return nameCheckFlag
}

// 数値入力確認用関数
func intCheck(inputData string) bool {
	if inputData == "1" || inputData == "2" {
		return true
	}
	return false
}

// 標準入力
func stdin() string {
	scanner := bufio.NewScanner(os.Stdin)
	scanner.Scan()
	return scanner.Text()
}
