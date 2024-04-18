package browsertest;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;
import com.yr.rpa.framework.utils.ProcessUtil;

import java.nio.file.Paths;
import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

import static browsertest.ChromeUtil.*;
import static browsertest.ChromeUtil.checkBrowser;

public class HelloPopTabs {
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
        final String url = "https://image.baidu.com/";
        Response rsp = page.navigate(url);
        page.waitForURL(url);
        Locator more = page.locator("//*[@id=\"wrapper_main_box\"]/div[1]/div[2]/div[1]/div/a");
        more.hover();
        ElementHandle handler = page.waitForSelector("#wrapper_main_box > div.wrapper_userinfo_box > div.bidu_top_user_info > div.sites > div > div > div.userinfo-top-more-content.row-1.clearfix > a:nth-child(4)");
        handler.click();
        b.close();
        driver.close();
    }

    public static void main(String[] args) {
        new HelloPopTabs().action();
    }
}
