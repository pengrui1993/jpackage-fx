package windowstest;

import cn.hutool.core.swing.RobotUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.HexUtil;
import io.appium.java_client.AppiumBy;
import io.appium.java_client.remote.options.BaseOptions;
import io.appium.java_client.windows.WindowsDriver;
import org.openqa.selenium.By;
import org.openqa.selenium.Rectangle;
import org.openqa.selenium.WebElement;

import java.awt.*;
import java.awt.event.KeyEvent;
import java.awt.event.MouseEvent;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.util.List;
import java.util.Map;

public class WindowsRobot {
    public static void main(String[] args) throws Exception {
        String id = "67074";//0x10602
        int num = Integer.parseInt(id);
        System.out.printf("%x%n", 123);//7b
        System.out.println(Integer.toHexString(num));
//        openAppByHandlerId(num);
        openRootUseWindowsScript();
    }
    //& 'C:\Program Files\Google\Chrome\Application\chrome.exe' --start-maximized --remote-debugging-port=9999
    public static void openRoot() throws MalformedURLException {
        final URL url = URI.create("http://127.0.0.1:4723/").toURL();
        final BaseOptions<?> options = new BaseOptions<>()
                .amend("platformName", "Windows")
                .amend("app", "Root")
                .amend("appium:deviceName", "WindowsPC")
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);
        options.amend("automationName", "windows");
        options.amend("language", "en");
        options.amend("locale", "US");
        WindowsDriver driver = new WindowsDriver(url, options);
        String handler = driver.findElement(By.className("WeChatMainWndForPC"))
                .getAttribute("NativeWindowHandle");
        System.out.println(handler);
        //driver.findElement(By.className("KPromeMainWindow")).findElements(By.className("KxWpsView"))
//        driver.findElement(AppiumBy.name("NTKO--文档控件浏览器."));
        WebElement kxWpsView = driver.findElements(By.className("KxWpsView")).getFirst();
        driver.quit();
        //driver.executeScript("windows: click", Map.of("x",10,"y",10))
    }

    /**
     * https://github.com/appium/appium-windows-driver#usage
     * @throws MalformedURLException
     */
    public static void openRootUseWindowsScript() throws MalformedURLException {
        final URL url = URI.create("http://127.0.0.1:4723/").toURL();
        final BaseOptions<?> options = new BaseOptions<>()
                .amend("platformName", "Windows")
                .amend("app", "Root")
                .amend("appium:deviceName", "WindowsPC")
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);
        options.amend("automationName", "windows");
        options.amend("language", "en");
        options.amend("locale", "US");
        WindowsDriver driver = new WindowsDriver(url, options);
//        driver.findElements(By.name("10000集约平台")).getFirst().getAttribute("ClassName");
//        driver.executeScript("windows: click", Map.of("x",10,"y",10,"durationMs",100));
        WebElement first = driver.findElements(By.name("10000集约平台")).getFirst();
        WebElement phone = first.findElement(By.name("请输入手机号码"));
        phone.clear();
        phone.sendKeys("13628672212");
        WebElement pwd = first.findElement(By.name("请输入密码"));
        pwd.clear();
        pwd.sendKeys("123456");//登 录
        ThreadUtil.sleep(1000);
        Rectangle rect = first.getRect();
        int btnOffsetX = 580,btnOffsetY = 275;
        int btnEndOffsetX = 845 , btnEndOffsetY = btnOffsetY;
        driver.executeScript("windows: clickAndDrag"
                , Map.of("startX", rect.getX()+btnOffsetX
                        , "startY", rect.getY()+btnOffsetY
                        , "endX", rect.getX()+btnEndOffsetX
                        , "endY", rect.getY()+btnEndOffsetY
                        , "durationMs", 100
                )
        );
        ThreadUtil.sleep(1000);
        WebElement submit = first.findElement(By.name("登 录"));
        submit.click();
        List<WebElement> els = driver.findElements(By.name("手机号验证"));
        if(!els.isEmpty()){
            WebElement we = els.getFirst();
            if(we.isDisplayed()){

            }
        }
        driver.quit();
    }
    //driver.executeScript("windows: click","x","10","y","10")
    public static void openAppBy() throws MalformedURLException {
        final URL url = URI.create("http://127.0.0.1:4723/").toURL();
        final BaseOptions<?> options = new BaseOptions<>()
                .amend("appium:app", "D:\\docs\\统一客户接触平台消息接入http接口协议v1.1.doc")
                .amend("platformName", "Windows")
                .amend("appium:deviceName", "WindowsPC")
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);
        options.amend("automationName", "windows");
        options.amend("language", "en");
        options.amend("locale", "US");
        WindowsDriver driver = new WindowsDriver(url, options);
        driver.quit();
    }
    static void openAppByHandlerId(Integer nativeWindowHandleNumber) throws MalformedURLException {
//        WebElement domElement = driver.findElement(By.className("KxWpsView"));
//        final WebElement webElement = driver.findElement(AppiumBy.name("NTKO--文档控件浏览器."));
//        final String nativeWindowHandle = webElement.getAttribute("NativeWindowHandle");
        final String id = Integer.toHexString(nativeWindowHandleNumber);
        final BaseOptions options2 = new BaseOptions();
        final URL url2 = URI.create("http://127.0.0.1:4723/").toURL();
        options2.amend("automationName", "windows");
        options2.amend("language", "en");
        options2.amend("locale", "US");
        options2.amend("appTopLevelWindow", id);
        WindowsDriver driver2= new WindowsDriver(url2, options2);
        WebElement element = driver2.findElement(By.className("popupshadow"));
        //download visual studio to get inspect.exe to find some attributes to use
        System.out.println(element.getAttribute("LocalizedControlType"));
        driver2.quit();
    }
    public static void test0() throws IOException {
        final URL url = URI.create("http://127.0.0.1:4723/").toURL();
        final BaseOptions<?> options = new BaseOptions<>()
                .amend("platformName", "Windows")
                .amend("app", "Root")
                .amend("appium:deviceName", "WindowsPC")
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);
        options.amend("automationName", "windows");
        options.amend("language", "en");
        options.amend("locale", "US");
        WindowsDriver driver = new WindowsDriver(url, options);
        final WebElement ele = driver.findElement(AppiumBy.name("NTKO--文档控件浏览器."));
        final String nativeWindowHandle = ele.getAttribute("NativeWindowHandle");
        final String id = HexUtil.toHex(Integer.parseInt(nativeWindowHandle));
        final BaseOptions options2 = new BaseOptions();
        options2.amend("automationName", "windows");
        options2.amend("language", "en");
        options2.amend("locale", "US");
        options2.amend("appTopLevelWindow", id);
        final URL url2 = URI.create("http://127.0.0.1:4723/").toURL();
        WindowsDriver driver2= new WindowsDriver(url2, options2);
        ThreadUtil.sleep(5000);
        WebElement domElement = driver2.findElement(By.className("KxWpsView"));
        System.in.read();
        driver2.quit();
        driver.quit();
    }
    void wd(WindowsDriver d){
    }
    void demo(WindowsDriver driver){}
    void click(WindowsDriver driver){
    }
    void page(WindowsDriver driver){
        driver.getPageSource();
    }
    void app(WindowsDriver driver){
        driver.getWindowHandles();
    }
}
