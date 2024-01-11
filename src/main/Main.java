package main;

import vehicles.*;


import java.util.Scanner;

public class Main {


    
    public static void main(String[] args) {

        
        Simulation.populateVehicles();
        long startTime = System.currentTimeMillis();
        for(Vehicle vehicle : Simulation.vehicles) {
            System.out.println(vehicle.getVehicleType() + " " + vehicle.getVehicleID());
            vehicle.start();
        }
        Simulation.ftw.setDaemon(true);
        Simulation.ftw.start();

        for(Vehicle vehicle : Simulation.tmpVehicles) {
            try {
                vehicle.join();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        System.out.println("compute time: " + (System.currentTimeMillis() - startTime));
        System.out.println("Succesful vehicles: " + Simulation.successVehclesCounter);
        System.out.println("Failed vehicles: " + Simulation.failedVehiclesCounter);
        System.out.println("SIMULATION DONE");
        System.out.println("Show incidents yes/no");

        Scanner scan = new Scanner(System.in);
        String input = scan.nextLine();
        if("yes".equals(input)) {
            Simulation.showIncidents();
        }
        else {
            System.out.println("DONE!");
            Simulation.ftw.printStatuses();
        }


        
        




        
        
    }
 
}
