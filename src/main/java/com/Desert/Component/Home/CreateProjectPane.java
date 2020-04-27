package com.Desert.Component.Home;

import com.Desert.Utility.FXUtility;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CreateProjectPane extends VBox implements FXUtility {

    @Autowired
    private CreateBox createBox;

    @PostConstruct
    private void render() {
        getStyleClass().add("create-project-pane");
        getChildren().addAll(welcomeText(), createBox());
    }

    private Text welcomeText() {
        Text welcomeText = new Text("So, we have things to do :))) Fill in some info, and we'll be ready :D");
        welcomeText.getStyleClass().add("welcome-text");
        return welcomeText;
    }

    private CreateBox createBox() {
        createBox.setCreatingType(CreatingType.PROJECT);
        createBox.setOKButtonText("Here we go! :D");
        return createBox;
    }
}
