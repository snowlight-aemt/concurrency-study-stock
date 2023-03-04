package me.snowlight.productmanagement.service;

import me.snowlight.productmanagement.facade.NamedLockServiceFacade;
import me.snowlight.productmanagement.facade.OptimisticLockServiceFacade;
import me.snowlight.productmanagement.model.Stock;
import me.snowlight.productmanagement.repository.StockRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
public class NamedLockServiceFacadeTest {
    @Autowired
    private StockRepository stockRepository;

    @Autowired
    private NamedLockServiceFacade stockService;

    @AfterEach
    public void after() {
        stockRepository.deleteAll();
    }

    @Test
    public void sut_decrease_repeat_100() throws InterruptedException {
        int threadCount = 100;

        Stock stock = saveStock();

        CountDownLatch latch =new CountDownLatch(threadCount);
        ExecutorService executorService = Executors.newFixedThreadPool(2);
        for (int i = 0;i < threadCount; i++) {
            executorService.submit(() -> {
                try {
                    stockService.decrease(stock.getId(), 1L);
                } finally {
                    latch.countDown();
                }
            });
        }

        latch.await();

        Stock actual = this.stockRepository.findById(stock.getId()).orElseThrow(IllegalArgumentException::new);
        assertEquals(0L, actual.getQuantity());
    }

    private Stock saveStock() {
        return stockRepository.saveAndFlush(new Stock(1L, 100L));
    }
}
