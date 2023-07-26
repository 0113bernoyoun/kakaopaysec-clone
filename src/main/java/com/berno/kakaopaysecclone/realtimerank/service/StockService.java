package com.berno.kakaopaysecclone.realtimerank.service;


import com.berno.kakaopaysecclone.realtimerank.domain.Stock;
import com.berno.kakaopaysecclone.realtimerank.model.StockDTO;
import com.berno.kakaopaysecclone.realtimerank.model.StockModel;

import java.io.IOException;
import java.util.List;

public interface StockService{
    List<StockModel> saveRandomData();

    List<StockDTO> getUpList(Integer startIdx, Integer offset);

    List<StockDTO> getDownList(Integer startIdx, Integer offset);

    List<StockModel> getAllStockList();

    List<Stock> generateRandomDatas() throws IOException;

}
