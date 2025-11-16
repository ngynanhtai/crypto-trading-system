package com.aquariux.crypto.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeOrderDTO {
    private Long id;
    private List<WalletDTO> wallets;
    private UserDTO user;
    private TradingPairDTO tradingPair;
    private String side; // BUY or SELL
    private BigDecimal amount;
    private BigDecimal price;
    private BigDecimal total;
    private String status;
    private LocalDateTime createdAt;
    private LocalDateTime executedAt;
}
