package com.berno.kakaopaysecclone.realtimerank.controller;

import com.berno.kakaopaysecclone.common.enums.ResponseType;
import com.berno.kakaopaysecclone.common.model.BasicResponse;
import com.berno.kakaopaysecclone.realtimerank.service.StockService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/sec/realtime")
@RequiredArgsConstructor
public class StockController {

    @Autowired
    private StockService stockService;

    @GetMapping("/list")
    public BasicResponse getList() {
        return BasicResponse.toResponse(ResponseType.OK, stockService.getAllStockList());
    }

    @GetMapping("/up")
    public BasicResponse getUpList(@RequestParam Integer startIdx, @RequestParam Integer offset) {
        return BasicResponse.toResponse(ResponseType.OK, stockService.getUpList(startIdx, offset));
    }

    @GetMapping("/down")
    public BasicResponse getDownList(@RequestParam Integer startIdx, @RequestParam Integer offset) {
        return BasicResponse.toResponse(ResponseType.OK, stockService.getDownList(startIdx, offset));
    }

    @PutMapping("/stocks")
    public BasicResponse saveRandomData() {
        return BasicResponse.toResponse(ResponseType.OK, stockService.saveRandomData());
    }
}
