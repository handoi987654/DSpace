package com.Desert.Component.Home;

import com.Desert.Application.HomeApp;
import com.Desert.Application.MusicApp;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class HomePane extends HBox {

    @Autowired
    private ApplicationContext context;

    @Value("classpath:images/border.png")
    private Resource borderRsrc;

    private AnchorPane controlPane;

    @PostConstruct
    private void render() throws IOException {
        setAlignment(Pos.CENTER);
        getChildren().add(controlPane());
    }

    private AnchorPane controlPane() throws IOException {
        controlPane = new AnchorPane();
        controlPane.getStyleClass().add("control-pane");

        Line projectPaneLine = buttonLine(406.5, 138.5, 451.5, 181.5); // w = 45; h = 43
        Line ideaPaneLine = buttonLine(635.5, 266.5, 713, 266.5); // w = 77.5; h = 0
        Line musicPaneLine = buttonLine(434.5, 409.5, 473.5, 354.5); // w = 39; h = 55

        StackPane projectButton = navigationButton(0, -1, "Let's get works done");
        AnchorPane.setLeftAnchor(projectButton, 0d);
        AnchorPane.setTopAnchor(projectButton, 0d);

        StackPane ideaButton = navigationButton(-1, 0, "I've got some idea");
        AnchorPane.setRightAnchor(ideaButton, 0d);
        AnchorPane.setTopAnchor(ideaButton, 205d);

        StackPane musicButton = navigationButton(0, 0, "Time to relax...");
        AnchorPane.setLeftAnchor(musicButton, 28d);
        AnchorPane.setBottomAnchor(musicButton, 0d);

        projectButton.setOnMouseClicked(event -> gotoProjectList());
        ideaButton.setOnMouseClicked(event -> gotoIDeaList());
        musicButton.setOnMouseClicked(event -> gotoMusic());

        controlPane.getChildren().add(centerCircle());
        controlPane.getChildren().addAll(projectButton, projectPaneLine);
        controlPane.getChildren().addAll(ideaButton, ideaPaneLine);
        controlPane.getChildren().addAll(musicButton, musicPaneLine);
        return controlPane;
    }

    private StackPane centerCircle() {
        /*
        Draw Circle: set CenterX, CenterY & Radius is compulsory
         */
        double radius = 103;
        Circle circle = new Circle();
        circle.setCenterX(controlPane.getPrefWidth() - radius);
        circle.setCenterY(controlPane.getPrefHeight() - radius);
        circle.setRadius(radius);
        circle.setFill(Color.color(0, 0, 0, 0.3));
        circle.setStroke(Color.color(1, 1, 1));
        circle.setStrokeWidth(3);

        /*
        Center Text
         */
        Text text = new Text("DESERT");
        text.setFont(new Font("SF Pro Text Light", 42));
        text.setFill(Color.rgb(255, 255, 255));

        /*
        Create complete Center Circle
         */
        StackPane centerCircle = new StackPane();
        centerCircle.setAlignment(Pos.CENTER);
        centerCircle.getChildren().addAll(circle, text);
        centerCircle.setLayoutX(580);
        centerCircle.setLayoutY(217);
        AnchorPane.setTopAnchor(centerCircle, 156d);
        AnchorPane.setLeftAnchor(centerCircle, 424d);

        return centerCircle;
    }

    private StackPane navigationButton(double scaleX, double scaleY, String title) throws IOException {
        /*
        Set border by Image
        ScaleX == -1: flip image horizontally
        ScaleY == -1: flip image vertically
         */
        Image borderImage = new Image(borderRsrc.getInputStream());
        ImageView borderView = new ImageView(borderImage);
        borderView.setFitWidth(424);
        borderView.setFitHeight(156);
        if (scaleX != 0) borderView.setScaleX(scaleX);
        if (scaleY != 0) borderView.setScaleY(scaleY);

        /*
        Center text for Button
         */
        Text text = new Text(title);
        text.getStyleClass().add("text");

        /*
        Create complete Button
         */
        StackPane stackPane = new StackPane();
        stackPane.getStyleClass().add("nav-button");
        stackPane.getChildren().addAll(borderView, text);

        return stackPane;
    }

    private Line buttonLine(double startX, double startY, double endX, double endY) {
        /*
        Draw white line
         */
        Line line = new Line();
        line.setStartX(startX);
        line.setStartY(startY);
        line.setEndX(endX);
        line.setEndY(endY);
        line.setStroke(Color.rgb(255, 255, 255));
        line.setStrokeWidth(3);

        return line;
    }

    private void gotoProjectList() {
        HomeApp homeApp = (HomeApp) context.getBean("homeApp");
        homeApp.gotoProjectList();
    }

    private void gotoIDeaList() {
        HomeApp homeApp = (HomeApp) context.getBean("homeApp");
        homeApp.gotoIDeaList();
    }

    private void gotoMusic() {
        Scene scene = (Scene) context.getBean("scene");
        MusicApp musicApp = (MusicApp) context.getBean("musicApp");
        scene.setRoot(musicApp);
    }

}
