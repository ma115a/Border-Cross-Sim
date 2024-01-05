import java.util.ArrayList;
// import java.util.List;
public abstract class Vehicle extends Thread {
    
    protected ArrayList<Passenger> passengers;
    protected ArrayList<Baggage> baggages;

    protected String vehicleType;

    
    public String getVehicleType() {
        return this.vehicleType;
    }
    
    public ArrayList<Passenger> getPassengers() {
        return this.passengers;
    }

    public ArrayList<Baggage> getBaggages() {
        return this.baggages;
    }
    
    public Passenger getDriver() {
        return this.passengers.get(0);
    }

    public void removePassenger(Passenger passenger) {
        this.passengers.remove(passenger);
    }

    
    @Override
    public String toString() {
        return this.vehicleType + ": " + "\nPassengers: " + this.passengers + "\nBaggages: " + this.baggages;
    }


    
    
    @Override
    public void run() {
        System.out.println("I am a " + this.vehicleType + " trying to cross the border!");
        
    }
    
}
