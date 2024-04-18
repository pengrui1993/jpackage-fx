package browsertest;

import com.microsoft.playwright.*;
import com.yr.rpa.framework.utils.ProcessUtil;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import static browsertest.ChromeUtil.chromePid;
import static browsertest.ChromeUtil.chromeAndPortOk;
import static browsertest.ChromeUtil.checkBrowser;

public class BrowserFileUpload {
    protected Playwright driver;
    protected Browser browser;
    void test(){
        Playwright driver = Playwright.create();
        Browser launch = driver.chromium().launch();
        launch.close();
        driver.close();
    }
    private Browser prepareEnv() {
        if(Objects.nonNull(browser))return browser;
        if(Objects.isNull(driver))this.driver = Playwright.create();
        List<String> chromePidList = chromePid();
        boolean underControl = chromeAndPortOk(chromePidList,9999);
        final Supplier<Browser> connBrowser = ()->{
            BrowserType.ConnectOverCDPOptions options = new BrowserType.ConnectOverCDPOptions().setSlowMo(1000);
            return this.driver.chromium().connectOverCDP("http://localhost:9999", options);
        };
        final Runnable runChrome = ()-> ProcessUtil.asyncRunApp("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", "--start-maximized", "--remote-debugging-port=9999");
        if(chromePidList.isEmpty()){
            runChrome.run();
            checkBrowser();
        }else{
            if (!underControl) {
                ProcessUtil.checkCloseApp("chrome.exe");
                runChrome.run();
                checkBrowser();
            }
        }
        browser = connBrowser.get();
        return browser;
    }
    void action(){
        waitFor();
        browser.close();
        driver.close();
    }
    void waitFor(){
        Browser b = prepareEnv();
        Page page = b.newPage();
        Response rsp = page.navigate("https://image.baidu.com/");
        System.out.println(rsp.finished());
        page.locator("//*[@id=\"sttb\"]/img[1]").click();
        FileChooser chooser = page.waitForFileChooser(() -> page.locator("//*[@id=\"stfile\"]").click());
        chooser.setFiles(Paths.get("d:/img.png"));
        b.close();
    }
    void callback(){
        Browser b = prepareEnv();
        Page page = b.newPage();
        Response rsp = page.navigate("https://image.baidu.com/");
        System.out.println(rsp.finished());
        page.locator("//*[@id=\"sttb\"]/img[1]").click();
        Consumer<FileChooser> consumer = chooser -> {
            System.out.println(chooser);
            chooser.setFiles(Paths.get("d:/img.png"));
        };
        page.onFileChooser(consumer);
        page.locator("//*[@id=\"stfile\"]").click();
        page.offFileChooser(consumer);
        b.close();
    }

    public static void main(String[] args) {
        new BrowserFileUpload().action();
    }
}
