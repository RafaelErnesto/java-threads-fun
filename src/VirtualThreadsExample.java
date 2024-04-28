public class VirtualThreadsExample {
    public static void run() {
        System.out.println("Hello from virtual thread: " + Thread.currentThread().getName());
    }
}


class VirtualThreadsExampleApp {
    public static void main(String[] args) {
        var builder = Thread.ofVirtual().name("thread-",0);

        var t1 = builder.start(VirtualThreadsExample::run);
        var t2 = builder.start(VirtualThreadsExample::run);

        try {
            t1.join();
            t2.join();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
