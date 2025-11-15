package com.aquariux.crypto.repository;

import com.aquariux.crypto.entity.AggregatedPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

@Repository
public interface AggregatedPriceRepository extends JpaRepository<AggregatedPrice, Long> {

    @Query(nativeQuery = true, value = "SELECT ap FROM AggregatedPrice ap " +
        "WHERE ap.tradingPair.id = :pairId " +
        "ORDER BY ap.aggregatedAt DESC LIMIT 1")
    AggregatedPrice findLatestPrice(Long pairId);
}
