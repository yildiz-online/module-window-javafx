open module be.yildizgames.module.window.javafx {

    requires be.yildizgames.module.window;
    requires javafx.graphics;
    requires javafx.controls;
    requires javafx.media;
    requires org.controlsfx.controls;
    requires javafx.web;
    requires javafx.fxml;

    provides be.yildizgames.module.window.WindowEngineProvider
            with be.yildizgames.module.window.javafx.JavaFxWindowEngineProvider;

    exports be.yildizgames.module.window.javafx;
}
