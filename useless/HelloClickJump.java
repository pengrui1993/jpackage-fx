package browsertest;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

import java.io.IOException;

public class HelloClickJump {
        public static void main(String[] args) {
            boolean headless = false;
            try (Playwright playwright = Playwright.create()) {
//                BrowserType type = playwright.firefox();
//                BrowserType type = playwright.webkit();
                BrowserType type = playwright.chromium();
                Browser browser = type.launch(new BrowserType.LaunchOptions()
                        .setHeadless(headless)
                        .setSlowMo(100));
                Page page = browser.newPage();
                pagePopup(page);
                System.in.read();
                browser.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        static void pagePopup(Page page){
            final String url = "https://www.baidu.com/";
            page.navigate(url);
            page.waitForURL(url);
            Page img = page.waitForPopup(() -> {
                page.locator("//*[@id=\"s-top-left\"]/a[6]").click();
            });
            System.out.println("popup done");
            img.locator("//*[@id=\"kw\"]").fill("hello");
            img.locator("//*[@id=\"homeSearchForm\"]/span[2]/input").click();
            img.waitForLoadState();
            System.out.println();
        }
        static void pageLoad(Page page){
            final String url = "https://cn.bing.com/";
            page.navigate(url);
            page.waitForURL(url);
            page.locator("//*[@id=\"images\"]/a").click();
            page.waitForLoadState(LoadState.DOMCONTENTLOADED);
            System.out.println("loaded");
        }
}
