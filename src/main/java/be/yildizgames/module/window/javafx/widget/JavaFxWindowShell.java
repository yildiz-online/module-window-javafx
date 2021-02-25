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
import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.FullCoordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.WindowHandle;
import be.yildizgames.module.window.input.KeyboardListener;
import be.yildizgames.module.window.javafx.input.JavaFxMapperKeyPressed;
import be.yildizgames.module.window.javafx.input.JavaFxMapperKeyReleased;
import be.yildizgames.module.window.javafx.widget.experimental.CallBack;
import be.yildizgames.module.window.javafx.widget.experimental.VirtualKeyboard;
import be.yildizgames.module.window.widget.OnMinimize;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowCheckBox;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowMenuBar;
import be.yildizgames.module.window.widget.WindowMenuBarElementDefinition;
import be.yildizgames.module.window.widget.WindowModal;
import be.yildizgames.module.window.widget.WindowModalFile;
import be.yildizgames.module.window.widget.WindowNotification;
import be.yildizgames.module.window.widget.WindowPopup;
import be.yildizgames.module.window.widget.WindowShape;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowShellOptions;
import be.yildizgames.module.window.widget.WindowTextArea;
import be.yildizgames.module.window.widget.WindowToggle;
import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import javafx.application.Platform;
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
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Screen;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Random;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxWindowShell extends JavaFxBaseWidget<JavaFxWindowShell> implements WindowShell {

    private static final Map<FontData, JavaFxFont> FONTS = new HashMap<>();

    private final WindowImageProvider imageProvider;

    private Stage stage;

    private final Map<String, Pane> panes = new HashMap<>();

    private Pane pane;

    private String title;

    private String background = "";

    JavaFxWindowShell(Stage stage, WindowImageProvider imageProvider, WindowShellOptions... options) {
        super();
        this.stage = stage;
        this.imageProvider = imageProvider;
        this.handleOptions(stage, "", options);
    }

    JavaFxWindowShell(Stage stage, WindowImageProvider imageProvider, String loadingImage, WindowShellOptions... options) {
        super();
        this.stage = stage;
        this.imageProvider = imageProvider;
        this.handleOptions(stage, loadingImage, options);
    }

    JavaFxWindowShell(WindowImageProvider imageProvider, WindowShellOptions... options) {
        super();
        this.imageProvider = imageProvider;
        createStage(options);
    }

    JavaFxWindowShell(WindowImageProvider imageProvider, Stage parent) {
        super();
        this.imageProvider = imageProvider;
        this.stage = new Stage();
        this.pane = new Pane();
        Scene scene = new Scene(pane);
        this.panes.put("primary", pane);
        this.stage.setScene(scene);
        this.stage.initModality(Modality.WINDOW_MODAL);
        this.stage.initOwner(parent);
        this.stage.initStyle(StageStyle.UNDECORATED);
        this.stage.show();
        this.stage.setTitle("Modal");
        this.setReady(this.pane);
    }

    private void createStage(WindowShellOptions... options) {
        this.stage = new Stage();
        this.handleOptions(stage, "", options);
    }

    private void handleOptions(Stage stage, String loadingImage, WindowShellOptions... options) {
        this.pane = new Pane();
        this.setBackground(loadingImage);
        Scene scene = new Scene(pane);
        this.panes.put("primary", pane);
        stage.setScene(scene);
        if (options != null) {
            for (WindowShellOptions o : options) {
                switch (o) {
                    case FULLSCREEN:
                        stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                        stage.setFullScreen(true);
                        stage.setMaximized(true);
                        this.updateSize(FullCoordinates.size((int) Screen.getPrimary().getBounds().getWidth(), (int) Screen.getPrimary().getBounds().getHeight()));
                        break;
                    case NO_TITLE_BAR:
                        stage.initStyle(StageStyle.UNDECORATED);
                        break;
                }
            }
        }
        this.stage.show();
        Random random = new Random();
        this.title = "UnnamedWindow" + random.nextInt();
        this.stage.setTitle(this.title);
        this.setReady(this.pane);

    }

    public void addScene(String name) {
        Pane pane = new Pane();
        new Scene(pane);
        this.panes.put(name, pane);

    }

    public void selectScene(String name) {
        this.pane = panes.get(name);
        this.stage.setScene(pane.getScene());

    }

    @Override
    public JavaFxWindowShell setTitle(String title) {
        this.title = title;
        stage.setTitle(title);
        return this;
    }

    @Override
    public JavaFxWindowShell setIcon(String file) {
        this.stage.getIcons().add(new Image(file));
        return this;
    }

    @Override
    public JavaFxWindowShell setBackground(Color color) {
        this.pane.setBackground(
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
    public JavaFxWindowShell setBackground(String file) {
        if (file.equals(this.background)) {
            return this;
        }
        this.background = file;
        BackgroundImage myBI = new BackgroundImage(new Image(this.imageProvider.getImage(file)),
                BackgroundRepeat.REPEAT, BackgroundRepeat.REPEAT, BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, true, true));
        this.pane.setBackground(new Background(myBI));

        return this;
    }

    @Override
    public JavaFxWindowShell setFullScreen() {
        this.stage.setFullScreen(true);
        this.stage.setMaximized(true);
        return this;
    }

    @Override
    public ScreenSize getSize() {
        return new ScreenSize(this.getCoordinates().getWidth(), this.getCoordinates().getHeight());
    }

    @Override
    public ScreenSize getMonitorSize() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return new ScreenSize((int) screenBounds.getWidth(), (int) screenBounds.getHeight());
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {
        this.stage.close();
    }

    @Override
    public void update() {
        //hack to ensure to refresh the view in case of non full screen.
        // if the screen is not maximized/resized, the view is not correctly updated.
        this.stage.setHeight(this.stage.getHeight() - 1);
        this.stage.setHeight(this.stage.getHeight() + 1);
    }

    @Override
    public void checkForEvent() {

    }

    @Override
    public WindowShape createRectangle() {
        this.update();
        return new JavaFxShape(this.pane);
    }

    @Override
    public WindowToggle createToggle() {
        this.update();
        return new JavaFxToggle(this.pane);
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
    public WindowNotification createNotification() {
        this.update();
        return new JavaFxNotification();
    }

    @Override
    public final JavaFxCanvas createCanvas() {
        this.update();
        HWND hWnd = User32.INSTANCE.GetForegroundWindow();
        return new JavaFxCanvas(this.pane, new WindowHandle(Pointer.nativeValue(hWnd.getPointer())));
    }

    @Override
    public JavaFxLabel createTextLine() {
        this.update();
        return new JavaFxLabel(this.pane);
    }

    @Override
    public JavaFxButton createButton() {
        this.update();
        return new JavaFxButton(this.pane, this.imageProvider);
    }

    @Override
    public JavaFxButton createButton(String background, String hover) {
        return null;
    }

    @Override
    public JavaFxImage createImage(String image) {
        this.update();
        return new JavaFxImage(this.pane, this.imageProvider, image);
    }

    @Override
    public JavaFxProgressBar createProgressBar() {
        this.update();
        return new JavaFxProgressBar(this.pane);
    }

    @Override
    public WindowTreeRoot createTreeRoot(int width, int height, WindowTreeElement... elements) {
        return null;
    }

    @Override
    public JavaFxDropDown createDropdown() {
        this.update();
        return new JavaFxDropDown(this.pane);
    }

    @Override
    public WindowButtonText createTextButton() {
        this.update();
        return new JavaFxButton(this.pane, this.imageProvider);
    }

    @Override
    public JavaFxInputBox createInputBox() {
        this.update();
        return new JavaFxInputBox(this.pane);
    }

    @Override
    public JavaFxWindowShell createChildWindow(WindowShellOptions... options) {
        this.update();
        return new JavaFxWindowShell(this.imageProvider, options);
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
    public final WindowPopup createPopup() {
        this.update();
        return new JavaFxPopup(this.imageProvider, this.stage);
    }

    @Override
    public final WindowCheckBox createCheckBox() {
        this.update();
        return new JavaFxCheckBox(this.pane);
    }

    @Override
    public JavaFxFont createFont(String path, int height) {
        return FONTS.computeIfAbsent(new FontData(path, height), k -> new JavaFxFont(path, height));
    }

    @Override
    public final JavaFxWindowShell setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
        this.pane.setLayoutX(coordinates.getLeft());
        this.pane.setLayoutY(coordinates.getTop());
        this.pane.setMaxHeight(coordinates.getHeight());
        this.pane.setMinHeight(coordinates.getHeight());
        this.pane.setMaxWidth(coordinates.getWidth());
        this.pane.setMinWidth(coordinates.getWidth());

        return this;
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
        this.pane.setLayoutX(position.getLeft());
        this.pane.setLayoutY(position.getTop());

        return this;
    }

    @Override
    public final JavaFxWindowShell addKeyListener(KeyboardListener listener) {
        this.stage.getScene().setOnKeyPressed(new JavaFxMapperKeyPressed(listener));
        this.stage.getScene().setOnKeyReleased(new JavaFxMapperKeyReleased(listener));

        return this;
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
    public final JavaFxMediaPlayer createMediaPlayer() {
        this.update();
        return new JavaFxMediaPlayer(this.pane);
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
        this.stage.setOnHidden((e) -> callBack.onEvent());
    }

    public WindowShell hide() {
        this.stage.hide();
        return this;
    }

    private static class FontData {

        private final String name;

        private final int height;

        private FontData(String name, int height) {
            this.name = name;
            this.height = height;
        }

        @Override
        public boolean equals(Object o) {
            if (this == o) return true;
            if (o == null || getClass() != o.getClass()) return false;
            FontData fontData = (FontData) o;
            return height == fontData.height &&
                    Objects.equals(name, fontData.name);
        }

        @Override
        public int hashCode() {
            return Objects.hash(name, height);
        }
    }

    public final void createVirtualKeyboard() {
        this.update();
        VirtualKeyboard vk = new VirtualKeyboard();
        this.pane.getChildren().addAll(vk.view());
    }

    @Override
    public final JavaFxDirectoryChooser createDirectoryChooser() {
        this.update();
        return new JavaFxDirectoryChooser(this.stage);
    }

    @Override
    public final JavaFxNotificationPane createNotificationPane() {
        this.update();
        return new JavaFxNotificationPane(this.pane);
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
}
