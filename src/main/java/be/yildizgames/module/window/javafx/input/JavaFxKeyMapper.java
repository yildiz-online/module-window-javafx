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
class JavaFxKeyMapper {

    protected static final EnumMap<KeyCode, Key> mapping = new EnumMap<>(KeyCode.class);

    static {
        mapping.put(KeyCode.ENTER, Key.ENTER);
        mapping.put(KeyCode.ESCAPE, Key.ESC);
        mapping.put(KeyCode.BACK_SPACE, Key.DELETE);
        mapping.put(KeyCode.UP, Key.UP);
        mapping.put(KeyCode.DOWN, Key.DOWN);
        mapping.put(KeyCode.LEFT, Key.LEFT);
        mapping.put(KeyCode.RIGHT, Key.RIGHT);
        mapping.put(KeyCode.ALT, Key.ALT);
        mapping.put(KeyCode.CONTROL, Key.CTRL);
        mapping.put(KeyCode.SHIFT, Key.SHIFT);
        mapping.put(KeyCode.PAGE_UP, Key.PAGE_UP);
        mapping.put(KeyCode.PAGE_DOWN, Key.PAGE_DOWN);
        mapping.put(KeyCode.F1, Key.F1);
        mapping.put(KeyCode.F2, Key.F2);
        mapping.put(KeyCode.F3, Key.F3);
        mapping.put(KeyCode.F4, Key.F4);
        mapping.put(KeyCode.F5, Key.F5);
        mapping.put(KeyCode.F6, Key.F6);
        mapping.put(KeyCode.F7, Key.F7);
        mapping.put(KeyCode.F8, Key.F8);
        mapping.put(KeyCode.F9, Key.F9);
        mapping.put(KeyCode.F10, Key.F10);
        mapping.put(KeyCode.F11, Key.F11);
        mapping.put(KeyCode.F12, Key.F12);
    }

    protected JavaFxKeyMapper() {

    }
}
