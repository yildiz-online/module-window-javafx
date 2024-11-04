package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.widget.WindowScrollbar;
import javafx.scene.control.ScrollPane;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxScrollbar implements WindowScrollbar {

    private final ScrollPane scrollPane;

    public JavaFxScrollbar(ScrollPane scrollPane) {
        super();
        this.scrollPane = scrollPane;
    }

    @Override
    public final void scrollToTop() {
        this.scrollPane.setVvalue(0);
    }

    @Override
    public final void scrollToBottom() {
        this.scrollPane.setVvalue(1);
    }

    @Override
    public void down(double value) {
        this.scrollPane.setVvalue(this.scrollPane.getVvalue() + value);
    }

    @Override
    public void up(double value) {
        this.scrollPane.setVvalue(this.scrollPane.getVvalue() - value);
    }
}
