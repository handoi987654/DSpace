package com.Desert.Application;

import com.Desert.Component.Project.*;
import com.Desert.Component.Public.SidePane;
import com.Desert.Component.Public.TopPane;
import com.Desert.Entity.Project.Project;
import com.Desert.Utility.FXUtility;
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
public class ProjectApp extends StackPane implements FXUtility {

    @Autowired
    private TopPane topPane;
    @Autowired
    private SidePane sidePane;
    @Autowired
    private RoadMapPane roadmapPane;
    @Autowired
    private ResourcePane resourcePane;
    @Autowired
    private StickyNotePane stickyNotePane;
    @Autowired
    private SettingPane settingPane;
    @Autowired
    private TaskPane taskPane;
    @Autowired
    private IssuePane issuePane;

    private BorderPane mainPane;

    @Value("classpath:images/razer.png")
    private Resource razerRsrc;

    @PostConstruct
    private void render() throws IOException {
        getChildren().addAll(bgView(), mainPane());
        topPane.enableNavigation();
    }

    /*
    Render methods
     */

    private BorderPane mainPane() {
        sidePane.activateProjectNavigation();

        mainPane = new BorderPane();
        mainPane.setTop(topPane);
        mainPane.setLeft(sidePane);
        mainPane.setCenter(roadmapPane);

        return mainPane;
    }

    private ImageView bgView() throws IOException {
        Image bgImage = new Image(razerRsrc.getInputStream());
        return new ImageView(bgImage);
    }

    /*
    Event methods
     */

    public final void openRoadMap() {
        mainPane.setCenter(roadmapPane);
    }

    public final void openResource() {
        mainPane.setCenter(resourcePane);
    }

    public final void openNote() {
        mainPane.setCenter(stickyNotePane);
    }

    public final void openSetting() {
        mainPane.setCenter(settingPane);
    }

    public void loadProject(Project project) throws IOException {
        roadmapPane.load();
        resourcePane.load();
        stickyNotePane.loadProjectNotes();
        settingPane.load();
    }
}
