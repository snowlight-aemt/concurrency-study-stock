package me.snowlight.productmanagement.service;

import me.snowlight.productmanagement.model.Stock;
import me.snowlight.productmanagement.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class OptimisticLockService {
    private StockRepository stockRepository;

    public OptimisticLockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        Stock stock = this.stockRepository.findByIdWithOptimisticLock(id);
        stock.decrease(quantity);
        this.stockRepository.saveAndFlush(stock);
    }
}
