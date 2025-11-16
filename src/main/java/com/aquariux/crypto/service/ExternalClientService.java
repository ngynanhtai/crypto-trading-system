package com.aquariux.crypto.service;

import com.aquariux.crypto.dto.ExternalPriceDTO;

public interface ExternalClientService {

    /**
     * Fetch the current price of a trading pair from an external provider.
     *
     * @param sourceCode   the code representing the external price source (e.g., BINANCE, COINBASE)
     * @param tradingPair  the symbol of the trading pair to fetch (e.g., BTC/USDT)
     * @return ExternalPriceDTO containing the fetched price information
     */
    ExternalPriceDTO fetchPrice(String sourceCode, String tradingPair);
}
