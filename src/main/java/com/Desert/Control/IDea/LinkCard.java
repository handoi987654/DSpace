package com.Desert.Control.IDea;

import com.Desert.Control.Public.SVGButton;
import com.Desert.Utility.FXUtility;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.text.Text;
import org.springframework.core.io.DefaultResourceLoader;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;

import java.io.IOException;

public class LinkCard extends AnchorPane implements FXUtility {

    private static final ResourceLoader loader = new DefaultResourceLoader();

    public LinkCard() throws IOException {
        getStyleClass().add("rsrc-card");
        getChildren().addAll(linkButton(), titleField(), domainText(), metaText(), pictureView());
    }

    private SVGButton linkButton() throws IOException {
        SVGButton linkButton = new SVGButton(getVector("link"), 20, 10);
        linkButton.setAnchor(2, 7);
        AnchorPane.setTopAnchor(linkButton, 0d);
        AnchorPane.setLeftAnchor(linkButton, 0d);
        return linkButton;
    }

    private TextField titleField() {
        TextField titleField = new TextField("Neverware");
        titleField.getStyleClass().add("title-field");
        AnchorPane.setLeftAnchor(titleField, 32d);
        return titleField;
    }

    private Text domainText() {
        Text domainText = new Text("neverware.com");
        domainText.getStyleClass().add("domain-text");
        AnchorPane.setTopAnchor(domainText, 32d);
        AnchorPane.setLeftAnchor(domainText, 0d);
        return domainText;
    }

    private Text metaText() {
        Text metaText = new Text("Whether you’re a business, a school, or a home user, CloudReady OS is the fast, easy way to convert your hardware to the security and manageability of Google's Chrome ecosystem.");
        metaText.getStyleClass().add("meta-text");
        metaText.setWrappingWidth(224);
        AnchorPane.setTopAnchor(metaText, 56d);
        AnchorPane.setLeftAnchor(metaText, 0d);
        AnchorPane.setBottomAnchor(metaText, 40d);
        return metaText;
    }

    private ImageView pictureView() throws IOException {
        Resource imageRsrc = loader.getResource("images/cloudready.jpg");
        Image image = new Image(imageRsrc.getInputStream());
        ImageView pictureView = new ImageView(image);
        pictureView.setPreserveRatio(true);
        pictureView.setSmooth(true);
        pictureView.setFitWidth(232);
        AnchorPane.setTopAnchor(pictureView, 32d);
        AnchorPane.setRightAnchor(pictureView, 0d);
        return pictureView;
    }
}
