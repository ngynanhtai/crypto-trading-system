package com.aquariux.crypto.repository;

import com.aquariux.crypto.entity.PriceFeed;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceFeedRepository extends JpaRepository<PriceFeed, Long> {
}
