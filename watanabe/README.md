# candd2021　渡邉　ライフゲーム

## ライフゲームとは
セル・オートマトンの例として知られている。<br>
生命の誕生、淘汰などのプロセスを簡易的なモデルで再現したシミュレーションゲーム。<br>
参照　[ライフゲーム - Wikipedia](https://ja.wikipedia.org/wiki/%E3%83%A9%E3%82%A4%E3%83%95%E3%82%B2%E3%83%BC%E3%83%A0)


## 当ライフゲームのルール
1. 各セルを1つの生命として扱う。
1. 各セルは黒＝生、白＝死とする。
1. ゲーム開始後、盤面は一定の間隔で世代交代していく。
1. 次世代のセルは隣接するセルの生死によって決まる。
- 誕生<br>
    死んでいるセルに隣接する生きたセルがちょうど3つの場合、誕生する。
- 生存<br>
    生きているセルに隣接する生きたセルが2つか3つの場合、生存する。
- 過疎<br>
    生きているセルに隣接する生きたセルが1つ以下の場合、過疎で死滅する。
- 過密<br>
    生きているセルに隣接する生きたセルが4つ以上の場合、過密で死滅する。   

## 前提条件
ブラウザはGoogle Chromeを使用しているため、その他ブラウザについては動作保証しない。<br>
実行前に[開発環境インストール手順](#開発環境インストール手順)のNode.js(npm)のインストールを実施しておくこと。

## 実行方法・遊び方
1. コマンドプロンプトを起動する。
1. チェックアウトしたソースのwatanabe/lifegame配下に移動する。

    `cd (チェックアウトしたソースの場所)/watanabe/lifegame`

1. 以下コマンドを実行し、プロジェクトのセットアップを行う。

    `npm install`

1. 以下コマンドを実行し、コンパイル、ローカル起動する。

    `npm run serve`

1. ブラウザを起動し、下記にアクセスする。<br>
　http://localhost:8080
1. 初期配置を設定する。<br>
　デフォルト値を使用する場合はとばして次の手順へ。
1. スタートボタンを押すとゲームスタート、ストップボタンを押すと停止する。
1. クリアボタンを押すと盤面がクリアされ、0世代目にリセットされる。

## 開発環境
- OS：Windows10
- 言語：javascript
- フレームワーク：Vue.js
    - コマンドラインインタフェース：vue-cli 4.3.1
    - vue-cli プラグイン（UIライブラリー）：Vuetify
- ツール：Visual Studio Code
    - Visual Studio Code プラグイン：Vetur, ESLint, prettier

## 開発環境インストール手順
1. Node.js(npm)のインストール（vue-cliを使用するために必要）
    1. [公式サイト](https://nodejs.org/ja/)からインストーラーをダウンロードする。<br>
    (v12.14.1で開発。過去のバージョンのインストーラーは公式サイト内で入手可能)
    1. インストーラーを起動し、インストールする。（「Automatically install the necessary tools.・・・」のチェックは外す）
    1. コマンドプロンプトを起動し、バージョンを確認する。
        ```
        Node.jsのバージョン確認コマンド
        node --version
    
        npmのバージョン確認コマンド
        npm --version
        ```
1. vue-cliのインストール　※以降は初めてVue.jsをインストールしてプロジェクト作成する場合の手順。参考用に記載。
    1. コマンドプロンプトで下記コマンドを実行し、インストールする。
        ```
        npm install -g @vue/cli

        デフォルトでは最新版がインストールされる。
        バージョン指定してインストールすることも可能（参考まで）。
        npm install -g @vue/cli4.3.1
        ```
    1. バージョンを確認する。
        ```
        vue --version
        ```
1. プロジェクト作成
    ```
    vue create プロジェクト名

    roster：babel、router、eslintをインストールする
    default：babel、eslintをインストールする
    Manually select features：導入するライブラリを選定してインストールする
    ```
    下記参考<br>
    https://qiita.com/YMori0811/items/2b48d05a08b743bd70c9

1. Vuetifyのインストール
    ```
    プロジェクトのフォルダに移動し、以下コマンド実行。

    vue add vuetify
    ```
    下記参考<br>
    https://vuetifyjs.com/ja/getting-started/quick-start/

## unit test
1. テストツール
    - Vue Test Utils
    - Jest

1. テストの実行方法

    watanabe/lifegameに移動し、以下コマンドを実行する。

    `npm test`

1. テストツールのインストール（参考）
    ```
    npm install --save-dev vue-jest
    npm install --save-dev babel-jest
    npm install --save-dev babel-core@bridge

    インストール後、package.jsonにscripts.testとjestの設定を追加する。
    また、babel.config.jsにenv.testの設定を追加する。
    ```

## ソース構成

    lifegame                                        
    ├─ __tests__                                    # テストコード 
    │   
    ├─ public                                       # 静的ファイル(favicon.icoなど)
    │   
    ├─ src                                          # ソース
    │   ├─ assets                                   # 静的ファイル(ロゴなど)
    │   ├─ components                               # コンポーネント
    │   ├─ const                                    # 定数
    │   ├─ plugins                                  # vueのプラグイン
    │   ├─ router                                   # vue-routerのpath設定はここで行う
    │   ├─ views                                    # viewファイル（router-viewとなるもの）
    │   ├─ App.js                                   # 
    │   └─ main.js                                  # 
    │   
    ├─ .gitignore                                   # 
    ├─ babel.config.js                              # babelの設定
    ├─ jest.setup.js                                # jestがテスト実行時に読み込むファイル
    ├─ README.md                                    # 
    ├─ package-lock.json                            # 
    ├─ package.json                                 # パッケージ／モジュールの管理ファイル
    └─ vue.config.js                                # vueの設定
