package com.Desert.Component.Home;

import com.Desert.Application.ProjectApp;
import com.Desert.Control.Home.HCard;
import com.Desert.Control.Home.HPane;
import com.Desert.Entity.Project.Project;
import com.Desert.Service.HomeService;
import javafx.scene.Scene;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.List;

@Component
public class ProjectListPane extends HPane {

    @Autowired
    private HomeService homeService;
    @Autowired
    private ApplicationContext context;

    public ProjectListPane() {
        super();
    }

    @PostConstruct
    private void render() throws IOException {
        List<Project> projectList = homeService.getProjects();
        for (Project project : projectList) {
            ProjectCard card = new ProjectCard();
            card.setEventHandler(event -> openProject(project));
            addCard(card, 0, 0);
        }
    }

    @SneakyThrows
    private void openProject(Project project) {
        Scene scene = (Scene) context.getBean("scene");
        ProjectApp projectApp = (ProjectApp) context.getBean("projectApp");
        projectApp.loadProject(project);
        scene.setRoot(projectApp);
    }
}

class ProjectCard extends HCard {

    public ProjectCard() throws IOException {
        getStyleClass().add("project-card");

        setTitle("Project");
        setImage("arch-linux.png", 272, 200);
        addInfo("Overdue", 1, 72, true);
        addInfo("Todo", 2, 104, false);
        addInfo("Doing", 3, 136, false);
        addInfo("Done", 4, 168, false);
    }
}