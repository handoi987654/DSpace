package com.Desert.Component.IDea;

import com.Desert.Application.IDeaApp;
import com.Desert.Control.IDea.FileCard;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.css.PseudoClass;
import javafx.geometry.Insets;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.ScrollEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class TransferPane extends VBox implements FXUtility {

    @Autowired
    private ApplicationContext context;
    @Value("classpath:images/ghost-rider.png")
    private Resource pictureRsrc;

    private VBox mainBox;
    private ScrollPane scrollPane;

    @PostConstruct
    private void render() throws IOException {
        mainBox = new VBox();
        mainBox.getStyleClass().add("main-box");
        mainBox.pseudoClassStateChanged(PseudoClass.getPseudoClass("up"), true);
        mainBox.getChildren().addAll(titleText(), scrollPane(), actionPane());

        getStyleClass().add("transfer-pane");
        getChildren().add(mainBox);
    }

    private Text titleText() {
        Text titleText = new Text("It's time to make your dream comes true :)))");
        titleText.getStyleClass().add("title-text");
        VBox.setMargin(titleText, new Insets(0, 0, 32, 0));
        return titleText;
    }

    private ScrollPane scrollPane() throws IOException {
        AnchorPane formPane = new AnchorPane();
        formPane.getStyleClass().add("form-pane");
        formPane.getChildren().addAll(nameBox(), descriptionBox(), imagePane());

        Text rsrcText = new Text("May need some stuff for your project?");
        rsrcText.getStyleClass().add("rsrc-text");
        VBox.setMargin(rsrcText, new Insets(0, 0, 32, 0));

        GridPane rsrcGrid = new GridPane();
        rsrcGrid.getStyleClass().add("rsrc-grid");
        rsrcGrid.add(new FileCard(), 0, 0);

        VBox box = new VBox();
        box.getStyleClass().add("scroll-box");
        box.getChildren().addAll(formPane, arrowButton(), rsrcText, rsrcGrid);

        scrollPane = new ScrollPane();
        scrollPane.setContent(box);
        scrollPane.getContent().setOnScroll(this::scroll);
        return scrollPane;
    }

    private AnchorPane actionPane() {
        Button cancelButton = new Button("Hold on :v");
        cancelButton.getStyleClass().add("cancel-button");
        cancelButton.setOnAction(event -> cancelTransferring());
        AnchorPane.setTopAnchor(cancelButton, 0d);
        AnchorPane.setLeftAnchor(cancelButton, 0d);

        Button okButton = new Button("It's my show time! :D");
        okButton.getStyleClass().add("ok-button");
        AnchorPane.setTopAnchor(okButton, 0d);
        AnchorPane.setRightAnchor(okButton, 0d);

        AnchorPane actionPane = new AnchorPane();
        actionPane.getStyleClass().add("action-pane");
        actionPane.getChildren().addAll(cancelButton, okButton);
        return actionPane;
    }

    /*
    Form Pane
     */

    private VBox nameBox() {
        Label label = new Label("Make it my dream:");

        TextField nameField = new TextField();
        nameField.setPromptText("Name");

        VBox nameBox = new VBox();
        nameBox.getStyleClass().add("name-box");
        nameBox.getChildren().addAll(label, nameField);
        return nameBox;
    }

    private VBox descriptionBox() {
        Label label = new Label("And I know how my dream is...");

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description");

        VBox descriptionBox = new VBox();
        descriptionBox.getStyleClass().add("description-box");
        descriptionBox.getChildren().addAll(label, descriptionArea);
        AnchorPane.setTopAnchor(descriptionBox, 96d);
        return descriptionBox;
    }

    private AnchorPane imagePane() throws IOException {
        Label label = new Label("A picture of my dream :3");

        Button galleryButton = new Button("Gallery");
        AnchorPane.setTopAnchor(galleryButton, 40d);

        Button imageButton = new Button("Image");
        AnchorPane.setTopAnchor(imageButton, 40d);
        AnchorPane.setLeftAnchor(imageButton, 144d);

        Image image = new Image(pictureRsrc.getInputStream());
        ImageView imageView = new ImageView(image);
        imageView.setSmooth(true);
        imageView.setPreserveRatio(true);
        imageView.setFitHeight(284);
        AnchorPane.setTopAnchor(imageView, 88d);
        AnchorPane.setBottomAnchor(imageView, 0d);

        AnchorPane imagePane = new AnchorPane();
        imagePane.getStyleClass().add("image-pane");
        imagePane.getChildren().addAll(label, galleryButton, imageButton, imageView);
        AnchorPane.setLeftAnchor(imagePane, 571d);
        return imagePane;
    }

    private SVGButton arrowButton() throws IOException {
        SVGButton downButton = new SVGButton(getVector("down-arrow"), 24, 14);
        downButton.setAnchor(4, 9);
        downButton.setMaxWidth(32);
        VBox.setMargin(downButton, new Insets(40, 0, 16, 0));
        return downButton;
    }

    private void cancelTransferring() {
        IDeaApp iDeaApp = (IDeaApp) context.getBean("IDeaApp");
        iDeaApp.cancelTransferring();
    }

    private void scroll(ScrollEvent event) {
        if (event.getDeltaY() > 0) {
            mainBox.pseudoClassStateChanged(PseudoClass.getPseudoClass("up"), true);
            mainBox.pseudoClassStateChanged(PseudoClass.getPseudoClass("down"), false);
            scrollPane.setVvalue(0);
        } else {
            mainBox.pseudoClassStateChanged(PseudoClass.getPseudoClass("down"), true);
            mainBox.pseudoClassStateChanged(PseudoClass.getPseudoClass("up"), false);
            scrollPane.setVvalue(1);
        }
    }

}
