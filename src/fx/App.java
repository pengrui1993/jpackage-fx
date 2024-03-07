package fx;

import javafx.application.Application;
import javafx.stage.Stage;

public class App extends Application {
    final UI ui = new UI();
    final Logic logic = new Logic();
    final Net net = new Net();
    boolean started = false;
    @Override
    public void start(Stage stage) throws Exception {
        if(started)return;
        ui.start(stage);
        logic.start();
        net.start();
        started = true;
    }
    public static void main(String[] args) {
//        com.sun.glass.ui.Application.GetApplication();
//        stage.addEventHandler(MouseEvent.ANY, mouseAny);
//        stage.addEventHandler(WindowEvent.ANY, windowAny);
//        Platform.runLater(()->{});
//        PlatformImpl.runAndWait(()->{});
        launch(args);
    }
}
