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

import be.yildizgames.module.color.Color;
import be.yildizgames.module.window.javafx.widget.experimental.CallBack;
import be.yildizgames.module.window.widget.WindowButton;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowInputBox;
import be.yildizgames.module.window.widget.WindowPopup;
import be.yildizgames.module.window.widget.WindowTextLine;
import javafx.stage.Stage;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxPopup implements WindowPopup {


    private JavaFxWindowShell modal;


    public JavaFxPopup(WindowImageProvider imageProvider, Stage parent) {
        super();
        this.modal = new JavaFxWindowShell(imageProvider, parent);
    }


    @Override
    public final WindowTextLine createTextLine() {
       return this.modal.createTextLine();
    }

    @Override
    public final WindowPopup setTitle(String title) {
        this.modal.setTitle(title);
        return this;
    }

    @Override
    public final WindowPopup setBackground(Color color) {
        this.modal.setBackground(color);
        return this;
    }

    @Override
    public final WindowPopup setBackground(String file) {
        this.modal.setBackground(file);
        return this;
    }

    @Override
    public final WindowPopup setSize(int width, int height) {
        this.modal.setSize(width, height);
        return this;
    }

    @Override
    public final WindowButton createButton() {
        return this.modal.createButton();
    }

    @Override
    public final WindowButtonText createTextButton() {
        return this.modal.createTextButton();
    }

    @Override
    public final void close() {
        this.modal.close();
    }

    @Override
    public final WindowPopup centerOnScreen() {
        this.modal.centerOnScreen();
        return this;
    }

    public final WindowPopup listenHidden(CallBack callBack) {
        this.modal.addOnHiddenListener(callBack);
        return this;
    }

    @Override
    public final WindowPopup setPosition(int left, int top) {
        this.modal.setPosition(left, top);
        return this;
    }

    @Override
    public final WindowInputBox createInputBox() {
        return this.modal.createInputBox();
    }

    @Override
    public final WindowImage createImage(String image) {
        return this.modal.createImage(image);
    }

    public void createVirtualKeyboard() {
         this.modal.createVirtualKeyboard();
    }
}
