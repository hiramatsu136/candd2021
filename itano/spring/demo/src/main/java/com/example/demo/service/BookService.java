package com.example.demo.service;

import com.example.demo.entity.Book;
import com.example.demo.form.BookForm;
import com.example.demo.repository.BookRepository;
import java.util.List;
import java.text.SimpleDateFormat;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * BookService
 * 
 * データ処理・整形クラス
 */
@Service
public class BookService {

    /** BookRepository.javaとの紐づけ */
    @Autowired
    BookRepository repository;

    /**
     * データ全件取得処理
     * 
     * @return DBに格納されている全てのデータ
     * @throws Exception repository.findAllにて発生したエラーを流す
     */
    public List<Book> findAll() throws Exception {
        // データ全件取得
        return this.repository.findAll();
    }

    /**
     * ISBNコードを元にデータを１件取得し、BookForm型に整形する処理
     * 
     * @param bookId 対象のデータのISBNコード
     * @return 対象のBookデータをBookFormに整形したデータ
     * @throws Exception repository.findByBookIdにて発生したエラー または
     *                   Book型をBookForm型に整形する際に発生したエラー
     */
    public BookForm findByBookIdToBookForm(String bookId) throws Exception {
        // 対象のBook型データ取得
        Book book = this.repository.findByBookId(Long.parseLong(bookId));
        // 取得したBook型データをBookForm型に整形
        BookForm bookForm = new BookForm();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        bookForm.setBook_id(String.valueOf(book.getBook_id()));
        bookForm.setRelease_date(format.format(book.getRelease_date()));
        bookForm.setTitle(book.getTitle());
        bookForm.setAuthor(book.getAuthor());
        bookForm.setPrice(String.valueOf(book.getPrice()));
        bookForm.setStock(String.valueOf(book.getStock()));

        return bookForm;
    }

    /**
     * データ追加処理
     * 
     * @param bookForm 追加するデータ
     * @throws Exception repository.insertBookDataにて発生したエラー または
     *                   BookForm型をBook型に整形する際に発生したエラー
     */
    public void insertBook(BookForm bookForm) throws Exception {
        // 追加するデータをBook型に整形
        Book book = new Book();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        book.setBook_id(Long.parseLong(bookForm.getBook_id()));
        book.setRelease_date(format.parse(bookForm.getRelease_date()));
        book.setTitle(bookForm.getTitle());
        book.setAuthor(bookForm.getAuthor());
        book.setPrice(Integer.parseInt(bookForm.getPrice()));
        book.setStock(Integer.parseInt(bookForm.getStock()));

        // データ追加
        this.repository.insertBookData(book);
    }

    /**
     * データ更新処理
     * 
     * @param bookForm 更新データ
     * @throws Exception repository.updateBookDataにて発生したエラー または
     *                   BookForm型をBook型に整形する際に発生したエラー
     */
    public void updateBook(BookForm bookForm) throws Exception {
        // 更新するデータをBook型に整形
        Book book = new Book();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        book.setBook_id(Long.parseLong(bookForm.getBook_id()));
        book.setRelease_date(format.parse(bookForm.getRelease_date()));
        book.setTitle(bookForm.getTitle());
        book.setAuthor(bookForm.getAuthor());
        book.setPrice(Integer.parseInt(bookForm.getPrice()));
        book.setStock(Integer.parseInt(bookForm.getStock()));

        // データ更新
        this.repository.updateBookData(book);
    }

    /**
     * データ削除処理
     * 
     * @param bookId 削除対象のISBNコード
     * @throws Exception repository.deleteBookDataにて発生したエラー または
     *                   BookForm型をBook型に整形する際に発生したエラー
     */
    public void deleteBook(String bookId) throws Exception {
        // データ削除
        this.repository.deleteBookData(Long.parseLong(bookId));
    }
}
