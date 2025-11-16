package com.aquariux.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "trade_transaction")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradeTransaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "trade_order_id", nullable = false)
    private TradeOrder tradeOrder;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch =  FetchType.LAZY)
    @JoinColumn(name = "trading_pair_id", nullable = false)
    private TradingPair tradingPair;

    @Column(length = 4, nullable = false)
    private String side;

    @Column(precision = 30, scale = 10, nullable = false)
    private BigDecimal price;

    @Column(precision = 30, scale = 10, nullable = false)
    private BigDecimal amount;

    @Column(precision = 30, scale = 10, nullable = false)
    private BigDecimal total;

    @Column(name = "created_at")
    private LocalDateTime createdAt;
}
