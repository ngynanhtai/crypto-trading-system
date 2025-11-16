package com.aquariux.crypto.service.impl;

import com.aquariux.crypto.dto.*;
import com.aquariux.crypto.dto.request.TradeRequestDTO;
import com.aquariux.crypto.entity.*;
import com.aquariux.crypto.enums.OrderStatusEnum;
import com.aquariux.crypto.enums.TransactionTypeEnum;
import com.aquariux.crypto.repository.*;
import com.aquariux.crypto.service.TradeService;
import com.aquariux.crypto.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Arrays;

@Service
@Slf4j
public class TradeServiceImpl implements TradeService {

    @Autowired
    private TradingPairRepository tradingPairRepo;

    @Autowired
    private AggregatedPriceRepository aggregatedPriceRepo;

    @Autowired
    private WalletRepository walletRepo;

    @Autowired
    private TradeOrderRepository tradeOrderRepo;

    @Autowired
    private TradeTransactionRepository tradeTxRepo;

    @Autowired
    private WalletService walletService;

    @Transactional
    @Override
    public ResponseDTO executeTrade(TradeRequestDTO requestDTO) {
        ResponseDTO responseDTO = new ResponseDTO();
        log.info("START executeTrade for userId: {}, transactionType: {}, amount: {}",
                requestDTO.getUserId(), requestDTO.getTransactionType(), requestDTO.getAmount());
        try {
            TradingPair tradingPair = tradingPairRepo.findById(requestDTO.getTradingPairId())
                    .orElse(null);
            if (tradingPair == null) {
                responseDTO.setMessage("Trading pair not found");
                responseDTO.setStatus(HttpStatus.BAD_REQUEST);
                return responseDTO;
            }

            AggregatedPrice price = aggregatedPriceRepo.findFirstByTradingPairIdOrderByAggregatedAtDesc(
                    requestDTO.getTradingPairId()).orElse(null);
            if (price == null) {
                responseDTO.setMessage("Aggregated price not found");
                responseDTO.setStatus(HttpStatus.BAD_REQUEST);
                return responseDTO;
            }

            BigDecimal executedPrice = TransactionTypeEnum.BUY.equals(requestDTO.getTransactionType())
                    ? price.getBestAsk()
                    : price.getBestBid();
            BigDecimal totalCost = executedPrice.multiply(requestDTO.getAmount());

            // Lock quote (USDT) and base (BTC/ETH)
            Wallet baseWallet = walletRepo.lockWalletForUpdate(requestDTO.getUserId(),
                    tradingPair.getBaseCurrency().getId());
            Wallet quoteWallet = walletRepo.lockWalletForUpdate(requestDTO.getUserId(),
                    tradingPair.getQuoteCurrency().getId());

            // Build init data for TradeOrder
            TradeOrder order = TradeOrder.builder()
                    .user(User.builder().id(requestDTO.getUserId()).build())
                    .tradingPair(tradingPair)
                    .side(requestDTO.getTransactionType().name())
                    .amount(requestDTO.getAmount())
                    .price(executedPrice)
                    .total(totalCost)
                    .status(OrderStatusEnum.FILLED.name())
                    .createdAt(LocalDateTime.now())
                    .executedAt(LocalDateTime.now())
                    .build();
            // --------------------------------------------
            // BUY FLOW
            // --------------------------------------------
            if (TransactionTypeEnum.BUY.equals(requestDTO.getTransactionType())) {
                if (quoteWallet.getAvailable().compareTo(totalCost) < 0) {
                    order.setStatus(OrderStatusEnum.REJECTED.name());

                    // Save trade order
                    tradeOrderRepo.save(order);

                    responseDTO.setMessage("Insufficient quote currency (USDT)");
                    responseDTO.setStatus(HttpStatus.BAD_REQUEST);
                    return responseDTO;
                }
                quoteWallet.setAvailable(quoteWallet.getAvailable().subtract(totalCost));
                baseWallet.setAvailable(baseWallet.getAvailable().add(requestDTO.getAmount()));
            }
            // --------------------------------------------
            // SELL FLOW
            // --------------------------------------------
            else {
                if (baseWallet.getAvailable().compareTo(requestDTO.getAmount()) < 0) {
                    order.setStatus(OrderStatusEnum.REJECTED.name());

                    // Save trade order
                    tradeOrderRepo.save(order);

                    responseDTO.setMessage("Insufficient base currency");
                    responseDTO.setStatus(HttpStatus.BAD_REQUEST);
                    return responseDTO;
                }
                baseWallet.setAvailable(baseWallet.getAvailable().subtract(requestDTO.getAmount()));
                quoteWallet.setAvailable(quoteWallet.getAvailable().add(totalCost));
            }

            // Save updated wallets
            walletRepo.save(baseWallet);
            walletRepo.save(quoteWallet);

            // Save trade order
            tradeOrderRepo.save(order);

            // Save transaction ledger entry
            TradeTransaction tx = TradeTransaction.builder()
                    .tradeOrder(order)
                    .user(User.builder().id(requestDTO.getUserId()).build())
                    .tradingPair(tradingPair)
                    .side(requestDTO.getTransactionType().name())
                    .price(executedPrice)
                    .amount(requestDTO.getAmount())
                    .total(totalCost)
                    .createdAt(LocalDateTime.now())
                    .build();

            tradeTxRepo.save(tx);

            TradeOrderDTO data = TradeOrderDTO.convertToDTO(order);
            data.setWallets(WalletDTO.convertToListDTO(Arrays.asList(baseWallet, quoteWallet)));
            data.setUser(UserDTO.builder()
                            .id(requestDTO.getUserId())
                            .build());
            data.setTradingPair(TradingPairDTO.builder()
                            .id(tradingPair.getId())
                            .symbol(tradingPair.getSymbol())
                            .build());
            responseDTO.setData(data);
        } catch (Exception e) {
            log.error("Error occurred while executing trade", e);
            responseDTO.setMessage("Error occurred while executing trade");
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        log.info("END executeTrade for userId: {}, transactionType: {}, amount: {}",
                requestDTO.getUserId(), requestDTO.getTransactionType(), requestDTO.getAmount());
        return responseDTO;
    }
}
