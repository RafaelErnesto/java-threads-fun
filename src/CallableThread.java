import java.util.concurrent.Callable;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class CallableThread implements Callable<Integer> {

    @Override
    public Integer call() throws Exception {
        TimeUnit.SECONDS.sleep(5);
        return (int)(Math.random() * 1000);
    }
}

class CallableThreadApp {
    public static void main(String[] args) {
        try {
            var executor = Executors.newFixedThreadPool(2);
            var future = executor.submit(new CallableThread());
            System.out.println("Result is: " + future.get());
            executor.shutdown();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
