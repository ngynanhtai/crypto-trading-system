package com.aquariux.crypto.repository;

import com.aquariux.crypto.entity.Wallet;
import jakarta.persistence.LockModeType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query(nativeQuery = true, value = "SELECT w FROM Wallet w " +
            "WHERE w.user.id = :userId AND w.currency.id = :currencyId")
    Wallet lockWalletForUpdate(Long userId, Long currencyId);
}
