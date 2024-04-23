import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkAndJoinFibonacci extends RecursiveTask<Integer> {
    private final int n;

    ForkAndJoinFibonacci(int n) {
        this.n = n;
    }


    @Override
    protected Integer compute() {
        if (n <= 1) {
            return n;
        } else {
            var n1 = new ForkAndJoinFibonacci(n - 1);
            var n2 = new ForkAndJoinFibonacci(n - 2);

            n1.fork();
            n2.fork();

            try {
                return n1.join() + n2.join();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}

class ForkAndJoinFibonacciApp {
    public static void main(String[] args) {
        var forkAndJoinFibonacci = new ForkAndJoinFibonacci(4);
        var pool = new ForkJoinPool();
        System.out.println(forkAndJoinFibonacci.invoke());
    }
}
