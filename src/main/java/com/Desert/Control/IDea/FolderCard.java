package com.Desert.Control.IDea;

import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import java.io.IOException;

public class FolderCard extends AnchorPane implements FXUtility {

    public FolderCard() throws IOException {
        getStyleClass().add("rsrc-card");
        getChildren().addAll(folderButton(), titleField(), folderGrid());
    }

    private SVGButton folderButton() throws IOException {
        SVGButton folderButton = new SVGButton(getVector("folder-collapsed"), 20, 16);
        folderButton.setAnchor(2, 4);
        AnchorPane.setTopAnchor(folderButton, 0d);
        AnchorPane.setLeftAnchor(folderButton, 0d);
        return folderButton;
    }

    private TextField titleField() {
        TextField titleField = new TextField("Courses");
        titleField.getStyleClass().add("title-field");
        AnchorPane.setLeftAnchor(titleField, 32d);
        return titleField;
    }

    private GridPane folderGrid() {
        GridPane folderGrid = new GridPane();
        folderGrid.getStyleClass().add("file-grid");
        folderGrid.add(browseBox(), 0, 0);
        AnchorPane.setTopAnchor(folderGrid, 40d);
        AnchorPane.setLeftAnchor(folderGrid, 0d);
        return folderGrid;
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
