package me.snowlight.productmanagement.repository;

import me.snowlight.productmanagement.model.Stock;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StockRepository extends JpaRepository<Stock, Long> {
}
