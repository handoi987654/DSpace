package com.Desert.Control.Public;

import com.Desert.Utility.FXUtility;
import com.Desert.Utility.SVGPathGenerator;
import javafx.geometry.Insets;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.SVGPath;
import javafx.scene.text.Text;

import java.io.IOException;

public class RemoveButton extends HBox implements FXUtility {

    public RemoveButton(String textValue) throws IOException {
        SVGPath deletePath = new SVGPath();
        deletePath.setContent(SVGPathGenerator.generate(getVector("delete")));
        deletePath.setFill(Color.WHITE);

        Text text = new Text();
        text.setText(textValue);
        text.getStyleClass().add("text");
        HBox.setMargin(text, new Insets(0, 0, 0, 8));

        getStyleClass().add("remove-button");
        getChildren().addAll(deletePath, text);
    }
}
