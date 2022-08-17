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

import be.yildizgames.module.window.input.Key;
import be.yildizgames.module.window.input.KeyboardListener;
import be.yildizgames.module.window.input.KeyboardState;
import be.yildizgames.module.window.javafx.widget.JavaFxPopup;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.experimental.KeyboardLayout;
import be.yildizgames.module.window.widget.experimental.KeyboardLayoutKey;
import be.yildizgames.module.window.widget.experimental.QwertyKeyboardLayout;
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
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import javafx.scene.layout.VBox;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxVirtualKeyboard implements VirtualKeyboard {

    private final VBox root;

    private final KeyboardLayout layout = new QwertyKeyboardLayout();

    private final Map<Key, Button> keys = new HashMap<>();
    private final WindowImageProvider imageProvider;
    private final JavaFxPopup parent;

    private int positionInRow;

    private int positionInColumn;

    /**
     * Creates a Virtual Keyboard.
     *
     * @param target The node that will receive KeyEvents from this keyboard.
     *               If target is null, KeyEvents will be dynamically forwarded to the focus owner
     *               in the Scene containing this keyboard.
     * @param parent
     */
    public JavaFxVirtualKeyboard(KeyboardListener target, WindowImageProvider imageProvider, JavaFxPopup parent) {
        super();
        this.parent = parent;
        this.imageProvider = imageProvider;
        this.root = new VBox(5);
        this.root.prefWidthProperty().bind(parent.widthProperty().multiply(0.95));
        root.setPadding(new Insets(10));
        final Modifiers modifiers = new Modifiers();

        // non-regular buttons (don't respond to Shift)
        final Button escape = createNonshiftableButton("Esc", Key.ESC, modifiers, target);
        final Button backspace = createNonshiftableButton("Backspace", Key.BACK_SPACE, modifiers, target);
        final Button delete = createNonshiftableButton("Del", Key.DELETE, modifiers, target);
        final Button enter = createNonshiftableButton("Enter", Key.ENTER, modifiers, target);
        final Button tab = createNonshiftableButton("Tab", Key.TAB, modifiers, target);

        // "Extras" to go at the left or right end of each row of buttons.
        final Node[][] extraLeftButtons = new Node[][]{{escape}, {tab}, {modifiers.capsLockKey()}, {modifiers.shiftKey()}};
        final Node[][] extraRightButtons = new Node[][]{{backspace}, {delete}, {enter}, {modifiers.secondShiftKey()}};

        // build layout
        for (int row = 0; row < layout.getNumberOfRows(); row++) {
            HBox hbox = new HBox(5);
            hbox.setAlignment(Pos.CENTER);
            root.getChildren().add(hbox);

            //  hbox.getChildren().addAll(extraLeftButtons[row]);
            for (int k = 0; k < this.layout.getNumberOfKeyForRow(row); k++) {
                hbox.getChildren().add(createShiftableButton(layout.getKey(row, k), modifiers, target));
            }
            //  hbox.getChildren().addAll(extraRightButtons[row]);
        }

        final Button spaceBar = createNonshiftableButton(" ", Key.SPACE, modifiers, target);
        spaceBar.setMaxWidth(Double.POSITIVE_INFINITY);
        HBox.setHgrow(spaceBar, Priority.ALWAYS);

        final HBox bottomRow = new HBox(5);
        bottomRow.setAlignment(Pos.CENTER);
        bottomRow.getChildren().addAll(/*modifiers.ctrlKey(), modifiers.altKey(),
                modifiers.metaKey(),*/ spaceBar);
        root.getChildren().add(bottomRow);
    }

    /**
     * Visual component displaying this keyboard. The returned node has a style class of "virtual-keyboard".
     * Buttons in the view have a style class of "virtual-keyboard-button".
     *
     * @return a view of the keyboard.
     */
    public Node view() {
        return root;
    }

    private Button createShiftableButton(final KeyboardLayoutKey key, Modifiers modifiers, final KeyboardListener target) {
        final ReadOnlyBooleanProperty letter = new SimpleBooleanProperty(key.isLetter());
        final StringBinding text =
                Bindings.when(modifiers.shiftDown().or(modifiers.capsLockOn().and(letter)))
                        .then(key.getShifted())
                        .otherwise(key.getUnshifted());
        return createButton(text, key.getCode(), modifiers, target);
    }

    private Button createNonshiftableButton(final String text, final Key code, final Modifiers modifiers, final KeyboardListener target) {
        StringProperty textProperty = new SimpleStringProperty(text);
        return createButton(textProperty, code, modifiers, target);
    }

    private Button createButton(final ObservableStringValue text, final Key code, final Modifiers modifiers, final KeyboardListener target) {
        final Button button = new Button();
        button.textProperty().bind(text);
        button.setFocusTraversable(false);
        button.setOnAction(event -> {
            final String character;
            if (text.get().length() == 1) {
                character = text.get();
                target.keyPressed(character.charAt(0), modifiers);
                target.keyReleased(character.charAt(0), modifiers);
            } else {
                character = KeyEvent.CHAR_UNDEFINED;
            }

            modifiers.releaseKeys();
        });
        this.keys.put(code, button);
        return button;
    }

    @Override
    public VirtualKeyboard setSize(Key key, int width, int height) {
        this.keys.get(key).setMinSize(width, height);
        this.keys.get(key).setMaxSize(width, height);
        return this;
    }

    @Override
    public VirtualKeyboard setImage(Key key, String image) {
        var myBI = new BackgroundImage(
                new Image(this.imageProvider.getImage(image)),
                BackgroundRepeat.SPACE, BackgroundRepeat.SPACE, BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, true, true, true, true));
        this.keys.get(key).setBackground(new Background(myBI));
        return this;
    }

    private static class Modifiers implements KeyboardState {
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
