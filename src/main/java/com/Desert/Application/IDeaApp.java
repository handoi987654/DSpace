package com.Desert.Application;

import com.Desert.Component.IDea.PreparationPane;
import com.Desert.Component.IDea.QuestionPane;
import com.Desert.Component.IDea.SettingPane;
import com.Desert.Component.IDea.TransferPane;
import com.Desert.Component.Project.StickyNotePane;
import com.Desert.Component.Public.SidePane;
import com.Desert.Component.Public.TopPane;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.UserData;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class IDeaApp extends StackPane implements FXUtility {

    @Autowired
    private UserData userData;

    @Autowired
    private SidePane sidePane;
    @Autowired
    private TopPane topPane;
    @Autowired
    private PreparationPane preparationPane;
    @Autowired
    private StickyNotePane stickyNotePane;
    @Autowired
    private QuestionPane questionPane;
    @Autowired
    private SettingPane settingPane;
    @Autowired
    private TransferPane transferPane;

    private BorderPane mainPane;

    @Value("classpath:images/ghost-rider.png")
    private Resource bgRsrc;

    @PostConstruct
    private void render() throws IOException {
        getChildren().addAll(bgView(), mainPane());
        topPane.enableNavigation();
    }

    private BorderPane mainPane() {
        sidePane.activateIDeaNavigation();

        mainPane = new BorderPane();
        mainPane.setTop(topPane);
        mainPane.setLeft(sidePane);
        mainPane.setCenter(preparationPane);

        return mainPane;
    }

    private ImageView bgView() throws IOException {
        Image bgImage = new Image(bgRsrc.getInputStream());
        return new ImageView(bgImage);
    }

    /*
    Event methods
     */

    public final void openPreparation() {
        mainPane.setCenter(preparationPane);
    }

    public final void openNote() {
        mainPane.setCenter(stickyNotePane);
    }

    public final void openQuestion() {
        mainPane.setCenter(questionPane);
    }

    public final void openSetting() {
        mainPane.setCenter(settingPane);
    }

    public final void openTransferPane() {
        getChildren().add(transferPane);
    }

    public final void cancelTransferring() {
        getChildren().remove(transferPane);
    }

    public final void load() throws IOException {
        preparationPane.loadTask();
        stickyNotePane.loadIDeaNotes();
//        questionPane.loadQuestion(iDea.getQuestionList());
        settingPane.load();
    }

}
