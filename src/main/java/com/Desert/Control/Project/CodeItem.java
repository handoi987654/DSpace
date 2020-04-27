package com.Desert.Control.Project;

import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import javafx.event.EventType;
import javafx.scene.control.TreeItem;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;

import java.io.IOException;

public class CodeItem<T> extends TreeItem<T> implements FXUtility {

    private SVGPath expandedPath;
    private SVGPath collapsedPath;
    private SVGPath filePath;

    public CodeItem(T value) throws IOException {
        super(value);
        createSVGPath();
        generateGraphic();

        addEventHandler(EventType.ROOT, event -> generateGraphic());
    }

    public CodeItem(T value, boolean isExpanded) throws IOException {
        super(value);
        setExpanded(isExpanded);
        createSVGPath();

        generateGraphic();
        addEventHandler(EventType.ROOT, event -> generateGraphic());
    }

    private void createSVGPath() throws IOException {
        expandedPath = new SVGPath();
        expandedPath.setContent(SVGPathGenerator.generate(getVector("folder-expanded")));
        expandedPath.setFill(Color.WHITE);

        collapsedPath = new SVGPath();
        collapsedPath.setContent(SVGPathGenerator.generate(getVector("folder-collapsed")));
        collapsedPath.setFill(Color.WHITE);

        filePath = new SVGPath();
        filePath.setContent(SVGPathGenerator.generate(getVector("file")));
        filePath.setFill(Color.WHITE);
    }

    private void generateGraphic() {
        if (getChildren().size() == 0) {
            setGraphic(filePath);
            return;
        }

        if (isExpanded()) setGraphic(expandedPath);
        else setGraphic(collapsedPath);
    }
}
