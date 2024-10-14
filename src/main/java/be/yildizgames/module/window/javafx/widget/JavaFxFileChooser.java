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

import be.yildizgames.module.window.widget.WindowFileChooser;
import javafx.stage.Stage;

import java.nio.file.Path;

/**
 * @author Grégory Van den Borre
 */
class JavaFxFileChooser implements WindowFileChooser {

    private final javafx.stage.FileChooser fileChooser;

    private final Stage stage;

    JavaFxFileChooser(final Stage stage) {
        super();
        this.stage = stage;
        this.fileChooser = new javafx.stage.FileChooser();
        this.fileChooser.setInitialDirectory(Path.of(System.getProperty("user.dir")).toFile());
    }

    @Override
    public WindowFileChooser setTitle(String title) {
        this.fileChooser.setTitle(title);
        return this;
    }

    @Override
    public WindowFileChooser setInitialDirectory(Path path) {
        this.fileChooser.setInitialDirectory(path.toFile());
        return this;
    }

    @Override
    public Path open() {
        var result = this.fileChooser.showOpenDialog(this.stage);
        if (result != null) {
            return Path.of(result.toURI());
        }
        return Path.of(this.fileChooser.getInitialDirectory().toURI());
    }

    @Override
    public WindowFileChooser setAllowedExtensions(String... ext) {
        this.fileChooser.setSelectedExtensionFilter(new javafx.stage.FileChooser.ExtensionFilter("file", ext));
        return this;
    }
}
