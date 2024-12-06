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

package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.widget.WindowShape;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

/**
 * @author Grégory Van den Borre
 */
class JavaFxShape extends JavaFxBaseWidget<JavaFxShape> implements WindowShape {

    private final Rectangle rectangle;

    JavaFxShape(final Pane pane) {
        super();
        this.rectangle = new Rectangle();
        pane.getChildren().add(this.rectangle);
        this.setReady(this.rectangle);
    }

    JavaFxShape(final Rectangle shape) {
        super();
        this.rectangle = shape;
        this.setReady(this.rectangle);
    }

    @Override
    public JavaFxShape setCoordinates(Coordinates coordinates) {
        this.rectangle.setX(coordinates.getLeft());
        this.rectangle.setY(coordinates.getTop());
        this.rectangle.setHeight(coordinates.getHeight());
        this.rectangle.setWidth(coordinates.getWidth());
        return this;
    }

    @Override
    public JavaFxShape setSize(Size size) {
        this.rectangle.setHeight(size.getHeight());
        this.rectangle.setWidth(size.getWidth());
        return this;
    }

    @Override
    public JavaFxShape setPosition(Position position) {
        this.rectangle.setX(position.getLeft());
        this.rectangle.setY(position.getTop());
        return this;
    }

    @Override
    public WindowShape toFront() {
        this.rectangle.toFront();
        return this;
    }

    @Override
    public WindowShape toBack() {
        this.rectangle.toBack();
        return this;
    }

    @Override
    public JavaFxShape setColor(Color color) {
        this.rectangle.setFill(new javafx.scene.paint.Color(
                color.normalizedRedValue,
                color.normalizedGreenValue,
                color.normalizedBlueValue,
                color.normalizedAlphaValue));
        return this;
    }
}
