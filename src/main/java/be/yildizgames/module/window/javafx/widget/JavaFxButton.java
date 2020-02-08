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
import be.yildizgames.module.window.input.MouseLeftClickListener;
import be.yildizgames.module.window.javafx.input.JavaFxMapperMouseLeftClick;
import be.yildizgames.module.window.widget.WindowButton;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowImageProvider;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;

/**
 * @author Grégory Van den Borre
 */
class JavaFxButton extends JavaFxBaseWidget<JavaFxButton> implements WindowButton, WindowButtonText {

    private final WindowImageProvider imageProvider;

    private final Button button;

    JavaFxButton(final Pane pane, WindowImageProvider imageProvider) {
        super();
            this.button = new Button();
            this.imageProvider = imageProvider;
            pane.getChildren().add(this.button);
            this.setReady(this.button);

    }


    @Override
    public WindowButton setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
        this.button.setLayoutX(coordinates.left);
            this.button.setLayoutY(coordinates.top);
            this.button.setMaxHeight(coordinates.height);
            this.button.setMinHeight(coordinates.height);
            this.button.setMaxWidth(coordinates.width);
            this.button.setMinWidth(coordinates.width);

        return this;
    }

    @Override
    public WindowButton setSize(Size size) {
        this.updateCoordinates(size);
            this.button.setMaxHeight(size.height);
            this.button.setMinHeight(size.height);
            this.button.setMaxWidth(size.width);
            this.button.setMinWidth(size.width);

        return this;
    }

    @Override
    public WindowButton setPosition(Position position) {
        this.updateCoordinates(position);
            this.button.setLayoutX(position.left);
            this.button.setLayoutY(position.top);

        return this;
    }

    @Override
    public WindowButton addMouseLeftClickListener(MouseLeftClickListener l) {
        this.button.setOnAction(new JavaFxMapperMouseLeftClick(l));
        return this;
    }

    @Override
    public WindowButton setBackground(String image) {
        BackgroundImage myBI= new BackgroundImage(new Image(this.imageProvider.getImage(image)),
                BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        this.button.setBackground(new Background(myBI));
        return this;
    }

    @Override
    public WindowButtonText setText(String text) {
         this.button.setText(text);
        return this;
    }

}
