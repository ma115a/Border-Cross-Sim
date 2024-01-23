package utils;
import java.io.File;
import java.util.ArrayList;
import java.util.Random;

import passengers.Baggage;
import passengers.Passenger;

import javafx.scene.image.*;

import main.Main;



//class that contains various static methods for generating random data. like passenger names and truck mass
public class RandomData {
    

    private static final String[] names = {"Seamus", "Kailey", "Kiana", "Waylon", "Guillermo", "Miriam", "Mae" ,"Ian", "Jett", "Maximillian", "Lillian", "Esteban", "Aya", "Phillip", "Cordell", "Stephen", "Joana", "Celine", "Julie", "Cora"};
    private static final String[] lastNames = {"Cochrane", "Pease", "Smalley", "Rios", "Whiteside", "Kern", "Steed", "Fajardo", "Marroquin","Daniels", "Caballero", "Lang", "Devries", "Landrum", "Raymond", "Spicer", "Perales", "Cloud", "Southard", "Locklear"};


    private static final int[] persmissibleVehicleMasses = {10_000, 11_500, 18_000, 25_000, 31_000, 40_000, 44_000};
    
    private static final Random rand = new Random();
    
    public static String getRandomName() {
        return names[rand.nextInt(20)];
    }

    public static String getRandomLastName() {
        return lastNames[rand.nextInt(20)];
    }

    public static ArrayList<Passenger> getRandomPassengers(int count) {
        ArrayList<Passenger> passengers = new ArrayList<>();
        for(int i = 0; i < count; i++) {
            passengers.add(new Passenger(RandomData.getRandomName(), RandomData.getRandomLastName()));
        }
        return passengers;
    }

    public static int getRandomPermissibleMass() {
        return persmissibleVehicleMasses[rand.nextInt(7)];
    }
    

    public static ArrayList<Baggage> getRandomBaggage(ArrayList<Passenger> passengers, double baggageProbabililty) {

        ArrayList<Baggage> result = new ArrayList<>();
        for(int i = 0; i < passengers.size(); i++) {
            double probability = rand.nextDouble();
            if(probability <= baggageProbabililty) {
                result.add(new Baggage(passengers.get(i)));
            }
        }
        return result;

    }


    public static String getRandomVehicleIconName(String vehicleType) {
        String iconName = vehicleType + (rand.nextInt(3) + 1) + ".png";
        return iconName;

    }

    public static ImageView getVehicleIcon(String iconName) {
        double factor = 0.08;
        String path = "img" + File.separator + iconName;
        Image img = new Image(new File(path).toURI().toString());

        ImageView imv = new ImageView(img);
        imv.setPreserveRatio(true);
        imv.fitHeightProperty().bind(Main.mainStage.heightProperty().multiply(factor));
        imv.fitWidthProperty().bind(Main.mainStage.widthProperty().multiply(factor));
        return imv;
    }
}
