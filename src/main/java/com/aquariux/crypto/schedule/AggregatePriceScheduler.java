package com.aquariux.crypto.schedule;

import com.aquariux.crypto.service.PriceAggregationService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@EnableScheduling
@Slf4j
public class AggregatePriceScheduler {

    @Autowired
    private PriceAggregationService priceAggregationService;

    @Scheduled(fixedRate = 10000)
    public void aggregatePrices() {
        log.info("Running 10s price aggregation");
        priceAggregationService.aggregatePrices();
    }
}
