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

import be.yildizgames.module.window.widget.WindowPopup;
import be.yildizgames.module.window.widget.WindowTextLine;
import javafx.application.Platform;
import javafx.stage.Popup;
import javafx.stage.Stage;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxPopup implements WindowPopup {

    private final Popup popup = new Popup();

    private boolean ready = false;


    public JavaFxPopup(Stage parent) {
        super();
        Platform.runLater(() -> {
            popup.centerOnScreen();
            popup.show(parent);
            this.ready = true;
        });
    }

    protected final void runWhenReady(Runnable r) {
        while (!ready) {
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        Platform.runLater(r);
    }


    @Override
    public final WindowTextLine createTextLine() {
       return null;
    }
}
