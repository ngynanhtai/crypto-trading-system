package com.aquariux.crypto.service;

import com.aquariux.crypto.dto.ResponseDTO;

public interface PriceAggregationService {

    /**
     * Aggregate prices from various external sources and update internal price records.
     * Typically used for scheduled tasks to refresh current market prices.
     */
    void aggregatePrices();

    /**
     * Retrieve the latest aggregated trading prices.
     *
     * @return ResponseDTO containing the newest available price data
     */
    ResponseDTO findLatestPrices();
}
