package com.aquariux.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AggregatedPriceDTO {
    private Long id;
    private TradingPairDTO tradingPair;
    private BigDecimal bestBid;
    private PriceSourceDTO bestBidSource;
    private BigDecimal bestAsk;
    private PriceSourceDTO bestAskSource;
    private LocalDateTime aggregatedAt;
}
