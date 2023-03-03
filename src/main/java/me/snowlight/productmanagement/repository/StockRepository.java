package me.snowlight.productmanagement.repository;

import jakarta.persistence.LockModeType;
import me.snowlight.productmanagement.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Lock;
import org.springframework.data.jpa.repository.Query;

public interface StockRepository extends JpaRepository<Stock, Long> {

    @Lock(LockModeType.PESSIMISTIC_WRITE)
    @Query("select s from Stock s where s.id = :id")
    public Stock findByIdWithPessimisticLock(Long id);

    @Lock(LockModeType.OPTIMISTIC)
    @Query("select s from Stock s where s.id = :id")
    public Stock findByIdWithOptimisticLock(Long id);
}
