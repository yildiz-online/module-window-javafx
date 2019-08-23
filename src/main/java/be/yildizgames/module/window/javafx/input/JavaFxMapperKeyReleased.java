package be.yildizgames.module.window.javafx.input;

import be.yildizgames.module.window.input.KeyboardListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyEvent;

public class JavaFxMapperKeyReleased extends JavaFxKeyMapper implements EventHandler<KeyEvent> {

    private final KeyboardListener listener;

    public JavaFxMapperKeyReleased(KeyboardListener listener) {
        super();
        this.listener = listener;
    }

    @Override
    public final void handle(final KeyEvent keyEvent) {
        if(keyEvent.getCode().isLetterKey() || keyEvent.getCode().isDigitKey() || keyEvent.getCode().isWhitespaceKey()) {
            this.listener.keyReleased(keyEvent.getCode().getChar().charAt(0));
        } else {
            this.listener.specialKeyReleased(mapping.get(keyEvent.getCode()));
        }
    }
}
