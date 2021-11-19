package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.ui.Model;

import java.util.List;

import com.example.demo.entity.Book;
import com.example.demo.form.BookForm;
import com.example.demo.service.BookService;

/**
 * MyBatisController
 * 
 * 画面遷移操作クラス
 */
@Controller
@Slf4j
public class MyBatisController {

    /** BookService.javaとの紐づけ */
    @Autowired
    BookService bookService;

    /**
     * データ一覧表示画面遷移
     * 
     * データ全件取得処理を行い、データ一覧表示画面に遷移
     * 
     * @param model データ一覧のリスト(bookList)格納用
     * @return bookList.html エラー発生時...failed.htmlに遷移
     */
    @RequestMapping("/bookList")
    public String getBookList(Model model) {
        try {
            // DBデータ操作処理開始宣言
            log.info("{} starting", "bookService.findAll");

            // データ全件取得処理
            List<Book> bookList = this.bookService.findAll();

            // DBデータ操作処理終了宣言
            log.info("{} finished", "bookService.findAll");

            model.addAttribute("bookList", bookList);
            return "bookList";
        } catch (Exception e) {
            // データ全件取得処理にてエラー発生時、エラー内容をログファイルに出力し、failed.htmlに遷移
            log.error(e.getMessage());
            return "failed";
        }
    }

    /**
     * データ追加画面遷移
     * 
     * データ追加画面に遷移
     * 
     * @param model データ入力フォーマット(bookForm)格納用
     * @return add.html
     */
    @GetMapping("/add")
    public String addForm(Model model) {
        // データ入力フォーマットのインスタンスを新規作成し格納
        model.addAttribute("bookForm", new BookForm());
        return "add";
    }

    /**
     * データ追加処理結果画面遷移
     * 
     * データ追加処理を行い、データ処理結果画面に遷移
     * 
     * @param bookForm データ入力フォーマット(入力済み)
     * @return success.html エラー発生時、failed.htmlに遷移
     */
    @PostMapping("/add/execute")
    public String addBook(@ModelAttribute BookForm bookForm) {
        try {
            // DBデータ操作処理開始宣言
            log.info("{} starting", "bookService.insertBook");

            // データ追加処理
            this.bookService.insertBook(bookForm);
            
            // DBデータ操作処理終了宣言
            log.info("{} finished", "bookService.insertBook");

            return "success";
        } catch (Exception e) {
            // データ追加処理にてエラー発生時、エラー内容をログファイルに出力し、failed.htmlに遷移
            log.error(e.getMessage());
            return "failed";
        }
    }

    /**
     * データ更新内容入力画面遷移
     * 
     * 対象のデータ１件取得処理を行い、データ更新画面に遷移
     * 
     * @param bookId データ更新対象のISBNコード
     * @param model  更新対象のデータの格納用
     * @return bookUpdate.html エラー発生時、failed.htmlに遷移
     */
    @GetMapping("/update/{bookId}")
    public String updateBookCheck(@PathVariable String bookId, Model model) {
        try {
            // DBデータ操作処理開始宣言
            log.info("{} starting", "bookService.findByBookIdToBookForm");

            // データ１件取得処理
            BookForm bookForm = this.bookService.findByBookIdToBookForm(bookId);

            // DBデータ操作処理終了宣言
            log.info("{} finished", "bookService.findByBookIdToBookForm");

            model.addAttribute("bookForm", bookForm);
            return "bookUpdate";
        } catch (Exception e) {
            // データ１件取得処理にてエラー発生時、エラー内容をログファイルに出力し、failed.htmlに遷移
            log.error(e.getMessage());
            return "failed";
        }
    }

    /**
     * データ更新処理結果画面遷移
     * 
     * データ更新処理を行い、データ処理結果画面に遷移
     * 
     * @param bookForm データ入力フォーマット(入力済み)
     * @return success.html エラー発生時、failed.htmlに遷移
     */
    @PostMapping("/update/execute")
    public String updateBook(@ModelAttribute BookForm bookForm) {
        try {
            // DBデータ操作処理開始宣言
            log.info("{} starting", "bookService.updateBook");

            // データ更新処理
            this.bookService.updateBook(bookForm);

            // DBデータ操作処理終了宣言
            log.info("{} finished", "bookService.updateBook");

            return "success";
        } catch (Exception e) {
            // データ更新処理にてエラー発生時、エラー内容をログファイルに出力し、failed.htmlに遷移
            log.error(e.getMessage());
            return "failed";
        }
    }

    /**
     * データ削除確認画面遷移
     * 
     * 対象のデータ１件取得処理を行い、データ削除画面に遷移
     * 
     * @param bookId データ削除対象のISBNコード
     * @param model  削除対象のデータの格納用
     * @return bookDelete.html エラー発生時、failed.htmlに遷移
     */
    @GetMapping("/delete/{bookId}")
    public String deleteBookCheck(@PathVariable String bookId, Model model) {
        try {
            // DBデータ操作処理開始宣言
            log.info("{} starting", "bookService.findByBookIdToBookForm");

            // データ１件取得処理
            BookForm bookForm = this.bookService.findByBookIdToBookForm(bookId);

            // DBデータ操作処理終了宣言
            log.info("{} finished", "bookService.findByBookIdToBookForm");

            model.addAttribute("bookForm", bookForm);
            return "bookDelete";
        } catch (Exception e) {
            // データ１件取得処理にてエラー発生時、エラー内容をログファイルに出力し、failed.htmlに遷移
            log.error(e.getMessage());
            return "failed";
        }
    }

    /**
     * データ削除処理結果画面遷移
     * 
     * データ削除処理を行い、データ処理結果画面に遷移
     * 
     * @param bookId データ削除対象のISBNコード
     * @return success.html エラー発生時、failed.htmlに遷移
     */
    @GetMapping("/delete/execute/{bookId}")
    public String deleteBook(@PathVariable String bookId) {
        try {
            // DBデータ操作処理開始宣言
            log.info("{} starting", "bookService.deleteBook");

            // データ削除処理
            this.bookService.deleteBook(bookId);

            // DBデータ操作処理終了宣言
            log.info("{} finished", "bookService.deleteBook");

            return "success";
        } catch (Exception e) {
            // データ削除処理にてにてエラー発生時、エラー内容をログファイルに出力し、failed.htmlに遷移
            log.error(e.getMessage());
            return "failed";
        }
    }
}
