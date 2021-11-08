# タイムカードシステムの作成

## 概要
csvファイルを用いた簡単なタイムカードのシステムを作成しました。<br>
システムを起動した時刻を出勤時刻または退勤時刻として記録します。<br>
csvファイルは名前ごとに作成されます。また、年月ごとに新しく作成されます。

## 使用上の注意
- 本プログラムの実行中は、該当のcsvファイルは閉じてください。
- 本プログラムで使用可能な文字種は、半角英数字のみです。

## 使い方
- 起動方法
    1. ターミナルを起動します。
    1. チェックアウトしたソースのitano/timecard配下に移動します。<br>
        `cd (チェックアウトしたソースの場所)/itano/timecard`
    1. 以下のコマンドでプログラムを実行します。<br>
     ```
     macで起動する場合
     ./timecardForMac

     windowsで起動する場合
     ./timecardForWindows.exe
     ```
- 操作方法
     1. 名前を入力します。（半角英数字15字以内）
     1. 出勤か退勤かを選択します。<br>
     出勤の場合は"1"を、退勤の場合は"2"を入力してください。
     1. 打刻する時刻が表示されます。<br>
     打刻する場合には"1"を、打刻をしない場合には"2"を入力してください。
     1. 処理が終了次第、チェックアウトしたソースのitano/timecard配下に存在するcsvファイルを確認してください。

## 開発環境
- OS : Mac(darwin/amd64)
- 言語 : Go
- ツール : Visual Studio Code

## 開発環境構築手順

- Goのインストール
    1. [公式サイト](https://golang.org/)より、インストーラーをダウンロードする。
    1. インストールを実行し、以下のコマンドでバージョンを確認する。<br>
        `go version`

- プロジェクト作成
    1. プロジェクトを作成するディレクトリへ移動<br>
        `cd (任意のディレクトリ)`
    1. プロジェクトを初期化する<br>
        `go mod init (任意のモジュール名)`<br>
        ※下記参考
        - [Go Modules - Qiita](https://qiita.com/propella/items/e49bccc88f3cc2407745)
        - [Naming a module](https://golang.org/doc/modules/managing-dependencies#naming_module)

- ビルド
    1. 作成したプロジェクトの配置ディレクトリへ移動<br>
        `cd (任意のディレクトリ)`
    1. 実行する想定の環境に合わせて、ビルドコマンドを実行する
        ```
        mac(darwin/amd64)用のバイナリファイル作成
        GOOS=darwin GOARCH=amd64 go build -o (生成ファイル名)

       windows(64bit)用のバイナリファイル作成
       GOOS=windows GOARCH=amd64 go build -o (生成ファイル名).exe

       windows(32bit)用のバイナリファイル作成
        GOOS=windows GOARCH=386 go build -o (生成ファイル名).exe

        ```
        ※下記参考
        - [Goでクロスコンパイル - Qiita](https://qiita.com/httpd443/items/0c055ca3eb4b26cac4ff)


## ソース構成

    timecard
    ├─ main.go                          # メイン処理
    │ 
    ├─ go.mod                           # パッケージ/モジュールの管理ファイル
    │ 
    └─ process
        └─ file.go                      # ファイル操作・csvファイル作成
        └─ input.go                     # 標準入力関連
        └─ record.go                    # 打刻データ作成

