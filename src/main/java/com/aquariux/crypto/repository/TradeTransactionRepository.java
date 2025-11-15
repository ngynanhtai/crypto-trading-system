package com.aquariux.crypto.repository;

import com.aquariux.crypto.entity.TradeTransaction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeTransactionRepository extends JpaRepository<TradeTransaction, Long> {
}
