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

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.WindowHandle;
import be.yildizgames.module.window.input.KeyboardListener;
import be.yildizgames.module.window.javafx.input.JavaFxMapperKeyPressed;
import be.yildizgames.module.window.javafx.input.JavaFxMapperKeyReleased;
import be.yildizgames.module.window.widget.OnMinimize;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowMenuBar;
import be.yildizgames.module.window.widget.WindowMenuBarElementDefinition;
import be.yildizgames.module.window.widget.WindowModal;
import be.yildizgames.module.window.widget.WindowModalFile;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowShellOptions;
import be.yildizgames.module.window.widget.WindowTextArea;
import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.Arrays;
import java.util.Optional;
import java.util.Random;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxWindowShell extends JavaFxBaseWidget<JavaFxWindowShell> implements WindowShell {

    private final WindowImageProvider imageProvider;

    private Stage stage;

    private Pane pane;

    private boolean increase;
    
    private String title;

    JavaFxWindowShell(Stage stage, WindowImageProvider imageProvider, WindowShellOptions... options) {
        super();
        this.stage = stage;
        this.imageProvider = imageProvider;
        this.handleOptions(stage, options);
    }

    JavaFxWindowShell(WindowImageProvider imageProvider, WindowShellOptions... options) {
        super();
        this.imageProvider = imageProvider;
        Platform.runLater(() -> createStage(options));
    }

    private void createStage(WindowShellOptions... options) {
        this.stage = new Stage();
        this.handleOptions(stage, options);
    }

    private void handleOptions(Stage stage, WindowShellOptions... options) {
        Platform.runLater(() -> {
            this.pane = new Pane();
            Scene scene = new Scene(pane);
            stage.setScene(scene);
            if(options!= null) {
                for (WindowShellOptions o : options) {
                    switch (o) {
                        case FULLSCREEN:
                            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                            stage.setFullScreen(true);
                            stage.setMaximized(true);
                            break;
                    }
                }
            }
            this.stage.show();
            Random random = new Random();
            this.title = "UnnamedWindow" + random.nextInt();
            this.stage.setTitle(this.title);
            this.setReady(this.pane);
        });
    }

    @Override
    public JavaFxWindowShell setTitle(String title) {
        this.title = title;
        Platform.runLater(() -> stage.setTitle(title));
        return this;
    }

    @Override
    public JavaFxWindowShell setIcon(String file) {
        return null;
    }

    @Override
    public JavaFxWindowShell setBackground(Color color) {

        return null;
    }

    @Override
    public JavaFxWindowShell setBackground(String file) {
        this.runWhenReady(() -> {
            BackgroundImage myBI= new BackgroundImage(new Image(this.imageProvider.getImage(file)),
                    BackgroundRepeat.REPEAT, BackgroundRepeat.NO_REPEAT, BackgroundPosition.DEFAULT,
                    BackgroundSize.DEFAULT);
            this.pane.setBackground(new Background(myBI));
        });
        return this;
    }

    @Override
    public JavaFxWindowShell setSize(int width, int height) {
        return null;
    }

    @Override
    public JavaFxWindowShell setFullScreen() {
        return null;
    }

    @Override
    public ScreenSize getSize() {
        return null;
    }

    @Override
    public ScreenSize getMonitorSize() {
        return null;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public void update() {
        this.runWhenReady(() -> {
            //hack to ensure to refresh the view in case of non full screen.
            // if the screen is not maximized/resized, the view is not correctly updated.
            this.stage.setHeight(this.stage.getHeight() + (increase ? 1 : -1));
            increase = ! increase;
        });
    }

    @Override
    public void checkForEvent() {

    }

    @Override
    public WindowModal createMessageBox() {
        return null;
    }

    @Override
    public WindowModal createMessageButtonBox() {
        return null;
    }

    @Override
    public WindowTextArea createTextArea() {
        return null;
    }

    @Override
    public final JavaFxCanvas createCanvas() {
        this.runWhenReady(this::update);
        HWND hWnd = User32.INSTANCE.GetForegroundWindow();
        return new JavaFxCanvas(this.pane, new WindowHandle(Pointer.nativeValue(hWnd.getPointer())));
    }

    @Override
    public JavaFxLabel createTextLine() {
        this.runWhenReady(this::update);
        return new JavaFxLabel(this.pane);
    }

    @Override
    public JavaFxButton createButton() {
        this.runWhenReady(this::update);
        return new JavaFxButton(this.pane);
    }

    @Override
    public JavaFxButton createButton(String background, String hover) {
        return null;
    }

    @Override
    public JavaFxImage createImage(String image) {
        this.runWhenReady(this::update);
        return new JavaFxImage(this.pane, this.imageProvider, image);
    }

    @Override
    public JavaFxProgressBar createProgressBar() {
        this.runWhenReady(this::update);
        return new JavaFxProgressBar(this.pane);
    }

    @Override
    public WindowTreeRoot createTreeRoot(int width, int height, WindowTreeElement... elements) {
        return null;
    }

    @Override
    public JavaFxDropDown createDropdown() {
        this.runWhenReady(this::update);
        return new JavaFxDropDown(this.pane);
    }

    @Override
    public WindowButtonText createTextButton() {
        return null;
    }

    @Override
    public JavaFxInputBox createInputBox() {
        this.runWhenReady(this::update);
        return new JavaFxInputBox(this.pane);
    }

    @Override
    public JavaFxWindowShell createChildWindow() {
        this.runWhenReady(this::update);
        return new JavaFxWindowShell(this.imageProvider);
    }

    @Override
    public WindowMenuBar createMenuBar(WindowMenuBarElementDefinition... elements) {
        return null;
    }

    @Override
    public WindowModalFile createOpenFileBox() {
        return null;
    }

    @Override
    public JavaFxFont createFont(String path, int height) {
        return new JavaFxFont(path, height);
    }

    @Override
    public final JavaFxWindowShell setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
        this.runWhenReady(() -> {
            this.pane.setLayoutX(coordinates.left);
            this.pane.setLayoutY(coordinates.top);
            this.pane.setMaxHeight(coordinates.height);
            this.pane.setMinHeight(coordinates.height);
            this.pane.setMaxWidth(coordinates.width);
            this.pane.setMinWidth(coordinates.width);
        });
        return this;
    }

    @Override
    public final JavaFxWindowShell setSize(Size size) {
        this.updateCoordinates(size);
        this.runWhenReady(() -> {
            this.pane.setMaxHeight(size.height);
            this.pane.setMinHeight(size.height);
            this.pane.setMaxWidth(size.width);
            this.pane.setMinWidth(size.width);
        });
        return this;
    }

    @Override
    public final JavaFxWindowShell setPosition(Position position) {
        this.updateCoordinates(position);
        this.runWhenReady(() -> {
            this.pane.setLayoutX(position.left);
            this.pane.setLayoutY(position.top);
        });
        return this;
    }

    @Override
    public final JavaFxWindowShell addKeyListener(KeyboardListener listener) {
        this.runWhenReady(
                () -> {
                    this.stage.getScene().setOnKeyPressed(new JavaFxMapperKeyPressed(listener));
                    this.stage.getScene().setOnKeyReleased(new JavaFxMapperKeyReleased(listener));
                }
        );
        return this;
    }

    @Override
    public final JavaFxWindowShell toBack() {
        this.runWhenReady(() -> this.stage.toBack());
        return this;
    }

    @Override
    public final JavaFxWindowShell minimize(OnMinimize... minimizes) {
        this.runWhenReady(() -> {
            this.stage.setIconified(true);
            Optional.ofNullable(minimizes).ifPresent(m -> Arrays.stream(m).forEach(OnMinimize::minimized));
        });
        return this;
    }

    @Override
    public final JavaFxWindowShell maximize() {
        this.runWhenReady(() -> {
            this.stage.setIconified(false);
        });
        return this;
    }

    @Override
    public final JavaFxMediaPlayer createMediaPlayer() {
        this.runWhenReady(this::update);
        return new JavaFxMediaPlayer(this.pane);
    }

    @Override
    public final void exit() {
        Platform.exit();
    }

}
