package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.window.widget.GridLayout;
import be.yildizgames.module.window.widget.BorderLayout;
import be.yildizgames.module.window.widget.TileLayout;
import be.yildizgames.module.window.widget.WindowButton;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowShape;
import be.yildizgames.module.window.widget.WindowState;
import be.yildizgames.module.window.widget.WindowTextLine;
import be.yildizgames.module.window.widget.WindowWidgetCreator;
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
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.TilePane;
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

    private final Map<String, JavaFxLabel> labels = new HashMap<>();
    private final Map<String, JavaFxImage> images = new HashMap<>();
    private final Map<String, JavaFxShape> shapes = new HashMap<>();
    private final Map<String, JavaFxButton> buttons = new HashMap<>();
    private final Map<String, JavaFxGridLayout> grids = new HashMap<>();
    private final Map<String, JavaFxTileLayout> tiles = new HashMap<>();


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
    public Optional<GridLayout> findGrid(String id) {
        var result = this.grids.get(id);
        if(result == null) {
            var item = this.pane.lookup(id);
            if(item != null && item.getClass() == GridPane.class) {
                result = new JavaFxGridLayout((GridPane)item);
                this.grids.put(id, result);
            }
        }
        return Optional.ofNullable(result);
    }

    @Override
    public Optional<TileLayout> findTile(String id) {
        var result = this.tiles.get(id);
        if(result == null) {
            var item = this.pane.lookup(id);
            if(item != null && item.getClass() == TilePane.class) {
                result = new JavaFxTileLayout((TilePane) item);
                this.tiles.put(id, result);
            }
        }
        return Optional.ofNullable(result);
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
