package com.example.demo.repository;

import java.util.List;
import java.io.InputStream;

import com.example.demo.entity.PrefSymbol;

import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * PrefSymbolRepository
 * 
 * SQL実行インタフェース
 */
@Repository
public class PrefSymbolRepository {

    /**
     * configXml
     * 
     * DBとの接続設定及び各SQL文とのマッピングが定義されているファイルのパス
     */
    private final String configXml = "/mybatis-config.xml";

    /**
     * 全件取得処理
     * 
     * @return prefcode_tableのデータを結合したpref_symbol_tableの全データリスト
     */
    public List<PrefSymbol> selectAllSymbol() throws Exception {
        List<PrefSymbol> prefSymbolList;
        // ルートとなる設定ファイルを読み込む
        try (InputStream in = BookRepository.class.getResourceAsStream(configXml)) {
            // 設定ファイルを元に、 SqlSessionFactory を作成する
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

            // SqlSessionFactory から SqlSession を生成する
            try (SqlSession session = factory.openSession()) {
                // SqlSession を使って select(全件) を実行する
                prefSymbolList = session.selectList("mybatis.prefSymbolsql.selectAllSymbol");
                session.close();
            }
        }
        return prefSymbolList;
    }
}
