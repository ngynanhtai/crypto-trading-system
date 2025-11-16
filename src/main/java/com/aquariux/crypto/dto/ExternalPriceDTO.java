package com.aquariux.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ExternalPriceDTO {
    private String symbol;
    private BigDecimal bid;
    private BigDecimal ask;
    private String rawJson;
}