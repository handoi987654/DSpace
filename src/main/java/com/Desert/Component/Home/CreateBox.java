package com.Desert.Component.Home;

import com.Desert.Application.IDeaApp;
import com.Desert.Application.ProjectApp;
import com.Desert.Entity.IDea.IDea;
import com.Desert.Entity.Project.Project;
import com.Desert.Service.HomeService;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.time.LocalDateTime;

enum CreatingType {
    PROJECT, IDEA
}

@Component
@Scope("prototype")
public class CreateBox extends HBox {

    @Autowired
    private HomeService homeService;
    @Autowired
    private ApplicationContext context;

    @Value("classpath:images/razer.png")
    private Resource bgRsrc;

    @Setter
    private CreatingType creatingType;
    private Button okButton;
    private TextField nameField;
    private TextArea descriptionArea;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("create-box");
        getChildren().addAll(infoPane(), picturePane());
    }

    private AnchorPane infoPane() {
        Label nameLabel = new Label("What will we name it?");
        nameLabel.getStyleClass().add("name-label");
        AnchorPane.setTopAnchor(nameLabel, 0d);

        nameField = new TextField();
        nameField.getStyleClass().add("name-field");
        nameField.setPromptText("Name...");
        AnchorPane.setTopAnchor(nameField, 40d);

        Label descriptionLabel = new Label("May we describe a little bit?");
        descriptionLabel.getStyleClass().add("description-label");
        AnchorPane.setTopAnchor(descriptionLabel, 112d);

        descriptionArea = new TextArea();
        descriptionArea.getStyleClass().add("description-area");
        descriptionArea.setPromptText("Description...");
        AnchorPane.setTopAnchor(descriptionArea, 152d);

        AnchorPane infoPane = new AnchorPane();
        infoPane.getStyleClass().add("info-pane");
        infoPane.getChildren().addAll(nameLabel, nameField, descriptionLabel, descriptionArea);

        return infoPane;
    }

    private AnchorPane picturePane() throws IOException {
        Label pictureLabel = new Label("Choose a picture for beauty :3");
        pictureLabel.getStyleClass().add("label");
        AnchorPane.setTopAnchor(pictureLabel, 0d);

        Button galleryButton = new Button("Gallery");
        galleryButton.getStyleClass().add("button");
        AnchorPane.setTopAnchor(galleryButton, 40d);

        Button imageButton = new Button("Image");
        imageButton.getStyleClass().add("button");
        AnchorPane.setTopAnchor(imageButton, 40d);
        AnchorPane.setLeftAnchor(imageButton, 144d);

        ImageView imageView = new ImageView(new Image(bgRsrc.getInputStream()));
        imageView.setPreserveRatio(true);
        imageView.setSmooth(true);
        imageView.setFitWidth(488);
        AnchorPane.setTopAnchor(imageView, 96d);

        okButton = new Button();
        okButton.getStyleClass().add("ok-button");
        okButton.setOnMouseClicked(event -> create());
        AnchorPane.setBottomAnchor(okButton, 0d);

        AnchorPane picturePane = new AnchorPane();
        picturePane.getStyleClass().add("picture-pane");
        picturePane.getChildren().addAll(pictureLabel, galleryButton, imageButton, imageView, okButton);

        return picturePane;
    }

    public final void setOKButtonText(String text) {
        okButton.setText(text);
    }

    private void create() {
        String name = nameField.getText();
        String description = descriptionArea.getText();
        Parent parent;

        if (creatingType.equals(CreatingType.PROJECT)) {
            Project project = new Project();
            project.setName(name);
            project.setDescription(description);
            project.setCreatedDateTime(LocalDateTime.now());
            project.setUpdatedDateTime(LocalDateTime.now());
            homeService.insertProject(project);

            parent = (ProjectApp) context.getBean("projectApp");
        } else {
            IDea iDea = new IDea();
            iDea.setName(name);
            iDea.setDescription(description);
            iDea.setCreatedDateTime(LocalDateTime.now());
            iDea.setUpdatedDateTime(LocalDateTime.now());
            homeService.insertIDea(iDea);

            parent = (IDeaApp) context.getBean("IDeaApp");
        }

        Scene scene = (Scene) context.getBean("scene");
        scene.setRoot(parent);
    }
}
