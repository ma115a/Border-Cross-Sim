package vehicles;
import java.util.ArrayList;

import main.Simulation;
import passengers.Baggage;
import passengers.Passenger;
// import java.util.List;
public abstract class Vehicle extends Thread {
    
    protected ArrayList<Passenger> passengers;
    protected ArrayList<Baggage> baggages;

    protected String vehicleType;

    protected int vehicleID;
    
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

    public void removePassengers(ArrayList<Passenger> passengers) {
        this.passengers.removeAll(passengers);
    }

    public int getPassengerCount() {
        return this.passengers.size();
    }

    
    // @Override
    // public String toString() {
    //     return this.vehicleType + ": " + "\nPassengers: " + this.passengers + "\nBaggages: " + this.baggages;
    // }

    @Override
    public String toString() {
        return this.vehicleType + " " + this.vehicleID;
    }

    @Override
    public boolean equals(Object obj) {
        if(obj != null && obj.getClass().equals(getClass())) {
            return this.vehicleID == ((Vehicle) obj).vehicleID;
        }
        return false;
    }

    public int getVehicleID() {
        return this.vehicleID;
    }




    
    
    @Override
    public void run() {

        if("Truck".equals(this.vehicleType)) {

            boolean terminalOccupied = false;

            while(!terminalOccupied) {
                if(this.equals(Simulation.vehicles.peek())) {
                    if(Simulation.policeTerminal3.isWorking() && Simulation.policeTerminal3.isAcceptingTrucks() && Simulation.policeTerminal3.isFree()) {
                        Simulation.policeTerminal3.occupy();
                        terminalOccupied = true;
                        if(Simulation.policeTerminal3.process(this)) {
                            System.out.println("Truck" + this.vehicleID + " successfully passed Police Terminal! " + Simulation.policeTerminal3.getTerminalName());
                            while(true) {
                                if(Simulation.customsTerminal3.isAcceptingTrucks() && Simulation.customsTerminal3.isWorking()) {
                                    synchronized(Simulation.customsTerminal3) {
                                        Simulation.policeTerminal3.free();
                                        if(Simulation.customsTerminal3.process(this)) {
                                            System.out.println("Truck" + this.vehicleID + " successfully passed Customs Terminal! " + Simulation.customsTerminal3.getTerminalName());
                                            Simulation.successVehclesCounter++;
                                            break;
                                        }
                                        else {
                                            System.out.println("Truck" + this.vehicleID + " failed to pass Customs Terminal!");
                                            Simulation.failedVehiclesCounter++;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            Simulation.policeTerminal3.free();
                            Simulation.failedVehiclesCounter++;
                            System.out.println("Truck" + this.vehicleID + " failed to pass Police Terminal!");
                        }
                    }
                }
            }
        }
        else if("Personal Vehicle".equals(this.vehicleType)) {
            boolean terminalOccupied = false;

            while(!terminalOccupied) {
                if(this.equals(Simulation.vehicles.peek())) {
                    if(Simulation.policeTerminal1.isWorking() && Simulation.policeTerminal1.isAcceptingPersonalVehicles() && Simulation.policeTerminal1.isFree()) {
                        Simulation.policeTerminal1.occupy();
                        terminalOccupied = true;
                        if(Simulation.policeTerminal1.process(this)) {
                            System.out.println("Personal Vehicle" + this.vehicleID + " succesfully passed Police terminal " + Simulation.policeTerminal1.getTerminalName());
                            while(true) {
                                if(Simulation.customsTerminal1.isAcceptingPersonalVehicles() && Simulation.customsTerminal1.isWorking()) {
                                    synchronized(Simulation.customsTerminal1) {
                                        Simulation.policeTerminal1.free();
                                        if(Simulation.customsTerminal1.process(this)) {
                                            System.out.println("Personal Vehicle" + this.vehicleID + " succesfully passed Customs terminal! " + Simulation.customsTerminal1.getTerminalName());
                                            Simulation.successVehclesCounter++;
                                            break;
                                        }
                                        else {
                                            System.out.println("Personal Vehicle" + this.vehicleID + " failed to pass Customs terminal!");
                                            Simulation.failedVehiclesCounter++;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            Simulation.policeTerminal1.free();
                            Simulation.failedVehiclesCounter++;
                            System.out.println(this.vehicleType + this.vehicleID + " failed to pass Police Terminal " + Simulation.policeTerminal1.getTerminalName());
                        }
                    }
                    else if(Simulation.policeTerminal2.isWorking() && Simulation.policeTerminal2.isAcceptingPersonalVehicles() && Simulation.policeTerminal2.isFree()) {
                        Simulation.policeTerminal2.occupy();
                        terminalOccupied = true;
                        if(Simulation.policeTerminal2.process(this)) {
                            System.out.println("Personal Vehicle" + this.vehicleID + " succesfully passed Police terminal " + Simulation.policeTerminal2.getTerminalName());
                            while(true) {
                                if(Simulation.customsTerminal1.isAcceptingPersonalVehicles() && Simulation.customsTerminal1.isWorking()) {
                                    synchronized(Simulation.customsTerminal1) {
                                        Simulation.policeTerminal2.free();
                                        if(Simulation.customsTerminal1.process(this)) {
                                            System.out.println("Personal Vehicle" + this.vehicleID + " succesfully passed Customs terminal! " + Simulation.customsTerminal1.getTerminalName());
                                            Simulation.successVehclesCounter++;
                                            break;
                                        }
                                        else {
                                            System.out.println("Personal Vehicle" + this.vehicleID + " failed to pass Customs terminal!");
                                            Simulation.failedVehiclesCounter++;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            Simulation.policeTerminal2.free();
                            Simulation.failedVehiclesCounter++;
                            System.out.println(this.vehicleType + this.vehicleID + " failed to pass Police Terminal " + Simulation.policeTerminal2.getTerminalName());
                        }
                    }
                }
            }
        }
        else {
            boolean terminalOccupied = false;

            while(!terminalOccupied) {

                if(this.equals(Simulation.vehicles.peek())) {
                    
                    if(Simulation.policeTerminal1.isWorking() && Simulation.policeTerminal1.isAcceptingBusses() && Simulation.policeTerminal1.isFree()) {
                        Simulation.policeTerminal1.occupy();
                        terminalOccupied = true;

                        if(Simulation.policeTerminal1.process(this)) {
                            System.out.println("Bus" + this.vehicleID + " sucessfuly passed Police terminal " + Simulation.policeTerminal1.getTerminalName());
                            while(true) {
                                if(Simulation.customsTerminal1.isAcceptingBusses() && Simulation.customsTerminal1.isWorking()) {
                                    synchronized(Simulation.customsTerminal1) {
                                        Simulation.policeTerminal1.free();
                                        if(Simulation.customsTerminal1.process(this)) {
                                            System.out.println(this.vehicleType + this.vehicleID + " successfully passed Customs terminal " + Simulation.customsTerminal1.getTerminalName());
                                            Simulation.successVehclesCounter++;
                                            break;
                                        }
                                        else {
                                            System.out.println(this.vehicleType + this.vehicleID + " failed to pass Customs terminal " + Simulation.customsTerminal1.getTerminalName());
                                            Simulation.failedVehiclesCounter++;
                                            break;   
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            Simulation.policeTerminal1.free();
                            System.out.println(this.vehicleType + this.vehicleID + " failed to pass Police terminal " + Simulation.policeTerminal1.getTerminalName());
                        }
                    }
                    else if(Simulation.policeTerminal2.isWorking() && Simulation.policeTerminal2.isAcceptingBusses() && Simulation.policeTerminal2.isFree()) {
                        Simulation.policeTerminal2.occupy();
                        terminalOccupied = true;

                        if(Simulation.policeTerminal2.process(this)) {
                            System.out.println(this.vehicleType + this.vehicleID + " successfully passed Police terminal " + Simulation.policeTerminal2.getTerminalName());
                            while(true) {
                                if(Simulation.customsTerminal1.isAcceptingBusses() && Simulation.customsTerminal1.isWorking()) {
                                    synchronized(Simulation.customsTerminal1) {
                                        Simulation.policeTerminal2.free();
                                        if(Simulation.customsTerminal1.process(this)) {
                                            System.out.println(this.vehicleType + this.vehicleID + " successfully passed Customs terminal " + Simulation.customsTerminal1.getTerminalName());
                                            Simulation.successVehclesCounter++;
                                            break;
                                        }
                                        else {
                                            System.out.println(this.vehicleType + this.vehicleID + " failed to pass Customs terminal " + Simulation.customsTerminal1.getTerminalName());
                                            Simulation.failedVehiclesCounter++;
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            Simulation.policeTerminal2.free();
                            System.out.println(this.vehicleType + this.vehicleID + " failed to pass Police terminal " + Simulation.policeTerminal2.getTerminalName());
                            Simulation.failedVehiclesCounter++;
                        }
                    }
                }
            }
        }

    }
    
}
