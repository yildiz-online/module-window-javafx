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

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.widget.WidgetEvent;
import be.yildizgames.module.window.widget.WindowDropdown;
import be.yildizgames.module.window.widget.WindowWidgetChangeListener;
import javafx.collections.FXCollections;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.Pane;

import java.util.Arrays;

class JavaFxDropDown extends JavaFxBaseWidget<JavaFxDropDown> implements WindowDropdown {

    private final ComboBox<String> comboBox;

    JavaFxDropDown(Pane pane) {
        super();
        this.comboBox = new ComboBox<>();
        pane.getChildren().add(this.comboBox);
        this.setReady(this.comboBox);

    }

    @Override
    public final JavaFxDropDown select(int line) {
        this.comboBox.getSelectionModel().select(line);
        return this;
    }

    @Override
    public final JavaFxDropDown setItems(Object... items) {
        this.comboBox.setItems(FXCollections.observableArrayList(Arrays.stream(items).map(Object::toString).toArray(String[]::new)));
        return this;
    }

    @Override
    public final JavaFxDropDown setItems(String... items) {
        this.comboBox.setItems(FXCollections.observableArrayList(items));
        return this;
    }

    @Override
    public final int getSelectionIndex() {
        return this.comboBox.getSelectionModel().getSelectedIndex();
    }

    @Override
    public final JavaFxDropDown setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
        this.comboBox.setLayoutX(coordinates.getLeft());
        this.comboBox.setLayoutY(coordinates.getTop());
        this.comboBox.setMaxHeight(coordinates.getHeight());
        this.comboBox.setMinHeight(coordinates.getHeight());
        this.comboBox.setMaxWidth(coordinates.getWidth());
        this.comboBox.setMinWidth(coordinates.getWidth());

        return this;
    }

    @Override
    public final JavaFxDropDown setSize(Size size) {
        this.updateSize(size);
        this.comboBox.setMaxHeight(size.getHeight());
        this.comboBox.setMinHeight(size.getHeight());
        this.comboBox.setMaxWidth(size.getWidth());
        this.comboBox.setMinWidth(size.getWidth());

        return this;
    }

    @Override
    public final JavaFxDropDown setPosition(Position position) {
        this.updatePosition(position);
        this.comboBox.setLayoutX(position.getLeft());
        this.comboBox.setLayoutY(position.getTop());

        return this;
    }

    @Override
    public final JavaFxDropDown onChange(WindowWidgetChangeListener<Integer> listener) {
        this.comboBox.getSelectionModel().selectedIndexProperty().addListener((observableValue, oldValue, newValue) -> listener.changed(newValue.intValue()));
        return this;
    }

    @Override
    public final JavaFxDropDown fireEvent(WidgetEvent event) {
        if (event == WidgetEvent.MOUSE_LEFT_CLICK) {
            this.comboBox.show();
        }
        return this;
    }
}
