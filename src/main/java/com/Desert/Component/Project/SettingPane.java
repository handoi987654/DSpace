package com.Desert.Component.Project;

import com.Desert.Control.Public.RemoveButton;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Entity.Project.Project;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.UserData;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class SettingPane extends VBox implements FXUtility {

    @Autowired
    private UserData userData;

    @Value("classpath:images/razer.png")
    private Resource razerRsrc;

    private Project project;
    private TextField nameField;
    private TextArea descriptionArea;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("setting-pane");
        getChildren().addAll(titleBox(), detailPane(), new RemoveButton("Delete this Project"));
    }

    private HBox titleBox() throws IOException {
        nameField = new TextField();
        nameField.getStyleClass().add("name-field");
        nameField.setText("Project");

        SVGButton editButton = new SVGButton(getVector("edit"), 26, 26);
        editButton.setMaxSize(32, 32);
        editButton.setAnchor(3, 3);
        editButton.setVectorColor("#ffffff");
        HBox.setMargin(editButton, new Insets(0, 0, 0, 8));

        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-box");
        titleBox.getChildren().addAll(nameField, editButton);
        return titleBox;
    }

    private AnchorPane detailPane() throws IOException {
        Label descriptionLabel = new Label("Description");
        descriptionLabel.getStyleClass().add("description-label");

        descriptionArea = new TextArea();
        descriptionArea.getStyleClass().add("description-area");
        AnchorPane.setTopAnchor(descriptionArea, 40d);

        Label pictureLabel = new Label("Cover Picture");
        pictureLabel.getStyleClass().add("picture-label");
        AnchorPane.setLeftAnchor(pictureLabel, 664d);

        Button galleryButton = new Button();
        galleryButton.getStyleClass().add("gallery-button");
        galleryButton.setText("Gallery");
        AnchorPane.setTopAnchor(galleryButton, 40d);
        AnchorPane.setLeftAnchor(galleryButton, 664d);

        Button imageButton = new Button();
        imageButton.getStyleClass().add("image-button");
        imageButton.setText("Image");
        AnchorPane.setTopAnchor(imageButton, 40d);
        AnchorPane.setLeftAnchor(imageButton, 800d);

        Image image = new Image(razerRsrc.getInputStream(), 312, 184, true, true);
        ImageView imageView = new ImageView(image);
        AnchorPane.setBottomAnchor(imageView, 0d);
        AnchorPane.setRightAnchor(imageView, 0d);

        AnchorPane detailPane = new AnchorPane();
        detailPane.getStyleClass().add("detail-pane");
        detailPane.getChildren().addAll(descriptionLabel, descriptionArea, pictureLabel, galleryButton, imageButton, imageView);
        return detailPane;
    }

    public void load() {
        this.project = userData.getProject();
        nameField.setText(project.getName());
        descriptionArea.setText(project.getDescription());
    }
}
