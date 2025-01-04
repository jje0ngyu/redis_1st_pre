import org.junit.jupiter.api.Test;
import thread.Item;
import thread.LatestOrderDatabase;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CountDownLatch;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

public class MyshopMainTest {

    @Test
    void testConcurrentBuyItems() throws InterruptedException {
        // 재고 세팅
        Item apple = new Item("apple", 100);

        Map<String, Object> latestOrderMap = new ConcurrentHashMap<>();

        // 스레드 동기화 처리를 위한 CountDownLatch
        int numThreads = 100;
        CountDownLatch latch = new CountDownLatch(numThreads);

        // 스레드 수행 업무
        Runnable buyTask = () -> {
            try {
                int orderAmount = 8;
                apple.buyItems(orderAmount);
                LatestOrderDatabase order = new LatestOrderDatabase(Thread.currentThread().getName(), "apple", orderAmount);
                latestOrderMap.put(Thread.currentThread().getName() ,order.getLatestOrderInfo());

                // 스레드 종료 대기
                latch.countDown();
            } catch (Exception e) {
                e.printStackTrace();
            }
        };

        // 스레드 생성 및 실행
        Thread[] threads = new Thread[numThreads];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(buyTask, "Thread" + (i+1));
            threads[i].start();
        }

        // 메인 스레드에서 모든 작업이 완료될 때까지 대기
        latch.await();  // 모든 스레드가 작업을 완료할 때까지 대기

        // 최종 재고 확인
        assertEquals(4, apple.getStock(), "Final stock should match expected value after orders");

        // 여기서 최종 주문 내역 확인
        assertNotNull(latestOrderMap, "Order map should not be null");
        assertEquals(numThreads, latestOrderMap.size(), "There should be exactly 100 orders placed.");
    }
}
