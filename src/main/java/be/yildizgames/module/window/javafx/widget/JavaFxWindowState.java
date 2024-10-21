package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.window.widget.BorderLayout;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowState;
import be.yildizgames.module.window.widget.WindowWidgetCreator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

class JavaFxWindowState extends BaseWidgetCreator implements WindowState {

    private final Scene root;

    private String background = "";

    private final Pane pane;

    private final WindowImageProvider imageProvider;

    private final JavaFxWindowShell shell;

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
