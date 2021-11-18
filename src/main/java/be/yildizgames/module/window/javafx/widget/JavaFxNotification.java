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

import be.yildizgames.module.window.widget.WindowNotification;
import javafx.geometry.Pos;
import org.controlsfx.control.Notifications;

import java.time.Duration;

/**
 * @author Grégory Van den Borre
 */
class JavaFxNotification implements WindowNotification {

    private final Notifications notification;

    JavaFxNotification() {
        super();
        this.notification = Notifications.create();
        this.notification.position(Pos.TOP_RIGHT);
    }

    @Override
    public void show() {
        this.notification.show();
    }

    @Override
    public void showError() {
        this.notification.showError();
    }

    @Override
    public void showWarning() {
        this.notification.showWarning();
    }

    @Override
    public void showInfo() {
        this.notification.showInformation();
    }

    @Override
    public WindowNotification setTitle(String title) {
        this.notification.title(title);
        return this;
    }

    @Override
    public WindowNotification setText(String text) {
        this.notification.text(text);
        return this;
    }

    @Override
    public WindowNotification hideAfter(Duration duration) {
        this.notification.hideAfter(new javafx.util.Duration(duration.toMillis()));
        return this;
    }

}
