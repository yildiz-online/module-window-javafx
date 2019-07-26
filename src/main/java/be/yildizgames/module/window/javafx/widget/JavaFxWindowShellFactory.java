package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.javafx.internal.JavaFxApplication;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowShellFactory;
import javafx.application.Application;

public class JavaFxWindowShellFactory implements WindowShellFactory {

    private boolean javafxStarted = false;

    @Override
    public WindowShell buildShell() {
        if(!javafxStarted) {
            javafxStarted = true;
            new Thread(() -> Application.launch(JavaFxApplication.class)).start();
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            return new JavaFxWindowShell(JavaFxApplication.getInstance().getStage());
        }
        return new JavaFxWindowShell();
    }

    @Override
    public WindowShell buildShellWithClose() {
        return null;
    }

    @Override
    public WindowShell buildShellNoClose(WindowImageProvider provider) {
        return null;
    }
}
