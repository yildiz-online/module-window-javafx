package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.widget.TileLayout;
import be.yildizgames.module.window.widget.WindowImage;
import javafx.scene.layout.TilePane;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxTileLayout implements TileLayout {

    private final TilePane pane;

    public JavaFxTileLayout(TilePane pane) {
        super();
        this.pane = pane;
    }

    @Override
    public final void addItem(WindowImage image) {
        this.pane.getChildren().add(((JavaFxImage)image).getNode());
    }

    @Override
    public final void setGap(int horizontal, int vertical) {
        this.pane.setHgap(horizontal);
        this.pane.setVgap(vertical);
    }

    @Override
    public int numberOfRow() {
        try {
            var clazz = this.pane.getClass();
            var rowField = clazz.getField("actualRows");
            rowField.setAccessible(true);
            return rowField.getInt(this.pane);
        } catch (Exception e) {
            System.getLogger(JavaFxTileLayout.class.getName()).log(System.Logger.Level.ERROR, "Failed to get number of rows", e);
            return 0;
        }
    }

    @Override
    public int numberOfColumn() {
        try {
            var clazz = this.pane.getClass();
            var columnField = clazz.getDeclaredField("actualColumns");
            columnField.setAccessible(true);
            return columnField.getInt(this.pane);
        } catch (Exception e) {
            System.getLogger(JavaFxTileLayout.class.getName()).log(System.Logger.Level.ERROR, "Failed to get number of columns", e);
            return 0;
        }
    }
}
