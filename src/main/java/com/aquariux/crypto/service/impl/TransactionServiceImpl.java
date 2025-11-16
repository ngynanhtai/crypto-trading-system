package com.aquariux.crypto.service.impl;

import com.aquariux.crypto.dto.ResponseDTO;
import com.aquariux.crypto.dto.TradeTransactionDTO;
import com.aquariux.crypto.entity.TradeTransaction;
import com.aquariux.crypto.repository.TradeTransactionRepository;
import com.aquariux.crypto.service.TransactionService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class TransactionServiceImpl implements TransactionService {

    @Autowired
    private TradeTransactionRepository  tradeTransactionRepo;

    @Override
    public ResponseDTO getUserTradeTransaction(Long userId, Pageable pageable) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<TradeTransaction> entities = tradeTransactionRepo.findByUserId(userId, pageable);
            responseDTO.setData(TradeTransactionDTO.convertToListDTO(entities));
        } catch (Exception e) {
            log.error("Error occurred when getting user trade transactions from database.", e);
            responseDTO.setMessage("Error occurred when getting user trade transactions from database.");
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }
}
