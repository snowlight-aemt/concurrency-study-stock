package me.snowlight.productmanagement.service;

import me.snowlight.productmanagement.model.Stock;
import me.snowlight.productmanagement.repository.StockRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
public  class StockService {

    private StockRepository stockRepository;

    public StockService(StockRepository stockRepository) {
        this.stockRepository = stockRepository;
    }

    // TransactionStockService 예시 코드
    @Transactional(propagation = Propagation.REQUIRES_NEW)
    public void decrease(Long id, Long quantity) {
        Stock stock = this.stockRepository.findById(id).orElseThrow(IllegalArgumentException::new);
        stock.decrease(quantity);
        this.stockRepository.saveAndFlush(stock) ;
    }
}
