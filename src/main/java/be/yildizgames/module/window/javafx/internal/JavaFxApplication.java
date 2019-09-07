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

package be.yildizgames.module.window.javafx.internal;

import be.yildizgames.module.window.ScreenSize;
import javafx.application.Application;
import javafx.stage.Stage;

/**
 * This class is internal, do not instantiate it manually nor use it.
 * This package is hidden when using modules, anyway without it this class is visible.
 * @author Grégory Van den Borre
 */
public class JavaFxApplication extends Application {

    private static JavaFxApplication instance;

    private Stage stage;

    public JavaFxApplication() {
        super();
    }

    public Stage getStage() {
        return stage;
    }

    public static JavaFxApplication getInstance() {
        if(instance == null) {
            try {
                Thread.sleep(200);
                return getInstance();
            } catch (InterruptedException e) {
                Thread.currentThread().interrupt();
            }
        }
        return instance;
    }

    @Override
    public final void start(Stage stage) {
        instance = this;
        this.stage = stage;
        stage.show();
    }

    public final void setTitle(String title) {
        this.stage.setTitle(title);
    }

    public final ScreenSize getScreenSize() {
        return new ScreenSize((int)this.stage.getWidth(), (int)this.stage.getHeight());
    }
}
