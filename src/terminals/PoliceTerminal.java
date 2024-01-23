package terminals;
import java.util.ArrayList;

import main.Simulation;
import passengers.Passenger;
import vehicles.*;
import incidents.Incident;
import utils.FileUtil;
public class PoliceTerminal extends Terminal {

    private static final int processTime = 500;
    private static final int processTimeBus = 100;
    


    public PoliceTerminal(String name, boolean isWorking, boolean isAcceptingPersonalVehicles, boolean isAcceptingTrucks, boolean isAcceptingBusses) {
        super(name, isWorking, isAcceptingPersonalVehicles, isAcceptingTrucks, isAcceptingBusses);
    }


    // private ArrayList<Passenger> checkPassengers(ArrayList<Passenger> passengers) {
    //     ArrayList<Passenger> badPassengers = new ArrayList<>();
    //     for(Passenger passenger : passengers) {
    //         if(!passenger.isDocumentValid()) {
    //             badPassengers.add(passenger);
    //         }
    //     }
    //     if(badPassengers.size() > 0) Simulation.incidents.add(new Incident(badPassengers, this));
    //     return badPassengers;
    // }


    private boolean checkPassengers(Vehicle vehicle) {
        ArrayList<Passenger> badPassengers = new ArrayList<>();
        for(Passenger passenger : vehicle.getPassengers()) {
            if(!passenger.isDocumentValid()) {
                badPassengers.add(passenger);
            }
        }
        if(badPassengers.size() > 0) {
            // Incident incident = new Incident(badPassengers, this, vehicle);
            // FileUtil.writeToTextFile(incident);
            // Simulation.incidents.add(incident);
            if (badPassengers.contains(vehicle.getPassengers().getFirst())) {
                FileUtil.writeToBinaryFile(new Incident(badPassengers, this.getTerminalName(), vehicle.getIconName(), vehicle.getVehicleID(), "Police problem", true));
                return false;
            }
            else {
                FileUtil.writeToBinaryFile(new Incident(badPassengers, this.getTerminalName(), vehicle.getIconName(), vehicle.getVehicleID(), "Police problem", false));

                vehicle.removePassengers(badPassengers);
            }
        }
        return true;
    }

    // public synchronized boolean process(Truck vehicle) {


    //     Simulation.vehicles.poll();
    //     try {
            
    //         Thread.sleep(PoliceTerminal.processTime * vehicle.getPassengerCount());
    //     }
    //     catch(Exception e) {
    //         Simulation.logger.severe(e.getMessage());
    //     }
    //     return this.checkPassengers(vehicle);
    // }

    // public synchronized boolean process(PersonalVehicle vehicle) {

    //     Simulation.vehicles.poll();

    //     try {
            
    //         Thread.sleep(PoliceTerminal.processTime * vehicle.getPassengerCount());

    //     } catch (Exception e) {
    //         Simulation.logger.severe(e.getMessage());
    //     }

    //     return this.checkPassengers(vehicle);
    // }

    // public synchronized boolean process(Bus vehicle) {


    //     Simulation.vehicles.poll();

    //     try {

    //         Thread.sleep(PoliceTerminal.processTimeBus * vehicle.getPassengerCount());

    //     } catch (Exception e) {
    //         Simulation.logger.severe(e.getMessage());
    //     }
    //     return this.checkPassengers(vehicle);
        
    // }


    public synchronized boolean process(Vehicle vehicle) {
        Simulation.vehicles.poll();
        if(vehicle instanceof IPersonalVehicle) {
            try {
                Thread.sleep(PoliceTerminal.processTime * vehicle.getPassengerCount());
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }
        } else if(vehicle instanceof ITruck) {
            try {
                Thread.sleep(PoliceTerminal.processTime * vehicle.getPassengerCount());
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }
        }
        else {
            try {
                Thread.sleep(PoliceTerminal.processTimeBus * vehicle.getPassengerCount());
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }
        }
        return this.checkPassengers(vehicle);
    }

}
