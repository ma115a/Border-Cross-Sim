package main;
import vehicles.*;
import java.util.Scanner;
import java.io.FileInputStream;
import java.io.File;
import java.io.PrintStream;
import java.io.OutputStream;
import java.io.IOException;
import java.util.ArrayList;
import utils.FileUtil;
import incidents.*;

import javafx.animation.AnimationTimer;
import javafx.animation.KeyFrame;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.geometry.HPos;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.stage.Popup;
import javafx.stage.Stage;
import javafx.util.Duration;
import passengers.Passenger;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.image.Image;
import javafx.scene.Cursor;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Label;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;



public class Main extends Application {

    public static boolean simulationStarted = false;
    public static boolean simulationPaused = false;
    public static boolean incidentSceneCreated = false;

    public long lastUpdateTime = -1;

    public static Stage mainStage;
    public static Scene mainScene;
    public static Scene terminalsScene;
    public static Scene queueScene;
    public static Scene incidentScene;
    public static StackPane mainStackPane = new StackPane();
    public static GridPane gridPane1 = new GridPane();

    public static GridPane gridPaneQueue = new GridPane();
    public static GridPane gridPaneIncident = new GridPane();

    public static VBox smallVehicleQueue = new VBox(10);

    public static VBox vehicleQueue1 = new VBox(15);
    public static VBox vehicleQueue2 = new VBox(15);
    public static VBox vehicleQueue3 = new VBox(15);

    public static VBox incidentQueue1 = new VBox(15);
    public static VBox incidentQueue2 = new VBox(15);
    public static VBox incidentQueue3 = new VBox(15);

    public static VBox failedQueue = new VBox(15);
    public static VBox passedWithIncidentQueue = new VBox(15);

    public static ImageView policeTerminal1active;
    public static ImageView policeTerminal2active;
    public static ImageView policeTerminal3active;
    public static ImageView customsTerminal1active;
    public static ImageView customsTerminal2active;

    public static ImageView policeTerminal1inactive;
    public static ImageView policeTerminal2inactive;
    public static ImageView policeTerminal3inactive;
    public static ImageView customsTerminal1inactive;
    public static ImageView customsTerminal2inactive;

    public static ImageView policeTerminal1;
    public static ImageView policeTerminal2;
    public static ImageView policeTerminal3;

    public static ImageView customsTerminal1;
    public static ImageView customsTerminal2;

    public static ImageView policeTerminal1Vehicle;
    public static ImageView policeTerminal2Vehicle;
    public static ImageView policeTerminal3Vehicle;

    public static ImageView customsTerminal1Vehicle;
    public static ImageView customsTerminal2Vehicle;

    public static Button incidentBtn = new Button("Incidents");
    public static Button showQueueBtn = new Button("Show Queue");
    public static Button startPauseBtn = new Button("Start");
    public static Button exitBtn = new Button("Exit");


    public static Button startPauseBtn2 = new Button("Start");
    
    public static ListView<String> consoleOutput = new ListView<>();

    
    public static StringBuilder consoleOutputLine = new StringBuilder();
    
    public static AnimationTimer mainTimer;
    public static Timeline timeline;
    public static Label timerLabel = new Label();
    public static int elapsedSeconds = 0;

    public static Thread simThread;
    
    public int value = 0;
    
    private static boolean firstClick = true;
    private static boolean firstClick2 = true;
    
    public static void redirectOutput() {
        consoleOutput.setCellFactory(lv -> new ListCell<String>() {
                @Override
                public void updateItem(String item, boolean empty) {
                    super.updateItem(item, empty);
                    if (empty) {
                        setText(null);
                    } else {
                        setText(item);
                        setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 12)); // Replace "Arial" and 20 with your desired font and size
                    }
                }
            });
        PrintStream ps = new PrintStream(new OutputStream() {
                @Override
                public void write(int b) throws IOException {
                    char c = (char) b;
                    if(c == '\n') {
                        appendText(consoleOutputLine.toString());
                        consoleOutputLine.setLength(0);
                    }
                    else {
                        consoleOutputLine.append(c);
                    }
                }
            });

            System.setOut(ps);
            System.setErr(ps);
    }
    public static void setPoliceTerminal1Vehicle(ImageView icon) {
        if(icon == null && policeTerminal1Vehicle != null) {
            gridPane1.getChildren().remove(policeTerminal1Vehicle);
        }
        policeTerminal1Vehicle = icon;
        if(policeTerminal1Vehicle != null) {
            gridPane1.getChildren().remove(policeTerminal1Vehicle);
            gridPane1.add(policeTerminal1Vehicle, 2, 5);
        }
        updateVehicles();
    }

    public static void setPoliceTerminal2Vehicle(ImageView icon) {
        if(icon == null && policeTerminal2Vehicle != null) {
            gridPane1.getChildren().remove(policeTerminal2Vehicle);
        }
        policeTerminal2Vehicle = icon;
        if(policeTerminal2Vehicle != null) {
            gridPane1.getChildren().remove(policeTerminal2Vehicle);
            gridPane1.add(policeTerminal2Vehicle, 10, 5);
        }
        updateVehicles();
    }

    public static void setPoliceTerminal3Vehicle(ImageView icon) {
        if(icon == null && policeTerminal3Vehicle != null) {
            gridPane1.getChildren().remove(policeTerminal3Vehicle);
        }
        policeTerminal3Vehicle = icon;
        if(policeTerminal3Vehicle != null) {
            gridPane1.getChildren().remove(policeTerminal3Vehicle);            
            gridPane1.add(policeTerminal3Vehicle, 18, 5);
        }
        updateVehicles();
    }

    public static void setCustomsTerminal1Vehicle(ImageView icon) {
        if(icon == null && customsTerminal1Vehicle != null) {
            gridPane1.getChildren().remove(customsTerminal1Vehicle);
        }
        customsTerminal1Vehicle = icon;
        if(customsTerminal1Vehicle != null) {
            gridPane1.getChildren().remove(customsTerminal1Vehicle);
            gridPane1.add(customsTerminal1Vehicle, 6, 2);
        }
        updateVehicles();
    }
    
    public static void setCustomsTerminal2Vehicle(ImageView icon) {
        if(icon == null && customsTerminal2Vehicle != null) {
            gridPane1.getChildren().remove(customsTerminal2Vehicle);
        }
        customsTerminal2Vehicle = icon;
        if(customsTerminal2Vehicle != null) {
            gridPane1.getChildren().remove(customsTerminal2Vehicle);
            gridPane1.add(customsTerminal2Vehicle, 18, 2);
        }
        updateVehicles();
    }

    



    public ImageView getImage(String name){
        double multiplyBy = 0.08;
        String path = "img" + File.separator + name;
        Image img = new Image(new File(path).toURI().toString());

        ImageView imv = new ImageView(img);
        imv.setPreserveRatio(true); 
        imv.fitHeightProperty().bind(mainStage.heightProperty().multiply(multiplyBy));
        imv.fitWidthProperty().bind(mainStage.widthProperty().multiply(multiplyBy));
        return imv;
    }

    public static void appendText(String str) {
        Platform.runLater(() -> {
            consoleOutput.getItems().add(str);
            consoleOutput.scrollTo(consoleOutput.getItems().size() - 1);
        });
    }

    public static void createIncidentScene() {
        // gridPaneIncident.setGridLinesVisible(true);
        // gridPaneIncident.setBackground(new Background(new BackgroundFill(Color.DARKSLATEGREY, null, null)));
        gridPaneIncident.setStyle("-fx-background-color: #3c3836");

        gridPaneIncident.setPadding(new Insets(10, 10, 10, 10));

        ColumnConstraints ccIncident = new ColumnConstraints();

        ccIncident.setPercentWidth(4.76);

        RowConstraints rcIncident = new RowConstraints();
        rcIncident.setPercentHeight(6.66);

        for(int i = 0; i < 15; i++) {
            gridPaneIncident.getRowConstraints().add(rcIncident);
        }

        for(int i = 0; i < 21; i++) {
            gridPaneIncident.getColumnConstraints().add(ccIncident);
        }

        Button backToMainBtn = new Button("Back");
        Button exitBtn = new Button("Exit");
        Label passedLabel = new Label("Failed to pass");
        Label failedLabel = new Label("Passed with incident");

        passedLabel.setStyle("-fx-background-color: #3c3836; -fx-text-fill: #ffffff;");
        failedLabel.setStyle("-fx-backgoround-color: #3c3836; -fx-text-fill: #ffffff;");
        passedLabel.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
        failedLabel.setFont(Font.font("Jetbrains Mono", FontWeight.BOLD, 14));

        backToMainBtn.setOnMouseClicked(e -> {
            mainStage.setScene(terminalsScene);
        });

        exitBtn.setOnMouseClicked(e -> {
            Platform.exit();
        });

        int counterPassed = 0;
        int counterFailed = 0;
        ArrayList<Incident> incidents = new ArrayList<>();
        ArrayList<Incident> customsIncidents = FileUtil.readIncidentsFromTextFile();
        ArrayList<Incident> documentIncidents = FileUtil.readFromBinaryFile();
        if(customsIncidents != null) {
            incidents.addAll(customsIncidents);
        }
        if(documentIncidents != null) {
            incidents.addAll(documentIncidents);
        }
        if(incidents.size() > 0) {
            failedQueue.getChildren().clear();
            passedWithIncidentQueue.getChildren().clear();
            for(Incident incident : incidents) {
                String fileName = "img" + File.separator + incident.getVehicleIconName();
                Image incidentVehicleImg = new Image(new File(fileName).toURI().toString());
                ImageView incidentVehicleImv = new ImageView(incidentVehicleImg);
                incidentVehicleImv.fitHeightProperty().bind(mainStage.heightProperty().multiply(0.052));
                incidentVehicleImv.fitWidthProperty().bind(mainStage.widthProperty().multiply(0.052));
                incidentVehicleImv.setPreserveRatio(true);
                incidentVehicleImv.setOnMouseClicked(e -> {
                    Popup popup = new Popup();
                    Label labelPopup = new Label();
                    Button closeBtn = new Button("Close");
                    VBox vboxPopup = new VBox(10);
                    HBox hboxPopup = new HBox();
        
                    vboxPopup.setPadding(new Insets(15, 15, 15, 15));

                    labelPopup.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
                    labelPopup.setStyle("-fx-text-fill: #ffffff;");

                    
                    closeBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 10));
                    closeBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                    closeBtn.prefHeightProperty().bind(Main.mainStage.heightProperty().multiply(0.02));
                    closeBtn.prefWidthProperty().bind(Main.mainStage.widthProperty().multiply(0.04));
                    closeBtn.setOnMouseEntered(event -> {
                        closeBtn.setStyle("-fx-background-color: #d03a30; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                        closeBtn.setCursor(Cursor.HAND);
                    });
                    closeBtn.setOnMouseExited(event -> {
                        closeBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                        closeBtn.setCursor(Cursor.DEFAULT);
                    });
                    
                    
                    closeBtn.setOnMouseClicked(event -> {
                        popup.hide();
                    });
                    
                    hboxPopup.setAlignment(Pos.CENTER);
                    hboxPopup.getChildren().add(closeBtn);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Date and time: " + incident.getDateAndTime());
                    sb.append("\nVehicle ID: " + incident.getVehicleID());
                    sb.append("\nTerminal: " + incident.getTerminalName());
                    sb.append("\nMessage: " + incident.getMessage());
                    sb.append("\nFailed to pass: " + incident.isSevere());
                    sb.append("\nPassengers: ");
                    int numPass = 0;
                    for(Passenger p : incident.getPassengers()) {
                        if(numPass == 0) {
                            sb.append(p.getFullName() + "\n");
                        }
                        else {
                            sb.append("            " + p.getFullName() + "\n");
                        }
                        numPass++;
                    }
                    labelPopup.setText(new String(sb));
                    vboxPopup.getChildren().addAll(labelPopup, hboxPopup);
                    popup.getContent().addAll(vboxPopup);
                    popup.show(Main.mainStage);
                });


                if(!incident.isSevere() && counterFailed < 14) {
                    failedQueue.getChildren().add(incidentVehicleImv);
                    counterFailed++;
                }
                else if(counterPassed < 14) {
                    passedWithIncidentQueue.getChildren().add(incidentVehicleImv);
                    counterPassed++;
                }
            }
        }


        backToMainBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
        backToMainBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
        backToMainBtn.prefHeightProperty().bind(mainStage.heightProperty().multiply(0.05));
        backToMainBtn.prefWidthProperty().bind(mainStage.widthProperty().multiply(0.07));
        backToMainBtn.setOnMouseEntered(e -> {
            backToMainBtn.setStyle("-fx-background-color: #7a7a10; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            backToMainBtn.setCursor(Cursor.HAND); // Change cursor to hand on mouse enter
        });
        backToMainBtn.setOnMouseExited(e -> {
            backToMainBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            backToMainBtn.setCursor(Cursor.DEFAULT); // Change cursor to default on mouse exit
        });

        exitBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
        exitBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
        exitBtn.prefHeightProperty().bind(mainStage.heightProperty().multiply(0.05));
        exitBtn.prefWidthProperty().bind(mainStage.widthProperty().multiply(0.07));
        exitBtn.setOnMouseEntered(e -> {
            exitBtn.setStyle("-fx-background-color: #d03a30; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            exitBtn.setCursor(Cursor.HAND); // Change cursor to hand on mouse enter
        });
        exitBtn.setOnMouseExited(e -> {
            exitBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            exitBtn.setCursor(Cursor.DEFAULT); // Change cursor to default on mouse exit
        });
        
        failedQueue.setAlignment(Pos.TOP_CENTER);
        passedWithIncidentQueue.setAlignment(Pos.TOP_CENTER);
        GridPane.setHalignment(failedQueue, HPos.CENTER);
        GridPane.setHalignment(passedWithIncidentQueue, HPos.CENTER);
        gridPaneIncident.add(backToMainBtn, 1, 12, 2, 1);
        gridPaneIncident.add(exitBtn, 1, 14, 2, 1);
        gridPaneIncident.add(failedLabel, 4, 0, 3, 1);
        gridPaneIncident.add(passedLabel, 15, 0, 3, 1);

        GridPane.setHalignment(failedLabel, HPos.CENTER);
        GridPane.setHalignment(passedLabel, HPos.CENTER);
        gridPaneIncident.add(failedQueue, 5, 1, 1, 14);
        gridPaneIncident.add(passedWithIncidentQueue, 16, 1, 1, 14);


        incidentScene = new Scene(gridPaneIncident);
    }

    public static void updateIncidentScene() {
        int counterPassed = 0;
        int counterFailed = 0;
        ArrayList<Incident> incidents = new ArrayList<>();
        ArrayList<Incident> customsIncidents = FileUtil.readIncidentsFromTextFile();
        ArrayList<Incident> documentIncidents = FileUtil.readFromBinaryFile();
        if(customsIncidents != null) {
            incidents.addAll(customsIncidents);
        }
        if(documentIncidents != null) {
            incidents.addAll(documentIncidents);
        }
        if(incidents.size() > 0) {
            failedQueue.getChildren().clear();
            passedWithIncidentQueue.getChildren().clear();
            for(Incident incident : incidents) {
                String fileName = "img" + File.separator + incident.getVehicleIconName();
                Image incidentVehicleImg = new Image(new File(fileName).toURI().toString());
                ImageView incidentVehicleImv = new ImageView(incidentVehicleImg);
                incidentVehicleImv.fitHeightProperty().bind(mainStage.heightProperty().multiply(0.052));
                incidentVehicleImv.fitWidthProperty().bind(mainStage.widthProperty().multiply(0.052));
                incidentVehicleImv.setPreserveRatio(true);
                incidentVehicleImv.setOnMouseClicked(e -> {
                    Popup popup = new Popup();
                    Label labelPopup = new Label();
                    Button closeBtn = new Button("Close");
                    VBox vboxPopup = new VBox(10);
                    HBox hboxPopup = new HBox();
        
                    vboxPopup.setPadding(new Insets(15, 15, 15, 15));

                    labelPopup.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
                    labelPopup.setStyle("-fx-text-fill: #ffffff;");
                    
                    closeBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 10));
                    closeBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                    closeBtn.prefHeightProperty().bind(Main.mainStage.heightProperty().multiply(0.02));
                    closeBtn.prefWidthProperty().bind(Main.mainStage.widthProperty().multiply(0.04));
                    closeBtn.setOnMouseEntered(event -> {
                        closeBtn.setStyle("-fx-background-color: #d03a30; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                        closeBtn.setCursor(Cursor.HAND);
                    });
                    closeBtn.setOnMouseExited(event -> {
                        closeBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                        closeBtn.setCursor(Cursor.DEFAULT);
                    });
                    
                    
                    closeBtn.setOnMouseClicked(event -> {
                        popup.hide();
                    });
                    
                    hboxPopup.setAlignment(Pos.CENTER);
                    hboxPopup.getChildren().add(closeBtn);
                    StringBuilder sb = new StringBuilder();
                    sb.append("Date and time: " + incident.getDateAndTime());
                    sb.append("\nVehicle ID: " + incident.getVehicleID());
                    sb.append("\nTerminal: " + incident.getTerminalName());
                    sb.append("\nMessage: " + incident.getMessage());
                    sb.append("\nFailed to pass: " + incident.isSevere());
                    sb.append("\nPassengers: ");
                    int numPass = 0;
                    for(Passenger p : incident.getPassengers()) {
                        if(numPass == 0) {
                            sb.append(p.getFullName() + "\n");
                        }
                        else {
                            sb.append("            " + p.getFullName() + "\n");
                        }
                        numPass++;
                    }
                    labelPopup.setText(new String(sb));
                    vboxPopup.getChildren().addAll(labelPopup, hboxPopup);
                    popup.getContent().addAll(vboxPopup);
                    popup.show(Main.mainStage);
                });


                if(!incident.isSevere() && counterFailed < 14) {
                    failedQueue.getChildren().add(incidentVehicleImv);
                    counterFailed++;
                }
                else if(counterPassed < 14) {
                    passedWithIncidentQueue.getChildren().add(incidentVehicleImv);
                    counterPassed++;
                }
            }
        }
    }

    public static void createQueueScene() {
        // gridPaneQueue.setGridLinesVisible(true);
        // gridPaneQueue.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
        gridPaneQueue.setStyle("-fx-background-color: #3c3836");
        gridPaneQueue.setPadding(new Insets(10, 10, 10, 10));


        ColumnConstraints ccQueue = new ColumnConstraints();
        ccQueue.setPercentWidth(4.76);

        RowConstraints rcQueue = new RowConstraints();
        rcQueue.setPercentHeight(6.66);
        
        for(int i = 0; i < 15; i++) {
            gridPaneQueue.getRowConstraints().add(rcQueue);
        }

        for(int i = 0; i < 21; i++) {
            gridPaneQueue.getColumnConstraints().add(ccQueue);
        }

        Button backToMainBtn = new Button("Back");
        Button exitBtn = new Button("Exit");
        Button incidentBtn = new Button("Incidents");

        incidentBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
        incidentBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
        incidentBtn.prefHeightProperty().bind(mainStage.heightProperty().multiply(0.05));
        incidentBtn.prefWidthProperty().bind(mainStage.widthProperty().multiply(0.07));
        incidentBtn.setOnMouseEntered(e -> {
            incidentBtn.setStyle("-fx-background-color: #7a7a10; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            incidentBtn.setCursor(Cursor.HAND);
        });
        incidentBtn.setOnMouseExited(e -> {
            incidentBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            incidentBtn.setCursor(Cursor.DEFAULT);
        });



        backToMainBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
        backToMainBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
        backToMainBtn.prefHeightProperty().bind(mainStage.heightProperty().multiply(0.05));
        backToMainBtn.prefWidthProperty().bind(mainStage.widthProperty().multiply(0.07));
        backToMainBtn.setOnMouseEntered(e -> {
            backToMainBtn.setStyle("-fx-background-color: #7a7a10; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            backToMainBtn.setCursor(Cursor.HAND);
        });
        backToMainBtn.setOnMouseExited(e -> {
            backToMainBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            backToMainBtn.setCursor(Cursor.DEFAULT);
        });

        exitBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
        exitBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
        exitBtn.prefHeightProperty().bind(mainStage.heightProperty().multiply(0.05));
        exitBtn.prefWidthProperty().bind(mainStage.widthProperty().multiply(0.07));
        exitBtn.setOnMouseEntered(e -> {
            exitBtn.setStyle("-fx-background-color: #d03a30; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            exitBtn.setCursor(Cursor.HAND);
        });
        exitBtn.setOnMouseExited(e -> {
            exitBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            exitBtn.setCursor(Cursor.DEFAULT);
        });

        startPauseBtn2.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
        startPauseBtn2.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
        startPauseBtn2.prefHeightProperty().bind(mainStage.heightProperty().multiply(0.05));
        startPauseBtn2.prefWidthProperty().bind(mainStage.widthProperty().multiply(0.07));
        startPauseBtn2.setOnMouseEntered(e -> {
            startPauseBtn2.setStyle("-fx-background-color: #7a7a10; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            startPauseBtn2.setCursor(Cursor.HAND); 
        });
        startPauseBtn2.setOnMouseExited(e -> {
            startPauseBtn2.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            startPauseBtn2.setCursor(Cursor.DEFAULT); 
        });

        incidentBtn.setOnMouseClicked(e -> {
            if(!incidentSceneCreated) {
                    createIncidentScene();
                    incidentSceneCreated = true;
                }
                else {
                    updateIncidentScene();
                }
                mainStage.setScene(incidentScene);
        });

        backToMainBtn.setOnMouseClicked(e -> {
            mainStage.setScene(terminalsScene);
        });

        exitBtn.setOnMouseClicked(e -> Platform.exit());
        startPauseBtn2.setOnMouseClicked(e -> {
            if(!simulationStarted) {
                System.out.println("test");
                simThread.setDaemon(true);
                simThread.start();
                simulationStarted = true;
                startPauseBtn.setText("Pause");
                startPauseBtn2.setText("Pause");
           }
           if(!firstClick2) {
               
               if(simulationPaused) {
                   startPauseBtn.setText("Pause");
                   startPauseBtn2.setText("Pause");
                   simulationPaused = false;
                   synchronized(Simulation.pauseObject) {
                        Simulation.pauseObject.notifyAll();
                   }
                }
                else {
                    startPauseBtn.setText("Start");
                    startPauseBtn2.setText("Start");
                    simulationPaused = true;
                }
            }
            firstClick = false;
            firstClick2 = false;
        });
        
        gridPaneQueue.add(backToMainBtn, 1, 11, 2, 1);
        gridPaneQueue.add(startPauseBtn2, 1, 12, 2, 1);
        gridPaneQueue.add(incidentBtn, 1, 13, 2, 1);
        gridPaneQueue.add(exitBtn, 1, 14, 2, 1);


        GridPane.setHalignment(vehicleQueue1, HPos.CENTER);
        GridPane.setValignment(vehicleQueue1, VPos.CENTER);
        gridPaneQueue.add(vehicleQueue1, 9, 0, 1, 15);
        gridPaneQueue.add(vehicleQueue2, 10, 0, 1, 15);
        gridPaneQueue.add(vehicleQueue3, 11, 0, 1, 15);


        queueScene = new Scene(gridPaneQueue);
    }
    
    public void simulationStart() {
        if(!simulationStarted) {
            redirectOutput();
            System.out.println("simulation start");
            // createIncidentScene();
            Simulation.populateVehicles();
            simThread = new Thread(() -> {

                Simulation.simulationRun();
                System.out.println("thread executed");
            });
            timerLabel.setStyle("-fx-text-fill: #ffffff;");
            timeline = new Timeline(new KeyFrame(Duration.seconds(1), new EventHandler<ActionEvent>() {

                @Override
                public void handle(ActionEvent e) {
                    if(!Main.simulationPaused) {
                        elapsedSeconds++;
                        int minutes = elapsedSeconds / 60;
                        int seconds = elapsedSeconds % 60;
                        String timeFormat = String.format("Duration: %d:%02d", minutes, seconds);
                        timerLabel.setText(timeFormat);
                        if(Simulation.failedVehiclesCounter + Simulation.successVehclesCounter == 50) {
                            timeline.stop();
                        }
                    }
                }
            }));
            timeline.setCycleCount(Timeline.INDEFINITE);
            createQueueScene();
            // gridPane1.setGridLinesVisible(true);
            // gridPane1.setBackground(new Background(new BackgroundFill(Color.DARKGRAY, null, null)));
            gridPane1.setStyle("-fx-background-color: #3c3836");
            gridPane1.setPadding(new Insets(10, 10, 10, 10));
            gridPane1.setHgap(2);
            gridPane1.setVgap(2);
            

            ColumnConstraints ccMain = new ColumnConstraints();
            ccMain.setPercentWidth(4.76);
            RowConstraints rcMain = new RowConstraints();
            rcMain.setPercentHeight(8.33);

            for(int i = 0; i < 12; i++) {
                gridPane1.getRowConstraints().add(rcMain);
            }

            for(int i = 0; i < 21; i++) {
               gridPane1.getColumnConstraints().add(ccMain);
            }


            policeTerminal1active = getImage("policeActive.png");
            policeTerminal2active = getImage("policeActive.png");
            policeTerminal3active = getImage("policeActive.png");

            policeTerminal1inactive = getImage("policeInactive.png");
            policeTerminal3inactive = getImage("policeInactive.png");
            policeTerminal2inactive = getImage("policeInactive.png");

            customsTerminal1active = getImage("customsActive.png");
            customsTerminal2active = getImage("customsActive.png");

            customsTerminal1inactive = getImage("customsInactive.png");
            customsTerminal2inactive = getImage("customsInactive.png");

            policeTerminal1 = policeTerminal1active;
            policeTerminal2 = policeTerminal2active;
            policeTerminal3 = policeTerminal3active;

            customsTerminal1 = customsTerminal1active;
            customsTerminal2 = customsTerminal2active;

            int counter = 0;
            for(Vehicle vehicle : Simulation.vehicles) {
                if(counter >= 5) break;
                smallVehicleQueue.getChildren().add(vehicle.getIcon());
                counter++;
            }


            exitBtn.setOnMouseClicked(e -> Platform.exit());
            startPauseBtn.setOnMouseClicked(e -> {
               if(!simulationStarted) {
                    System.out.println("test");
                    simThread.setDaemon(true);
                    simThread.start();
                    timeline.play();
                    simulationStarted = true;
                    startPauseBtn.setText("Pause");
                    startPauseBtn2.setText("Pause");
               }
               if(!firstClick) {
                   
                   if(simulationPaused) {
                       startPauseBtn.setText("Pause");
                       startPauseBtn2.setText("Pause");
                       simulationPaused = false;
                       synchronized(Simulation.pauseObject) {
                            Simulation.pauseObject.notifyAll();
                       }
                    }
                    else {
                        startPauseBtn.setText("Start");
                        startPauseBtn2.setText("Start");
                        simulationPaused = true;
                    }
                }
                firstClick = false;
                firstClick2 = false;
            });



            incidentBtn.setOnMouseClicked(e -> {
                if(!incidentSceneCreated) {
                    createIncidentScene();
                    incidentSceneCreated = true;
                }
                else {
                    updateIncidentScene();
                }
                mainStage.setScene(incidentScene);
            });


            showQueueBtn.setOnMouseClicked(e -> {
                updateVehicles();
                mainStage.setScene(queueScene);
            });
            
            
            incidentBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
            incidentBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            incidentBtn.prefHeightProperty().bind(mainStage.heightProperty().multiply(0.05));
            incidentBtn.prefWidthProperty().bind(mainStage.widthProperty().multiply(0.07));
            incidentBtn.setOnMouseEntered(e -> {
                incidentBtn.setStyle("-fx-background-color: #7a7a10; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                incidentBtn.setCursor(Cursor.HAND);
            });
            incidentBtn.setOnMouseExited(e -> {
                incidentBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                incidentBtn.setCursor(Cursor.DEFAULT);
            });


            showQueueBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
            showQueueBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            showQueueBtn.prefHeightProperty().bind(mainStage.heightProperty().multiply(0.05));
            showQueueBtn.prefWidthProperty().bind(mainStage.widthProperty().multiply(0.07));
            showQueueBtn.setOnMouseEntered(e -> {
                showQueueBtn.setStyle("-fx-background-color: #7a7a10; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                showQueueBtn.setCursor(Cursor.HAND);
            });
            showQueueBtn.setOnMouseExited(e -> {
                showQueueBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                showQueueBtn.setCursor(Cursor.DEFAULT);
            });

            startPauseBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
            startPauseBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            startPauseBtn.prefHeightProperty().bind(mainStage.heightProperty().multiply(0.05));
            startPauseBtn.prefWidthProperty().bind(mainStage.widthProperty().multiply(0.07));
            startPauseBtn.setOnMouseEntered(e -> {
                startPauseBtn.setStyle("-fx-background-color: #7a7a10; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                startPauseBtn.setCursor(Cursor.HAND);
            });
            startPauseBtn.setOnMouseExited(e -> {
                startPauseBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                startPauseBtn.setCursor(Cursor.DEFAULT);
            });

            exitBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
            exitBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            exitBtn.prefHeightProperty().bind(mainStage.heightProperty().multiply(0.05));
            exitBtn.prefWidthProperty().bind(mainStage.widthProperty().multiply(0.07));
            exitBtn.setOnMouseEntered(e -> {
                exitBtn.setStyle("-fx-background-color: #d03a30; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                exitBtn.setCursor(Cursor.HAND);
            });
            exitBtn.setOnMouseExited(e -> {
                exitBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                exitBtn.setCursor(Cursor.DEFAULT);
            });

            timerLabel.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 14));
            GridPane.setHalignment(timerLabel, HPos.CENTER);
            
            
            
            gridPane1.add(policeTerminal1, 2, 4);
            gridPane1.add(policeTerminal2, 10, 4);
            gridPane1.add(policeTerminal3, 18, 4);
            gridPane1.add(customsTerminal1, 6, 1);
            gridPane1.add(customsTerminal2, 18, 1);
            gridPane1.add(smallVehicleQueue, 10, 7, 1, 5);
            gridPane1.add(timerLabel, 16, 7, 4, 1);
            gridPane1.add(consoleOutput, 14, 8, 7, 4);
            
            gridPane1.add(startPauseBtn, 1, 8, 2, 1);
            GridPane.setHalignment(startPauseBtn, HPos.CENTER);
            GridPane.setValignment(startPauseBtn, VPos.CENTER);
            gridPane1.add(showQueueBtn, 1, 9, 2, 1);
            GridPane.setHalignment(showQueueBtn, HPos.CENTER);
            GridPane.setValignment(showQueueBtn, VPos.CENTER);
            gridPane1.add(incidentBtn, 1, 10, 2, 1);
            GridPane.setHalignment(incidentBtn, HPos.CENTER);
            GridPane.setValignment(incidentBtn, VPos.CENTER);
            gridPane1.add(exitBtn, 1, 11, 2, 1);
            GridPane.setHalignment(exitBtn, HPos.CENTER);
            GridPane.setValignment(exitBtn, VPos.CENTER);

            terminalsScene = new Scene(gridPane1);



            mainTimer = new AnimationTimer() {
                @Override
                public void handle(long now) {

                    if(now - lastUpdateTime >= 500_000_000) {
                        updateTerminals();
                        // updateVehicles();
                        lastUpdateTime = now;
                    }

                }

                @Override
                public void start() {
                    super.start();
                    lastUpdateTime = System.nanoTime();
                }

                @Override 
                public void stop() {
                    super.stop();
                    lastUpdateTime = -1;
                }
            };


            mainTimer.start();
        }
    }

    public void updateTerminals() {
        gridPane1.getChildren().removeAll(policeTerminal1, policeTerminal2, policeTerminal3, customsTerminal1, customsTerminal2);

        if(Simulation.policeTerminal1.isWorking()) {
            policeTerminal1 = policeTerminal1active;
        }
        else policeTerminal1 = policeTerminal1inactive;

        if(Simulation.policeTerminal2.isWorking()) {
            policeTerminal2 = policeTerminal2active;
        }
        else policeTerminal2 = policeTerminal2inactive;

        if(Simulation.policeTerminal3.isWorking()) {
            policeTerminal3 = policeTerminal3active;
        }
        else policeTerminal3 = policeTerminal3inactive;

        
        if(Simulation.customsTerminal1.isWorking()) {
            customsTerminal1 = customsTerminal1active;
        }
        else customsTerminal1 = customsTerminal1inactive;

        if(Simulation.customsTerminal3.isWorking()) {
            customsTerminal2 = customsTerminal2active;
        }
        else customsTerminal2 = customsTerminal2inactive;

        gridPane1.add(policeTerminal1, 2, 4);
        gridPane1.add(policeTerminal2, 10, 4);
        gridPane1.add(policeTerminal3, 18, 4);
        gridPane1.add(customsTerminal1, 6, 1);
        gridPane1.add(customsTerminal2, 18, 1);

    }


    public static void updateVehicles() {
        smallVehicleQueue.getChildren().clear();
        vehicleQueue1.getChildren().clear();
        vehicleQueue2.getChildren().clear();
        vehicleQueue3.getChildren().clear();
        int counter = 0;
        for(Vehicle vehicle : Simulation.vehicles) {
            ImageView vehicleIcon = vehicle.getIcon();
            if(counter < 5) {
                vehicleIcon.fitHeightProperty().bind(mainStage.heightProperty().multiply(0.08));
                vehicleIcon.fitWidthProperty().bind(mainStage.widthProperty().multiply(0.08));
                vehicleIcon.setPreserveRatio(true);
                smallVehicleQueue.getChildren().add(vehicleIcon);
                counter++;
            }
            else {
                vehicleIcon.fitHeightProperty().bind(mainStage.heightProperty().multiply(0.052));
                vehicleIcon.fitWidthProperty().bind(mainStage.widthProperty().multiply(0.052));
                vehicleIcon.setPreserveRatio(true);

                if(counter >= 5 && counter < 20) {
                    vehicleQueue1.getChildren().add(vehicleIcon);
                    counter++;
                }
                else if(counter >= 20 && counter < 35) {
                    vehicleQueue2.getChildren().add(vehicleIcon);
                    counter++;
                }
                else if(counter >= 35 && counter < 50) {
                    vehicleQueue3.getChildren().add(vehicleIcon);
                    counter++;
                }
            }    
        }

    }

    @Override
    public void start(Stage primaryStage) {

        mainStage = primaryStage;
        
        primaryStage.setTitle("BorderCross by Mihajlo");
        primaryStage.setWidth(1920);
        primaryStage.setHeight(1200);

        Image img = new Image(new File("img" + File.separator + "splashPicture.jpg").toURI().toString());

        ImageView bg = new ImageView(img);
        bg.fitWidthProperty().bind(mainStage.widthProperty());
        bg.fitHeightProperty().bind(mainStage.heightProperty());
        mainStackPane.getChildren().add(bg);
        Button startBtn = new Button("Proceed to Simulation");
        Button exitBtn = new Button("Exit App");

        startBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 18));
        startBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
        startBtn.setOnMouseEntered(e -> {
            startBtn.setStyle("-fx-background-color: #7a7a10; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            startBtn.setCursor(Cursor.HAND);
        });
        startBtn.setOnMouseExited(e -> {
            startBtn.setStyle("-fx-background-color: #98971a; -fx-text-fill: #ffffff; -fx-border-color: #6a6a10; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            startBtn.setCursor(Cursor.DEFAULT);
        });


        exitBtn.setFont(Font.font("JetBrains Mono", FontWeight.BOLD, 18));

        exitBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
        exitBtn.setOnMouseEntered(e -> {
            exitBtn.setStyle("-fx-background-color: #d03a30; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            exitBtn.setCursor(Cursor.HAND);
        });
        exitBtn.setOnMouseExited(e -> {
            exitBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
            exitBtn.setCursor(Cursor.DEFAULT);
        });
        startBtn.prefWidthProperty().bind(mainStage.widthProperty().divide(6));
        startBtn.prefHeightProperty().bind(mainStage.heightProperty().divide(22));

        exitBtn.prefWidthProperty().bind(mainStage.widthProperty().divide(12));
        exitBtn.prefHeightProperty().bind(mainStage.heightProperty().divide(22)); 
        VBox vbox = new VBox(30);
        vbox.setAlignment(Pos.CENTER);
        vbox.getChildren().addAll(startBtn, exitBtn);
        mainStackPane.getChildren().add(vbox);
        mainScene = new Scene(mainStackPane);

        EventHandler<MouseEvent> click = new EventHandler<MouseEvent>() {
            @Override
            public void handle(MouseEvent e) {
                simulationStart();
                mainStage.setScene(terminalsScene);
            }
        };
        
        startBtn.setOnMouseClicked(click);
        
        exitBtn.setOnMouseClicked(e -> Platform.exit());


        
        mainStage.setScene(mainScene);
        mainStage.show();
    }



    
    public static void main(String[] args) {

        launch();
    }
 
}
