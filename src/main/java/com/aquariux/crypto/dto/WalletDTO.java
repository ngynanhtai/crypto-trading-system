package com.aquariux.crypto.dto;

import com.aquariux.crypto.entity.Wallet;
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
public class WalletDTO {
    private String currency;
    private BigDecimal available;
    private BigDecimal locked;
    private LocalDateTime updatedAt;

    public static WalletDTO convertToDTO(Wallet wallet) {
        return WalletDTO
                .builder()
                .currency(wallet.getCurrency().getSymbol())
                .available(wallet.getAvailable())
                .locked(wallet.getLocked())
                .updatedAt(wallet.getUpdatedAt())
                .build();
    }

    public static List<WalletDTO> convertToListDTO(List<Wallet> wallets) {
        List<WalletDTO> list = new ArrayList<>();
        for (Wallet wallet : wallets) {
            list.add(convertToDTO(wallet));
        }
        return list;
    }
}
