package com.aquariux.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "price_feed")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceFeed {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "trading_pair_id", nullable = false)
    private TradingPair tradingPair;

    @ManyToOne
    @JoinColumn(name = "source_id", nullable = false)
    private PriceSource source;

    @Column(precision = 30, scale = 10)
    private BigDecimal bid;

    @Column(precision = 30, scale = 10)
    private BigDecimal ask;

    @Column(name = "fetched_at")
    private LocalDateTime fetchedAt;

    @Lob
    @Column(name = "raw_json")
    private String rawJson;
}
