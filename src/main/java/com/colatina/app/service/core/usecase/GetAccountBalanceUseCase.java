package com.colatina.app.service.core.usecase;

import com.colatina.app.service.core.gateway.WalletGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAccountBalanceUseCase {

    private final WalletGateway walletGateway;

    public String getWalletGateway(final Integer accountId) {
        return walletGateway.get(accountId);
    }

}
