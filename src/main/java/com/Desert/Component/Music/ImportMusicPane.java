package com.Desert.Component.Music;


import com.Desert.Application.MusicApp;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.ProgressBar;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class ImportMusicPane extends VBox implements FXUtility {

    @Autowired
    private ApplicationContext context;

    @Value("classpath:images/maroon-5.jpg")
    private Resource coverRsrc;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("import-music-pane");
        getChildren().addAll(titleBox(), scrollPane(), actionPane());
        setOnMouseClicked(this::checkCancel);
    }

    private HBox titleBox() {
        Text titleText = new Text("Color of Life");
        titleText.getStyleClass().add("title-text");

        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-box");
        titleBox.getChildren().add(titleText);
        return titleBox;
    }

    private ScrollPane scrollPane() throws IOException {
        HBox browseButton = addButton(getVector("folder-expanded"), "Browse...");
        HBox urlButton = addButton(getVector("link"), "Paste URL");

        HBox addBox = new HBox();
        addBox.getStyleClass().add("add-box");
        addBox.getChildren().addAll(browseButton, urlButton);

        GridPane gridPane = new GridPane();
        gridPane.getColumnConstraints().add(new ColumnConstraints(432));
        gridPane.getColumnConstraints().add(new ColumnConstraints(432));
        gridPane.getStyleClass().add("music-grid");
        gridPane.add(addBox, 0, 0);
        gridPane.add(musicCard(), 1, 0);
        gridPane.add(retrievingCard(), 0, 1);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(gridPane);
        return scrollPane;
    }

    private AnchorPane actionPane() {
        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-button");
        cancelButton.setOnAction(event -> cancelImport());
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

    private HBox addButton(Resource svgRsrc, String title) throws IOException {
        SVGPath path = new SVGPath();
        path.setContent(SVGPathGenerator.generate(svgRsrc));
        path.setFill(Color.WHITE);

        Text titleText = new Text(title);
        titleText.getStyleClass().add("title-text");

        HBox addButton = new HBox();
        addButton.getStyleClass().add("add-button");
        addButton.getChildren().addAll(path, titleText);
        return addButton;
    }

    private AnchorPane musicCard() throws IOException {
        Image coverImage = new Image(coverRsrc.getInputStream());
        ImageView coverView = new ImageView(coverImage);
        coverView.setPreserveRatio(true);
        coverView.setSmooth(true);
        coverView.setFitHeight(152);

        TextField titleField = new TextField("Memories");
        titleField.setPromptText("Title");
        titleField.getStyleClass().add("title-field");
        AnchorPane.setTopAnchor(titleField, 32d);
        AnchorPane.setLeftAnchor(titleField, 168d);

        TextField artistField = new TextField("Maroon 5");
        artistField.setPromptText("Artist | Singer");
        artistField.getStyleClass().add("artist-field");
        AnchorPane.setTopAnchor(artistField, 96d);
        AnchorPane.setLeftAnchor(artistField, 168d);

        SVGPath removePath = new SVGPath();
        removePath.setContent(SVGPathGenerator.generate(getVector("remove")));
        removePath.getStyleClass().add("remove-path");
        AnchorPane.setBottomAnchor(removePath, 10d);
        AnchorPane.setRightAnchor(removePath, 10d);

        AnchorPane musicCard = new AnchorPane();
        musicCard.getStyleClass().add("music-card");
        musicCard.getChildren().addAll(coverView, titleField, artistField, removePath);
        return musicCard;
    }

    private VBox retrievingCard() {
        Text text = new Text("Retrieving Information");
        text.getStyleClass().add("title-text");
        VBox.setMargin(text, new Insets(40, 0, 0, 0));

        ProgressBar progressBar = new ProgressBar();
        progressBar.getStyleClass().add("bar");

        VBox retrievingCard = new VBox();
        retrievingCard.getStyleClass().add("retrieving-card");
        retrievingCard.getChildren().addAll(text, progressBar);
        return retrievingCard;
    }

    private void cancelImport() {
        MusicApp musicApp = (MusicApp) context.getBean("musicApp");
        musicApp.cancelImportMusic();
    }

    private void checkCancel(MouseEvent event) {
        if (event.getTarget().equals(this)) cancelImport();
    }
}