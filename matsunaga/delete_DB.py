import sqlite3
import sys


##DB終了処理
def delete_main_DB(db_title):
    conn = sqlite3.connect(db_title,isolation_level =None)
    cur = conn.cursor()
    sql =  """DROP TABLE IF EXISTS touch_list"""
    cur.execute(sql)
    conn.close()
    print("DBを終了しました。")

