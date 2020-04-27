package com.Desert.Control.IDea;

import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class FileCard extends AnchorPane implements FXUtility {

    public FileCard() throws IOException {
        getStyleClass().add("rsrc-card");
        getChildren().addAll(fileButton(), titleField(), fileGrid());
    }

    private SVGButton fileButton() throws IOException {
        SVGButton fileButton = new SVGButton(getVector("file"), 16, 20);
        fileButton.setAnchor(4, 2);
        AnchorPane.setTopAnchor(fileButton, 0d);
        AnchorPane.setLeftAnchor(fileButton, 0d);
        return fileButton;
    }

    private TextField titleField() {
        TextField titleField = new TextField("Ebooks");
        titleField.getStyleClass().add("title-field");
        AnchorPane.setLeftAnchor(titleField, 32d);
        return titleField;
    }

    private GridPane fileGrid() {
        GridPane fileGrid = new GridPane();
        fileGrid.getStyleClass().add("file-grid");
        fileGrid.add(browseBox(), 0, 0);
        AnchorPane.setTopAnchor(fileGrid, 40d);
        AnchorPane.setLeftAnchor(fileGrid, 0d);
        return fileGrid;
    }

    private VBox browseBox() {
        Text browseText = new Text("Browse...");
        browseText.getStyleClass().add("browse-text");

        VBox browseBox = new VBox();
        browseBox.getStyleClass().add("browse-box");
        browseBox.getChildren().add(browseText);

        return browseBox;
    }
}
