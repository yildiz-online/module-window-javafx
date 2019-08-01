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
import be.yildizgames.module.window.input.KeyboardListener;
import be.yildizgames.module.window.javafx.input.JavaFxKeyMapper;
import be.yildizgames.module.window.widget.WindowButton;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowDropdown;
import be.yildizgames.module.window.widget.WindowFont;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowInputBox;
import be.yildizgames.module.window.widget.WindowMenuBar;
import be.yildizgames.module.window.widget.WindowMenuBarElementDefinition;
import be.yildizgames.module.window.widget.WindowModal;
import be.yildizgames.module.window.widget.WindowModalFile;
import be.yildizgames.module.window.widget.WindowProgressBar;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowShellOptions;
import be.yildizgames.module.window.widget.WindowTextArea;
import be.yildizgames.module.window.widget.WindowTextLine;
import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxWindowShell extends JavaFxBaseWidget implements WindowShell {

    private final WindowImageProvider imageProvider;

    private Stage stage;

    private Pane pane;

    JavaFxWindowShell(Stage stage, WindowImageProvider imageProvider, WindowShellOptions... options) {
        this.stage = stage;
        this.imageProvider = imageProvider;
        this.handleOptions(stage, options);
    }

    JavaFxWindowShell(WindowImageProvider imageProvider, WindowShellOptions... options) {
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
            this.setReady();
        });
    }

    @Override
    public WindowShell setTitle(String title) {
        Platform.runLater(() -> stage.setTitle(title));
        return this;
    }

    @Override
    public WindowShell setIcon(String file) {
        return null;
    }

    @Override
    public WindowShell setBackground(Color color) {

        return null;
    }

    @Override
    public WindowShell setBackground(String file) {
        return null;
    }

    @Override
    public WindowShell setSize(int width, int height) {
        return null;
    }

    @Override
    public WindowShell setFullScreen() {
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
    public WindowTextLine createTextLine() {
        this.runWhenReady(() -> {});
        return new JavaFxLabel(this.pane);
    }

    @Override
    public WindowButton createButton() {
        return null;
    }

    @Override
    public WindowButton createButton(String background, String hover) {
        return null;
    }

    @Override
    public WindowImage createImage(String image) {
        this.runWhenReady(() -> {});
        return new JavaFxImage(this.pane, this.imageProvider, image);
    }

    @Override
    public WindowProgressBar createProgressBar() {
        return null;
    }

    @Override
    public WindowTreeRoot createTreeRoot(int width, int height, WindowTreeElement... elements) {
        return null;
    }

    @Override
    public WindowDropdown createDropdown() {
        return null;
    }

    @Override
    public WindowButtonText createTextButton() {
        return null;
    }

    @Override
    public WindowInputBox createInputBox() {
        return null;
    }

    @Override
    public WindowShell createChildWindow() {
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
    public WindowFont createFont(String path, int height) {
        return new JavaFxFont(path, height);
    }

    @Override
    public WindowShell setCoordinates(Coordinates coordinates) {
        return null;
    }

    @Override
    public WindowShell setSize(Size size) {
        return null;
    }

    @Override
    public WindowShell setPosition(Position position) {
        return null;
    }

    @Override
    public WindowShell setVisible(boolean visible) {
        return null;
    }

    @Override
    public int getLeft() {
        return 0;
    }

    @Override
    public int getRight() {
        return 0;
    }

    @Override
    public int getTop() {
        return 0;
    }

    @Override
    public int getBottom() {
        return 0;
    }

    public WindowShell addKeyListener(KeyboardListener listener) {
        this.runWhenReady(
                () -> this.stage.getScene().setOnKeyPressed(new JavaFxKeyMapper(listener))
        );
        return this;
    }

    public WindowShell toBack() {
        this.runWhenReady(() -> this.stage.toBack());
        return this;
    }

}
