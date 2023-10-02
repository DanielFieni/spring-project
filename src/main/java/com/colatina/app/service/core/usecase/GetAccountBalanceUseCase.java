package com.colatina.app.service.core.usecase;

import com.colatina.app.service.core.gateway.BalanceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class GetAccountBalanceUseCase {

    private final BalanceGateway balanceGateway;

    public String getBalanceGateway(final Integer accountId) {
        return balanceGateway.get(accountId);
    }

}
