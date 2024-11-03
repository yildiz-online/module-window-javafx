package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.widget.GridLayout;
import be.yildizgames.module.window.widget.WindowImage;
import javafx.scene.layout.GridPane;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxGridLayout implements GridLayout {

    private final GridPane item;

    public JavaFxGridLayout(GridPane item) {
        super();
        this.item = item;
    }

    @Override
    public void addItem(WindowImage image, int column, int row) {
        this.item.add(((JavaFxImage)image).getNode(), column, row);
    }
}
