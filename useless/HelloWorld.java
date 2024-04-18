package browsertest;

import com.microsoft.playwright.*;
import com.microsoft.playwright.options.LoadState;

import java.io.IOException;
import java.nio.file.Paths;

public class HelloWorld {
        public static void main(String[] args) {
            boolean headless = false;
            try (Playwright playwright = Playwright.create()) {
                BrowserType type = playwright.firefox();
//                BrowserType type = playwright.webkit();
//                BrowserType type = playwright.chromium();
                Browser browser = type.launch(new BrowserType.LaunchOptions()
                        .setHeadless(headless)
                        .setSlowMo(100));
                Page page = browser.newPage();
                final String url = "https://www.baidu.com/";
                page.navigate(url);
                page.waitForURL(url);
//                page.screenshot(new Page.ScreenshotOptions().setPath(Paths.get("example.png")));
                System.in.read();
                browser.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            System.out.println();
        }

        void handler(Page page){
            ElementHandle handle = page.querySelector("text=Submit");
        }
}
