package com.aquariux.crypto.service;

import com.aquariux.crypto.dto.ResponseDTO;

public interface PriceAggregationService {
    void aggregatePrices();

    ResponseDTO findLatestPrices();
}
