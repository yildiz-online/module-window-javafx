module be.yildizgames.module.window.javafx {

    requires be.yildizgames.module.window;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires com.sun.jna.platform;
    requires com.sun.jna;
    requires org.controlsfx.controls;

    provides be.yildizgames.module.window.WindowEngineProvider
            with be.yildizgames.module.window.javafx.JavaFxWindowEngineProvider;

    exports be.yildizgames.module.window.javafx;
}