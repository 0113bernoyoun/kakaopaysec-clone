package com.berno.kakaopaysecclone.realtimerank.repository;

import com.berno.kakaopaysecclone.common.BaseSpringBootTest;
import com.berno.kakaopaysecclone.realtimerank.domain.Stock;
import com.berno.kakaopaysecclone.realtimerank.model.StockQueryDTO;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@DisplayName("Stock 레포지토리 테스트")
class StockRepositoryTest extends BaseSpringBootTest {

    @Autowired
    private StockRepository stockRepository;
    @Test
    public void 전체_데이터_조회() throws Exception{
        // when
        List<Stock> stockList = stockRepository.getStockList();
        // then
        Assertions.assertEquals(stockList.size(), 1896);
    }

    @Test
    public void 증가한_주식_조회() throws Exception{
        // when
        List<StockQueryDTO> increasedStock = stockRepository.findIncreasedStock();
        // then
        Assertions.assertEquals(increasedStock.size(), 100);
    }

    @Test
    public void 하락한_주식_조회(){
        // when
        List<StockQueryDTO> decreasedStock = stockRepository.findDecreasedStock();
        // then
        Assertions.assertEquals(decreasedStock.size(), 100);
    }

}