@echo off
::"D:\software\openlogic-openjdk-8u402-b06-windows-64\bin\java.exe" -jar out/artifacts/hello_jar/hello.jar

::java -classpath D:\software\openjfx\javafx-sdk-21.0.2\lib\javafx-swt.jar;D:\software\openjfx\javafx-sdk-21.0.2\lib\javafx.fxml.jar;D:\software\openjfx\javafx-sdk-21.0.2\lib\javafx.media.jar;D:\software\openjfx\javafx-sdk-21.0.2\lib\javafx.swing.jar;D:\software\openjfx\javafx-sdk-21.0.2\lib\javafx.web.jar -p D:\software\openjfx\javafx-sdk-21.0.2\lib\javafx.base.jar;D:\software\openjfx\javafx-sdk-21.0.2\lib\javafx.graphics.jar;D:\software\openjfx\javafx-sdk-21.0.2\lib\javafx.controls.jar;D:\project\hello\out\artifacts\hello_jar\hello.jar -m hello/fx.Main
setLocal EnableDelayedExpansion
set SEARCH_CP=
for /R D:\software\openjfx\javafx-sdk-21.0.2\lib %%a in (*.jar) do (
set SEARCH_CP=!SEARCH_CP!%%a;
)
set CP=!SEARCH_CP!D:\project\fx-hello\out\production\fx-hello
set PATH=%PATH%;C:\Program Files\Java\jdk-21\bin\
echo %CP%
::start javaw -classpath %CP% fx.Main
java -classpath %CP% fx.Main

