package fx;

public class Net extends Thread{
    void exit(){
        System.out.println("net exit");
    }
    public Net(){
        setDaemon(true);
        Runtime.getRuntime().addShutdownHook(new Thread(this::exit));
    }
    @Override
    public void run() {
        System.out.println("net blocking");
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
