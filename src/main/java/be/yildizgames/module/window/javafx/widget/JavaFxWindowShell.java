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
import be.yildizgames.module.window.javafx.widget.experimental.CallBack;
import be.yildizgames.module.window.javafx.widget.experimental.VirtualKeyboard;
import be.yildizgames.module.window.widget.OnMinimize;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowMenuBar;
import be.yildizgames.module.window.widget.WindowMenuBarElementDefinition;
import be.yildizgames.module.window.widget.WindowModal;
import be.yildizgames.module.window.widget.WindowModalFile;
import be.yildizgames.module.window.widget.WindowNotification;
import be.yildizgames.module.window.widget.WindowPopup;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowShellOptions;
import be.yildizgames.module.window.widget.WindowTextArea;
import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import com.sun.jna.Pointer;
import com.sun.jna.platform.win32.User32;
import com.sun.jna.platform.win32.WinDef.HWND;
import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Rectangle2D;
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

    private Map<String, Pane> panes = new HashMap<>();

    private Pane pane;

    private boolean increase;
    
    private String title;

    private String background = "";

    JavaFxWindowShell(Stage stage, WindowImageProvider imageProvider, WindowShellOptions... options) {
        super();
        this.stage = stage;
        this.imageProvider = imageProvider;
        this.handleOptions(stage, options);
        this.updateCoordinates(new Coordinates((int)stage.getWidth(), (int) stage.getHeight(), 0, 0));
    }

    JavaFxWindowShell(WindowImageProvider imageProvider, WindowShellOptions... options) {
        super();
        this.imageProvider = imageProvider;
        createStage(options);
        this.updateCoordinates(new Coordinates((int)stage.getWidth(), (int) stage.getHeight(), 0, 0));
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
            this.updateCoordinates(new Coordinates((int)stage.getWidth(), (int) stage.getHeight(), 0, 0));
    }

    private void createStage(WindowShellOptions... options) {
        this.stage = new Stage();
        this.handleOptions(stage, options);
    }

    private void handleOptions(Stage stage, WindowShellOptions... options) {

            this.pane = new Pane();
            Scene scene = new Scene(pane);
            this.panes.put("primary", pane);
            stage.setScene(scene);
            if(options!= null) {
                for (WindowShellOptions o : options) {
                    switch (o) {
                        case FULLSCREEN:
                            stage.setFullScreenExitKeyCombination(KeyCombination.NO_MATCH);
                            stage.setFullScreen(true);
                            stage.setMaximized(true);
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
        //FIXME implements
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

        return this;
    }

    @Override
    public JavaFxWindowShell setBackground(String file) {
        if(file.equals(this.background)) {
            return this;
        }
        this.background = file;
            BackgroundImage myBI= new BackgroundImage(new Image(this.imageProvider.getImage(file)),
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
        return new ScreenSize(this.getCoordinates().width, this.getCoordinates().height);
    }

    @Override
    public ScreenSize getMonitorSize() {
        Rectangle2D screenBounds = Screen.getPrimary().getBounds();
        return new ScreenSize((int)screenBounds.getWidth(), (int) screenBounds.getHeight());
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
            this.stage.setHeight(this.stage.getHeight() + (increase ? 1 : -1));
            increase = ! increase;

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
        return new JavaFxButton(this.pane);
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
        return new JavaFxButtonText(this.pane);
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
    public JavaFxFont createFont(String path, int height) {
        return FONTS.computeIfAbsent(new FontData(path, height), k -> new JavaFxFont(path, height));
    }

    @Override
    public final JavaFxWindowShell setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
            this.pane.setLayoutX(coordinates.left);
            this.pane.setLayoutY(coordinates.top);
            this.pane.setMaxHeight(coordinates.height);
            this.pane.setMinHeight(coordinates.height);
            this.pane.setMaxWidth(coordinates.width);
            this.pane.setMinWidth(coordinates.width);

        return this;
    }

    @Override
    public final JavaFxWindowShell setSize(Size size) {
        this.updateCoordinates(size);
            this.stage.setMaxHeight(size.height);
            this.stage.setMinHeight(size.height);
            this.stage.setMaxWidth(size.width);
            this.stage.setMinWidth(size.width);

        return this;
    }

    @Override
    public final JavaFxWindowShell setPosition(Position position) {
        this.updateCoordinates(position);
            this.pane.setLayoutX(position.left);
            this.pane.setLayoutY(position.top);

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
    }

    final void centerOnScreen() {
        this.update();
        ScreenSize screenSize = this.getMonitorSize();
        float x = (screenSize.width / 2f) -  (this.getRight() / 2f);
        float y = (screenSize.height / 2f) - (this.getBottom() / 2f);
        this.setPosition(new Position((int) x, (int) y));
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

    public void createVirtualKeyboard() {
        this.update();
            VirtualKeyboard vk = new VirtualKeyboard();
            this.pane.getChildren().addAll(vk.view());


    }
}
