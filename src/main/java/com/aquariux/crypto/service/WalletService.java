package com.aquariux.crypto.service;

import com.aquariux.crypto.dto.ResponseDTO;

public interface WalletService {
    ResponseDTO getWallets(Long userId);
}
