package vehicles;
import java.util.ArrayList;

import main.Simulation;
import passengers.Baggage;
import passengers.Passenger;
import javafx.application.Platform;
import javafx.scene.image.ImageView;
import main.Main;
public abstract class Vehicle extends Thread {
    
    protected ArrayList<Passenger> passengers;
    protected ArrayList<Baggage> baggages;

    protected String vehicleType;

    protected int vehicleID;

    protected ImageView icon;

    protected String iconName;
    
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

    public ImageView getIcon() {
        return this.icon;
    }

    public String getIconName() {
        return this.iconName;
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

    public void checkSimulationPause() {
        synchronized(Simulation.pauseObject) {
            if(Main.simulationPaused) {
                try {
                    Simulation.pauseObject.wait();
                } catch (Exception e) {
                    Simulation.logger.severe(e.getMessage());
                }
    
            }
        }
    }




    
    
    @Override
    public void run() {

        if(this instanceof ITruck) {

            boolean terminalOccupied = false;

            while(!terminalOccupied) {
                if(this.equals(Simulation.vehicles.peek())) {
                    if(Simulation.policeTerminal3.isWorking() && Simulation.policeTerminal3.isAcceptingTrucks() && Simulation.policeTerminal3.isFree()) {
                        Simulation.policeTerminal3.occupy();
                        terminalOccupied = true;
                        this.checkSimulationPause();
                        Platform.runLater(() -> Main.setPoliceTerminal3Vehicle(this.icon));
                        if(Simulation.policeTerminal3.process(this)) {
                            System.out.println(this.getClass().getName() + this.vehicleID + " successfully passed Police Terminal! " + Simulation.policeTerminal3.getTerminalName());
                            while(true) {
                                if(Simulation.customsTerminal3.isAcceptingTrucks() && Simulation.customsTerminal3.isWorking()) {
                                    synchronized(Simulation.customsTerminal3) {
                                        Simulation.policeTerminal3.free();
                                        Platform.runLater(() -> Main.setPoliceTerminal3Vehicle(null));
                                        Platform.runLater(() -> Main.setCustomsTerminal2Vehicle(this.icon));
                                        this.checkSimulationPause();
                                        if(Simulation.customsTerminal3.process(this)) {
                                            System.out.println(this.getClass().getName() + this.vehicleID + " successfully passed Customs Terminal! " + Simulation.customsTerminal3.getTerminalName());
                                            Simulation.successVehclesCounter++;
                                            Platform.runLater(() -> Main.setCustomsTerminal2Vehicle(null));
                                            break;
                                        }
                                        else {
                                            System.out.println(this.getClass().getName() + this.vehicleID + " failed to pass Customs Terminal!");
                                            Simulation.failedVehiclesCounter++;
                                            Platform.runLater(() -> Main.setCustomsTerminal2Vehicle(null));
                                            break;
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            Platform.runLater(() -> Main.setPoliceTerminal3Vehicle(null));
                            Simulation.policeTerminal3.free();
                            Simulation.failedVehiclesCounter++;
                            System.out.println(this.getClass().getName() + this.vehicleID + " failed to pass Police Terminal!");
                        }
                    }
                }
            }
        }
        else if(this instanceof IPersonalVehicle) {
            boolean terminalOccupied = false;

            while(!terminalOccupied) {
                if(this.equals(Simulation.vehicles.peek())) {
                    if(Simulation.policeTerminal1.isWorking() && Simulation.policeTerminal1.isAcceptingPersonalVehicles() && Simulation.policeTerminal1.isFree()) {
                        Simulation.policeTerminal1.occupy();
                        terminalOccupied = true;
                        this.checkSimulationPause();
                        Platform.runLater(() -> Main.setPoliceTerminal1Vehicle(this.icon));
                        if(Simulation.policeTerminal1.process(this)) {
                            System.out.println(this.getClass().getName() + this.vehicleID + " succesfully passed Police terminal " + Simulation.policeTerminal1.getTerminalName());
                            Simulation.onCustomsVehicles.add(this);
                            while(true) {
                                if(this.equals(Simulation.onCustomsVehicles.peek())) {
                                    if(Simulation.customsTerminal1.isAcceptingPersonalVehicles() && Simulation.customsTerminal1.isWorking()) {
                                        synchronized(Simulation.customsTerminal1) {
                                            Simulation.onCustomsVehicles.poll();
                                            Simulation.policeTerminal1.free();
                                            Platform.runLater(() -> Main.setPoliceTerminal1Vehicle(null));
                                            Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(this.icon));
                                            this.checkSimulationPause();
                                            if(Simulation.customsTerminal1.process(this)) {
                                                System.out.println(this.getClass().getName() + this.vehicleID + " succesfully passed Customs terminal! " + Simulation.customsTerminal1.getTerminalName());
                                                Simulation.successVehclesCounter++;
                                                Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(null));
                                                break;
                                            }
                                            else {
                                                System.out.println(this.getClass().getName() + this.vehicleID + " failed to pass Customs terminal!");
                                                Simulation.failedVehiclesCounter++;
                                                Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(null));
                                                break;
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        else {
                            Platform.runLater(() -> Main.setPoliceTerminal1Vehicle(null));
                            Simulation.policeTerminal1.free();
                            Simulation.failedVehiclesCounter++;
                            System.out.println(this.getClass().getName() + this.vehicleID + " failed to pass Police Terminal " + Simulation.policeTerminal1.getTerminalName());
                        }
                    }
                    else if(Simulation.policeTerminal2.isWorking() && Simulation.policeTerminal2.isAcceptingPersonalVehicles() && Simulation.policeTerminal2.isFree()) {
                        Simulation.policeTerminal2.occupy();
                        terminalOccupied = true;
                        this.checkSimulationPause();
                        Platform.runLater(() -> Main.setPoliceTerminal2Vehicle(this.icon));
                        if(Simulation.policeTerminal2.process(this)) {
                            System.out.println(this.getClass().getName() + this.vehicleID + " succesfully passed Police terminal " + Simulation.policeTerminal2.getTerminalName());
                            Simulation.onCustomsVehicles.add(this);
                            while(true) {
                                if(this.equals(Simulation.onCustomsVehicles.peek())) {
                                    if(Simulation.customsTerminal1.isAcceptingPersonalVehicles() && Simulation.customsTerminal1.isWorking()) {
                                        synchronized(Simulation.customsTerminal1) {
                                            Simulation.onCustomsVehicles.poll();
                                            Simulation.policeTerminal2.free();
                                            Platform.runLater(() -> Main.setPoliceTerminal2Vehicle(null));
                                            Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(this.icon));
                                            this.checkSimulationPause();
                                            if(Simulation.customsTerminal1.process(this)) {
                                                System.out.println(this.getClass().getName() + this.vehicleID + " succesfully passed Customs terminal! " + Simulation.customsTerminal1.getTerminalName());
                                                Simulation.successVehclesCounter++;
                                                Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(null));
                                                break;
                                            }
                                            else {
                                                System.out.println(this.getClass().getName() + this.vehicleID + " failed to pass Customs terminal!");
                                                Simulation.failedVehiclesCounter++;
                                                Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(null));
                                                break;
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        else {
                            Platform.runLater(() -> Main.setPoliceTerminal2Vehicle(null));
                            Simulation.policeTerminal2.free();
                            Simulation.failedVehiclesCounter++;
                            System.out.println(this.getClass().getName() + this.vehicleID + " failed to pass Police Terminal " + Simulation.policeTerminal2.getTerminalName());
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
                        this.checkSimulationPause();
                        Platform.runLater(() -> Main.setPoliceTerminal1Vehicle(this.icon));
                        if(Simulation.policeTerminal1.process(this)) {
                            System.out.println(this.getClass().getName() + this.vehicleID + " sucessfuly passed Police terminal " + Simulation.policeTerminal1.getTerminalName());
                            Simulation.onCustomsVehicles.add(this);
                            while(true) {
                                if(this.equals(Simulation.onCustomsVehicles.peek())) {
                                    if(Simulation.customsTerminal1.isAcceptingBusses() && Simulation.customsTerminal1.isWorking()) {
                                        synchronized(Simulation.customsTerminal1) {
                                            Simulation.onCustomsVehicles.poll();
                                            Simulation.policeTerminal1.free();
                                            Platform.runLater(() -> Main.setPoliceTerminal1Vehicle(null));
                                            Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(this.icon));
                                            this.checkSimulationPause();
                                            if(Simulation.customsTerminal1.process(this)) {
                                                System.out.println(this.getClass().getName() + this.vehicleID + " successfully passed Customs terminal " + Simulation.customsTerminal1.getTerminalName());
                                                Simulation.successVehclesCounter++;
                                                Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(null));
                                                break;
                                            }
                                            else {
                                                System.out.println(this.getClass().getName() + this.vehicleID + " failed to pass Customs terminal " + Simulation.customsTerminal1.getTerminalName());
                                                Simulation.failedVehiclesCounter++;
                                                Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(null));
                                                break;   
                                            }
                                        }
                                    }

                                }
                            }
                        }
                        else {
                            Simulation.policeTerminal1.free();
                            Platform.runLater(() -> Main.setPoliceTerminal1Vehicle(null));
                            System.out.println(this.getClass().getName() + this.vehicleID + " failed to pass Police terminal " + Simulation.policeTerminal1.getTerminalName());
                        }
                    }
                    else if(Simulation.policeTerminal2.isWorking() && Simulation.policeTerminal2.isAcceptingBusses() && Simulation.policeTerminal2.isFree()) {
                        Simulation.policeTerminal2.occupy();
                        terminalOccupied = true;
                        this.checkSimulationPause();
                        Platform.runLater(() -> Main.setPoliceTerminal2Vehicle(this.icon));
                        if(Simulation.policeTerminal2.process(this)) {
                            System.out.println(this.getClass().getName() + this.vehicleID + " successfully passed Police terminal " + Simulation.policeTerminal2.getTerminalName());
                            Simulation.onCustomsVehicles.add(this);
                            while(true) {
                                if(this.equals(Simulation.onCustomsVehicles.peek())) {
                                    if(Simulation.customsTerminal1.isAcceptingBusses() && Simulation.customsTerminal1.isWorking()) {
                                        synchronized(Simulation.customsTerminal1) {
                                            Simulation.onCustomsVehicles.poll();
                                            Simulation.policeTerminal2.free();
                                            Platform.runLater(() -> Main.setPoliceTerminal2Vehicle(null));
                                            Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(this.icon));
                                            this.checkSimulationPause();
                                            if(Simulation.customsTerminal1.process(this)) {
                                                System.out.println(this.getClass().getName() + this.vehicleID + " successfully passed Customs terminal " + Simulation.customsTerminal1.getTerminalName());
                                                Simulation.successVehclesCounter++;
                                                Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(null));
                                                break;
                                            }
                                            else {
                                                System.out.println(this.getClass().getName() + this.vehicleID + " failed to pass Customs terminal " + Simulation.customsTerminal1.getTerminalName());
                                                Simulation.failedVehiclesCounter++;
                                                Platform.runLater(() -> Main.setCustomsTerminal1Vehicle(null));
                                                break;
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        else {
                            Simulation.policeTerminal2.free();
                            Platform.runLater(() -> Main.setPoliceTerminal2Vehicle(null));
                            System.out.println(this.getClass().getName() + this.vehicleID + " failed to pass Police terminal " + Simulation.policeTerminal2.getTerminalName());
                            Simulation.failedVehiclesCounter++;
                        }
                    }
                }
            }
        }

    }
    
}
