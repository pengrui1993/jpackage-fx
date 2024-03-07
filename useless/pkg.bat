rmdir /S /Q jre
"C:\Program Files\Java\jdk-21\bin\jpackage" --type app-image --name jre ^
    --input D:\software\openjfx\javafx-sdk-21.0.2\lib ^
    --main-jar hello.jar ^
    --main-class fx.Main ^
    --win-console


::"C:\Program Files\Java\jdk-21\bin\jpackage" --type app-image --name jre --input D:\project\fx-hello\out\production --main-jar hello.jar --main-class fx.Main --win-console
::copy jfx bin/* to runtime/bin/*