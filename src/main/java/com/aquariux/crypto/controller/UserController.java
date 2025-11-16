package com.aquariux.crypto.controller;

import com.aquariux.crypto.common.Const;
import com.aquariux.crypto.dto.ResponseDTO;
import com.aquariux.crypto.service.TransactionService;
import com.aquariux.crypto.service.WalletService;
import com.aquariux.crypto.utils.CommonUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = Const.API)
public class UserController {

    @Autowired
    private WalletService walletService;

    @Autowired
    private TransactionService transactionService;

    @RequestMapping(method = RequestMethod.GET, value = "/balance/{userId}")
    public ResponseEntity<?> getBalance(@PathVariable("userId") Long userId) {
        ResponseDTO responseDTO = walletService.getWallets(userId);
        return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
    }

    @RequestMapping(method = RequestMethod.GET, value = "/transaction-history/{userId}")
    public ResponseEntity<?> getTransactionHistory(@PathVariable("userId") Long userId,
                                                   @RequestParam(value = "page", required = false, defaultValue = "0") Integer page,
                                                   @RequestParam(value = "size", required = false, defaultValue = "10") Integer size,
                                                   @RequestParam(value = "sort", required = false, defaultValue = "createdAt;desc") String sort) {
        Pageable pageable = PageRequest.of(page, size, CommonUtils.parseSort(sort));
        ResponseDTO responseDTO = transactionService.getUserTradeTransaction(userId, pageable);
        return new ResponseEntity<>(responseDTO, responseDTO.getStatus());
    }
}
