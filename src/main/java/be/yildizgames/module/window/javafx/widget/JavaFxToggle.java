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
import be.yildizgames.module.window.widget.WindowFont;
import be.yildizgames.module.window.widget.WindowToggle;
import be.yildizgames.module.window.widget.WindowWidgetChangeListener;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import org.controlsfx.control.ToggleSwitch;

/**
 * @author Grégory Van den Borre
 */
class JavaFxToggle extends JavaFxBaseWidget<JavaFxToggle> implements WindowToggle {

    private final ToggleSwitch toggle;

    private final Label caption;

    JavaFxToggle(Pane pane) {
        super();
        this.toggle = new ToggleSwitch();
        this.caption = new Label();
        pane.getChildren().addAll(this.toggle, this.caption);
        this.setReady(this.toggle);
    }

    @Override
    public JavaFxToggle setCoordinates(Coordinates coordinates) {
        this.toggle.setLayoutX(coordinates.getLeft());
        this.toggle.setLayoutY(coordinates.getTop());
        this.toggle.setMinSize(coordinates.getWidth(), coordinates.getHeight());
        this.toggle.setMaxSize(coordinates.getWidth(), coordinates.getHeight());
        this.caption.setLayoutX(coordinates.getLeft() + 20.0);
        this.caption.setLayoutY(coordinates.getTop() - 8.0);
        return this;
    }

    @Override
    public JavaFxToggle setSize(Size size) {
        this.toggle.setMinSize(size.getWidth(), size.getHeight());
        this.toggle.setMaxSize(size.getWidth(), size.getHeight());
        return this;
    }

    @Override
    public JavaFxToggle setPosition(Position position) {
        this.toggle.setLayoutX(position.getLeft());
        this.toggle.setLayoutY(position.getTop());
        this.caption.setLayoutX(position.getLeft() + 20.0);
        this.caption.setLayoutY(position.getTop() - 8.0);
        return this;
    }

    @Override
    public final JavaFxToggle check() {
        this.toggle.setSelected(true);
        return this;
    }

    @Override
    public final JavaFxToggle uncheck() {
        this.toggle.setSelected(false);
        return this;
    }

    @Override
    public final boolean isChecked() {
        return this.toggle.isSelected();
    }

    @Override
    public final JavaFxToggle onChange(WindowWidgetChangeListener<Boolean> listener) {
        this.toggle.selectedProperty().addListener((observable, oldValue, newValue) -> listener.changed(newValue));
        return this;
    }

    @Override
    public final JavaFxToggle setVisible(boolean visible) {
        this.caption.setVisible(visible);
        this.toggle.setVisible(visible);
        return this;
    }

    @Override
    public WindowToggle toFront() {
        this.caption.toFront();
        this.toggle.toFront();
        return this;
    }

    @Override
    public WindowToggle toBack() {
        this.caption.toBack();
        this.toggle.toBack();
        return this;
    }

    @Override
    public final JavaFxToggle setCaption(String text) {
        this.caption.setText(text);
        return this;
    }

    @Override
    public final JavaFxToggle setCaptionUnderlined(boolean underlined) {
        this.caption.setUnderline(underlined);
        return this;
    }

    @Override
    public final JavaFxToggle setCaptionFont(WindowFont font) {
        this.caption.setFont(JavaFxFont.getById(font.getId()).getInnerFont());
        return this;
    }

    @Override
    public final JavaFxToggle setCaptionColor(Color color) {
        this.caption.setTextFill(new javafx.scene.paint.Color(
                color.normalizedRedValue,
                color.normalizedGreenValue,
                color.normalizedBlueValue,
                color.normalizedAlphaValue));
        return this;
    }
}
