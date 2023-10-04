package com.colatina.app.service.dataprovider.adapter;

import com.colatina.app.service.core.gateway.WalletGateway;
import com.colatina.app.service.dataprovider.repository.WalletRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.text.NumberFormat;
import java.util.Locale;

@Service
@RequiredArgsConstructor
public class WalletAdapter implements WalletGateway {

    private final WalletRepository walletRepository;

    @Override
    public String get(Integer accountId) {
        return NumberFormat.getInstance(new Locale("pt", "BR"))
                .format(walletRepository.findBalanceByAccountId(accountId));
    }
}
