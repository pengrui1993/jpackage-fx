package browsertest;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.microsoft.playwright.*;
import com.yr.rpa.framework.utils.ProcessUtil;
import com.yr.rpa.util.WindowsCmdUtil;
import lombok.extern.slf4j.Slf4j;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * PurchaseTask
 *
 * @author dev
 * @date 2024/2/18
 */
@Slf4j
public class BrowserRobot {
    protected Playwright driver;
    protected Browser browser;
    public void run() {
        browser = prepareEnv();
        List<BrowserContext> contexts = browser.contexts();
        BrowserContext ctx = contexts.getFirst();
        Page page = ctx.pages().getFirst();
        String loginUrl = "http://10.142.218.242:11910/mss-purchase/user/login";
//        final String homeUrl = "http://www.hubeitel.com/ieappCenter.html";
        loginUrl = "https://www.baidu.com/";
        // 输入用户名

        System.out.println(page.url());
        String open="https://developer.mozilla.org/en-US/docs/Web/API/Window/open";
        if(!Objects.equals(page.url(),open)){
            Response navigate = page.navigate(open);
            System.out.println(navigate.url());
        }
        Locator jp = page.locator("xpath=//*[@id=\"root\"]/div/div[2]/div/div/nav/ol/li[3]/a");
        final Consumer<Page> cb = (p)-> {
            innerPage = p;
        };
        page.onDOMContentLoaded(cb);
        jp.click();
        page.offDOMContentLoaded(cb);
        System.out.println(page.url());
        innerPage.close();
        innerPage = null;
        //window.open('https://www.baidu.com','_blank','popup')
    }
    Page innerPage;
    void clickPopPage(Page curr, Locator l, Consumer<Page> action){
        curr.onPopup(action);
        l.click();
        curr.offPopup(action);
    }
    private Browser prepareEnv() {
        if(Objects.isNull(driver))this.driver = Playwright.create();
        List<String> chromePidList = chromePid();
        boolean underControl = chromeAndPortOk(chromePidList,9999);
        final Supplier<Browser> connBrowser = ()->{
            BrowserType.ConnectOverCDPOptions options = new BrowserType.ConnectOverCDPOptions().setSlowMo(1000);
            return this.driver.chromium().connectOverCDP("http://localhost:9999", options);
        };
        final Runnable runChrome = ()->ProcessUtil.asyncRunApp("C:\\Program Files\\Google\\Chrome\\Application\\chrome.exe", "--start-maximized", "--remote-debugging-port=9999");
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
        return connBrowser.get();
    }

    private static boolean chromeAndPortOk(List<String> chromePidList,int port) {
        String s = WindowsCmdUtil.cmd(String.format("netstat -ano|findstr %s",port));
        for (String string : s.split("\r\n")) {
            try{
                String port1 = string.split("LISTENING")[1].trim();
                if(chromePidList.contains(port1))return true;
            }catch(Throwable ignore){}
        }
        return false;
    }
    static void ports(){
        String s = WindowsCmdUtil.cmd("netstat -ano|findstr 9999");
        for (String string : s.split("\r\n")) {
            try{
                String trim = string.split("LISTENING")[1].trim();
            }catch(Throwable ignore){}
        }
    }
    public static void main(String[] args) {

    }
    private static List<String> chromePid() {
        String s = WindowsCmdUtil.cmd("tasklist|findstr chrome.exe");
        try{
            return Arrays.stream(s.split("\r\n"))
                    .map(string->string.split("chrome.exe")[1].trim().split(" ")[0].trim())
                    .collect(Collectors.toList());
        }catch (ArrayIndexOutOfBoundsException e){
            return Collections.emptyList();
        }
    }

    protected Page current;
    public Page getPage(){
        return current;
    }
    public Page open(final Runnable callback) {
        return getPage().waitForPopup(callback);
    }

    boolean ignoreTempLogic = true;
    private void tempPageLogic(Page page) {
        if(ignoreTempLogic)return;
        page.locator("#dropDown .move").locator("a:has-text('集团应用')").hover();
        final Page tempPage = page.waitForPopup(() -> page.locator("#dropDown .move").locator("a:has-text('集团应用')")
                .locator("..").getByTitle("集中MSS-法律辅助系统").click());
        tempPage.close();
    }
    protected void checkBrowser() {
        boolean hasDevice = false;
        while(!hasDevice){
            final String resultMsg = ProcessUtil.cmdRunApp("netstat -ano | findstr 9999");
            hasDevice = StrUtil.isNotEmpty(resultMsg);
            if(!hasDevice)ThreadUtil.sleep(1000);
        }
    }

    static void sleep(long l){
        try {
            Thread.sleep(Math.max(0,l));
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }


}
