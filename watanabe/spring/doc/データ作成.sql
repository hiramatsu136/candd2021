-- sampleプロジェクト実行に必要なテーブルなどのSQLを記載する。

-- テーブル作成用SQL
-- memberテーブル
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

-- データ作成用SQLサンプル（データ内容は任意に変更）
insert into sample.member values("xxxx@aaa.co.jp","xxxx", now(), "ins_user", now(), "upd_user");
