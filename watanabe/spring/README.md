# Spring Boot 勉強用プロジェクト

## 概要
### Visual Studio Codeを使ってSpring Bootのプロジェクトを作成する。
※Visual Studio Codeはインストール済みであることを前提とする。
1. JDKインストール
1. Visual Studio Code拡張機能のインストール
1. Spring Bootのプロジェクト作成
1. 「Hello World」を表示させる
1. DBをインストール、MyBatisでDBアクセスを行う

参考<br>
https://tech-lab.sios.jp/archives/19941<br>
https://zenn.dev/s_t_pool/articles/486dfaa7c2e5fb7a6a03<br>
https://qiita.com/tsunemiso/items/32d67fc633cf5448d89a#mybatis-generator<br>

## バージョン情報
- java：jdk-⁠11.0.11.9
- Spring Boot：2.4.6
- MyBatis：2.1.4

## JDKインストール
JDKがインストールされていない場合、インストールと環境変数の設定を行う。
1. Oracleのサイトからインストーラーを入手する。
[ダウンロードページ](https://www.oracle.com/jp/java/technologies/javase/jdk11-archive-downloads.html)<br>
（以下Windows10の場合の手順を記載する）
1. インストーラーを右クリックし、「管理者として実行」を選択する。
1. ウィザードが開いたら「次へ」をクリックし、インストールする。
1. 環境変数を設定する。<br>
    1. Cortana（PC画面左下）で「環境変数」と検索し、「システム環境変数の編集」を開く。<br>
    1. 「環境変数」をクリックし、設定画面を開く。ユーザー環境変数の「新規」ボタンをクリックし、下記を追加する。<br>
    変数名：JAVA_HOME<br>
    変数値：Javaフォルダの中にあるjdkから始まるフォルダ（例：C:\Program Files\Java\jdk-11.0.11）
    1. 上記に続いて、Pathを設定する。Pathを選択した状態で「編集」ボタンをクリックする。Pathの編集画面で「新規」ボタンをクリックし、以下を設定する。<br>
    %JAVA_HOME%¥bin
1. PCを再起動し、環境変数の設定を反映させる。
1. コマンドプロンプトを起動し、「java -version」コマンドでバージョンが表示されれば完了。

参考<br>
https://rightcode.co.jp/blog/become-engineer/java-install

## Visual Studio Code拡張機能のインストール
Visual Studio Codeに下記拡張機能をインストールする。
- Java Extension Pack
- Spring Boot Extension Pack

## プロジェクトの作成
1. コマンドパレットでSpringを検索、「Spring Initializr: Generate a Maven Project」を選択する。<br>
※コマンドパレット表示方法<br>
Windows：Ctrl + Shift + P<br>
Mac：Command + Shift + P<br>
または上部メニュー「表示」＞「コマンドパレット」<br>
※ビルドツールについて<br>
現場で何を使用しているか不明だが、ひとまずMavenを使用する。<br>
Gradleの場合は「Spring Initializr: Generate a Maven Project」を選択する。
1. project languageを聞かれたら「Java」を選択する。
1. パッケージ名を入力し、「Enter」を押す。
1. プロジェクト名を入力し、「Enter」を押す。
1. Spring Bootのバージョンを選択する。
