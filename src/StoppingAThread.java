public class StoppingAThread {
    //volatile keyword is used to prevent the thread from caching the value of the variable
    private volatile Boolean terminated = false;

    public void run() {
        while(!terminated) {
            try{
                Thread.sleep(1000);
                System.out.println("Thread is running");
            } catch(Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void setTerminated(Boolean terminated) {
        this.terminated = terminated;
    }
}

class AppStopping {
    public static void main(String[] args) {
        var worker = new StoppingAThread();
        var thread = new Thread(worker::run);

        try {
            thread.start();
            Thread.sleep(5000);
            worker.setTerminated(true);
        } catch(Exception e) {
            throw new RuntimeException(e);
        }
    }
}
