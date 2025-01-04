package thread;

import java.util.Map;
import java.util.Random;
import java.util.concurrent.ConcurrentHashMap;

public class MyshopMain {

    // 동시성 문제가 발생할 수 있는 스레드를 재현한다.
    // Lock을 사용하지 않을 경우, 남은 사과 개수가 예상 개수보다 많았다. (ex. 예상: 60개, 결과 68개)
    // Lock을 사용하였을 경우, 남은 사과 개수가 예상 개수와 일치하였다.

    public static void main(String[] args) {
        // 재고 세팅
        Item apple = new Item("apple", 100);

        Map<String, Object> latestorderMap = new ConcurrentHashMap<>();
        Random random = new Random();

        // 스레드 수행 업무
        Runnable buyTask = () -> {
            int orderAmount = random.nextInt(11);
            LatestOrderDatabase order = new LatestOrderDatabase(Thread.currentThread().getName(), "apple", orderAmount);
            latestorderMap.put(Thread.currentThread().getName(), order.getLatestOrderInfo());
            apple.buyItems(orderAmount);

            try{
                Thread.sleep(100);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }

        };

        // 스레드 생성 및 실행
        Thread[] threads = new Thread[5];
        for (int i = 0; i < threads.length; i++) {
            threads[i] = new Thread(buyTask, "Thread" + (i+1));
            threads[i].start();
        }

        // 메인 스레드에서 모든 작업이 완료될 때까지 대기
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }

        // 최종 재고 확인
        System.out.println("FINAL STOCKED APPLE : " + apple.getStock());

        // 사용자별 최종 구매 아이템 확인
//        System.out.println("LAST ORDER LIST FOR USER : " + latestorderMap);
    }

}
