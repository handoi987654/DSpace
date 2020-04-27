package com.Desert.Component.Public;

import com.Desert.Control.Public.SVGButton;
import com.Desert.Entity.Music.Playlist;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class MiniPlayer extends AnchorPane implements FXUtility {

    @Autowired
    private Scene scene;
    private AnchorPane playerPane;

    @PostConstruct
    private void render() throws IOException {
        getChildren().add(playerPane());
    }

    private AnchorPane playerPane() throws IOException {
        playerPane = new AnchorPane();
        playerPane.getStyleClass().addAll("mini-player");
        playerPane.getChildren().addAll(musicPath(), playButton(), previousButton(), nextButton(), titleText(), artistText(),
                separationLine(), songList(),
                playlistPath(), playlistBox());
        AnchorPane.setLeftAnchor(playerPane, 0d);
        AnchorPane.setBottomAnchor(playerPane, 0d);

        return playerPane;
    }

    private SVGPath musicPath() throws IOException {
        SVGPath musicPath = new SVGPath();
        musicPath.setContent(SVGPathGenerator.generate(getVector("music")));
        musicPath.setFill(Color.WHITE);
        AnchorPane.setLeftAnchor(musicPath, 16d);
        AnchorPane.setBottomAnchor(musicPath, 16d);

        return musicPath;
    }

    private SVGPath playlistPath() throws IOException {
        SVGPath playlistPath = new SVGPath();
        playlistPath.setContent(SVGPathGenerator.generate(getVector("playlist")));
        playlistPath.setFill(Color.WHITE);
        AnchorPane.setLeftAnchor(playlistPath, 16d);
        AnchorPane.setTopAnchor(playlistPath, 24d);

        return playlistPath;
    }

    private ComboBox<Playlist> playlistBox() {
        ComboBox<Playlist> playlistBox = new ComboBox<>();
        playlistBox.getStyleClass().addAll("playlist-box");
        AnchorPane.setTopAnchor(playlistBox, 16d);
        AnchorPane.setRightAnchor(playlistBox, 16d);

        return playlistBox;
    }

    private ScrollPane songList() throws IOException {
        VBox songsBox = new VBox();
        songsBox.setSpacing(16);
        songsBox.getChildren().add(new SongItem());

        ScrollPane songsPane = new ScrollPane();
        songsPane.getStyleClass().add("scroll-pane");
        songsPane.setContent(songsBox);
        AnchorPane.setTopAnchor(songsPane, 72d);
        AnchorPane.setLeftAnchor(songsPane, 16d);

        return songsPane;
    }

    private Line separationLine() {
        Line separationLine = new Line();
        separationLine.setStartX(8);
        separationLine.setStartY(448);
        separationLine.setEndX(600);
        separationLine.setEndY(448);
        separationLine.setStroke(Color.valueOf("#5C7080"));
        separationLine.setStrokeWidth(2);

        return separationLine;
    }

    private Text titleText() {
        Text titleText = new Text();
        titleText.setText("Memories");
        titleText.setFont(new Font("SF Pro Text Regular", 22));
        titleText.setFill(Color.WHITE);
        AnchorPane.setLeftAnchor(titleText, 224d);
        AnchorPane.setBottomAnchor(titleText, 32d);

        return titleText;
    }

    private Text artistText() {
        Text artistText = new Text();
        artistText.setText("Maroon 5");
        artistText.setFont(new Font("SF Pro Text Regular", 14));
        artistText.setFill(Color.valueOf("#bfccd6"));
        AnchorPane.setLeftAnchor(artistText, 224d);
        AnchorPane.setBottomAnchor(artistText, 8d);

        return artistText;
    }

    public SVGButton playButton() throws IOException {
        SVGButton playButton = new SVGButton(getVector("play"), 27, 32);
        playButton.setAnchor(2, 0);
        AnchorPane.setLeftAnchor(playButton, 121d);
        AnchorPane.setBottomAnchor(playButton, 16d);
        return playButton;
    }

    public SVGButton previousButton() throws IOException {
        SVGButton previousButton = new SVGButton(getVector("previous"), 16, 16);
        previousButton.setAnchor(8, 8);
        AnchorPane.setLeftAnchor(previousButton, 80d);
        AnchorPane.setBottomAnchor(previousButton, 16d);
        return previousButton;
    }

    public SVGButton nextButton() throws IOException {
        SVGButton nextButton = new SVGButton(getVector("next"), 16, 16);
        nextButton.setAnchor(8, 8);
        AnchorPane.setLeftAnchor(nextButton, 160d);
        AnchorPane.setBottomAnchor(nextButton, 16d);
        return nextButton;
    }

    public final void show() {
        StackPane stackPane = (StackPane) scene.getRoot();

        setOnMouseClicked(event -> {
            double pointerX = event.getSceneX();
            double pointerY = event.getSceneY();
            double playerMaxX = playerPane.localToScene(playerPane.getBoundsInLocal()).getMaxX();
            double playerMinY = playerPane.localToScene(playerPane.getBoundsInLocal()).getMinY();
            if (pointerX < playerMaxX && pointerY > playerMinY) return;

            stackPane.getChildren().remove(this);
        });

        stackPane.getChildren().add(this);
    }
}

class SongItem extends HBox implements FXUtility {

    public SongItem() throws IOException {
        SVGPath musicNodePath = new SVGPath();
        musicNodePath.setContent(SVGPathGenerator.generate(getVector("music-node")));
        musicNodePath.setFill(Color.WHITE);

        Label titleLabel = new Label();
        titleLabel.setText("Memories");
        titleLabel.getStyleClass().addAll("title-label");
        HBox.setMargin(titleLabel, new Insets(0, 0, 0, 56));

        Label artistLabel = new Label();
        artistLabel.setText("Maroon 5");
        artistLabel.getStyleClass().addAll("artist-label");

        getStyleClass().addAll("song-item");
        getChildren().addAll(musicNodePath, titleLabel, artistLabel);
    }

}