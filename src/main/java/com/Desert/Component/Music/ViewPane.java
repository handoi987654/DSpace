package com.Desert.Component.Music;

import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.SVGPathGenerator;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.effect.GaussianBlur;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import javafx.scene.media.MediaView;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class ViewPane extends StackPane {

    @Value("classpath:images/maroon-5.jpg")
    private Resource coverRsrc;
    @Value("classpath:vectors/cover-circle-mask.svg")
    private Resource maskSvgRsrc;
    @Value("classpath:vectors/back.svg")
    private Resource backSvgRsrc;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("view-pane");
        getChildren().addAll(coverView(), overlayPane(), musicBox(), backButton());
    }

    /*
    Video View
     */
    private MediaView mediaView() {
        MediaPlayer player = new MediaPlayer(new Media("file:///home/anod/Downloads/video.mp4"));
        player.play();
        player.setMute(true);

        MediaView mediaView = new MediaView();
        mediaView.setMediaPlayer(player);
        mediaView.setFitHeight(968);
        return mediaView;
    }

    private SVGButton backButton() throws IOException {
        SVGButton backButton = new SVGButton(backSvgRsrc, 25, 16);
        backButton.setAnchor(19.5, 24);
        backButton.getStyleClass().add("back-button");
        StackPane.setAlignment(backButton, Pos.TOP_LEFT);
        return backButton;
    }

    /*
    Music View
     */
    private ImageView coverView() throws IOException {
        Image coverImage = new Image(coverRsrc.getInputStream());
        ImageView coverView = new ImageView(croppedImage(coverImage, 1920, 968));
        coverView.setPreserveRatio(true);
        coverView.setSmooth(true);
        coverView.setFitWidth(1920);
        coverView.setEffect(new GaussianBlur(50));
        return coverView;
    }

    private Pane overlayPane() {
        Pane overlayPane = new Pane();
        overlayPane.setStyle("-fx-background-color: rgba(0, 0, 0, 0.5)");
        return overlayPane;
    }

    private HBox musicBox() throws IOException {
        SVGPath maskPath = new SVGPath();
        maskPath.setContent(SVGPathGenerator.generate(maskSvgRsrc));

        double size = 256;
        Image coverImage = new Image(coverRsrc.getInputStream());
        ImageView coverView = new ImageView(croppedImage(coverImage, size, size));
        coverView.setPreserveRatio(true);
        coverView.setFitWidth(size);
        coverView.setClip(maskPath);
        HBox.setMargin(coverView, new Insets(292, 352, 292, 352));

        Line line = new Line();
        line.setStartX(960);
        line.setStartY(0);
        line.setEndX(960);
        line.setEndY(840);
        line.setStroke(Color.rgb(255, 255, 255, 0.3));
        line.setStrokeWidth(2);

        VBox lyricBox = new VBox();
        lyricBox.getStyleClass().add("lyric-box");
        lyricBox.getChildren().add(lyricText("lyric"));
        lyricBox.getChildren().add(lyricText("lyric"));
        lyricBox.getChildren().add(lyricText("lyric"));

        ScrollPane lyricPane = new ScrollPane();
        lyricPane.getStyleClass().add("lyric-pane");
        lyricPane.setContent(lyricBox);
        HBox.setMargin(lyricPane, new Insets(0, 152, 0, 152));

        HBox musicBox = new HBox();
        musicBox.getStyleClass().add("music-box");
        musicBox.getChildren().addAll(coverView, line, lyricPane);
        StackPane.setAlignment(musicBox, Pos.TOP_CENTER);
        StackPane.setMargin(musicBox, new Insets(64, 0, 0, 0));
        return musicBox;
    }

    private Image croppedImage(Image originalImage, double imgWidth, double imgHeight) {
        double ratio = imgHeight / imgWidth;
        int height = (int) Math.floor(originalImage.getWidth() * ratio);
        int y = (int) Math.floor((originalImage.getHeight() - height) / 2);

        return new WritableImage(originalImage.getPixelReader(),
                0, y, (int) Math.floor(originalImage.getWidth()), height);
    }

    private Text lyricText(String value) {
        Text lyricText = new Text(value);
        lyricText.setFill(Color.WHITE);
        lyricText.getStyleClass().add("lyric-text");
        return lyricText;
    }
}
