package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.widget.BorderLayout;
import be.yildizgames.module.window.widget.BorderLayoutPart;
import be.yildizgames.module.window.widget.WindowImageProvider;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;

/**
 * @author Grégory Van den Borre
 */
class JavaFxBorderLayout implements BorderLayout {

    private final JavaFxBorderLayoutPart bottom;
    private final JavaFxBorderLayoutPart top;
    private final JavaFxBorderLayoutPart left;
    private final JavaFxBorderLayoutPart right;
    private final JavaFxBorderLayoutPart center;

    JavaFxBorderLayout(BorderPane border, JavaFxWindowShell shell, WindowImageProvider imageProvider) {
        super();
        var topPane = new Pane();
        var bottomPane = new Pane();
        var leftPane = new Pane();
        var rightPane = new Pane();
        var centerPane = new Pane();

        border.setTop(topPane);
        border.setBottom(bottomPane);
        border.setLeft(leftPane);
        border.setRight(rightPane);
        border.setCenter(centerPane);

        this.top = new JavaFxBorderLayoutPart(topPane, shell, imageProvider);
        this.bottom = new JavaFxBorderLayoutPart(bottomPane, shell, imageProvider);
        this.left = new JavaFxBorderLayoutPart(leftPane, shell, imageProvider);
        this.right = new JavaFxBorderLayoutPart(rightPane, shell, imageProvider);
        this.center = new JavaFxBorderLayoutPart(centerPane, shell, imageProvider);
    }

    @Override
    public BorderLayoutPart getTop() {
        return top;
    }

    @Override
    public JavaFxBorderLayoutPart getBottom() {
        return bottom;
    }

    @Override
    public BorderLayoutPart getLeft() {
        return left;
    }

    @Override
    public BorderLayoutPart getCenter() {
        return center;
    }

    @Override
    public BorderLayoutPart getRight() {
        return right;
    }
}
