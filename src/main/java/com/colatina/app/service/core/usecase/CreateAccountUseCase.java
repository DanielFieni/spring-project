package com.colatina.app.service.core.usecase;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.WalletDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.dataprovider.client.negativecpfvalidator.NegativeCpfValidatorClient;
import liquibase.pro.packaged.W;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class CreateAccountUseCase {

    private final AccountGateway accountGateway;
    private final NegativeCpfValidatorClient cpfValidator;

    public void execute(AccountDomain account) {
        if(!account.checkAge()) {
            throw new BusinessException("User age incorrect");
        }

//        if(cpfValidator.isNegativeCpf(account.getDocument())) {
//            throw new BusinessException("CPF invalid");
//        }

        accountGateway.create(account);
    }


}
