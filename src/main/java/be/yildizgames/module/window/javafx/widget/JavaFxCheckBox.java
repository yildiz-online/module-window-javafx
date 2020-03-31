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
import be.yildizgames.module.window.widget.WindowCheckBox;
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
        this.caption.setMaxWidth(100);
        this.caption.setMaxHeight(30);
        this.setReady(this.checkBox);
    }

    @Override
    public boolean isChecked() {
        return this.checkBox.isSelected();
    }

    @Override
    public JavaFxCheckBox check() {
        this.checkBox.setSelected(true);
        return this;
    }

    @Override
    public JavaFxCheckBox uncheck() {
        this.checkBox.setSelected(false);
        return this;
    }

    @Override
    public final WindowCheckBox setCaption(String text) {
        this.caption.setText(text);
        return this;
    }

    @Override
    public final JavaFxCheckBox setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
        this.checkBox.setLayoutX(coordinates.left);
        this.checkBox.setLayoutY(coordinates.top);
        this.checkBox.setMaxHeight(coordinates.height);
        this.checkBox.setMinHeight(coordinates.height);
        this.checkBox.setMaxWidth(coordinates.width);
        this.checkBox.setMinWidth(coordinates.width);
        this.caption.setLayoutX(coordinates.left + 30);
        this.caption.setLayoutY(coordinates.top);
        return this;
    }

    @Override
    public WindowCheckBox setSize(Size size) {
        this.updateCoordinates(size);
        this.checkBox.setMaxHeight(size.height);
        this.checkBox.setMinHeight(size.height);
        this.checkBox.setMaxWidth(size.width);
        this.checkBox.setMinWidth(size.width);
        return this;
    }

    @Override
    public WindowCheckBox setPosition(Position position) {
        this.updateCoordinates(position);
        this.checkBox.setLayoutX(position.left);
        this.checkBox.setLayoutY(position.top);
        this.caption.setLayoutX(position.left + 30);
        this.caption.setLayoutY(position.top);

        return this;
    }

}
