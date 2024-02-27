package be.yildizgames.module.window.javafx;

import javafx.animation.Interpolator;
import javafx.animation.ScaleTransition;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.geometry.Point2D;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.net.MalformedURLException;
import java.nio.file.Path;

public class ImageRing extends Application {

    private static final double RADIUS = 150;
    private static final int NUM_IMAGES = 5;
    private static final double ANGLE_PER_IMAGE = 360.0 / NUM_IMAGES;

    public static void main(String[] args) {
        launch(args);
    }

    private ImageView[] images = new ImageView[NUM_IMAGES];

    @Override
    public void start(Stage primaryStage) throws MalformedURLException {
        Pane root = new Pane();
        Scene scene = new Scene(root, 400, 400);

        // Add images to the scene
        for (int i = 0; i < NUM_IMAGES; i++) {
            ImageView imageView = new ImageView(new Image(Path.of("D:/a.png").toUri().toURL().toExternalForm()));
            imageView.setFitWidth(50);
            imageView.setFitHeight(50);
            imageView.setPreserveRatio(true);
            imageView.getTransforms().add(new Rotate(-90 + i * ANGLE_PER_IMAGE, Rotate.Z_AXIS));
            images[i] = imageView;
            root.getChildren().add(imageView);
        }

        // Set initial positions of images
        for (int i = 0; i < NUM_IMAGES; i++) {
            Point2D point = calculatePosition(i, 0);
            images[i].setLayoutX(point.getX());
            images[i].setLayoutY(point.getY());
        }

        // Handle mouse events
        scene.setOnMousePressed(this::handleMousePressed);
        scene.setOnMouseReleased(this::handleMouseReleased);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void handleMousePressed(MouseEvent event) {
        for (int i = 0; i < NUM_IMAGES; i++) {
            ImageView image = images[i];
            Point2D point = new Point2D(event.getSceneX(), event.getSceneY());
            if (image.getBoundsInParent().contains(image.parentToLocal(point))) {
                // Animate the image on press
                ScaleTransition transition = new ScaleTransition(Duration.seconds(0.2), image);
                transition.setFromX(1.0);
                transition.setFromY(1.0);
                transition.setToX(0.8);
                transition.setToY(0.8);
                transition.setInterpolator(Interpolator.EASE_OUT);
                transition.play();
                break;
            }
        }
    }

    private void handleMouseReleased(MouseEvent event) {
        double x = event.getSceneX();
        double y = event.getSceneY();
        double angle = Math.toDegrees(Math.atan2(y - 200, x - 200));
        if (angle < 0) {
            angle += 360;
        }
        int index = (int) Math.floor(angle / ANGLE_PER_IMAGE) % NUM_IMAGES;
        ImageView image = images[index];
        // Animate the selected image on release
        ScaleTransition transition = new ScaleTransition(Duration.seconds(0.2), image);
        transition.setFromX(0.8);
        transition.setFromY(0.8);
        transition.setToX(1.0);
        transition.setToY(1.0);
        transition.setInterpolator(Interpolator.EASE_OUT);
        transition.play();
    }

    private Point2D calculatePosition(int index, double time) {
        double angle = index * ANGLE_PER_IMAGE + time;
        double x = Math.cos(Math.toRadians(angle)) * RADIUS + 200;
        double y = Math.sin(Math.toRadians(angle)) * RADIUS + 200;
        return new Point2D(x, y);
    }

    private class ImageTransition extends Transition {

        private final ImageView imageView;
        private final int index;
        private final double startAngle;
        private final double endAngle;

        public ImageTransition(ImageView imageView, int index, double startAngle, double endAngle) {
            this.imageView = imageView;
            this.index = index;
            this.startAngle = startAngle;
            this.endAngle = endAngle;
            setCycleDuration(Duration.seconds(1));
            setInterpolator(Interpolator.EASE_BOTH);
        }

        @Override
        protected void interpolate(double frac) {
            double angle = startAngle + frac * (endAngle - startAngle);
            Point2D point = calculatePosition(index, angle);
            imageView.setLayoutX(point.getX());
            imageView.setLayoutY(point.getY());
            double scale = 0.8 + 0.2 * Math.sin(angle * Math.PI / 180);
            imageView.setScaleX(scale);
            imageView.setScaleY(scale);
        }
    }

}