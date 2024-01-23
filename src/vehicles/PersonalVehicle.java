package vehicles;
import java.util.Random;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Cursor;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Popup;
import main.Main;
import passengers.Passenger;
import utils.RandomData;

import java.util.ArrayList;

public class PersonalVehicle extends Vehicle implements IPersonalVehicle {

    private static final int MAX_PASSENGER_COUNT = 5;

    public PersonalVehicle(ArrayList<Passenger> passengers) {
        // this.vehicleType = "Personal Vehicle";
        if(passengers.size() <= MAX_PASSENGER_COUNT) {
            this.passengers = passengers;
        }
        else {
            this.passengers = new ArrayList<>();
            for(int i = 0; i < MAX_PASSENGER_COUNT; i++) {
                this.passengers.add(passengers.get(i));
            }
        }
        this.iconName = RandomData.getRandomVehicleIconName("Personal Vehicle");
        this.icon = RandomData.getVehicleIcon(this.iconName);
        this.icon.setOnMouseClicked(e -> {
            Platform.runLater(() -> {

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
                    closeBtn.setCursor(Cursor.HAND); // Change cursor to hand on mouse enter
                });
                closeBtn.setOnMouseExited(event -> {
                    closeBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                    closeBtn.setCursor(Cursor.DEFAULT); // Change cursor to default on mouse exit
                });


                closeBtn.setOnMouseClicked(event -> {
                    popup.hide();
                });
                
                hboxPopup.setAlignment(Pos.CENTER);
                hboxPopup.getChildren().add(closeBtn);
                StringBuilder sb = new StringBuilder();
    
                sb.append("Vehicle ID: " + this.vehicleID);
                sb.append("\nNumber of passengers: " + this.passengers.size());
                sb.append("\nDriver: " + this.passengers.getFirst().getFullName());
                if(this.passengers.size() > 1) {

                    sb.append("\nPassengers: ");
                    int numPass = 0;
                    for(Passenger p : this.passengers) {
                        if(numPass == 0) {
        
                        }
                        else if(numPass == 1) {
                            sb.append(p.getFullName() + "\n");
                        }
                        else {
                            sb.append("            " + p.getFullName() + "\n");
                        }
                        numPass++;
                    }
                }
                labelPopup.setText(new String(sb));
                vboxPopup.getChildren().addAll(labelPopup, hboxPopup);
                popup.getContent().addAll(vboxPopup);
                popup.show(Main.mainStage);
            });
        });
    }

    public PersonalVehicle() {
        // this.vehicleType = "Personal Vehicle";
        Random rand = new Random();
        this.passengers = RandomData.getRandomPassengers(rand.nextInt(MAX_PASSENGER_COUNT) + 1);
        this.vehicleID = rand.nextInt(1_000_000);
        this.iconName = RandomData.getRandomVehicleIconName("Personal Vehicle");
        this.icon = RandomData.getVehicleIcon(this.iconName);
        this.icon.setOnMouseClicked(e -> {
            Platform.runLater(() -> {

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
                    closeBtn.setCursor(Cursor.HAND); // Change cursor to hand on mouse enter
                });
                closeBtn.setOnMouseExited(event -> {
                    closeBtn.setStyle("-fx-background-color: #fb4934; -fx-text-fill: #ffffff; -fx-border-color: #b02a26; -fx-border-width: 2px; -fx-border-radius: 5px; -fx-background-radius: 5px; -fx-padding: 10px 20px;");
                    closeBtn.setCursor(Cursor.DEFAULT); // Change cursor to default on mouse exit
                });
                closeBtn.setOnMouseClicked(event -> {
                    popup.hide();
                });
                
                hboxPopup.setAlignment(Pos.CENTER);
                hboxPopup.getChildren().add(closeBtn);
                StringBuilder sb = new StringBuilder();
    
                sb.append("Vehicle ID: " + this.vehicleID);
                sb.append("\nNumber of passengers: " + this.passengers.size());
                sb.append("\nDriver: " + this.passengers.getFirst().getFullName());
                if(this.passengers.size() > 1) {

                    sb.append("\nPassengers: ");
                    int numPass = 0;
                    for(Passenger p : this.passengers) {
                        if(numPass == 0) {
        
                        }
                        else if(numPass == 1) {
                            sb.append(p.getFullName() + "\n");
                        }
                        else {
                            sb.append("            " + p.getFullName() + "\n");
                        }
                        numPass++;
                    }
                }
                labelPopup.setText(new String(sb));
                vboxPopup.getChildren().addAll(labelPopup, hboxPopup);
                popup.getContent().addAll(vboxPopup);
                popup.show(Main.mainStage);
            });
        });
    }

    
}
