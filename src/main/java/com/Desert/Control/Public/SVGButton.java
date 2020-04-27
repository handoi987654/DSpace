package com.Desert.Control.Public;

import com.Desert.Utility.SVGPathGenerator;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.shape.SVGPath;
import org.springframework.core.io.Resource;

import java.io.IOException;

public class SVGButton extends AnchorPane {

    private final Region region;
    private double top, right, bottom, left;

    public SVGButton(Resource svgResource, double svgWidth, double svgHeight) throws IOException {
        /*
        Create SVGPath from svgResource
         */
        String svgContent = SVGPathGenerator.generate(svgResource);
        SVGPath svgPath = new SVGPath();
        svgPath.setContent(svgContent);

        /*
        Make Region for SVG
         */
        region = new Region();
        region.setShape(svgPath);
        region.setPrefSize(svgWidth, svgHeight);
        region.setMinSize(svgWidth, svgHeight);
        region.setMaxSize(svgWidth, svgHeight);
        region.setStyle("-fx-background-color: white");

        getChildren().add(region);
    }

    public final void setAnchor(double top, double right, double bottom, double left) {
        this.top = top;
        this.right = right;
        this.bottom = bottom;
        this.left = left;
        setPrefSize();
    }

    public final void setAnchor(double x, double y) {
        this.top = y;
        this.right = x;
        this.bottom = y;
        this.left = x;
        setPrefSize();
    }

    public final void setHoverColor(String color) {
        setStyle("-fx-background-radius: 3");
        setOnMouseEntered(event -> setStyle("-fx-background-color: " + color));
        setOnMouseExited(event -> setStyle("-fx-background-color: transparent"));
    }

    public final void setVectorColor(String color) {
        region.setStyle("-fx-background-color: " + color);
    }

    private void setPrefSize() {
        /*
        Use anchor as padding
         */
        AnchorPane.setTopAnchor(region, top);
        AnchorPane.setRightAnchor(region, right);
        AnchorPane.setBottomAnchor(region, bottom);
        AnchorPane.setLeftAnchor(region, left);

        /*
        Set prefWidth & prefHeight by content
         */
        setPrefWidth(region.getPrefWidth() + left + right);
        setPrefHeight(region.getPrefHeight() + top + bottom);
    }
}
