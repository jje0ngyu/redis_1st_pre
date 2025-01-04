package thread;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Item {
    private String itemName;
    private int stock;
    private final Lock lock = new ReentrantLock();  // 동시성 문제 해결을 위한 Lock 사용

    public Item(String itemName, int stock) {
        this.itemName = itemName;
        this.stock = stock;

    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getStock() {
        return stock;
    }

    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * @brief 구매한 아이템의 재고를 감소한다.
     * @param cnt 구매할 개수
     */
    public void buyItems(int cnt) {
        lock.lock();
        try{
            if (cnt > stock) {
                System.out.println(Thread.currentThread().getName() + " - shortage of stock !");
            } else {
                this.stock -= cnt;
                System.out.println(Thread.currentThread().getName() + " - purchase: " + cnt + " (stock: "+ this.stock + ")");
            }
        } finally {
            lock.unlock();
        }
    }
}
