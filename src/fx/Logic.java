package fx;

public class Logic extends Thread{
    void exit(){
        System.out.println("logic exit");
    }
    public Logic(){
        setDaemon(true);
        Runtime.getRuntime().addShutdownHook(new Thread(this::exit));
    }
    @Override
    public void run() {
        System.out.println("logic blocking");
        try {
            Thread.sleep(Integer.MAX_VALUE);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
