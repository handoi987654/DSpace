package com.Desert.Control.Public;

import com.Desert.Utility.FXUtility;
import javafx.geometry.Insets;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

public class FileItem extends VBox implements FXUtility {

    public FileItem() throws IOException {
        getStyleClass().add("file-card");

        ResourceLoader loader = new DefaultResourceLoader();
        Resource imgRsrc = loader.getResource("classpath:images/girl.png");
        Image image = new Image(imgRsrc.getInputStream());
        ImageView imageView = new ImageView(image);

        Text fileName = new Text("girl.png");
        fileName.getStyleClass().add("file-name");
        VBox.setMargin(fileName, new Insets(8));

        getChildren().addAll(imageView, fileName);
    }
}
