package com.Desert.Component.Music;


import com.Desert.Application.MusicApp;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.SVGPathGenerator;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class MusicPane extends HBox {

    @Autowired
    private MusicListPane musicListPane;
    @Autowired
    private MetadataPane metadataPane;
    @Autowired
    private ApplicationContext context;

    @Value("classpath:vectors/add.svg")
    private Resource addSvgRsrc;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("music-pane");
        getChildren().addAll(addButton(), musicListPane, metadataPane);
    }

    private SVGButton addButton() throws IOException {
        SVGButton addButton = new SVGButton(addSvgRsrc, 14, 14);
        addButton.setAnchor(13, 13);
        addButton.getStyleClass().add("add-button");
        addButton.setOnMouseClicked(event -> openImportPane());
        return addButton;
    }

    private void openImportPane() {
        MusicApp musicApp = (MusicApp) context.getBean("musicApp");
        musicApp.openImportMusicPane();
    }
}

@Component
@Scope("prototype")
class MusicListPane extends AnchorPane {

    @Value("classpath:vectors/search.svg")
    private Resource searchRsrc;
    @Value("classpath:vectors/music-node.svg")
    private Resource musicNodeRsrc;
    @Value("classpath:vectors/more.svg")
    private Resource moreRsrc;

    private ContextMenu optionMenu;

    @PostConstruct
    private void render() throws IOException {
        optionMenu = optionMenu();
        getStyleClass().add("music-list-pane");
        getChildren().addAll(searchBox(), scrollPane());
    }

    private ContextMenu optionMenu() {
        optionMenu = new ContextMenu();
        optionMenu.getStyleClass().add("option-menu");
        optionMenu.getItems().addAll(
                menuItem("Add to Queue"),
                menuItem("Edit Metadata"),
                menuItem("Remove"));
        return optionMenu;
    }

    private MenuItem menuItem(String optionText) {
        MenuItem menuItem = new MenuItem(optionText);
        menuItem.getStyleClass().add("option-item");
        return menuItem;
    }

    private HBox searchBox() throws IOException {
        SVGPath searchPath = new SVGPath();
        searchPath.setContent(SVGPathGenerator.generate(searchRsrc));
        Region searchRegion = new Region();
        searchRegion.getStyleClass().add("search-icon");
        searchRegion.setShape(searchPath);

        TextField searchField = new TextField();
        searchField.setPromptText("Search...");
        searchField.getStyleClass().add("search-field");

        HBox searchBox = new HBox();
        searchBox.getStyleClass().add("search-box");
        searchBox.getChildren().addAll(searchRegion, searchField);
        return searchBox;
    }

    private ScrollPane scrollPane() throws IOException {
        VBox listBox = new VBox();
        listBox.getStyleClass().add("list-box");
        listBox.getChildren().add(songItem());
        listBox.getChildren().add(songItem());

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(listBox);
        AnchorPane.setTopAnchor(scrollPane, 56d);
        return scrollPane;
    }

    private AnchorPane songItem() throws IOException {
        SVGPath nodePath = new SVGPath();
        nodePath.setContent(SVGPathGenerator.generate(musicNodeRsrc));
        Region nodeRegion = new Region();
        nodeRegion.setShape(nodePath);
        nodeRegion.getStyleClass().add("node-icon");
        AnchorPane.setTopAnchor(nodeRegion, 16d);
        AnchorPane.setLeftAnchor(nodeRegion, 16d);
        AnchorPane.setBottomAnchor(nodeRegion, 16d);

        Text titleText = new Text("Memories");
        titleText.getStyleClass().add("title-text");
        AnchorPane.setTopAnchor(titleText, 16d);
        AnchorPane.setLeftAnchor(titleText, 80d);

        Text artistText = new Text("Maroon 5");
        artistText.getStyleClass().add("artist-text");
        AnchorPane.setTopAnchor(artistText, 16d);
        AnchorPane.setLeftAnchor(artistText, 424d);

        SVGButton optionButton = new SVGButton(moreRsrc, 16, 4);
        optionButton.setAnchor(4, 10);
        optionButton.getStyleClass().add("option-button");
        optionButton.setOnMouseClicked(event -> openOptionMenu(optionButton));
        AnchorPane.setTopAnchor(optionButton, 12d);
        AnchorPane.setRightAnchor(optionButton, 16d);

        AnchorPane songItem = new AnchorPane();
        songItem.getStyleClass().add("song-item");
        songItem.getChildren().addAll(nodeRegion, titleText, artistText, optionButton);
        return songItem;
    }

    private void openOptionMenu(SVGButton optionButton) {
        double offsetX = optionButton.getScene().getWindow().getX();
        double offsetY = optionButton.getScene().getWindow().getY();
        double x = optionButton.localToScene(optionButton.getBoundsInLocal()).getMinX();
        double y = optionButton.localToScene(optionButton.getBoundsInLocal()).getMinY();
        optionMenu.show(optionButton, x + offsetX - 104, y + offsetY + 32);
    }
}

@Component
@Scope("prototype")
class MetadataPane extends VBox {

    @Value("classpath:images/maroon-5.jpg")
    private Resource coverRsrc;
    @Value("classpath:vectors/check.svg")
    private Resource checkRsrc;
    @Value("classpath:vectors/image.svg")
    private Resource imageSVGRsrc;
    @Value("classpath:vectors/music-picture-big-mask.svg")
    private Resource maskRsrc;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("metadata-pane");
        getChildren().addAll(coverPane(), titleEditBox(), artistEditBox(), saveButton());
    }

    private AnchorPane coverPane() throws IOException {
        SVGPath clipPath = new SVGPath();
        clipPath.setContent(SVGPathGenerator.generate(maskRsrc));

        Image coverImage = new Image(coverRsrc.getInputStream());
        ImageView coverView = new ImageView(coverImage);
        coverView.setPreserveRatio(true);
        coverView.setSmooth(true);
        coverView.setFitWidth(248);
        coverView.setClip(clipPath);
        coverView.getStyleClass().add("cover-view");
        VBox.setMargin(coverView, new Insets(0, 0, 16, 0));

        SVGButton pictureButton = new SVGButton(imageSVGRsrc, 18, 18);
        pictureButton.setAnchor(11, 11);
        pictureButton.getStyleClass().add("picture-button");
        AnchorPane.setBottomAnchor(pictureButton, 0d);
        AnchorPane.setRightAnchor(pictureButton, 0d);

        AnchorPane coverPane = new AnchorPane();
        coverPane.getStyleClass().add("cover-pane");
        coverPane.getChildren().addAll(coverView, pictureButton);
        return coverPane;
    }

    private VBox titleEditBox() {
        Label titleLabel = new Label("Title");
        titleLabel.getStyleClass().add("label");

        TextField titleField = new TextField("Memories");
        titleField.getStyleClass().add("field");

        VBox titleEditBox = new VBox();
        titleEditBox.getStyleClass().add("edit-box");
        titleEditBox.getChildren().addAll(titleLabel, titleField);
        return titleEditBox;
    }

    private VBox artistEditBox() {
        Label artistLabel = new Label("Singer | Artist");
        artistLabel.getStyleClass().add("label");

        TextField artistField = new TextField("Maroon 5");
        artistField.getStyleClass().add("field");

        VBox artistEditBox = new VBox();
        artistEditBox.getStyleClass().add("edit-box");
        artistEditBox.getChildren().addAll(artistLabel, artistField);
        return artistEditBox;
    }

    private HBox saveButton() throws IOException {
        Text saveText = new Text("Save");
        saveText.getStyleClass().add("save-text");

        SVGPath checkPath = new SVGPath();
        checkPath.setContent(SVGPathGenerator.generate(checkRsrc));
        Region checkRegion = new Region();
        checkRegion.setShape(checkPath);
        checkRegion.getStyleClass().add("check-region");
        HBox.setMargin(checkRegion, new Insets(0, 0, 0, 8));

        HBox saveButton = new HBox();
        saveButton.getStyleClass().add("save-button");
        saveButton.getChildren().addAll(saveText, checkRegion);
        return saveButton;
    }
}


