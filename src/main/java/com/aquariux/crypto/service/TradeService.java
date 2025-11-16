package com.aquariux.crypto.service;

import com.aquariux.crypto.dto.ResponseDTO;
import com.aquariux.crypto.dto.request.TradeRequestDTO;

public interface TradeService {

    /**
     * Execute a trade to perform a buy or sell transaction for a user.
     *
     * @param requestDTO the trade request containing required information
     *                   (userId, tradingPairId, transactionType, amount, etc.)
     * @return ResponseDTO containing the result of the executed trade
     */
    ResponseDTO executeTrade(TradeRequestDTO requestDTO);
}
