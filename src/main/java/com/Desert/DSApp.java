package com.Desert;

import com.Desert.Application.HomeApp;
import com.Desert.Application.IDeaApp;
import com.Desert.Application.MusicApp;
import com.Desert.Application.ProjectApp;
import com.Desert.Utility.UserData;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;

@SpringBootApplication
public class DSApp extends Application {

    static {
        System.setProperty("java.awt.headless", "false");
    }

    private ApplicationContext context;

    @Override
    public void init() {
        context = SpringApplication.run(DSApp.class);
    }

    @Override
    public void start(Stage stage) {
        UserData data = (UserData) context.getBean("userData");
        data.setHostServices(getHostServices());

        HomeApp homeApp = (HomeApp) context.getBean("homeApp");
        ProjectApp projectApp = (ProjectApp) context.getBean("projectApp");
        IDeaApp iDeaApp = (IDeaApp) context.getBean("IDeaApp");
        MusicApp musicApp = (MusicApp) context.getBean("musicApp");

        Scene scene = (Scene) context.getBean("scene");
        scene.setRoot(homeApp);
        scene.setUserData(data);

        stage.initStyle(StageStyle.UNDECORATED);
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }
}
