package com.berno.kakaopaysecclone.realtimerank.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.math.BigDecimal;
@AllArgsConstructor
@Getter
public class StockPojo {
    private String code;
    private String name;
    private BigDecimal price;
}
