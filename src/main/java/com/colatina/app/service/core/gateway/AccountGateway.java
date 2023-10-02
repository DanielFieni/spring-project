package com.colatina.app.service.core.gateway;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.domain.enumeration.AccountStatus;

public interface AccountGateway {

    AccountDomain create(AccountDomain account);

    void changeStatus(AccountStatus status, Integer id);

    AccountDomain getAccountById(Integer id);

}
