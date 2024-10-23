/*
 This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 Copyright (c) 2019-2023 Grégory Van den Borre
 More infos available: https://engine.yildiz-games.be
 Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 documentation files (the "Software"), to deal in the Software without restriction, including without limitation
 the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to
 permit persons to whom the Software is furnished to do so, subject to the following conditions: The above copyright
 notice and this permission notice shall be included in all copies or substantial portions of the  Software.
 THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR
 OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.FullCoordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.audio.AudioEffect;
import be.yildizgames.module.window.input.KeyboardListener;
import be.yildizgames.module.window.javafx.audio.JavaFxAudioEffect;
import be.yildizgames.module.window.javafx.input.JavaFxMapperKeyPressed;
import be.yildizgames.module.window.javafx.input.JavaFxMapperKeyReleased;
import be.yildizgames.module.window.screen.ScreenSize;
import be.yildizgames.module.window.widget.DirectoryChooser;
import be.yildizgames.module.window.widget.OnMinimize;
import be.yildizgames.module.window.widget.WindowAlert;
import be.yildizgames.module.window.widget.WindowFileChooser;
import be.yildizgames.module.window.widget.WindowFont;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowNotification;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowShellOptions;
import be.yildizgames.module.window.widget.WindowState;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Pane;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.nio.file.Path;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.Random;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxWindowShell implements WindowShell {

    private static final Map<FontData, JavaFxFont> FONTS = new HashMap<>();

    private final Random random = new Random();

    private final WindowImageProvider imageProvider;

    private Stage stage;

    private String title;

    JavaFxWindowShell(Stage stage, WindowImageProvider imageProvider, String loadingImage, WindowShellOptions... options) {
        super();
        this.imageProvider = imageProvider;
        this.handleOptions(stage, loadingImage, options);
    }

    JavaFxWindowShell(WindowImageProvider imageProvider, WindowShellOptions... options) {
        this(new Stage(), imageProvider, "", options);
    }

    private void handleOptions(Stage stage, String loadingImage, WindowShellOptions... options) {
        this.stage = stage;
        var p = new Pane();
        var rootScene = new Scene(p);
        var primaryState = new JavaFxWindowState(p, this.imageProvider, this, rootScene);
        this.stage.setScene(rootScene);

        primaryState.setBackground(loadingImage);
        this.stage.show();
        if (options != null) {
            for (WindowShellOptions o : options) {
                if (o == WindowShellOptions.FULLSCREEN) {
                    this.stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    this.stage.setFullScreen(true);
                    this.stage.setMaximized(true);
                 //   this.updateSize(FullCoordinates.size((int) Screen.getPrimary().getBounds().getWidth(), (int) Screen.getPrimary().getBounds().getHeight()));
                } else if (o == WindowShellOptions.NO_TITLE_BAR) {
                    this.stage.initStyle(StageStyle.UNDECORATED);
                }
            }
        }
        this.title = "UnnamedWindow" + random.nextInt();
        this.stage.setTitle(this.title);
        if(!this.stage.isFullScreen()) {
            this.setSize(FullCoordinates.size((int) rootScene.getWidth(), (int) rootScene.getHeight()));
            this.setPosition(FullCoordinates.position((int) rootScene.getX(), (int) rootScene.getY()));
        }
    }

    @Override
    public final WindowState createState() {
        return new JavaFxWindowState(new Pane(), this.imageProvider, this, this.stage.getScene());
    }

     @Override
    public final WindowState createState(Path template) throws IOException  {
        return new JavaFxWindowState(FXMLLoader.load(template.toUri().toURL()), this.imageProvider, this, this.stage.getScene());
    }

    @Override
    public final WindowShell setVisible(boolean visible) {
        if (visible) {
            this.stage.show();
        } else {
            this.stage.hide();
        }
        return this;
    }

    @Override
    public final JavaFxWindowShell setTitle(String title) {
        this.title = title;
        this.stage.setTitle(title);
        return this;
    }

    @Override
    public final JavaFxWindowShell setIcon(String file) {
        this.stage.getIcons().add(new Image(file));
        return this;
    }

    @Override
    public final JavaFxWindowShell setFullScreen() {
        this.stage.setFullScreen(true);
        this.stage.setMaximized(true);
        return this;
    }

    @Override
    public final ScreenSize getSize() {
        return new ScreenSize(this.getCoordinates().getWidth(), this.getCoordinates().getHeight());
    }

    @Override
    public final ScreenSize getMonitorSize() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return new ScreenSize((int) screenBounds.getWidth(), (int) screenBounds.getHeight());
    }

    @Override
    public final void open() {
        //Does nothing, already opened at creation.
    }

    @Override
    public final void close() {
        this.stage.close();
    }

    @Override
    public final void update() {
        //hack to ensure to refresh the view in case of non full screen.
        // if the screen is not maximized/resized, the view is not correctly updated.
        this.stage.setHeight(this.stage.getHeight() - 1);
        this.stage.setHeight(this.stage.getHeight() + 1);
    }

    @Override
    public final WindowShell setCss(Path cssFile) {
        this.stage.getScene().getStylesheets().add("file:///" + cssFile.toAbsolutePath().toString().replace("\\", "/"));
        return this;
    }

    @Override
    public final void checkForEvent() {
        //Does nothing.
    }

    @Override
    public final WindowNotification createNotification() {
        this.update();
        return new JavaFxNotification();
    }

    @Override
    public final WindowFont createFont(String path, int height) {
        return FONTS.computeIfAbsent(new FontData(path, height), k -> new JavaFxFont(path, height));
    }

    @Override
    public final WindowShell setSize(Size size) {
        this.stage.setMaxHeight(size.getHeight());
        this.stage.setMinHeight(size.getHeight());
        this.stage.setMaxWidth(size.getWidth());
        this.stage.setMinWidth(size.getWidth());
        return this;
    }

    @Override
    public final WindowShell setPosition(Position position) {
        this.stage.setX(position.getLeft());
        this.stage.setY(position.getTop());

        return this;
    }

    @Override
    public final JavaFxWindowShell addKeyListener(KeyboardListener listener) {
        this.stage.getScene().setOnKeyPressed(new JavaFxMapperKeyPressed(listener));
        this.stage.getScene().setOnKeyReleased(new JavaFxMapperKeyReleased(listener));
        return this;
    }

    @Override
    public final AudioEffect createAudioEffect(String file) {
        return new JavaFxAudioEffect(file);
    }

    @Override
    public final JavaFxWindowShell toBack() {
        this.stage.toBack();
        return this;
    }

    @Override
    public final JavaFxWindowShell toFront() {
        this.stage.toFront();
        return this;
    }

    @Override
    public final JavaFxWindowShell minimize(OnMinimize... minimizes) {
        this.stage.setIconified(true);
        Optional.ofNullable(minimizes).ifPresent(m -> Arrays.stream(m).forEach(OnMinimize::minimized));

        return this;
    }

    @Override
    public final JavaFxWindowShell maximize() {
        this.run();
        return this;
    }

    @Override
    public final void exit() {
        this.stage.close();
        Platform.exit();
    }

    @Override
    public final Coordinates getCoordinates() {
        return FullCoordinates.full((int) this.stage.getWidth(), (int) this.stage.getHeight(), (int) this.stage.getX(), (int) this.stage.getY());
    }

    private void run() {
        this.stage.setIconified(false);
        this.stage.toFront();
    }

    @Override
    public WindowShell hide() {
        this.stage.hide();
        return this;
    }

    @Override
    public final DirectoryChooser createDirectoryChooser() {
        this.update();
        return new JavaFxDirectoryChooser(this.stage);
    }

    @Override
    public final WindowAlert createAlert() {
        this.update();
        return new JavaFxAlert(this.stage);
    }

    @Override
    public final WindowShell showCursor() {
        Platform.runLater(() -> this.stage.getScene().setCursor(Cursor.DEFAULT));
        return this;
    }

    @Override
    public final WindowShell hideCursor() {
        Platform.runLater(() -> this.stage.getScene().setCursor(javafx.scene.Cursor.NONE));
        return this;
    }

    @Override
    public final WindowFileChooser createFileChooser() {
        this.update();
        return new JavaFxFileChooser(this.stage);
    }

    ReadOnlyDoubleProperty widthProperty() {
        return this.stage.widthProperty();
    }

    private record FontData(String name, int height) {
    }
}
