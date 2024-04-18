import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ConsumerAndProducer {
    public static final int MAX_SIZE = 5;
    ReentrantLock lock = new ReentrantLock();
    Condition condition = lock.newCondition();
    List<Integer> list = new ArrayList<>();

    public void producer() throws InterruptedException {
        System.out.println("Producer started");
        while (true) {
            lock.lock();

            for (int i = 0; i < MAX_SIZE; i++) {
                list.add(i);
                System.out.println("Produced: " + i);
                Thread.sleep(1000);
            }
            condition.signal();
            condition.await();
            lock.unlock();
        }
    }

    public void consumer() throws InterruptedException {
        System.out.println("Consumer started");
        while (true) {
            lock.lock();
            while (!list.isEmpty()) {
                int value = list.remove(list.size() - 1);
                System.out.println("Consumed: " + value);
                Thread.sleep(1000);
            }
            condition.signal();
            condition.await();
            lock.unlock();
        }
    }
}

class ConsumerAndProducerApp {

    public static void main(String[] args) {
        ConsumerAndProducer consumerAndProducer = new ConsumerAndProducer();

        var thread1 = new Thread(() -> {
            try {
                consumerAndProducer.producer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }

        });
        var thread2 = new Thread(() -> {
            try {
                Thread.sleep(2000);
                consumerAndProducer.consumer();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        });

        thread1.start();
        thread2.start();

        try {
            thread1.join();
            thread2.join();
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
