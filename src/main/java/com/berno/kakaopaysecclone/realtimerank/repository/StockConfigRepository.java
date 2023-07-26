package com.berno.kakaopaysecclone.realtimerank.repository;


import com.berno.kakaopaysecclone.realtimerank.domain.StockConfig;

public interface StockConfigRepository {
    StockConfig findStockConfig();

    void save(StockConfig stockConfig);
}
