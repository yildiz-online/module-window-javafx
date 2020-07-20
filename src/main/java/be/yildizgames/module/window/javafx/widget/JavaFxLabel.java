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

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.widget.WindowFont;
import be.yildizgames.module.window.widget.WindowTextLine;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

/**
 * @author Grégory Van den Borre
 */
class JavaFxLabel extends JavaFxBaseWidget<JavaFxLabel> implements WindowTextLine {

    private final Label label;

    private String text = "";

    private Color color = Color.BLACK;

    JavaFxLabel(Pane pane) {
        super();
            this.label = new Label();
            pane.getChildren().add(this.label);
            this.setReady(this.label);

    }

    @Override
    public final WindowTextLine setText(String text) {
            this.label.setText(text);
            this.text = text;

        return this;
    }

    @Override
    public final WindowTextLine wrapText() {
        this.label.setWrapText(true);
        return this;
    }

    @Override
    public final WindowTextLine setColor(Color color) {
        if(!color.equals(this.color)) {
                this.label.setTextFill(new javafx.scene.paint.Color(
                        color.normalizedRedValue, color.normalizedGreenValue, color.normalizedBlueValue, color.normalizedAlphaValue));

        }
        this.color = color;
        return this;
    }

    @Override
    public final String getText() {
        return this.text;
    }

    @Override
    public final WindowTextLine setUnderline(boolean active) {
        this.label.setUnderline(active);
        return this;
    }

    @Override
    public final WindowTextLine setFont(WindowFont font) {
        this.label.setFont(JavaFxFont.getById(font.getId()).getInnerFont());
        return this;
    }

    @Override
    public final WindowTextLine setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
            this.label.setLayoutX(coordinates.left);
            this.label.setLayoutY(coordinates.top);
            this.label.setMaxHeight(coordinates.height);
            this.label.setMaxWidth(coordinates.width);

        return this;
    }

    @Override
    public final WindowTextLine setSize(Size size) {
        this.updateCoordinates(size);
            this.label.setMaxWidth(size.width);
            this.label.setMaxHeight(size.height);

        return this;
    }

    @Override
    public final WindowTextLine setPosition(Position position) {
        this.updateCoordinates(position);
            this.label.setLayoutX(position.left);
            this.label.setLayoutY(position.top);

        return this;
    }

    @Override
    public final WindowTextLine alignText(TextAlignment alignment) {
        switch (alignment) {
            case RIGHT:
                this.label.setTextAlignment(javafx.scene.text.TextAlignment.RIGHT);
                break;
            case CENTER:
                this.label.setTextAlignment(javafx.scene.text.TextAlignment.CENTER);
                break;
            default:
                this.label.setTextAlignment(javafx.scene.text.TextAlignment.LEFT);
        }
        return this;
    }
}
