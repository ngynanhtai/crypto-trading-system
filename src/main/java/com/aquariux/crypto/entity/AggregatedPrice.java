package com.aquariux.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "aggregated_price")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AggregatedPrice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trading_pair_id", nullable = false)
    private TradingPair tradingPair;

    @Column(name = "best_bid", precision = 30, scale = 10)
    private BigDecimal bestBid;

    @ManyToOne
    @JoinColumn(name = "best_bid_source_id")
    private PriceSource bestBidSource;

    @Column(name = "best_ask", precision = 30, scale = 10)
    private BigDecimal bestAsk;

    @ManyToOne
    @JoinColumn(name = "best_ask_source_id")
    private PriceSource bestAskSource;

    @Column(name = "aggregated_at")
    private LocalDateTime aggregatedAt;
}

