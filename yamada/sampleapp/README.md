# Reactのサンプルアプリケーション
## Reactとは
### 概要
React は、Facebookとコミュニティにより開発されているJavaScriptライブラリ。シングルページアプリケーション(SPA)の開発に用いることができる。Netflix、Salesforce、Dropboxなど様々なサービスで利用されている。ライブラリのため、複雑なアプリケーションを作成する場合、状態管理(Reduxなど)、ルーティング(React Routerなど)に別途ライブラリを導入する必要がある。

 - [React – ユーザインターフェース構築のための JavaScriptライブラリ](https://ja.reactjs.org/ "React")
 - [53サービス・アプリのクラウドやフレームワーク・言語など聞いてみた！ アーキテクチャ大調査2020](https://eh-career.com/engineerhub/entry/2020/01/28/103000 "53サービス・アプリのクラウドやフレームワーク・言語など聞いてみた！ アーキテクチャ大調査2020")
 - [The Best Websites Examples Built With React JS - ProCoders](https://procoders.tech/blog/popular-react-js-websites-examples/ "ProCoders")

### 特徴
1. コンポーネント志向  
  複雑な画面を「コンポーネント」と呼ばれる小さい部品単位で作成可能。
2. 宣言的  
  処理の条件や結果が明確に記載されたような記述となる  
  ReactではJSX形式で記述する。
  以下の例では、変数にHTML要素を代入している。(参考：[JSX の導入](https://ja.reactjs.org/docs/introducing-jsx.html))
  
   ```JavaScript
    const element = <h1>Hello, world!</h1>;
   ```  
3. 一度の学習  
  得た知識は、モバイルアプリ(React Native)やVRコンテンツ(React 360)の開発にも利用可能。
    
## サンプルアプリケーションについて
### 概要
本サンプルアプリケーションは、以下手順で作成しました。
1. コマンドによりベースプロジェクトを作成 (参考：[新しい React アプリを作る](https://ja.reactjs.org/docs/create-a-new-react-app.html#create-react-app))
   ```Bash
    create-react-app <プロジェクト名>
   ``` 
2. Material-UIのテンプレートを導入 (参考：[React Templates](https://mui.com/getting-started/templates/))

3. コンポーネントの分割(Dashboard.js→Header.js)

4. テストコードの追加

### 使用方法
#### 事前準備
- Node.jsインストール  
  [公式サイト](https://nodejs.org/ja/)にアクセスし、インストーラーをダウンロード  
  インストールを実行し、以下コマンドでバージョンを確認。
  ```
  node -v
  ```

#### ローカル起動
- VSCodeでsampleappフォルダを開く
- ローカルにライブラリをインストールする
    ```bash
    npm install
    ```
- アプリケーションを起動する
    ```bash
    npm start
    ```
- ブラウザで以下にアクセスする  
   http://localhost:3000/

#### ビルド
- VSCodeでsampleappフォルダを開く
- ビルドを実行する
    ```bash
    npm run build
    ```
- セキュリティオプションを無効化したGoogle Chromeを起動する  
   [クロスドメイン制約を回避するChromeショートカットを作る](https://qiita.com/shika-e/items/808ccdd047133315b95c)
    ```bash
    "C:\Program Files (x86)\Google\Chrome\Application\chrome.exe" --disable-web-security --user-data-dir="C:\Users\ユーザ名\Local\Google\Chrome\User Data"
    ```

- 起動したChromeでbuildフォルダ配下のindex.htmlを開く

### ソース構成

    sampleapp                                        
    ├─ build                                        # ビルド後のファイル 
    │   
    ├─ public                                       # 静的ファイル(buildにコピーされる)
    │   
    ├─ src                                          # ソース
    │   ├─ components                               # コンポーネント
    │   │   
    │   ├─ pages                                    # ページ
    │   │   ├─ Dashboard.js                         # ダッシュボード
    │   │   └─ SignIn.js                            # サインイン
    │   │   
    │   ├─ tests                                    # テストコード
    │   │   
    │   ├─ App.js                                   # 
    │   ├─ index.css                                # 
    │   ├─ index.js                                 # メインファイル(JavaScriptの開始点)
    │   ├─ logo.svg                                 # 
    │   ├─ reportWebVitals.js                       # パフォーマンス計測ツール
    │   └─ setupTests.js                            # テストのセットアップ
    │   
    ├─ .gitignore                                   # 
    ├─ README.md                                    # 
    ├─ package-lock.json                            # 
    └─ package.json                                 # パッケージ／モジュールの管理ファイル

### テストコード
JavaScriptテスティングフレームワークJestを使用してテストを実行する。  
Facebook製のため、Reactとの親和性がとても良い。  
参考：[JEST](https://facebook.github.io/jest/)

#### テスト実行
- 以下コマンドを実行する
  ```bash
    npm test
  ```
- コンソールのテスト結果を確認する

#### カバレッジ計測
- 以下コマンドを実行する
  ```bash
    npm test -- --coverage
  ```
- coverage/lcov-reportフォルダのindex.htmlを開く

### パフォーマンス計測
パフォーマンス計測ツールreportWebVitalsを使用して計測する。  
Reactアプリの作成コマンド(reate-react-app)でアプリを作成すると、デフォルトで組み込まれる。  
参考：[GoogleChrome/web-vitals](https://github.com/GoogleChrome/web-vitals/)  

src/index.js
```JavaScript
import React from "react";
import ReactDOM from "react-dom";
import "./index.css";
import reportWebVitals from "./reportWebVitals";
import App from "./App";

ReactDOM.render(
  <React.StrictMode>
    <App />
  </React.StrictMode>,
  document.getElementById("root")
);

// If you want to start measuring performance in your app, pass a function
// to log results (for example: reportWebVitals(console.log))
// or send to an analytics endpoint. Learn more: https://bit.ly/CRA-vitals
reportWebVitals(console.log);
```
#### 測定方法
- アプリケーションをローカル起動する
- ブラウザで以下にアクセスする  
   http://localhost:3000/
- F12を押下し、開発者ツールを開く
- Consoleに以下が出力される  
  TTFB (Time to First Byte)：ユーザーのブラウザがページコンテンツの最初のバイトを受信するのにかかる時間  
  FCP (First Contentful Paint)：ぺージの読込からコンテンツのいずれかの部品がレンダリングされるまでの時間  
  CLS (Cumulative Layout Shift)：累積レイアウトシフト数。ユーザーが予期しないレイアウトシフトに遭遇する頻度の数値。  
  LCP (Largest Contentful Paint)：最大視覚コンテンツの表示時間。知覚される読み込み速度を測定するための指標。

### ほかなにか
