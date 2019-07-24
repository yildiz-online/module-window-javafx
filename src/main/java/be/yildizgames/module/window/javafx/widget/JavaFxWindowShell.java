package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.window.ScreenSize;
import be.yildizgames.module.window.javafx.internal.JavaFxApplication;
import be.yildizgames.module.window.widget.WindowButton;
import be.yildizgames.module.window.widget.WindowButtonText;
import be.yildizgames.module.window.widget.WindowDropdown;
import be.yildizgames.module.window.widget.WindowFont;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowInputBox;
import be.yildizgames.module.window.widget.WindowMenuBar;
import be.yildizgames.module.window.widget.WindowMenuBarElementDefinition;
import be.yildizgames.module.window.widget.WindowModal;
import be.yildizgames.module.window.widget.WindowModalFile;
import be.yildizgames.module.window.widget.WindowProgressBar;
import be.yildizgames.module.window.widget.WindowShell;
import be.yildizgames.module.window.widget.WindowTextArea;
import be.yildizgames.module.window.widget.WindowTextLine;
import be.yildizgames.module.window.widget.WindowTreeElement;
import be.yildizgames.module.window.widget.WindowTreeRoot;
import javafx.application.Platform;
import javafx.stage.Stage;

public class JavaFxWindowShell implements WindowShell {

    private Stage stage;

    public JavaFxWindowShell(Stage stage) {
        this.stage = stage;
    }

    public JavaFxWindowShell() {
        Platform.runLater(() -> createStage());
    }

    private void createStage() {
        this.stage = new Stage();
        this.stage.show();
    }

    @Override
    public WindowShell setTitle(String title) {
        Platform.runLater(() -> stage.setTitle(title));
        return this;
    }

    @Override
    public WindowShell setIcon(String file) {
        return null;
    }

    @Override
    public WindowShell setBackground(Color color) {

        return null;
    }

    @Override
    public WindowShell setBackground(String file) {
        return null;
    }

    @Override
    public WindowShell setSize(int width, int height) {
        return null;
    }

    @Override
    public WindowShell setFullScreen() {
        return null;
    }

    @Override
    public ScreenSize getSize() {
        return null;
    }

    @Override
    public ScreenSize getMonitorSize() {
        return null;
    }

    @Override
    public void open() {

    }

    @Override
    public void close() {

    }

    @Override
    public void update() {

    }

    @Override
    public void checkForEvent() {

    }

    @Override
    public WindowModal createMessageBox() {
        return null;
    }

    @Override
    public WindowModal createMessageButtonBox() {
        return null;
    }

    @Override
    public WindowTextArea createTextArea() {
        return null;
    }

    @Override
    public WindowTextLine createTextLine() {
        return null;
    }

    @Override
    public WindowButton createButton() {
        return null;
    }

    @Override
    public WindowButton createButton(String background, String hover) {
        return null;
    }

    @Override
    public WindowImage createImage(String image) {
        return null;
    }

    @Override
    public WindowProgressBar createProgressBar() {
        return null;
    }

    @Override
    public WindowTreeRoot createTreeRoot(int width, int height, WindowTreeElement... elements) {
        return null;
    }

    @Override
    public WindowDropdown createDropdown() {
        return null;
    }

    @Override
    public WindowButtonText createTextButton() {
        return null;
    }

    @Override
    public WindowInputBox createInputBox() {
        return null;
    }

    @Override
    public WindowShell createChildWindow() {
        return null;
    }

    @Override
    public WindowMenuBar createMenuBar(WindowMenuBarElementDefinition... elements) {
        return null;
    }

    @Override
    public WindowModalFile createOpenFileBox() {
        return null;
    }

    @Override
    public WindowFont createFont(String path, int height) {
        return null;
    }

    @Override
    public WindowShell setCoordinates(Coordinates coordinates) {
        return null;
    }

    @Override
    public WindowShell setVisible(boolean visible) {
        return null;
    }

    @Override
    public int getLeft() {
        return 0;
    }

    @Override
    public int getRight() {
        return 0;
    }

    @Override
    public int getTop() {
        return 0;
    }

    @Override
    public int getBottom() {
        return 0;
    }
}
