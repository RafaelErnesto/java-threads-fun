
class DaemonWorker implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Daemon worker is running");
            Thread.sleep(150000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Daemon worker stopped");
    }
}

class RegularWorker implements Runnable {

    @Override
    public void run() {
        try {
            System.out.println("Regular worker is running");
            Thread.sleep(2000);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
        System.out.println("Regular worker stopped");
    }
}


public class DaemonAndWorkerThreads {

    public static void main(String[] args) {
        var daemonThread = new Thread(new DaemonWorker());
        daemonThread.setDaemon(true);
        var regularThread = new Thread(new RegularWorker());

        daemonThread.start();
        regularThread.start();

    }

}
