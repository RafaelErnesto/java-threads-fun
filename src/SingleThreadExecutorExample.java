import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

class Worker implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            try {
                var sleepTime = (int) (Math.random() * 10);
                TimeUnit.SECONDS.sleep(i);
                System.out.println("Worker slept for " + sleepTime + " seconds");
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class SingleThreadExecutorExample {
    public static void main(String[] args) {
        //each task will be executed in sequence, because we are using a single thread executor
        var executor = Executors.newSingleThreadExecutor();
        for (int i = 0; i < 10; i++) {
            executor.execute(new Worker());
        }

        executor.shutdown();

    }
}