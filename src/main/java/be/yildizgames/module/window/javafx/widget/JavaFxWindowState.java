package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.window.widget.BorderLayout;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowState;
import be.yildizgames.module.window.widget.WindowWidgetCreator;
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
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowState;
import be.yildizgames.module.window.widget.WindowTextArea;
import be.yildizgames.module.window.widget.WindowTextLine;
import be.yildizgames.module.window.widget.WindowToggle;
import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import be.yildizgames.module.window.widget.WindowWidgetCreator;
import be.yildizgames.module.window.widget.experimental.VirtualKeyboard;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

class JavaFxWindowState extends BaseWidgetCreator implements WindowState {

    private final Scene root;

    private String background = "";

    private final Pane pane;

    private final WindowImageProvider imageProvider;

    private final JavaFxWindowShell shell;

    private final Map<String, WindowTextLine> labels = new HashMap<>();
    private final Map<String, WindowImage> images = new HashMap<>();
    private final Map<String, WindowShape> shapes = new HashMap<>();
    private final Map<String, WindowButton> buttons = new HashMap<>();


    JavaFxWindowState(Pane pane, WindowImageProvider imageProvider, JavaFxWindowShell shell, Scene scene) {
        super(pane, shell, imageProvider);
        this.pane = pane;
        this.imageProvider = imageProvider;
        this.shell = shell;
        this.root = scene;
    }

    @Override
    public final WindowState activate() {
        this.root.setRoot(this.pane);
        return this;
    }

    @Override
    public final BorderLayout createBorderLayout() {
        var border = new BorderPane();
        this.pane.getChildren().add(border);
        return new JavaFxBorderLayout(border, shell, imageProvider);
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
    public final Optional<WindowTextLine> findTextLine(String id) {
        var result = this.labels.get(id);
        if(result == null) {
            var item = this.pane.lookup(id);
            if(item != null && item.getClass() == Label.class) {
                 result = new JavaFxLabel((Label)item, this.shell);
                 this.labels.put(id, result);
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public final Optional<WindowImage> findImage(String id) {
        var result = this.images.get(id);
        if(result == null) {
            var item = this.pane.lookup(id);
            if(item != null && item.getClass() == ImageView.class) {
                result = new JavaFxImage((ImageView)item, this.imageProvider);
                this.images.put(id, result);
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<WindowShape> findShape(String id) {
        var result = this.shapes.get(id);
        if(result == null) {
            var item = this.pane.lookup(id);
            if(item != null && item.getClass() == Rectangle.class) {
                result = new JavaFxShape((Rectangle)item);
                this.shapes.put(id, result);
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<WindowButton> findButton(String id) {
        var result = this.buttons.get(id);
        if(result == null) {
            var item = this.pane.lookup(id);
            if(item != null && item.getClass() == Button.class) {
                result = new JavaFxButton((Button)item, this.imageProvider);
                this.buttons.put(id, result);
            }
        }
        return Optional.ofNullable(result);
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
}
