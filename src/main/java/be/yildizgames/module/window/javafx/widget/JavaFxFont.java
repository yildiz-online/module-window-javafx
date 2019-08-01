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

package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.widget.WindowFont;
import javafx.scene.text.Font;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * @author Grégory Van den Borre
 */
class JavaFxFont implements WindowFont {

    private static final Map<UUID, JavaFxFont> fonts = new HashMap<>();

    private final UUID id;

    private final Font font;

    JavaFxFont(String name, int height) {
        this.id = UUID.randomUUID();
        this.font = new Font(name, height);
        fonts.put(this.id, this);
    }

    static JavaFxFont getById(UUID uuid) {
        return fonts.get(uuid);
    }

    @Override
    public UUID getId() {
        return this.id;
    }

    Font getInnerFont() {
        return this.font;
    }
}
