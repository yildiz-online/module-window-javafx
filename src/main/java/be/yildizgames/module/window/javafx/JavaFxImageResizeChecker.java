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

package be.yildizgames.module.window.javafx;

import be.yildizgames.module.window.util.image.ImageResizeChecker;
import javafx.scene.image.Image;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

/**
 * @author Grégory Van den Borre
 */
class JavaFxImageResizeChecker implements ImageResizeChecker {

    private final int maxWidth;

    private final int maxHeight;

    private Image image;

    JavaFxImageResizeChecker(int width, int height) {
        super();
        this.maxWidth = width;
        this.maxHeight = height;
    }

    @Override
    public final boolean check(Path path) {
        try {
            this.image = new Image(Files.newInputStream(path));
            return image.getWidth() > this.maxWidth || image.getHeight() > this.maxHeight;
        } catch (IOException e) {
            System.getLogger(this.getClass().getName()).log(System.Logger.Level.ERROR, "I/O error", e);
        }
        return false;
    }

    @Override
    public final float compute() {
        float w = (float) (this.maxWidth / image.getWidth());
        float h = (float) (this.maxHeight / image.getHeight());
        return Math.min(w, h);
    }
}
