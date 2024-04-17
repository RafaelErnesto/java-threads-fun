public class SyncOnCustomObjects {

    public static Integer counter = 0;

    // In this case the threads are using different locks, so they can run concurrently, but the result may be wrong
    //this approach is useful when different threads tries to update different parts of the same object, so they can run concurrently
    //and the result will be correct
    public static Object lock1 = new Object();
    public static Object lock2 = new Object();

    public static void increment() {
        synchronized (lock1) {
            counter++;
        }
    }

    public static void decrement() {
        synchronized (lock2) {
            counter--;
        }
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
