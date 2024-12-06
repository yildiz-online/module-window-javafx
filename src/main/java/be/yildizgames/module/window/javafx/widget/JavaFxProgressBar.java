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
import be.yildizgames.module.window.widget.WindowProgressBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

/**
 * @author Grégory Van den Borre
 */
class JavaFxProgressBar extends JavaFxBaseWidget<JavaFxProgressBar> implements WindowProgressBar {

    private final ProgressBar progressBar;

    JavaFxProgressBar(Pane pane) {
        super();
        this.progressBar = new ProgressBar();
        pane.getChildren().add(this.progressBar);
        this.setReady(this.progressBar);

    }

    @Override
    public WindowProgressBar setProgress(int progress) {
        this.progressBar.setProgress(progress * 0.01);
        return this;
    }

    @Override
    public WindowProgressBar setCoordinates(Coordinates coordinates) {
        this.progressBar.setLayoutX(coordinates.getLeft());
        this.progressBar.setLayoutY(coordinates.getTop());
        this.progressBar.setMaxHeight(coordinates.getHeight());
        this.progressBar.setMinHeight(coordinates.getHeight());
        this.progressBar.setMaxWidth(coordinates.getWidth());
        this.progressBar.setMinWidth(coordinates.getWidth());

        return this;
    }

    @Override
    public WindowProgressBar setSize(Size size) {
        this.progressBar.setMaxHeight(size.getHeight());
        this.progressBar.setMinHeight(size.getHeight());
        this.progressBar.setMaxWidth(size.getWidth());
        this.progressBar.setMinWidth(size.getWidth());

        return this;
    }

    @Override
    public WindowProgressBar setPosition(Position position) {
        this.progressBar.setLayoutX(position.getLeft());
        this.progressBar.setLayoutY(position.getTop());

        return this;
    }

    @Override
    public WindowProgressBar toFront() {
        this.progressBar.toFront();
        return this;
    }

    @Override
    public WindowProgressBar toBack() {
        this.progressBar.toBack();
        return this;
    }
}
