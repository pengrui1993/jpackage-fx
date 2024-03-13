<h1 align="center" style="margin: 30px 0 30px; font-weight: bold;">园锐-RPA v1.0.0</h1>
<h4 align="center">基于SpringBoot+Playwright+Appium的自动化架构</h4>

## 平台简介

园锐-RPA是一套公司自研的RPA架构。

* SpringBoot一键启动。
* Playwright页面自动化控制。
* Appium多端一体化。

## 软件安装

### Node.js
nodejs需要先配置淘宝镜像
```
npm config set registry https://registry.npm.taobao.org
```

### Appium
使用<code>npm</code>全局安装<code>appium</code>，以及所要使用的驱动<code>xcuitest</code>、<code>uiautomator2</code>
```
npm install --global appium
```
安装Android驱动，可能会出现安装异常问题可以在指令末尾添加<code>--chromedriver-skip-install</code>
```
appium driver install uiautomator2
```
安装Windows驱动较为特殊需要使用以下命令，这里如果一直下载失败优先检查网络问题，实在不行就切外网。
```
appium driver install --source=npm appium-windows-driver
```

## 编译配置

1. Maven编译配置，当前集成了IDEA的编译配置，在独立模块中添加
<code>package-***.run.xml</code>
文件。
```
<!-- package-***.run.xml -->
<component name="ProjectRunConfigurationManager">
  <configuration default="false" name="package-***" type="MavenRunConfiguration" factoryName="Maven">
    <MavenSettings>
      <option name="myGeneralSettings" />
      <option name="myRunnerSettings" />
      <option name="myRunnerParameters">
        <MavenRunnerParameters>
          <option name="cmdOptions" />
          <option name="profiles">
            <set />
          </option>
          <option name="goals">
            <list>
              <option value="package" />
              <option value="-pl" />
              <option value="rpa-main,rpa-framework,rpa-common,rpa-***" />
            </list>
          </option>
          <option name="multimoduleDir" />
          <option name="pomFileName" value="pom.xml" />
          <option name="profilesMap">
            <map />
          </option>
          <option name="projectsCmdOptionValues">
            <list />
          </option>
          <option name="resolveToWorkspace" value="false" />
          <option name="workingDirPath" value="$PROJECT_DIR$" />
        </MavenRunnerParameters>
      </option>
    </MavenSettings>
    <method v="2" />
  </configuration>
</component>
```

2. 多模块情况各子模块单独打包在<code>rpa-main/pom.xml</code>中进行以下操作
```
<dependencies>

    ...
    
    <!-- 对应的common模块 -->
    <dependency>
        <groupId>com.yr.rpa</groupId>
        <artifactId>rpa-common-A</artifactId>
        <version>${parent.version}</version>
    </dependency>
<!-- 仅对子模块进行打包在这里将其他模块注释掉即可 -->
<!--        <dependency>-->
<!--            <groupId>com.yr.rpa</groupId>-->
<!--            <artifactId>rpa-common-B</artifactId>-->
<!--            <version>${parent.version}</version>-->
<!--        </dependency>-->
    
    ...
    
</dependencies>
```