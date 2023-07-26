package com.berno.kakaopaysecclone.realtimerank.repository;

import com.berno.kakaopaysecclone.realtimerank.domain.StockConfig;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockConfigJpaRepository extends JpaRepository<StockConfig, Integer> {
    StockConfig findTopById(Integer id);
}
