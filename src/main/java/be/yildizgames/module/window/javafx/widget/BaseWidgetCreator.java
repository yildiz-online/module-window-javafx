package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.window.input.KeyboardListener;
import be.yildizgames.module.window.javafx.widget.experimental.JavaFxVirtualKeyboard;
import be.yildizgames.module.window.widget.WindowCanvas;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowMenuBar;
import be.yildizgames.module.window.widget.WindowMenuBarElementDefinition;
import be.yildizgames.module.window.widget.WindowModal;
import be.yildizgames.module.window.widget.WindowModalFile;
import be.yildizgames.module.window.widget.WindowTextArea;
import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import be.yildizgames.module.window.widget.WindowWidgetCreator;
import javafx.scene.layout.Pane;

/**
 * @author Grégory Van den Borre
 */
abstract class BaseWidgetCreator implements WindowWidgetCreator {

    private final Pane pane;
    private final JavaFxWindowShell shell;
    private final WindowImageProvider imageProvider;

    protected BaseWidgetCreator(Pane pane, JavaFxWindowShell shell, WindowImageProvider imageProvider) {
        super();
        this.pane = pane;
        this.shell = shell;
        this.imageProvider = imageProvider;
    }

    @Override
    public final JavaFxShape createRectangle() {
        return new JavaFxShape(this.pane);
    }

    @Override
    public final JavaFxToggle createToggle() {
        return new JavaFxToggle(this.pane);
    }

    @Override
    public final JavaFxLabel createTextLine() {
        return new JavaFxLabel(this.pane, shell);
    }

    @Override
    public final JavaFxButton createButton() {
        return new JavaFxButton(this.pane, this.imageProvider);
    }

    @Override
    public final JavaFxImage createImage(String image) {
        return new JavaFxImage(this.pane, this.imageProvider, image);
    }

    @Override
    public final JavaFxProgressBar createProgressBar() {
        return new JavaFxProgressBar(this.pane);
    }

    @Override
    public final JavaFxDropDown createDropdown() {
        return new JavaFxDropDown(this.pane);
    }

    @Override
    public final JavaFxButton createTextButton() {
        return new JavaFxButton(this.pane, this.imageProvider);
    }

    @Override
    public final JavaFxInputBox createInputBox() {
        return new JavaFxInputBox(this.pane);
    }

    @Override
    public final JavaFxCheckBox createCheckBox() {
        return new JavaFxCheckBox(this.pane);
    }

    @Override
    public final JavaFxMediaPlayer createMediaPlayer() {
        return new JavaFxMediaPlayer(this.pane);
    }

    @Override
    public final JavaFxMultiLayerImage createMultiLayerImage() {
        return new JavaFxMultiLayerImage(this.pane, this.imageProvider);
    }

    @Override
    public final JavaFxVirtualKeyboard createVirtualKeyboard(KeyboardListener listener) {
        return new JavaFxVirtualKeyboard(listener, this.imageProvider, this.pane);
    }

    @Override
    public final JavaFxNotificationPane createNotificationPane() {
        return new JavaFxNotificationPane(this.pane);
    }

    @Override
    public final WindowModal createMessageBox() {
        return null;
    }

    @Override
    public final WindowModal createMessageButtonBox() {
        return null;
    }

    @Override
    public final WindowTextArea createTextArea() {
        return null;
    }

    @Override
    public final WindowMenuBar createMenuBar(WindowMenuBarElementDefinition... elements) {
        return null;
    }

    @Override
    public final WindowTreeRoot createTreeRoot(int width, int height, WindowTreeElement... elements) {
        return null;
    }

    @Override
    public final WindowModalFile createOpenFileBox() {
        return null;
    }

    @Override
    public final WindowCanvas createCanvas() {
        return null;
    }

    @Override
    public final JavaFxButton createButton(String background, String hover) {
        return null;
    }

    @Override
    public final JavaFxWindowShell getWindow() {
        return this.shell;
    }
}
