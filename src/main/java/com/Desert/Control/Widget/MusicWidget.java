package com.Desert.Control.Widget;

import com.Desert.Component.Music.CurrentPlaylist;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.geometry.Bounds;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.StackPane;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.swing.*;
import java.awt.*;
import java.io.IOException;

@Component
public class MusicWidget extends JFrame implements FXUtility {

    @Autowired
    private CurrentPlaylist currentPlaylist;

    @Value("classpath:widget.css")
    private Resource styleRsrc;

    private AnchorPane musicWidget;
    private StackPane mainPane;

    private final double coverSize = 320;
    private final Dimension prefDimension = new Dimension((int) coverSize, (int) coverSize);
    private final Dimension maxDimension = new Dimension(792, 416);
    private double xOffset, yOffset;
    private boolean launched = false;

    public MusicWidget() throws HeadlessException {
        setType(Type.UTILITY);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setMinimumSize(prefDimension);
        setBackground(new java.awt.Color(0, 0, 0, 0));
    }

    @PostConstruct
    private void render() throws IOException {
        mainPane = new StackPane();
        mainPane.getChildren().addAll(coverView(), controlPane());
        AnchorPane.setTopAnchor(mainPane, 0d);
        AnchorPane.setRightAnchor(mainPane, 0d);

        musicWidget = new AnchorPane();
        musicWidget.getStyleClass().add("music-widget");
        musicWidget.getChildren().add(mainPane);

        Scene scene = new Scene(musicWidget, coverSize, coverSize);
        scene.getStylesheets().add(styleRsrc.getURI().toString());
        scene.setFill(javafx.scene.paint.Color.TRANSPARENT);

        JFXPanel panel = new JFXPanel();
        panel.setScene(scene);
        getContentPane().add(panel);
    }

    private ImageView coverView() throws IOException {
        ResourceLoader loader = new DefaultResourceLoader();
        Resource resource = loader.getResource("images/maroon-5.jpg");
        Image coverImage = new Image(resource.getInputStream());
        ImageView coverView = new ImageView(coverImage);
        coverView.setPreserveRatio(true);
        coverView.setSmooth(true);
        coverView.setFitWidth(coverSize);
        return coverView;
    }

    private AnchorPane controlPane() throws IOException {
        SVGButton playlistButton = controlButton(getVector("playlist"), 24, 24);
        playlistButton.setOnMouseClicked(event -> togglePlaylist());
        AnchorPane.setTopAnchor(playlistButton, 0d);
        AnchorPane.setLeftAnchor(playlistButton, 0d);

        SVGButton launchButton = controlButton(getVector("launch"), 18, 18);
        AnchorPane.setBottomAnchor(launchButton, 0d);
        AnchorPane.setLeftAnchor(launchButton, 0d);

        SVGButton closeButton = controlButton(getVector("close"), 24, 24);
        closeButton.setOnMouseClicked(event -> close());
        AnchorPane.setTopAnchor(closeButton, 0d);
        AnchorPane.setRightAnchor(closeButton, 0d);

        AnchorPane controlPane = new AnchorPane();
        controlPane.getStyleClass().add("control-pane");
        controlPane.getChildren().addAll(playlistButton, launchButton, closeButton);
        controlPane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        controlPane.setOnMouseDragged(event ->
                setLocation((int) Math.floor(event.getScreenX() - xOffset), (int) Math.floor(event.getScreenY() - yOffset)));
        return controlPane;
    }

    private SVGButton controlButton(Resource svgRsrc, double svgWidth, double svgHeight) throws IOException {
        double size = 40;
        SVGButton controlButton = new SVGButton(svgRsrc, svgWidth, svgHeight);
        controlButton.setAnchor((size - svgWidth) / 2, (size - svgHeight) / 2);
        controlButton.getStyleClass().add("control-button");
        return controlButton;
    }

    private void close() {
        launched = false;
        musicWidget.getChildren().remove(currentPlaylist);
        dispose();
    }

    private void togglePlaylist() {
        Bounds bounds = mainPane.localToScreen(mainPane.getBoundsInLocal());
        int x = (int) Math.floor(bounds.getMinX());
        int y = (int) Math.floor(bounds.getMinY());

        if (musicWidget.getChildren().contains(currentPlaylist)) {
            musicWidget.getChildren().remove(currentPlaylist);
            setSize(prefDimension);
            setLocation(x, y);
        } else {
            musicWidget.getChildren().add(currentPlaylist);
            setSize(maxDimension);
            setLocation(x - 472, y);
        }
    }

    public final void display() {
        if (launched) return;
        Platform.runLater(() -> {
            launched = true;
            SwingUtilities.invokeLater(() -> setVisible(true));
        });
    }
}
