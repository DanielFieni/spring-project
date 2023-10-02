package com.colatina.app.service.core.usecase;


import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.domain.enumeration.TransactionStatus;
import com.colatina.app.service.core.gateway.TransactionGateway;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GetAccountStatementUseCase {

    private final TransactionGateway transactionGateway;

    public List<TransactionDomain> getAccountStatement(Integer accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionGateway.getAccountStatement(accountId, startDate, endDate);
    }

    public BigDecimal getAccountTransactionWithOrigin(Integer accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionGateway.getAccountTransactionWithOrigin(accountId, startDate, endDate);
    }

    public BigDecimal getAccountTransactionWithDestination(Integer accountId, LocalDateTime startDate, LocalDateTime endDate) {
        return transactionGateway.getAccountTransactionWithDestination(accountId, startDate, endDate);
    }

    public List<TransactionDomain> getStatusTransaction(Integer id, String status) {
        return transactionGateway.getStatusTransaction(id, status);
    }

}
