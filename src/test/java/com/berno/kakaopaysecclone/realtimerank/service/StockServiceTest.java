package com.berno.kakaopaysecclone.realtimerank.service;

import com.berno.kakaopaysecclone.common.BaseSpringBootTest;
import com.berno.kakaopaysecclone.realtimerank.model.StockDTO;
import com.berno.kakaopaysecclone.realtimerank.model.StockModel;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


class StockServiceTest extends BaseSpringBootTest {


    @Autowired
    private StockService stockService;


    @Test
    public void 증가한_주식_조회_테스트() throws Exception {
        // when
        List<StockDTO> increaseList = stockService.getUpList(0, 100);
        // then
        Assertions.assertEquals(increaseList.size(), 100);
    }

    @Test
    public void 하락한_주식_조회_테스트() throws Exception {
        // when
        List<StockDTO> decreaseList = stockService.getDownList(0, 100);
        // then
        Assertions.assertEquals(decreaseList.size(), 100);
    }

    @Test
    @Transactional
    public void 랜덤_데이터_생성_테스트() throws Exception {
        // when
        List<StockModel> allStockList = stockService.getAllStockList();
        List<StockModel> stockModels = stockService.saveRandomData();
        List<StockModel> rstList = stockService.getAllStockList();
        // then
        Assertions.assertNotEquals(stockModels.size(), 0);
        Assertions.assertNotEquals(allStockList.size(), rstList.size());


    }

}