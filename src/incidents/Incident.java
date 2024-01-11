package incidents;

import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;

import passengers.Passenger;
import terminals.Terminal;
import vehicles.Vehicle;
public class Incident {
    

    private String dateAndTime;
    private ArrayList<Passenger> passengers;
    private Terminal terminal;
    private Vehicle vehicle;


    public Incident() {

    }

    public Incident(ArrayList<Passenger> passengers, Terminal terminal, Vehicle vehicle) {
        this.passengers = passengers;
        this.terminal = terminal;
        this.vehicle = vehicle;
        this.dateAndTime = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(new Date());
    }

    public String getDateAndTime() {
        return this.dateAndTime;
    }

    public ArrayList<Passenger> getPassengers() {
        return this.passengers;
    }

    public Terminal getTerminal() {
        return this.terminal;
    }

    public Vehicle getVehicle() {
        return this.vehicle;
    }

    public void setDateAndTime() {
        this.dateAndTime = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(new Date());
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void setTerminal(Terminal terminal) {
        this.terminal = terminal;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    @Override
    public String toString() {
        return "Passengers: " + this.passengers + ", terminal: " + this.terminal + ", date: " + this.dateAndTime + ",vehicle: " + this.vehicle; 
    }
}
