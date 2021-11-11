# VSCodeによる、Java, Spring Boot, Oracle, MyBatisを使用したプロジェクト作成

## 概要
Java, Spring Boot, Oracle, MyBatisの環境構築～プロジェクト作成までの手順を記載する。

## 前提条件
使用するOSはWindows10(64bit)。<br>
VSCodeはインストール済みの想定。<br>
使用する各種ツールについては以下を参照のこと。
- Java: jdk-⁠11.0.11
- Spring Boot: 2.4.6
- MyBatis: 2.1.4
- Oracle: Oracle Database 21c Express Edition

## 環境構築
### Java
1. [公式サイト](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)より、インストーラーをダウンロードする。
1. インストーラを起動し、表示に従ってjdkのインストールを行う。<br>
また、**jdkのインストール先のフォルダのパス**をメモしておく。
1. 環境変数Pathを編集し、以下のパスを追加する。
    ```
    (jdkのインストール先のフォルダのパス)/jdk-11.x.xx/bin

    <例>C:\Program Files\Java\jdk-11.0.11\bin
    ```
1. 以下のコマンドで動作を確認する。<br>
    `javac -version`

### VSCodeでJava, Spring Bootを使用可能にする
1. VSCodeを起動し、以下の拡張機能をインストールする。
    - Extension Pack for Java
    - Spring Boot Extension Pack

1. VSCodeの設定を開いて「java home」と検索し、以下の設定があることを確認する。
    ```
    Java: Home
    Specifies the folder path to the JDK (11 or more recent) used to launch the Java Language Server.
    On Windows, backslashes must be escaped, i.e.
    "java.home":"C:\\Program Files\\Java\\jdk11.0_8"
    ```
1. 「setting.jsonで編集」を押下し、以下の記載を追加する。
    ```
    "java.home": "(jdkのインストール先のフォルダのパス)\\jdk-1.11.x.xx",

    <例>"java.home": "C:\\Program Files\\Java\\jdk-11.0.11",
    ```

### Oracle
1. [公式サイト](https://www.oracle.com/jp/database/technologies/xe-downloads.html)より、windows用のものをダウンロードする。
1. ダウンロードしたファイルを展開し、その中のsetup.exeを実行する。
1. しばらく待つと、InstallShieldウィザードが表示されるため、表示に従ってインストールを実行する。<br>
（☆インストールの際、「データベース・パスワードの入力」にて入力したパスワードをメモしておくこと）
1. インストール完了後、以下のコマンドで接続を確認する。<br>
    `sqlplus sys/(データベース・パスワード) as sysdba`

### DBデータ作成
1. 以下のコマンドで、Oracleにログインする。<br>
    `sqlplus sys/(データベース・パスワード) as sysdba`
1. 接続先をPDBに変更する。<br>
    `alter session set container = XEPDC1;`

    (Oracleはマルチテナント・アーキテクチャを採用しており、CDBと呼ばれる基盤のDBと、PDBと呼ばれるCDBの中に作成されるDBがある。<br>
    Oracleのインストール時にデフォルトで「XEPDB1」というPDBが作成されているため、こちらに接続を切り替えている。)
1. ユーザ(testuser)を作成し、権限を付与する。
    ```
    ユーザ作成：
    CREATE USER testuser identified by (任意のパスワード);

    権限付与：
    grant connect to testuser;
    grant resource to testuser;
    grant dba to testuser;
    ```
1. sysユーザからログアウトし、作成したユーザでPDBにログインする。
    ```
    ログアウト：
    exit;

    testuserでログイン：
    sqlplus testuser/(任意のパスワード)@//localhost:1521/XEPDB1
    ```
1. テーブルを作成する。
    ```
    CREATE TABLE test_table (
    id int not null,
    val varchar(100) not null,
    );
    ```
1. データのINSERTとSELECTの動作を確認する。
    ```
    INSERT INTO test_table (id, val) VALUES (1, 'テスト');

    SELECT * FROM test_table;
    ```

## プロジェクト作成
1. VSCodeにてコマンドパレットを表示し、以下のコマンドを選択する。<br>
    `Spring Initializr: Create a Maven Project`
1. Specify Spring Boot version.にて任意のバージョンを選択する。
1. Specify project language.にてJavaを選択する。
1. Input Group Id for your project.にて任意のパッケージ名を入力する。
1. Input Artifact Id for your project.にて任意のプロジェクト名を入力する。
1. Specify packaging type.にてJarを選択する。
1. Specify Java version.にて11を選択する。
1. Search for dependencies.にて以下を選択する。
    - Spring Web
    - Thymeleaf
    - MyBatis Framework
    - Oracle Driver
1. プロジェクトの保存先を決定し、Generate into this folder.を押下する。
1. 動作確認として、src/main/java/(パッケージ名)/(プロジェクト名)内のjavaファイルを開き、F5を押下する。<br>
VSCodeのターミナルが開き、springのロゴが表示されればOK。<br>
※DBアクセス設定をしていない場合、spring起動後にfailが発生する。

### DBアクセス設定
1. 作成したプロジェクトのsrc/main/resources内のapplication.propertiesに、以下を追加する。
    ```
    spring.datasource.url=jdbc:oracle:thin:@localhost:1521/XEPDB1
    spring.datasource.username=testuser
    spring.datasource.password=xxxx
    
    xxxx:DBデータ作成のユーザ作成時に設定した任意のパスワード
    ```
1. 動作確認として、src/main/java/(パッケージ名)/(プロジェクト名)内のjavaファイルを開き、F5を押下する。<br>
VSCodeのターミナルが開き、springのロゴが表示され、failが発生しなければOK。

### ※maven実行時にプロキシ設定が必要な場合
1. 以下のデータを入力したsettings.xmlを作成する。
    ```
    <settings>
        <proxies>
            <proxy>
                <id>(任意)</id>
                <active>true</active>
                <protocol>http</protocol>
                <host>(プロキシサーバのアドレス)</host>
                <port>(ポート番号)</port>
                <username>(認証ユーザ)</username>
                <password>(認証パスワード)</password>
            </proxy>
        </proxies>
    </settings>
    
    （<username>と<password>は、必要なければ省略可）
    ```
1. VSCodeの設定を開いて「maven」と検索し、以下の設定の入力欄にsettings.xmlのパスを入力する。
    ```
    Java › Configuration › Maven: Global Settings
    Path to Maven's global settings.xml

    <例>C:\Users\itano240\maven\apache-maven-3.8.3\conf\settings.xml
    ```

### ※Spring Bootのバージョンダウン方法
1. 任意のSpring Bootのバージョンでプロジェクト作成後、生成されたフォルダ内のpom.xmlを開く。
1. parentタグ内のversionタグの値を変更する。
    ```
    ・Spring Boot versionを2.4.12から2.4.6に変更する場合
    変更前：
    <parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.4.12</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>

    ↓

    変更後：
    <parent>
		<groupId>org.springframework.boot</groupId>
		<artifactId>spring-boot-starter-parent</artifactId>
		<version>2.4.6</version>
		<relativePath/> <!-- lookup parent from repository -->
	</parent>
    ```
