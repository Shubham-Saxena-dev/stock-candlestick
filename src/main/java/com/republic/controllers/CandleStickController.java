package com.republic.controllers;

/*
 * Controller class to receive candlestick request
 * */

import com.republic.model.CandleStick;
import com.republic.service.CandleStickService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
public class CandleStickController {

    private final CandleStickService service;

    public CandleStickController(CandleStickService service) {
        this.service = service;
    }

    @GetMapping("/candlestick")
    public ResponseEntity<List<CandleStick>> getCandleSticks(@RequestParam String isin) {
        return new ResponseEntity<>(
                service.computeCandleSticks(isin), HttpStatus.OK);
    }
}
