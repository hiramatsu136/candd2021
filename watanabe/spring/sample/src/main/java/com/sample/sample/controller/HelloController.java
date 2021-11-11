package com.sample.sample.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * Hello Controller 
 */
@Controller
public class HelloController {
    /**
     * Hello World表示
     * @return Hello画面
     */
    @RequestMapping("/hello")
    public String index() {
        return "hello";
    }
}