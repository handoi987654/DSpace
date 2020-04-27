package com.Desert.Application;

import com.Desert.Component.Home.*;
import com.Desert.Component.Public.SidePane;
import com.Desert.Component.Public.TopPane;
import com.Desert.Utility.FXUtility;
import javafx.scene.Scene;
import javafx.scene.effect.ColorAdjust;
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

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class HomeApp extends StackPane implements FXUtility {

    @Autowired
    private Scene scene;
    @Autowired
    private TopPane topPane;
    @Autowired
    private SidePane sidePane;

    @Autowired
    private HomePane homePane;
    @Autowired
    private ProjectListPane projectListPane;
    @Autowired
    private IDeaListPane ideaListPane;
    @Autowired
    private CreateProjectPane createProjectPane;
    @Autowired
    private CreateIDeaPane createIDeaPane;

    private BorderPane mainPane;

    @Value("classpath:images/background.jpg")
    private Resource backgroundRsrc;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("home");
        getChildren().addAll(bgView(), mainPane());
    }

    /*
    Render methods
     */

    private ImageView bgView() throws IOException {
        Image bgImage = new Image(backgroundRsrc.getInputStream());
        ImageView bgView = new ImageView(bgImage);
        bgView.setPreserveRatio(true);
        bgView.setEffect(new ColorAdjust(0, 0, -0.7, 0.2));
        bgView.setFitWidth(scene.getWidth());

        return bgView;
    }

    private BorderPane mainPane() {
        topPane.disableNavigation();
        topPane.disableBackButton();
        sidePane.disableMiniPlayer();

        mainPane = new BorderPane();
        mainPane.setTop(topPane);
        mainPane.setLeft(sidePane);
        mainPane.setCenter(homePane);
        return mainPane;
    }

    /*
    Event methods
     */

    private void backHome() {
        sidePane.disableMiniPlayer();
        sidePane.disableAddButton();

        mainPane.setCenter(homePane);
    }

    public final void gotoProjectList() {
        sidePane.enableMiniPlayer();
        sidePane.enableAddButton();
        sidePane.setAddButtonEvent(event -> createProject());

        topPane.enableBackButton();
        topPane.setBackButtonEvent(event -> backHome());

        mainPane.setCenter(projectListPane);
    }

    public final void gotoIDeaList() {
        sidePane.enableMiniPlayer();
        sidePane.enableAddButton();
        sidePane.setAddButtonEvent(event -> createIDea());

        topPane.enableBackButton();
        topPane.setBackButtonEvent(event -> backHome());

        mainPane.setCenter(ideaListPane);
    }

    private void createProject() {
        topPane.enableBackButton();
        topPane.setBackButtonEvent(event -> gotoProjectList());

        sidePane.disableAddButton();
        mainPane.setCenter(createProjectPane);
    }

    private void createIDea() {
        topPane.enableBackButton();
        topPane.setBackButtonEvent(event -> gotoIDeaList());
        sidePane.disableAddButton();
        mainPane.setCenter(createIDeaPane);
    }
}
