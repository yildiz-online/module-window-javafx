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

import be.yildizgames.module.coordinates.Coordinates;
import be.yildizgames.module.coordinates.Position;
import be.yildizgames.module.coordinates.Size;
import javafx.scene.layout.Pane;
import javafx.scene.web.WebView;

/**
 * @author Grégory Van den Borre
 */
class YoutubeMediaPlayer extends JavaFxBaseWidget<YoutubeMediaPlayer> implements MediaPlayerStrategy {

    private final System.Logger logger = System.getLogger(this.getClass().getName());

    private final WebView webview;

    YoutubeMediaPlayer(Pane pane) {
        super();
        this.webview = new WebView();
        pane.getChildren().add(this.webview);
        this.setReady(this.webview);
    }

    @Override
    public void setMedia(String url) {
        var rurl = this.reformatUrl(url);
        logger.log(System.Logger.Level.DEBUG, "Setting media with url {0} : {1}", url, rurl);
        this.webview.getEngine().load(rurl);
    }

    @Override
    public void play() {
        //autoplay does nothing.
    }

    @Override
    public void stop() {
        this.webview.getEngine().load(null);
    }

    @Override
    public void setPosition(Position position) {
        webview.setLayoutX(position.getLeft());
        webview.setLayoutY(position.getTop());
    }

    @Override
    public final void setSize(Size size) {
        webview.setPrefSize(size.getWidth(), size.getHeight());
    }

    @Override
    public final void setCoordinates(Coordinates coordinates) {
        webview.setPrefSize(coordinates.getWidth(), coordinates.getHeight());
        webview.setLayoutX(coordinates.getLeft());
        webview.setLayoutY(coordinates.getTop());
    }

    @Override
    public final YoutubeMediaPlayer setVisible(boolean visible) {
        super.setVisible(visible);
        if (!visible) {
            this.stop();
        }
        return this;
    }

    private String reformatUrl(String url) {
        url = url.replace("http://", "https://");
        url = url.replace("www.youtube.com/watch?v=", "www.youtube-nocookie.com/embed/");
        url = url + "?autoplay=1&controls=0&modestbranding=1&showinfo=0&rel=0&iv_load_policy=0";
        return url;
    }
}
