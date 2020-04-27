package com.Desert.Component.IDea;

import com.Desert.Control.Public.SVGButton;
import com.Desert.Entity.IDea.IDeaQuestion;
import com.Desert.Utility.FXUtility;
import javafx.geometry.Insets;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.scene.control.TitledPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class QuestionPane extends HBox implements FXUtility {

    private VBox leftPane, rightPane;

    @PostConstruct
    public void render() throws IOException {
        getStyleClass().add("question-pane");
        getChildren().addAll(leftPane(), rightPane());
    }

    private VBox leftPane() throws IOException {
        TextField addField = new TextField();
        addField.setPromptText("Well, something need to be considered");
        addField.getStyleClass().add("add-field");

        leftPane = new VBox();
        leftPane.getStyleClass().add("pane");
        leftPane.getChildren().addAll(addField, questionCard());

        return leftPane;
    }

    private VBox rightPane() throws IOException {
        rightPane = new VBox();
        rightPane.getStyleClass().add("pane");
        rightPane.getChildren().add(questionCard());

        return rightPane;
    }

    private AnchorPane questionCard() throws IOException {
        /*
        Question Box
         */
        TextField questionField = new TextField("Some question");
        questionField.getStyleClass().add("question-field");

        HBox questionBox = new HBox();
        questionBox.getStyleClass().add("question-box");
        questionBox.getChildren().add(questionField);

        /*
        Option Box (vbox)
         */
        RadioButton optionButton = new RadioButton("An option");
        optionButton.getStyleClass().add("option-button");

        TextField addOptionField = new TextField();
        addOptionField.setPromptText("+ Maybe something else...");
        addOptionField.getStyleClass().add("add-option-field");
        VBox.setMargin(addOptionField, new Insets(0, 0, 0, 8));

        VBox vBox = new VBox();
        vBox.getStyleClass().add("vbox");
        vBox.getChildren().addAll(optionButton, addOptionField);
        AnchorPane.setTopAnchor(vBox, 0d);
        AnchorPane.setLeftAnchor(vBox, 0d);
        AnchorPane.setBottomAnchor(vBox, 48d);

        /*
        Controls
         */
        SVGButton deleteButton = new SVGButton(getVector("delete"), 14, 18);
        deleteButton.setAnchor(9, 7);
        deleteButton.getStyleClass().add("delete-button");
        AnchorPane.setBottomAnchor(deleteButton, 0d);
        AnchorPane.setLeftAnchor(deleteButton, 0d);

        SVGButton makeNoteButton = new SVGButton(getVector("note"), 16, 20);
        makeNoteButton.setAnchor(8, 6);
        makeNoteButton.getStyleClass().add("make-note-button");
        AnchorPane.setBottomAnchor(makeNoteButton, 0d);
        AnchorPane.setLeftAnchor(makeNoteButton, 48d);

        /*
        AddAll
         */
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getStyleClass().add("anchorpane");
        anchorPane.getChildren().addAll(vBox, deleteButton, makeNoteButton);

        TitledPane titledPane = new TitledPane();
        titledPane.setExpanded(true);
        titledPane.setContent(anchorPane);

        /*
        Return
         */
        AnchorPane questionCard = new AnchorPane();
        questionCard.getStyleClass().add("question-card");
        questionCard.getChildren().addAll(questionBox, titledPane);
        return questionCard;
    }

    public final void loadQuestion(List<IDeaQuestion> questionList) {

    }
}