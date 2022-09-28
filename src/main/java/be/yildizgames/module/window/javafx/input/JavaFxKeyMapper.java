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
import javafx.scene.input.KeyCode;

import java.util.EnumMap;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxKeyMapper {

    private static final EnumMap<KeyCode, Key> mapping = new EnumMap<>(KeyCode.class);
    private static final EnumMap<Key, KeyCode> reverse = new EnumMap<>(Key.class);

    static {
        put(KeyCode.ENTER, Key.ENTER);
        put(KeyCode.ESCAPE, Key.ESC);
        put(KeyCode.BACK_SPACE, Key.BACK_SPACE);
        put(KeyCode.DELETE, Key.DELETE);
        put(KeyCode.UP, Key.UP);
        put(KeyCode.DOWN, Key.DOWN);
        put(KeyCode.LEFT, Key.LEFT);
        put(KeyCode.RIGHT, Key.RIGHT);
        put(KeyCode.ALT, Key.ALT);
        put(KeyCode.CONTROL, Key.CTRL);
        put(KeyCode.SHIFT, Key.SHIFT);
        put(KeyCode.PAGE_UP, Key.PAGE_UP);
        put(KeyCode.PAGE_DOWN, Key.PAGE_DOWN);
        put(KeyCode.F1, Key.F1);
        put(KeyCode.F2, Key.F2);
        put(KeyCode.F3, Key.F3);
        put(KeyCode.F4, Key.F4);
        put(KeyCode.F5, Key.F5);
        put(KeyCode.F6, Key.F6);
        put(KeyCode.F7, Key.F7);
        put(KeyCode.F8, Key.F8);
        put(KeyCode.F9, Key.F9);
        put(KeyCode.F10, Key.F10);
        put(KeyCode.F11, Key.F11);
        put(KeyCode.F12, Key.F12);
        put(KeyCode.A, Key.A);
        put(KeyCode.Q, Key.Q);
        put(KeyCode.W, Key.W);
        put(KeyCode.E, Key.E);
        put(KeyCode.R, Key.R);
        put(KeyCode.T, Key.T);
    }

    private static void put(KeyCode code, Key key) {
        mapping.put(code, key);
        reverse.put(key, code);
    }

    private JavaFxKeyMapper() {

    }

    public static Key getFromCode(KeyCode code) {
        return mapping.get(code);
    }

    public static KeyCode getFromKey(Key key) {
        return reverse.get(key);
    }
}
