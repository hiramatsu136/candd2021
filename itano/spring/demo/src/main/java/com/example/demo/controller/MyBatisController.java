package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
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
            // データ全件取得処理
            List<Book> bookList = this.bookService.findAll();
            model.addAttribute("bookList", bookList);
            return "bookList";
        } catch (Exception e) {
            // データ全件取得処理にてエラー発生時、コンソールにエラー内容を出力し、failed.htmlに遷移
            System.out.println("----------------");
            System.out.println(e);
            System.out.println("----------------");
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
            // データ追加処理
            this.bookService.insertBook(bookForm);
            return "success";
        } catch (Exception e) {
            // データ追加処理にてエラー発生時、コンソールにエラー内容を出力し、failed.htmlに遷移
            System.out.println("----------------");
            System.out.println(e);
            System.out.println("----------------");
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
            // データ１件取得処理
            BookForm bookForm = this.bookService.findByBookIdToBookForm(bookId);
            model.addAttribute("bookForm", bookForm);
            return "bookUpdate";
        } catch (Exception e) {
            // データ１件取得処理にてエラー発生時、コンソールにエラー内容を出力し、failed.htmlに遷移
            System.out.println("----------------");
            System.out.println(e);
            System.out.println("----------------");
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
            // データ更新処理
            this.bookService.updateBook(bookForm);
            return "success";
        } catch (Exception e) {
            // データ更新処理にてエラー発生時、コンソールにエラー内容を出力し、failed.htmlに遷移
            System.out.println("----------------");
            System.out.println(e);
            System.out.println("----------------");
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
            // データ１件取得処理
            BookForm bookForm = this.bookService.findByBookIdToBookForm(bookId);
            model.addAttribute("bookForm", bookForm);
            return "bookDelete";
        } catch (Exception e) {
            // データ１件取得処理にてエラー発生時、コンソールにエラー内容を出力し、failed.htmlに遷移
            System.out.println("----------------");
            System.out.println(e);
            System.out.println("----------------");
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
            // データ削除処理
            this.bookService.deleteBook(bookId);
            return "success";
        } catch (Exception e) {
            // データ削除処理にてにてエラー発生時、コンソールにエラー内容を出力し、failed.htmlに遷移
            System.out.println("----------------");
            System.out.println(e);
            System.out.println("----------------");
            return "failed";
        }
    }
}
