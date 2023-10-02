package com.colatina.app.service.dataprovider.adapter;

import com.colatina.app.service.configuration.mapper.AccountMapper;
import com.colatina.app.service.configuration.mapper.TransactionMapper;
import com.colatina.app.service.configuration.mapper.WalletMapper;
import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.TransactionGateway;
import com.colatina.app.service.dataprovider.entity.AccountEntity;
import com.colatina.app.service.dataprovider.entity.TransactionEntity;
import com.colatina.app.service.dataprovider.entity.WalletEntity;
import com.colatina.app.service.dataprovider.repository.AccountRepository;
import com.colatina.app.service.dataprovider.repository.TransactionRepository;
import com.colatina.app.service.dataprovider.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Transactional
public class TransactionAdapter implements TransactionGateway {

    private final TransactionRepository transactionRepository;
    private final TransactionMapper transactionMapper;
    private final WalletMapper walletMapper;
    private final AccountRepository accountRepository;
    private final WalletRepository walletRepository;
    private final AccountMapper accountMapper;

    @Override
    public List<TransactionDomain> getAccountStatement(final Integer accountId, final LocalDateTime startDate, final LocalDateTime endDate) {
        return transactionMapper.toDto(transactionRepository.findAllByAccountOriginIdAndCreatedAtBetween(accountId, startDate, endDate));
    }

    @Override
    @Transactional
    public TransactionDomain checkTransaction(TransactionDomain transaction, AccountDomain origin, AccountDomain destination) {
        WalletEntity walletEntity = walletMapper.toEntity(origin.getWallet());
        walletEntity.setAccount(accountMapper.toEntity(origin));
        walletRepository.save(walletEntity);
        walletEntity = walletMapper.toEntity(destination.getWallet());
        walletEntity.setAccount(accountMapper.toEntity(origin));
        walletRepository.save(walletEntity);
        return transactionMapper.toDto(transactionRepository.save(transactionMapper.toEntity(transaction)));
    }

    private WalletEntity findByIdWallet(Integer wallet_id) {
        return walletRepository.findById(wallet_id).orElseThrow(
                () -> new BusinessException("Wallet not found")
        );
    }

    @Override
    public BigDecimal getAccountTransactionWithOrigin(Integer accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getTotalValueAccountOriginIdAndCreatedAtBetween(accountId, startDate, endDate);
    }

    @Override
    public BigDecimal getAccountTransactionWithDestination(Integer accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionRepository.getTotalValueAccountDestinationIdAndCreatedAtBetween(accountId, startDate, endDate);
    }

    @Override
    public List<TransactionDomain> getStatusTransaction(Integer id, String status) {
        return transactionMapper.toDto(transactionRepository.findAllByAccountOriginIdAndTransactionStatus(id, status));
    }


}
