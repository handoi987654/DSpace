package com.Desert.Component.Project;

import com.Desert.Control.Project.CodeItem;
import com.Desert.Control.Project.PPane;
import com.Desert.Control.Project.RepoContent;
import com.Desert.Control.Public.FileItem;
import com.Desert.Control.Public.RemoveButton;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Entity.Project.ProjectResource;
import com.Desert.Entity.ResourceEnumType;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import com.Desert.Utility.UserData;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class ResourcePane extends PPane implements FXUtility {

    @Autowired
    private UserData userData;

    public ResourcePane() {
        super();
    }

    @PostConstruct
    private void render() {
        setAddingBoxText("+ Something interesting");
    }

    public void load() throws IOException {
        List<ProjectResource> resourceList = userData.getProject().getResourceList();
        for (ProjectResource resource : resourceList) {
            addTab(new ResourceTab(resource));
        }
    }
}

class ResourceTab extends AnchorPane implements FXUtility {

    private TitledPane contentPane;
    private ProjectResource resource;

    public ResourceTab(ProjectResource resource) throws IOException {
        this.resource = resource;

        getStyleClass().add("resource-content");
        if (resource.getType() == ResourceEnumType.REPOSITORY) {
            getChildren().addAll(titleBox(resource.getName(), getVector("code")), contentPane());
            setContent(new RepoContent());
        } else {
            getChildren().addAll(titleBox(resource.getName(), getVector("brush")), contentPane());
            setContent(new DocContent());
        }
    }

    private HBox titleBox(String title, Resource svgRsrc) throws IOException {
        SVGPath codePath = new SVGPath();
        codePath.setContent(SVGPathGenerator.generate(svgRsrc));
        codePath.setFill(Color.WHITE);

        Text text = new Text(title);
        text.getStyleClass().add("text");
        HBox.setMargin(text, new Insets(0, 0, 0, 16));

        HBox titleBox = new HBox();
        titleBox.getStyleClass().add("title-box");
        titleBox.getChildren().addAll(codePath, text);
        titleBox.setOnMouseClicked(event -> contentPane.setExpanded(!contentPane.isExpanded()));

        return titleBox;
    }

    private TitledPane contentPane() {
        contentPane = new TitledPane();
        contentPane.setExpanded(false);
        contentPane.getStyleClass().add("content-pane");
        AnchorPane.setTopAnchor(contentPane, 40d);

        return contentPane;
    }

    private void setContent(Node content) {
        contentPane.setContent(content);
    }
}

class DocContent extends AnchorPane implements FXUtility {

    public DocContent() throws IOException {
        getStyleClass().add("document-content");
        getChildren().addAll(new FileExplorer(), removeButton());
    }

    private HBox removeButton() throws IOException {
        double prefWidth = 256;
        RemoveButton removeButton = new RemoveButton("Remove this Resource");
        AnchorPane.setLeftAnchor(removeButton, (1048 - prefWidth) / 2);
        AnchorPane.setTopAnchor(removeButton, 560d);

        return removeButton;
    }
}

class FileExplorer extends SplitPane implements FXUtility {

    public FileExplorer() throws IOException {
        getStyleClass().add("file-explorer");
        setDividerPositions(304d / 1048);

        setupFilePane();
        setupViewPane();
    }

    private void setupFilePane() throws IOException {
        SplitPane explorePane = new SplitPane();

        TextField searchField = new TextField();
        searchField.setPromptText("Search for file or folder...");
        searchField.getStyleClass().add("search-field");
        AnchorPane.setTopAnchor(searchField, 0d);
        AnchorPane.setLeftAnchor(searchField, 0d);

        TreeItem<String> someTree = new CodeItem<>("java");

        TreeItem<String> mainTree = new CodeItem<>("main");
        mainTree.getChildren().add(someTree);

        TreeItem<String> srcTree = new CodeItem<>("src");
        srcTree.getChildren().add(mainTree);

        TreeView<String> srcView = new TreeView<>(srcTree);

        explorePane.getItems().addAll(searchField, srcView);
        explorePane.getStyleClass().add("explore-pane");

        getItems().add(explorePane);
    }

    private void setupViewPane() throws IOException {
        SVGButton addFileButton = button("add-file", 18.26, 21.71);
        AnchorPane.setTopAnchor(addFileButton, 6d);
        AnchorPane.setLeftAnchor(addFileButton, 16d);

        SVGButton addDirButton = button("add-folder", 20, 16);
        AnchorPane.setTopAnchor(addDirButton, 8d);
        AnchorPane.setLeftAnchor(addDirButton, 56d);

        Line line = new Line();
        line.setStartX(96);
        line.setStartY(8);
        line.setEndX(96);
        line.setEndY(32);
        line.setStroke(Color.valueOf("#8A9BA8"));
        line.setStrokeWidth(1);

        SVGButton dirButton = button("folder-expanded", 20, 16);
        AnchorPane.setTopAnchor(dirButton, 8d);
        AnchorPane.setLeftAnchor(dirButton, 112d);

        Text currentDir = new Text("images");
        currentDir.getStyleClass().add("current-dir");
        AnchorPane.setTopAnchor(currentDir, 4d);
        AnchorPane.setLeftAnchor(currentDir, 152d);

        GridPane filePane = new GridPane();
        filePane.getStyleClass().add("file-pane");
        filePane.add(addBox(), 0, 0);
        filePane.add(new FileItem(), 1, 0);
        AnchorPane.setTopAnchor(filePane, 48d);
        AnchorPane.setLeftAnchor(filePane, 16d);

        AnchorPane viewPane = new AnchorPane();
        viewPane.getChildren().addAll(addFileButton, addDirButton, line, dirButton, currentDir, filePane);
        viewPane.getStyleClass().add("view-pane");

        getItems().add(viewPane);
    }

    private SVGButton button(String vectorName, double svgWidth, double svgHeight) throws IOException {
        SVGButton button = new SVGButton(getVector(vectorName), svgWidth, svgHeight);
        button.setAnchor((24 - svgWidth) / 2, (24 - svgHeight) / 2);
        return button;
    }

    private VBox addBox() throws IOException {
        SVGPath upPath = new SVGPath();
        upPath.setContent(SVGPathGenerator.generate(getVector("up-level")));
        upPath.getStyleClass().add("up-path");

        VBox addBox = new VBox();
        addBox.getStyleClass().add("add-box");
        addBox.getChildren().add(upPath);

        return addBox;
    }
}
