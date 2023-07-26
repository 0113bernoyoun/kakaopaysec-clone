package com.berno.kakaopaysecclone.realtimerank.repository;


import com.berno.kakaopaysecclone.common.enums.ExceptionType;
import com.berno.kakaopaysecclone.common.exception.CustomException;
import com.berno.kakaopaysecclone.realtimerank.domain.Stock;
import com.berno.kakaopaysecclone.realtimerank.model.StockQueryDTO;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.stream.Collectors;

@Slf4j
@Repository
@RequiredArgsConstructor
public class StockRepositoryImpl implements StockRepository {

    private final StockJpaRepository stockJpaRepository;

    @PersistenceContext
    private EntityManager entityManager;

    @Value("${const.show-limit-number}")
    private Integer limitDataNum;

    @Override
    public List<Stock> getStockList() {
        return stockJpaRepository.findAll();
    }

    @Override
    public List<Stock> saveAll(List<Stock> stockList) {
        try {
            stockJpaRepository.saveAll(stockList);
        } catch (DataIntegrityViolationException e) {
            log.error("데이터 저장 중 에러 발생 {} ", e.toString());
            throw new CustomException(ExceptionType.CANNOT_SAVE_RANDOM_DATA);
        }
        return stockList;
    }

    @Override
    public List<StockQueryDTO> findIncreasedStock() {
        return stockJpaRepository.findIncreasedPriceStock(limitDataNum)
                .stream()
                .map(stockQueryInterface -> new StockQueryDTO(stockQueryInterface.getStock(), stockQueryInterface.getRateOfChange()))
                .collect(Collectors.toList());
    }

    @Override
    public List<StockQueryDTO> findDecreasedStock() {
        return stockJpaRepository.findDecreasedPriceStock(limitDataNum)
                .stream()
                .map(stockQueryInterface -> new StockQueryDTO(stockQueryInterface.getStock(), stockQueryInterface.getRateOfChange()))
                .collect(Collectors.toList());
    }


    @Override
    public void save(Stock stock) {
        stockJpaRepository.save(stock);
    }

    @Override
    public List<Stock> findAll() {
        return stockJpaRepository.findAll();
    }
}
