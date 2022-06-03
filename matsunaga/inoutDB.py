import sqlite3
import sys
import delete_DB

def print_record(cursor):
    ##テーブル名取得
    sql = """SELECT name FROM sqlite_master WHERE TYPE='table'"""

    for t in cursor.execute(sql):
        #for文で作成した全テーブルを確認
        print(t)

    ##レコード表示処理
    select_sql = """SELECT * FROM touch_list"""
    cursor.execute(select_sql)

    while True:
        #データを1行抽出
        result=cursor.fetchone()
        if result is None :
            #データを抽出しきって空になったら抜ける
            break 

        print(result)

def add_record(conn, cursor):
    import datetime

     #日時の取得
    dt_now = datetime.datetime.now()
    dt_str = dt_now.strftime('%Y/%m/%d %H:%M:%S')
    num_count = 0

    #?は後で値を受け取る意味。不正ＳＱＬ対策
    sql = """INSERT INTO touch_list VALUES(?,?,?)"""
    
    #id,name,timeの順に格納する。
    
    data =((num_count,'name',dt_str))
    
    cursor.execute(sql,data)
    conn.commit()
    num_count += 1
    print("レコードを追加しました。")

def create_main_DB():
    
    tof = 1

    #データベースの作成
    dbname = ('tag.db')
    conn = sqlite3.connect(dbname,isolation_level =None)

    #テーブルの作成
    cursor = conn.cursor()
    sql = """CREATE TABLE IF NOT EXISTS touch_list(id,name,date)"""

    #SQL文の実行
    cursor.execute(sql)
    #データベースに上書き保存
    conn.commit()

    print("DB起動しました。")
    
    #ループ
    while tof == 1:
        print("操作を選んでください。\n")
        print("1:テーブルの追加、3:テーブルの表示、0:DB終了")
        try:
            cmd_num =input()
            cmd_num = int(cmd_num)
        except ValueError:
                print ("エラー:0から10の数字で入力してください。")
                sys.exit()

        if cmd_num == 0:
            break
        elif cmd_num == 1:
            add_record(conn,cursor)    
        elif cmd_num ==3:
            print_record(cursor)
        
        if cmd_num == 0 :
            t0f = cmd_num
            break



    ##終了処理
    delete_DB.delete_main_DB(dbname)



    
    return 0




##mainは一番最後に呼ぶ。宣言はないため。importで代わりができる。
if __name__ == "__main__":
    create_main_DB()