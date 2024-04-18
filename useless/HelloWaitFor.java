package browsertest;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.yr.rpa.framework.utils.ProcessUtil;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static browsertest.ChromeUtil.*;

public class HelloWaitFor {
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
        Page image = page.waitForPopup(() -> page.locator("//*[@id=\"s-top-left\"]/a[6]").click());
        image.waitForLoadState(LoadState.DOMCONTENTLOADED);
        Page closedImage = image.waitForClose(image::close);
        b.close();
        driver.close();
    }
    void download(){
        Page page = browser.newPage();
        page.waitForDownload(()->{});
    }
    public static void main(String[] args) {
        new HelloWaitFor().action();
    }
}
