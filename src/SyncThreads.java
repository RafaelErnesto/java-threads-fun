
public class SyncThreads {

    //just to be clear, the synchronization is not on the counter, but on the object SyncThreads, every object in java
    // has an intrinsic lock, and when we use the synchronized keyword, we are locking the object that is being used
    public static Integer counter = 0;

    public synchronized static void increment() {
        counter++;
    }

    public synchronized static void decrement() {
        counter--;
    }

    public static void main(String[] args) {

        var thread1 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                increment();
            }
        });

        var thread2 = new Thread(() -> {
            for (int i = 0; i < 1000; i++) {
                decrement();
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

        System.out.println(counter);
    }
}
