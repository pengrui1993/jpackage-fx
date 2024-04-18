package browsertest;

import com.microsoft.playwright.*;
import com.yr.rpa.framework.utils.ProcessUtil;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.BooleanSupplier;
import java.util.function.Consumer;
import java.util.function.Supplier;

import static browsertest.ChromeUtil.*;
import static browsertest.ChromeUtil.checkBrowser;

public class HelloSelector {
    protected Playwright driver;
    protected Browser browser;
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
        Browser b = prepareEnv();
        Page page = b.newPage();
        final String url = "https://www.baidu.com/";
        Response rsp = page.navigate(url);
        page.waitForURL(url);
        ElementHandle element = page.querySelector("id=s_lg_img");
        element.hover();
        page.waitForPopup(element::click);
        b.close();
        driver.close();
    }
    void page(Page page){
        Page page1 = page.waitForPopup(() -> {
        });
        page1.waitForLoadState();

    }
    void handler(Page page){
        ElementHandle handle = page.querySelector("text=Submit");
    }

    public static void main(String[] args) {
        new HelloSelector().action();
    }
}
