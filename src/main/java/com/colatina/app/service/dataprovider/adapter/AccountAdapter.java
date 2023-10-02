package com.colatina.app.service.dataprovider.adapter;

import com.colatina.app.service.configuration.mapper.AccountMapper;
import com.colatina.app.service.configuration.mapper.TransactionMapper;
import com.colatina.app.service.configuration.mapper.WalletMapper;
import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.domain.WalletDomain;
import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.dataprovider.entity.TransactionEntity;
import com.colatina.app.service.dataprovider.entity.WalletEntity;
import com.colatina.app.service.dataprovider.repository.AccountRepository;
import com.colatina.app.service.dataprovider.entity.AccountEntity;
import com.colatina.app.service.dataprovider.repository.TransactionRepository;
import com.colatina.app.service.dataprovider.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountAdapter implements AccountGateway {

    private final AccountMapper mapper;
    private final TransactionMapper transactionMapper;
    private final AccountRepository repository;
    private final WalletRepository walletRepository;
    private final TransactionRepository transactionRepository;
    private final WalletMapper walletMapper;

    @Override
    @Transactional
    public AccountDomain create(AccountDomain account) {
        AccountEntity entity = mapper.toEntity(account);
        entity = repository.save(entity);
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setAccount(entity);
        walletEntity.setBalance(BigDecimal.ZERO);
        walletRepository.save(walletEntity);
        return mapper.toDto(entity);
    }

    @Override
    public void changeStatus(AccountStatus status, Integer id) {
        AccountEntity entity = findByIdAccount(id);
        entity.setStatus(String.valueOf(status));
        repository.save(entity);
    }

    @Override
    @Transactional
    public TransactionDomain checkTransaction(TransactionDomain transaction) {
        TransactionEntity entity = transactionMapper.toEntity(transaction);
        WalletEntity walletEntity = walletMapper.toEntity(transaction.getAccountOrigin().getWallet());
        walletEntity.setAccount(entity.getAccountOrigin());
        walletRepository.save(walletEntity);
        walletEntity = walletMapper.toEntity(transaction.getAccountDestination().getWallet());
        walletEntity.setAccount(entity.getAccountDestination());
        walletRepository.save(walletEntity);
        return transactionMapper.toDto(transactionRepository.save(entity));
    }

    private AccountEntity findByIdAccount(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("User not found"));
    }

}
