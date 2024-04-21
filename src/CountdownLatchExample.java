import java.util.concurrent.CountDownLatch;

public class CountdownLatchExample implements Runnable {
    CountDownLatch readyCountDownLatch;
    CountDownLatch finishedCountDownLatch;

    CountdownLatchExample(CountDownLatch readyCountDownLatch, CountDownLatch finishedCountDownLatch) {
        this.readyCountDownLatch = readyCountDownLatch;
        this.finishedCountDownLatch = finishedCountDownLatch;
    }


    @Override
    public void run() {
        try {
            readyCountDownLatch.countDown();
            readyCountDownLatch.await();
            System.out.println("Thread: " + Thread.currentThread().getName() + " is running");
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            finishedCountDownLatch.countDown();
        }
    }
}

class CountdownLatchApp {
    public static void main(String[] args) {
        var numberOfThreads = 10;

        var readyCountDownLatch = new CountDownLatch(numberOfThreads);
        var finishedCountDownLatch = new CountDownLatch(numberOfThreads);

        for (int i = 0; i < numberOfThreads; i++) {
            var thread = new Thread(new CountdownLatchExample(readyCountDownLatch, finishedCountDownLatch));
            thread.start();
        }

        try {
            finishedCountDownLatch.await();
            System.out.println("All threads have finished");
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
