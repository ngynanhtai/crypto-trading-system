package com.aquariux.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "trading_pair")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TradingPair {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 20)
    private String symbol;

    @ManyToOne
    @JoinColumn(name = "base_currency_id", nullable = false)
    private Currency baseCurrency;

    @ManyToOne
    @JoinColumn(name = "quote_currency_id", nullable = false)
    private Currency quoteCurrency;
}

