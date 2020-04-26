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

package be.yildizgames.module.window.javafx;

import be.yildizgames.module.window.BaseWindowEngine;
import be.yildizgames.module.window.Cursor;
import be.yildizgames.module.window.RegisteredView;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.WindowHandle;
import be.yildizgames.module.window.WindowThreadManager;
import be.yildizgames.module.window.input.WindowInputListener;
import be.yildizgames.module.window.javafx.widget.JavaFxWindowShell;
import be.yildizgames.module.window.javafx.widget.JavaFxWindowShellFactory;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowImageProviderClassPath;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxWindowEngine implements BaseWindowEngine {

    private final WindowThreadManager threadManager = new JavaFxThreadManager();

    private final List<RegisteredView> views = new ArrayList<>();

    private final WindowImageProvider imageProvider;

    private boolean started;

    public JavaFxWindowEngine() {
        this(new WindowImageProviderClassPath());
    }

    public JavaFxWindowEngine(WindowImageProvider imageProvider) {
        super();
        System.Logger logger = System.getLogger(JavaFxWindowShell.class.getName());
        logger.log(System.Logger.Level.INFO, "Window Engine JavaFx implementation initializing...");
        logger.log(System.Logger.Level.INFO, "Window Engine JavaFx implementation initialized.");
        this.imageProvider = imageProvider;
    }

    @Override
    public final JavaFxWindowEngine registerView(RegisteredView view) {
        this.views.add(view);
        return this;
    }

    @Override
    public final JavaFxWindowEngine update() {
        if(!started) {
            Platform.startup(() -> {
                Application application = new Application() {

                    @Override
                    public void start(Stage stage) {
                        JavaFxWindowShellFactory factory = new JavaFxWindowShellFactory(imageProvider, stage);
                        for(RegisteredView view : views) {
                            view.build(factory);
                        }
                        started = true;
                    }
                };
                try {
                    application.init();
                    application.start(new Stage());
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        return this;
    }

    @Override
    public final JavaFxWindowEngine deleteLoadingResources() {
        return this;
    }

    @Override
    public final WindowHandle getHandle() {
        //FIXME IMPLEMENT!
        return new WindowHandle(0);
    }

    @Override
    public final JavaFxWindowEngine registerInput(WindowInputListener listener) {
        //FIXME IMPLEMENT!
        return this;
    }

    @Override
    public final Cursor createCursor(Cursor cursor) {
        //FIXME IMPLEMENT!
        return null;
    }

    @Override
    public final JavaFxWindowEngine setWindowTitle(String title) {
        //FIXME IMPLEMENT!
        return this;
    }

    @Override
    public final JavaFxWindowEngine setCursor(Cursor cursor) {
        //FIXME IMPLEMENT!
        return this;
    }

    @Override
    public final JavaFxWindowEngine showCursor() {
        //FIXME IMPLEMENT!
        return this;
    }

    @Override
    public final JavaFxWindowEngine hideCursor() {
        //FIXME IMPLEMENT!
        //JavaFxApplication.instance.stage.getScene().setCursor(javafx.scene.Cursor.NONE);
        return this;
    }

    @Override
    public final ScreenSize getScreenSize() {
        //FIXME IMPLEMENT!
        throw new IllegalArgumentException("Do not use it");
    }

    @Override
    public final JavaFxWindowEngine setWindowIcon(String file) {
        //FIXME IMPLEMENT!
        return this;
    }

    @Override
    public final WindowThreadManager getThreadManager() {
        return this.threadManager;
    }
}
