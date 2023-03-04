package me.snowlight.productmanagement.facade;

import me.snowlight.productmanagement.repository.LockRepository;
import me.snowlight.productmanagement.service.StockService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class NamedLockServiceFacade {

    private LockRepository lockRepository;
    private StockService stockService;

    public NamedLockServiceFacade(LockRepository lockRepository, StockService stockService) {
        this.lockRepository = lockRepository;
        this.stockService = stockService;
    }

    @Transactional
    public void decrease(Long id, Long quantity) {
        try {
            this.lockRepository.getLock(id.toString());
            this.stockService.decrease(id, quantity);
        }catch (Exception e) {
            e.printStackTrace();
            throw e;
        } finally {
            this.lockRepository.releaseLock(id.toString());
        }
    }
}
