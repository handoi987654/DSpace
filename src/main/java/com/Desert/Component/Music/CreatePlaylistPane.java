package com.Desert.Component.Music;


import com.Desert.Application.MusicApp;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class CreatePlaylistPane extends VBox implements FXUtility {

    @Autowired
    private ApplicationContext context;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("create-playlist-pane");
        getChildren().addAll(titlePane(), browseBox(), actionPane());
        setOnMouseClicked(this::checkCancel);
    }

    private AnchorPane titlePane() {
        Label titleLabel = new Label("Playlist Name:");
        titleLabel.getStyleClass().add("title-label");
        AnchorPane.setTopAnchor(titleLabel, 20d);
        AnchorPane.setLeftAnchor(titleLabel, 16d);
        AnchorPane.setBottomAnchor(titleLabel, 20d);

        TextField titleField = new TextField("Playlist 4");
        titleField.getStyleClass().add("title-field");
        AnchorPane.setTopAnchor(titleField, 8d);

        AnchorPane titlePane = new AnchorPane();
        titlePane.getStyleClass().add("title-pane");
        titlePane.getChildren().addAll(titleLabel, titleField);
        return titlePane;
    }

    private VBox browseBox() throws IOException {
        SVGPath searchPath = new SVGPath();
        searchPath.setContent(SVGPathGenerator.generate(getVector("search")));
        searchPath.getStyleClass().add("search-path");
        HBox.setMargin(searchPath, new Insets(0, 0, 0, 11));

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.getStyleClass().add("search-field");

        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("search-box");
        searchBox.getChildren().addAll(searchPath, searchField);

        VBox listBox = new VBox();
        listBox.getStyleClass().add("list-box");
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(listBox);

        VBox browseBox = new VBox();
        browseBox.getStyleClass().add("browse-box");
        browseBox.getChildren().addAll(searchBox, listBox);
        return browseBox;
    }

    private AnchorPane actionPane() {
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-button");
        cancelButton.setOnAction(event -> cancelCreating());
        AnchorPane.setTopAnchor(cancelButton, 0d);
        AnchorPane.setLeftAnchor(cancelButton, 0d);

        Button okButton = new Button("Okay");
        okButton.getStyleClass().add("ok-button");
        AnchorPane.setTopAnchor(okButton, 0d);
        AnchorPane.setRightAnchor(okButton, 0d);

        AnchorPane actionPane = new AnchorPane();
        actionPane.getStyleClass().add("action-pane");
        actionPane.getChildren().addAll(cancelButton, okButton);
        return actionPane;
    }

    private void cancelCreating() {
        MusicApp musicApp = (MusicApp) context.getBean("musicApp");
        musicApp.cancelCreatePlaylist();
    }

    private void checkCancel(MouseEvent event) {
        if (event.getTarget().equals(this)) cancelCreating();
    }
}
