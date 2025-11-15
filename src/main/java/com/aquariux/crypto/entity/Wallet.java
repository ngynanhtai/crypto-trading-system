package com.aquariux.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "wallet",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "currency_id"}))
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Wallet {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "currency_id", nullable = false)
    private Currency currency;

    @Column(nullable = false, precision = 30, scale = 10)
    private BigDecimal available;

    @Column(nullable = false, precision = 30, scale = 10)
    private BigDecimal locked;

    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
}

