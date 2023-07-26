package com.berno.kakaopaysecclone.realtimerank.repository;

import com.berno.kakaopaysecclone.realtimerank.domain.StockConfig;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@AllArgsConstructor
public class StockConfigRepositoryImpl implements StockConfigRepository{
    private StockConfigJpaRepository stockConfigJpaRepository;
    @Override
    public StockConfig findStockConfig() {
        return stockConfigJpaRepository.findTopById(1);
    }

    @Override
    public void save(StockConfig stockConfig) {
        stockConfigJpaRepository.save(stockConfig);
    }
}
