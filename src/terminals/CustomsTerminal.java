package terminals;
import java.util.ArrayList;

import main.Simulation;
import passengers.*;
import utils.FileUtil;
import vehicles.*;
import incidents.Incident;
public class CustomsTerminal extends Terminal {

    // public static final int processTime = 2_000;
    private static final int passengerProcessTime = 500;
    private static final int passengerProcessTimeBus = 100;

    public CustomsTerminal(String name, boolean isWorking, boolean isAcceptingPersonalVehicles, boolean isAcceptingTrucks, boolean isAcceptingBusses) {
        super(name, isWorking, isAcceptingPersonalVehicles, isAcceptingTrucks, isAcceptingBusses);

    }



    public boolean checkBaggage(Vehicle vehicle) {
        ArrayList<Passenger> badPassengers = new ArrayList<>();
        for(Baggage baggage : vehicle.getBaggages()) {
            if(baggage.containsSuspiciousItems()) {
                badPassengers.add(baggage.getOwner());
            }
        }

        if(badPassengers.size() > 0) {
            // Simulation.incidents.add(new Incident(badPassengers, this, vehicle));
            if(badPassengers.contains(vehicle.getPassengers().getFirst())) {
                FileUtil.writeToTextFile(new Incident(badPassengers, this.getTerminalName(), vehicle.getIconName(), vehicle.getVehicleID(), "Baggage problem", true));
                return false;
            }
            else {
                FileUtil.writeToTextFile(new Incident(badPassengers, this.getTerminalName(), vehicle.getIconName(), vehicle.getVehicleID(), "Baggage problem", false));

                vehicle.removePassengers(badPassengers);
                System.out.println("Passengers removed!");
            }
        }
        return true;
    }

    // public boolean process(Truck vehicle) {

    //     // System.out.println("Truck" + vehicle.getVehicleID() + " is on customs terminal!");

    //     try {
    //         for(int i = 0; i < vehicle.getPassengerCount(); i++) {
    //             Thread.sleep(passengerProcessTime);
    //         }
    //     } catch (Exception e) {
    //         Simulation.logger.severe(e.getMessage());
    //     }


    //     //checking for documentation
    //     if(vehicle.needsDocumentation()) {
    //         try {
    //             Thread.sleep(1_000);
    //             vehicle.setDocumentation(true);
    //             System.out.println("Truck" + vehicle.getVehicleID() + " documentation generated!");
    //         } catch (Exception e) {
    //             Simulation.logger.severe(e.getMessage());
    //         }
    //     }


    //     //check for mass
    //     if(vehicle.getRealMass() > vehicle.getDeclaredMass()) {
    //         System.out.println("Truck" + vehicle.getVehicleID() + " is overweight!");
    //         return false;
    //     }
    //     return true;
    // }

    // public boolean process(PersonalVehicle vehicle) {

    //     try {
    //         Thread.sleep(2_000);
    //     } catch (Exception e) {
    //         Simulation.logger.severe(e.getMessage());
    //     }
    //     return true;
    // }

    // public boolean process(Bus vehicle) {

    //     try {
            
    //         Thread.sleep(vehicle.getPassengerCount() * CustomsTerminal.passengerProcessTimeBus);

    //     } catch (Exception e) {
    //         Simulation.logger.severe(e.getMessage());
    //     }

    //     return this.checkBaggage(vehicle);
    // }
    
    public boolean process(Vehicle vehicle) {
        if(vehicle instanceof ITruck) {
            try {
                Thread.sleep(passengerProcessTime * vehicle.getPassengerCount());
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }

            if(((Truck) vehicle).needsDocumentation()) {
                try {
                    Thread.sleep(1_000);
                } catch (Exception e) {
                    Simulation.logger.severe(e.getMessage());
                }

                ((Truck) vehicle).setDocumentation(true);
                System.out.println("Documentation generated!");
            }

            if(((Truck) vehicle).getRealMass() > ((Truck) vehicle).getDeclaredMass()) {
                FileUtil.writeToTextFile(new Incident(vehicle.getPassengers(), this.getTerminalName(), vehicle.getIconName(), vehicle.getVehicleID(), "Overweight problem", true));
                System.out.println("Truck" + vehicle.getVehicleID() + " is overweight!");
                return false;
            }

            return true;
        }
        else if(vehicle instanceof IPersonalVehicle) {
            try {
                Thread.sleep(2_000);
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }
            return true;
        }
        else {
            try {
                Thread.sleep(passengerProcessTimeBus * vehicle.getPassengerCount());
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }

            return this.checkBaggage(vehicle);
        }
    }
}
