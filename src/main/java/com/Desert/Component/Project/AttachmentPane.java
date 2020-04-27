package com.Desert.Component.Project;

import com.Desert.Control.Public.FileItem;
import com.Desert.Utility.FXUtility;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.text.Text;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
@Scope("prototype")
public class AttachmentPane extends AnchorPane implements FXUtility {

    public AttachmentPane() throws IOException {
        Text titleText = new Text("Attachment");
        titleText.getStyleClass().add("title-text");
        AnchorPane.setTopAnchor(titleText, 0d);

        GridPane filePane = new GridPane();
        filePane.getStyleClass().add("file-pane");
        filePane.add(new FileItem(), 0, 0);
        AnchorPane.setTopAnchor(filePane, 64d);

        getStyleClass().add("attachment-pane");
        getChildren().addAll(titleText, filePane);
    }
}
