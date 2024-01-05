import java.util.Random;


//class that represents Passengers baggage
public class Baggage {


    private static final double suspiciousItemsBound = 0.1;
    
    private boolean containsSuspiciousItems;
    private Passenger owner;


    public Baggage(Passenger owner, boolean containsSuspiciousItems) {
        this.owner = owner;
        this.containsSuspiciousItems = containsSuspiciousItems;
    }

    public Baggage(Passenger owner) {
        this.owner = owner;
        this.containsSuspiciousItems = false;
        Random rand = new Random();
        double probability = rand.nextDouble();
        if(probability <= suspiciousItemsBound) {
            this.containsSuspiciousItems = true;
        }
    }

    public Baggage() {
        this.containsSuspiciousItems = false;
        Random rand = new Random();
        double probability = rand.nextDouble();
        if(probability <= suspiciousItemsBound) {
            this.containsSuspiciousItems = true;
        }
    }
    
    //returns baggage owner
    public Passenger getOwner() {
        return this.owner;
    }


    //returns true if baggage has any suspicious items in it
    public boolean containsSuspiciousItems() {
        return this.containsSuspiciousItems;
    }

    @Override
    public String toString() {
        return "Baggage, Owner: " + this.owner + " SuspiciousItems: " + this.containsSuspiciousItems;
    } 



}
