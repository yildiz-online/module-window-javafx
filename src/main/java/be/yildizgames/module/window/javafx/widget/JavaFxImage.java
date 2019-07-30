package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowImageProvider;
import javafx.application.Platform;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.InputStream;

public class JavaFxImage extends JavaFxBaseWidget implements WindowImage {

    private WindowImageProvider provider;
    private ImageView imageView;
    private Coordinates coordinates = new Coordinates(0,0,0,0);

    JavaFxImage(Pane pane, WindowImageProvider provider, String image) {
        Platform.runLater(() -> {
            this.provider = provider;
            this.imageView = new ImageView();
            this.imageView.setImage(new Image(provider.getImage(image)));
            pane.getChildren().addAll(this.imageView);
            this.setReady();
        });
    }

    @Override
    public WindowImage setCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
        this.runWhenReady(() -> {
            this.imageView.setX(coordinates.left);
            this.imageView.setY(coordinates.top);
            this.imageView.setFitHeight(coordinates.height);
            this.imageView.setFitWidth(coordinates.width);
        });
        return this;
    }

    @Override
    public WindowImage setSize(Size size) {
        this.coordinates = new Coordinates(size, this.coordinates.left, this.coordinates.top);
        this.runWhenReady(() -> {
            this.imageView.setFitHeight(coordinates.height);
            this.imageView.setFitWidth(coordinates.width);
        });
        return this;
    }

    @Override
    public WindowImage setPosition(Position position) {
        this.coordinates = new Coordinates(this.coordinates.width, this.coordinates.height, position);
        this.runWhenReady(() -> {
            this.imageView.setX(coordinates.left);
            this.imageView.setY(coordinates.top);
        });
        return this;
    }

    @Override
    public WindowImage setVisible(boolean visible) {
        this.runWhenReady(() -> {
            this.imageView.setVisible(visible);
        });
        return this;
    }

    @Override
    public int getLeft() {
        return this.coordinates.left;
    }

    @Override
    public int getRight() {
        return this.coordinates.left + this.coordinates.width;
    }

    @Override
    public int getTop() {
        return this.coordinates.top;
    }

    @Override
    public int getBottom() {
        return this.coordinates.top + this.coordinates.height;
    }

    @Override
    public final WindowImage setImage(String url) {
        this.runWhenReady(() -> {
            this.imageView.setImage(new Image(provider.getImage(url)));
        });
        return this;
    }
}
