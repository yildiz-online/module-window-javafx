package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.FullCoordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.widget.WindowScrollbar;
import be.yildizgames.module.window.widget.animation.AnimationBehavior;
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

    @Override
    public WindowScrollbar setCssStyleClass(String cssClass) {
        return this;
    }

    @Override
    public WindowScrollbar playBlinkAnimation(double duration) {
        return this;
    }

    @Override
    public WindowScrollbar stopBlinkAnimation() {
        return this;
    }

    @Override
    public WindowScrollbar setScaleAnimation(AnimationBehavior animation) {
        return this;
    }

    @Override
    public WindowScrollbar playScaleAnimation() {
        return this;
    }

    @Override
    public WindowScrollbar stopScaleAnimation() {
        return this;
    }

    @Override
    public WindowScrollbar setTranslationAnimation(AnimationBehavior animation) {
        return this;
    }

    @Override
    public WindowScrollbar playTranslationAnimation() {
        return this;
    }

    @Override
    public WindowScrollbar stopTranslationAnimation() {
        return this;
    }

    @Override
    public WindowScrollbar setCoordinates(Coordinates coordinates) {
        return this;
    }

    @Override
    public WindowScrollbar setOpacity(float opacity) {
        this.scrollPane.setOpacity(opacity);
        return this;
    }

    @Override
    public WindowScrollbar setSize(Size size) {
        return this;
    }

    @Override
    public WindowScrollbar requestFocus() {
        this.scrollPane.requestFocus();
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
    public WindowScrollbar setPosition(Position position) {
        return this;
    }

    @Override
    public WindowScrollbar setVisible(boolean visible) {
        this.scrollPane.setVisible(visible);
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
    public WindowScrollbar toFront() {
        this.scrollPane.toFront();
        return this;
    }

    @Override
    public WindowScrollbar toBack() {
        this.scrollPane.toBack();
        return this;
    }

    @Override
    public Coordinates getCoordinates() {
        return FullCoordinates.ZERO;
    }
}
