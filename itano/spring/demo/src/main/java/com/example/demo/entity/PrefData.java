package com.example.demo.entity;

import java.io.Serializable;

import lombok.Data;

import javax.persistence.Entity;
import javax.persistence.Id;

/**
 * prefcode_table情報 Entity
 */
@Entity
@Data
public class PrefData implements Serializable {
  /**
   * 都道府県コード
   */
  @Id
  private Integer prefCode;
  /**
   * 都道府県名
   */
  private String prefName;

}
