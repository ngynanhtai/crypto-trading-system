package com.aquariux.crypto.repository;

import com.aquariux.crypto.entity.TradeOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TradeOrderRepository extends JpaRepository<TradeOrder, Long> {
}
