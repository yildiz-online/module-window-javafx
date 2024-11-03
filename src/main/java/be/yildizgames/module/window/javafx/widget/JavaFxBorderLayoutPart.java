package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.window.widget.BorderLayoutPart;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowWidgetCreator;
import javafx.scene.layout.Pane;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxBorderLayoutPart extends BaseWidgetCreator implements BorderLayoutPart {


    public JavaFxBorderLayoutPart(Pane pane, JavaFxWindowShell shell, WindowImageProvider imageProvider) {
        super(pane, shell, imageProvider);
    }

    @Override
    public WindowWidgetCreator setCoordinates(Coordinates coordinates) {
        return this;
    }
}
