package com.berno.kakaopaysecclone.realtimerank.model;

import com.berno.kakaopaysecclone.realtimerank.domain.Stock;
import lombok.Data;

@Data
public class StockQueryDTO {
    Stock stock;
    Integer rateOfChange;

    public StockQueryDTO(Stock stock, Integer rateOfChange){
        this.stock = stock;
        this.rateOfChange = rateOfChange;
    }
}
