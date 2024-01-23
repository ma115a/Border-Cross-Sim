package incidents;

import java.util.ArrayList;
import java.util.Date;
import java.io.Serializable;
import java.text.SimpleDateFormat;

import passengers.Passenger;
import terminals.Terminal;
import vehicles.Vehicle;
public class Incident implements Serializable {
    
    private String dateAndTime;
    private ArrayList<Passenger> passengers;
    private String terminalName;;
    private String vehicleIcon;
    private String message;
    private boolean isSevere;
    private int vehicleID;

    public Incident() {

    }

    public Incident(ArrayList<Passenger> passengers, String terminalName, String vehicleIcon, int vehicleID, String message, Boolean isSevere) {
        this.passengers = passengers;
        this.terminalName = terminalName;
        this.vehicleIcon = vehicleIcon;
        this.message = message;
        this.isSevere = isSevere;
        this.vehicleID = vehicleID;
        this.dateAndTime = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(new Date());
    }

    public Incident(ArrayList<Passenger> passengers, String terminalName, String vehicleIcon, int vehicleID, String message, Boolean isSevere, String dateAndTime) {
        this.passengers = passengers;
        this.terminalName = terminalName;
        this.vehicleIcon = vehicleIcon;
        this.message = message;
        this.isSevere = isSevere;
        this.vehicleID = vehicleID;
        this.dateAndTime = dateAndTime;
    }

    public String getDateAndTime() {
        return this.dateAndTime;
    }

    public ArrayList<Passenger> getPassengers() {
        return this.passengers;
    }

    public String getTerminalName() {
        return this.terminalName;
    }

    public String getVehicleIconName() {
        return this.vehicleIcon;
    }

    public String getMessage() {
        return this.message;
    }

    public boolean isSevere() {
        return this.isSevere;
    }

    public int getVehicleID() {
        return this.vehicleID;
    }

    public void setDateAndTime() {
        this.dateAndTime = new SimpleDateFormat("dd.MM.yyyy-HH:mm:ss").format(new Date());
    }

    public void setPassengers(ArrayList<Passenger> passengers) {
        this.passengers = passengers;
    }

    public void setTerminal(String terminalName) {
        this.terminalName = terminalName;
    }

    public void setVehicleIcon(String vehicleIcon) {
        this.vehicleIcon = vehicleIcon;
    }

    public String incidentToString() {
        StringBuilder sb = new StringBuilder();
        sb.append(this.isSevere + "#");
        sb.append(this.dateAndTime + "#");
        sb.append(this.vehicleIcon + "#");
        sb.append(this.vehicleID + "#");
        sb.append(this.terminalName + "#");
        sb.append(this.message);
        for(Passenger passenger : this.passengers) {
            sb.append("#" + passenger.getFullName() + " " + passenger.getDocumentId());
        }
        return new String(sb);
    }

    @Override
    public String toString() {
        return "Severe: " + this.isSevere + " Date: " + this.dateAndTime + " VehicleID: " + this.vehicleID + " Terminal: " + this.terminalName + " " + this.message;
    }
}
