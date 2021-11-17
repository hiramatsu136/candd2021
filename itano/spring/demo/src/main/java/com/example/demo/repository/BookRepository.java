package com.example.demo.repository;

import java.io.InputStream;
import java.util.List;

import com.example.demo.entity.Book;

import org.springframework.stereotype.Repository;
import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;

/**
 * BookRepository
 * 
 * SQL実行クラス
 */
@Repository
public class BookRepository {

    /**
     * configXml
     * 
     * DBとの接続設定及び各SQL文とのマッピングが定義されているファイルのパス
     */
    private final String configXml = "/mybatis-config.xml";

    /**
     * 全件取得処理
     * 
     * @return book_table内の全データリスト
     * @throws Exception InputStream生成失敗 または SELECT処理失敗
     */
    public List<Book> findAll() throws Exception {
        List<Book> bookList;
        // ルートとなる設定ファイルを読み込む
        try (InputStream in = BookRepository.class.getResourceAsStream(configXml)) {
            // 設定ファイルを元に、 SqlSessionFactory を作成する
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

            // SqlSessionFactory から SqlSession を生成する
            try (SqlSession session = factory.openSession()) {
                // SqlSession を使って select(全件) を実行する
                bookList = session.selectList("sample.mybatis.selectTest");
                session.close();
            }
        }
        return bookList;
    }

    /**
     * １件取得処理(主キー"book_id"を用いた検索)
     * 
     * @param bookId 取得対象のISBNコード
     * @return 取得したBook型データ
     * @throws Exception InputStream生成失敗 または SELECT処理失敗
     */
    public Book findByBookId(Long bookId) throws Exception {
        Book book;
        // ルートとなる設定ファイルを読み込む
        try (InputStream in = BookRepository.class.getResourceAsStream(configXml)) {
            // 設定ファイルを元に、 SqlSessionFactory を作成する
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

            // SqlSessionFactory から SqlSession を生成する
            try (SqlSession session = factory.openSession()) {
                // SqlSession を使って select(1件) を実行する
                book = session.selectOne("sample.mybatis.selectOne", bookId);
                session.close();
            }
        }
        return book;
    }

    /**
     * データ追加処理
     * 
     * @param book 追加するBook型データの情報
     * @throws Exception InputStream生成失敗 または INSERT処理失敗
     */
    public void insertBookData(Book book) throws Exception {
        // ルートとなる設定ファイルを読み込む
        try (InputStream in = BookRepository.class.getResourceAsStream(configXml)) {
            // 設定ファイルを元に、 SqlSessionFactory を作成する
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

            // SqlSessionFactory から SqlSession を生成する
            try (SqlSession session = factory.openSession()) {
                // SqlSession を使って insert を実行する
                session.insert("sample.mybatis.insertBook", book);
                session.commit();
            }
        }
    }

    /**
     * データ更新処理
     * 
     * @param book 更新するBook型データの情報
     * @throws Exception InputStream生成失敗 または UPDATE処理失敗
     */
    public void updateBookData(Book book) throws Exception {
        // ルートとなる設定ファイルを読み込む
        try (InputStream in = BookRepository.class.getResourceAsStream(configXml)) {
            // 設定ファイルを元に、 SqlSessionFactory を作成する
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

            // SqlSessionFactory から SqlSession を生成する
            try (SqlSession session = factory.openSession()) {
                // SqlSession を使って insert を実行する
                session.update("sample.mybatis.updateBook", book);
                session.commit();
            }
        }
    }

    /**
     * データ削除処理
     * 
     * @param bookId 削除するデータのISBNコード
     * @throws Exception InputStream生成失敗 または DELETE処理失敗
     */
    public void deleteBookData(Long bookId) throws Exception {
        // ルートとなる設定ファイルを読み込む
        try (InputStream in = BookRepository.class.getResourceAsStream(configXml)) {
            // 設定ファイルを元に、 SqlSessionFactory を作成する
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

            // SqlSessionFactory から SqlSession を生成する
            try (SqlSession session = factory.openSession()) {
                // SqlSession を使って delete を実行する
                session.delete("sample.mybatis.deleteBook", bookId);
                session.commit();
            }
        }
    }
}
