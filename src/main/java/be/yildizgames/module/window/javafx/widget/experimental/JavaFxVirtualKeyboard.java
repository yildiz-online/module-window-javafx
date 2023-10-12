/*
 * MIT License
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package be.yildizgames.module.window.javafx.widget.experimental;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinates.FullCoordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.input.Key;
import be.yildizgames.module.window.input.KeyboardListener;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.experimental.KeyboardLayout;
import be.yildizgames.module.window.widget.experimental.KeyboardLayoutKey;
import be.yildizgames.module.window.widget.experimental.SimpleQwertyKeyboardLayout;
import be.yildizgames.module.window.widget.experimental.VirtualKeyboard;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableStringValue;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.Pane;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxVirtualKeyboard implements VirtualKeyboard {

    private final KeyboardLayout layout = new SimpleQwertyKeyboardLayout();

    private final Map<Key, Button> keys = new HashMap<>();
    private final WindowImageProvider imageProvider;
    private final Pane pane;

    private final Modifiers modifiers;

    private int positionX;

    private int positionY;

    private int keyWidth = 30;

    private int keyHeight = 30;

    private final List<KeyboardListener> listenerList = new ArrayList<>();

    /**
     * Creates a Virtual Keyboard.
     *
     * @param target The node that will receive KeyEvents from this keyboard.
     *               If target is null, KeyEvents will be dynamically forwarded to the focus owner
     *               in the Scene containing this keyboard.
     */
    public JavaFxVirtualKeyboard(KeyboardListener target, WindowImageProvider imageProvider, Pane pane) {
        super();
        this.pane = pane;
        this.imageProvider = imageProvider;
        this.modifiers = new Modifiers();
        this.listenerList.add(target);

        // non-regular buttons (don't respond to Shift)
        final Button escape = createNonshiftableButton("Esc", Key.ESC, modifiers);
        final Button backspace = createNonshiftableButton("Backspace", Key.BACK_SPACE, modifiers);
        final Button delete = createNonshiftableButton("Del", Key.DELETE, modifiers);
        final Button enter = createNonshiftableButton("Enter", Key.ENTER, modifiers);
        final Button tab = createNonshiftableButton("Tab", Key.TAB, modifiers);

        for(var layoutKey: this.layout.getKeys()) {
            var key = createShiftableButton(layoutKey, modifiers);
            this.pane.getChildren().addAll(key);
            this.keys.put(layoutKey.code(), key);
        }
        updateLayout();
    }

    private void updateLayout() {
        int inset = 10;
        var max = 0;
        for (int row = 0; row < this.layout.getNumberOfRows(); row++) {
             var c = this.layout.getNumberOfKeyForRow(row);
             if(c > max) {
                 max = c;
             }
        }
        for (int row = 0; row < this.layout.getNumberOfRows(); row++) {
            int keys = this.layout.getNumberOfKeyForRow(row);
            int spaces = ((max - keys) >> 1) * keyWidth;
            for (int colums = 0; colums < keys; colums++) {
                var key = this.keys.get(layout.getKey(row, colums).code());
                key.setLayoutY((row * keyHeight) + inset + positionY);
                key.setLayoutX((colums * keyWidth) + inset + spaces + positionX);
            }
        }
    }

    private Button createShiftableButton(final KeyboardLayoutKey key, Modifiers modifiers) {
        final ReadOnlyBooleanProperty letter = new SimpleBooleanProperty(key.isLetter());
        final StringBinding text =
                Bindings.when(modifiers.shiftDown().or(modifiers.capsLockOn().and(letter)))
                        .then(key.shifted())
                        .otherwise(key.unshifted());
        var button = createButton(text, key.code(), modifiers);
        button.setMinSize(this.keyWidth, this.keyHeight);
        button.setMaxSize(this.keyWidth, this.keyHeight);
        return button;
    }

    private Button createNonshiftableButton(final String text, final Key code, final Modifiers modifiers) {
        StringProperty textProperty = new SimpleStringProperty(text);
        return createButton(textProperty, code, modifiers);
    }

    private Button createButton(final ObservableStringValue text, final Key code, final Modifiers modifiers) {
        final Button button = new Button();
        button.textProperty().bind(text);
        button.setFocusTraversable(false);
        button.setOnAction(event -> {
            final String character;
            if (text.get().length() == 1) {
                character = text.get();
                listenerList.forEach(l -> l.keyPressed(character.charAt(0)));
                listenerList.forEach(l -> l.keyReleased(character.charAt(0)));
            }

            modifiers.releaseKeys();
        });
        this.keys.put(code, button);
        return button;
    }

    @Override
    public final VirtualKeyboard setSize(Key key, int width, int height) {
        this.keys.get(key).setMinSize(width, height);
        this.keys.get(key).setMaxSize(width, height);
        if(key != Key.SPACE) {
            this.keyWidth = width;
            this.keyHeight = height;
            this.updateLayout();
        }
        return this;
    }

    @Override
    public final VirtualKeyboard addListener(KeyboardListener listener) {
        this.listenerList.add(listener);
        return this;
    }

    @Override
    public final VirtualKeyboard setPosition(Position position) {
        this.positionX = position.getLeft();
        this.positionY = position.getTop();
        this.updateLayout();
        return this;
    }

    @Override
    public final Size getSize() {
        return FullCoordinates.size((int) this.pane.getWidth(), (int) this.pane.getHeight());
    }

    @Override
    public final int getLeft() {
        return this.positionX;
    }

    @Override
    public final int getTop() {
        return this.positionY;
    }

    @Override
    public final VirtualKeyboard setSize(Size size) {
        this.pane.setMinSize(size.getWidth(), size.getHeight());
        this.pane.setMaxSize(size.getWidth(), size.getHeight());
        return this;
    }

    @Override
    public final VirtualKeyboard setImage(Key key, String image) {
        var myBI = new BackgroundImage(
                new Image(this.imageProvider.getImage(image)),
                BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        this.keys.get(key).setBackground(new Background(myBI));
        return this;
    }

    @Override
    public VirtualKeyboard setColor(Key key, Color color) {
        this.keys.get(key).setBackground(new Background(
                new BackgroundFill(new javafx.scene.paint.Color(
                        color.normalizedRedValue,
                        color.normalizedGreenValue,
                        color.normalizedBlueValue,
                        color.normalizedAlphaValue),
                        CornerRadii.EMPTY, Insets.EMPTY)));
        return this;
    }

    @Override
    public final VirtualKeyboard press(Key key) {
        this.keys.get(key).fire();
        return this;
    }

    @Override
    public final VirtualKeyboard setBackground(String image) {

        return this;
    }

    @Override
    public final int getWidth() {
        return (int) this.pane.getWidth();
    }

    @Override
    public final VirtualKeyboard setBackground(Color color) {
        return this;
    }

    @Override
    public final KeyboardLayout getLayout() {
        return this.layout;
    }

    @Override
    public VirtualKeyboard toFront() {
        this.keys.values().forEach(Node::toFront);
        return this;
    }

    @Override
    public final VirtualKeyboard setVisible(boolean visible) {
        this.keys.values().forEach(k -> k.setVisible(visible));
        if(visible) {
            this.keys.values().forEach(Node::toFront);
        }
        return this;
    }

    @Override
    public final VirtualKeyboard shift(boolean selected) {
        this.modifiers.shift.setSelected(selected);
        return this;
    }

    @Override
    public final VirtualKeyboard capsLock(boolean selected) {
        this.modifiers.capsLock.setSelected(selected);
        return this;
    }

    private static class Modifiers {
        private final ToggleButton shift;
        private final ToggleButton shift2;
        private final ToggleButton ctrl;
        private final ToggleButton alt;
        private final ToggleButton meta;
        private final ToggleButton capsLock;

        Modifiers() {
            this.shift = createToggle("Shift");
            this.shift2 = createToggle("Shift");
            this.ctrl = createToggle("Ctrl");
            this.alt = createToggle("Alt");
            this.meta = createToggle("Meta");
            this.capsLock = createToggle("Caps");

            shift2.selectedProperty().bindBidirectional(shift.selectedProperty());
        }

        private ToggleButton createToggle(final String text) {
            final ToggleButton tb = new ToggleButton(text);
            tb.setFocusTraversable(false);
            return tb;
        }

        public Node shiftKey() {
            return shift;
        }

        public Node secondShiftKey() {
            return shift2;
        }

        public Node ctrlKey() {
            return ctrl;
        }

        public Node altKey() {
            return alt;
        }

        public Node metaKey() {
            return meta;
        }

        public Node capsLockKey() {
            return capsLock;
        }

        public BooleanProperty shiftDown() {
            return shift.selectedProperty();
        }

        public BooleanProperty ctrlDown() {
            return ctrl.selectedProperty();
        }

        public BooleanProperty altDown() {
            return alt.selectedProperty();
        }

        public BooleanProperty metaDown() {
            return meta.selectedProperty();
        }

        public BooleanProperty capsLockOn() {
            return capsLock.selectedProperty();
        }

        public void releaseKeys() {
            shift.setSelected(false);
            ctrl.setSelected(false);
            alt.setSelected(false);
            meta.setSelected(false);
        }
    }
}
