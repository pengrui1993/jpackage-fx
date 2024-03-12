package androidtest;

import cn.hutool.core.date.DateUtil;
import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.yr.rpa.framework.utils.ProcessUtil;
import io.appium.java_client.android.AndroidDriver;
import io.appium.java_client.android.nativekey.AndroidKey;
import io.appium.java_client.android.nativekey.KeyEvent;
import io.appium.java_client.remote.options.BaseOptions;
import org.openqa.selenium.By;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.remote.Command;

import java.io.IOException;
import java.net.URI;
import java.net.URL;
import java.util.Objects;
//https://cloud.tencent.com/developer/article/2056035
public class AndroidRobot {
    public static void main(String[] args) throws IOException {
        env();
        //appium -a localhost -p 4723 --log d:\appium.log --local-timezone
        //adb devices -l
        //adb shell getprop ro.build.version.release    7.1.2
        //adb shell getprop ro.build.version.sdk            25
        /*
        & 'D:\Program Files\Nox\bin\Nox.exe' -clone:Nox_2 -package:com.tencent.wework -title:YrRpa -lang:zh-Hans -screen:vertical -resolution:720x1280 -dpi:270 -root:true -virtualKey:false -manufacturer:samsung -model:SM_G9810 -phoneNumber:18511118888
         */
        final String appiumServerHostUrl = "http://127.0.0.1:4723/";
        final URL url = URI.create(appiumServerHostUrl).toURL();
        BaseOptions options = new BaseOptions()
                .amend("platformName", "Android")
                .amend("appium:deviceName", deviceName)
                .amend("appium:appPackage", configAppPackage)
                .amend("appium:appActivity", ".launch.WwMainActivity")
                .amend("appium:noReset", true)
                .amend("appium:newCommandTimeout", 3600)
                .amend("appium:connectHardwareKeyboard", true);
        // 添加默认配置参数
        options.amend("automationName", "uiautomator2");
        options.amend("language", "en");
        options.amend("locale", "US");
//        waitDevice();
//        waitApp();
        AndroidDriver driver = new AndroidDriver(url, options);

        System.in.read();
    }
    enum AppState{
        NOT_RUNNING,RUNNING_IN_FOREGROUND,RUNNING_IN_BACKGROUND

        ;
        public static AppState from(String s){
            for (AppState value : values()) {
                if(Objects.equals(value.toString(),s))return value;
            }
            throw new RuntimeException("unknown:"+s);
        }
    }

    void pkg(AndroidDriver driver){//com.android.settings
        String currentPackage = driver.getCurrentPackage();
        System.out.println(currentPackage);
    }
    void activity(AndroidDriver driver){
        String s = driver.currentActivity();
    }
    AppState appState(AndroidDriver driver){
        //RUNNING_IN_FOREGROUND RUNNING_IN_BACKGROUND NOT_RUNNING
        return AppState.from(driver.queryAppState("com.tencent.wework").name());
    }
    void page(AndroidDriver driver){
        String pageSource = driver.getPageSource();
    }
    void element(AndroidDriver driver){
        driver.findElement(By.id("com.tencent.wework:id/nn")).click();
        driver.findElement(By.xpath("//*[@text='WeCom']")).click();
    }
    void key(AndroidDriver driver){
        try{
            driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
            driver.pressKey(new KeyEvent().withKey(AndroidKey.HOME));
        }catch(Throwable t){
            t.printStackTrace();
        }
    }
    void clickEnter(AndroidDriver driver) throws IOException {//appium:appActivity -> .launch.WwMainActivity
        WebElement el = driver.findElement(By.xpath("//*[@text='微信登录']"));
        if(Objects.nonNull(el)){
            el.click();
        }//已阅读并同意 软件许可及服务协议 和 隐私政策
        driver.pressKey(new KeyEvent().withKey(AndroidKey.BACK));
    }
    static void waitApp(){
        boolean hasDevice;
        do {
            final String resultMsg = ProcessUtil.runApp(adb, "shell", "pidof", configAppPackage);
            hasDevice = StrUtil.isNotEmpty(resultMsg) && !resultMsg.contains("error");
            if (hasDevice) {  // 检测到启动成功后，再等10秒的程序加载
                ThreadUtil.sleep(10000);
            }else{
                ThreadUtil.sleep(1000);
            }
        } while (!hasDevice);
    }
    static final String configAppPackage = "com.tencent.wework";
    static final String deviceName = "SM_G9810";
    static final String adb = "D:\\Program Files\\Nox\\bin\\adb.exe";
    static void waitDevice(){
        boolean hasDevice;
        do {
            final String resultMsg = ProcessUtil.runApp(adb, "devices", "-l");
            hasDevice = StrUtil.isNotEmpty(resultMsg) && resultMsg.contains(deviceName);
            if (hasDevice) {
                ThreadUtil.sleep(5000); // 检测到启动成功后，再等5秒的GUI加载
            }
            ThreadUtil.sleep(1000); // 每次间隔一段时间
        } while (!hasDevice);
    }
    private static void env() {
        System.out.println(System.getenv("ANDROID_HOME"));
    }

    enum Activity{
        LOGIN_0(".login.controller.LoginWxAuthActivity")
        ,LOGIN_VERIFY_1(".login.controller.LoginVeryfyStep1Activity")
        ,LOGIN_VERIFY_2(".login.controller.LoginVeryfyStep2Activity")
        ,ENTERPRISE_MGR(".enterprisemgr.controller.LoginEnterpriseListActivity")
        ,ENTERPRISE_INFO(".enterprisemgr.controller.NormalEnterpriseInfoActivity")
        ,MAIN(".launch.WwMainActivity")
        ,WEB_VIEW(".common.controller.AndroidWebViewActivity")
        ;
        public final String name;

        Activity(String name) {
            this.name = name;
        }
    }
}
