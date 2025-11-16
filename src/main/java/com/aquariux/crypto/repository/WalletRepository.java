package com.aquariux.crypto.repository;

import com.aquariux.crypto.entity.Wallet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WalletRepository extends JpaRepository<Wallet, Long>, WalletRepositoryCustom {
    List<Wallet> findByUserId(Long userId);
}
