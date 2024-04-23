import java.util.concurrent.RecursiveAction;
import java.util.stream.IntStream;

public class ForkJoinExample extends RecursiveAction {
    private final int data;

    public ForkJoinExample(int data) {
        this.data = data;
    }

    @Override
    protected void compute() {

        if(data > 10) {
            var workers = IntStream.range(0, data)
                    .mapToObj(i -> new ForkJoinExample(i/2))
                    .toArray(ForkJoinExample[]::new);
            invokeAll(workers);

        } else {
            System.out.println("Worker: " + Thread.currentThread().getName() + " is working with the value: " + data);
        }
    }
}

class ForkJoinApp {
    public static void main(String[] args) {
        var forkJoinExample = new ForkJoinExample(100);
        forkJoinExample.invoke();
    }
}
