package com.Desert.Control.Home;

import com.Desert.Utility.FXUtility;
import javafx.geometry.Pos;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public class HPane extends AnchorPane implements FXUtility {

    private GridPane gridPane;

    public HPane() {
        getChildren().addAll(welcomeText(), scrollPane());
    }

    private Text welcomeText() {
        Text welcomeText = new Text();
        welcomeText.setFont(new Font("SF Pro Text Light", 28));
        welcomeText.setFill(Color.WHITE);
        double textWidth = welcomeText.getLayoutBounds().getWidth();
        double leftAnchor = (1920 - textWidth) / 2 - 64;
        AnchorPane.setTopAnchor(welcomeText, 32d);
        AnchorPane.setLeftAnchor(welcomeText, leftAnchor);

        return welcomeText;
    }

    private ScrollPane scrollPane() {
//        TODO: remember how to style background color of ScrollPane
        gridPane = new GridPane();
        gridPane.setHgap(216);
        gridPane.setVgap(64);

        VBox box = new VBox();
        box.setAlignment(Pos.TOP_CENTER);
        box.getChildren().add(gridPane);

        double prefWidth = 1200;
        ScrollPane scrollPane = new ScrollPane();
        scrollPane.getStyleClass().add("scroll-pane");
        scrollPane.setContent(box);
        scrollPane.setPrefWidth(prefWidth);
        scrollPane.setPrefHeight(1080 - 200);
        AnchorPane.setTopAnchor(scrollPane, 136d);
        AnchorPane.setLeftAnchor(scrollPane, (1920 - prefWidth) / 2 - 64);

        return scrollPane;
    }

    public final void addCard(HCard card, int col, int row) {
        gridPane.add(card, col, row);
    }
}
