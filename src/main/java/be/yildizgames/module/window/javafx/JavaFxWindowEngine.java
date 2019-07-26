package be.yildizgames.module.window.javafx;

import be.yildizgames.module.window.BaseWindowEngine;
import be.yildizgames.module.window.Cursor;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.WindowHandle;
import be.yildizgames.module.window.WindowThreadManager;
import be.yildizgames.module.window.input.WindowInputListener;
import be.yildizgames.module.window.javafx.widget.JavaFxWindowShellFactory;
import be.yildizgames.module.window.widget.WindowShellFactory;

public class JavaFxWindowEngine implements BaseWindowEngine {

    private final WindowShellFactory shellFactory = new JavaFxWindowShellFactory();
    private final WindowThreadManager threadManager = new JavaFxThreadManager();

    public JavaFxWindowEngine() {
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
    public WindowShellFactory getWindowShellFactory() {
        return this.shellFactory;
    }

    @Override
    public WindowThreadManager getThreadManager() {
        return this.threadManager;
    }
}
