package be.yildizgames.module.window.javafx.input;

import be.yildizgames.module.window.widget.WindowInputBoxChangeListener;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

public class JavaFxMapperInput implements ChangeListener {

    private final WindowInputBoxChangeListener listener;

    public JavaFxMapperInput(WindowInputBoxChangeListener l) {
        this.listener = l;
    }

    @Override
    public void changed(ObservableValue observableValue, Object o, Object t1) {
        this.listener.onChange();
    }
}
