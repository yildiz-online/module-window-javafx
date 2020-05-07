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

import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.widget.WindowInputBox;
import be.yildizgames.module.window.widget.WindowWidgetChangeListener;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.Pane;

/**
 * @author Grégory Van den Borre
 */
class JavaFxInputBox extends JavaFxBaseWidget<JavaFxInputBox> implements WindowInputBox {

    private final TextField textField;

    JavaFxInputBox(Pane pane) {
        super();
        this.textField = new TextField();
        pane.getChildren().add(this.textField);
        this.setReady(this.textField);
    }


    @Override
    public final WindowInputBox setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
        this.textField.setLayoutX(coordinates.left);
        this.textField.setLayoutY(coordinates.top);
        this.textField.setMaxHeight(coordinates.height);
        this.textField.setMinHeight(coordinates.height);
        this.textField.setMaxWidth(coordinates.width);
        this.textField.setMinWidth(coordinates.width);

        return this;
    }

    @Override
    public final WindowInputBox setSize(Size size) {
        this.updateCoordinates(size);
        this.textField.setMaxHeight(size.height);
        this.textField.setMinHeight(size.height);
        this.textField.setMaxWidth(size.width);
        this.textField.setMinWidth(size.width);

        return this;
    }

    @Override
    public final WindowInputBox setPosition(Position position) {
        this.updateCoordinates(position);
        this.textField.setLayoutX(position.left);
        this.textField.setLayoutY(position.top);

        return this;
    }

    @Override
    public final WindowInputBox setText(String text) {
        this.textField.setText(text);
        return this;
    }

    @Override
    public final String getText() {
        return this.textField.getText();
    }

    @Override
    public final WindowInputBox setToolTip(String text) {
        Tooltip tooltip = new Tooltip();
        tooltip.setText(text);
        this.textField.setTooltip(tooltip);

        return this;
    }

    @Override
    public final WindowInputBox onChange(WindowWidgetChangeListener<String> l) {
        this.textField.textProperty().addListener((observableValue, s, t1) -> l.changed(t1));
        return this;
    }
}
