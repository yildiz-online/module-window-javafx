package be.yildizgames.module.window.javafx.widget;

import javafx.application.Platform;

public class JavaFxBaseWidget {

    private boolean ready = false;

    protected void setReady() {
        ready = true;
    }

    protected void runWhenReady(Runnable r) {
        while (!ready) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        Platform.runLater(r);
    }
}
