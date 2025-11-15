package com.aquariux.crypto.repository;

import com.aquariux.crypto.entity.TradingPair;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradingPairRepository extends JpaRepository<TradingPair, Long> {
}
