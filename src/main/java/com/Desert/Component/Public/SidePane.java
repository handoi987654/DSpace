package com.Desert.Component.Public;

import com.Desert.Application.IDeaApp;
import com.Desert.Application.ProjectApp;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
@DependsOn({"resourceConfig"})
@Scope("prototype")
public class SidePane extends AnchorPane implements FXUtility {

    @Autowired
    private MiniPlayer miniPlayer;
    @Autowired
    private ApplicationContext context;

    private VBox vBox;
    private VBox player;

    private SVGButton roadMapButton, resourceButton, noteButton, settingButton;
    private SVGButton preparationButton, questionButton;
    private SVGButton addButton;

    private boolean addButtonActivated;

    @PostConstruct
    private void render() throws IOException {
        roadMapButton = navigationButton(getVector("road-map"), 27, 24);
        resourceButton = navigationButton(getVector("resource"), 32, 28);
        noteButton = navigationButton(getVector("note"), 23, 28);
        settingButton = navigationButton(getVector("setting"), 30, 30);

        preparationButton = navigationButton(getVector("preparation"), 27, 24);
        questionButton = navigationButton(getVector("question"), 23, 28);

        addButton = navigationButton(getVector("add"), 22, 22);

        getStyleClass().add("side-pane");
        getChildren().addAll(sideBox(), miniPlayer());
    }

    private VBox sideBox() {
        vBox = new VBox();
        vBox.setPadding(new Insets(16, 0, 0, 0));
        vBox.setSpacing(16);
        vBox.setPrefWidth(64);
        AnchorPane.setTopAnchor(vBox, 0d);

        return vBox;
    }

    private VBox miniPlayer() throws IOException {
        SVGButton miniPlayButton = new SVGButton(getVector("play"), 27, 32);
        miniPlayButton.getStyleClass().add("play-button");
        miniPlayButton.setAnchor(16, 16);

        SVGButton miniMusicButton = new SVGButton(getVector("music"), 32, 32);
        miniMusicButton.getStyleClass().add("music-button");
        miniMusicButton.setAnchor(16, 16);
        miniMusicButton.setOnMouseClicked(event -> miniPlayer.show());

        player = new VBox();
        player.getStyleClass().add("player");
        player.getChildren().addAll(miniPlayButton, miniMusicButton);
        AnchorPane.setBottomAnchor(player, 0d);
        return player;
    }

    private SVGButton navigationButton(Resource svgRsrc, double svgWidth, double svgHeight) throws IOException {
        double size = 64;
        SVGButton button = new SVGButton(svgRsrc, svgWidth, svgHeight);
        button.setAnchor((size - svgWidth) / 2, (size - svgHeight) / 2);
        button.getStyleClass().add("nav-button");
        return button;
    }

    public final void activateProjectNavigation() {
        ProjectApp projectApp = (ProjectApp) context.getBean("projectApp");
        roadMapButton.setOnMouseClicked(event -> projectApp.openRoadMap());
        resourceButton.setOnMouseClicked(event -> projectApp.openResource());
        noteButton.setOnMouseClicked(event -> projectApp.openNote());
        settingButton.setOnMouseClicked(event -> projectApp.openSetting());

        vBox.getChildren().removeAll(vBox.getChildren());
        vBox.getChildren().addAll(roadMapButton, resourceButton, noteButton, settingButton);
    }

    public final void activateIDeaNavigation() {
        IDeaApp iDeaApp = (IDeaApp) context.getBean("IDeaApp");
        preparationButton.setOnMouseClicked(event -> iDeaApp.openPreparation());
        noteButton.setOnMouseClicked(event -> iDeaApp.openNote());
        questionButton.setOnMouseClicked(event -> iDeaApp.openQuestion());
        settingButton.setOnMouseClicked(event -> iDeaApp.openSetting());

        vBox.getChildren().removeAll(vBox.getChildren());
        vBox.getChildren().addAll(preparationButton, noteButton, questionButton, settingButton);
    }

    public final void enableAddButton() {
        addButtonActivated = true;
        vBox.getChildren().removeAll(vBox.getChildren());
        vBox.getChildren().add(addButton);
    }

    public final void disableAddButton() {
        vBox.getChildren().remove(addButton);
    }

    public final void enableMiniPlayer() {
        if (!getChildren().contains(player)) getChildren().add(player);
    }

    public final void disableMiniPlayer() {
        getChildren().remove(player);
    }

    public final void setAddButtonEvent(EventHandler<? super MouseEvent> event) {
        if (!addButtonActivated) return;
        addButton.setOnMouseClicked(event);
    }
}
