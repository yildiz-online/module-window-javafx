package be.yildizgames.module.window.javafx;

import be.yildizgames.module.window.BaseWindowEngine;
import be.yildizgames.module.window.Cursor;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.WindowHandle;
import be.yildizgames.module.window.input.WindowInputListener;
import be.yildizgames.module.window.javafx.internal.JavaFxApplication;
import be.yildizgames.module.window.javafx.widget.JavaFxWindowShell;
import be.yildizgames.module.window.widget.WindowShell;
import javafx.application.Application;
import javafx.stage.Stage;

public class JavaFxWindowEngine implements BaseWindowEngine {

    private boolean javafxStarted = false;

    public JavaFxWindowEngine() {

       // new Thread(() -> Application.launch(JavaFxApplication.class)).start();
       // try {
        //    Thread.sleep(1000);
       // } catch (InterruptedException e) {
        //    e.printStackTrace();
       // }
    }

    @Override
    public void update() {

    }

    @Override
    public void deleteLoadingResources() {

    }

    @Override
    public WindowHandle getHandle() {
        return null;
    }

    @Override
    public void registerInput(WindowInputListener listener) {

    }

    @Override
    public Cursor createCursor(Cursor cursor) {
        return null;
    }

    @Override
    public void setWindowTitle(String title) {

    }

    @Override
    public void setCursor(Cursor cursor) {

    }

    @Override
    public void showCursor() {

    }

    @Override
    public void hideCursor() {
        //JavaFxApplication.instance.stage.getScene().setCursor(javafx.scene.Cursor.NONE);
    }

    @Override
    public ScreenSize getScreenSize() {
        return null;// JavaFxApplication.instance.getScreenSize();
    }

    @Override
    public void setWindowIcon(String file) {
    }

    @Override
    public WindowShell createShell() {
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
}
