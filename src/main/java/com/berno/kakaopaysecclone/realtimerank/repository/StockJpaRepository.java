package com.berno.kakaopaysecclone.realtimerank.repository;


import com.berno.kakaopaysecclone.realtimerank.domain.Stock;
import com.berno.kakaopaysecclone.realtimerank.model.StockQueryInterface;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;


public interface StockJpaRepository extends JpaRepository<Stock, Integer> {

    @Query("SELECT s1 as stock, COALESCE((s1.price - s2.price) / s2.price, 0) as rateOfChange FROM Stock s1 JOIN Stock s2 ON s1.code = s2.code " +
            "WHERE s1.ts = (SELECT MAX(ts) FROM Stock) AND s2.ts = (SELECT MAX(ts) FROM Stock WHERE ts < s1.ts) " +
            "AND s1.price > s2.price ORDER BY s1.price / s2.price DESC LIMIT :limitValue")
    List<StockQueryInterface> findIncreasedPriceStock(@Param("limitValue") Integer limitValue);

    @Query("SELECT s1 as stock, COALESCE((s1.price - s2.price) / s2.price, 0) as rateOfChange FROM Stock s1 INNER JOIN Stock s2 ON s1.code = s2.code AND s1.ts > s2.ts " +
            "WHERE s1.ts = (SELECT MAX(ts) FROM Stock WHERE code = s1.code) " +
            "AND s1.price < s2.price " +
            "ORDER BY (s1.price - s2.price) / s2.price DESC LIMIT :limitValue")
    List<StockQueryInterface> findDecreasedPriceStock(@Param("limitValue") Integer limitValue);
}

