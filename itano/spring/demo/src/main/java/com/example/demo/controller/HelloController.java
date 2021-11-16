package com.example.demo.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * HelloController
 */
@Controller
public class HelloController {
	/**
	 * ホーム画面遷移
	 * 
	 * @return hello.html
	 */
	@RequestMapping("/")
	public String index() {
		return "hello";
	}
}
