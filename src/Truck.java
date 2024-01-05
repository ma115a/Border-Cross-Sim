import java.util.ArrayList;
import java.util.Random;
public class Truck extends Vehicle {

    private static final int MAX_PASSENGER_COUNT = 3;
    private static final double massOverloadProbabilityBound = 0.2;
    private int declaredMass;
    private int realMass;


    private boolean needsDocumentation;

    public Truck(ArrayList<Passenger> passengers) {
        this.vehicleType = "Truck";
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
    }

    public Truck() {
        this.vehicleType = "Truck";
        Random rand = new Random();
        this.passengers = RandomData.getRandomPassengers(rand.nextInt(MAX_PASSENGER_COUNT) + 1);
        this.generateMasses();
        this.needsDocumentation = rand.nextBoolean();
    }


    public boolean isOverweight() {
        if(this.realMass > this.declaredMass) return true;
        return false;
    }
    
    private void generateMasses() {
        Random rand = new Random();
        double probability = rand.nextDouble();
        this.declaredMass = RandomData.getRandomPermissibleMass();
        if(probability <= massOverloadProbabilityBound) {
            double multiplyBy = rand.nextDouble(0.3) + 1;
            this.realMass = (int) (this.declaredMass * multiplyBy);
        }
        else {
            this.realMass = rand.nextInt(this.declaredMass);
        }
    }

    @Override
    public String toString() {
        return super.toString() + "\nDeclared mass: " + this.declaredMass + " Real mass: " + this.realMass + " Needs documentation : " + this.needsDocumentation;
    }

    
}
