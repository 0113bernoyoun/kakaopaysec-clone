package com.berno.kakaopaysecclone.common.service;


import com.berno.kakaopaysecclone.common.respository.BaseDataRepository;
import com.berno.kakaopaysecclone.realtimerank.domain.Stock;
import com.berno.kakaopaysecclone.realtimerank.domain.StockConfig;
import com.berno.kakaopaysecclone.realtimerank.model.StockPojo;
import com.berno.kakaopaysecclone.realtimerank.repository.StockConfigRepository;
import com.berno.kakaopaysecclone.realtimerank.repository.StockRepository;
import jakarta.annotation.PostConstruct;
import lombok.AllArgsConstructor;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.util.List;

@Component
@EnableTransactionManagement
@AllArgsConstructor
@Profile("!test")
public class InitData {


    private StockRepository stockRepository;

    private StockConfigRepository stockConfigRepository;
    private BaseDataRepository baseDataRepository;


    @PostConstruct
    @Transactional
    public void initBaseData() throws IOException {
        StockConfig stockConfig = stockConfigRepository.findStockConfig();
        if (stockConfig == null || !stockConfig.getInitYn()) {
            List<StockPojo> stockPojoList = baseDataRepository.readAll();
            stockPojoList.forEach(
                    stockPojo -> stockRepository.save(
                            new Stock(
                                    stockPojo.getName(),
                                    stockPojo.getPrice(),
                                    stockPojo.getCode())
                    )
            );
            if (stockConfig != null) {
                stockConfig.initComplete();
                stockConfigRepository.save(stockConfig);
            }
        }
    }

}
