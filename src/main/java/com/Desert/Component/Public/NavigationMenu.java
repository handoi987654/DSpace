package com.Desert.Component.Public;

import com.Desert.Application.HomeApp;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.geometry.Insets;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
public class NavigationMenu extends AnchorPane implements FXUtility {

    @Autowired
    private ApplicationContext context;
    @Autowired
    private Scene scene;

    @Value("classpath:images/logo.png")
    private Resource logoRsrc;

    @PostConstruct
    private void render() throws IOException {
        Image logoImage = new Image(logoRsrc.getInputStream());
        ImageView logoView = new ImageView(logoImage);
        AnchorPane.setTopAnchor(logoView, 16d);
        AnchorPane.setLeftAnchor(logoView, 16d);

        AnchorPane logoPane = new AnchorPane();
        logoPane.setMinSize(64, 64);
        logoPane.getChildren().add(logoView);

        Text text = new Text("Home");
        text.getStyleClass().add("text");

        HBox itemBox = new HBox();
        itemBox.getStyleClass().add("item-box");
        itemBox.getChildren().addAll(logoPane, text);
        AnchorPane.setLeftAnchor(itemBox, 0d);
        AnchorPane.setTopAnchor(itemBox, 0d);
        itemBox.setOnMouseClicked(event -> {
            HomeApp app = (HomeApp) context.getBean("homeApp");
            scene.setRoot(app);
        });

        Line line = new Line();
        line.setStartX(8);
        line.setStartY(64);
        line.setEndX(248);
        line.setEndY(64);
        line.setStroke(Color.valueOf("#5c7080"));
        line.setStrokeWidth(2);
        VBox.setMargin(line, new Insets(0, 0, 0, 8));

        VBox box = new VBox();
        box.getStyleClass().add("navigation-menu");
        box.getChildren().addAll(itemBox, line,
                option(getVector("project"), "Project", "projectApp"),
                option(getVector("idea"), "IDea", "IDeaApp"),
                option(getVector("report"), "Report", ""));

        getChildren().add(box);
        setOnMouseClicked(this::checkHide);
    }

    private HBox option(Resource svgResource, String title, String appName) throws IOException {
        SVGButton projectButton = new SVGButton(svgResource, 24, 24);
        projectButton.setAnchor(20, 20);

        Text text = new Text(title);
        text.getStyleClass().add("text");

        HBox menuOption = new HBox();
        menuOption.getStyleClass().add("menu-option");
        menuOption.setOnMouseClicked(event -> navigate(appName));
        menuOption.getChildren().addAll(projectButton, text);
        return menuOption;
    }

    private void navigate(String appName) {
        ((StackPane) getParent()).getChildren().remove(this);
        Parent parent = (Parent) context.getBean(appName);
        scene.setRoot(parent);
    }

    private void checkHide(MouseEvent event) {
        if (event.getTarget().equals(this)) {
            StackPane stackPane = (StackPane) getParent();
            stackPane.getChildren().remove(this);
        }
    }

    public final void show() {
        StackPane pane = (StackPane) scene.getRoot();
        pane.getChildren().add(this);
    }
}
