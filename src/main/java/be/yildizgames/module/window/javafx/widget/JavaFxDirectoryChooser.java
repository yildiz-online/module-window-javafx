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

package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.widget.DirectoryChooser;
import javafx.stage.Stage;

import java.nio.file.Path;

/**
 * @author Grégory Van den Borre
 */
class JavaFxDirectoryChooser implements DirectoryChooser {

    private final javafx.stage.DirectoryChooser directoryChooser;

    private final Stage stage;

    JavaFxDirectoryChooser(final Stage stage) {
        super();
        this.stage = stage;
        this.directoryChooser = new javafx.stage.DirectoryChooser();
        this.directoryChooser.setInitialDirectory(Path.of(System.getProperty("user.dir")).toFile());
    }

    @Override
    public final JavaFxDirectoryChooser setTitle(String title) {
        this.directoryChooser.setTitle(title);
        return this;
    }

    @Override
    public final JavaFxDirectoryChooser setInitialDirectory(Path path) {
        this.directoryChooser.setInitialDirectory(path.toFile());
        return this;
    }

    @Override
    public final Path open() {
        var result = this.directoryChooser.showDialog(this.stage);
        if(result!= null) {
            return Path.of(result.toURI());
        }
        return Path.of(this.directoryChooser.getInitialDirectory().toURI());
    }
}
