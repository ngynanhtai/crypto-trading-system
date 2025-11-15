package com.aquariux.crypto.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "price_source")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class PriceSource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true, length = 50)
    private String code;  // BINANCE, HUOBI

    @Column(name = "base_url", length = 255)
    private String baseUrl;
}

