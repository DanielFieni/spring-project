package com.colatina.app.service.entrypoint.api;

import com.colatina.app.service.core.domain.TransactionDomain;
import com.colatina.app.service.core.domain.enumeration.TransactionStatus;
import com.colatina.app.service.core.usecase.GetAccountStatementUseCase;
import com.colatina.app.service.core.usecase.MakeTransactionUseCase;
import lombok.RequiredArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@RestController
@RequestMapping("/api/transaction")
@RequiredArgsConstructor
public class TransactionController {

    private final GetAccountStatementUseCase getAccountStatementUseCase;
    private final MakeTransactionUseCase makeTransactionUseCase;

    @GetMapping("/account-statement/{account_id}")
    public ResponseEntity<List<TransactionDomain>> getAccountStatement(@PathVariable("account_id") Integer accountId,
                                                                        @RequestHeader("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
                                                                        @RequestHeader("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
        final List<TransactionDomain> accountStatement = getAccountStatementUseCase.getAccountStatement(accountId, startDate, endDate);
        return new ResponseEntity<>(accountStatement, accountStatement.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/transaction-status/{account_id}")
    public ResponseEntity<List<TransactionDomain>> getStatusTransaction(@PathVariable("account_id") Integer accountId,
                                                                        @RequestParam(name = "status") String status){
        final List<TransactionDomain> transactions = getAccountStatementUseCase.getStatusTransaction(accountId, status);
        return new ResponseEntity<>(transactions, transactions.isEmpty() ? HttpStatus.NO_CONTENT : HttpStatus.OK);
    }

    @GetMapping("/transaction-origin/{account_id}")
    public ResponseEntity<BigDecimal> getTotalBalanceTransactionWithOrigin(@PathVariable("account_id") Integer accountId,
                                                                      @RequestHeader("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
                                                                      @RequestHeader("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
        return new ResponseEntity<>(getAccountStatementUseCase.getTotalBalanceTransactionWithOrigin(accountId, startDate, endDate), HttpStatus.OK);
    }

    @GetMapping("/transaction-destination/{account_id}")
    public ResponseEntity<BigDecimal> getAccountTransactionWithDestination(@PathVariable("account_id") Integer accountId,
                                                                           @RequestHeader("start_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime startDate,
                                                                           @RequestHeader("end_date") @DateTimeFormat(pattern = "yyyy-MM-dd'T'HH:mm") LocalDateTime endDate) {
        return new ResponseEntity<>(getAccountStatementUseCase.getAccountTransactionWithDestination(accountId, startDate, endDate), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<Void> executeTransaction(@RequestBody TransactionDomain transaction) {
        makeTransactionUseCase.execute(transaction);
        return new ResponseEntity<>(HttpStatus.CREATED);
    }

}
