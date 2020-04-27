package com.Desert.Control.Widget;

import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.application.Platform;
import javafx.embed.swing.JFXPanel;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.CheckBox;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Text;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import javax.swing.*;
import java.awt.*;
import java.io.IOException;

enum TaskType {PROJECT_TASK, IDEA_TASK}

public class TaskWidget extends JFrame implements FXUtility {

    private BorderPane borderPane;
    private Text deadlineText;
    private double xOffset, yOffset;

    public TaskWidget() throws IOException {
        setType(Type.UTILITY);
        setUndecorated(true);
        setLocationRelativeTo(null);
        setMinimumSize(new Dimension(400, 328));
        setBackground(new java.awt.Color(0, 0, 0, 0));

        borderPane = new BorderPane();
        borderPane.getStyleClass().add("task-widget");
        borderPane.setTop(titlePane());
        borderPane.setCenter(subtaskBox());
        borderPane.setBottom(deadlinePane());
    }

    private AnchorPane titlePane() throws IOException {
        SVGButton projectButton = new SVGButton(getVector("project"), 18, 20);
        projectButton.setAnchor(3, 2);
        projectButton.setMaxSize(24, 24);

        Text labelText = new Text("Task:");
        labelText.getStyleClass().add("label-text");

        Text titleText = new Text("Task 1");
        titleText.getStyleClass().add("title-text");

        HBox leftBox = new HBox();
        leftBox.getStyleClass().add("left-box");
        leftBox.getChildren().addAll(projectButton, labelText, titleText);
        AnchorPane.setLeftAnchor(leftBox, 0d);
        AnchorPane.setTopAnchor(leftBox, 0d);

        SVGButton closeButton = new SVGButton(getVector("close"), 20, 20);
        closeButton.setAnchor(2, 2);
        closeButton.setMaxSize(24, 24);
        closeButton.setCursor(Cursor.HAND);
        closeButton.setOnMouseClicked(event -> dispose());

        HBox rightBox = new HBox();
        rightBox.getStyleClass().add("right-box");
        rightBox.getChildren().add(closeButton);
        AnchorPane.setTopAnchor(rightBox, 0d);
        AnchorPane.setRightAnchor(rightBox, 0d);

        AnchorPane titlePane = new AnchorPane();
        titlePane.getStyleClass().add("title-pane");
        titlePane.getChildren().addAll(leftBox, rightBox);
        titlePane.setOnMousePressed(event -> {
            xOffset = event.getSceneX();
            yOffset = event.getSceneY();
        });
        titlePane.setOnMouseDragged(event -> {
            setLocation((int) Math.floor(event.getScreenX() - xOffset), (int) Math.floor(event.getScreenY() - yOffset));
            setOpacity(0.7f);
        });
        titlePane.setOnMouseReleased(event -> setOpacity(1));
        return titlePane;
    }

    private VBox subtaskBox() {
        VBox subtaskBox = new VBox();
        subtaskBox.getChildren().add(subtaskItem());
        subtaskBox.getStyleClass().add("subtask-box");
        return subtaskBox;
    }

    private AnchorPane deadlinePane() throws IOException {
        SVGButton launchButton = new SVGButton(getVector("launch"), 18, 18);
        launchButton.setAnchor(3, 3);
        AnchorPane.setTopAnchor(launchButton, 4d);
        AnchorPane.setLeftAnchor(launchButton, 0d);

        deadlineText = new Text("Deadline: 02/02/2020");
        deadlineText.getStyleClass().add("deadline-text");
        AnchorPane.setTopAnchor(deadlineText, 0d);
        AnchorPane.setRightAnchor(deadlineText, 0d);

        AnchorPane deadlinePane = new AnchorPane();
        deadlinePane.getStyleClass().add("deadline-pane");
        deadlinePane.getChildren().addAll(launchButton, deadlineText);
        return deadlinePane;
    }

    private AnchorPane subtaskItem() {
        Text text = new Text("Subtask 1");
        text.getStyleClass().add("text");

        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-box");
        titleBox.getChildren().add(text);
        AnchorPane.setTopAnchor(titleBox, 0d);
        AnchorPane.setLeftAnchor(titleBox, 8d);

        CheckBox checkBox = new CheckBox();
        checkBox.getStyleClass().add("check-box");
        AnchorPane.setTopAnchor(checkBox, 0d);
        AnchorPane.setRightAnchor(checkBox, 0d);

        AnchorPane subtaskItem = new AnchorPane();
        subtaskItem.getStyleClass().add("subtask-widget-card");
        subtaskItem.getChildren().addAll(titleBox, checkBox);
        return subtaskItem;
    }

    public final void setDeadline(String deadline) {
        deadlineText.setText(deadline);
    }

    public final void display() {
        Platform.runLater(() -> {
            ResourceLoader loader = new DefaultResourceLoader();
            Resource styleRsrc = loader.getResource("widget.css");

            Scene scene;
            try {
                scene = new Scene(borderPane, 600, 400);
                scene.getStylesheets().add(styleRsrc.getURI().toString());
                scene.setFill(Color.TRANSPARENT);
            } catch (IOException e) {
                e.printStackTrace();
                return;
            }

            JFXPanel panel = new JFXPanel();
            panel.setScene(scene);
            getContentPane().add(panel);

            SwingUtilities.invokeLater(() -> setVisible(true));
        });
    }
}
