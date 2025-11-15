package com.aquariux.crypto.repository;

import com.aquariux.crypto.entity.PriceSource;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PriceSourceRepository extends JpaRepository<PriceSource, Long> {
}
