module hello {
    requires java.desktop;
    requires javafx.graphics;
    opens fx to javafx.graphics;
}