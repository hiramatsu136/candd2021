package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

import com.example.demo.service.PrefSymbolService;
import com.example.demo.entity.PrefSymbol;

/**
 * PrefSymbolController
 * 
 * 画面遷移操作クラス
 */
@Controller
public class PrefSymbolController {
    /** PrefSymbolService.javaとの紐づけ */
    @Autowired
    PrefSymbolService prefSymbolService;

    /**
     * 県のシンボル一覧表示画面遷移
     * 
     * pref_symbol_tableのデータ全件取得処理を行い、県のシンボル一覧表示画面に遷移
     * 
     * @param model データ一覧のリスト(prefSymbolList)格納用
     * @return prefSymbolList.html エラー発生時...failed.htmlに遷移
     */
    @RequestMapping("/prefSymbolList")
    public String getPrefSymbolList(Model model) {
        try {
            // データ全件取得処理
            List<PrefSymbol> prefSymbolList = this.prefSymbolService.selectAll();
            model.addAttribute("prefSymbolList", prefSymbolList);
            return "prefSymbolList";
        } catch (Exception e) {
            // データ全件取得処理にてエラー発生時、コンソールにエラー内容を出力し、failed.htmlに遷移
            System.out.println("----------------");
            System.out.println(e);
            System.out.println("----------------");
            return "failed";
        }
    }
}
