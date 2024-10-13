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

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.FullCoordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.audio.AudioEffect;
import be.yildizgames.module.window.input.KeyboardListener;
import be.yildizgames.module.window.javafx.audio.JavaFxAudioEffect;
import be.yildizgames.module.window.javafx.input.JavaFxMapperKeyPressed;
import be.yildizgames.module.window.javafx.input.JavaFxMapperKeyReleased;
import be.yildizgames.module.window.javafx.widget.experimental.CallBack;
import be.yildizgames.module.window.javafx.widget.experimental.JavaFxVirtualKeyboard;
import be.yildizgames.module.window.screen.ScreenSize;
import be.yildizgames.module.window.widget.*;
import javafx.application.Platform;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
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
public class JavaFxWindowShell extends JavaFxBaseWidget<JavaFxWindowShell> implements WindowShell {

    private static final Map<FontData, JavaFxFont> FONTS = new HashMap<>();

    private final Random random = new Random();

    private final WindowImageProvider imageProvider;

    private StateName activeState;

    private Stage stage;

    private final Map<String, Pane> states = new HashMap<>();

    private String title;

    private String background = "";

    JavaFxWindowShell(Stage stage, WindowImageProvider imageProvider, WindowShellOptions... options) {
        this(stage, imageProvider, "", options);
    }

    JavaFxWindowShell(Stage stage, WindowImageProvider imageProvider, String loadingImage, WindowShellOptions... options) {
        super();
        this.imageProvider = imageProvider;
        this.handleOptions(stage, loadingImage, options);
    }

    JavaFxWindowShell(WindowImageProvider imageProvider, WindowShellOptions... options) {
        this(new Stage(), imageProvider, options);
    }

    JavaFxWindowShell(WindowImageProvider imageProvider, Stage parent) {
        super();
        this.imageProvider = imageProvider;
        this.stage = new Stage();
        var primaryState = new StateName("primary");
        this.addState(primaryState);
        this.stage.setScene(new Scene(this.states.get(primaryState.name())));
        this.selectState(primaryState);
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.initOwner(parent);
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.show();
        this.stage.setTitle("Modal");
        this.setReady(this.states.get(this.activeState.name()));
    }

    private void handleOptions(Stage stage, String loadingImage, WindowShellOptions... options) {
        this.stage = stage;
        var primaryState = new StateName("primary");
        this.addState(primaryState);
        this.stage.setScene(new Scene(this.states.get(primaryState.name())));
        this.selectState(primaryState);
        this.setBackground(loadingImage);
        this.stage.show();
        if (options != null) {
            for (WindowShellOptions o : options) {
                if (o == WindowShellOptions.FULLSCREEN) {
                    this.stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                    this.stage.setFullScreen(true);
                    this.stage.setMaximized(true);
                    this.updateSize(FullCoordinates.size((int) Screen.getPrimary().getBounds().getWidth(), (int) Screen.getPrimary().getBounds().getHeight()));
                } else if (o == WindowShellOptions.NO_TITLE_BAR) {
                    this.stage.initStyle(StageStyle.UNDECORATED);
                }
            }
        }
        this.title = "UnnamedWindow" + random.nextInt();
        this.stage.setTitle(this.title);
        this.setReady(this.states.get(this.activeState.name()));
        if(!this.stage.isFullScreen()) {
            var scene = this.states.get(this.activeState.name()).getScene();
            this.setSize((int) scene.getWidth(), (int) scene.getHeight());
            this.setPosition((int) scene.getX(), (int) scene.getY());
        }
    }

    @Override
    public final void addState(StateName name) {
        var p = new Pane();
        this.states.put(name.name(), p);
    }

    @Override
    public final void addGridState(StateName name) {
        var p = new GridPane();
        this.states.put(name.name(), p);
    }

    @Override
    public final void selectState(StateName name) {
        this.activeState = name;
        this.stage.getScene().setRoot(this.states.get(name.name()));
    }

    @Override
    public final JavaFxWindowShell setVisible(boolean visible) {
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
    public final JavaFxWindowShell setBackground(Color color) {
        return setBackground(color, this.activeState);
    }

    @Override
    public final JavaFxWindowShell setBackground(Color color, StateName state) {
        this.states.get(state.name()).setBackground(
                new Background(
                        new BackgroundFill(new javafx.scene.paint.Color(
                                color.normalizedRedValue,
                                color.normalizedGreenValue,
                                color.normalizedBlueValue,
                                color.normalizedAlphaValue),
                                CornerRadii.EMPTY, Insets.EMPTY)));
        this.background = "";
        return this;
    }

    @Override
    public final JavaFxWindowShell setBackground(String file) {
        return this.setBackground(file, this.activeState);
    }

    @Override
    public final JavaFxWindowShell setBackground(String file, StateName state) {
        if (file.equals(this.background)) {
            return this;
        }
        this.background = file;
        BackgroundImage myBI = new BackgroundImage(new Image(this.imageProvider.getImage(file)),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, true, true));
        this.getPane(state).setBackground(new Background(myBI));

        return this;
    }

    @Override
    public final WindowShape createRectangle() {
        return this.createRectangle(this.activeState);
    }

    @Override
    public final WindowShape createRectangle(StateName state) {
        this.update();
        return new JavaFxShape(this.getPane(state));
    }

    @Override
    public final WindowToggle createToggle() {
        return this.createToggle(this.activeState);
    }

    @Override
    public final WindowToggle createToggle(StateName state) {
        this.update();
        return new JavaFxToggle(this.getPane(state));
    }

    @Override
    public final WindowNotification createNotification() {
        this.update();
        return new JavaFxNotification();
    }

    @Override
    public final WindowTextLine createTextLine() {
        return this.createTextLine(this.activeState);
    }

    @Override
    public final WindowTextLine createTextLine(StateName state) {
        this.update();
        return new JavaFxLabel(this.getPane(state), this);
    }

    @Override
    public final WindowButton createButton() {
        return this.createButton(this.activeState);
    }

    @Override
    public final WindowButton createButton(StateName state) {
        this.update();
        return new JavaFxButton(this.getPane(state), this.imageProvider);
    }

    @Override
    public final WindowImage createImage(String image) {
        return this.createImage(image, this.activeState);
    }

    @Override
    public final WindowImage createImage(String image, StateName state) {
        if(!image.isEmpty()) {
            this.update();
        }
        return new JavaFxImage(this.getPane(state), this.imageProvider, image);
    }

    @Override
    public final WindowProgressBar createProgressBar() {
        return this.createProgressBar(this.activeState);
    }

    @Override
    public final WindowProgressBar createProgressBar(StateName state) {
        this.update();
        return new JavaFxProgressBar(this.getPane(state));
    }

    @Override
    public final WindowDropdown createDropdown() {
        return this.createDropdown(this.activeState);
    }

    @Override
    public final WindowDropdown createDropdown(StateName state) {
        this.update();
        return new JavaFxDropDown(this.getPane(state));
    }

    @Override
    public final WindowButtonText createTextButton() {
        return this.createTextButton(this.activeState);
    }

    @Override
    public final WindowButtonText createTextButton(StateName state) {
        this.update();
        return new JavaFxButton(this.getPane(state), this.imageProvider);
    }

    @Override
    public final WindowInputBox createInputBox() {
        return createInputBox(this.activeState);
    }

    @Override
    public final WindowInputBox createInputBox(StateName state) {
        this.update();
        return new JavaFxInputBox(this.getPane(state));
    }

    @Override
    public final WindowCheckBox createCheckBox() {
        return this.createCheckBox(this.activeState);
    }

    @Override
    public final WindowCheckBox createCheckBox(StateName state) {
        this.update();
        return new JavaFxCheckBox(this.getPane(state));
    }

    @Override
    public final JavaFxMediaPlayer createMediaPlayer() {
        return this.createMediaPlayer(this.activeState);
    }

    @Override
    public final JavaFxMediaPlayer createMediaPlayer(StateName state) {
        this.update();
        return new JavaFxMediaPlayer(this.getPane(state));
    }

    @Override
    public final JavaFxVirtualKeyboard createVirtualKeyboard(KeyboardListener listener) {
        return this.createVirtualKeyboard(listener, this.activeState);
    }

    @Override
    public final JavaFxVirtualKeyboard createVirtualKeyboard(KeyboardListener listener, StateName state) {
        this.update();
        return new JavaFxVirtualKeyboard(listener, this.imageProvider, this.getPane(state));
    }

    @Override
    public final JavaFxNotificationPane createNotificationPane() {
        return this.createNotificationPane(this.activeState);
    }

    @Override
    public final JavaFxNotificationPane createNotificationPane(StateName state) {
        this.update();
        return new JavaFxNotificationPane(this.getPane(state));
    }

    @Override
    public final JavaFxWindowShell setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
        this.states.get(this.activeState.name()).setLayoutX(coordinates.getLeft());
        this.states.get(this.activeState.name()).setLayoutY(coordinates.getTop());
        this.states.get(this.activeState.name()).setMaxHeight(coordinates.getHeight());
        this.states.get(this.activeState.name()).setMinHeight(coordinates.getHeight());
        this.states.get(this.activeState.name()).setMaxWidth(coordinates.getWidth());
        this.states.get(this.activeState.name()).setMinWidth(coordinates.getWidth());

        return this;
    }

    @Override
    public final WindowModal createMessageBox() {
        return null;
    }

    @Override
    public final WindowModal createMessageButtonBox() {
        return null;
    }

    @Override
    public final WindowTextArea createTextArea() {
        return null;
    }

    @Override
    public final WindowMenuBar createMenuBar(WindowMenuBarElementDefinition... elements) {
        return null;
    }

    @Override
    public final WindowTreeRoot createTreeRoot(int width, int height, WindowTreeElement... elements) {
        return null;
    }

    @Override
    public final WindowModalFile createOpenFileBox() {
        return null;
    }

    @Override
    public final JavaFxCanvas createCanvas() {
        this.update();
        return null;
    }

    @Override
    public final WindowButton createButton(String background, String hover) {
        return null;
    }

    @Override
    public final WindowFont createFont(String path, int height) {
        return FONTS.computeIfAbsent(new FontData(path, height), k -> new JavaFxFont(path, height));
    }

    @Override
    public final JavaFxWindowShell createChildWindow(WindowShellOptions... options) {
        this.update();
        return new JavaFxWindowShell(this.imageProvider, options);
    }

    @Override
    public final WindowPopup createPopup() {
        this.update();
        return new JavaFxPopup(this.imageProvider, this.stage);
    }

    @Override
    public final JavaFxWindowShell setSize(Size size) {
        this.updateSize(size);
        this.stage.setMaxHeight(size.getHeight());
        this.stage.setMinHeight(size.getHeight());
        this.stage.setMaxWidth(size.getWidth());
        this.stage.setMinWidth(size.getWidth());
        return this;
    }

    @Override
    public final JavaFxWindowShell setPosition(Position position) {
        this.updatePosition(position);
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

    private void run() {
        this.stage.setIconified(false);
        this.stage.toFront();
    }

    final void centerOnScreen() {
        this.update();
        ScreenSize screenSize = this.getMonitorSize();
        float x = (screenSize.width / 2f) - (this.getRight() / 2f);
        float y = (screenSize.height / 2f) - (this.getBottom() / 2f);
        this.setPosition((int) x, (int) y);
    }

    public final void addOnHiddenListener(CallBack callBack) {
        this.stage.setOnHidden(e -> callBack.onEvent());
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
    public final JavaFxAlert createAlert() {
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

    private Pane getPane(StateName state) {
        var pane = this.states.get(state.name());
        if (pane == null) {
            throw new IllegalArgumentException("State not found: " + state.name());
        }
        return pane;
    }

    private record FontData(String name, int height) {
    }
}
