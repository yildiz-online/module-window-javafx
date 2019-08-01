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
import be.yildizgames.module.window.widget.WindowDropdown;
import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.util.Arrays;

class JavaFxDropDown extends JavaFxBaseWidget<JavaFxDropDown> implements WindowDropdown {

    private ComboBox comboBox;

    JavaFxDropDown(Pane pane) {
        super();
        Platform.runLater(() -> {
            this.comboBox = new ComboBox();
            pane.getChildren().add(this.comboBox);
            this.setReady(this.comboBox);
        });
    }

    @Override
    public WindowDropdown select(int line) {
        return null;
    }

    @Override
    public WindowDropdown setItems(Object... items) {
        this.runWhenReady(() -> {
            this.comboBox.setItems(FXCollections.observableArrayList(Arrays.stream(items).map(Object::toString).toArray(String[]::new)));
        });
        return this;
    }

    @Override
    public WindowDropdown setItems(String... items) {
        this.runWhenReady(() -> {
            this.comboBox.setItems(FXCollections.observableArrayList(items));
        });
        return this;
    }

    @Override
    public int getSelectionIndex() {
        return 0;
    }

    @Override
    public WindowDropdown setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
        this.runWhenReady(() -> {
            this.comboBox.setLayoutX(coordinates.left);
            this.comboBox.setLayoutY(coordinates.top);
            this.comboBox.setMaxHeight(coordinates.height);
            this.comboBox.setMinHeight(coordinates.height);
            this.comboBox.setMaxWidth(coordinates.width);
            this.comboBox.setMinWidth(coordinates.width);
        });
        return this;
    }

    @Override
    public WindowDropdown setSize(Size size) {
        this.updateCoordinates(size);
        this.runWhenReady(() -> {
            this.comboBox.setMaxHeight(size.height);
            this.comboBox.setMinHeight(size.height);
            this.comboBox.setMaxWidth(size.width);
            this.comboBox.setMinWidth(size.width);
        });
        return this;
    }

    @Override
    public WindowDropdown setPosition(Position position) {
        this.updateCoordinates(position);
        this.runWhenReady(() -> {
            this.comboBox.setLayoutX(position.left);
            this.comboBox.setLayoutY(position.top);
        });
        return this;
    }
}
