package com.sample.sample.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.Data;

/**
 * member テーブルのエンティティ
 */
@Entity
@Table(name = "member")
@Data
public class Member implements Serializable {

    /**
     * メールアドレス
     */
    @Id
    @Column(name = "MAIL_ADDRESS")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String mail_address;
     
    /**
     * 名前
     */
    @Column(name = "NAME")
    private String name;

    /**
     * 登録日時
     */
    @Column(name = "CREATED_DATE")
    private Date created_date;

    /**
     * 登録者
     */
    @Column(name = "CREATED_USER")
    private String created_user;

    /**
     * 更新日時
     */
    @Column(name = "UPDATED_DATE")
    private Date updated_date;

    /**
     * 更新者
     */
    @Column(name = "UPDATED_USER")
    private String updated_user;
}
