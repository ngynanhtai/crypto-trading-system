package com.aquariux.crypto.service;

import com.aquariux.crypto.dto.ExternalPriceDTO;

public interface ExternalClientService {
    ExternalPriceDTO fetchPrice(String sourceCode, String tradingPair);
}
