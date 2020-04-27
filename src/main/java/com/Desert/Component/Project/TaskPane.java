package com.Desert.Component.Project;

import com.Desert.Control.Public.RemoveButton;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.geometry.Insets;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class TaskPane extends VBox implements FXUtility {

    @Autowired
    private SubtaskPane subtaskPane;
    @Autowired
    private TaskNotePane taskNotePane;
    @Autowired
    private AttachmentPane attachmentPane;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("task-pane");
        getChildren().addAll(titleBox(), contentBox(), deadlineBox(), new RemoveButton("Delete this Task"));
    }

    private HBox titleBox() throws IOException {
        TextField titleField = new TextField("Task 1");
        titleField.getStyleClass().add("title-field");

        SVGButton editButton = new SVGButton(getVector("edit"), 26, 26);
        editButton.setAnchor(3, 3);
        editButton.setMaxSize(32, 32);
        HBox.setMargin(editButton, new Insets(0, 0, 0, 8));

        Button sendButton = new Button("Send to Desktop");
        sendButton.getStyleClass().add("send-button");
        HBox.setMargin(sendButton, new Insets(0, 0, 0, 32));

        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-box");
        titleBox.getChildren().addAll(titleField, editButton, sendButton);
        titleBox.setMaxHeight(40);
        return titleBox;
    }

    private HBox contentBox() {
        HBox contentBox = new HBox();
        contentBox.getStyleClass().add("content-box");
        contentBox.getChildren().addAll(subtaskPane, taskNotePane, attachmentPane);

        return contentBox;
    }

    private HBox deadlineBox() {
        Text labelText = new Text("Deadline:");
        labelText.getStyleClass().add("label-text");
        HBox.setMargin(labelText, new Insets(0, 0, 0, 32));

        Button button = new Button("20/01/2020");
        button.getStyleClass().add("button");

        HBox deadlineBox = new HBox();
        deadlineBox.getStyleClass().add("deadline-box");
        deadlineBox.getChildren().addAll(labelText, button);
        return deadlineBox;
    }
}

@Component
class TaskNotePane extends AnchorPane implements FXUtility {

    public TaskNotePane() throws IOException {
        Text titleText = new Text("Note");
        titleText.getStyleClass().add("title-text");
        AnchorPane.setTopAnchor(titleText, 0d);
        AnchorPane.setLeftAnchor(titleText, 0d);

        TextArea addArea = new TextArea();
        addArea.getStyleClass().add("add-area");
        addArea.setPromptText("To remember something...");
        AnchorPane.setTopAnchor(addArea, 56d);

        VBox noteBox = new VBox();
        noteBox.getStyleClass().add("note-box");
        noteBox.getChildren().add(new TaskNoteCard());
        AnchorPane.setTopAnchor(noteBox, 192d);

        getStyleClass().add("task-note-pane");
        getChildren().addAll(titleText, addArea, noteBox);
    }
}

@Component
class SubtaskPane extends AnchorPane implements FXUtility {

    public SubtaskPane() throws IOException {
        getStyleClass().add("subtask-pane");

        Text labelText = new Text("Subtask");
        labelText.getStyleClass().add("label-text");
        AnchorPane.setTopAnchor(labelText, 0d);
        AnchorPane.setLeftAnchor(labelText, 0d);

        TextField addField = new TextField();
        addField.getStyleClass().add("add-field");
        addField.setPromptText("Thing to do");
        addField.prefWidthProperty().bindBidirectional(prefWidthProperty());
        AnchorPane.setTopAnchor(addField, 56d);

        VBox subtaskBox = new VBox();
        subtaskBox.getStyleClass().add("subtask-box");
        subtaskBox.prefWidthProperty().bindBidirectional(prefWidthProperty());
        subtaskBox.getChildren().add(new SubtaskCard());
        AnchorPane.setTopAnchor(subtaskBox, 136d);

        getChildren().addAll(labelText, addField, subtaskBox);
    }
}

class SubtaskCard extends AnchorPane implements FXUtility {

    public SubtaskCard() throws IOException {
        getStyleClass().add("subtask-card");

        SVGButton deleteButton = new SVGButton(getVector("delete"), 14, 18);
        deleteButton.setAnchor(5, 3);
        deleteButton.setVectorColor("#DB3737");
        AnchorPane.setTopAnchor(deleteButton, 16d);
        AnchorPane.setLeftAnchor(deleteButton, 16d);

        TextField titleField = new TextField("Subtask");
        titleField.getStyleClass().add("title-field");
        AnchorPane.setBottomAnchor(titleField, 6d);
        AnchorPane.setLeftAnchor(titleField, 48d);

        SVGButton dragButton = new SVGButton(getVector("drag"), 16, 6);
        dragButton.setAnchor(4, 8);
        dragButton.setCursor(Cursor.CLOSED_HAND);
        AnchorPane.setTopAnchor(dragButton, 16d);
        AnchorPane.setRightAnchor(dragButton, 16d);

        CheckBox checkBox = new CheckBox();
        checkBox.getStyleClass().add("check-box");
        AnchorPane.setTopAnchor(checkBox, 16d);
        AnchorPane.setRightAnchor(checkBox, 56d);

        getChildren().addAll(deleteButton, titleField, dragButton, checkBox);
    }
}

class TaskNoteCard extends AnchorPane implements FXUtility {

    public TaskNoteCard() throws IOException {
        TextArea contentArea = new TextArea();
        contentArea.getStyleClass().add("content-area");
        contentArea.setPromptText("To remember something");

        SVGButton deleteButton = new SVGButton(getVector("delete"), 14, 18);
        deleteButton.setAnchor(5, 3);
        deleteButton.setVectorColor("#DB3737");
        AnchorPane.setBottomAnchor(deleteButton, 8d);
        AnchorPane.setLeftAnchor(deleteButton, 8d);

        SVGButton dragButton = new SVGButton(getVector("drag"), 16, 6);
        dragButton.setAnchor(4, 9);
        AnchorPane.setTopAnchor(dragButton, 8d);
        AnchorPane.setRightAnchor(dragButton, 8d);

        getStyleClass().add("task-note-card");
        getChildren().addAll(contentArea, deleteButton, dragButton);
    }
}