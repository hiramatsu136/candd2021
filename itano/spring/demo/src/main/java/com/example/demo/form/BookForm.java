package com.example.demo.form;

import lombok.Data;

/**
 * BookForm
 * 
 * データ入力用フォーマット
 */
@Data
public class BookForm {
    /**
     * ISBNコード
     */
    private String book_id;
    /**
     * 出版日
     */
    private String release_date;
    /**
     * タイトル
     */
    private String title;
    /**
     * 著者
     */
    private String author;
    /**
     * 値段
     */
    private String price;
    /**
     * 在庫数
     */
    private String stock;
}
