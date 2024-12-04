package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.widget.TileLayout;
import be.yildizgames.module.window.widget.WindowWidget;
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
}
