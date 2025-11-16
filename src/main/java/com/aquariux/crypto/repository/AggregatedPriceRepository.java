package com.aquariux.crypto.repository;

import com.aquariux.crypto.entity.AggregatedPrice;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AggregatedPriceRepository extends JpaRepository<AggregatedPrice, Long> {

    @Query(nativeQuery = true, value = "SELECT * FROM aggregated_price as ap " +
            "WHERE ap.aggregated_at = " +
            "(SELECT MAX(ap2.aggregated_at) FROM aggregated_price as ap2 " +
            "WHERE ap2.trading_pair_id = ap.trading_pair_id)")
    List<AggregatedPrice> findLatestPricesForAllPairs();
}
