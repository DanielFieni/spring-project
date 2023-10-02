package com.colatina.app.service.dataprovider.repository;

import com.colatina.app.service.core.domain.enumeration.AccountStatus;
import com.colatina.app.service.core.domain.enumeration.TransactionStatus;
import com.colatina.app.service.dataprovider.entity.TransactionEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

@Repository
public interface TransactionRepository extends JpaRepository<TransactionEntity, Integer> {
    List<TransactionEntity> findAllByAccountOriginIdAndCreatedAtBetween(Integer accountId, LocalDateTime startDate, LocalDateTime endDate);

    // All amounts transferred by an account
    @Query(
            "SELECT SUM(t.value) FROM TransactionEntity t WHERE" +
                    " t.accountOrigin.id = :accountId AND t.createdAt > :startDate AND t.createdAt < :endDate GROUP BY t.accountOrigin.id"
    )
    BigDecimal getTotalValueAccountOriginIdAndCreatedAtBetween(Integer accountId, LocalDateTime startDate, LocalDateTime endDate);

    // All amounts received by and account
    @Query(
            "SELECT SUM(t.value) FROM TransactionEntity t WHERE" +
                    " t.accountDestination.id = :accountId AND t.createdAt > :startDate AND t.createdAt < :endDate GROUP BY t.accountDestination.id"
    )
    BigDecimal getTotalValueAccountDestinationIdAndCreatedAtBetween(Integer accountId, LocalDateTime startDate, LocalDateTime endDate);

    // Retornar todos as transições em estão em determinado estado
    @Query(
            "SELECT t FROM TransactionEntity t WHERE" +
                    " t.accountOrigin.id = :id AND t.status = :status"
    )
    List<TransactionEntity> findAllByAccountOriginIdAndTransactionStatus(Integer id, String status);

}
