package be.yildizgames.module.window.javafx.internal;

import be.yildizgames.module.window.ScreenSize;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is internal, do not instantiate it manually nor use it.
 * This package is hidden when using modules, anyway without it this class is visible.
 * @author Grégory Van den Borre
 */
public class JavaFxApplication extends Application {

    private static JavaFxApplication instance;

    private Stage stage;

    public JavaFxApplication() {
        super();
    }

    public Stage getStage() {
        return stage;
    }

    public static JavaFxApplication getInstance() {
        if(instance == null) {
            try {
                Thread.sleep(200);
                return getInstance();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return instance;
    }

    @Override
    public void start(Stage stage) {
        instance = this;
        this.stage = stage;
        stage.show();
    }

    public final void setTitle(String title) {
        this.stage.setTitle(title);
    }

    public final ScreenSize getScreenSize() {
        return new ScreenSize((int)this.stage.getWidth(), (int)this.stage.getHeight());
    }
}
