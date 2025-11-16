package com.aquariux.crypto.service;

import com.aquariux.crypto.dto.ResponseDTO;

public interface WalletService {

    /**
     * Retrieve all wallets belonging to a specific user.
     *
     * @param userId the ID of the user whose wallets are requested
     * @return ResponseDTO containing the list of wallets and related metadata
     */
    ResponseDTO getWallets(Long userId);
}
