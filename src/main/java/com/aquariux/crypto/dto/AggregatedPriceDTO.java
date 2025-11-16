package com.aquariux.crypto.dto;

import com.aquariux.crypto.entity.AggregatedPrice;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

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
    
    public static AggregatedPriceDTO convertToDTO(AggregatedPrice aggregatedPrice) {
        return AggregatedPriceDTO
                .builder()
                .id(aggregatedPrice.getId())
                .tradingPair(TradingPairDTO
                        .builder()
                        .id(aggregatedPrice.getTradingPair().getId())
                        .symbol(aggregatedPrice.getTradingPair().getSymbol())
                        .build())
                .bestBid(aggregatedPrice.getBestBid())
                .bestBidSource(PriceSourceDTO
                        .builder()
                        .id(aggregatedPrice.getBestBidSource().getId())
                        .code(aggregatedPrice.getBestBidSource().getCode())
                        .build())
                .bestAsk(aggregatedPrice.getBestAsk())
                .bestAskSource(PriceSourceDTO
                        .builder()
                        .id(aggregatedPrice.getBestAskSource().getId())
                        .code(aggregatedPrice.getBestAskSource().getCode())
                        .build())
                .aggregatedAt(aggregatedPrice.getAggregatedAt())
                .build();
    }

    public static List<AggregatedPriceDTO> convertToListDTO(List<AggregatedPrice> aggregatedPrices) {
        List<AggregatedPriceDTO> data  = new ArrayList<>();
        for (AggregatedPrice aggregatedPrice : aggregatedPrices) {
            data.add(convertToDTO(aggregatedPrice));
        }
        return data;
    }
}
