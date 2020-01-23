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
import be.yildizgames.module.window.widget.WindowProgressBar;
import javafx.scene.control.ProgressBar;
import javafx.scene.layout.Pane;

/**
 * @author Grégory Van den Borre
 */
class JavaFxProgressBar extends JavaFxBaseWidget<JavaFxProgressBar> implements WindowProgressBar {

    private ProgressBar progressBar;

    JavaFxProgressBar(Pane pane) {
        super();
            this.progressBar = new ProgressBar();
            pane.getChildren().add(this.progressBar);
            this.setReady(this.progressBar);

    }

    @Override
    public WindowProgressBar setProgress(int progress) {
        this.progressBar.setProgress(progress);
        return this;
    }

    @Override
    public WindowProgressBar setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
            this.progressBar.setLayoutX(coordinates.left);
            this.progressBar.setLayoutY(coordinates.top);
            this.progressBar.setMaxHeight(coordinates.height);
            this.progressBar.setMinHeight(coordinates.height);
            this.progressBar.setMaxWidth(coordinates.width);
            this.progressBar.setMinWidth(coordinates.width);

        return this;
    }

    @Override
    public WindowProgressBar setSize(Size size) {
        this.updateCoordinates(size);
            this.progressBar.setMaxHeight(size.height);
            this.progressBar.setMinHeight(size.height);
            this.progressBar.setMaxWidth(size.width);
            this.progressBar.setMinWidth(size.width);

        return this;
    }

    @Override
    public WindowProgressBar setPosition(Position position) {
        this.updateCoordinates(position);
            this.progressBar.setLayoutX(position.left);
            this.progressBar.setLayoutY(position.top);

        return this;
    }
}
