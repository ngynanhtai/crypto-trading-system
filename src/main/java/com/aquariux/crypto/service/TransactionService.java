package com.aquariux.crypto.service;

import com.aquariux.crypto.dto.ResponseDTO;
import org.springframework.data.domain.Pageable;

public interface TransactionService {
    ResponseDTO getUserTradeTransaction(Long userId, Pageable pageable);
}
