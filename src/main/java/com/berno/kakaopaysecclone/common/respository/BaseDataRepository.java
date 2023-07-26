package com.berno.kakaopaysecclone.common.respository;

import com.berno.kakaopaysecclone.realtimerank.model.StockPojo;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Repository;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Repository
public class BaseDataRepository {
    private static final String CSV_FILE_PATH = "/BaseData.csv";


    public List<StockPojo> readAll() throws IOException {
        List<StockPojo> stocks = new ArrayList<>();

        try (BufferedReader br = new BufferedReader(new InputStreamReader(new ClassPathResource(CSV_FILE_PATH).getInputStream()))) {
            String line;
            while ((line = br.readLine()) != null) {
                if(line.contains("code,name")){
                    continue;
                }
                String[] values = line.split(",");
                StockPojo stock = new StockPojo(values[0], values[1], new BigDecimal(values[2]));
                stocks.add(stock);
            }
        }

        return stocks;
    }


}
