package thread;

public class MyshopMain {

    // 동시성 문제가 발생할 수 있는 스레드를 재현한다.
    // Lock을 사용하지 않을 경우, 남은 사과 개수가 예상 개수보다 많았다. (ex. 예상: 60개, 결과 68개)
    // Lock을 사용하였을 경우, 남은 사과 개수가 예상 개수와 일치하였다.

    public static void main(String[] args) {

        Item apple = new Item("apple", 100);

        Runnable buyTask = () -> {
            for (int i = 0; i < 5; i++) {
                apple.buyItems(20);

                try{
                    Thread.sleep(100);
                } catch (InterruptedException e) {
                    Thread.currentThread().interrupt();
                }

            }
        };
        // 스레드 생성 및 실행
        Thread thread1 = new Thread(buyTask, "Thread-1");
        Thread thread2 = new Thread(buyTask, "Thread-2");

        thread1.start();
        thread2.start();

        // 메인 스레드에서 모든 작업이 완료될 때까지 대기
        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
        }

        // 최종 재고 확인
        System.out.println("FINAL STOCKED APPLE : " + apple.getStock());


    }

}
