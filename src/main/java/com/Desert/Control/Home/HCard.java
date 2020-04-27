package com.Desert.Control.Home;

import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import javafx.event.EventHandler;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.control.MenuItem;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import lombok.Setter;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

public class HCard extends AnchorPane implements FXUtility {

    @Setter
    private EventHandler<MouseEvent> eventHandler;
    private SVGButton optionButton;
    private ContextMenu optionMenu;

    public HCard() {
        setOnMouseClicked(this::clickEvent);
    }

    private SVGButton optionButton() throws IOException {
        optionMenu = new ContextMenu();
        optionMenu.getItems().addAll(
                option(getVector("edit"), 18, "#ffffff", "Rename"),
                option(getVector("image"), 18, "#ffffff", "Change cover picture"),
                option(getVector("delete"), 14, "#db3737", "Delete Project"));

        optionButton = new SVGButton(getVector("edit"), 18, 18);
        optionButton.setAnchor(11, 11);
        optionButton.setOnMouseClicked(this::openOptionMenu);
        AnchorPane.setBottomAnchor(optionButton, 0d);
        AnchorPane.setRightAnchor(optionButton, 0d);

        return optionButton;
    }

    private MenuItem option(Resource svgResource, double svgWidth, String color, String title) throws IOException {
        SVGButton button = new SVGButton(svgResource, svgWidth, 18);
        button.setVectorColor(color);
        button.setAnchor((24 - svgWidth) / 2, 3);

        Label label = new Label(title);
        label.setTextFill(Color.WHITE);
        label.setFont(new Font("SF Pro Text Light", 14));
        HBox.setMargin(label, new Insets(0, 0, 0, 16));

        HBox hBox = new HBox();
        hBox.setPrefWidth(200);
        hBox.setPadding(new Insets(8));
        hBox.setAlignment(Pos.CENTER_LEFT);
        hBox.getChildren().addAll(button, label);

        CustomMenuItem menuItem = new CustomMenuItem();
        menuItem.setContent(hBox);

        return menuItem;
    }

    public final void setTitle(String title) {
        Text titleText = new Text(title);
        titleText.getStyleClass().add("title-text");
        AnchorPane.setLeftAnchor(titleText, 16d);
        AnchorPane.setTopAnchor(titleText, 16d);

        Line line = new Line();
        line.setStartX(16);
        line.setStartY(56);
        line.setEndX(200);
        line.setEndY(56);
        line.setStroke(Color.valueOf("#5c7080"));
        line.setStrokeWidth(1);

        getChildren().addAll(titleText, line);
    }

    public final void setImage(String imgName, double imgWidth, double imgHeight) throws IOException {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource imageRsrc = loader.getResource("images/" + imgName);
        Image image = new Image(imageRsrc.getInputStream());

        double ratio = imgHeight / imgWidth;
        int width = (int) Math.floor(image.getHeight() / ratio);
        int height = (int) Math.floor(image.getHeight());
        int x = (int) Math.floor((image.getWidth() - width) / 2);
        WritableImage projectImage = new WritableImage(image.getPixelReader(), x, 0, width, height);

        SVGPath maskPath = new SVGPath();
        maskPath.setContent(SVGPathGenerator.generate(getVector("project-card-mask")));
        maskPath.setStroke(Color.WHITE);

        ImageView view = new ImageView(projectImage);
        view.setSmooth(true);
        view.setPreserveRatio(true);
        view.setFitHeight(imgHeight);
        view.setClip(maskPath);
        AnchorPane.setLeftAnchor(view, 216d);

        getChildren().addAll(view, optionButton());
    }

    public final void addInfo(String title, int value, double topAnchor, boolean highLighted) {
        String styleClass = highLighted ? "high-light" : "info";

        Text titleText = new Text(title);
        titleText.getStyleClass().add(styleClass);
        AnchorPane.setTopAnchor(titleText, topAnchor);
        AnchorPane.setLeftAnchor(titleText, 16d);

        Text valueText = new Text(value + "");
        valueText.setWrappingWidth(92);
        valueText.getStyleClass().addAll(styleClass, "value");
        AnchorPane.setTopAnchor(valueText, topAnchor);
        AnchorPane.setLeftAnchor(valueText, 108d);

        getChildren().addAll(titleText, valueText);
    }

    private void openOptionMenu(MouseEvent event) {
        double offsetX = optionButton.getScene().getWindow().getX();
        double offsetY = optionButton.getScene().getWindow().getY();
        double x = optionButton.localToScene(optionButton.getBoundsInLocal()).getMinX();
        double y = optionButton.localToScene(optionButton.getBoundsInLocal()).getMinY();
        optionMenu.show(this, x + offsetX, y + offsetY);
    }

    private void clickEvent(MouseEvent event) {
        //noinspection SuspiciousMethodCalls
        if (!optionButton.getChildren().contains(event.getTarget())) {
            eventHandler.handle(event);
        }
    }
}
