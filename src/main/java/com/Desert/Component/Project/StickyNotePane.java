package com.Desert.Component.Project;

import com.Desert.Control.Note;
import com.Desert.Control.Public.SVGButton;
import com.Desert.Entity.IDea.IDea;
import com.Desert.Entity.IDea.IDeaNote;
import com.Desert.Entity.Project.Project;
import com.Desert.Entity.Project.ProjectNote;
import com.Desert.Service.IDeaService;
import com.Desert.Service.ProjectService;
import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import com.Desert.Utility.UserData;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class StickyNotePane extends VBox implements FXUtility {

    @Autowired
    private UserData userData;
    @Autowired
    private IDeaService iDeaService;
    @Autowired
    private ProjectService projectService;

    private GridPane noteGrid;

    @PostConstruct
    public void render() throws IOException {
        getStyleClass().add("sticky-note-pane");
        getChildren().add(noteGrid());
    }

    private GridPane noteGrid() throws IOException {
        noteGrid = new GridPane();
        noteGrid.getStyleClass().add("note-grid");
        noteGrid.add(addNote(), 0, 0);

        return noteGrid;
    }

    private AnchorPane addNote() throws IOException {
        SVGPath stickyPath = new SVGPath();
        stickyPath.setContent(SVGPathGenerator.generate(getVector("sticky-note")));
        Region region = new Region();
        region.setShape(stickyPath);
        region.getStyleClass().add("svg-region");

        Text text = new Text("I need to remember");
        text.getStyleClass().add("prompt-text");
        AnchorPane.setLeftAnchor(text, 16d);
        AnchorPane.setTopAnchor(text, 16d);

        AnchorPane addNote = new AnchorPane();
        addNote.getStyleClass().add("add-note");
        addNote.getChildren().addAll(region, text);
        return addNote;
    }

    public final void loadIDeaNotes() throws IOException {
        IDea iDea = userData.getIDea();
        List<IDeaNote> noteList = iDeaService.getNoteList(iDea);
        for (Note note : noteList) {
            StickyNote stickyNote = new StickyNote(note);
            noteGrid.add(stickyNote, 1, 0);
        }
    }

    public final void loadProjectNotes() throws IOException {
        Project project = userData.getProject();
        List<ProjectNote> noteList = projectService.getNoteList(project);
        for (Note note : noteList) {
            StickyNote stickyNote = new StickyNote(note);
            noteGrid.add(stickyNote, 1, 0);
        }
    }
}

class StickyNote extends StackPane implements FXUtility {

    private Note note;

    public StickyNote(Note note) throws IOException {
        this.note = note;

        getStyleClass().add("sticky-note");
        getChildren().addAll(background(), dataPane());
    }

    private Region background() throws IOException {
        SVGPath stickyPath = new SVGPath();
        stickyPath.setContent(SVGPathGenerator.generate(getVector("sticky-note")));
        Region background = new Region();
        background.setShape(stickyPath);
        background.getStyleClass().add("background");
        return background;
    }

    private AnchorPane dataPane() throws IOException {
        TextField titleField = new TextField(note.getTitle());
        titleField.getStyleClass().add("title-field");
        AnchorPane.setTopAnchor(titleField, 16d);
        AnchorPane.setLeftAnchor(titleField, 16d);

        TextArea contentArea = new TextArea();
        contentArea.setText(note.getContent());
        contentArea.getStyleClass().add("description-area");
        AnchorPane.setTopAnchor(contentArea, 48d);
        AnchorPane.setLeftAnchor(contentArea, 8d);

        SVGButton deleteButton = new SVGButton(getVector("delete"), 14, 18);
        deleteButton.setPrefSize(24, 24);
        deleteButton.setAnchor(5, 3);
        deleteButton.setVectorColor("#DB3737");
        AnchorPane.setLeftAnchor(deleteButton, 8d);
        AnchorPane.setBottomAnchor(deleteButton, 8d);

        AnchorPane dataPane = new AnchorPane();
        dataPane.getStyleClass().add("data-pane");
        dataPane.getChildren().addAll(titleField, contentArea, deleteButton);
        return dataPane;
    }
}

