package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.widget.VerticalLayout;
import be.yildizgames.module.window.widget.WindowWidget;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;

import java.util.ArrayList;
import java.util.List;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxVerticalWidget extends JavaFxBaseWidget<JavaFxVerticalWidget> implements VerticalLayout {

    private final VBox vbox = new VBox();

    private final List<WindowWidget<?>> children = new ArrayList<>();

    public JavaFxVerticalWidget(Pane pane) {
        super();
        pane.getChildren().add(this.vbox);
        this.setReady(this.vbox);
    }

    @Override
    public final VerticalLayout addWidget(WindowWidget<?> widget) {
        this.vbox.getChildren().add(((JavaFxBaseWidget<?>)widget).getNode());
        this.children.add(widget);
        return this;
    }

    @Override
    public final <T extends WindowWidget<T>> T getWidget(int index, Class<T> clazz) {
        return clazz.cast(this.children.get(index));
    }

    @Override
    public final VerticalLayout setCoordinates(Coordinates coordinates) {
        this.vbox.setLayoutX(coordinates.getLeft());
        this.vbox.setLayoutY(coordinates.getTop());
        this.vbox.setPrefHeight(coordinates.getHeight());
        this.vbox.setPrefWidth(coordinates.getWidth());
        return this;
    }

    @Override
    public final VerticalLayout setSize(Size size) {
        this.vbox.setMaxWidth(size.getWidth());
        this.vbox.setMaxHeight(size.getHeight());
        this.vbox.setMinWidth(size.getWidth());
        this.vbox.setMinHeight(size.getHeight());
        return this;
    }

    @Override
    public final VerticalLayout setPosition(Position position) {
        this.vbox.setLayoutX(position.getLeft());
        this.vbox.setLayoutY(position.getTop());
        return this;
    }

    @Override
    public final VerticalLayout toFront() {
        this.vbox.toFront();
        return this;
    }

    @Override
    public final VerticalLayout toBack() {
        this.vbox.toBack();
        return this;
    }
}
