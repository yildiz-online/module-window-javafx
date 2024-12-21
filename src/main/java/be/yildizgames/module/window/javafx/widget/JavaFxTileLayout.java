package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.FullCoordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.widget.TileLayout;
import be.yildizgames.module.window.widget.WindowWidget;
import be.yildizgames.module.window.widget.animation.AnimationBehavior;
import javafx.scene.layout.TilePane;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxTileLayout<T extends WindowWidget<T>> implements TileLayout<T> {

    private final TilePane pane;

    public JavaFxTileLayout(TilePane pane) {
        super();
        this.pane = pane;
    }

    @Override
    public final void addItem(T t) {
        this.pane.getChildren().add(((JavaFxBaseWidget<?>)t).getNode());
    }

    @Override
    public final void removeItem(T t) {
        this.pane.getChildren().remove(((JavaFxBaseWidget<?>)t).getNode());
    }

    @Override
    public final int getSize() {
        return this.pane.getChildren().size();
    }

    @Override
    public final void setGap(int horizontal, int vertical) {
        this.pane.setHgap(horizontal);
        this.pane.setVgap(vertical);
    }

    @Override
    public final int numberOfRow() {
        try {
            var clazz = this.pane.getClass();
            var rowField = clazz.getDeclaredField("actualRows");
            rowField.setAccessible(true);
            return rowField.getInt(this.pane);
        } catch (Exception e) {
            System.getLogger(JavaFxTileLayout.class.getName()).log(System.Logger.Level.ERROR, "Failed to get number of rows for " + this.pane.getClass(), e);
            return 0;
        }
    }

    @Override
    public final int numberOfColumn() {
        try {
            var clazz = this.pane.getClass();
            var columnField = clazz.getDeclaredField("actualColumns");
            columnField.setAccessible(true);
            return columnField.getInt(this.pane);
        } catch (Exception e) {
            System.getLogger(JavaFxTileLayout.class.getName()).log(System.Logger.Level.ERROR, "Failed to get number of columns for " + this.pane.getClass(), e);
            return 0;
        }
    }

    @Override
    public TileLayout<T> setCssStyleClass(String cssClass) {
        return this;
    }

    @Override
    public TileLayout<T> playBlinkAnimation(double duration) {
        return this;
    }

    @Override
    public TileLayout<T> stopBlinkAnimation() {
        return this;
    }

    @Override
    public TileLayout<T> setScaleAnimation(AnimationBehavior animation) {
        return this;
    }

    @Override
    public TileLayout<T> playScaleAnimation() {
        return this;
    }

    @Override
    public TileLayout<T> stopScaleAnimation() {
        return this;
    }

    @Override
    public TileLayout<T> setTranslationAnimation(AnimationBehavior animation) {
        return this;
    }

    @Override
    public TileLayout<T> playTranslationAnimation() {
        return this;
    }

    @Override
    public TileLayout<T> stopTranslationAnimation() {
        return this;
    }

    @Override
    public TileLayout<T> setCoordinates(Coordinates coordinates) {
        this.pane.setPrefSize(coordinates.getWidth(), coordinates.getHeight());
        this.pane.setTranslateX(coordinates.getLeft());
        this.pane.setTranslateY(coordinates.getTop());
        return this;
    }

    @Override
    public TileLayout<T> setOpacity(float opacity) {
        this.pane.setOpacity(opacity);
        return this;
    }

    @Override
    public TileLayout<T> setSize(Size size) {
        this.pane.setPrefSize(size.getWidth(), size.getHeight());
        return this;
    }

    @Override
    public TileLayout<T> requestFocus() {
        this.pane.requestFocus();
        return this;
    }

    @Override
    public int getHeight() {
        return 0;
    }

    @Override
    public int getWidth() {
        return 0;
    }

    @Override
    public TileLayout<T> setPosition(Position position) {
        return this;
    }

    @Override
    public TileLayout<T> setVisible(boolean visible) {
        this.pane.setVisible(visible);
        return this;
    }

    @Override
    public int getLeft() {
        return 0;
    }

    @Override
    public int getRight() {
        return 0;
    }

    @Override
    public int getTop() {
        return 0;
    }

    @Override
    public int getBottom() {
        return 0;
    }

    @Override
    public TileLayout<T> toFront() {
        this.pane.toFront();
        return this;
    }

    @Override
    public TileLayout<T> toBack() {
        this.pane.toBack();
        return this;
    }

    @Override
    public Coordinates getCoordinates() {
        return FullCoordinates.ZERO;
    }
}
