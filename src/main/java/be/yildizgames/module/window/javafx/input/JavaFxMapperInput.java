package be.yildizgames.module.window.javafx.input;

import be.yildizgames.module.window.widget.WindowInputBoxChangeListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class JavaFxMapperInput implements ChangeListener<String> {

    private final WindowInputBoxChangeListener listener;

    public JavaFxMapperInput(WindowInputBoxChangeListener l) {
        this.listener = l;
    }

    @Override
    public void changed(ObservableValue observableValue, String o, String t1) {
        this.listener.onChange();
    }
}
