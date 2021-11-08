package process

import (
	"encoding/csv"
	"fmt"
	"log"
	"os"
	"time"
)

// ファイル名確認処理
func SetFileName(name string, nowDate time.Time) (string, bool) {
	// ファイル名設定
	var fileName string = string(name + "_" + nowDate.Format("200601") + ".csv")
	// 新規ファイル作成フラグ
	var newFileFlag bool
	// ファイルの存在確認
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
	return fileName, newFileFlag
}

// ファイル生成処理
func FileCreateProcess(records [][]string, fileName string, newFileFlag bool) error {
	var err error
	// 元ファイルのファイル名変更（バックアップ）
	if !newFileFlag {
		err = os.Rename("./"+fileName, "./old_"+fileName)
		if err != nil {
			fmt.Println("ファイル処理中に問題が発生しました。")
			log.Print(err)
			return err
		}
	}

	// ファイル生成
	createFile, err := os.Create("./" + fileName)
	if err != nil {
		fmt.Println("ファイル生成中に問題が発生しました。")
		log.Print(err)
		return err
	}
	defer createFile.Close()

	// データ書き込み
	writeCSV := csv.NewWriter(createFile)

	for _, record := range records {
		err = writeCSV.Write(record)
		if err != nil {
			fmt.Println("データ書き込み中に問題が発生しました。")
			log.Print(err)
			return err
		}
	}
	writeCSV.Flush()

	err = writeCSV.Error()
	if err != nil {
		fmt.Println("データ書き込み中に問題が発生しました。")
		log.Print(err)
		return err
	}

	// バックアップした元データの削除
	if !newFileFlag {
		err = os.Remove("./old_" + fileName)
		if err != nil {
			fmt.Println("バックアップデータ削除中に問題が発生しました。")
			log.Print(err)
			return err
		}
	}
	return nil
}

// csvファイル読み込み処理
func readCsvFile(fileName string) ([][]string, error) {
	file, err := os.Open(fileName)
	if err != nil {
		return nil, err
	}
	defer file.Close()

	readData := csv.NewReader(file)
	readFileRecords, err := readData.ReadAll()
	if err != nil {
		return nil, err
	}

	return readFileRecords, nil
}
