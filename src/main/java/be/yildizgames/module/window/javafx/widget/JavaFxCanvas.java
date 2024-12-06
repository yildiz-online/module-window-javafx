    
/*
 *
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.widget.WindowCanvas;
import javafx.scene.canvas.Canvas;
import javafx.scene.layout.Pane;

/**
 * @author Grégory Van den Borre
 */
class JavaFxCanvas extends JavaFxBaseWidget<JavaFxCanvas> implements WindowCanvas {

    private final Canvas canvas;

    JavaFxCanvas(Pane pane) {
        super();
        this.canvas = new Canvas();
        pane.getChildren().add(this.canvas);
        this.setReady(this.canvas);
    }

    @Override
    public final JavaFxCanvas setCoordinates(Coordinates coordinates) {
        this.canvas.setLayoutX(coordinates.getLeft());
        this.canvas.setLayoutY(coordinates.getTop());
        this.canvas.setHeight(coordinates.getHeight());
        this.canvas.setWidth(coordinates.getWidth());
        return this;
    }

    @Override
    public final JavaFxCanvas setSize(Size size) {
        this.canvas.setHeight(size.getHeight());
        this.canvas.setWidth(size.getWidth());

        return this;
    }

    @Override
    public final JavaFxCanvas setPosition(Position position) {
        this.canvas.setLayoutX(position.getLeft());
        this.canvas.setLayoutY(position.getTop());

        return this;
    }

}
