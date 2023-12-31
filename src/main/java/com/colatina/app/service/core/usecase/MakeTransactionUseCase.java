package com.colatina.app.service.core.usecase;


import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.exception.BusinessException;
import com.colatina.app.service.core.gateway.AccountGateway;
import com.colatina.app.service.core.gateway.WalletGateway;
import com.colatina.app.service.core.gateway.TransactionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;

@Service
@RequiredArgsConstructor
public class MakeTransactionUseCase {

    private final WalletGateway walletGateway;
    private final TransactionGateway transactionGateway;
    private final AccountGateway accountGateway;

    @Transactional
    public void execute(TransactionDomain transaction) {
        AccountDomain accountOrigin = accountGateway.getAccountById(transaction.getAccountOrigin().getId());
        AccountDomain accountDestination = accountGateway.getAccountById(transaction.getAccountDestination().getId());

        verifyAccountIsActive(accountOrigin, accountDestination);

        BigDecimal balanceOrigin = balanceAccount(accountOrigin);
        BigDecimal result = subtractBalanceWithTransactionValue(balanceOrigin, transaction.getValue());

        checkSufficientBalance(result);

        creditAndDebit(accountOrigin, accountDestination, result, transaction.getValue());
        transactionGateway.checkTransaction(transaction, accountOrigin, accountDestination);
    }

    private void creditAndDebit(
            AccountDomain origin,
            AccountDomain destination,
            BigDecimal result,
            BigDecimal valueTransaction
            ){
        origin.getWallet().setBalance(result);
        destination.getWallet().setBalance(balanceAccount(destination).add(valueTransaction));
    }

    private boolean checkSufficientBalance(BigDecimal balance){
        if(balance.compareTo(BigDecimal.ZERO) < 0) {
            throw new BusinessException("Balance must be positive");
        }
        return true;
    }

    private boolean verifyAccountIsActive(AccountDomain accountOrigin, AccountDomain accountDestination) {
        if(!accountOrigin.isActive() || !accountDestination.isActive()) {
            throw new BusinessException("Account is not active");
        }
        return true;
    }

    private BigDecimal balanceAccount(AccountDomain account){
        return new BigDecimal(walletGateway.get(account.getId()));
    }

    private BigDecimal subtractBalanceWithTransactionValue(BigDecimal balanceAccount, BigDecimal valueTransaction) {
        return balanceAccount.subtract(valueTransaction);
    }

}
