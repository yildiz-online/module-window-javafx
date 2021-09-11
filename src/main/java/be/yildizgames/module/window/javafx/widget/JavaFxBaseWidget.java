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
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.widget.WidgetEvent;
import be.yildizgames.module.window.widget.animation.AnimationBehavior;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Building a widget is an async process done by the javafx thread, thus it is necessary to ensure that it is fully built before attempting to use it.
 *
 * @author Grégory Van den Borre
 */
class JavaFxBaseWidget<T extends JavaFxBaseWidget> {

    private Coordinates coordinates = FullCoordinates.ZERO;

    private Node node;

    private final ScaleTransition scaleAnimation = new ScaleTransition();

    private final TranslateTransition translateAnimation = new TranslateTransition();

    /**
     * Set ready is to be invoked once the widget is completely built.
     */
    protected final void setReady(Node node) {
        if(this.node == null) {
            this.node = node;
            this.scaleAnimation.setNode(this.node);
            this.translateAnimation.setNode(this.node);
        }
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
        return this.coordinates;
    }

    public final int getLeft() {
        return this.coordinates.getLeft();
    }

    public final int getRight() {
        return this.coordinates.getLeft() + this.coordinates.getWidth();
    }

    public final int getTop() {
        return this.coordinates.getTop();
    }

    public final int getBottom() {
        return this.coordinates.getTop() + this.coordinates.getHeight();
    }

    protected final void updateCoordinates(Coordinates coordinates) {
        this.coordinates = coordinates;
    }

    protected final void updatePosition(Position c) {
        this.coordinates = FullCoordinates.full(this.coordinates.getWidth(), this.coordinates.getHeight(), c.getLeft(), c.getTop());
    }

    protected final void updateSize(Size c) {
        this.coordinates = FullCoordinates.full(c.getWidth(), c.getHeight(), this.coordinates.getLeft(), this.coordinates.getTop());
    }

    public final T setOpacity(float opacity) {
        this.node.setOpacity(opacity);
        return (T) this;
    }

    public T setVisible(boolean visible) {
        this.node.setVisible(visible);
        return (T) this;
    }

    public T requestFocus() {
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
}
