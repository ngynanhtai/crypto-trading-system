package com.aquariux.crypto.service.impl;

import com.aquariux.crypto.dto.ResponseDTO;
import com.aquariux.crypto.dto.WalletDTO;
import com.aquariux.crypto.entity.Wallet;
import com.aquariux.crypto.repository.WalletRepository;
import com.aquariux.crypto.service.WalletService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class WalletServiceImpl implements WalletService {

    @Autowired
    private WalletRepository walletRepo;

    @Override
    public ResponseDTO getWallets(Long userId) {
        ResponseDTO responseDTO = new ResponseDTO();
        try {
            List<Wallet> entities = walletRepo.findByUserId(userId);
            responseDTO.setData(WalletDTO.convertToListDTO(entities));
        } catch (Exception e) {
            log.error("Error occurred while getting wallets from database.", e);
            responseDTO.setMessage("Error occurred while getting wallets from database.");
            responseDTO.setStatus(HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return responseDTO;
    }
}
