/*
 * MIT License
 *
 * Copyright (c) 2021 Grégory Van den Borre
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.widget.WindowNotificationPane;
import javafx.scene.layout.Pane;
import org.controlsfx.control.NotificationPane;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxNotificationPane extends JavaFxBaseWidget<JavaFxNotificationPane> implements WindowNotificationPane {

    private final NotificationPane notificationPane;

    public JavaFxNotificationPane(final Pane pane) {
        super();
        this.notificationPane = new NotificationPane();
        pane.getChildren().add(this.notificationPane);
        this.setReady(this.notificationPane);
        notificationPane.show("test");
    }

    @Override
    public WindowNotificationPane setCoordinates(Coordinates coordinates) {
        return this;
    }

    @Override
    public WindowNotificationPane setSize(Size size) {
        return this;
    }

    @Override
    public WindowNotificationPane setPosition(Position position) {
        return this;
    }
}
