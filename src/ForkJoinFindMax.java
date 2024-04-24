import java.util.Arrays;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveTask;

public class ForkJoinFindMax extends RecursiveTask<Integer> {
    private final int[] arr;

    ForkJoinFindMax(int[] arr) {
        this.arr = arr;
    }

    @Override
    protected Integer compute() {
        if (arr.length == 1) {
            return arr[0];
        }

        var middle = arr.length / 2;

        var f1 = new ForkJoinFindMax(Arrays.copyOfRange(arr, 0, middle));
        var f2 = new ForkJoinFindMax(Arrays.copyOfRange(arr, middle, arr.length));

        try {
            var result1 = f1.fork().join();
            var result2 = f2.fork().join();

            return Math.max(result1, result2);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error computing the result.");
        }
    }
}

class ForkJoinFindMaxApp {
    public static void main(String[] args) {
        ForkJoinPool pool = new ForkJoinPool();
        var arr = new int[]{2, 3, 0, -2, 4, 100, -345, 9, 2, 0, 5, 6, 7, 8, 9, 10, 11, 12, 13, 14, 15, 16, 17, 18, 19, 20, 4567};
        var task = new ForkJoinFindMax(arr);
        var result = task.compute();
        System.out.println("Max value: " + result);
    }
}
