@echo off
setLocal EnableDelayedExpansion
set SEARCH_CP=
for /R D:\project\fx-hello\out\artifacts\hello %%a in (*.jar) do (
set SEARCH_CP=!SEARCH_CP!%%a;
)
set CP=!SEARCH_CP!
set PATH=%PATH%;C:\Program Files\Java\jdk-21\bin\
java -classpath %CP% fx.Main