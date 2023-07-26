package com.berno.kakaopaysecclone.realtimerank.model;

import com.berno.kakaopaysecclone.realtimerank.domain.Stock;

public interface StockQueryInterface {
    Stock getStock();
    Integer getRateOfChange();
}
