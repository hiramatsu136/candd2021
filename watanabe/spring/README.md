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
↓　mybatis<br>
https://medium-company.com/spring-boot-thymeleaf%E3%81%A7%E4%B8%80%E8%A6%A7%E7%94%BB%E9%9D%A2%E3%82%92%E4%BD%9C%E6%88%90%E3%81%99%E3%82%8B/#UserControllerjava<br>
https://www.bedroomcomputing.com/2020/03/2020-0327-mybatis/<br>

## バージョン情報
- java：jdk-⁠11.0.11.9
- Spring Boot：2.4.6　←選択肢に表示されないため2.4.12で作成
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
    1. 「環境変数」をクリックし、設定画面を開く。「新規」ボタンをクリックし、下記を追加する。<br>
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
- Lombok Annotations Support for VS Code（Lombokを使用する場合追加する）


## Visual Studio Codeの設定
1. 「Ctrl + , 」でVisual Studio Codeの設定を開く。
1. 「java.home」と検索し、「settings.json で編集」をクリックする。
1. settings.jsonに以下を追加する。
    ```
    "java.home": Javaフォルダの中にあるjdkから始まるフォルダ（例："C:\\Program Files\\Java\\jdk-11.0.11"）
    ```
1. プロキシを使用する場合、settings.jsonに以下を設定する。
    ```
    "http.proxy": "http://username:password@your-proxy-ip:port",
    "http.proxyAuthorization": null,
    "http.proxyStrictSSL": false,
    "java.configuration.maven.globalSettings": mavenの設定ファイルのパス（例："C:\\Users\\watanabe210\\.m2\\settings.xml"）

    ※mavenのプロキシ設定はファイルに記載し、settings.jsonでパスの指定を行う。
    ```

## プロジェクトの作成
1. コマンドパレットでSpringを検索、「Spring Initializr: Generate a Maven Project」を選択する。<br>
※コマンドパレット表示方法<br>
Windows：Ctrl + Shift + P<br>
Mac：Command + Shift + P<br>
または上部メニュー「表示」＞「コマンドパレット」<br>
※ビルドツールについて<br>
現場で何を使用しているか不明だが、ひとまずMavenを使用する。<br>
Gradleの場合は「Spring Initializr: Generate a Maven Project」を選択する。
1. Spring Bootのバージョンを選択する。
1. project languageを聞かれたら「Java」を選択する。
1. パッケージ名を入力し、Enterを押す。
1. プロジェクト名を入力し、Enterを押す。
1. パッケージングタイプを選択する。（今回はJARを選択）
1. Javaのバージョンを選択する。（今回はJDKのバージョンが11のため11を選択）
1. 依存関係を追加する。（一旦以下を追加。必要に応じて変更する。）
    - Spring Web
    - Spring Boot DevTools
    - Thymeleaf
    - Lombok　←getter,setterを自動生成するプラグイン
    - MyBatis Framework
    - Spring Data JPA
1. ディレクトリを選択し、「Generate into this folder」を押す。

## 実行方法
1. Visual Studio Code の左側、SPRING BOOT DASHBOARD　に表示されているプロジェクトのStartボタンを押下し、com.sample.sample.SampleApplicationを選択する。<br>
または、上部メニューの実行＞デバッグの開始を押下する。
2. 下記へアクセスする。
    ```
    Hello World
    http://localhost:8080/hello
    
    DBアクセスサンプル
    http://localhost:8080/member/list
    ```

## MySQL
無償版「MySQL Community Edition」を使用する。以下インストールなどの手順を記載する。<br>
### インストール
1. [公式サイト](https://www.mysql.com/jp/)からインストーラーを入手する。<br>
サイト上部のダウンロードから「MySQL Community Downloads」へとび、
MySQL Community Serverをダウンロードする。
1. インストーラーを実行し、Developer Defaultを選択する。<br>
1. 「Check Requirements」画面でExecuteボタンを押下し、必要なものをインストールしてからNextボタンを押下する。<br>
特に記載のないものは画面に従ってインストールを進める。基本はデフォルト値で良い。
1. 「Type and Networking」画面で初期設定を行う。<br>
「Development Computer」を選択する。ポートは重複していなければそのまま。
1. そのまま進めていき、rootユーザーのパスワード設定とユーザー追加を行う。<br>
今回は以下で作成
    ```
    rootユーザーパスワード：mysql_root
    ユーザー名：mysql_admin
    パスワード：mysql_admin
    Role：DB Admin
    ```
1. 「Apply Configuration」画面でExecuteボタンを押下し、設定を反映する。
1. Finishボタン押下後、続けて「MySQL Router」の設定画面となる。そのままNext、Finishボタンを押下する。
1. 「Samples and Examples」の設定ではrootユーザーのパスワードを入力し、Checkボタンを押下する。<br>
接続に成功したらNextボタンを押下し、次の画面でExecute、Finishボタンを押下する。
後は画面に従い、Next、Finishを押下していく。<br>

参考<br>
https://agency-star.co.jp/column/mysql<br>
https://qiita.com/ponsuke0531/items/176a158ea4db3714adda<br>

### mySQLドライバー追加
プロジェクトにドライバーを追加する。
1. Visual Studio Codeのエクスプローラーにてpom.xml上で右クリックし、「Add Starters...」を選択する。
1. MySQL Driverを選択し、Enterを押す。pom.xmlに設定が追加されればOK。
1. application.propertiesにDBの接続設定を追加する。（データベースが作成してあること）

### データベース作成
当プロジェクトで使用するデータベースなどの作成方法を記載する。
1. MySQL Workbenchを起動し、「sample」スキーマを作成する。
1. テーブルを作成する。
    ```
    CREATE TABLE IF NOT EXISTS
    sample.member                                              -- テーブル名
    (
        MAIL_ADDRESS varchar(255) NOT NULL PRIMARY KEY  -- メールアドレス：PK
        ,NAME varchar(255) NOT NULL                     -- 名前
        ,CREATED_DATE datetime                          -- 登録日時
        ,CREATED_USER varchar(255)                      -- 登録者
        ,UPDATED_DATE datetime                          -- 更新日時
        ,UPDATED_USER varchar(255)                      -- 更新者
    );
    ```
1. 任意のデータを登録する。
