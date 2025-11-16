package com.aquariux.crypto.service;

import com.aquariux.crypto.dto.ResponseDTO;
import org.springframework.data.domain.Pageable;

public interface TransactionService {

    /**
     * Retrieve the trade transactions of a specific user with pagination support.
     *
     * @param userId   the ID of the user whose trade history is requested
     * @param pageable pagination and sorting information
     * @return ResponseDTO containing paginated trade transaction data
     */
    ResponseDTO getUserTradeTransaction(Long userId, Pageable pageable);
}
