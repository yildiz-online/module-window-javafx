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
import javafx.scene.web.WebView;

/**
 * @author Grégory Van den Borre
 */
class YoutubeMediaPlayer extends JavaFxBaseWidget<YoutubeMediaPlayer> implements MediaPlayerStrategy {

    private final WebView webview;

    YoutubeMediaPlayer(Pane pane) {
        super();
        this.webview = new WebView();
        pane.getChildren().add(this.webview);
        this.setReady(this.webview);
    }

    @Override
    public void setMedia(String url) {
        this.webview.getEngine().load(this.reformatUrl(url));
    }

    @Override
    public void play() {
        //autoplay does nothing.
    }

    @Override
    public void stop() {
        //does nothing
    }

    @Override
    public void setPosition(Position position) {

    }

    @Override
    public void setSize(Size size) {
        webview.setPrefSize(size.width, size.height);
    }

    @Override
    public void setCoordinates(BaseCoordinate coordinates) {
        webview.setPrefSize(coordinates.width, coordinates.height);
        webview.setLayoutX(coordinates.left);
        webview.setLayoutY(coordinates.top);
    }

    private String reformatUrl(String url) {
        url = url.replace("http://", "https://");
        url = url.replace("www.youtube.com/watch?v=", "www.youtube.com/embed/");
        url = url + "?autoplay=1&controls=0&modestbranding=1&showinfo=0&rel=0&iv_load_policy=0";
        return url;
    }
}
