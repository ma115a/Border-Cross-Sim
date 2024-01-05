import java.util.Collections;
import java.util.ArrayList;
public class Main {


    public static void main(String[] args) {
        
        ArrayList<Vehicle> vehicles = new ArrayList<>();
        // for(int i = 0; i < 35; i++) {
        //     vehicles.add(new PersonalVehicle());
        // }
        // for(int i = 0; i < 10; i++) {
        //     vehicles.add(new Truck());
        // }

        for(int i = 0; i < 5; i++) {
            vehicles.add(new Bus());
        }

        // Collections.shuffle(vehicles);
        for(Vehicle vehicle : vehicles) {
            System.out.println(vehicle);
        }
    }
 
}
