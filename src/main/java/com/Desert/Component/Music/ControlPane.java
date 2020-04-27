package com.Desert.Component.Music;

import com.Desert.Application.MusicApp;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Control.Widget.MusicWidget;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.Slider;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class ControlPane extends AnchorPane implements FXUtility {

    @Autowired
    private MusicWidget musicWidget;
    @Autowired
    private ApplicationContext context;

    @Value("classpath:images/maroon-5.jpg")
    private Resource pictureRsrc;

    private SVGButton openViewButton, closeViewButton;

    @PostConstruct
    private void render() throws IOException {
        initCloseViewButton();

        getStyleClass().add("player-pane");
        getChildren().addAll(playlistButton(), playerBox(), widgetButton(), openViewButton());
    }

    private SVGButton playlistButton() throws IOException {
        SVGButton playlistButton = new SVGButton(getVector("playlist"), 34, 32);
        playlistButton.setAnchor(3, 4);
        playlistButton.getStyleClass().add("playlist-button");
        playlistButton.setOnMouseClicked(event -> openPlaylist());
        AnchorPane.setTopAnchor(playlistButton, 40d);
        AnchorPane.setLeftAnchor(playlistButton, 184d);
        return playlistButton;
    }

    private HBox playerBox() throws IOException {
        /*
        Cover Image
         */
        SVGPath clipPath = new SVGPath();
        clipPath.setContent(SVGPathGenerator.generate(getVector("music-picture-small-mask")));

        Image coverImage = new Image(pictureRsrc.getInputStream());
        ImageView coverView = new ImageView(coverImage);
        coverView.setPreserveRatio(true);
        coverView.setSmooth(true);
        coverView.setClip(clipPath);
        coverView.setFitWidth(96);

        SVGButton previousButton = new SVGButton(getVector("previous"), 16, 16);
        previousButton.getStyleClass().add("previous-button");
        SVGButton nextButton = new SVGButton(getVector("next"), 16, 16);
        nextButton.getStyleClass().add("next-button");
        SVGButton playButton = new SVGButton(getVector("play"), 26, 32);
        playButton.getStyleClass().add("play-button");
        playButton.setAnchor(3, 0);

        /*
        Title Box
         */
        Text titleText = new Text("Memories");
        titleText.getStyleClass().add("title-text");
        Text artistText = new Text(" - Maroon 5");
        artistText.getStyleClass().add("artist-text");
        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-box");
        titleBox.getChildren().addAll(titleText, artistText);

        /*
        Slider
         */
        Slider slider = new Slider();
        VBox.setMargin(slider, new Insets(8, 0, 0, 0));
        ProgressBar bar = new ProgressBar();
        StackPane sliderPane = new StackPane();
        sliderPane.getStyleClass().add("slider-pane");
        sliderPane.getChildren().addAll(bar, slider);

        /*
        Time
         */
        Text startTime = new Text("0:36");
        startTime.getStyleClass().add("time-text");
        AnchorPane.setTopAnchor(startTime, 0d);
        AnchorPane.setLeftAnchor(startTime, 0d);
        Text endTime = new Text("3:05");
        endTime.getStyleClass().add("time-text");
        AnchorPane.setTopAnchor(endTime, 0d);
        AnchorPane.setRightAnchor(endTime, 0d);
        AnchorPane timePane = new AnchorPane();
        timePane.getChildren().addAll(startTime, endTime);

        VBox box = new VBox();
        box.getStyleClass().add("box");
        box.getChildren().addAll(titleBox, sliderPane, timePane);

        /*
        Player Box
         */
        HBox playerBox = new HBox();
        playerBox.getStyleClass().add("player-box");
        playerBox.getChildren().addAll(coverView, previousButton, playButton, nextButton, box);
        AnchorPane.setTopAnchor(playerBox, 8d);
        AnchorPane.setBottomAnchor(playerBox, 8d);
        AnchorPane.setLeftAnchor(playerBox, 592d);
        return playerBox;
    }

    private SVGButton widgetButton() throws IOException {
        SVGButton widgetButton = new SVGButton(getVector("music-widget"), 32, 32);
        widgetButton.setAnchor(8, 8);
        widgetButton.getStyleClass().add("widget-button");
        widgetButton.setOnMouseClicked(event -> openWidget());
        AnchorPane.setTopAnchor(widgetButton, 32d);
        AnchorPane.setRightAnchor(widgetButton, 235d);
        return widgetButton;
    }

    private SVGButton openViewButton() throws IOException {
        openViewButton = new SVGButton(getVector("open-space"), 39, 32);
        openViewButton.getStyleClass().add("open-space-button");
        openViewButton.setAnchor(4.5, 8);
        openViewButton.setOnMouseClicked(event -> openViewPane());
        AnchorPane.setTopAnchor(openViewButton, 32d);
        AnchorPane.setRightAnchor(openViewButton, 164d);
        return openViewButton;
    }

    private void initCloseViewButton() throws IOException {
        closeViewButton = new SVGButton(getVector("close-space"), 39, 32);
        closeViewButton.getStyleClass().add("close-view-button");
        closeViewButton.setAnchor(4.5, 8);
        closeViewButton.setOnMouseClicked(event -> closeViewPane());
        AnchorPane.setTopAnchor(closeViewButton, 32d);
        AnchorPane.setRightAnchor(closeViewButton, 164d);
    }

    private void openWidget() {
        Stage stage = (Stage) ((Scene) context.getBean("scene")).getWindow();
        stage.setIconified(true);
        musicWidget.display();
    }

    private void openPlaylist() {
        MusicApp musicApp = (MusicApp) context.getBean("musicApp");
        musicApp.openCurrentPlaylist();
    }

    private void openViewPane() {
        getChildren().remove(openViewButton);
        getChildren().add(closeViewButton);

        MusicApp musicApp = (MusicApp) context.getBean("musicApp");
        musicApp.openViewPane();
    }

    private void closeViewPane() {
        getChildren().remove(closeViewButton);
        getChildren().add(openViewButton);

        MusicApp musicApp = (MusicApp) context.getBean("musicApp");
        musicApp.closeViewPane();
    }
}
