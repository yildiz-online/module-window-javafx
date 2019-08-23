package be.yildizgames.module.window.javafx.input;

import be.yildizgames.module.window.input.KeyboardListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class JavaFxMapperKeyPressed extends JavaFxKeyMapper implements EventHandler<KeyEvent> {

    private final KeyboardListener listener;

    public JavaFxMapperKeyPressed(KeyboardListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    public final void handle(final KeyEvent keyEvent) {
        if(keyEvent.getCode().isLetterKey() || keyEvent.getCode().isDigitKey() || keyEvent.getCode().isWhitespaceKey()) {
            this.listener.keyPressed(keyEvent.getCode().getChar().charAt(0));
        } else {
            this.listener.specialKeyPressed(mapping.get(keyEvent.getCode()));
        }
    }
}
