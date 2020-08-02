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

import java.net.MalformedURLException;
import java.nio.file.Path;

/**
 * @author Grégory Van den Borre
 */
public class JavaFxMediaPlayer implements WindowMediaPlayer {

    private MediaPlayerStrategy current;

    private final FileMediaPlayer fileMediaPlayer;

    private final YoutubeMediaPlayer youtubeMediaPlayer;

    private boolean playing;
    
    JavaFxMediaPlayer(Pane pane) {
        super();
        this.fileMediaPlayer = new FileMediaPlayer(pane);
        this.youtubeMediaPlayer = new YoutubeMediaPlayer(pane);
        this.current = this.fileMediaPlayer;
        this.youtubeMediaPlayer.setVisible(false);
    }

    @Override
    public JavaFxMediaPlayer setMedia(String url) {
        this.defineStrategy(url);
        this.current.setMedia(url);
        return this;
    }

    private void defineStrategy(String url) {
        this.current.setVisible(false);
        this.current = url.contains("www.youtube.com") ? this.youtubeMediaPlayer : this.fileMediaPlayer;
        this.current.setVisible(true);
    }

    @Override
    public final JavaFxMediaPlayer setCoordinates(BaseCoordinate coordinates) {
        this.youtubeMediaPlayer.updateCoordinates(coordinates);
        this.youtubeMediaPlayer.setCoordinates(coordinates);
        this.fileMediaPlayer.updateCoordinates(coordinates);
        this.fileMediaPlayer.setCoordinates(coordinates);
        return this;
    }

    @Override
    public final JavaFxMediaPlayer setSize(Size size) {
        this.youtubeMediaPlayer.updateCoordinates(size);
        this.fileMediaPlayer.updateCoordinates(size);
        this.youtubeMediaPlayer.setSize(size);
        this.fileMediaPlayer.setSize(size);
        return this;
    }

    @Override
    public final JavaFxMediaPlayer setPosition(Position position) {
        this.youtubeMediaPlayer.updateCoordinates(position);
        this.fileMediaPlayer.updateCoordinates(position);
        this.youtubeMediaPlayer.setPosition(position);
        this.fileMediaPlayer.setPosition(position);
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
        this.current.play();
        playing = true;
        return this;
    }

    @Override
    public WindowMediaPlayer stop() {
        if (playing) {
            this.current.stop();
            playing = false;
        }

        return this;
    }

    public JavaFxMediaPlayer setVisible(boolean visible) {
        this.current.setVisible(visible);
        return this;
    }

}
