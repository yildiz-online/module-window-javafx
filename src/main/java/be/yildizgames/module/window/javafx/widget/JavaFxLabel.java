package be.yildizgames.module.window.javafx.widget;

import be.yildizgames.module.color.Color;
import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.widget.WindowFont;
import be.yildizgames.module.window.widget.WindowTextLine;
import javafx.application.Platform;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

class JavaFxLabel extends JavaFxBaseWidget implements WindowTextLine {

    private Label label;

    private String text = "";

    JavaFxLabel(Pane pane) {
        Platform.runLater(() -> {
            this.label = new Label();
            pane.getChildren().add(this.label);
            this.setReady();
        });
    }

    @Override
    public WindowTextLine setText(String text) {
        this.runWhenReady(() -> {
            this.label.setText(text);
            this.text = text;
        });
        return this;
    }

    @Override
    public WindowTextLine setPosition(int left, int top) {
        this.runWhenReady(() -> {
            this.label.setLayoutX(left);
            this.label.setLayoutY(top);
        });
        return this;
    }

    @Override
    public WindowTextLine setColor(Color color) {
        return null;
    }

    @Override
    public String getText() {
        return this.text;
    }

    @Override
    public WindowTextLine setUnderline(boolean active) {
        this.runWhenReady(() -> {
            this.label.setUnderline(active);
        });
        return this;
    }

    @Override
    public WindowTextLine setFont(WindowFont font) {
        this.runWhenReady(() -> {
            System.out.println(this.label);
            System.out.println(JavaFxFont.getById(font.getId()));
            System.out.println(JavaFxFont.getById(font.getId()).getInnerFont());
            this.label.setFont(JavaFxFont.getById(font.getId()).getInnerFont());
        });
        return this;
    }

    @Override
    public WindowTextLine setCoordinates(Coordinates coordinates) {
        return null;
    }

    @Override
    public WindowTextLine setSize(Size size) {
        this.runWhenReady(() -> {
            this.label.setMaxWidth(size.width);
            this.label.setMaxHeight(size.height);
        });
        return this;
    }

    @Override
    public WindowTextLine setPosition(Position position) {
        this.runWhenReady(() -> {
            this.label.setLayoutX(position.left);
            this.label.setLayoutY(position.top);
        });
        return this;
    }

    @Override
    public WindowTextLine setVisible(boolean visible) {
        this.runWhenReady(() -> {
            this.label.setVisible(visible);
        });
        return this;
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
