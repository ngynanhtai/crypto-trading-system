package com.aquariux.crypto.controller;

import com.aquariux.crypto.common.Const;
import com.aquariux.crypto.dto.ResponseDTO;
import com.aquariux.crypto.service.PriceAggregationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(value = Const.API)
public class TradeController {

    @Autowired
    private PriceAggregationService priceAggregationService;

    @RequestMapping(method = RequestMethod.GET, value = "/best-prices")
    public ResponseEntity<?> latestBestAggregatedPrice() {
        ResponseDTO responseDTO = priceAggregationService.findLatestPrices();
        return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
    }
}
