package com.Desert.Component.Project;

import com.Desert.Control.Project.PPane;
import com.Desert.Control.Public.RemoveButton;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Control.Widget.TaskWidget;
import com.Desert.Entity.Project.ProjectRoad;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import com.Desert.Utility.UserData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Component
public class RoadMapPane extends PPane {

    @Autowired
    private UserData userData;

    public RoadMapPane() {
        super();
    }

    @PostConstruct
    private void render() {
        setAddingBoxText("+ Keep walking");
    }

    public void load() throws IOException {
        List<ProjectRoad> roadList = userData.getProject().getRoadList();
        for (ProjectRoad road : roadList) {
            addTab(new RoadMapTab(road));
        }
    }
}

class RoadMapTab extends AnchorPane implements FXUtility {

    private TitledPane tabContent;
    private ContextMenu tagMenu;
    private SVGButton tagButton;
    private final ProjectRoad road;

    public RoadMapTab(ProjectRoad road) throws IOException {
        this.road = road;

        getStyleClass().add("roadmap-tab");
        getChildren().addAll(tabTitle(), tabContent());
    }

    private AnchorPane tabTitle() throws IOException {
        SVGPath waitingPath = new SVGPath();
        waitingPath.setContent(SVGPathGenerator.generate(getVector("waiting")));
        waitingPath.setFill(Color.valueOf("#48AFF0"));

        Text text = new Text(road.getName());
        text.getStyleClass().add("text");
        HBox.setMargin(text, new Insets(0, 0, 0, 16));

        HBox hBox = new HBox();
        hBox.getStyleClass().add("hbox");
        hBox.getChildren().addAll(waitingPath, text);
        AnchorPane.setRightAnchor(hBox, 48d);
        AnchorPane.setLeftAnchor(hBox, 48d);

        AnchorPane tabTitle = new AnchorPane();
        tabTitle.getStyleClass().add("title-tab");
        tabTitle.getChildren().addAll(hBox, tagButton());
        tabTitle.setOnMouseClicked(event -> tabContent.setExpanded(!tabContent.isExpanded()));
        AnchorPane.setTopAnchor(tabTitle, 0d);

        return tabTitle;
    }

    private TitledPane tabContent() throws IOException {
        TaskListPane todoPane = new TaskListPane("To Do");
        TaskListPane doingPane = new TaskListPane("Doing");
        TaskListPane donePane = new TaskListPane("Done");

        HBox taskBox = new HBox();
        taskBox.getStyleClass().add("task-box");
        taskBox.getChildren().addAll(todoPane, doingPane, donePane);

        VBox contentBox = new VBox();
        contentBox.getStyleClass().add("content-box");
        contentBox.getChildren().addAll(taskBox, removeButton());

        tabContent = new TitledPane();
        tabContent.setContent(contentBox);
        tabContent.setExpanded(true);
        tabContent.getStyleClass().add("content-tab");
        AnchorPane.setTopAnchor(tabContent, 32d);
        return tabContent;
    }

    private SVGButton tagButton() throws IOException {
        tagMenu = new ContextMenu();
        tagMenu.getStyleClass().add("tag-menu");
        tagMenu.getItems().addAll(
                option(getVector("waiting"), 19, 20, "#48aff0", "So far away"),
                option(getVector("on-going"), 16, 20, "#ffc940", "Walking on it"),
                option(getVector("completed"), 24, 18, "#0f9960", "So far away"));

        tagButton = new SVGButton(getVector("tag"), 22, 20);
        tagButton.setAnchor(2, 1);
        tagButton.setCursor(Cursor.DEFAULT);
        tagButton.setOnMouseClicked(event -> openTagMenu());
        AnchorPane.setRightAnchor(tagButton, 16d);
        AnchorPane.setTopAnchor(tagButton, 12d);

        return tagButton;
    }

    private HBox removeButton() throws IOException {
        HBox box = new HBox();
        box.setAlignment(Pos.CENTER);
        box.getChildren().add(new RemoveButton("Remove this Road from my way"));
        return box;
    }

    private MenuItem option(Resource svgResource, double svgWidth, double svgHeight, String color, String title) throws IOException {
        SVGButton button = new SVGButton(svgResource, svgWidth, svgHeight);
        button.setVectorColor(color);
        button.setAnchor((24 - svgWidth) / 2, (24 - svgHeight) / 2);

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

    private void openTagMenu() {
        tabContent.setExpanded(!tabContent.isExpanded());

        double offsetX = tagButton.getScene().getWindow().getX();
        double offsetY = tagButton.getScene().getWindow().getY();
        double x = tagButton.localToScene(tagButton.getBoundsInLocal()).getMinX();
        double y = tagButton.localToScene(tagButton.getBoundsInLocal()).getMinY();
        tagMenu.show(this, x + offsetX - 158, y + offsetY + 36);
    }
}

class TaskListPane extends VBox implements FXUtility {

    public TaskListPane(String title) throws IOException {
        getStyleClass().add("task-list-pane");
        getChildren().addAll(titleText(title), taskBox(), addBox());
    }

    private Text titleText(String title) {
        Text titleText = new Text(title);
        titleText.getStyleClass().add("title-text");
        VBox.setMargin(titleText, new Insets(0, 0, 8, 0));
        return titleText;
    }

    private VBox taskBox() throws IOException {
        VBox tasksBox = new VBox();
        tasksBox.setSpacing(8);
        tasksBox.getChildren().add(new TaskCard());
        return tasksBox;
    }

    private HBox addBox() throws IOException {
        SVGButton addButton = new SVGButton(getVector("add"), 14, 14);
        addButton.setMaxSize(14, 14);

        HBox addBox = new HBox();
        addBox.getChildren().add(addButton);
        addBox.getStyleClass().add("add-box");
        return addBox;
    }
}

class TaskCard extends AnchorPane implements FXUtility {

    public TaskCard() throws IOException {
        getStyleClass().add("task-card");
        getChildren().addAll(titleText("Task 1"), subtaskText(3), deadlineText(Date.from(Instant.now())),
                pinButton(), dragButton());
    }

    private Text titleText(String title) {
        Text titleText = new Text(title);
        titleText.getStyleClass().add("title-text");
        AnchorPane.setTopAnchor(titleText, 16d);
        AnchorPane.setLeftAnchor(titleText, 16d);
        return titleText;
    }

    private SVGButton pinButton() throws IOException {
        SVGButton pinButton = new SVGButton(getVector("pin-to-desktop"), 16, 14);
        AnchorPane.setTopAnchor(pinButton, 20d);
        AnchorPane.setRightAnchor(pinButton, 52d);
        pinButton.setCursor(Cursor.HAND);
        pinButton.setOnMouseClicked(event -> openWidget(pinButton.getScene()));
        return pinButton;
    }

    private SVGButton dragButton() throws IOException {
        SVGButton dragButton = new SVGButton(getVector("drag"), 16, 6);
        AnchorPane.setTopAnchor(dragButton, 25d);
        AnchorPane.setRightAnchor(dragButton, 20d);
        return dragButton;
    }

    private Text deadlineText(Date date) {
        SimpleDateFormat format = new SimpleDateFormat();
        format.applyPattern("dd/MM/yyyy");
        String deadline = format.format(date);

        Text deadlineText = new Text("Deadline: " + deadline);
        deadlineText.getStyleClass().add("deadline-text");
        AnchorPane.setRightAnchor(deadlineText, 16d);
        AnchorPane.setBottomAnchor(deadlineText, 16d);
        return deadlineText;
    }

    private Text subtaskText(int amount) {
        Text subtaskText = new Text(amount + " Subtasks");
        subtaskText.getStyleClass().add("amount-text");
        AnchorPane.setLeftAnchor(subtaskText, 16d);
        AnchorPane.setTopAnchor(subtaskText, 48d);
        return subtaskText;
    }

    /*
    Event methods
     */

    private void openWidget(Scene scene) {
        try {
            TaskWidget taskWidget = new TaskWidget();
            taskWidget.display();
            ((Stage) scene.getWindow()).setIconified(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
