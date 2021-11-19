package com.example.demo.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.demo.entity.PrefSymbol;
import com.example.demo.repository.PrefSymbolRepository;

/**
 * PrefSymbolService
 * 
 * データ処理・整形クラス
 */
@Service
public class PrefSymbolService {
    /**
     * prefSymbol情報マッピング
     */
    @Autowired
    private PrefSymbolRepository prefSymbolRepository;

    /**
     * 全件取得処理
     * 
     * @return prefcode_tableのデータを結合したpref_symbol_tableの全データリスト
     */
    @Transactional(rollbackFor = Exception.class)
    public List<PrefSymbol> selectAll() throws Exception {
        // データ全件取得
        return this.prefSymbolRepository.selectAllSymbol();
    }

}
