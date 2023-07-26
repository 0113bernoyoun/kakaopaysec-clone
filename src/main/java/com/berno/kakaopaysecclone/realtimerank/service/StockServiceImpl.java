package com.berno.kakaopaysecclone.realtimerank.service;


import com.berno.kakaopaysecclone.common.enums.ExceptionType;
import com.berno.kakaopaysecclone.common.exception.CustomException;
import com.berno.kakaopaysecclone.common.respository.BaseDataRepository;
import com.berno.kakaopaysecclone.realtimerank.domain.Stock;
import com.berno.kakaopaysecclone.realtimerank.model.StockDTO;
import com.berno.kakaopaysecclone.realtimerank.model.StockModel;
import com.berno.kakaopaysecclone.realtimerank.model.StockPojo;
import com.berno.kakaopaysecclone.realtimerank.model.StockQueryDTO;
import com.berno.kakaopaysecclone.realtimerank.repository.StockRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Slf4j
@Service
@RequiredArgsConstructor
public class StockServiceImpl implements StockService {

    private final BaseDataRepository baseDataRepository;

    private final StockRepository stockRepository;

    @Value("${const.show-limit-number}")
    private Integer limitDataNum;

    @Override
    @Transactional
    public List<StockModel> saveRandomData() {
        try {
            List<Stock> stockList = generateRandomDatas();
            stockRepository.saveAll(stockList);
            return getStockModelListFromStockList(stockList);
        } catch (IOException e) {
            log.error("데이터 저장 중 에러 발생 {} ", e.toString());
            throw new CustomException(ExceptionType.CANNOT_SAVE_RANDOM_DATA);
        }
    }

    @Override
    public List<StockDTO> getUpList(Integer startIdx, Integer offset) {
        List<StockQueryDTO> stockList = stockRepository.findIncreasedStock();

        return getStockDTOList(startIdx, offset, stockList);
    }

    @Override
    public List<StockDTO> getDownList(Integer startIdx, Integer offset) {
        List<StockQueryDTO> stockList = stockRepository.findDecreasedStock();
        return getStockDTOList(startIdx, offset, stockList);
    }


    @Override
    public List<StockModel> getAllStockList() {
        List<Stock> allStockList = stockRepository.getStockList();
        return getStockModelListFromStockList(allStockList);
    }

    private List<StockDTO> getStockDTOList(Integer startIdx, Integer offset, List<StockQueryDTO> stockList) {
        if (offset > stockList.size() - startIdx) {
            offset = stockList.size();
            if (offset - startIdx >= limitDataNum) {
                startIdx = 0;
                offset = 99;
            }
        }
        stockList = new ArrayList<>(stockList.subList(startIdx, offset));

        return getStockDTOListFromStockList(stockList);
    }


    private List<StockModel> getStockModelListFromStockList(List<Stock> stockList) {
        List<StockModel> stockModelList = new ArrayList<>();
        for (Stock stock : stockList) {
            stockModelList.add(stockToStockModel(stock));
        }
        return stockModelList;
    }

    private StockModel stockToStockModel(Stock stock) {
        return new StockModel(stock.getName(), stock.getCode(), stock.getPrice());
    }

    private List<StockDTO> getStockDTOListFromStockList(List<StockQueryDTO> stockList) {
        List<StockDTO> stockDTOList = new ArrayList<>();
        for (StockQueryDTO rst : stockList) {
            stockDTOList.add(stockToStockDTO(rst.getStock(), BigDecimal.valueOf(rst.getRateOfChange())));
        }

        return stockDTOList;
    }

    private StockDTO stockToStockDTO(Stock stock, BigDecimal rate) {
        double roundOff = rate.setScale(2, RoundingMode.HALF_UP).doubleValue();
        return new StockDTO(stock.getName(), stock.getCode(), stock.getPrice(), roundOff);
    }

    public List<Stock> generateRandomDatas() throws IOException {
        List<StockPojo> stockPojoList = baseDataRepository.readAll();
        List<Stock> stockList = new ArrayList<>();

        for (StockPojo stockPojo : stockPojoList) {
            int randomPercentage = ThreadLocalRandom.current().nextInt(-70, 201);
            BigDecimal currentPrice = stockPojo.getPrice();

            if (randomPercentage < 0) {
                int discountPercentage = -randomPercentage;
                int discountedAmount = (int) (currentPrice.intValue() * (1 - (discountPercentage / 100.0)));
                stockList.add(new Stock(stockPojo.getName(), new BigDecimal(discountedAmount), stockPojo.getCode()));
                continue;
            }

            int increasePercentage = randomPercentage;
            int increasedAmount = (int) (currentPrice.intValue() * (1 + (increasePercentage / 100.0)));
            stockList.add(new Stock(stockPojo.getName(), new BigDecimal(increasedAmount), stockPojo.getCode()));
        }

        return stockList;
    }
}
