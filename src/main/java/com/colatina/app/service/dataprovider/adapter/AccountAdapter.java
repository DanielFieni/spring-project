package com.colatina.app.service.dataprovider.adapter;

import com.colatina.app.service.configuration.mapper.AccountMapper;
import com.colatina.app.service.configuration.mapper.WalletMapper;
import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.dataprovider.entity.WalletEntity;
import com.colatina.app.service.dataprovider.repository.AccountRepository;
import com.colatina.app.service.dataprovider.entity.AccountEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class AccountAdapter implements AccountGateway {

    private final AccountMapper mapper;
    private final AccountRepository repository;
    private final WalletMapper walletMapper;

    @Override
    @Transactional
    public AccountDomain create(AccountDomain account) {
        AccountEntity entity = mapper.toEntity(account);
        entity.setWallet(createWallet(entity));
        repository.save(entity);
        return mapper.toDto(entity);
    }

    private WalletEntity createWallet(AccountEntity account) {
        WalletEntity walletEntity = new WalletEntity();
        walletEntity.setAccount(account);
        walletEntity.setBalance(BigDecimal.ZERO);
        return walletEntity;
    }

    @Override
    public void changeStatus(AccountStatus status, Integer id) {
        AccountEntity entity = findByIdAccount(id);
        entity.setStatus(String.valueOf(status));
        repository.save(entity);
    }

    @Override
    public AccountDomain getAccountById(Integer account_id) {
        AccountEntity entity = findByIdAccount(account_id);
        AccountDomain domain = mapper.toDto(entity);
        domain.setWallet(walletMapper.toDto(entity.getWallet()));
        return domain;
    }


    private AccountEntity findByIdAccount(Integer id) {
        return repository.findById(id).orElseThrow(() -> new BusinessException("User not found"));
    }

}
