package demo;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {


    public static void main(String[] args) throws Exception {
        //TIP Press <shortcut actionId="ShowIntentionActions"/> with your caret at the highlighted text
        // to see how IntelliJ IDEA suggests fixing it.
        ip();
//        env();

//        robot();
    }

    private static void robot() throws AWTException, IOException {
        Robot robot = new Robot();
//            Thread.sleep(2000);
//            robot.keyPress(KeyEvent.VK_E);
//            robot.keyRelease(KeyEvent.VK_E);
        Toolkit tk = Toolkit.getDefaultToolkit();
        Dimension size = tk.getScreenSize();
        BufferedImage screenCapture = robot.createScreenCapture(new Rectangle(0, 0, size.width, size.height));
        Path img = Paths.get("D:\\img.png");
        if(!Files.exists(img))
            Files.createFile(img);
        ImageIO.write(screenCapture,"png",img.toFile());
    }

    private static void env() {
        String appdata = System.getenv("APPDATA");
        System.out.println(appdata);
        File[] files = new File(appdata).listFiles();
        for (File file : files) {
            System.out.println(file);
        }
    }
    static ServerSocket server;

    static {
        int port = 12345;
        try {
            server = new ServerSocket(port);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        System.out.println("server bind on:"+port);
    }

    public static void ip()
            throws Exception {

        InetAddress addr = InetAddress.getLocalHost();

        System.out.println("Local HostAddress:"+addr.getHostAddress());
                String hostname = addr.getHostName();
        System.out.println("Local host name: "+hostname);
        Thread.sleep(Integer.MAX_VALUE);
        ;
    }
}