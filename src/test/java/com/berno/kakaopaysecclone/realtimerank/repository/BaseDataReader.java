package com.berno.kakaopaysecclone.realtimerank.repository;

import com.berno.kakaopaysecclone.realtimerank.domain.Stock;
import com.berno.kakaopaysecclone.realtimerank.service.StockService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@SpringBootTest
public class BaseDataReader {
    private static final String CSV_FILE_PATH = "/BaseData.csv";

    @Autowired
    private StockService stockService;

    @Test
    void createRandomData() throws IOException, InterruptedException {
        int cnt = 0;
        int id = 1;
        List<Stock> stockList = new ArrayList<>();
        while(cnt < 2) {
            stockList = stockService.generateRandomDatas();
            for (Stock stock : stockList) {
                String stockName = stock.getName();
                if (stockName.contains("&")) {
                    stockName = stockName.replace("&", "&amp;");
                }
                System.out.println("<stock id=\"" + id + "\" name=\"" + stockName + "\" price=\"" + stock.getPrice() + "\" code=\"" + stock.getCode() + "\" ts=\"" + (int) (System.currentTimeMillis() / 1000) + "\" />");
                id++;
            }
            Thread.sleep(3000);
            cnt++;
        }
        assertThat(stockList).isNotNull();

    }
}
