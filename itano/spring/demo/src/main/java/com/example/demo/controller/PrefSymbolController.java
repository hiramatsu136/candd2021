package com.example.demo.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import lombok.extern.slf4j.Slf4j;

import java.util.List;

import com.example.demo.service.PrefSymbolService;
import com.example.demo.entity.PrefSymbol;

/**
 * PrefSymbolController
 * 
 * 画面遷移操作クラス
 */
@Controller
@Slf4j
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
            // DBデータ取得処理開始宣言
            log.info("{} starting", "prefSymbolService.selectAll");

            // データ全件取得処理
            List<PrefSymbol> prefSymbolList = this.prefSymbolService.selectAll();

            // DBデータ取得処理終了宣言
            log.info("{} finished", "prefSymbolService.selectAll");

            model.addAttribute("prefSymbolList", prefSymbolList);
            return "prefSymbolList";
        } catch (Exception e) {
            // データ全件取得処理にてエラー発生時、エラー内容をログファイルに出力し、failed.htmlに遷移
            log.error(e.getMessage());
            return "failed";
        }
    }
}
