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

@Entity
@Table(name = "member")
@Data
public class Member implements Serializable {

    @Id
    @Column(name = "MAIL_ADDRESS")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String mail_address;
    
    @Column(name = "NAME")
    private String name;

    @Column(name = "CREATED_DATE")
    private Date created_date;

    @Column(name = "CREATED_USER")
    private String created_user;

    @Column(name = "UPDATED_DATE")
    private Date updated_date;

    @Column(name = "UPDATED_USER")
    private String updated_user;
}
