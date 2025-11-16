package com.aquariux.crypto.dto.request;

import com.aquariux.crypto.enums.TransactionTypeEnum;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class TradeRequestDTO {
    private Long userId;
    private Long tradingPairId;
    private TransactionTypeEnum transactionType;
    private BigDecimal amount;
}
