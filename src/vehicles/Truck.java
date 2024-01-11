package vehicles;
import java.util.ArrayList;
import java.util.Random;

import passengers.Passenger;
import utils.RandomData;
public class Truck extends Vehicle {

    private static final int MAX_PASSENGER_COUNT = 3;
    private static final double massOverloadProbabilityBound = 0.2;
    private static final double maxMassOverloadBound = 0.3;
    private int declaredMass;
    private int realMass;


    private boolean needsDocumentation;

    private boolean hasDocumentation;

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
        this.vehicleID = rand.nextInt(1_000_000);
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
