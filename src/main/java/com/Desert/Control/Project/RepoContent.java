package com.Desert.Control.Project;

import com.Desert.Control.Public.RemoveButton;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.UserData;
import javafx.animation.TranslateTransition;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.text.Font;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.util.Duration;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

public class RepoContent extends AnchorPane implements FXUtility {

    /* Code Tab */
    private HBox tabBar;
    private TabPane tabPane;
    private int currentIndex = 0;

    /* Option Tab */
    private TranslateTransition transition;
    private AnchorPane toggle;
    private Label statusLabel;

    public RepoContent() throws IOException {
        getStyleClass().add("repo-content");
        getChildren().addAll(repoDetail(), versionBox(), repoTab(), removeButton());
    }

    private AnchorPane repoDetail() throws IOException {
        /* Logo */
        ResourceLoader loader = new DefaultResourceLoader();
        Resource logoRsrc = loader.getResource("classpath:images/java-logo.png");
        Image logoImage = new Image(logoRsrc.getInputStream());
        ImageView logoView = new ImageView(logoImage);
        AnchorPane.setLeftAnchor(logoView, 0d);
        AnchorPane.setTopAnchor(logoView, 8d);

        /* Repo name */
        TextField repoNameField = new TextField();
        repoNameField.setText("Spring Security with JWT");
        repoNameField.setEditable(false);
        repoNameField.getStyleClass().add("repo-name");
        AnchorPane.setTopAnchor(repoNameField, 0d);
        AnchorPane.setLeftAnchor(repoNameField, 100d);

        /* Edit button */
        SVGButton editButton = new SVGButton(getVector("edit"), 18, 18);
        editButton.setAnchor(3, 3);
        AnchorPane.setTopAnchor(editButton, 8d);
        AnchorPane.setRightAnchor(editButton, 8d);

        /* Language Text */
        Text languageText = new Text("Programming Language: Java");
        languageText.getStyleClass().add("language-text");
        AnchorPane.setBottomAnchor(languageText, 0d);
        AnchorPane.setLeftAnchor(languageText, 116d);

        /* Repo Detail */
        AnchorPane repoDetail = new AnchorPane();
        repoDetail.getStyleClass().add("repo-detail");
        repoDetail.getChildren().addAll(logoView, repoNameField, editButton, languageText, textFlow());
        return repoDetail;
    }

    private ComboBox<String> versionBox() {
        String[] versions = {"1.1.2 (Current)", "1.1.1", "1.1.0"};
        ObservableList<String> versionList = FXCollections.observableArrayList(versions);

        ComboBox<String> versionBox = new ComboBox<>();
        versionBox.setItems(versionList);
        versionBox.setEditable(false);
        versionBox.getSelectionModel().select(0);
        versionBox.getStyleClass().add("version-box");
        AnchorPane.setTopAnchor(versionBox, 144d);
        AnchorPane.setLeftAnchor(versionBox, 0d);

        return versionBox;
    }

    private AnchorPane repoTab() throws IOException {
        /* TabBar */
        tabBar = new HBox();
        tabBar.getStyleClass().add("tab-bar");
        tabBar.getChildren().add(tabButton(getVector("code"), 19.16, 11.19, "Code", "tab-box-focused"));
        tabBar.getChildren().add(tabButton(getVector("bug"), 16, 17.59, "Issue", "tab-box"));
        tabBar.getChildren().add(tabButton(getVector("commit"), 7, 19.02, "Commit", "tab-box"));
        tabBar.getChildren().add(tabButton(getVector("option"), 18, 18, "Options", "tab-box"));
        AnchorPane.setTopAnchor(tabBar, 0d);
        AnchorPane.setLeftAnchor(tabBar, 0d);
        for (Node child : tabBar.getChildren()) {
            child.setOnMouseClicked(event -> changeTab(tabBar.getChildren().indexOf(child)));
        }

        /* TabPane */
        tabPane = new TabPane();
        tabPane.getStyleClass().add("tab-pane");
        tabPane.getTabs().addAll(codeTab(), issueTab(), commitTab(), optionTab());
        tabPane.getSelectionModel().select(0);
        AnchorPane.setTopAnchor(tabPane, 56d);

        /* RepoTab */
        AnchorPane repoTab = new AnchorPane();

        repoTab.getStyleClass().add("repo-tab");
        repoTab.getChildren().addAll(tabBar, tabPane);
        AnchorPane.setTopAnchor(repoTab, 208d);
        AnchorPane.setLeftAnchor(repoTab, 0d);

        return repoTab;
    }

    private HBox removeButton() throws IOException {
        double prefWidth = 256;
        RemoveButton removeButton = new RemoveButton("Remove this Resource");
        removeButton.setPrefWidth(prefWidth);
        AnchorPane.setLeftAnchor(removeButton, (1048 - prefWidth) / 2);
        AnchorPane.setTopAnchor(removeButton, 1000d);

        return removeButton;
    }

    /* Repo Detail */
    private TextFlow textFlow() {
        Text text = new Text("Online Repository:");
        text.getStyleClass().add("repo-text");

        Hyperlink hyperlink = new Hyperlink();
        hyperlink.setText("BitBucket");
        hyperlink.setVisited(false);
        hyperlink.setOnAction(event -> {
            UserData data = (UserData) getScene().getUserData();
            data.getHostServices().showDocument("www.google.com");
        });

        TextFlow textFlow = new TextFlow();
        textFlow.getStyleClass().add("text-flow");
        textFlow.getChildren().addAll(text, hyperlink);
        AnchorPane.setBottomAnchor(textFlow, 24d);
        AnchorPane.setLeftAnchor(textFlow, 116d);
        return textFlow;
    }

    /* Repo Tab */
    private HBox tabButton(Resource svgRsrc, double svgWidth, double svgHeight, String title, String styleClass) throws IOException {
        double size = 24;
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

    private Tab codeTab() throws IOException {
        /* Search Field */
        TextField searchField = new TextField();
        searchField.setPromptText("Search for file or folder...");
        searchField.getStyleClass().add("search-field");
        AnchorPane.setTopAnchor(searchField, 0d);
        AnchorPane.setLeftAnchor(searchField, 0d);

        /* Src View */
        TreeItem<String> someTree = new CodeItem<>("java");
        TreeItem<String> mainTree = new CodeItem<>("main", true);
        mainTree.getChildren().add(someTree);
        TreeItem<String> srcTree = new CodeItem<>("src", true);
        srcTree.getChildren().add(mainTree);

        TreeView<String> srcView = new TreeView<>(srcTree);
        srcView.setPrefSize(304, 632);
        AnchorPane.setTopAnchor(srcView, 48d);

        /* Code Area */
        TextArea codeArea = new TextArea();
        codeArea.getStyleClass().add("code-area");
        codeArea.setText("FUCK\n");
        codeArea.setEditable(false);
        AnchorPane.setTopAnchor(codeArea, 0d);
        AnchorPane.setRightAnchor(codeArea, 0d);

        /* Code Pane */
        AnchorPane codePane = new AnchorPane();
        codePane.setMaxSize(1048, 712);
        codePane.getStyleClass().add("code-tab");
        codePane.getChildren().addAll(searchField, srcView, codeArea);

        /* Code Tab */
        Tab codeTab = new Tab();
        codeTab.setContent(codePane);
        return codeTab;
    }

    private Tab issueTab() throws IOException {
        GridPane gridPane = new GridPane();
        gridPane.getStyleClass().add("issue-tab");
        gridPane.add(new IssueCard(), 0, 0);

        Tab issueTab = new Tab();
        issueTab.setContent(gridPane);
        return issueTab;
    }

    private Tab commitTab() {
        /* Commit Line */
        Line commitLine = new Line();
        commitLine.setStartX(160);
        commitLine.setStartY(8);
        commitLine.setEndX(160);
        commitLine.setEndY(704);
        commitLine.setStroke(Color.valueOf("#8A9BA8"));
        commitLine.setStrokeWidth(1);

        /* Commit Box */
        VBox commitBox = new VBox();
        commitBox.getChildren().add(new CommitPoint());
        commitBox.setSpacing(32);

        ScrollPane scrollPane = new ScrollPane();
        scrollPane.setContent(commitBox);
        AnchorPane.setTopAnchor(scrollPane, 32d);
        AnchorPane.setLeftAnchor(scrollPane, 103d);

        /* Commit Pane */
        AnchorPane commitPane = new AnchorPane();
        commitPane.getStyleClass().add("commit-tab");
        commitPane.getChildren().addAll(commitLine, scrollPane);

        /* Commit Tab */
        Tab commitTab = new Tab();
        commitTab.setContent(commitPane);
        return commitTab;
    }

    private Tab optionTab() {
        /* Title */
        Text titleText = new Text("Repository Setting");
        titleText.setFill(Color.WHITE);
        titleText.setFont(new Font("SF Pro Text Regular", 18));
        AnchorPane.setTopAnchor(titleText, 0d);
        AnchorPane.setLeftAnchor(titleText, 0d);

        Line line = new Line();
        line.setStartX(0);
        line.setStartY(40);
        line.setEndX(504);
        line.setEndY(40);
        line.setStroke(Color.rgb(138, 155, 168, 0.4));
        line.setStrokeWidth(1);

        /* Description Form */
        Label descriptionLabel = new Label("Description:");
        descriptionLabel.getStyleClass().add("description-label");
        AnchorPane.setTopAnchor(descriptionLabel, 64d);
        AnchorPane.setLeftAnchor(descriptionLabel, 0d);

        TextArea descriptionArea = new TextArea();
        descriptionArea.setPromptText("Description of Repository...");
        descriptionArea.getStyleClass().add("description-area");
        AnchorPane.setLeftAnchor(descriptionArea, 120d);
        AnchorPane.setTopAnchor(descriptionArea, 56d);

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("cancel-button");
        AnchorPane.setTopAnchor(cancelButton, 216d);
        AnchorPane.setLeftAnchor(cancelButton, 120d);

        Button saveButton = new Button("Save");
        saveButton.getStyleClass().add("save-button");
        saveButton.setDisable(true);
        AnchorPane.setTopAnchor(saveButton, 216d);
        AnchorPane.setLeftAnchor(saveButton, 320d);

        /* Language */
        Label languageLabel = new Label("Language:");
        languageLabel.getStyleClass().add("option-label");
        AnchorPane.setLeftAnchor(languageLabel, 0d);
        AnchorPane.setTopAnchor(languageLabel, 268d);

        String[] languages = {"Java", "Javascript", "Python"};
        ComboBox<String> languageBox = new ComboBox<>();
        languageBox.setItems(FXCollections.observableArrayList(languages));
        languageBox.getStyleClass().add("language-box");
        languageBox.getSelectionModel().select("Java");
        AnchorPane.setTopAnchor(languageBox, 264d);
        AnchorPane.setLeftAnchor(languageBox, 120d);

        /* Access */
        Label accessLabel = new Label("Access:");
        accessLabel.getStyleClass().add("option-label");
        AnchorPane.setLeftAnchor(accessLabel, 0d);
        AnchorPane.setTopAnchor(accessLabel, 328d);

        Circle toggleCircle = new Circle();
        toggleCircle.setCenterX(8);
        toggleCircle.setCenterY(8);
        toggleCircle.setRadius(6);
        toggleCircle.setFill(Color.valueOf("#30404D"));

        transition = new TranslateTransition(Duration.seconds(0.5), toggleCircle);
        transition.setByX(16);

        toggle = new AnchorPane();
        toggle.getStyleClass().add("private-toggle");
        toggle.getChildren().add(toggleCircle);
        toggle.setOnMouseClicked(event -> toggle());
        AnchorPane.setTopAnchor(toggle, 332d);
        AnchorPane.setLeftAnchor(toggle, 120d);

        statusLabel = new Label("Private");
        statusLabel.getStyleClass().add("private-label");
        AnchorPane.setTopAnchor(statusLabel, 332d);
        AnchorPane.setLeftAnchor(statusLabel, 176d);

        /* Option Pane */
        AnchorPane optionPane = new AnchorPane();
        optionPane.getStyleClass().add("option-tab");
        optionPane.getChildren().addAll(
                titleText, line,
                descriptionLabel, descriptionArea, cancelButton, saveButton,
                languageLabel, languageBox,
                accessLabel, toggle, statusLabel);

        /* Option Tab */
        Tab optionTab = new Tab();
        optionTab.setContent(optionPane);
        return optionTab;
    }

    private void changeTab(int tabIndex) {
        tabBar.getChildren().get(currentIndex).getStyleClass().remove("tab-box-focused");
        tabBar.getChildren().get(currentIndex).getStyleClass().add("tab-box");

        currentIndex = tabIndex;
        tabBar.getChildren().get(currentIndex).getStyleClass().remove("tab-box");
        tabBar.getChildren().get(currentIndex).getStyleClass().add("tab-box-focused");

        tabPane.getSelectionModel().select(tabIndex);
    }

    private void toggle() {
        transition.play();

        if (transition.getByX() < 0) {
            statusLabel.setText("Private");
            statusLabel.getStyleClass().remove("public-label");
            statusLabel.getStyleClass().add("private-label");

            toggle.getStyleClass().remove("public-toggle");
            toggle.getStyleClass().add("private-toggle");
        } else {
            statusLabel.setText("Public");
            statusLabel.getStyleClass().remove("private-label");
            statusLabel.getStyleClass().add("public-label");

            toggle.getStyleClass().remove("private-toggle");
            toggle.getStyleClass().add("public-toggle");
        }

        transition.setByX(-transition.getByX());
    }
}

class CommitPoint extends AnchorPane implements FXUtility {

    public CommitPoint() {
        Label version = new Label("1.1.2");
        version.setTextFill(Color.WHITE);
        AnchorPane.setTopAnchor(version, 0d);
        AnchorPane.setLeftAnchor(version, 0d);

        Circle circle = new Circle();
        circle.setCenterX(56);
        circle.setCenterY(8);
        circle.setRadius(8);
        circle.setFill(Color.valueOf("#202B33"));
        circle.setStroke(Color.valueOf("#8A9BA8"));
        circle.setStrokeWidth(1);

        TextArea message = new TextArea();
        message.textProperty().addListener((observable, oldValue, newValue) -> {
            Text text = new Text(newValue);
            message.setText(newValue);
            message.setPrefHeight(text.getLayoutBounds().getHeight());
        });
        message.setText("Initial Commit");
        message.setEditable(false);
        message.getStyleClass().add("message-area");
        AnchorPane.setTopAnchor(message, -4d);
        AnchorPane.setLeftAnchor(message, 80d);

        getChildren().addAll(version, circle, message);
        getStyleClass().add("commit-point");
    }
}

class IssueCard extends AnchorPane implements FXUtility {

    public IssueCard() throws IOException {
        addTitle();
        addDescription();
        addLabel();
        addButtons();

        getStyleClass().add("issue-card");
    }

    private void addTitle() {
        Text title = new Text("Some Issue");
        title.getStyleClass().add("title-text");
        AnchorPane.setTopAnchor(title, 16d);
        AnchorPane.setLeftAnchor(title, 16d);

        getChildren().add(title);
    }

    private void addDescription() {
        TextArea description = new TextArea();
        description.setText("Typing a description");
        description.setEditable(false);
        description.getStyleClass().add("description-area");
        AnchorPane.setTopAnchor(description, 56d);
        AnchorPane.setLeftAnchor(description, 8d);

        getChildren().add(description);
    }

    private void addLabel() {
        Label label = new Label("Open");
        label.getStyleClass().add("status-label");
        AnchorPane.setTopAnchor(label, 16d);
        AnchorPane.setRightAnchor(label, 16d);

        getChildren().add(label);
    }

    private void addButtons() throws IOException {
        SVGButton pinButton = button(getVector("pin-to-desktop"), 15.59, 13.18);
        AnchorPane.setLeftAnchor(pinButton, 16d);
        AnchorPane.setBottomAnchor(pinButton, 16d);

        SVGButton addTaskButton = button(getVector("add-task"), 19, 19);
        AnchorPane.setLeftAnchor(addTaskButton, 56d);
        AnchorPane.setBottomAnchor(addTaskButton, 16d);

        SVGButton closeButton = button(getVector("close-issue"), 16, 21);
        AnchorPane.setRightAnchor(closeButton, 16d);
        AnchorPane.setBottomAnchor(closeButton, 16d);

        getChildren().addAll(pinButton, addTaskButton, closeButton);
    }

    private SVGButton button(Resource svgRsrc, double svgWidth, double svgHeight) throws IOException {
        SVGButton button = new SVGButton(svgRsrc, svgWidth, svgHeight);
        button.setAnchor((24 - svgWidth) / 2, (24 - svgHeight) / 2);
        return button;
    }
}
