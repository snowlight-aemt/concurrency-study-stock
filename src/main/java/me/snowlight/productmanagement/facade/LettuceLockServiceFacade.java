package me.snowlight.productmanagement.facade;

import me.snowlight.productmanagement.repository.RedisLockRepository;
import me.snowlight.productmanagement.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class LettuceLockServiceFacade {
    private RedisLockRepository redisLockRepository;
    private StockService stockService;

    public LettuceLockServiceFacade(RedisLockRepository lockRepository, StockService stockService) {
        this.redisLockRepository = lockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public void decrease(Long key, Long quantity) throws InterruptedException {
        while (!this.redisLockRepository.lock(key)) {
            Thread.sleep(100);
        }

        try {
            this.stockService.decrease(key, quantity);
        } finally {
            this.redisLockRepository.unlock(key);
        }
    }
}
