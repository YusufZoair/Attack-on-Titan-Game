package game.gui;

import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.util.Duration;
import javafx.scene.Node;
import javafx.scene.Scene;
import javafx.scene.image.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.PriorityQueue;
import game.engine.*;
import game.engine.exceptions.InsufficientResourcesException;
import game.engine.exceptions.InvalidLaneException;
import game.engine.lanes.Lane;
import game.engine.titans.*;

public class GUI extends Application {

    private Stage primaryStage;
    private Battle battle;
    private Scene Game;
    private GridPane gridPane;
    private GridPane Info;
    private GridPane LaneInfo;
    private int ShopLane;
    private int laneIndex;
    private List<Integer> size = new ArrayList<>();
    private Button PassButton;

    public void start(Stage primaryStage) throws Exception {

        this.primaryStage = primaryStage;

        primaryStage.setTitle("Titan Souls");
        primaryStage.getIcons().add(new Image("file:Game assets/Icon.png"));
        primaryStage.setMinWidth(1000);
        primaryStage.setMinHeight(600);
        primaryStage.setMaxWidth(1000);
        primaryStage.setMaxHeight(600);

        primaryStage.setScene(StartSCreen());
        primaryStage.show();
    }

    public Scene StartSCreen() {

        VBox main = new VBox();
        main.setAlignment(Pos.CENTER);
        main.setSpacing(0);

        Image wall = new Image("file:Game assets/Brick_Wall 3.jpg");
        Image name = new Image("file:Game assets/Name.png");

        ImageView nameView = new ImageView(name);

        BackgroundImage backgroundImage = new BackgroundImage(
                wall,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(200, 200, false, false, false, false));

        Background background = new Background(backgroundImage);

        ImageView Startv = new ImageView(new Image("file:Game assets/Start.png"));

        main.setBackground(background);
        main.getChildren().add(nameView);

        Button startButton = new Button();
        startButton.setBackground(null);
        startButton.setGraphic(Startv);
        startButton.setOnAction(e -> {

            ChangeScene(this.primaryStage, GameInstructions());

        });

        startButton.setOnMouseEntered(e -> startButton.setStyle("-fx-opacity: 0.7"));
        startButton.setOnMouseExited(e -> startButton.setStyle("-fx-opacity: 1.0"));

        main.getChildren().add(startButton);

        Scene scene = new Scene(main, 1000, 600);

        nameView.fitWidthProperty().bind(main.widthProperty().multiply(0.5));
        nameView.fitHeightProperty().bind(main.heightProperty().multiply(0.5));

        Startv.fitWidthProperty().bind(main.widthProperty().multiply(0.5));
        Startv.fitHeightProperty().bind(main.heightProperty().multiply(0.1));

        return scene;

    }

    public Scene GameInstructions() {

        VBox Main = new VBox();
        Main.setAlignment(Pos.CENTER);
        Main.setPadding(new Insets(20));
        Main.setSpacing(20);
        Main.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        Text Title = new Text("Game Instructions");

        Text GameOverview = new Text("Game Overview: " + "\n" + "\n"
                + "is a tower defense game inspired by the hit anime Attack on Titan." + "\n"
                + "Your objective is to defend the Utopia District Walls from the titan onslaught using anti-titan weapons");

        Text GameplayMechanics = new Text("Gameplay Mechanics:" + "\n" + "\n"
                + " ➣ Defend multiple lanes representing sections of the wall" + "\n"
                + " ➣ Titans approach the walls, and it's your job to stop them." + "\n"
                + " ➣ Each turn, players can purchase and deploy weapons or pass their turn.");

        Text PlayerActions = new Text("Player Actions:" + "\n" + "\n"
                + " ➣ Use the Purchase and Deploy Weapon button to buy and place weapons strategically" + "\n"
                + " ➣ Utilize various anti-titan weapons to defend against approaching titans." + "\n"
                + " ➣ Consider available resources and lane conditions when deploying weapons.");

        Text TitanAttacks = new Text("Titan Attacks:" + "\n" + "\n"
                + " ➣ Titans will attack wall parts, reducing their HP each turn." + "\n"
                + " ➣ Deploy weapons strategically to eliminate titans before they reach the walls.");

        Text GamePhases = new Text("Game Phases:" + "\n" + "\n"
                + " ➣ Progress through Early, Intense, and Grumbling phases, each presenting new challenges." + "\n"
                + " ➣ Adapt strategies as the battle intensifies and new titan types appear.");

        Text WinningLosing = new Text("Winning and Losing:" + "\n" + "\n"
                + " ➣ Survive and defeat as many titans as possible; the game has no winning condition." + "\n"
                + " ➣ The game ends when all starting lanes become lost; final score is based on accumulated resources and score.");

        Text InterfaceButtons = new Text("Interface Buttons:" + "\n" + "\n"
                + " ➣ Purchase and Deploy Weapon button: Buy and deploy weapons into active lanes." + "\n"
                + " ➣ Pass Turn button: Skip your turn without taking actions." + "\n");

        Title.setTextAlignment(TextAlignment.CENTER);
        GameOverview.setTextAlignment(TextAlignment.CENTER);
        GameplayMechanics.setTextAlignment(TextAlignment.CENTER);
        PlayerActions.setTextAlignment(TextAlignment.CENTER);
        TitanAttacks.setTextAlignment(TextAlignment.CENTER);
        GamePhases.setTextAlignment(TextAlignment.CENTER);
        WinningLosing.setTextAlignment(TextAlignment.CENTER);
        InterfaceButtons.setTextAlignment(TextAlignment.CENTER);

        Main.getChildren().addAll(Title, GameOverview, GameplayMechanics, PlayerActions, TitanAttacks, GamePhases,
                WinningLosing, InterfaceButtons);

        Title.setStyle("-fx-font-size: 24px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        String commonStyle = "-fx-font-size: 16px; -fx-fill: #FFFFFF;";
        GameOverview.setStyle(commonStyle);
        GameplayMechanics.setStyle(commonStyle);
        PlayerActions.setStyle(commonStyle);
        TitanAttacks.setStyle(commonStyle);
        GamePhases.setStyle(commonStyle);
        WinningLosing.setStyle(commonStyle);
        InterfaceButtons.setStyle(commonStyle);

        ImageView Nextv = new ImageView(new Image("file:Game assets/Next.png"));
        Button modeButton = new Button();
        modeButton.setBackground(null);
        modeButton.setGraphic(Nextv);
        modeButton.setOnAction(e -> {

            ChangeScene(this.primaryStage, GameMode());

        });

        Nextv.setFitWidth(80);
        Nextv.setFitHeight(70);
        modeButton.setOnMouseEntered(e -> modeButton.setStyle("-fx-opacity: 0.7"));
        modeButton.setOnMouseExited(e -> modeButton.setStyle("-fx-opacity: 1.0"));

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));
        anchorPane.getChildren().addAll(Main, modeButton);

        AnchorPane.setTopAnchor(Main, 10.0);
        AnchorPane.setLeftAnchor(Main, 10.0);
        AnchorPane.setBottomAnchor(Main, 30.0);
        AnchorPane.setRightAnchor(Main, 10.0);

        AnchorPane.setBottomAnchor(modeButton, 10.0);
        AnchorPane.setRightAnchor(modeButton, 10.0);

        ScrollPane scrollPane = new ScrollPane(anchorPane);
        scrollPane.setFitToWidth(true);
        scrollPane.setFitToHeight(true);

        Scene scene = new Scene(scrollPane, 1000, 580);
        return scene;

    }

    public Scene GameMode() {

        ImageView easyView = new ImageView(new Image("file:Game assets/Easy.png"));
        Button easyButton = new Button();
        easyButton.setBackground(null);
        easyButton.setGraphic(easyView);
        easyButton.setPrefSize(easyView.getImage().getWidth(), easyView.getImage().getHeight());
        easyView.setFitWidth(270);
        easyView.setFitHeight(270);
        easyButton.setOnMouseEntered(e -> easyButton.setStyle("-fx-opacity: 0.7"));
        easyButton.setOnMouseExited(e -> easyButton.setStyle("-fx-opacity: 1.0"));
        easyButton.setOnAction(e -> {

            try {

                this.battle = new Battle(1, 0, 400, 3, 250);

            } catch (IOException e1) {

                e1.getMessage();
            }

            ChangeScene(primaryStage, Game());

        });

        ImageView hardView = new ImageView(new Image("file:Game assets/Hard.png"));
        Button hardButton = new Button();
        hardButton.setBackground(null);
        hardButton.setGraphic(hardView);
        hardButton.setPrefSize(hardView.getImage().getWidth(), hardView.getImage().getHeight());
        hardView.setFitWidth(250);
        hardView.setFitHeight(250);
        hardButton.setOnMouseEntered(e -> hardButton.setStyle("-fx-opacity: 0.7"));
        hardButton.setOnMouseExited(e -> hardButton.setStyle("-fx-opacity: 1.0"));
        hardButton.setOnAction(e -> {

            try {

                this.battle = new Battle(1, 0, 400, 5, 125);

            } catch (IOException e1) {

                e1.getMessage();
            }

            ChangeScene(primaryStage, Game());

        });

        Text Title = new Text("Choose Game Mode");
        Title.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-fill: #FFFFFF;");
        Title.setFont(Font.font("Arial", FontWeight.BOLD, 28));

        Text Easy = new Text("Initial Number of Lanes: 3 " + "\n" + "Initial Resources per Lane: 250");
        Text Hard = new Text("Initial Number of Lanes: 5 " + "\n" + "Initial Resources per Lane: 125");
        Easy.setTextAlignment(TextAlignment.CENTER);
        Hard.setTextAlignment(TextAlignment.CENTER);
        Easy.setStyle("-fx-font-size: 16px; -fx-fill: #FFFFFF;");
        Hard.setStyle("-fx-font-size: 16px; -fx-fill: #FFFFFF;");

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().addAll(Title, easyButton, hardButton, Easy, Hard);
        anchorPane.setBackground(new Background(new BackgroundFill(Color.rgb(22, 22, 24), null, null)));

        AnchorPane.setTopAnchor(Title, 60.0);
        AnchorPane.setLeftAnchor(Title, 340.0);

        AnchorPane.setTopAnchor(Easy, 450.0);
        AnchorPane.setLeftAnchor(Easy, 150.0);

        AnchorPane.setTopAnchor(Hard, 450.0);
        AnchorPane.setLeftAnchor(Hard, 620.0);

        AnchorPane.setTopAnchor(easyButton, 35.0);
        AnchorPane.setLeftAnchor(easyButton, 20.0);

        AnchorPane.setTopAnchor(hardButton, 30.0);
        AnchorPane.setRightAnchor(hardButton, 20.0);

        Scene scene = new Scene(anchorPane, 1000, 600);
        return scene;
    }

    public Scene Game() {

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setStyle("-fx-background-color: black;");

        GridPane Info = new GridPane();
        GridPane LaneInfo = new GridPane();
        GridPane gridPane = new GridPane();
        Info.setHgap(0);
        LaneInfo.setHgap(0);

        Image Sand = new Image("file:Game assets/Sand.png"); // Replace "your_image_file_path.jpg" with your image path

        // Create a BackgroundImage object with the loaded image
        BackgroundImage Sandv = new BackgroundImage(
                Sand,
                BackgroundRepeat.REPEAT,
                BackgroundRepeat.REPEAT,
                BackgroundPosition.CENTER,
                new BackgroundSize(100, 100, false, false, false, false));

        Background S = new Background(Sandv);
        gridPane.setBackground(S);

        this.Info = Info;
        this.LaneInfo = LaneInfo;

        updateData();
        CreateLaneinfo();

        LaneInfo.setStyle("-fx-border-color: Black; -fx-border-width: 1px;");

        String cellBorderStyle = "-fx-border-color: White; -fx-border-width: 1px;";

        for (Node node : Info.getChildren()) {
            if (node instanceof Pane) {
                ((Pane) node).setStyle(cellBorderStyle);
            }
        }

        int numRows = battle.getOriginalLanes().size();
        int numCols = 15;
        double gridWidth = 800.0;
        double gridHeight = 450.0;

        double cellWidth = gridWidth / numCols;
        double cellHeight = gridHeight / numRows;

        for (int row = 0; row < numRows; row++) {
            RowConstraints rowConst = new RowConstraints();
            rowConst.setPrefHeight(cellHeight);
            gridPane.getRowConstraints().add(rowConst);
        }

        for (int col = 0; col < numCols; col++) {
            ColumnConstraints colConst = new ColumnConstraints();
            colConst.setPrefWidth(cellWidth);
            gridPane.getColumnConstraints().add(colConst);
        }

        for (int row = 0; row < numRows; row++) {
            for (int col = 0; col < numCols; col++) {
                Pane pane = new Pane();

                // Apply different borders depending on the row
                if (row < numRows - 1) {
                    // Set a 5-pixel thick bottom border for all rows except the last row
                    pane.setStyle("-fx-border-color: black; -fx-border-width: 0 0 0px 0;");
                } else {
                    // No bottom border for the last row
                    pane.setStyle("-fx-border-color: black; -fx-border-width: 0;");
                }

                pane.setPrefSize(cellWidth, cellHeight);

                // Load the appropriate background image
                Image backgroundImage;
                if (col == 0) {
                    // Different background image for the first column
                    backgroundImage = new Image("file:Game assets/Wall.png");
                } else {
                    // Default background image for other columns
                    backgroundImage = new Image("file:Game assets/Lane.png");
                }

                // Create the BackgroundImage object with the loaded image
                BackgroundImage backgroundImg = new BackgroundImage(
                        backgroundImage,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundRepeat.NO_REPEAT,
                        BackgroundPosition.CENTER,
                        new BackgroundSize(100, 100, true, true, true, false));

                // Create the Background object and set it to the pane
                Background background = new Background(backgroundImg);
                pane.setBackground(background);

                // Add the pane to the GridPane
                gridPane.add(pane, col, row);
            }
        }

        anchorPane.getChildren().addAll(gridPane, Info, LaneInfo);
        AnchorPane.setBottomAnchor(gridPane, 40.0);
        AnchorPane.setRightAnchor(gridPane, 20.0);

        AnchorPane.setRightAnchor(Info, 0.0);

        AnchorPane.setLeftAnchor(LaneInfo, 1.0);
        AnchorPane.setBottomAnchor(LaneInfo, 40.0);

        this.gridPane = gridPane;

        Scene scene = new Scene(anchorPane, 1000, 600);
        this.Game = scene;
        return scene;
    }

    public Scene gameOver() {

        AnchorPane root = new AnchorPane();

        ImageView gameover = new ImageView(new Image("file:Game assets/gameover.png"));
        gameover.setFitWidth(990); // Set desired width
        gameover.setFitHeight(550);

        Label finalScoreLabel = new Label("FINAL SCORE " + battle.getScore());
        finalScoreLabel.setStyle("-fx-font-size: 32px; -fx-font-weight: bold; -fx-text-fill: white;");
        // Create the restart button
        ImageView restart = new ImageView(new Image("file:Game assets/restart.png"));
        restart.setFitWidth(250); // Set desired width
        restart.setFitHeight(250);

        Button button = new Button();
        button.setBackground(null);
        button.setGraphic(restart);
        button.setOnMouseEntered(e -> button.setStyle("-fx-opacity: 0.7"));
        button.setOnMouseExited(e -> button.setStyle("-fx-opacity: 1.0"));
        button.setOnAction(e -> {

            ChangeScene(primaryStage, StartSCreen());
        });

        VBox labelsVBox = new VBox(10); // Adjust spacing between labels
        labelsVBox.setAlignment(Pos.BOTTOM_CENTER);
        labelsVBox.getChildren().addAll(finalScoreLabel);
        root.getChildren().addAll(labelsVBox, button, gameover);

        // Set the background color to black
        root.setStyle("-fx-background-color: black;");

        AnchorPane.setTopAnchor(labelsVBox, 250.0);
        AnchorPane.setRightAnchor(labelsVBox, 250.0);

        AnchorPane.setTopAnchor(button, 150.0);
        AnchorPane.setRightAnchor(button, 600.0);

        Scene scene = new Scene(root, 1000, 600);

        FadeTransition fadeOut = new FadeTransition(Duration.seconds(5), gameover);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(event -> root.getChildren().remove(gameover));
        fadeOut.play();

        return scene;
    }

    public Scene weaponShop() {

        GridPane root = new GridPane();
        root.setStyle("-fx-background-color: black;");
        root.setPadding(new Insets(20));
        root.setHgap(10);
        root.setVgap(3);

        Image img1 = new Image("file:Game assets/Piercing Cannon.png");
        Image img2 = new Image("file:Game assets/Sniper Cannon.png");
        Image img3 = new Image("file:Game assets/Volley Spread Cannon.png");
        Image img4 = new Image("file:Game assets/wallTrap.jpg");

        AnchorPane piercingPane = createWeaponPane(img1, "Anti-Titan Shell",
                " ➣ Attacks the closest 5 titans to the wall on the weapon’s lane", "Piercing Cannon", 25, 10, 1);
        AnchorPane sniperPane = createWeaponPane(img2, "Long Range Spear",
                " ➣ Attacks the first closest titan to the wall on the weapon’s lane", "Sniper Cannon", 25, 35, 2);
        AnchorPane volleySpreadPane = createWeaponPane(img3, "Wall Spread Cannon",
                " ➣ Attack all the titans in between the weapon’s MIN RANGE 20 and MAX RANGE 50 on the weapon’s lane.",
                "Volley Spread Cannon", 100, 5, 3);
        AnchorPane wallTrapPane = createWeaponPane(img4, "Proximity Trap",
                " ➣ Attacks only one titan that has already reached the walls (if more than one have already reached only the first one is attacked)",
                "Wall Trap", 75, 100, 4);

        HBox weaponBoxes = new HBox(20, piercingPane, sniperPane, volleySpreadPane, wallTrapPane);
        weaponBoxes.setPadding(new Insets(20));

        AnchorPane selectionButtons = createSelectionButtons();
        root.add(selectionButtons, 0, 1); // Add the selectionButtons to the second row of the root GridPane

        root.add(weaponBoxes, 0, 0);

        Button button = new Button();
        ImageView back = new ImageView(new Image("file:Game assets/Back.png"));
        back.setFitWidth(80);
        back.setFitHeight(70);
        button.setBackground(null);
        button.setGraphic(back);
        button.setPrefSize(20, 20);
        button.setOnMouseEntered(e -> button.setStyle("-fx-opacity: 0.7"));
        button.setOnMouseExited(e -> button.setStyle("-fx-opacity: 1.0"));
        button.setOnMouseClicked(e -> {
            ChangeScene(primaryStage, Game);
        });
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-opacity: 1.0;"));
        AnchorPane anchorPane = new AnchorPane();
        anchorPane.setBackground(new Background(new BackgroundFill(Color.BLACK, null, null)));

        anchorPane.getChildren().add(button);
        AnchorPane.setLeftAnchor(button, 550.0);
        AnchorPane.setTopAnchor(button, 100.0);

        // Adjust the column constraints to allocate more space to the first column
        ColumnConstraints col1 = new ColumnConstraints();
        col1.setPercentWidth(30); // Adjust the width percentage as needed
        ColumnConstraints col2 = new ColumnConstraints();
        col2.setPercentWidth(50); // Adjust the width percentage as needed

        // Add the column constraints to the GridPane
        root.getColumnConstraints().addAll(col1);

        root.add(anchorPane, 1, 1);

        Scene scene = new Scene(root, 1000, 600);
        return scene;
    }

    private AnchorPane createWeaponPane(Image image, String name, String description, String type, int price,
            int damage, int Code) {

        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(150);
        imageView.setFitHeight(150);

        Button button = new Button();
        button.setGraphic(imageView);
        button.setPrefSize(200, 200);
        button.setStyle("-fx-background-color: transparent; -fx-opacity: 0.7;");
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-opacity: 0.7;");
            button.getParent().getChildrenUnmodifiable().stream()
                    .filter(child -> child instanceof Button)
                    .map(child -> (Button) child)
                    .filter(b -> !b.equals(button))
                    .forEach(b -> b.setStyle("-fx-opacity: 1.0;"));
        });
        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: transparent; -fx-opacity: 1.0;"));

        button.setOnAction(e -> {

            try {

                if (ShopLane == 0) {

                    throw new NullPointerException("Please select a lane before purchasing a weapon.");
                }

                this.size.clear();

                for (int i = 0; i < battle.getOriginalLanes().size(); i++) {
                    Lane titanLane = battle.getOriginalLanes().get(i);
                    PriorityQueue<Titan> titansInLane = titanLane.getTitans();

                    this.size.add(i, titanLane.getDangerLevel());
                }

                // this.size = size;

                battle.purchaseWeapon(Code, battle.getOriginalLanes().get(ShopLane - 1));

                if (!battle.isGameOver()) {

                    updateLaneinfo(null);
                    updateData();

                    removeDead();
                    MoveTitans(gridPane);

                    SpawnTitan(gridPane);

                    RemoveLostLane(gridPane);

                    ChangeScene(primaryStage, Game);

                } else {
                    isGameOver();
                }

                Node node = getNodeByRowColumnIndex(ShopLane - 1, 0, LaneInfo);

                if (node instanceof AnchorPane) {

                    AnchorPane specificAnchorPane = (AnchorPane) node;

                    if (specificAnchorPane != null) {
                        // Loop through the children of the AnchorPane
                        ObservableList<Node> children = specificAnchorPane.getChildren();
                        VBox vBox = null;

                        for (Node child : children) {

                            if (child instanceof VBox) {

                                vBox = (VBox) child;
                                break;
                            }
                        }

                        if (vBox != null) {

                            Label W = new Label(type);
                            W.setTextFill(Color.WHITE);
                            vBox.getChildren().add(W);

                        }
                    }

                }

                this.ShopLane = 0;

            } catch (InsufficientResourcesException | InvalidLaneException | NullPointerException e1) {
                // Create a Stage for the error message
                Stage errorStage = new Stage();
                errorStage.initModality(Modality.APPLICATION_MODAL);
                errorStage.setTitle("Error");

                // Create a Label to display the error message
                Label errorMessage = new Label(e1.getMessage());
                errorMessage.setAlignment(Pos.CENTER);
                errorMessage.setWrapText(true);
                errorMessage.setMaxWidth(300);

                // Create a Button to close the error message
                Button closeButton = new Button("Close");
                closeButton.setOnAction(closeEvent -> errorStage.close());

                // Layout for the error message
                VBox layout = new VBox(10);
                layout.getChildren().addAll(errorMessage, closeButton);
                layout.setAlignment(Pos.CENTER);
                layout.setPadding(new Insets(20));

                // Set the scene for the error Stage
                Scene errorScene = new Scene(layout);
                errorStage.setScene(errorScene);

                // Show the error Stage
                errorStage.showAndWait();
            }

        });

        Label nameLabel = new Label("Name: " + name);
        nameLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        nameLabel.setWrapText(true);
        nameLabel.setPrefWidth(200);
        nameLabel.setAlignment(Pos.CENTER);

        Label descriptionLabel = new Label("Description: " + description);
        descriptionLabel.setStyle("-fx-text-fill: white;");
        descriptionLabel.setWrapText(true);
        descriptionLabel.setPrefWidth(200);
        descriptionLabel.setAlignment(Pos.CENTER);

        Label typeLabel = new Label("Weapon Type: " + type);
        typeLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        typeLabel.setWrapText(true);
        typeLabel.setPrefWidth(200);
        typeLabel.setAlignment(Pos.CENTER);

        Label priceLabel = new Label("Price: " + price);
        priceLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        priceLabel.setWrapText(true);
        priceLabel.setPrefWidth(200);
        priceLabel.setAlignment(Pos.CENTER);

        Label damageLabel = new Label("Damage: " + damage);
        damageLabel.setStyle("-fx-text-fill: white; -fx-font-weight: bold;");
        damageLabel.setWrapText(true);
        damageLabel.setPrefWidth(200);
        damageLabel.setAlignment(Pos.CENTER);

        AnchorPane.setTopAnchor(nameLabel, 5.0);
        AnchorPane.setTopAnchor(descriptionLabel, 180.0);
        AnchorPane.setTopAnchor(typeLabel, 250.0);
        AnchorPane.setTopAnchor(priceLabel, 280.0);
        AnchorPane.setTopAnchor(damageLabel, 295.0);

        AnchorPane.setLeftAnchor(descriptionLabel, 10.0);
        AnchorPane.setLeftAnchor(typeLabel, 10.0);
        AnchorPane.setLeftAnchor(priceLabel, 10.0);
        AnchorPane.setLeftAnchor(damageLabel, 10.0);

        AnchorPane pane = new AnchorPane();
        pane.getChildren().addAll(button, nameLabel, descriptionLabel, typeLabel, priceLabel, damageLabel);

        return pane;
    }

    private AnchorPane createSelectionButtons() {
        VBox selectionButtons = new VBox(10);
        selectionButtons.setStyle("-fx-background-color: #333333;");

        for (int i = 0; i < battle.getOriginalLanes().size(); i++) { // getOriginalLanes().size
            String[] arr = { "#ff4500", "#4682b4", "#ffa07a", "#8a2be2", "#66cdaa" };
            selectionButtons.getChildren().add(createSelectionButton("Lane " + (i + 1), arr[i], i));

        }

        selectionButtons.setAlignment(Pos.CENTER);

        AnchorPane.setTopAnchor(selectionButtons, 0.0);
        AnchorPane.setRightAnchor(selectionButtons, 5.0);
        AnchorPane.setBottomAnchor(selectionButtons, 0.0);
        AnchorPane.setLeftAnchor(selectionButtons, 0.0);

        AnchorPane anchorPane = new AnchorPane();
        anchorPane.getChildren().add(selectionButtons);

        return anchorPane;
    }

    private Button createSelectionButton(String text, String color, int i) {

        Button button = new Button(text);
        button.setPrefWidth(150);
        button.setAlignment(Pos.CENTER);
        button.setStyle("-fx-background-color: " + color + "; -fx-text-fill: white; -fx-opacity: 1.0;");
        button.setOnMouseEntered(e -> {
            button.setStyle("-fx-background-color: " + color + "; -fx-opacity: 0.7;");
            button.getParent().getChildrenUnmodifiable().stream()
                    .filter(child -> child instanceof Button)
                    .map(child -> (Button) child)
                    .filter(b -> !b.equals(button))
                    .forEach(b -> b.setStyle("-fx-opacity: 1.0;"));
        });

        button.setOnAction(e -> {

            this.ShopLane = i + 1;

        });

        button.setOnMouseExited(e -> button.setStyle("-fx-background-color: " + color + "; -fx-opacity: 1.0;"));
        return button;
    }

    private void ChangeScene(Stage primaryStage, Scene scene) {
        primaryStage.setScene(scene);
    }

    private void updateData() {

        String cellBorderStyle = "-fx-border-color: White; -fx-border-width: 1px;";

        if (battle.getNumberOfTurns() > 0) {

            Info.getChildren().clear();
            ;
            // Process each node here
        }

        ImageView Passv = new ImageView(new Image("file:Game assets/Pass.png"));
        Button PassButton = new Button();
        this.PassButton = PassButton;
        PassButton.setBackground(null);
        PassButton.setGraphic(Passv);
        PassButton.setOnAction(e -> {

            ;

            size.clear();

            for (int i = 0; i < battle.getOriginalLanes().size(); i++) {

                Lane titanLane = battle.getOriginalLanes().get(i);
                PriorityQueue<Titan> titansInLane = titanLane.getTitans();

                size.add(i, titanLane.getDangerLevel());
            }

            // this.size = size;

            battle.passTurn();

            if (!battle.isGameOver()) {

                updateLaneinfo(null);
                updateData();
                removeDead();
                MoveTitans(gridPane);

                SpawnTitan(gridPane);

                RemoveLostLane(gridPane);

            } else {
                isGameOver();
            }

        });

        Passv.setFitWidth(100);
        Passv.setFitHeight(70);
        PassButton.setOnMouseEntered(e -> PassButton.setStyle("-fx-opacity: 0.7"));
        PassButton.setOnMouseExited(e -> PassButton.setStyle("-fx-opacity: 1.0"));

        ImageView Buyv = new ImageView(new Image("file:Game assets/Buy.png"));
        Button BuyButton = new Button();
        BuyButton.setBackground(null);
        BuyButton.setGraphic(Buyv);
        BuyButton.setOnAction(e -> {

            ChangeScene(primaryStage, weaponShop());

        });

        Buyv.setFitWidth(100);
        Buyv.setFitHeight(70);
        BuyButton.setOnMouseEntered(e -> BuyButton.setStyle("-fx-opacity: 0.7"));
        BuyButton.setOnMouseExited(e -> BuyButton.setStyle("-fx-opacity: 1.0"));

        Label scoreText = new Label("Score: " + battle.getScore());
        Label turnText = new Label("Turn: " + battle.getNumberOfTurns());
        Label phaseText = new Label("Phase: " + battle.getBattlePhase());
        Label resourcesText = new Label("Resources: " + battle.getResourcesGathered());

        scoreText.setTextAlignment(TextAlignment.CENTER);
        turnText.setTextAlignment(TextAlignment.CENTER);
        phaseText.setTextAlignment(TextAlignment.CENTER);
        resourcesText.setTextAlignment(TextAlignment.CENTER);

        String fontFamily = "Times New Roman";
        scoreText.setFont(Font.font(fontFamily, FontWeight.BOLD, 18));
        turnText.setFont(Font.font(fontFamily, FontWeight.BOLD, 18));
        phaseText.setFont(Font.font(fontFamily, FontWeight.BOLD, 18));
        resourcesText.setFont(Font.font(fontFamily, FontWeight.BOLD, 18));

        Info.setStyle("-fx-background-color: #192734;");

        scoreText.setTextFill(Color.WHITE);
        turnText.setTextFill(Color.WHITE);
        phaseText.setTextFill(Color.WHITE);
        resourcesText.setTextFill(Color.WHITE);

        AnchorPane scorePane = new AnchorPane(scoreText);
        AnchorPane turnPane = new AnchorPane(turnText);
        AnchorPane phasePane = new AnchorPane(phaseText);
        AnchorPane resourcesPane = new AnchorPane(resourcesText);
        AnchorPane buy = new AnchorPane(PassButton);
        AnchorPane pass = new AnchorPane(BuyButton);

        AnchorPane.setTopAnchor(scoreText, 0.0);
        AnchorPane.setLeftAnchor(scoreText, 65.0);
        AnchorPane.setBottomAnchor(scoreText, 0.0);
        AnchorPane.setRightAnchor(scoreText, 0.0);

        AnchorPane.setTopAnchor(turnText, 0.0);
        AnchorPane.setLeftAnchor(turnText, 60.0);
        AnchorPane.setBottomAnchor(turnText, 0.0);
        AnchorPane.setRightAnchor(turnText, 0.0);

        AnchorPane.setTopAnchor(phaseText, 0.0);
        AnchorPane.setLeftAnchor(phaseText, 25.0);
        AnchorPane.setBottomAnchor(phaseText, 0.0);
        AnchorPane.setRightAnchor(phaseText, 0.0);

        AnchorPane.setTopAnchor(resourcesText, 0.0);
        AnchorPane.setLeftAnchor(resourcesText, 25.0);
        AnchorPane.setBottomAnchor(resourcesText, 0.0);
        AnchorPane.setRightAnchor(resourcesText, 0.0);

        AnchorPane.setTopAnchor(PassButton, 0.0);
        AnchorPane.setLeftAnchor(PassButton, 0.0);
        AnchorPane.setBottomAnchor(PassButton, 0.0);
        AnchorPane.setRightAnchor(PassButton, 0.0);

        AnchorPane.setTopAnchor(BuyButton, 0.0);
        AnchorPane.setLeftAnchor(BuyButton, 0.0);
        AnchorPane.setBottomAnchor(BuyButton, 0.0);
        AnchorPane.setRightAnchor(BuyButton, 0.0);

        scorePane.setPrefSize(170, 100);
        turnPane.setPrefSize(170, 100);
        phasePane.setPrefSize(170, 100);
        resourcesPane.setPrefSize(170, 100);
        buy.setPrefSize(170, 100);
        pass.setPrefSize(170, 100);

        scorePane.setStyle(cellBorderStyle);
        turnPane.setStyle(cellBorderStyle);
        phasePane.setStyle(cellBorderStyle);
        resourcesPane.setStyle(cellBorderStyle);
        buy.setStyle(cellBorderStyle);
        pass.setStyle(cellBorderStyle);

        Info.add(scorePane, 0, 0);
        Info.add(turnPane, 1, 0);
        Info.add(phasePane, 2, 0);
        Info.add(resourcesPane, 3, 0);
        Info.add(pass, 4, 0);
        Info.add(buy, 5, 0);

    }

    private void CreateLaneinfo() {

        for (int row = 0; row < battle.getOriginalLanes().size(); row++) {

            AnchorPane Linfo = new AnchorPane();

            if (battle.getOriginalLanes().size() == 5) {
                Linfo.setPrefSize(170, 85);
            }

            else {
                Linfo.setPrefSize(170, 147);
            }

            Label WallHealth = new Label(
                    "Wall current health: " + battle.getOriginalLanes().get(row).getLaneWall().getCurrentHealth());
            Label DangerLevel = new Label("Danger Level: " + battle.getOriginalLanes().get(row).getDangerLevel());
            Label Available = new Label("Lane State : available ");
            Label LaneName = new Label("Lane Number: " + (row + 1));

            if (battle.getOriginalLanes().get(row).isLaneLost()) {

                Available = new Label("Lane State : Destroyed ");

            }

            VBox Weapons = new VBox(LaneName);
            Weapons.setPrefWidth(145);

            Label weaponLabel = new Label("Weapons Purchsed : ");
            weaponLabel.setTextFill(Color.WHITE);
            Weapons.getChildren().add(weaponLabel);

            WallHealth.setTextFill(Color.WHITE);
            DangerLevel.setTextFill(Color.WHITE);
            Available.setTextFill(Color.WHITE);
            LaneName.setTextFill(Color.WHITE);

            Linfo.getChildren().addAll(WallHealth, DangerLevel, Available, LaneName, Weapons);
            Linfo.setStyle("-fx-border-color: Black; -fx-border-width: 1px;");

            AnchorPane.setTopAnchor(DangerLevel, 30.0);
            AnchorPane.setTopAnchor(Available, 45.0);
            AnchorPane.setTopAnchor(WallHealth, 15.0);

            AnchorPane.setLeftAnchor(WallHealth, 5.0);
            AnchorPane.setLeftAnchor(DangerLevel, 5.0);
            AnchorPane.setLeftAnchor(Available, 5.0);
            AnchorPane.setLeftAnchor(LaneName, 5.0);

            AnchorPane.setLeftAnchor(Weapons, 5.0);
            AnchorPane.setTopAnchor(Weapons, 70.0);

            Linfo.setStyle("-fx-background-color: #192734;-fx-border-color: White; -fx-border-width: 1px;");

            ScrollPane scrollPane = new ScrollPane(Linfo);
            scrollPane.setFitToWidth(true);
            scrollPane.setFitToHeight(true);
            scrollPane.setStyle(
                    "-fx-background-color: transparent; -fx-background: transparent; -fx-control-inner-background: transparent;");
            scrollPane.setHbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);
            scrollPane.setVbarPolicy(ScrollPane.ScrollBarPolicy.NEVER);

            LaneInfo.add(scrollPane, 0, row);

        }

    }

    private void updateLaneinfo(String W) {

        for (int row = 0; row < battle.getOriginalLanes().size(); row++) {
            AnchorPane anchorPane = (AnchorPane) getNodeByRowColumnIndex(row, 0, LaneInfo);

            if (anchorPane != null) {

                ObservableList<Node> children = anchorPane.getChildren();

                VBox vBox = null;

                for (int i = children.size() - 1; i >= 0; i--) {

                    Node child = children.get(i);
                    if (child instanceof VBox) {

                        vBox = (VBox) child;

                    } else {

                        anchorPane.getChildren().remove(child);
                    }
                }

                if (vBox != null && W != null) {

                    vBox.getChildren().add(new Label(W));

                }
            }

            Label WallHealth = new Label(
                    "Wall current health: " + battle.getOriginalLanes().get(row).getLaneWall().getCurrentHealth());
            Label DangerLevel = new Label("Danger Level: " + battle.getOriginalLanes().get(row).getDangerLevel());
            Label Available = new Label("Lane State : available ");
            Label LaneName = new Label("Lane Number: " + (row + 1));

            if (battle.getOriginalLanes().get(row).isLaneLost()) {

                Available = new Label("Lane State : Destroyed ");

            }

            anchorPane.getChildren().addAll(WallHealth, DangerLevel, Available, LaneName);

            WallHealth.setTextFill(Color.WHITE);
            DangerLevel.setTextFill(Color.WHITE);
            Available.setTextFill(Color.WHITE);
            LaneName.setTextFill(Color.WHITE);

            AnchorPane.setTopAnchor(DangerLevel, 30.0);
            AnchorPane.setTopAnchor(Available, 45.0);
            AnchorPane.setTopAnchor(WallHealth, 15.0);

            AnchorPane.setLeftAnchor(WallHealth, 5.0);
            AnchorPane.setLeftAnchor(DangerLevel, 5.0);
            AnchorPane.setLeftAnchor(Available, 5.0);
            AnchorPane.setLeftAnchor(LaneName, 5.0);

        }
    }

    private AnchorPane getNodeByRowColumnIndex(int row, int column, GridPane gridPane) {

        Node result = null;
        ObservableList<Node> children = gridPane.getChildren();

        for (Node node : children) {
            if (GridPane.getRowIndex(node) == row && GridPane.getColumnIndex(node) == column) {
                if (node instanceof ScrollPane) {

                    ScrollPane scrollPane = (ScrollPane) node;
                    Node content = scrollPane.getContent();
                    if (content instanceof AnchorPane) {
                        result = (AnchorPane) content;
                    } else {

                        System.out.println("Warning: ScrollPane content is not an AnchorPane");
                    }
                } else if (node instanceof AnchorPane) {

                    result = (AnchorPane) node;
                } else {

                    System.out.println("Warning: Node is neither a ScrollPane nor an AnchorPane");
                }
                break;
            }
        }

        return (AnchorPane) result;
    }

    private void isGameOver() {

        if (battle.isGameOver()) {

            ChangeScene(primaryStage, gameOver());

        }

    }

    public void SpawnTitan(GridPane gridPane) {

        int laneIndex = lane();

        ArrayList<Titan> Titan = new ArrayList<>();
        Titan LastTitan = null;

        if (laneIndex != -1 && !battle.getOriginalLanes().get(laneIndex).isLaneLost()) {
            // Check if lane index is valid
            Pane pane = getPaneFromCell(this.gridPane, laneIndex, 14);
            Titan.addAll(battle.getOriginalLanes().get(laneIndex).getTitans());
            LastTitan = Titan.get(Titan.size() - 1);

            if (pane != null) {

                ArrayList<Titan> titans = new ArrayList<>(battle.getOriginalLanes().get(laneIndex).getTitans());
                // System.out.println(battle.getOriginalLanes().get(0).getTitans().peek().getCurrentHealth());

                if (!titans.isEmpty() && titans.get(titans.size() - 1) instanceof PureTitan) {

                    DisplayPure pureTitanDisplay = new DisplayPure((PureTitan) LastTitan);
                    pane.getChildren().add(pureTitanDisplay);

                } else if (!titans.isEmpty() && titans.get(titans.size() - 1) instanceof AbnormalTitan) {

                    DisplayAbnormal AbnormalTitanDisplay = new DisplayAbnormal((AbnormalTitan) LastTitan);
                    pane.getChildren().add(AbnormalTitanDisplay);

                } else if (!titans.isEmpty() && titans.get(titans.size() - 1) instanceof ColossalTitan) {

                    DisplayColossal ColossalTitanDisplay = new DisplayColossal((ColossalTitan) LastTitan);
                    pane.getChildren().add(ColossalTitanDisplay);

                } else if (!titans.isEmpty() && titans.get(titans.size() - 1) instanceof ArmoredTitan) {

                    DisplayArmored ArmoredTitanDisplay = new DisplayArmored((ArmoredTitan) LastTitan);
                    pane.getChildren().add(ArmoredTitanDisplay);
                }
            } else {
                System.out.println("Error: Pane not found at cell (rowIndex: " + laneIndex + ", columnIndex: 14)");
            }
        } else {
            System.out.println("Error: Lane index is invalid");
        }
    }

    private Pane getPaneFromCell(GridPane gridPane, int rowIndex, int colIndex) {
        // Retrieve the node at the specified row and column index
        Node node = getNodeFromGridPane(gridPane, rowIndex, colIndex);

        // Check if the node is a Pane
        if (node instanceof Pane) {
            return (Pane) node;
        } else {
            return null; // Return null if the node is not a Pane
        }
    }

    private Node getNodeFromGridPane(GridPane gridPane, int rowIndex, int colIndex) {
        // Iterate over the children of the GridPane to find the node at the specified
        // row and column index
        for (Node node : gridPane.getChildren()) {
            Integer rowIndexGrid = GridPane.getRowIndex(node);
            Integer colIndexGrid = GridPane.getColumnIndex(node);

            if (rowIndexGrid != null && colIndexGrid != null && rowIndexGrid == rowIndex && colIndexGrid == colIndex) {
                return node; // Return the node if its row and column index match the specified indices
            }
        }

        return null; // Return null if no node is found at the specified indices
    }

    public int lane() {

        for (int i = 0; i < battle.getOriginalLanes().size(); i++) {

            if (size.get(i) < battle.getOriginalLanes().get(i).getDangerLevel()) {
                return i; // Return the index of the first lane that meets the condition
            }

        }

        for (int j = 0; j < battle.getOriginalLanes().size(); j++) {

            if (battle.getOriginalLanes().get(j) == battle.getLanes().peek()) {
                System.out.println(j);
                return j;
            }
        }

        return -1; // Return -1 if no lane meets the condition
    }

    public void MoveTitans(GridPane gridPane) {
        // Iterate over the children of the GridPane
        for (Node node : gridPane.getChildren()) {

            if (node instanceof Pane) {
                Pane pane = (Pane) node;
                // Iterate over the children of each pane
                for (Node childNode : pane.getChildren()) {

                    if (childNode instanceof DisplayPure) {

                        DisplayPure pureTitanDisplay = (DisplayPure) childNode;
                        pureTitanDisplay.getTitan().setDistance(
                                pureTitanDisplay.getTitan().getDistance() - pureTitanDisplay.getTitan().getSpeed() * 4);
                        pureTitanDisplay.updateLabels();

                        if (!pureTitanDisplay.getTitan().hasReachedTarget()) {

                            pureTitanDisplay.setTranslateX(
                                    pureTitanDisplay.getTranslateX() - pureTitanDisplay.getTitan().getSpeed() * 10.3);

                        }

                    } else if (childNode instanceof DisplayAbnormal) {

                        DisplayAbnormal abnormalTitanDisplay = (DisplayAbnormal) childNode;
                        abnormalTitanDisplay.getTitan().setDistance(abnormalTitanDisplay.getTitan().getDistance()
                                - abnormalTitanDisplay.getTitan().getSpeed() * 4);
                        abnormalTitanDisplay.updateLabels();
                        // Apply translation to move the DisplayAbnormal horizontally by 30 pixels to
                        // the left
                        if (!abnormalTitanDisplay.getTitan().hasReachedTarget()) {

                            abnormalTitanDisplay.setTranslateX(abnormalTitanDisplay.getTranslateX()
                                    - abnormalTitanDisplay.getTitan().getSpeed() * 9.8);

                        }

                    } else if (childNode instanceof DisplayColossal) {

                        DisplayColossal colossalTitanDisplay = (DisplayColossal) childNode;
                        colossalTitanDisplay.getTitan().setDistance(colossalTitanDisplay.getTitan().getDistance()
                                - colossalTitanDisplay.getTitan().getSpeed() * 4);
                        colossalTitanDisplay.updateLabels();
                        // Apply translation to move the DisplayColossal horizontally by 30 pixels to
                        // the left
                        if (!colossalTitanDisplay.getTitan().hasReachedTarget()) {

                            colossalTitanDisplay.setTranslateX(colossalTitanDisplay.getTranslateX()
                                    - colossalTitanDisplay.getTitan().getSpeed() * 9.5);

                        }

                    } else if (childNode instanceof DisplayArmored) {
                        DisplayArmored armoredTitanDisplay = (DisplayArmored) childNode;
                        armoredTitanDisplay.getTitan().setDistance(armoredTitanDisplay.getTitan().getDistance()
                                - armoredTitanDisplay.getTitan().getSpeed() * 4);
                        armoredTitanDisplay.updateLabels();
                        // Apply translation to move the DispalyArmored horizontally by 30 pixels to the
                        // left
                        if (!armoredTitanDisplay.getTitan().hasReachedTarget()) {

                            armoredTitanDisplay.setTranslateX(armoredTitanDisplay.getTranslateX()
                                    - armoredTitanDisplay.getTitan().getSpeed() * 10.3);

                        }
                    }
                }
            }
        }
    }

    public void removeDead() {

        List<Node> nodesToRemove = new ArrayList<>();

        for (Node node : gridPane.getChildren()) {
            if (node instanceof Pane) {
                Pane pane = (Pane) node;
                Integer row = GridPane.getRowIndex(pane);
                if (row == null) {
                    row = 0; // Default to row 0 if no row index is found
                }

                for (Node childNode : pane.getChildren()) {
                    if (childNode instanceof DisplayPure) {
                        DisplayPure pureTitanDisplay = (DisplayPure) childNode;
                        if (pureTitanDisplay.getTitan().isDefeated()) {
                            nodesToRemove.add(pureTitanDisplay);

                        }
                    } else if (childNode instanceof DisplayAbnormal) {
                        DisplayAbnormal abnormalTitanDisplay = (DisplayAbnormal) childNode;
                        if (abnormalTitanDisplay.getTitan().isDefeated()) {
                            nodesToRemove.add(abnormalTitanDisplay);

                        }
                    } else if (childNode instanceof DisplayColossal) {
                        DisplayColossal colossalTitanDisplay = (DisplayColossal) childNode;
                        if (colossalTitanDisplay.getTitan().isDefeated()) {
                            nodesToRemove.add(colossalTitanDisplay);

                        }
                    } else if (childNode instanceof DisplayArmored) {
                        DisplayArmored armoredTitanDisplay = (DisplayArmored) childNode;
                        if (armoredTitanDisplay.getTitan().isDefeated()) {
                            nodesToRemove.add(armoredTitanDisplay);

                        }
                    }
                }
            }

        }

        for (Node nodeToRemove : nodesToRemove) {
            Pane parentPane = (Pane) nodeToRemove.getParent();
            parentPane.getChildren().remove(nodeToRemove);
        }

    }

    public void RemoveLostLane(GridPane gridPane) {
        int numRows = gridPane.getRowConstraints().size(); // Get the total number of rows

        // Loop over each row
        for (int row = 0; row < numRows; row++) {
            boolean isRowLost = battle.getOriginalLanes().get(row).isLaneLost(); // Check if the row is lost
            if (isRowLost) {
                removeLane(gridPane, row); // If the row is lost, remove all nodes in that row
            }
        }
    }

    private void removeLane(GridPane gridPane, int row) {
        Iterator<Node> iterator = gridPane.getChildren().iterator();
        while (iterator.hasNext()) {
            Node node = iterator.next();
            Integer rowIndex = GridPane.getRowIndex(node);
            if (rowIndex != null && rowIndex == row) {
                iterator.remove(); // Use iterator to remove the node
            }
        }
    }

    public static void main(String[] args) {
        launch(args);
    }
}