# unit test

## 概要
単体テストについて、テストコード作成関連のメモ、実行方法などを記載する。<br>

### 方針
Mapperの試験についてはH2を使用し、試験する。<br>
（H2はインメモリで展開できるDB。spring bootを停止するとデータは消える。）<br>
Mapperクラス以外の試験はモックを使用し、DBアクセスせず試験を行う。<br>

## 実行方法
Visual Studio Codeでテストコードを実行する手順を記載する。<br>
前提として、環境構築済みであり、ビルドできる状態であること。<br>
DBについてはH2を使用するため用意しなくても良い（はず）。<br>
1. testフォルダ内のテスト実行したいファイルを開く。
1. 左端の三角のアイコンを押下する。（ファイル内の全テスト、またはメソッド単位での実行が可能）<br>

## test tool
- MybatisTest ←マーケットプレイスに表示されないので[公式サイト](http://mybatis.org/spring-boot-starter/mybatis-spring-boot-test-autoconfigure/index.html)に従いpom.xmlに追加
- H2 Database

## H2関連メモ
test/resourcesにあるapplication.propertiesにDBの接続情報を記載する。<br>
（test実行時はこちらのファイルを参照するようになっている。target配下参照）<br>
データベースはデフォルトで作成される。<br>
### DDL
src/test/resources/schema.sql<br>
Spring Bootアプリケーションでは、schema.sqlというファイル名のスクリプトは起動時に実行される。
### DML
src/test/resources/data.sql<br>
schema.sql同様、data.sqlというファイルも起動時に実行される。<br>
テストに利用するデータを起動時に挿入しておく。<br>
ダブルクォーテーションだと実行時エラーになる。シングルクォーテーションを使用する。


## DB周りの試験について（今回調べたらでてきたのでメモ）
### TestContainers
JUnit のテスト中だけDockerコンテナ上でDBを起動し試験する方法もある。<br>
Dockerコンテナ化とツールの準備が必要。<br>
https://www.b1a9idps.com/posts/test-containers<br>
https://qiita.com/os1ma/items/f8d3df2c61e16a7c8b20

---
### 参考
[アプリケーションプロパティ設定一覧](https://spring.pleiades.io/spring-boot/docs/current/reference/html/application-properties.html)<br>

mapper H2まわり
https://qiita.com/nyandora/items/5b4a4bf79324a37f69ba<br>
https://nainaistar.hatenablog.com/entry/2020/12/09/083000<br>
https://it-jog.com/java/springdatajpa/starth2<br>
https://tech.excite.co.jp/entry/2021/05/01/120000<br>
https://qiita.com/YutaKase6/items/4241e0573fc4e104ea1c<br>

mapper mock<br>
https://www.webdevqa.jp.net/ja/junit/%E3%83%87%E3%83%BC%E3%82%BF%E3%83%99%E3%83%BC%E3%82%B9%E6%8E%A5%E7%B6%9A%E3%82%92%E3%83%86%E3%82%B9%E3%83%88%E3%81%99%E3%82%8B%E3%81%9F%E3%82%81%E3%81%ABmockito%E3%82%92%E4%BD%BF%E7%94%A8%E3%81%99%E3%82%8B%E6%96%B9%E6%B3%95/1050198524/<br>
https://qiita.com/YutaKase6/items/1f3ca15900b5146351de<br>

mockkitoまわり<br>
https://qiita.com/en-ken/items/9a7d3dcb937ea912c7e9<br>
https://qiita.com/kyabetsuda/items/16c565460580a8354f6a<br>
https://blueskyarea.hatenablog.com/entry/2018/06/08/230605<br>
https://qiita.com/segur/items/9cdf4ff04fb89bc463f6<br>

https://absj31.hatenadiary.com/entry/20120811/1344759958<br>
https://qiita.com/a-pompom/items/3f834119c756e5286730<br>
https://qiita.com/suke_masa/items/36d5f70577692575c929<br>
https://kohei.life/spring-test/<br>
https://techblog.gmo-ap.jp/2017/03/13/mock%E3%81%A7%E3%83%A6%E3%83%8B%E3%83%83%E3%83%88%E3%83%86%E3%82%B9%E3%83%88%E3%82%92%E7%B0%A1%E5%8D%98%E3%81%AB%E3%81%97%E3%82%88%E3%81%86%EF%BC%81/<br>
https://qiita.com/rcftdbeu/items/ffeb6160dd215565b39f<br>
https://macchinetta.github.io/server-guideline/current/ja/UnitTest/ImplementsOfUnitTest/ImplementsOfTestByLayer.html<br>
