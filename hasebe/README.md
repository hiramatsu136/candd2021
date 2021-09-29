# candd2021 長谷部


Pythonの環境作成方法

◆ANACONDA NAVIGATORをインストール
以下のリンクからAnacondaをインストールします
https://www.anaconda.com/distribution/
緑色のDownloadボタンをクリック
ダウンロードしたexeファイルをクリックしてインストール開始
方法は以下参照
https://aizine.ai/anaconda-navigator-0219/#toc4

◆Python動作環境の作成
　①ANACONDA NAVIGATORを起動し左端の Enviromentsをクリック
　②下にあるCreateをクリックし、Nameに任意の名前を入力
　③Pythonのリストボックスからバージョン選択(最新で良い)しCreateボタンをクリック

◆Anaconda Navigatorから、OpenCVを導入
　①Anaconda Navigatorを起動し、Enviormentsをクリックし、作成した環境をクリック
　②▶をクリックし、「Open Terminal」を選びターミナルを表示する

　③ターミナルに以下のコマンドを入力しEnterキーを押す
　　「conda install opencv==3.4.2」

　④「Process([y]/n)?」が表示されたら 「y」を入力しEnterキーを押す

　⑤インストールが開始される。終わり次第ターミナルとAnaconda Navigatorを終了する



◆実行方法
①feature_matching.pyと同じところに適当な画像を幾つか置いておく

②ターミナルからcdコマンドでfeature_matching.pyのあるディレクトリまで移動

③pyton feature_matching.py (画像1) (画像2)
※()内は任意で２つの画像を指定してください

④画像の特徴を検出してマッチング結果を表示します。