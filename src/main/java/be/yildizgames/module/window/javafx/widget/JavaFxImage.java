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

import be.yildizgames.module.coordinate.Coordinates;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.input.MouseOverListener;
import be.yildizgames.module.window.widget.ImageEffect;
import be.yildizgames.module.window.widget.ImageMetadata;
import be.yildizgames.module.window.widget.WindowImage;
import be.yildizgames.module.window.widget.WindowImageProvider;
import javafx.scene.effect.ColorAdjust;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

/**
 * @author Grégory Van den Borre
 */
class JavaFxImage extends JavaFxBaseWidget<JavaFxImage> implements WindowImage {

    private final WindowImageProvider provider;

    private final ImageView imageView;

    private ImageMetadata metadata = new ImageMetadata(0.0, 0.0);

    private String imageFileName = "";

    JavaFxImage(Pane pane, WindowImageProvider provider, String image) {
        super();
        this.provider = provider;
        this.imageView = new ImageView();
        this.setImage(image);
        pane.getChildren().add(this.imageView);
        this.setReady(this.imageView);
    }

    @Override
    public final WindowImage setCoordinates(Coordinates coordinates) {
        this.updateCoordinates(coordinates);
            this.imageView.setLayoutX(coordinates.left);
            this.imageView.setLayoutY(coordinates.top);
            this.imageView.setFitHeight(coordinates.height);
            this.imageView.setFitWidth(coordinates.width);

        return this;
    }

    @Override
    public final WindowImage setSize(Size size) {
        this.updateCoordinates(size);
            this.imageView.setFitHeight(size.height);
            this.imageView.setFitWidth(size.width);

        return this;
    }

    @Override
    public final WindowImage setPosition(Position position) {
        this.updateCoordinates(position);
            this.imageView.setLayoutX(position.left);
            this.imageView.setLayoutY(position.top);

        return this;
    }

    @Override
    public final WindowImage setImage(String url) {
        if(!this.imageFileName.equals(url)) {
            var image = new Image(provider.getImage(url));
            this.metadata = new ImageMetadata(image.getWidth(), image.getHeight());
            this.imageView.setImage(new Image(provider.getImage(url)));
            this.imageFileName = url;
        }
        return this;
    }

    @Override
    public final WindowImage addEffect(ImageEffect effect) {
            if(effect == ImageEffect.DESATURATE) {
                ColorAdjust adjust = new ColorAdjust();
                adjust.setSaturation(-1);
                this.imageView.setEffect(adjust);
            } else if(effect == ImageEffect.NORMAL_SATURATION) {
                ColorAdjust adjust = new ColorAdjust();
                adjust.setSaturation(0);
                this.imageView.setEffect(adjust);
            }

        return this;
    }

    @Override
    public final WindowImage addOnMouseOverListener(MouseOverListener l) {
        this.imageView.setOnMouseEntered(e -> l.enter());
        this.imageView.setOnMouseExited(e -> l.quit());

        return this;
    }

    @Override
    public final ImageMetadata getLoadedImageMetadata() {
        return this.metadata;
    }
}
