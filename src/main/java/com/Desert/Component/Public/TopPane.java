package com.Desert.Component.Public;

import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.application.Platform;
import javafx.event.EventHandler;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Screen;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.DependsOn;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;

@Component
@DependsOn({"resourceConfig"})
@Scope("prototype")
public class TopPane extends AnchorPane implements FXUtility {

    @Autowired
    private NavigationMenu navigationMenu;

    @Value("classpath:images/logo.png")
    private Resource logoRsrc;

    private AnchorPane logoButton;
    private SVGButton backButton;
    private Text spaceText, titleText;
    private double xOffset, yOffset;

    @PostConstruct
    private void render() throws IOException {
        getStyleClass().add("top-pane");
        getChildren().addAll(logoButton(),
                titleBox(), backButton(), spaceText(),
                closeButton(), minimizeButton());
    }

    /*
    Controls
     */

    private SVGButton closeButton() throws IOException {
        SVGButton closeButton = new SVGButton(getVector("close"), 20, 20);
        closeButton.setAnchor(8, 8);
        closeButton.setHoverColor("#DB3737");
        closeButton.setOnMouseClicked(event -> Platform.exit());
        AnchorPane.setRightAnchor(closeButton, 16d);
        AnchorPane.setTopAnchor(closeButton, 14d);

        return closeButton;
    }

    private SVGButton minimizeButton() throws IOException {
        SVGButton minimizeButton = new SVGButton(getVector("minimize"), 20, 4);
        minimizeButton.setAnchor(24, 8, 8, 8);
        minimizeButton.setHoverColor("#1E80BC");
        minimizeButton.setOnMouseClicked(event -> ((Stage) getScene().getWindow()).setIconified(true));
        AnchorPane.setRightAnchor(minimizeButton, 102d);
        AnchorPane.setTopAnchor(minimizeButton, 14d);

        return minimizeButton;
    }

    private SVGButton backButton() throws IOException {
        backButton = new SVGButton(getVector("back"), 25, 16);
        backButton.setAnchor(19.5, 24);
        backButton.getStyleClass().add("back-button");
        AnchorPane.setTopAnchor(backButton, 0d);
        AnchorPane.setLeftAnchor(backButton, 128d);
        return backButton;
    }

    private HBox titleBox() {
        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-box");
        titleBox.getChildren().add(titleText());
        AnchorPane.setLeftAnchor(titleBox, 436d);
        /*
        Drag Window events
        Never get Stage except inside an event
         */
        titleBox.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });

        titleBox.setOnMouseDragged(event -> {
            Stage stage = (Stage) getScene().getWindow();
            stage.setOpacity(0.7);
            stage.setX(event.getScreenX() - xOffset);
            stage.setY(event.getScreenY() - yOffset);
        });

        titleBox.setOnMouseReleased(event -> {
            Stage stage = (Stage) getScene().getWindow();
            if (event.getScreenY() == 0.0) {
                double minX = 0;
                double mouseX = event.getScreenX();
                for (Screen screen : Screen.getScreens()) {
                    if (mouseX < screen.getBounds().getMinX()) break;
                    minX = screen.getBounds().getMinX();
                }

                stage.setX(minX);
                stage.setY(0);
            }

            stage.setOpacity(1);
        });

        return titleBox;
    }

    private Text titleText() {
        titleText = new Text();
        titleText.getStyleClass().add("title-text");

        return titleText;
    }

    private Text spaceText() {
        spaceText = new Text();
        spaceText.getStyleClass().add("space-text");
        AnchorPane.setLeftAnchor(spaceText, 128d);
        AnchorPane.setTopAnchor(spaceText, 20d);

        return spaceText;
    }

    private AnchorPane logoButton() throws IOException {
        Image logoImage = new Image(logoRsrc.getInputStream());
        ImageView logoView = new ImageView(logoImage);

        logoButton = new AnchorPane();
        logoButton.setPrefSize(64, 64);
        logoButton.getChildren().add(logoView);
        AnchorPane.setTopAnchor(logoView, 16d);
        AnchorPane.setLeftAnchor(logoView, 16d);
        AnchorPane.setRightAnchor(logoView, 16d);
        AnchorPane.setBottomAnchor(logoView, 16d);
        return logoButton;
    }

    private void showNavigationMenu() {
//        double offsetX = logoButton.getScene().getWindow().getX();
//        double offsetY = logoButton.getScene().getWindow().getY();
//        double x = logoButton.localToScene(logoButton.getBoundsInLocal()).getMinX();
//        double y = logoButton.localToScene(logoButton.getBoundsInLocal()).getMinY();
//
//        System.out.println(x);
//        System.out.println(y);
//        navigationMenu.show(logoButton, x + offsetX, y + offsetY - 16);
//        navigationMenu.show(getScene().getWindow());
        navigationMenu.show();
    }

    /*
    Callable actions
     */

    public final void setTitle(String title) {
        titleText.setText(title);
    }

    public final void setSpaceTitle(String space) {
        spaceText.setText(space);
    }

    public final void enableNavigation() {
        logoButton.setOnMouseClicked(event -> showNavigationMenu());
    }

    public final void disableNavigation() {
        logoButton.setOnMouseClicked(null);
    }

    public final void enableBackButton() {
        if (!getChildren().contains(backButton)) getChildren().add(backButton);
    }

    public final void disableBackButton() {
        getChildren().remove(backButton);
    }

    public final void setBackButtonEvent(EventHandler<MouseEvent> event) {
        if (!getChildren().contains(backButton)) return;
        backButton.setOnMouseClicked(event);
    }

}
