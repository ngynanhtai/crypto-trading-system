package com.aquariux.crypto.repository.custom;

import com.aquariux.crypto.entity.Wallet;
import com.aquariux.crypto.repository.WalletRepositoryCustom;
import jakarta.persistence.EntityManager;
import jakarta.persistence.LockModeType;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Component;

@Component
public class WalletRepositoryCustomImpl implements WalletRepositoryCustom {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Wallet lockWalletForUpdate(Long userId, Long currencyId) {
        return entityManager.createQuery(
        "SELECT w FROM Wallet w " +
        "WHERE w.user.id = :userId AND w.currency.id = :currencyId", Wallet.class)
                .setParameter("userId", userId)
                .setParameter("currencyId", currencyId)
                .setLockMode(LockModeType.PESSIMISTIC_WRITE)
                .getSingleResult();
    }
}
