/*
 * MIT License
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package be.yildizgames.module.window.javafx.input;

import be.yildizgames.module.window.input.Key;
import be.yildizgames.module.window.input.KeyboardListener;
import javafx.event.EventHandler;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxKeyMapper implements EventHandler<KeyEvent> {

    private final KeyboardListener listener;

    private final Map<KeyCode, Key> mapping = new HashMap<>();

    public JavaFxKeyMapper(KeyboardListener listener) {
        this.listener = listener;
        this.mapping.put(KeyCode.ENTER, Key.ENTER);
        this.mapping.put(KeyCode.ESCAPE, Key.ESC);
        this.mapping.put(KeyCode.BACK_SPACE, Key.DELETE);
        this.mapping.put(KeyCode.UP, Key.UP);
        this.mapping.put(KeyCode.DOWN, Key.DOWN);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if(keyEvent.getCode().isLetterKey() || keyEvent.getCode().isDigitKey() || keyEvent.getCode().isWhitespaceKey()) {
            this.listener.keyPressed(keyEvent.getCode().getChar().charAt(0));
        } else {
            System.out.println(keyEvent.getCode().getCode());

            System.out.println(KeyCode.ENTER.getCode());
            System.out.println(this.mapping.get(keyEvent.getCode()));
            this.listener.specialKeyPressed(this.mapping.get(keyEvent.getCode()));
        }
    }
}
