package com.Desert.Component.Music;

import com.Desert.Application.MusicApp;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.SVGPathGenerator;
import javafx.animation.TranslateTransition;
import javafx.css.PseudoClass;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import javafx.util.Duration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class PlaylistPane extends HBox {

    @Autowired
    private MusicListPane musicListPane;
    @Autowired
    private MetadataPane metadataPane;
    @Autowired
    private ApplicationContext context;

    @Value("classpath:vectors/add.svg")
    private Resource addSvgRsrc;
    @Value("classpath:vectors/list.svg")
    private Resource listSvgRsrc;
    @Value("classpath:vectors/right-arrow.svg")
    private Resource arrowSvgRsrc;

    private TranslateTransition transition;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("playlist-pane");
        getChildren().addAll(addButton(), scrollPane(), arrowPane(), musicListPane, metadataPane);
    }

    private SVGButton addButton() throws IOException {
        SVGButton addButton = new SVGButton(addSvgRsrc, 14, 14);
        addButton.setAnchor(13, 13);
        addButton.getStyleClass().add("add-button");
        addButton.setOnMouseClicked(event -> openCreatePane());
        return addButton;
    }

    private ScrollPane scrollPane() throws IOException {
        VBox box = new VBox();
        box.getStyleClass().add("box");
        box.getChildren().add(playlistItem());
        box.getChildren().add(playlistItem());
        box.getChildren().add(playlistItem());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(box);
        return scrollPane;
    }

    private AnchorPane playlistItem() throws IOException {
        SVGPath listPath = new SVGPath();
        listPath.setContent(SVGPathGenerator.generate(listSvgRsrc));
        Region iconRegion = new Region();
        iconRegion.setShape(listPath);
        iconRegion.getStyleClass().add("icon");
        AnchorPane.setTopAnchor(iconRegion, 21d);
        AnchorPane.setLeftAnchor(iconRegion, 18d);
        AnchorPane.setBottomAnchor(iconRegion, 21d);

        TextField titleField = new TextField("Playlist 1");
        titleField.getStyleClass().add("title-field");
        titleField.setEditable(false);
        titleField.setOnMouseClicked(event -> showPlaylist((AnchorPane) titleField.getParent()));
        AnchorPane.setTopAnchor(titleField, 8d);
        AnchorPane.setLeftAnchor(titleField, 64d);

        Text songCount = new Text("7 songs");
        songCount.getStyleClass().add("song-count");
        AnchorPane.setTopAnchor(songCount, 20d);
        AnchorPane.setRightAnchor(songCount, 16d);

        AnchorPane playlistItem = new AnchorPane();
        playlistItem.getStyleClass().add("playlist-item");
        playlistItem.getChildren().addAll(iconRegion, titleField, songCount);
        playlistItem.pseudoClassStateChanged(PseudoClass.getPseudoClass("selected"), false);
        playlistItem.setOnMouseClicked(event -> showPlaylist(playlistItem));
        return playlistItem;
    }

    private Pane arrowPane() throws IOException {
        SVGPath arrowPath = new SVGPath();
        arrowPath.setContent(SVGPathGenerator.generate(arrowSvgRsrc));
        arrowPath.setFill(Color.WHITE);
        arrowPath.setLayoutY(16);

        transition = new TranslateTransition();
        transition.setNode(arrowPath);

        Pane arrowPane = new Pane();
        arrowPane.getChildren().add(arrowPath);
        return arrowPane;
    }

    private void showPlaylist(AnchorPane playlistItem) {
        transition.setToY(playlistItem.getLayoutY());
        transition.setDuration(Duration.seconds(0.6));
        transition.play();
    }

    private void openCreatePane() {
        MusicApp musicApp = (MusicApp) context.getBean("musicApp");
        musicApp.openCreatePlaylistPane();
    }
}