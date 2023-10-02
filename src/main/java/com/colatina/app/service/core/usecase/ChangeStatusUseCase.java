package com.colatina.app.service.core.usecase;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.core.gateway.BalanceGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ChangeStatusUseCase {

    private final AccountGateway accountGateway;

    public void execute(AccountStatus status, Integer id) {
        accountGateway.changeStatus(status, id);
    }

}
