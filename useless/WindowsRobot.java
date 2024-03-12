package windowstest;

import cn.hutool.core.util.HexUtil;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;

import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;

public class WindowsRobot {
    public static void main(String[] args) throws IOException {
        final URL url = URI.create("http://127.0.0.1:4723/").toURL();
        final BaseOptions<?> options = new BaseOptions<>()
                .amend("platformName", "Windows")
                .amend("app", "Root")
//                .amend("appium:deviceName", "WindowsPC")
//                .amend("appium:newCommandTimeout", 3600)
//                .amend("appium:connectHardwareKeyboard", true)
        ;
        // 添加默认配置参数
        options.amend("automationName", "windows");
//        options.amend("language", "en");
//        options.amend("locale", "US");
        WindowsDriver driver = new WindowsDriver(url, options);
        System.out.println(        driver.getWindowHandle());
        System.out.println(        driver.getPageSource());
        System.in.read();
    }
    static void driver2(WindowsDriver driver) throws MalformedURLException {
//        WebElement domElement = driver.findElement(By.className("KxWpsView"));
        final WebElement webElement = driver.findElement(AppiumBy.name("NTKO--文档控件浏览器."));
        final String nativeWindowHandle = webElement.getAttribute("NativeWindowHandle");
        final String id = HexUtil.toHex(Integer.parseInt(nativeWindowHandle));
        final BaseOptions options2 = new BaseOptions();
        final URL url2 = URI.create("http://127.0.0.1:4723/").toURL();
        // 添加默认配置参数
        options2.amend("automationName", "windows");
        options2.amend("language", "en");
        options2.amend("locale", "US");
        options2.amend("appTopLevelWindow", id);
        WindowsDriver driver2= new WindowsDriver(url2, options2);
    }
    void demo(WindowsDriver driver){}
    void page(WindowsDriver driver){
        driver.getPageSource();
    }
    void app(WindowsDriver driver){
        driver.getWindowHandles();
    }
}
