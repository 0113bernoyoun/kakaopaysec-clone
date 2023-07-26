package com.berno.kakaopaysecclone.realtimerank.domain;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@DisplayName("Stock 도메인 테스트")
class StockTest {

    @Test
    public void 생성_테스트() throws Exception{
        // given
        String name = "AJ네트웍스";
        BigDecimal price = new BigDecimal("100.00");
        String code = "095570";

        // when
        Stock stock = new Stock(name, price, code);

        // then
        assertThat(stock.getName()).isEqualTo(name);
        assertThat(stock.getPrice()).isEqualTo(price);
        assertThat(stock.getCode()).isEqualTo(code);
        assertThat(stock.getTs()).isNotNull();
    }

    @Test
    public void 타임스탬프_테스트() throws Exception{
        // given
        String name = "AJ네트웍스";
        BigDecimal price = new BigDecimal("100.00");
        String code = "095570";
        // when
        Stock stock = new Stock(name, price, code);

        // then
        assertThat(stock.getTs()).isNotNull();
        int currentTime = (int) (System.currentTimeMillis() / 1000);
        assertThat(stock.getTs()).isBetween(currentTime - 1, currentTime + 1);


    }


}