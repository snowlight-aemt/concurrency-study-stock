package me.snowlight.productmanagement.facade;

import me.snowlight.productmanagement.service.OptimisticLockService;
import org.springframework.stereotype.Service;

@Service
public class OptimisticLockServiceFacade {
    private OptimisticLockService optimisticLockService;

    public OptimisticLockServiceFacade(OptimisticLockService optimisticLockService) {
        this.optimisticLockService = optimisticLockService;
    }

    public void decrease(Long id, Long quantity) throws InterruptedException {
        while (true) {
            try {
                this.optimisticLockService.decrease(id, quantity);
                break;
            } catch (Exception e) {
                Thread.sleep(30);
            }
        }
    }
}
