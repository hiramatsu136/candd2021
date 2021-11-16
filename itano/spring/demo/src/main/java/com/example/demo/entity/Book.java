package com.example.demo.entity;

import java.io.Serializable;
import java.util.Date;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

/**
 * book_table情報 Entity
 */
@Entity
@Data
@Table(name = "book_table")
public class Book implements Serializable {
  /**
   * ISBNコード
   */
  @Id
  @Column(name = "book_id")
  @GeneratedValue(strategy = GenerationType.AUTO)
  private Long book_id;
  /**
   * 出版日
   */
  @Column(name = "release_date")
  private Date release_date;
  /**
   * タイトル
   */
  @Column(name = "title")
  private String title;
  /**
   * 著者
   */
  @Column(name = "author")
  private String author;
  /**
   * 値段
   */
  @Column(name = "price")
  private Integer price;
  /**
   * 在庫数
   */
  @Column(name = "stock")
  private Integer stock;
}
