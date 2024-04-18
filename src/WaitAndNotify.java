public class WaitAndNotify {

    public void worker1() {
        synchronized (this) {
            System.out.println("Worker 1 started");
            try {
                //release the intrinsic lock and wait for another thread to call notify
                wait();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            System.out.println("Worker 1 finished");
        }
    }

    public void worker2() {
        synchronized (this) {
            System.out.println("Worker 2 started");
            //release the lock, so all the threads that are waiting for the lock can run
            notify();
            System.out.println("Worker 2 finished");
        }
    }

}

class App {
    public static void main(String[] args) {
        var awaitAndNotify = new WaitAndNotify();

        var thread1 = new Thread(awaitAndNotify::worker1);
        var thread2 = new Thread(awaitAndNotify::worker2);

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
