# プロジェクト作成に関するtips

## プロジェクトの作成手順
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

## maven実行時にプロキシ設定が必要な場合
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

## Spring Bootのバージョンダウン方法
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

## VSCodeのターミナルの文字化け修正方法
1. VSCodeの設定を開き、settings.jsonを検索する。
1. 「settings.jsonで編集」を押下し、settings.jsonに以下を追加する。
    ```
    "terminal.integrated.defaultProfile.windows": "Command Prompt",
    "terminal.integrated.profiles.windows": {
        "PowerShell": {
            "source": "PowerShell",
            "icon": "terminal-powershell"
        },
        "Command Prompt": {
            "path": [
                "${env:windir}\\Sysnative\\cmd.exe",
                "${env:windir}\\System32\\cmd.exe"
            ],
            "args": [
                "/k",
                "chcp",
                "932"
            ],
            "icon": "terminal-cmd"
        }
    }
    ```

    args内の"/k", "chcp", "932"によって、ターミナルの文字コードをs-jisに変更している。
    