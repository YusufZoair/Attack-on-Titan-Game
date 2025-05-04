package game.gui;

import game.engine.titans.PureTitan;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

public class DisplayPure extends StackPane {

    private PureTitan titan;
    Tooltip tooltip;

    public DisplayPure(PureTitan titan) {
    	
    	this.titan = titan;
        this.setPrefSize(50, 50);

        Rectangle rectangle = new Rectangle(50, 50);
        rectangle.setFill(Color.TRANSPARENT);

        ImageView imageView = new ImageView(new Image("file:Game assets/Titan1.png"));
        imageView.setFitWidth(100);
        imageView.setFitHeight(100);
        imageView.setScaleX(-1); // Flip the image horizontally

        this.getChildren().addAll(imageView, rectangle);

        // Create a VBox to hold the label and health label
        VBox vbox = new VBox();
        vbox.setAlignment(Pos.CENTER); // Center-align the elements vertically
        vbox.setSpacing(5); // Set spacing between elements
        vbox.setMaxWidth(100); // Set maximum width for the VBox

        // Create labels for "Pure Titan" and "Health"
        Label label = new Label("Pure Titan");
        Label healthLabel = new Label("Health :" + titan.getCurrentHealth() + "HP");
        Label height = new Label("Height: " + titan.getHeightInMeters() + "Meter");
        Label position = new Label("Position From wall :" + titan.getDistance() + "pixcel");
        Label speed = new Label("Speed: " + titan.getSpeed());

        // Create an ImageView for the tooltip image
        ImageView tooltipImageView = new ImageView(new Image("file:Game assets/Titan1.png"));
        tooltipImageView.setFitWidth(50); // Set the width of the image in the tooltip
        tooltipImageView.setFitHeight(50); // Set the height of the image in the 

        vbox.getChildren().addAll(label,healthLabel,height,position,speed);

        // Create a tooltip with the VBox containing the tooltip image, label, and health label
        this.tooltip = new Tooltip();
        tooltip.setGraphic(new VBox( tooltipImageView,vbox));
        Tooltip.install(this, tooltip);
    }

    public PureTitan getTitan() {
        return titan;
    }

    public void setTitan(PureTitan titan) {
        this.titan = titan;
    }
    
    public void updateLabels() {
    	
        if (tooltip != null) {
            // Clear the content of the tooltip if it's not null
            VBox vbox = new VBox();
            vbox.setAlignment(Pos.CENTER); // Center-align the elements vertically
            vbox.setSpacing(5); // Set spacing between elements
            vbox.setMaxWidth(100); // Set maximum width for the VBox

            // Create new labels with updated Titan information
            Label label = new Label("Pure Titan");
            Label healthLabel = new Label("Health: " + titan.getCurrentHealth() + " HP");
            Label heightLabel = new Label("Height: " + titan.getHeightInMeters() + " Meters");
            Label positionLabel = new Label("Position From Wall: " + titan.getDistance() + " Pixels");
            Label speedLabel = new Label("Speed: " + titan.getSpeed());

            // Add labels to the VBox
            vbox.getChildren().addAll(label,healthLabel, heightLabel, positionLabel, speedLabel);

            // Create an ImageView for the tooltip image
            ImageView tooltipImageView = new ImageView(new Image("file:Game assets/Titan1.png"));
            tooltipImageView.setFitWidth(50); // Set the width of the image in the tooltip
            tooltipImageView.setFitHeight(50); // Set the height of the image in the tooltip

            // Update the tooltip with the VBox containing the new labels and the image
            tooltip.setGraphic(new VBox(tooltipImageView, vbox));
        }
    }
    
}
