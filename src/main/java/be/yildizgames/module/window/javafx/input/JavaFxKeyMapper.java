/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
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
        this.mapping.put(KeyCode.LEFT, Key.LEFT);
        this.mapping.put(KeyCode.RIGHT, Key.RIGHT);
        this.mapping.put(KeyCode.ALT, Key.ALT);
        this.mapping.put(KeyCode.CONTROL, Key.CTRL);
        this.mapping.put(KeyCode.SHIFT, Key.SHIFT);
        this.mapping.put(KeyCode.PAGE_UP, Key.PAGE_UP);
        this.mapping.put(KeyCode.PAGE_DOWN, Key.PAGE_DOWN);
        this.mapping.put(KeyCode.F1, Key.F1);
        this.mapping.put(KeyCode.F2, Key.F2);
        this.mapping.put(KeyCode.F3, Key.F3);
        this.mapping.put(KeyCode.F4, Key.F4);
        this.mapping.put(KeyCode.F5, Key.F5);
        this.mapping.put(KeyCode.F6, Key.F6);
        this.mapping.put(KeyCode.F7, Key.F7);
        this.mapping.put(KeyCode.F8, Key.F8);
        this.mapping.put(KeyCode.F9, Key.F9);
        this.mapping.put(KeyCode.F10, Key.F10);
        this.mapping.put(KeyCode.F11, Key.F11);
        this.mapping.put(KeyCode.F12, Key.F12);
    }

    @Override
    public void handle(KeyEvent keyEvent) {
        if(keyEvent.getCode().isLetterKey() || keyEvent.getCode().isDigitKey() || keyEvent.getCode().isWhitespaceKey()) {
            this.listener.keyPressed(keyEvent.getCode().getChar().charAt(0));
        } else {
            this.listener.specialKeyPressed(this.mapping.get(keyEvent.getCode()));
        }
    }
}
