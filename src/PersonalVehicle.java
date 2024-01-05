import java.util.Random;
import java.util.ArrayList;

public class PersonalVehicle extends Vehicle {

    private static final int MAX_PASSENGER_COUNT = 5;

    PersonalVehicle(ArrayList<Passenger> passengers) {
        this.vehicleType = "Personal Vehicle";
        if(passengers.size() <= MAX_PASSENGER_COUNT) {
            this.passengers = passengers;
        }
        else {
            this.passengers = new ArrayList<>();
            for(int i = 0; i < MAX_PASSENGER_COUNT; i++) {
                this.passengers.add(passengers.get(i));
            }
        }
    }

    PersonalVehicle() {
        this.vehicleType = "Personal Vehicle";
        Random rand = new Random();
        this.passengers = RandomData.getRandomPassengers(rand.nextInt(MAX_PASSENGER_COUNT) + 1);
    }

    
}
