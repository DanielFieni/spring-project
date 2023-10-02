package com.colatina.app.service.core.usecase;


import com.colatina.app.service.core.domain.AccountInfoDomain;
import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.core.gateway.BalanceGateway;
import com.colatina.app.service.dataprovider.adapter.BalanceAdapter;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MakeTransactionUseCase {

    private final BalanceGateway balanceGateway;
    private final AccountGateway accountGateway;

    @Transactional
    public void execute(TransactionDomain transaction) {
        AccountInfoDomain accountOrigin = transaction.getAccountOrigin();
        AccountInfoDomain accountDestination = transaction.getAccountDestination();
        BigDecimal balanceAcountOrigin = balanceAccount(accountOrigin);
        BigDecimal result = subtractBalanceWithTransaction(balanceAcountOrigin, transaction.getValue());
        verifyAccountIsActive(accountOrigin, accountDestination);
        verifyBalanceSufficient(result);
        accountOrigin.getWallet().setBalance(result);
        accountDestination.getWallet().setBalance(balanceAccount(accountDestination).add(transaction.getValue()));
        accountGateway.checkTransaction(transaction);
    }

    private boolean verifyBalanceSufficient(BigDecimal balance){
        if(balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Balance is insufficient");
        }
        return true;
    }

    private boolean verifyAccountIsActive(AccountInfoDomain accountOrigin, AccountInfoDomain accountDestination) {
        if(!accountOrigin.isActive() || !accountDestination.isActive()) {
            throw new BusinessException("Account not active");
        }
        return true;
    }

    private BigDecimal balanceAccount(AccountInfoDomain account){
        return new BigDecimal(balanceGateway.get(account.getId()));
    }

    private BigDecimal subtractBalanceWithTransaction(BigDecimal balanceAccount, BigDecimal valueTransaction) {
        return balanceAccount.subtract(valueTransaction);
    }

}
