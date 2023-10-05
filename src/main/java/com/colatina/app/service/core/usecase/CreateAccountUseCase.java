package com.colatina.app.service.core.usecase;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.core.gateway.NegativeCpfGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final AccountGateway accountGateway;
    private final NegativeCpfGateway negativeCpfGateway;

    public void execute(AccountDomain account) {
        if(!account.checkAge()) {
            throw new BusinessException("Incorrect user age");
        }

        if(negativeCpfGateway.isNegativeCpf(account.getDocument())) {
            throw new BusinessException("invalid CPF");
        }

        accountGateway.create(account);
    }


}
