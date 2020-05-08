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

import be.yildizgames.module.coordinate.BaseCoordinate;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.widget.WidgetEvent;
import javafx.event.Event;
import javafx.scene.Node;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;

/**
 * Building a widget is an async process done by the javafx thread, thus it is necessary to ensure that it is fully built before attempting to use it.
 * @author Grégory Van den Borre
 */
class JavaFxBaseWidget <T extends JavaFxBaseWidget>{

    /**
     * Widget readiness state.
     */
    private boolean ready = false;

    private BaseCoordinate coordinates = Coordinates.ZERO;

    private Node node;

    /**
     * Set ready is to be invoked once the widget is completely built.
     */
    protected final void setReady(Node node) {
        this.node = node;
    }

    public final BaseCoordinate getCoordinates() {
        return this.coordinates;
    }

    public final int getLeft() {
        return this.coordinates.left;
    }

    public final int getRight() {
        return this.coordinates.left + this.coordinates.width;
    }

    public final int getTop() {
        return this.coordinates.top;
    }

    public final int getBottom() {
        return this.coordinates.top + this.coordinates.height;
    }

    protected final void updateCoordinates(BaseCoordinate coordinates) {
        this.coordinates = coordinates;
    }

    protected final void updateCoordinates(Position position) {
        this.coordinates = new Coordinates(this.coordinates.width, this.coordinates.height, position);
    }

    protected final void updateCoordinates(Size size) {
        this.coordinates = new Coordinates(size, this.coordinates.left, this.coordinates.top);
    }

    public T setVisible(boolean visible) {
        this.node.setVisible(visible);
        return (T)this;
    }

    public T requestFocus() {
        this.node.requestFocus();
        return (T)this;
    }

    public T fireEvent(WidgetEvent event) {
        if(event == WidgetEvent.MOUSE_LEFT_CLICK) {
            Event.fireEvent(this.node, new MouseEvent(MouseEvent.MOUSE_CLICKED, 0,
                    0, 0, 0, MouseButton.PRIMARY, 1, true, true, true, true,
                    true, true, true, true, true, true, null));
        }
        return (T)this;
    }
}
