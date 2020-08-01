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

import be.yildizgames.module.coordinate.BaseCoordinate;
import be.yildizgames.module.coordinate.Position;
import be.yildizgames.module.coordinate.Size;
import javafx.scene.layout.Pane;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;

/**
 * @author Grégory Van den Borre
 */
class FileMediaPlayer extends JavaFxBaseWidget<FileMediaPlayer> implements MediaPlayerStrategy {

    private final MediaView mediaView;

    FileMediaPlayer(Pane pane) {
        this.mediaView = new MediaView();
        pane.getChildren().add(this.mediaView);
        this.setReady(this.mediaView);
    }

    @Override
    public void setMedia(String url) {
        System.out.println("FILE");
        Media media = new Media(url);
        MediaPlayer mediaPlayer = new MediaPlayer(media);
        // mediaPlayer.setAutoPlay(true);
        this.mediaView.setMediaPlayer(mediaPlayer);
    }

    @Override
    public FileMediaPlayer setVisible(boolean visible) {
        this.mediaView.setVisible(visible);
        return this;
    }

    @Override
    public void play() {
        this.mediaView.getMediaPlayer().play();
    }

    @Override
    public void stop() {
        this.mediaView.getMediaPlayer().stop();
    }

    @Override
    public void setPosition(Position position) {
        this.mediaView.setLayoutX(position.left);
        this.mediaView.setLayoutY(position.top);
    }

    @Override
    public void setSize(Size size) {
        this.mediaView.setFitHeight(size.height);
        this.mediaView.setFitWidth(size.width);
    }

    @Override
    public void setCoordinates(BaseCoordinate coordinates) {
        this.mediaView.setLayoutX(coordinates.left);
        this.mediaView.setLayoutY(coordinates.top);
        this.mediaView.setFitHeight(coordinates.height);
        this.mediaView.setFitWidth(coordinates.width);
    }
}
