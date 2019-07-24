package be.yildizgames.module.window.javafx;

import be.yildizgames.module.window.BaseWindowEngine;
import be.yildizgames.module.window.WindowEngineProvider;

public class JavaFxWindowEngineProvider implements WindowEngineProvider {

    @Override
    public BaseWindowEngine getEngine() {
        return new JavaFxWindowEngine();
    }
}
