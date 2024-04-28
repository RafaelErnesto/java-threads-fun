import java.util.concurrent.Executors;

public class VirtualThreadsExample {
    public static void run() throws InterruptedException {
        Thread.sleep(1000);
        System.out.println("Hello from virtual thread: " + Thread.currentThread());
    }
}


class VirtualThreadsExampleApp {
    public static void main(String[] args) {
        var executor = Executors.newVirtualThreadPerTaskExecutor();

        for(int i = 0; i < 10000000; i++) {
            executor.submit(() -> {
                try {
                    VirtualThreadsExample.run();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            });
        }
    }
}
