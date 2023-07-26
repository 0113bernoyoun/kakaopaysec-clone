package com.berno.kakaopaysecclone.realtimerank.repository;


import com.berno.kakaopaysecclone.realtimerank.domain.Stock;
import com.berno.kakaopaysecclone.realtimerank.model.StockQueryDTO;

import java.util.List;

public interface StockRepository {
    List<Stock> getStockList();

    List<Stock> saveAll(List<Stock> stockList);

    List<StockQueryDTO> findIncreasedStock();

    List<StockQueryDTO> findDecreasedStock();

    void save(Stock stock);

    List<Stock> findAll();
}
