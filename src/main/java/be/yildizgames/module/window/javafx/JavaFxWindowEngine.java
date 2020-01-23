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
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.WindowHandle;
import be.yildizgames.module.window.WindowThreadManager;
import be.yildizgames.module.window.input.WindowInputListener;
import be.yildizgames.module.window.javafx.widget.JavaFxWindowShell;
import be.yildizgames.module.window.javafx.widget.JavaFxWindowShellFactory;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowImageProviderClassPath;
import be.yildizgames.module.window.widget.WindowShellFactory;
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

    private final List<JavaFxRegisteredView> views = new ArrayList<>();

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

    public void registerView(JavaFxRegisteredView view) {
        this.views.add(view);
    }

    @Override
    public void update() {
        if(!started) {
            Platform.startup(() -> {
                Application application = new Application() {

                    @Override
                    public void start(Stage stage) {
                        JavaFxWindowShellFactory factory = new JavaFxWindowShellFactory(imageProvider, stage);
                        for(JavaFxRegisteredView view : views) {
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
    }

    @Override
    public void deleteLoadingResources() {

    }

    @Override
    public WindowHandle getHandle() {
        return null;
    }

    @Override
    public void registerInput(WindowInputListener listener) {

    }

    @Override
    public Cursor createCursor(Cursor cursor) {
        return null;
    }

    @Override
    public void setWindowTitle(String title) {

    }

    @Override
    public void setCursor(Cursor cursor) {

    }

    @Override
    public void showCursor() {

    }

    @Override
    public void hideCursor() {
        //JavaFxApplication.instance.stage.getScene().setCursor(javafx.scene.Cursor.NONE);
    }

    @Override
    public ScreenSize getScreenSize() {
        throw new IllegalArgumentException("Do not use it");
    }

    @Override
    public void setWindowIcon(String file) {
    }

    @Override
    public WindowShellFactory getWindowShellFactory() {
        throw new IllegalArgumentException("Do not use it");
    }

    @Override
    public WindowThreadManager getThreadManager() {
        return this.threadManager;
    }
}
