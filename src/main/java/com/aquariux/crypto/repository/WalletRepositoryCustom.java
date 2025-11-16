package com.aquariux.crypto.repository;

import com.aquariux.crypto.entity.Wallet;

public interface WalletRepositoryCustom {
    Wallet lockWalletForUpdate(Long userId, Long currencyId);
}
