package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

import java.io.InputStream;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.session.SqlSession;
import org.apache.ibatis.session.SqlSessionFactory;
import org.apache.ibatis.session.SqlSessionFactoryBuilder;
 
@Controller
public class MyBatisController {
    @RequestMapping("/mybatis")
	public String index() throws Exception {
         // ルートとなる設定ファイルを読み込む
         try (InputStream in = MyBatisController.class.getResourceAsStream("/mybatis-config.xml")) {
            // 設定ファイルを元に、 SqlSessionFactory を作成する
            SqlSessionFactory factory = new SqlSessionFactoryBuilder().build(in);

            // SqlSessionFactory から SqlSession を生成する
            try (SqlSession session = factory.openSession()) {
                // SqlSession を使って SQL を実行する
                List<Map<String, Object>> result = session.selectList("sample.mybatis.selectTest");

                result.forEach(row -> {
                    System.out.println("---------------");
                    row.forEach((columnName, value) -> {
                        System.out.printf("columnName=%s, value=%s%n", columnName, value);
                    });
                });
            }
        }
		return "mybatisTest";
	}
}
