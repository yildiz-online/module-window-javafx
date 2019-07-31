package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.javafx.internal.JavaFxApplication;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowShellFactory;
import be.yildizgames.module.window.widget.WindowShellOptions;
import javafx.application.Application;

/**
 * Create javafx instances of window shell.
 *
 * @author Grégory Van den Borre
 */
public class JavaFxWindowShellFactory implements WindowShellFactory {

    /**
     * Flag to check if the application is already launched or not.
     */
    private boolean javafxStarted = false;

    private final WindowImageProvider imageProvider;

    public JavaFxWindowShellFactory(WindowImageProvider imageProvider) {
        this.imageProvider = imageProvider;
    }

    @Override
    public JavaFxWindowShell buildShell(WindowShellOptions...options) {
        if(!javafxStarted) {
            javafxStarted = true;
            new Thread(() -> Application.launch(JavaFxApplication.class)).start();
            return new JavaFxWindowShell(JavaFxApplication.getInstance().getStage(), this.imageProvider, options);
        }
        return new JavaFxWindowShell(this.imageProvider, options);
    }
}
