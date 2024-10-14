package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.window.input.KeyboardListener;
import be.yildizgames.module.window.javafx.widget.experimental.JavaFxVirtualKeyboard;
import be.yildizgames.module.window.widget.WindowButton;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowCanvas;
import be.yildizgames.module.window.widget.WindowCheckBox;
import be.yildizgames.module.window.widget.WindowDropdown;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowInputBox;
import be.yildizgames.module.window.widget.WindowMediaPlayer;
import be.yildizgames.module.window.widget.WindowMenuBar;
import be.yildizgames.module.window.widget.WindowMenuBarElementDefinition;
import be.yildizgames.module.window.widget.WindowModal;
import be.yildizgames.module.window.widget.WindowModalFile;
import be.yildizgames.module.window.widget.WindowNotificationPane;
import be.yildizgames.module.window.widget.WindowProgressBar;
import be.yildizgames.module.window.widget.WindowShape;
import be.yildizgames.module.window.widget.WindowState;
import be.yildizgames.module.window.widget.WindowTextArea;
import be.yildizgames.module.window.widget.WindowTextLine;
import be.yildizgames.module.window.widget.WindowToggle;
import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import be.yildizgames.module.window.widget.experimental.VirtualKeyboard;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

public class JavaFxWindowState implements WindowState {

    private String background = "";

    private final Pane pane;

    private final WindowImageProvider imageProvider;

    private final JavaFxWindowShell shell;

    public JavaFxWindowState(Pane pane, WindowImageProvider imageProvider, JavaFxWindowShell shell) {
        this.pane = pane;
        this.imageProvider = imageProvider;
        this.shell = shell;
    }

    @Override
    public final WindowState setBackground(Color color) {
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
    public final WindowState setBackground(String file) {
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
    public final WindowShape createRectangle() {
        return new JavaFxShape(this.pane);
    }

    @Override
    public final WindowToggle createToggle() {
        return new JavaFxToggle(this.pane);
    }

    @Override
    public final WindowTextLine createTextLine() {
        return new JavaFxLabel(this.pane, shell);
    }

    @Override
    public final WindowButton createButton() {
        return new JavaFxButton(this.pane, this.imageProvider);
    }

    @Override
    public final WindowImage createImage(String image) {
        return new JavaFxImage(this.pane, this.imageProvider, image);
    }

    @Override
    public final WindowProgressBar createProgressBar() {
        return new JavaFxProgressBar(this.pane);
    }

    @Override
    public final WindowDropdown createDropdown() {
        return new JavaFxDropDown(this.pane);
    }

    @Override
    public final WindowButtonText createTextButton() {
        return new JavaFxButton(this.pane, this.imageProvider);
    }

    @Override
    public final WindowInputBox createInputBox() {
        return new JavaFxInputBox(this.pane);
    }

    @Override
    public final WindowCheckBox createCheckBox() {
        return new JavaFxCheckBox(this.pane);
    }

    @Override
    public final WindowMediaPlayer createMediaPlayer() {
        return new JavaFxMediaPlayer(this.pane);
    }

    @Override
    public final VirtualKeyboard createVirtualKeyboard(KeyboardListener listener) {
        return new JavaFxVirtualKeyboard(listener, this.imageProvider, this.pane);
    }

    @Override
    public final WindowNotificationPane createNotificationPane() {
        return new JavaFxNotificationPane(this.pane);
    }

    @Override
    public final WindowWidgetCreator setCoordinates(Coordinates coordinates) {
  //      this.updateCoordinates(coordinates);
        this.pane.setLayoutX(coordinates.getLeft());
        this.pane.setLayoutY(coordinates.getTop());
        this.pane.setMaxHeight(coordinates.getHeight());
        this.pane.setMinHeight(coordinates.getHeight());
        this.pane.setMaxWidth(coordinates.getWidth());
        this.pane.setMinWidth(coordinates.getWidth());

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
    public final WindowCanvas createCanvas() {
        return null;
    }

    @Override
    public final WindowButton createButton(String background, String hover) {
        return null;
    }

}
