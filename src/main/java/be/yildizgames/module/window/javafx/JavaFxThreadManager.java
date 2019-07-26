package be.yildizgames.module.window.javafx;

import be.yildizgames.module.window.WindowThreadManager;
import javafx.application.Platform;

public class JavaFxThreadManager implements WindowThreadManager {

    @Override
    public void runAsync(Runnable r) {
        Platform.runLater(r);
    }
}
