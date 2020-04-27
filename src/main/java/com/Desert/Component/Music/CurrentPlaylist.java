package com.Desert.Component.Music;

import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@Scope("prototype")
public class CurrentPlaylist extends AnchorPane implements FXUtility {

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("current-playlist");
        getChildren().addAll(titleText(), loopButton(), shuffleButton(), separatedLine(), scrollPane());
        AnchorPane.setTopAnchor(this, 0d);
        AnchorPane.setLeftAnchor(this, 0d);
    }

    private Text titleText() {
        Text titleText = new Text("TSFH");
        titleText.getStyleClass().add("title-text");
        AnchorPane.setBottomAnchor(titleText, 0d);
        AnchorPane.setLeftAnchor(titleText, 0d);
        return titleText;
    }

    private SVGButton loopButton() throws IOException {
        SVGButton loopButton = new SVGButton(getVector("loop"), 19, 24);
        loopButton.setAnchor(10.5, 8);
        loopButton.getStyleClass().add("loop-button");
        AnchorPane.setBottomAnchor(loopButton, 0d);
        AnchorPane.setRightAnchor(loopButton, 72d);
        return loopButton;
    }

    private SVGButton shuffleButton() throws IOException {
        SVGButton shuffleButton = new SVGButton(getVector("shuffle"), 24, 24);
        shuffleButton.setAnchor(8, 8);
        shuffleButton.getStyleClass().add("shuffle-button");
        AnchorPane.setBottomAnchor(shuffleButton, 0d);
        AnchorPane.setRightAnchor(shuffleButton, 16d);
        return shuffleButton;
    }

    private Line separatedLine() {
        Line line = new Line();
        line.setStartX(16);
        line.setStartY(344);
        line.setEndX(456);
        line.setEndY(344);
        line.setStroke(javafx.scene.paint.Color.valueOf("#5C7080"));
        line.setStrokeWidth(1);
        return line;
    }

    private ScrollPane scrollPane() throws IOException {
        VBox box = new VBox();
        box.getStyleClass().add("box");
        box.getChildren().add(musicItem());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setContent(box);
        AnchorPane.setTopAnchor(scrollPane, 0d);
        AnchorPane.setLeftAnchor(scrollPane, 0d);
        return scrollPane;
    }

    private AnchorPane musicItem() throws IOException {
        SVGPath musicPath = new SVGPath();
        musicPath.setContent(SVGPathGenerator.generate(getVector("music-node")));
        musicPath.setFill(javafx.scene.paint.Color.WHITE);
        AnchorPane.setTopAnchor(musicPath, 16d);
        AnchorPane.setLeftAnchor(musicPath, 16d);
        AnchorPane.setBottomAnchor(musicPath, 16d);

        Text titleText = new Text("Memories");
        titleText.getStyleClass().add("song-title");
        AnchorPane.setTopAnchor(titleText, 16d);
        AnchorPane.setLeftAnchor(titleText, 80d);

        Text artistText = new Text("Maroon 5");
        artistText.getStyleClass().add("song-artist");
        AnchorPane.setTopAnchor(artistText, 16d);
        AnchorPane.setLeftAnchor(artistText, 256d);

        AnchorPane musicItem = new AnchorPane();
        musicItem.getStyleClass().add("music-item");
        musicItem.getChildren().addAll(musicPath, titleText, artistText);
        return musicItem;
    }
}
