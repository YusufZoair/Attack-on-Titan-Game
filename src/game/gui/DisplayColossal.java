package game.gui;

import game.engine.titans.ColossalTitan;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DisplayColossal extends StackPane {

    private ColossalTitan titan;
    private Tooltip tooltip;

    public DisplayColossal(ColossalTitan titan) {
    	
        this.titan = titan;
        this.setPrefSize(50, 50);

        Rectangle rectangle = new Rectangle(50, 50);
        rectangle.setFill(Color.TRANSPARENT);

        ImageView imageView = new ImageView(new Image("file:Game assets/Titan 4.png"));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setScaleX(-1); // Flip the image horizontally

        this.getChildren().addAll(imageView, rectangle);

        // Create a VBox to hold the label
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER); // Center-align the elements vertically
        vbox.setSpacing(5); // Set spacing between elements
        vbox.setMaxWidth(100); // Set maximum width for the VBox

        // Create label for "Colossal Titan"
        Label label = new Label("Colossal Titan");

        // Create an ImageView for the tooltip image
        ImageView tooltipImageView = new ImageView(new Image("file:Game assets/Titan 4.png"));
        tooltipImageView.setFitWidth(50); // Set the width of the image in the tooltip
        tooltipImageView.setFitHeight(50); // Set the height of the image in the tooltip

        // Create a tooltip with the VBox containing the tooltip image and label
        this.tooltip = new Tooltip();
        tooltip.setGraphic(new VBox(tooltipImageView,label));
        Tooltip.install(this, tooltip);

        // Set initial data for the tooltip
        updateLabels();
    }

    public ColossalTitan getTitan() {
        return titan;
    }

    public void setTitan(ColossalTitan titan) {
        this.titan = titan;
    }
    
    public void updateLabels() {
        if (tooltip != null && titan != null) {
            // Clear the content of the tooltip if it's not null
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER); // Center-align the elements vertically
            vbox.setSpacing(5); // Set spacing between elements
            vbox.setMaxWidth(100); // Set maximum width for the VBox

            // Create new labels with updated Titan information
            Label label = new Label("Colossal Titan");
            Label healthLabel = new Label("Health: " + titan.getCurrentHealth() + " HP");
            Label heightLabel = new Label("Height: " + titan.getHeightInMeters() + " Meters");
            Label positionLabel = new Label("Position From Wall: " + titan.getDistance() + " Pixels");
            Label speedLabel = new Label("Speed: " + titan.getSpeed());

            // Add labels to the VBox
            vbox.getChildren().addAll(label,healthLabel, heightLabel, positionLabel, speedLabel);

            // Create an ImageView for the tooltip image
            ImageView tooltipImageView = new ImageView(new Image("file:Game assets/Titan 4.png"));
            tooltipImageView.setFitWidth(50); // Set the width of the image in the tooltip
            tooltipImageView.setFitHeight(50); // Set the height of the image in the tooltip

            // Update the tooltip with the VBox containing the new labels and the image
            tooltip.setGraphic(new VBox(tooltipImageView, vbox));
        }
    }
}
