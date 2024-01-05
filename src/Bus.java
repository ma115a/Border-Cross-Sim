import java.util.Random;
import java.util.ArrayList;


//class that represents a bus
public class Bus extends Vehicle {

    private static final int MAX_PASSENGER_COUNT = 52;
    private static final double suspiciousItemsInBaggageBound = 0.7;


    public Bus(ArrayList<Passenger> passengers) {
        this.vehicleType = "Bus";
        if (passengers.size() <= MAX_PASSENGER_COUNT) {
            this.passengers = passengers;
        } else {
            this.passengers = new ArrayList<>();
            for (int i = 0; i < MAX_PASSENGER_COUNT; i++) {
                this.passengers.add(passengers.get(i));
            }
        }
        this.baggages = RandomData.getRandomBaggage(this.passengers, suspiciousItemsInBaggageBound);
    }

    public Bus() {
        this.vehicleType = "Bus";
        Random rand = new Random();
        this.passengers = RandomData.getRandomPassengers(rand.nextInt(MAX_PASSENGER_COUNT) + 1);
        this.baggages = RandomData.getRandomBaggage(passengers, suspiciousItemsInBaggageBound); 
    }


    // //method that generates random baggage for passengers, for simplicity
    // private void generateRandomBaggages() {
    //     Random rand = new Random();
    //     this.baggages = new ArrayList<>();
    //     for (int i = 0; i < this.passengers.size(); i++) {
    //         double probability = rand.nextDouble();
    //         if (probability <= suspiciousItemsInBaggageBound) {
    //             this.baggages.add(new Baggage(this.passengers.get(i)));
    //         }

    //     }
    // }

}
