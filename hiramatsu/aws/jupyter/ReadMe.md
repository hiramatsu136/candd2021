# jupyter on Fargate

## 構成

```
  +--------------------------+
  | VPC                      |
  |  +---------------------+ |
  |  | Subnet              | |
  |  |  +---------------+  | |
  |  |  | AWS Fargate   |  | |
  |  |  |               |  | |
  |  |  | +----------+  |  | |
  |  |  | | ECS Task |  |  | |
  |  |  | | (jupyter)|  |  | |
  |  |  | +----------+  |  | |
  |  |  | +----------+  |  | |
  |  |  | | .note    |  |  | |
  |  |  | +----------+  |  | |
  |  |  +-----^---------+  | |
  |  |        |            | |
  |  |        | volume mount |
  |  |  +---------------+  | |
  |  |  | AWS EFS       |  | |
  |  |  | +----------+  |  | |
  |  |  | | .note    |  |  | |
  |  |  | +----------+  |  | |
  |  |  +---------------+  | |
  |  +---------------------+ |
  +--------------------------+
```
このテンプレートでできる事
- 指定したVPC内にサブネットを作成し、その中にFargateでjupyterコンテナを立ち上げます
- workディレクトリにEFSをアタッチし、テンプレート停止後もデータを残します(EFSは事前に作成)
- 起動に必要なセキュリティグループとIAMロールはテンプレートで作成します


## Prepare
AWSアカウントを持っている事
- CFn用にS3バケットを作成する
  - some-cfn-package
- VPCを作成する
- EFSを作成する
  - パーミッション777のアクセスポイントを作成する

## Usage
AWSコンソールにjupyterディレクトリ一式をアップロードし、ディレクトリ内で以下コマンドを実行

```bash
# S3にパッケージ情報を作成
aws cloudformation package --template-file jupyter_root.yml --s3-bucket some-cfn-package --s3-prefix jupyter --output-template-file package.yml

# デプロイ
aws cloudformation deploy --template-file package.yml --stack-name jupytersample --no-fail-on-empty-changeset --capabilities CAPABILITY_NAMED_IAM

# 削除
aws cloudformation delete-stack --stack-name jupytersample
```

ECSのタスク定義詳細からIPv4アドレス、ログからURLが取得できるので、  
URLのドメイン部分をIPv4アドレスに書き換えてブラウザからアクセスする。
