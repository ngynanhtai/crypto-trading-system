package com.aquariux.crypto.dto;

import com.aquariux.crypto.entity.TradeTransaction;
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
public class TradeTransactionDTO {
    private UserDTO user;
    private TradeOrderDTO tradeOrder;
    private TradingPairDTO tradingPair;
    private String side;
    private BigDecimal price;
    private BigDecimal amount;
    private BigDecimal total;
    private LocalDateTime createdAt;

    public static TradeTransactionDTO convertToDTO(TradeTransaction tradeTransaction) {
        return TradeTransactionDTO
                .builder()
                .user(UserDTO.builder().id(tradeTransaction.getUser().getId()).build())
                .tradeOrder(TradeOrderDTO
                        .builder()
                        .id(tradeTransaction.getTradeOrder().getId())
                        .status(tradeTransaction.getTradeOrder().getStatus())
                        .createdAt(tradeTransaction.getTradeOrder().getCreatedAt())
                        .executedAt(tradeTransaction.getTradeOrder().getExecutedAt())
                        .build())
                .tradingPair(TradingPairDTO
                        .builder()
                        .id(tradeTransaction.getTradingPair().getId())
                        .symbol(tradeTransaction.getTradingPair().getSymbol())
                        .build())
                .side(tradeTransaction.getSide())
                .price(tradeTransaction.getPrice())
                .amount(tradeTransaction.getAmount())
                .total(tradeTransaction.getTotal())
                .createdAt(tradeTransaction.getCreatedAt())
                .build();
    }

    public static List<TradeTransactionDTO> convertToListDTO(List<TradeTransaction> tradeTransactions) {
        List<TradeTransactionDTO> data = new ArrayList<>();
        for (TradeTransaction tradeTransaction : tradeTransactions) {
            data.add(convertToDTO(tradeTransaction));
        }
        return data;
    }
}
