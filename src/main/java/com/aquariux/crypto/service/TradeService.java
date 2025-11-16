package com.aquariux.crypto.service;

import com.aquariux.crypto.dto.ResponseDTO;
import com.aquariux.crypto.dto.request.TradeRequestDTO;

public interface TradeService {
    ResponseDTO executeTrade(TradeRequestDTO requestDTO);
}
