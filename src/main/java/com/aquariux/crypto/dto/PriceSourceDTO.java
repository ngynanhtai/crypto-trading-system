package com.aquariux.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PriceSourceDTO {
    private Long id;
    private String code;  // BINANCE, HUOBI
    private String baseUrl;
}
