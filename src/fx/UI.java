package fx;

import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.DragEvent;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

import java.util.Objects;

public class UI {
    private Stage mainStage;
    private final Group root = new Group();
    private final Scene scene = new Scene(root);
    private final Canvas cvs = new Canvas(400,200);
    private Thread mainThread;
    Event last;
    private final Scheduler scheduler = new Scheduler(()-> System.out.println(last));
    private final EventHandler<Event> eventAny = e->{
        last = e;
        if(e instanceof DragEvent){
            DragEvent de = (DragEvent)e;
            System.out.println(de.getDragboard().getFiles());
        }
        scheduler.run();
    };
    public void start(Stage stage) throws Exception {
        mainStage = stage;
        mainThread = Thread.currentThread();
        System.out.println(mainThread);
        stage.addEventHandler(Event.ANY, eventAny);
        root.getChildren().add(cvs);
        stage.setScene(scene);
        GraphicsContext g = cvs.getGraphicsContext2D();
        g.setFill(Color.PINK);
        g.fillRect(0,0,2,2);
        g.setFill(Color.RED);
        g.setStroke(Color.BLACK);
        g.setLineWidth(2);
        boolean useSystem = true;
        String fontStr = useSystem?"System":"Times New Roman";
        g.setFont(Font.font(fontStr, FontWeight.BOLD,48));
        g.fillText("Hello world!",60,50);
        g.strokeText("Hello world!",60,50);
        g.strokeText("你好 world!",60,90);
        stage.show();
    }
    static class Scheduler implements Runnable{
        final Runnable runnable;
        long last;
        final long duration;
        public Scheduler(Runnable runnable) {
            this(runnable,1000);
        }
        public Scheduler(Runnable runnable, long duration) {
            this.runnable = Objects.requireNonNull(runnable);
            this.duration = Math.max(duration,0);
        }
        void trigger(){
            long n = now();
            if(n-last>=duration){
                runnable.run();
                last = n;
            }
        }
        long now(){return System.currentTimeMillis();}
        @Override
        public void run() {
            trigger();
        }
    }
}
