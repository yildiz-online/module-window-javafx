/*
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 *  Copyright (c) 2019 Grégory Van den Borre
 *
 *  More infos available: https://engine.yildiz-games.be
 *
 *  Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 *  documentation files (the "Software"), to deal in the Software without restriction, including without
 *  limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 *  of the Software, and to permit persons to whom the Software is furnished to do so,
 *  subject to the following conditions:
 *
 *  The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 *  THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 *  OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 *  OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 */

package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.FullCoordinates;
import be.yildizgames.module.coordinates.ParentRelativePosition;
import be.yildizgames.module.window.widget.WidgetEvent;
import be.yildizgames.module.window.widget.animation.AnimationBehavior;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.util.Duration;

/**
 * Building a widget is an async process done by the javafx thread, thus it is necessary to ensure that it is fully built before attempting to use it.
 *
 * @author Grégory Van den Borre
 */
@SuppressWarnings("unchecked")
class JavaFxBaseWidget<T extends JavaFxBaseWidget<T>> {

    private Node node;

    private final ScaleTransition scaleAnimation = new ScaleTransition();

    private final TranslateTransition translateAnimation = new TranslateTransition();

    JavaFxBaseWidget() {
        super();
    }

    /**
     * Set ready is to be invoked once the widget is completely built.
     */
    protected final void setReady(Node node) {
        if (this.node == null) {
            this.node = node;
            this.scaleAnimation.setNode(this.node);
            this.translateAnimation.setNode(this.node);
        }
    }

    public final T setBlinkAnimation(double duration) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(duration), this.node);
        fadeTransition.setFromValue(1.0);
        fadeTransition.setToValue(0.0);
        fadeTransition.setCycleCount(Animation.INDEFINITE);
        fadeTransition.play();
        return (T)this;
    }

    public final T setCssStyleClass(String cssClass) {
        this.node.getStyleClass().add(cssClass);
        return (T)this;
    }

    public final int getHeight() {
        return (int) this.node.localToScreen(this.node.getBoundsInLocal()).getHeight();
    }

    public final int getWidth() {
        return (int) this.node.localToScreen(this.node.getBoundsInLocal()).getWidth();
    }

    public T setPosition(ParentRelativePosition position, int distance) {
        throw new UnsupportedOperationException("Not yet implemented");
    }

    public final T setTranslationAnimation(AnimationBehavior animation) {
        this.translateAnimation.setFromX(animation.getFromX());
        this.translateAnimation.setFromY(animation.getFromY());
        this.translateAnimation.setFromZ(animation.getFromZ());
        this.translateAnimation.setToX(animation.getToX());
        this.translateAnimation.setToY(animation.getToY());
        this.translateAnimation.setToZ(animation.getToZ());
        this.translateAnimation.setByX(animation.getByX());
        this.translateAnimation.setByY(animation.getByY());
        this.translateAnimation.setByZ(animation.getByZ());
        this.translateAnimation.setDuration(javafx.util.Duration.millis(animation.getDuration().toMillis()));
        return (T) this;
    }

    public final T setScaleAnimation(AnimationBehavior animation) {
        this.scaleAnimation.setFromX(animation.getFromX());
        this.scaleAnimation.setFromY(animation.getFromY());
        this.scaleAnimation.setFromZ(animation.getFromZ());
        this.scaleAnimation.setToX(animation.getToX());
        this.scaleAnimation.setToY(animation.getToY());
        this.scaleAnimation.setToZ(animation.getToZ());
        this.scaleAnimation.setByX(animation.getByX());
        this.scaleAnimation.setByY(animation.getByY());
        this.scaleAnimation.setByZ(animation.getByZ());
        this.scaleAnimation.setDuration(javafx.util.Duration.millis(animation.getDuration().toMillis()));
        return (T) this;
    }

    public final T playScaleAnimation() {
        this.scaleAnimation.stop();
        this.scaleAnimation.play();
        return (T) this;
    }

    public final T stopScaleAnimation() {
        this.scaleAnimation.stop();
        return (T) this;
    }

    public final T playTranslationAnimation() {
        this.translateAnimation.stop();
        this.translateAnimation.play();
        return (T) this;
    }

    public final T stopTranslationAnimation() {
        this.translateAnimation.stop();
        return (T) this;
    }

    public final Coordinates getCoordinates() {
        var boundsInScreen = this.node.localToScreen(this.node.getBoundsInLocal());
        return FullCoordinates.full(
                (int) boundsInScreen.getWidth(),
                (int) boundsInScreen.getHeight(),
                (int) boundsInScreen.getMinX(),
                (int) boundsInScreen.getMinY());
    }

    public final int getLeft() {
        return (int)this.node.localToScreen(this.node.getBoundsInLocal()).getMinX();
    }

    public final int getRight() {
        return (int)this.node.localToScreen(this.node.getBoundsInLocal()).getMaxX();
    }

    public final int getTop() {
        return (int)this.node.localToScreen(this.node.getBoundsInLocal()).getMinY();
    }

    public final int getBottom() {
        return (int)this.node.localToScreen(this.node.getBoundsInLocal()).getMaxY();
    }

    public final T setOpacity(float opacity) {
        this.node.setOpacity(opacity);
        return (T) this;
    }

    public T setVisible(boolean visible) {
        this.node.setVisible(visible);
        return (T) this;
    }

    public final T requestFocus() {
        this.node.requestFocus();
        return (T) this;
    }

    public T fireEvent(WidgetEvent event) {
        if (event == WidgetEvent.MOUSE_LEFT_CLICK) {
            Event.fireEvent(this.node, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1, false, false, false, false,
                    true, false, false, true, false, true, null));
        }
        return (T) this;
    }

    public final boolean isFocused() {
        return this.node.isFocused();
    }

    final Node getNode() {
        return this.node;
    }
}
