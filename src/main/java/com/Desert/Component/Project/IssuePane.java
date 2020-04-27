package com.Desert.Component.Project;

import com.Desert.Control.Public.RemoveButton;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class IssuePane extends VBox implements FXUtility {

    @Autowired
    private AttachmentPane attachmentPane;

    @PostConstruct
    public void render() throws IOException {
        getStyleClass().add("issue-pane");
        getChildren().addAll(titleBox(), issueBox(), kindBox(), new RemoveButton("Delete this Task"));

    }

    private HBox titleBox() throws IOException {
        TextField titleField = new TextField("Some Issue");
        titleField.getStyleClass().add("title-field");

        SVGButton editButton = new SVGButton(getVector("edit"), 26, 26);
        editButton.setAnchor(3, 3);
        editButton.setMaxSize(32, 32);
        HBox.setMargin(editButton, new Insets(0, 0, 0, 8));

        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-box");
        titleBox.getChildren().addAll(titleField, editButton);
        return titleBox;
    }

    private HBox issueBox() {
        Text titleText = new Text("Description");
        titleText.getStyleClass().add("title-text");
        AnchorPane.setTopAnchor(titleText, 0d);

        TextArea area = new TextArea();
        area.getStyleClass().add("area");
        area.setPromptText("Description");
        AnchorPane.setTopAnchor(area, 64d);

        AnchorPane contentPane = new AnchorPane();
        contentPane.getStyleClass().add("content-pane");
        contentPane.getChildren().addAll(titleText, area);

        HBox issueBox = new HBox();
        issueBox.getStyleClass().add("issue-box");
        issueBox.getChildren().addAll(contentPane, attachmentPane);
        return issueBox;
    }

    private HBox kindBox() {
        Text text = new Text("Kind");
        text.setFill(Color.WHITE);
        text.setFont(new Font("SF Pro Text Light", 22));
        HBox.setMargin(text, new Insets(0, 0, 0, 392));

        RadioButton btn1 = new RadioButton("Bug");
        btn1.setFont(new Font("SF Pro Text Regular", 14));
        btn1.setTextFill(Color.WHITE);
        RadioButton btn2 = new RadioButton("Enhancement");
        btn2.setFont(new Font("SF Pro Text Regular", 14));
        btn2.setTextFill(Color.WHITE);

        ToggleGroup group = new ToggleGroup();
        btn1.setToggleGroup(group);
        btn2.setToggleGroup(group);

        VBox vBox = new VBox();
        vBox.setSpacing(16);
        vBox.getChildren().addAll(btn1, btn2);

        HBox kindBox = new HBox();
        kindBox.setSpacing(32);
        kindBox.getChildren().addAll(text, vBox);
        return kindBox;
    }
}
