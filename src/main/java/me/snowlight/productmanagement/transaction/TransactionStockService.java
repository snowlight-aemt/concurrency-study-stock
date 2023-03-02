package me.snowlight.productmanagement.transaction;

import me.snowlight.productmanagement.service.StockService;

/**
 * 예시 코드
 * <code>@Transactional</code> & 래핑 패턴 등 에서 발생하는 synchronized 문제
 */
public class TransactionStockService {
    private StockService stockService;

    public TransactionStockService(StockService stockService) {
        this.stockService = stockService;
    }

    public void decrease(Long id, Long quantity) {
        startTransaction();
        this.stockService.decrease(id, quantity);

        // endTransaction 이후에 DB에 업데이트를 한다.
        // * synchronized 되어 있지 않다. (동일한게 공유 값에 여러 쓰레드가 동작할 수 있다.)
        endTransaction();
    }

    private void startTransaction() {

    }

    private void endTransaction() {

    }

}
