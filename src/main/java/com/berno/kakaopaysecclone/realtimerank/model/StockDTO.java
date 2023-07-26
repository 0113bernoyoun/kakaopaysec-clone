package com.berno.kakaopaysecclone.realtimerank.model;

import java.math.BigDecimal;

public record StockDTO(String name, String code, BigDecimal price, Double rateOfChange) {
}
