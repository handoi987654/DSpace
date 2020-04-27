package com.Desert.Control.Project;

import com.Desert.Utility.FXUtility;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;

public class PPane extends VBox implements FXUtility {

    private VBox contentBox;
    private TextField addingField;

    public PPane() {
        getStyleClass().add("ppane");
        getChildren().addAll(addingBox(), scrollPane());
    }

    private ScrollPane scrollPane() {
        contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(contentBox);
        scrollPane.getStyleClass().add("content-scroll-pane");
        AnchorPane.setTopAnchor(scrollPane, 88d);

        return scrollPane;
    }

    private VBox addingBox() {
        addingField = new TextField();
        addingField.setEditable(false);
        addingField.setText("+ Keep walking");
        addingField.getStyleClass().add("field");

        VBox addingBox = new VBox();
        addingBox.getChildren().add(addingField);
        addingBox.getStyleClass().add("adding-box");
        VBox.setMargin(addingBox, new Insets(0, 0, 16, 0));
        return addingBox;
    }

    public final void setAddingBoxText(String text) {
        addingField.setText(text);
    }

    public final void addTab(Node content) {
        contentBox.getChildren().add(content);
    }
}
