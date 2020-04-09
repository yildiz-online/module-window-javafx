/*
 *
 * This file is part of the Yildiz-Engine project, licenced under the MIT License  (MIT)
 *
 * Copyright (c) 2019 Grégory Van den Borre
 *
 * More infos available: https://engine.yildiz-games.be
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy of this software and associated
 * documentation files (the "Software"), to deal in the Software without restriction, including without
 * limitation the rights to use, copy, modify, merge, publish, distribute, sublicense, and/or sell copies
 * of the Software, and to permit persons to whom the Software is furnished to do so,
 * subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all copies or substantial
 *  portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR IMPLIED, INCLUDING BUT NOT LIMITED TO THE
 *  WARRANTIES OF MERCHANTABILITY, FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE AUTHORS
 * OR COPYRIGHT  HOLDERS BE LIABLE FOR ANY CLAIM,
 *  DAMAGES OR OTHER LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE  SOFTWARE.
 *
 *
 */

package be.yildizgames.module.window.javafx.widget;


import be.yildizgames.module.coordinate.BaseCoordinate;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import be.yildizgames.module.window.widget.WindowMediaPlayer;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

import java.net.MalformedURLException;
import java.nio.file.Path;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxMediaPlayer extends JavaFxBaseWidget<JavaFxMediaPlayer> implements WindowMediaPlayer {

    private MediaView mediaView;

    private boolean playing;
    
    JavaFxMediaPlayer(Pane pane ) {
            this.mediaView = new MediaView();
            pane.getChildren().add(this.mediaView);
            this.setReady(this.mediaView);

    }

    @Override
    public JavaFxMediaPlayer setMedia(String url) {
            Media media = new Media(url);
            MediaPlayer mediaPlayer = new MediaPlayer(media);
           // mediaPlayer.setAutoPlay(true);
            this.mediaView.setMediaPlayer(mediaPlayer);

        return this;
    }

    @Override
    public final JavaFxMediaPlayer setCoordinates(BaseCoordinate coordinates) {
        this.updateCoordinates(coordinates);
            this.mediaView.setLayoutX(coordinates.left);
            this.mediaView.setLayoutY(coordinates.top);
            this.mediaView.setFitHeight(coordinates.height);
            this.mediaView.setFitWidth(coordinates.width);

        return this;
    }

    @Override
    public final JavaFxMediaPlayer setSize(Size size) {
        this.updateCoordinates(size);
            this.mediaView.setFitHeight(size.height);
            this.mediaView.setFitWidth(size.width);

        return this;
    }

    @Override
    public final JavaFxMediaPlayer setPosition(Position position) {
        this.updateCoordinates(position);
            this.mediaView.setLayoutX(position.left);
            this.mediaView.setLayoutY(position.top);

        return this;
    }

    @Override
    public WindowMediaPlayer setMedia(Path path) {
        try {
            this.setMedia(path.toUri().toURL().toString());
        } catch (MalformedURLException e) {
            throw new IllegalArgumentException(e);
        }
        return this;
    }

    @Override
    public WindowMediaPlayer play() {
        this.mediaView.getMediaPlayer().play();
        playing = true;
        return this;
    }

    @Override
    public WindowMediaPlayer stop() {
        if (playing) {
            this.mediaView.getMediaPlayer().stop();
            playing = false;
        }

        return this;
    }

    public JavaFxMediaPlayer setVisible(boolean visible) {
        this.mediaView.setVisible(visible);
        return this;
    }

}
