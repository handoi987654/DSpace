package com.Desert.Component.IDea;

import com.Desert.Control.IDea.FolderCard;
import com.Desert.Control.Public.RemoveButton;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Control.Widget.TaskWidget;
import com.Desert.Entity.IDea.IDea;
import com.Desert.Entity.IDea.IDeaResource;
import com.Desert.Entity.IDea.IDeaResourceGroup;
import com.Desert.Entity.IDea.IDeaTask;
import com.Desert.Entity.ResourceEnumType;
import com.Desert.Service.IDeaService;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.UserData;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import lombok.Getter;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class PreparationPane extends HBox implements FXUtility {

    @Autowired
    private IDeaService service;
    @Autowired
    private UserData userData;
    @Autowired
    private ApplicationContext context;

    @Autowired
    private ResourceTab resourceTab;
    @Autowired
    private ResourceList resourceList;

    private IDea iDea;
    private VBox listBox;
    private TextField titleField;

    @PostConstruct
    public void render() throws IOException {
        getStyleClass().add("preparation-pane");
        getChildren().addAll(taskBox(), resourceBox());
    }

    private VBox taskBox() {
        Text titleText = new Text("Task");
        titleText.getStyleClass().add("title-text");

        titleField = new TextField();
        titleField.setPromptText("I need to do something...");
        titleField.getStyleClass().add("add-field");
        titleField.setOnKeyPressed(this::addTask);

        listBox = new VBox();
        listBox.getStyleClass().add("list-box");

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setPrefSize(464, 800);
        scrollPane.setContent(listBox);

        VBox taskBox = new VBox();
        taskBox.getStyleClass().add("task-box");
        taskBox.getChildren().addAll(titleText, titleField, scrollPane);
        return taskBox;
    }

    private VBox resourceBox() {
        Text titleText = new Text("Resource");
        titleText.getStyleClass().add("title-text");

        VBox resourceBox = new VBox();
        resourceBox.getStyleClass().add("resource-box");
        resourceBox.getChildren().addAll(titleText, resourceTab, resourceList);

        return resourceBox;
    }

    public final void loadTask() {
        listBox.getChildren().removeAll(listBox.getChildren());
        this.iDea = userData.getIDea();
        for (IDeaTask task : service.getTaskList(iDea)) {
            TaskItem item = (TaskItem) context.getBean("taskItem", task);
            listBox.getChildren().add(item);
        }
        System.out.println(listBox.getChildren().size());
    }

    @SneakyThrows
    private void addTask(KeyEvent event) {
        if (!event.getCode().equals(KeyCode.ENTER) ||
                titleField.getText().trim().equals("")) return;

        String title = titleField.getText();
        IDeaTask task = new IDeaTask();
        task.setIDea(iDea);
        task.setTitle(title);
        task.setCompleted(false);
        task.setOrder(0);
        task.setChildTasks(Collections.emptyList());
        task.setCreatedDateTime(LocalDateTime.now());
        int taskID = service.insertTask(task);
        task.setID(taskID);

        titleField.setText("");
        TaskItem item = (TaskItem) context.getBean("taskItem", task);
        listBox.getChildren().add(item);
    }

    public final void removeTask(IDeaTask task) {
        listBox.getChildren().removeIf(item -> ((TaskItem) item).getParentTask().equals(task));
    }
}

@Component
@Scope("prototype")
class TaskItem extends AnchorPane implements FXUtility {

    @Autowired
    private IDeaService service;
    @Autowired
    private ApplicationContext context;

    @Getter
    private final IDeaTask parentTask;

    private TitledPane contentPane;
    private SVGButton toggleButton;
    private TextField titleField;
    private VBox subtaskBox;
    private double rotateAngle = -90;
    private double y;

    @SuppressWarnings("SpringJavaInjectionPointsAutowiringInspection")
    public TaskItem(IDeaTask parentTask) {
        this.parentTask = parentTask;
    }

    @PostConstruct
    private void render() throws IOException {
        contentPane = new TitledPane();
        contentPane.setExpanded(true);
        contentPane.setContent(subtaskPane());
        AnchorPane.setTopAnchor(contentPane, 11d);

        getStyleClass().add("preparation-task-item");
        getChildren().addAll(contentPane, titlePane());
    }

    private AnchorPane titlePane() throws IOException {
        TextField titleField = new TextField(parentTask.getTitle());
        titleField.getStyleClass().add("title-field");
        titleField.setOnKeyPressed(this::renameTask);

        HBox leftBox = new HBox();
        leftBox.getChildren().add(titleField);
        leftBox.getStyleClass().add("left-box");
        AnchorPane.setTopAnchor(leftBox, 4d);

        SVGButton pinButton = button(getVector("pin-to-desktop"), 15.59, 13.18);
        pinButton.getStyleClass().add("pin-button");
        pinButton.setOnMouseClicked(event -> openWidget());

        SVGButton dragButton = button(getVector("drag"), 16, 6);
        dragButton.getStyleClass().add("drag-button");
        dragButton.setOnMousePressed(event -> y = event.getY());
        dragButton.setOnMouseDragged(this::drag);

        toggleButton = button(getVector("down-arrow"), 11, 7);
        toggleButton.setHoverColor("rgba(255, 255, 255, 0.2)");
        toggleButton.setOnMouseClicked(event -> toggleContent());

        HBox rightBox = new HBox();
        rightBox.getStyleClass().add("right-box");
        rightBox.getChildren().addAll(pinButton, dragButton, toggleButton);
        AnchorPane.setTopAnchor(rightBox, 8d);
        AnchorPane.setRightAnchor(rightBox, 8d);

        AnchorPane titlePane = new AnchorPane();
        titlePane.getStyleClass().add("title-pane");
        titlePane.getChildren().addAll(leftBox, rightBox);

        return titlePane;
    }

    private AnchorPane subtaskPane() throws IOException {
        Text detailText = new Text("Details");
        detailText.getStyleClass().add("detail-text");

        titleField = new TextField();
        titleField.setPromptText("A detail...");
        titleField.getStyleClass().add("add-field");
        titleField.setOnKeyPressed(this::addSubtask);

        subtaskBox = new VBox();
        subtaskBox.getStyleClass().add("subtask-box");
        subtaskBox.getChildren().addAll(detailText, titleField);
        for (IDeaTask childTask : parentTask.getChildTasks()) {
            subtaskBox.getChildren().add(subtaskItem(childTask));
        }
        AnchorPane.setTopAnchor(subtaskBox, 0d);
        AnchorPane.setLeftAnchor(subtaskBox, 0d);
        AnchorPane.setBottomAnchor(subtaskBox, 64d);

        RemoveButton removeButton = new RemoveButton("I don't need this");
        removeButton.setOnMouseClicked(event -> deleteTask());
        AnchorPane.setBottomAnchor(removeButton, 0d);
        AnchorPane.setLeftAnchor(removeButton, 0d);

        AnchorPane subtaskPane = new AnchorPane();
        subtaskPane.getStyleClass().add("subtask-pane");
        subtaskPane.getChildren().addAll(subtaskBox, removeButton);
        AnchorPane.setTopAnchor(subtaskPane, 120d);

        return subtaskPane;
    }

    @SneakyThrows
    private void addSubtask(KeyEvent event) {
        if (!event.getCode().equals(KeyCode.ENTER)) return;

        IDeaTask task = new IDeaTask();
        task.setParentTask(parentTask);
        task.setTitle(titleField.getText());
        task.setCreatedDateTime(LocalDateTime.now());
        task.setOrder(0);
        task.setCompleted(false);
        task.setID(service.insertTask(task));
        parentTask.getChildTasks().add(task);

        titleField.setText("");
        subtaskBox.getChildren().add(subtaskItem(task));
    }

    private AnchorPane subtaskItem(IDeaTask task) throws IOException {
        SVGButton deleteButton = button(getVector("delete"), 14, 18);
        deleteButton.setVectorColor("#DB3737");
        deleteButton.setMaxSize(24, 24);

        TextField titleField = new TextField(task.getTitle());
        titleField.getStyleClass().add("title-field");

        HBox leftBox = new HBox();
        leftBox.setAlignment(Pos.CENTER_LEFT);
        leftBox.getChildren().addAll(deleteButton, titleField);
        AnchorPane.setTopAnchor(leftBox, 0d);
        AnchorPane.setLeftAnchor(leftBox, 8d);

        SVGButton dragButton = button(getVector("drag"), 16, 6);
        CheckBox checkBox = new CheckBox();
        checkBox.setSelected(task.isCompleted());
        checkBox.setOnAction(event -> confirmTask(task, checkBox));

        HBox rightBox = new HBox();
        rightBox.getStyleClass().add("right-box");
        rightBox.getChildren().addAll(checkBox, dragButton);
        AnchorPane.setTopAnchor(rightBox, 8d);
        AnchorPane.setRightAnchor(rightBox, 8d);

        AnchorPane subtaskItem = new AnchorPane();
        subtaskItem.getStyleClass().add("subtask-item");
        subtaskItem.getChildren().addAll(leftBox, rightBox);

        return subtaskItem;
    }

    private SVGButton button(Resource svgRsrc, double svgWidth, double svgHeight) throws IOException {
        double size = 24;
        SVGButton button = new SVGButton(svgRsrc, svgWidth, svgHeight);
        button.setAnchor((size - svgWidth) / 2, (size - svgHeight) / 2);
        return button;
    }

    private void drag(MouseEvent event) {
        setTranslateY(getTranslateY() + event.getY() - y);
    }

    private void openWidget() {
        try {
            TaskWidget widget = new TaskWidget();
            widget.display();
            ((Stage) getScene().getWindow()).setIconified(true);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void toggleContent() {
        toggleButton.setRotate(rotateAngle);
        rotateAngle = -90 - rotateAngle;
        contentPane.setExpanded(!contentPane.isExpanded());
    }

    private void renameTask(KeyEvent event) {
        if (!event.getCode().equals(KeyCode.ENTER)) return;
        service.updateTask(parentTask);
    }

    private void confirmTask(IDeaTask task, CheckBox box) {
        task.setCompleted(!task.isCompleted());
        service.updateTask(task);
        box.setSelected(!box.isSelected());
    }

    private void deleteTask() {
        service.deleteTask(parentTask);
        PreparationPane pane = (PreparationPane) context.getBean("preparationPane");
        pane.removeTask(parentTask);
    }
}

@Component
class ResourceTab extends AnchorPane implements FXUtility {

    @Autowired
    private IDeaService iDeaService;
    @Autowired
    private UserData data;
    @Autowired
    private ResourceList resourceList;

    private HBox tabBar;
    private TabPane tabPane;
    private int currentIndex = 0;

    @PostConstruct
    public void render() throws IOException {
        getStyleClass().add("resource-tab");
        getChildren().addAll(tabBar(), tabPane());
    }

    private HBox tabBar() throws IOException {
        tabBar = new HBox();
        tabBar.getStyleClass().add("tab-bar");
        tabBar.getChildren().add(tabButton(getVector("link"), 16, 8, "Link", "tab-box-focused"));
        tabBar.getChildren().add(tabButton(getVector("file"), 12.8, 16, "File", "tab-box"));
        tabBar.getChildren().add(tabButton(getVector("folder-collapsed"), 16, 12.8, "Folder", "tab-box"));
        for (Node child : tabBar.getChildren()) {
            child.setOnMouseClicked(event -> changeTab(tabBar.getChildren().indexOf(child)));
        }

        AnchorPane.setTopAnchor(tabBar, 0d);
        AnchorPane.setLeftAnchor(tabBar, 0d);
        return tabBar;
    }

    private TabPane tabPane() throws IOException {
        tabPane = new TabPane();
        tabPane.getStyleClass().add("tab-pane");
        tabPane.getTabs().addAll(linkTab(), fileTab());
        AnchorPane.setTopAnchor(tabPane, 40d);

        return tabPane;
    }

    private HBox tabButton(Resource svgRsrc, double svgWidth, double svgHeight, String title, String styleClass) throws IOException {
        double size = 16;
        SVGButton button = new SVGButton(svgRsrc, svgWidth, svgHeight);
        button.setAnchor((size - svgWidth) / 2, (size - svgHeight) / 2);
        button.setMaxSize(size, size);

        Text text = new Text(title);
        text.getStyleClass().add("text");
        HBox.setMargin(text, new Insets(0, 0, 0, 8));

        HBox hBox = new HBox();
        hBox.getChildren().addAll(button, text);
        hBox.getStyleClass().add(styleClass);

        return hBox;
    }

    private Tab linkTab() throws IOException {
        SVGButton linkButton = new SVGButton(getVector("link"), 20, 10);
        linkButton.setAnchor(2, 7);
        AnchorPane.setTopAnchor(linkButton, 0d);
        AnchorPane.setLeftAnchor(linkButton, 0d);

        TextArea linkArea = new TextArea();
        linkArea.getStyleClass().add("link-area");
        linkArea.setPromptText("Some URL...");
        AnchorPane.setTopAnchor(linkArea, 0d);
        AnchorPane.setLeftAnchor(linkArea, 32d);

        AnchorPane linkPane = new AnchorPane();
        linkPane.getStyleClass().add("link-pane");
        linkPane.getChildren().addAll(linkButton, linkArea);

        Tab linkTab = new Tab();
        linkTab.setContent(linkPane);
        return linkTab;
    }

    private Tab fileTab() throws IOException {
        SVGButton fileButton = new SVGButton(getVector("file"), 16, 20);
        fileButton.setAnchor(4, 2);
        AnchorPane.setTopAnchor(fileButton, 0d);
        AnchorPane.setLeftAnchor(fileButton, 0d);

        VBox browseBox = browseBox();
        browseBox.setOnMouseClicked(event -> addFiles());

        GridPane fileGrid = new GridPane();
        fileGrid.getStyleClass().add("file-grid");
        fileGrid.add(browseBox(), 0, 0);
        AnchorPane.setTopAnchor(fileGrid, 0d);
        AnchorPane.setLeftAnchor(fileGrid, 32d);

        SVGButton okButton = new SVGButton(getVector("check"), 16.78, 12.58);
        okButton.setAnchor(3.61, 5.71);
        okButton.getStyleClass().add("ok-button");
        AnchorPane.setBottomAnchor(okButton, 0d);
        AnchorPane.setRightAnchor(okButton, 0d);

        AnchorPane filePane = new AnchorPane();
        filePane.getStyleClass().add("file-pane");
        filePane.getChildren().addAll(fileButton, fileGrid, okButton);

        Tab fileTab = new Tab();
        fileTab.setContent(filePane);
        return fileTab;
    }

    private VBox browseBox() {
        Text browseText = new Text("Browse...");
        browseText.getStyleClass().add("browse-text");

        VBox browseBox = new VBox();
        browseBox.getStyleClass().add("browse-box");
        browseBox.getChildren().add(browseText);
        return browseBox;
    }

    private void addFiles() {
        FileChooser chooser = new FileChooser();
        List<File> fileList = chooser.showOpenMultipleDialog(getScene().getWindow());
        List<IDeaResource> resourceList = new ArrayList<>();
        for (File file : fileList) {
            String path = file.getAbsolutePath();

            IDeaResource resource = new IDeaResource();
            resource.setType(ResourceEnumType.FILE);
            resource.setAddedDateTime(LocalDateTime.now());
            resource.setRsrcURL(path);
            resourceList.add(resource);
        }

        IDeaResourceGroup group = new IDeaResourceGroup();
        group.setIDea(data.getIDea());
        group.setName("Group Name");
        group.setResourceList(resourceList);
        group.setID(iDeaService.insertResources(group));
    }

    /*
    Event methods
     */

    private void changeTab(int tabIndex) {
        tabBar.getChildren().get(currentIndex).getStyleClass().remove("tab-box-focused");
        tabBar.getChildren().get(currentIndex).getStyleClass().add("tab-box");

        currentIndex = tabIndex;
        tabBar.getChildren().get(currentIndex).getStyleClass().remove("tab-box");
        tabBar.getChildren().get(currentIndex).getStyleClass().add("tab-box-focused");

        tabPane.getSelectionModel().select(tabIndex);
    }

}

@Component
class ResourceList extends VBox implements FXUtility {

    @PostConstruct
    public void render() throws IOException {
        getStyleClass().add("resource-list");

        FolderCard folderCard = new FolderCard();
        folderCard.setPrefHeight(200);
        addButtonsToCard(folderCard);
        getChildren().add(folderCard);
    }

    public final void addResourceGroup(IDeaResourceGroup group) {

    }

    private void addButtonsToCard(AnchorPane card) throws IOException {
        SVGButton deleteButton = new SVGButton(getVector("delete"), 14, 18);
        deleteButton.setVectorColor("#DB3737");
        deleteButton.setAnchor(5, 3);
        AnchorPane.setBottomAnchor(deleteButton, 0d);
        AnchorPane.setLeftAnchor(deleteButton, 0d);

        SVGButton dragButton = new SVGButton(getVector("drag"), 16, 6);
        dragButton.setAnchor(4, 9);
        AnchorPane.setTopAnchor(dragButton, 0d);
        AnchorPane.setRightAnchor(dragButton, 0d);

        card.getChildren().addAll(deleteButton, dragButton);
    }
}
