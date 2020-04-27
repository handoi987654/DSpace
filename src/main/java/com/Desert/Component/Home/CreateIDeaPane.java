package com.Desert.Component.Home;

import com.Desert.Utility.FXUtility;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

@Component
public class CreateIDeaPane extends VBox implements FXUtility {

    @Autowired
    private CreateBox createBox;

    @PostConstruct
    private void render() {
        getStyleClass().add("create-idea-pane");
        getChildren().addAll(welcomePane(), createBox());
    }

    private AnchorPane welcomePane() {
        Text leftText = new Text("Is it imagination? Illusion?");
        leftText.getStyleClass().add("text");
        AnchorPane.setTopAnchor(leftText, 0d);

        Text rightText = new Text("Or... your latent ability?");
        rightText.getStyleClass().add("text");
        AnchorPane.setTopAnchor(rightText, 0d);
        AnchorPane.setRightAnchor(rightText, 0d);

        AnchorPane welcomePane = new AnchorPane();
        welcomePane.getStyleClass().add("welcome-pane");
        welcomePane.getChildren().addAll(leftText, rightText);

        return welcomePane;
    }

    private CreateBox createBox() {
        createBox.setCreatingType(CreatingType.IDEA);
        createBox.setOKButtonText("Let's try out! :D");
        return createBox;
    }
}
