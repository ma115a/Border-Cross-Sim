package vehicles;
import java.util.ArrayList;
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
public class Truck extends Vehicle implements ITruck {

    private static final int MAX_PASSENGER_COUNT = 3;
    private static final double massOverloadProbabilityBound = 0.2;
    private static final double maxMassOverloadBound = 0.3;
    private int declaredMass;
    private int realMass;


    private boolean needsDocumentation;

    private boolean hasDocumentation;

    public Truck(ArrayList<Passenger> passengers) {
        // this.vehicleType = "Truck";
        if(passengers.size() <= MAX_PASSENGER_COUNT) {
            this.passengers = passengers;
        }
        else {
            this.passengers = new ArrayList<>();
            for(int i = 0; i < MAX_PASSENGER_COUNT; i++) {
                this.passengers.add(passengers.get(i));
            }
        }
        this.generateMasses();
        this.iconName = RandomData.getRandomVehicleIconName("Truck");
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

    public Truck() {
        // this.vehicleType = "Truck";
        Random rand = new Random();
        this.passengers = RandomData.getRandomPassengers(rand.nextInt(MAX_PASSENGER_COUNT) + 1);
        this.generateMasses();
        this.needsDocumentation = rand.nextBoolean();
        this.vehicleID = rand.nextInt(1_000_000);
        this.iconName = RandomData.getRandomVehicleIconName("Truck");
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


    public boolean isOverweight() {
        return this.realMass > this.declaredMass;
    }

    public int getDeclaredMass() {
        return this.declaredMass;
    }

    public int getRealMass() {
        return this.realMass;
    }
    
    private void generateMasses() {
        Random rand = new Random();
        double probability = rand.nextDouble();
        this.declaredMass = RandomData.getRandomPermissibleMass();
        if(probability <= massOverloadProbabilityBound) {
            double multiplyBy = rand.nextDouble(maxMassOverloadBound) + 1;
            this.realMass = (int) (this.declaredMass * multiplyBy);
        }
        else this.realMass = this.declaredMass;
    }

    public void setDocumentation(boolean hasDocumentation) {
        this.hasDocumentation = hasDocumentation;
    }

    public boolean needsDocumentation() {
        return this.needsDocumentation;
    }

    @Override
    public String toString() {
        return super.toString() + "\nDeclared mass: " + this.declaredMass + " Real mass: " + this.realMass + " Needs documentation : " + this.needsDocumentation;
    }

    
}
