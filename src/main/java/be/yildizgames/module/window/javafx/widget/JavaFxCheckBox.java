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
import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.widget.WidgetEvent;
import be.yildizgames.module.window.widget.WindowCheckBox;
import be.yildizgames.module.window.widget.WindowFont;
import be.yildizgames.module.window.widget.WindowWidgetChangeListener;
import javafx.scene.control.CheckBox;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

class JavaFxCheckBox extends JavaFxBaseWidget<JavaFxCheckBox> implements WindowCheckBox {

    private final CheckBox checkBox;

    private final Label caption;

    JavaFxCheckBox(final Pane pane) {
        super();
        this.checkBox = new CheckBox();
        this.caption = new Label();
        pane.getChildren().addAll(this.checkBox, this.caption);
        this.caption.setMaxWidth(200);
        this.caption.setMaxHeight(30);
        this.setReady(this.checkBox);
        this.setSize(0, 0);
    }

    @Override
    public final boolean isChecked() {
        return this.checkBox.isSelected();
    }

    @Override
    public final JavaFxCheckBox check() {
        this.checkBox.setSelected(true);
        return this;
    }

    @Override
    public final JavaFxCheckBox uncheck() {
        this.checkBox.setSelected(false);
        return this;
    }

    @Override
    public final WindowCheckBox setCaption(String text) {
        this.caption.setText(text);
        return this;
    }

    @Override
    public final WindowCheckBox setCaptionUnderlined(boolean underlined) {
        this.caption.setUnderline(underlined);
        return this;
    }

    @Override
    public final WindowCheckBox setCaptionFont(WindowFont font) {
        this.caption.setFont(JavaFxFont.getById(font.getId()).getInnerFont());
        return this;
    }

    @Override
    public final WindowCheckBox setCaptionColor(Color color) {
        this.caption.setTextFill(new javafx.scene.paint.Color(
                color.normalizedRedValue,
                color.normalizedGreenValue,
                color.normalizedBlueValue,
                color.normalizedAlphaValue));
        return this;
    }

    @Override
    public final JavaFxCheckBox setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
        this.checkBox.setLayoutX(coordinates.getLeft());
        this.checkBox.setLayoutY(coordinates.getTop());
        this.checkBox.setMaxHeight(coordinates.getHeight());
        this.checkBox.setMinHeight(coordinates.getHeight());
        this.checkBox.setMaxWidth(coordinates.getWidth());
        this.checkBox.setMinWidth(coordinates.getWidth());
        this.caption.setLayoutX(coordinates.getLeft() + 20);
        this.caption.setLayoutY(coordinates.getTop() - 8);
        return this;
    }

    @Override
    public final WindowCheckBox setSize(Size size) {
        this.updateSize(size);
        this.checkBox.setMaxHeight(size.getHeight());
        this.checkBox.setMinHeight(size.getHeight());
        this.checkBox.setMaxWidth(size.getWidth());
        this.checkBox.setMinWidth(size.getWidth());
        return this;
    }

    @Override
    public final WindowCheckBox setPosition(Position position) {
        this.updatePosition(position);
        this.checkBox.setLayoutX(position.getLeft());
        this.checkBox.setLayoutY(position.getTop());
        this.caption.setLayoutX(position.getLeft() + 20);
        this.caption.setLayoutY(position.getTop() - 8);

        return this;
    }

    @Override
    public final JavaFxCheckBox setVisible(boolean visible) {
        this.caption.setVisible(visible);
        this.checkBox.setVisible(visible);
        return this;
    }

    @Override
    public final JavaFxCheckBox onChange(WindowWidgetChangeListener<Boolean> listener) {
        this.checkBox.selectedProperty().addListener((observable, oldValue, newValue) -> listener.changed(newValue));
        return this;
    }

    @Override
    public final JavaFxCheckBox fireEvent(WidgetEvent event) {
        if (event == WidgetEvent.MOUSE_LEFT_CLICK) {
            if (checkBox.isSelected()) {
                this.uncheck();
            } else {
                this.check();
            }
        }
        return this;
    }

    @Override
    public final WindowCheckBox toFront() {
        this.caption.toFront();
        this.checkBox.toFront();
        return this;
    }

    @Override
    public final WindowCheckBox toBack() {
        this.caption.toBack();
        this.checkBox.toBack();
        return this;
    }

}
