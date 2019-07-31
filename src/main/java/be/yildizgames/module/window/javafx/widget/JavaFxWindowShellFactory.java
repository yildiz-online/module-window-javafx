package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.javafx.internal.JavaFxApplication;
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

    @Override
    public JavaFxWindowShell buildShell(WindowShellOptions...options) {
        if(!javafxStarted) {
            javafxStarted = true;
            new Thread(() -> Application.launch(JavaFxApplication.class)).start();
            try {
                //FIXME can cause race condition, build on event instead of after wait time.
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new JavaFxWindowShell(JavaFxApplication.getInstance().getStage(), options);
        }
        return new JavaFxWindowShell(options);
    }
}
