package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.widget.BorderLayout;
import javafx.scene.layout.BorderPane;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxBorderLayout implements BorderLayout {

    private final BorderPane border;

    public JavaFxBorderLayout(BorderPane border) {
        super();
        this.border = border;
    }
}
