import java.util.concurrent.locks.ReentrantLock;

//to prevent deadlock we can use the tryLock that will not wait forever if it can't acquire the lock
// or make sure that the locks are acquired in the same order
public class DeadLock {

    ReentrantLock lock1 = new ReentrantLock();
    ReentrantLock lock2 = new ReentrantLock();

    public void worker1() {
        lock1.lock();
        System.out.println("Worker 1 acquired lock1");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock2.lock();
        System.out.println("Worker 1 acquired lock2");
        lock2.unlock();
        lock1.unlock();
    }

    public void worker2() {
        lock2.lock();
        System.out.println("Worker 2 acquired lock2");
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        lock1.lock();
        System.out.println("Worker 2 acquired lock1");
        lock1.unlock();
        lock2.unlock();
    }

    public static void main(String[] args) {
        var deadLock = new DeadLock();

        var thread1 = new Thread(deadLock::worker1);
        var thread2 = new Thread(deadLock::worker2);

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
