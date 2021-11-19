package com.example.demo.entity;

import java.io.Serializable;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * pref_symbol_table情報 Entity
 */
@Entity
@Data
public class PrefSymbol implements Serializable {
  /**
   * 主キー用ID
   */
  @Id
  private Integer id;
  /**
   * 都道府県コード
   */
  private Integer prefCode;
  /**
   * 県のシンボル
   */
  private String prefSymbol;
  /**
   * シンボルの分類
   */
  private String symbolClass;
  /**
   * 都道府県データ
   */
  private PrefData prefData;

}
