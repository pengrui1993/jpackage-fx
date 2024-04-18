package browsertest;

import cn.hutool.core.thread.ThreadUtil;
import cn.hutool.core.util.StrUtil;
import com.yr.rpa.framework.utils.ProcessUtil;
import com.yr.rpa.util.WindowsCmdUtil;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

public class ChromeUtil {
    public static List<String> chromePid() {
        String s = WindowsCmdUtil.cmd("tasklist|findstr chrome.exe");
        try{
            return Arrays.stream(s.split("\r\n"))
                    .map(string->string.split("chrome.exe")[1].trim().split(" ")[0].trim())
                    .collect(Collectors.toList());
        }catch (ArrayIndexOutOfBoundsException e){
            return Collections.emptyList();
        }
    }
    public static boolean chromeAndPortOk(List<String> chromePidList,int port) {
        String s = WindowsCmdUtil.cmd(String.format("netstat -ano|findstr %s",port));
        for (String string : s.split("\r\n")) {
            try{
                String port1 = string.split("LISTENING")[1].trim();
                if(chromePidList.contains(port1))return true;
            }catch(Throwable ignore){}
        }
        return false;
    }
    public static void checkBrowser() {
        boolean hasDevice = false;
        while(!hasDevice){
            final String resultMsg = ProcessUtil.cmdRunApp("netstat -ano | findstr 9999");
            hasDevice = StrUtil.isNotEmpty(resultMsg);
            if(!hasDevice) ThreadUtil.sleep(1000);
        }
    }
}
