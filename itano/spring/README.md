# VSCodeを用いた、Java, Spring Boot, Oracle, MyBatisを使用したプログラムの実行手順

## 概要
本READMEでは、Java, Spring Boot, Oracle, MyBatisを使用したデモプログラムの実行環境構築～プログラムの実行までの手順を記載する。

## 前提条件
使用するOSはWindows10(64bit)。<br>
VSCodeはインストール済みの想定。<br>
デモプログラムでは、以下を使用している。
- Java: jdk-⁠11.0.11
- Spring Boot: 2.4.6
- MyBatis: 2.1.4
- Oracle: Oracle Database 21c Express Edition

## 環境構築
### Java
1. [公式サイト](https://www.oracle.com/java/technologies/javase/jdk11-archive-downloads.html)より、インストーラーをダウンロードする。
1. インストーラを起動し、表示に従ってjdkのインストールを行う。<br>
1. 環境変数Pathを編集し、以下のパスを追加する。
    ```
    (jdkのインストール先のフォルダのパス)/jdk-11.x.xx/bin

    <例>C:\Program Files\Java\jdk-11.0.11\bin
    ```
1. 環境変数JAVA_HOMEを新規作成する。
    ```
    変数名：JAVA_HOME
    変数値：(jdkのインストール先のフォルダのパス)/jdk-11.x.xx
    ```
1. 以下のコマンドで動作を確認する。<br>
    `javac -version`

### VSCodeでJava, Spring Bootを使用可能にする
1. VSCodeを起動し、以下の拡張機能をインストールする。
    - Extension Pack for Java
    - Spring Boot Extension Pack

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
    `alter session set container = XEPDB1;`

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
1. sysユーザからログアウトし、作成したtestuserでPDBにログインする。
    ```
    ログアウト：
    exit

    testuserでログイン：
    sqlplus testuser/(任意のパスワード)@//localhost:1521/XEPDB1
    ```
1. テーブルを作成し、データの追加を行う。
    「データ追加用SQL.txt」を参照し、記載されているSQLをそれぞれ実行する。

## デモプログラム実行方法
デモプログラムは、作成したDBデータの参照及び追加を行うWebアプリケーションとなっている。
1. itano/spring/demo/src/main/resources配下のapplication.propertiesを、以下のように修正する。
    ```
    spring.datasource.url=jdbc:oracle:thin:@localhost:1521/XEPDB1
    spring.datasource.username=testuser
    spring.datasource.password=xxxx
    
    xxxx:DBデータ作成のユーザ作成時に設定した任意のパスワード
    ```
1. itano/spring/demo/src/main/resources配下のmybatis-config.xmlを、以下のように修正する。
    ```
    ...
    <dataSource type="POOLED">
        <property name="driver" value="oracle.jdbc.driver.OracleDriver"/>
        <property name="url" value="jdbc:oracle:thin:@localhost:1521/XEPDB1"/>
        <property name="username" value="testuser"/>
        <property name="password" value="xxxx"/>
    </dataSource>
    ...

    xxxx:DBデータ作成のユーザ作成時に設定した任意のパスワード
    ```
1. itano\spring\demo\src\main\java\com\example\demo配下のDemoApplication.javaを開き、F5(デバッグの開始)を押下する。
1. ブラウザにて、[http://localhost:8080/](http://localhost:8080/)にアクセスする。

