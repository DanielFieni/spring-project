package com.colatina.app.service.core.gateway;

import com.colatina.app.service.core.domain.AccountDomain;
import com.colatina.app.service.core.domain.TransactionDomain;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

public interface TransactionGateway {

    List<TransactionDomain> getAccountStatement(Integer accountId, LocalDateTime startDate, LocalDateTime endDate);

    BigDecimal getAccountTransactionWithOrigin(Integer accountId, LocalDateTime startDate, LocalDateTime endDate);

    BigDecimal getAccountTransactionWithDestination(Integer accountId, LocalDateTime startDate, LocalDateTime endDate);

    List<TransactionDomain> getStatusTransaction(Integer id, String status);

    TransactionDomain checkTransaction(TransactionDomain transaction, AccountDomain origin, AccountDomain destination);

    List<TransactionDomain> getAllTransactionWithDate(LocalDateTime startDate, LocalDateTime endDate);

    List<TransactionDomain> getTransactionThatContainAccountId(Integer accountId, LocalDateTime startDate, LocalDateTime endDate);

}
