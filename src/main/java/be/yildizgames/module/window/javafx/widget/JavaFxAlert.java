/*
 * MIT License
 *
 * Copyright (c) 2021 Grégory Van den Borre
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated documentation files (the "Software"), to deal in the Software without restriction, including without limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies of the Software, and to permit persons to whom the Software is furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM, OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE SOFTWARE.
 */

package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.widget.WindowAlert;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Grégory Van den Borre
 */
class JavaFxAlert implements WindowAlert {

    private final Alert alert;

    private static final Map<ButtonType, AlertButtonType> BUTTON_TYPE_MAP = new HashMap<>();

    static {
        BUTTON_TYPE_MAP.put(ButtonType.OK, AlertButtonType.OK);
        BUTTON_TYPE_MAP.put(ButtonType.APPLY, AlertButtonType.APPLY);
        BUTTON_TYPE_MAP.put(ButtonType.CANCEL, AlertButtonType.CANCEL);
        BUTTON_TYPE_MAP.put(ButtonType.CLOSE, AlertButtonType.CLOSE);
        BUTTON_TYPE_MAP.put(ButtonType.NO, AlertButtonType.NO);
        BUTTON_TYPE_MAP.put(ButtonType.FINISH, AlertButtonType.FINISH);
        BUTTON_TYPE_MAP.put(ButtonType.NEXT, AlertButtonType.NEXT);
        BUTTON_TYPE_MAP.put(ButtonType.PREVIOUS, AlertButtonType.PREVIOUS);
        BUTTON_TYPE_MAP.put(ButtonType.YES, AlertButtonType.YES);
    }

    JavaFxAlert(Stage stage) {
        super();
        this.alert = new Alert(Alert.AlertType.NONE);
        this.alert.initOwner(stage);
        this.alert.initModality(Modality.NONE);
    }

    @Override
    public final WindowAlert setType(AlertType type) {
        switch (type) {
            case INFO: this.alert.setAlertType(Alert.AlertType.INFORMATION);
            break;
            case CONFIRM: this.alert.setAlertType(Alert.AlertType.CONFIRMATION);
            break;
            case WARNING: this.alert.setAlertType(Alert.AlertType.WARNING);
            break;
            case ERROR: this.alert.setAlertType(Alert.AlertType.ERROR);
            break;
            default:this.alert.setAlertType(Alert.AlertType.NONE);
        }
        return this;
    }

    @Override
    public final WindowAlert setHeaderText(String header) {
        this.alert.setHeaderText(header);
        return this;
    }

    @Override
    public final WindowAlert setContentText(String content) {
        this.alert.setContentText(content);
        return this;
    }

    @Override
    public final WindowAlert setTitle(String title) {
        this.alert.setTitle(title);
        return this;
    }

    @Override
    public final WindowAlert open(OnClickAlertButtonListener f) {
        var result = this.alert.showAndWait();
        result.ifPresent(button -> f.onClick(BUTTON_TYPE_MAP.get(button)));
        return this;
    }

    @Override
    public final WindowAlert close() {
        this.alert.hide();
        return this;
    }
}
