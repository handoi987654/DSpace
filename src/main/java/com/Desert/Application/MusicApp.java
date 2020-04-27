package com.Desert.Application;

import com.Desert.Component.Music.*;
import com.Desert.Component.Public.TopPane;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class MusicApp extends StackPane implements FXUtility {

    @Autowired
    private TopPane topPane;
    @Autowired
    private Scene scene;
    @Autowired
    private MusicPane musicPane;
    @Autowired
    private PlaylistPane playlistPane;
    @Autowired
    private ViewPane viewPane;
    @Autowired
    private ControlPane controlPane;
    @Autowired
    private CreatePlaylistPane createPlaylistPane;
    @Autowired
    private ImportMusicPane importMusicPane;
    @Autowired
    private CurrentPlaylist currentPlaylist;

    @Value("classpath:images/another-world.png")
    private Resource bgRsrc;

    private BorderPane mainPane;
    private VBox sideBox, topBox;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("music");
        getChildren().addAll(backgroundView(), mainPane());
    }

    private ImageView backgroundView() throws IOException {
        Image image = new Image(bgRsrc.getInputStream());
        ImageView bgView = new ImageView(image);
        bgView.setPreserveRatio(true);
        bgView.setFitWidth(scene.getWidth());
        return bgView;
    }

    private BorderPane mainPane() throws IOException {
        mainPane = new BorderPane();
        mainPane.getStyleClass().add("main-pane");
        mainPane.setTop(topBox());
        mainPane.setLeft(sideBox());
        mainPane.setCenter(musicPane);
        mainPane.setBottom(controlPane);
        return mainPane;
    }

    private VBox topBox() {
        Text lText = new Text("Your time");
        lText.getStyleClass().add("ltext");
        AnchorPane.setTopAnchor(lText, 16d);
        AnchorPane.setLeftAnchor(lText, 16d);
        AnchorPane.setBottomAnchor(lText, 16d);

        Text mText = new Text("Your world");
        mText.getStyleClass().add("mtext");
        AnchorPane.setTopAnchor(mText, 16d);
        AnchorPane.setLeftAnchor(mText, 840d);

        Text rText = new Text("Your feeling");
        rText.getStyleClass().add("rtext");
        AnchorPane.setTopAnchor(rText, 16d);
        AnchorPane.setRightAnchor(rText, 16d);
        AnchorPane.setBottomAnchor(rText, 16d);

        AnchorPane welcomePane = new AnchorPane();
        welcomePane.getStyleClass().add("welcome-pane");
        welcomePane.getChildren().addAll(lText, mText, rText);

        topBox = new VBox();
        topBox.getStyleClass().add("top-box");
        topBox.getChildren().addAll(topPane, welcomePane);
        topBox.setOnMouseClicked(event -> openViewPane());
        return topBox;
    }

    private VBox sideBox() throws IOException {
        SVGButton musicButton = new SVGButton(getVector("music"), 32, 32);
        musicButton.setAnchor(16, 16);
        musicButton.getStyleClass().add("side-button");
        musicButton.setOnMouseClicked(event -> mainPane.setCenter(musicPane));

        SVGButton playlistButton = new SVGButton(getVector("playlist"), 32, 24);
        playlistButton.setAnchor(16, 20);
        playlistButton.getStyleClass().add("side-button");
        playlistButton.setOnMouseClicked(event -> mainPane.setCenter(playlistPane));

        sideBox = new VBox();
        sideBox.getStyleClass().add("side-box");
        sideBox.getChildren().addAll(musicButton, playlistButton);
        return sideBox;
    }

    public final void openCreatePlaylistPane() {
        getChildren().add(createPlaylistPane);
    }

    public final void openImportMusicPane() {
        getChildren().add(importMusicPane);
    }

    public final void openCurrentPlaylist() {
        AnchorPane.setTopAnchor(currentPlaylist, 552d);
        AnchorPane.setLeftAnchor(currentPlaylist, 0d);

        AnchorPane overlayPane = new AnchorPane();
        overlayPane.getChildren().add(currentPlaylist);
        overlayPane.setOnMouseClicked(event -> checkClose(event, overlayPane));
        getChildren().add(overlayPane);
    }

    public final void cancelCreatePlaylist() {
        getChildren().remove(createPlaylistPane);
    }

    public final void cancelImportMusic() {
        getChildren().remove(importMusicPane);
    }

    public void openViewPane() {
        mainPane.setCenter(viewPane);
        mainPane.getChildren().removeAll(topBox, sideBox, musicPane, controlPane);
        mainPane.setBottom(controlPane);
    }

    public void closeViewPane() {
        mainPane.setTop(topBox);
        mainPane.setLeft(sideBox);
        mainPane.setCenter(musicPane);

        mainPane.getChildren().remove(viewPane);
    }

    private void checkClose(MouseEvent event, AnchorPane overlayPane) {
        if (event.getTarget().equals(overlayPane)) getChildren().remove(overlayPane);
    }
}
