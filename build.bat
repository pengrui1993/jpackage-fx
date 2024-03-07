@echo off
::https://www.jianshu.com/p/a0ca636b6bbf
::https://zhuanlan.zhihu.com/p/120351837
::jlink --launcher run=hello/fx.Main --module-path "%JAVA_HOME%\jmods;D:\software\openjfx\javafx-sdk-21.0.2\lib"; ./ --add-modules hello --output package

::jlink --output "D:\MyApp\jre" --module-path ..\jmods --add-modules java.base,javafx.base,javafx.graphics,javafx.controls
::D:\software\openjfx\javafx-sdk-21.0.2\lib

::"C:\Program Files\Java\jdk-21\bin\jmod" create ^
::    --class-path D:\project\hello\out\artifacts\hello_jar\hello.jar ^
::    D:\project\hello\out\artifacts\hello_jar\hello.jmod
set PATH=%PATH%;C:\Program Files\Java\jdk-21\bin
set INPUT_HOME=D:\project\fx-hello\out\artifacts\hello
set FX_HOME=D:\software\openjfx\javafx-sdk-21.0.2
set COMPILE_PATH=D:\project\fx-hello\out\production\fx-hello
set TARGET_NAME=hello.jar
set DIST_PATH=D:\project\fx-hello\out\dist
set PKG_NAME=demo

xcopy /y /c /h /r /s  %FX_HOME%\lib %INPUT_HOME% /e /i
cd %COMPILE_PATH% && jar cvf %TARGET_NAME% .
copy %COMPILE_PATH%\%TARGET_NAME% %INPUT_HOME%\%TARGET_NAME%
del %COMPILE_PATH%\%TARGET_NAME%
mkdir %DIST_PATH%
cd %DIST_PATH%
rmdir /S /Q %DIST_PATH%\%PKG_NAME%
jpackage --type app-image --name %PKG_NAME% --input %INPUT_HOME% --main-jar %TARGET_NAME% --main-class fx.Main --win-console
xcopy /y /c /h /r /s %FX_HOME%\bin %DIST_PATH%\%PKG_NAME%\runtime\bin /e /i
::copy jfx bin/* to runtime/bin/*
pause