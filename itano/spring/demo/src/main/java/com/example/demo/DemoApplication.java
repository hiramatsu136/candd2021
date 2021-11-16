package com.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * DemoApplication
 * 
 * アプリケーション起動クラス： OracleDB内のテーブル(book_table)に対して、CRUDを行うWebアプリケーションを起動する
 */
@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}
