package me.snowlight.productmanagement.facade;

import me.snowlight.productmanagement.service.StockService;
import org.redisson.api.RLock;
import org.redisson.api.RedissonClient;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
public class RedissonLockServiceFacade {
    private RedissonClient redissonClient;
    private StockService stockService;

    public RedissonLockServiceFacade(RedissonClient redissonClient, StockService stockService) {
        this.redissonClient = redissonClient;
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        RLock lock = redissonClient.getLock(id.toString());
        boolean available = lock.tryLock(5, 1, TimeUnit.SECONDS);

        if (!available) {
            System.out.println("lock 를 얻지 못함");
        }

        try {
            this.stockService.decrease(id, quantity);
        } finally {
            lock.unlock();
        }
    }
}
