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

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.FullCoordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import be.yildizgames.module.window.input.MouseOverListener;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowImageProvider;
import be.yildizgames.module.window.widget.WindowMultiLayerImage;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * @author Grégory Van den Borre
 */
class JavaFxMultiLayerImage extends JavaFxBaseWidget<JavaFxMultiLayerImage> implements WindowMultiLayerImage {

    private final WindowImageProvider provider;

    private final List<JavaFxImage> imageViews = new ArrayList<>();

    private final StackPane stackPane = new StackPane();

    private boolean preserveRatio;
    private Size size = FullCoordinates.ZERO;

    JavaFxMultiLayerImage(Pane pane, WindowImageProvider provider) {
        super();
        this.provider = provider;
        pane.getChildren().add(this.stackPane);
        this.setReady(this.stackPane);
    }

    @Override
    public final WindowMultiLayerImage setCoordinates(Coordinates coordinates) {
        this.stackPane.setLayoutX(coordinates.getLeft());
        this.stackPane.setLayoutY(coordinates.getTop());
        this.stackPane.setPrefHeight(coordinates.getHeight());
        this.stackPane.setPrefWidth(coordinates.getWidth());
        this.imageViews.forEach(i -> i.setCoordinates(coordinates));

        return this;
    }

    @Override
    public final WindowMultiLayerImage setSize(Size size) {
        this.size = size;
        this.stackPane.setPrefHeight(size.getHeight());
        this.stackPane.setPrefWidth(size.getWidth());
        this.imageViews.forEach(i -> i.setSize(size));
        return this;
    }

    @Override
    public final WindowMultiLayerImage setPosition(Position position) {
        this.stackPane.setLayoutX(position.getLeft());
        this.stackPane.setLayoutY(position.getTop());

        return this;
    }

    @Override
    public final WindowMultiLayerImage setImage(String url, int layer) {
        if (layer >= this.imageViews.size()) {
            for (int i = this.imageViews.size(); i <= layer; i++) {
                var image = new JavaFxImage(new ImageView(), provider);
                this.imageViews.add(image);
                if(this.preserveRatio) {
                    image.preserveRatio();
                }
            }
        }
        this.imageViews.get(layer).setImage(url);
        this.stackPane.getChildren().clear();
        this.imageViews.forEach(i -> this.stackPane.getChildren().add(i.getNode()));
        this.imageViews.forEach(i -> i.setSize(this.size));
        return this;
    }

    @Override
    public final Optional<WindowImage> getImage(int layer) {
        return Optional.ofNullable(this.imageViews.get(layer));
    }

    @Override
    public final WindowMultiLayerImage addOnMouseOverListener(MouseOverListener l) {
        this.stackPane.setOnMouseEntered(e -> l.enter());
        this.stackPane.setOnMouseExited(e -> l.quit());

        return this;
    }

    @Override
    public final WindowMultiLayerImage toFront() {
        this.stackPane.toFront();
        return this;
    }

    @Override
    public final WindowMultiLayerImage toBack() {
        this.stackPane.toBack();
        return this;
    }

    @Override
    public WindowMultiLayerImage preserveRatio() {
        this.imageViews.forEach(JavaFxImage::preserveRatio);
        this.preserveRatio = true;
        return this;
    }
}
