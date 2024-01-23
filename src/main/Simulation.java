package main;

import terminals.CustomsTerminal;
import terminals.PoliceTerminal;
import utils.*;
import vehicles.Bus;
import vehicles.PersonalVehicle;
import vehicles.Truck;
import vehicles.Vehicle;
import incidents.Incident;

import java.util.ArrayList;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.Collections;

import java.util.logging.Logger;

import java.text.SimpleDateFormat;
import java.util.Date;

public class Simulation {



    public static ArrayBlockingQueue<Vehicle> vehicles = new ArrayBlockingQueue<>(50);
    public static ArrayList<Vehicle> tmpVehicles = new ArrayList<>();

    public static PoliceTerminal policeTerminal3 = new PoliceTerminal("Police 3", true, false, true, false);
    public static CustomsTerminal customsTerminal3 = new CustomsTerminal("Customs 3", true, false, true, false);

    public static PoliceTerminal policeTerminal1 = new PoliceTerminal("Police 1", true, true, false, true);
    public static CustomsTerminal customsTerminal1 = new CustomsTerminal("Customs 1", true, true, false, true);

    public static PoliceTerminal policeTerminal2 = new PoliceTerminal("Police 2", true, true, false, true);

    public static String simulationDateAndTime = new SimpleDateFormat("yyyy_MM_dd_HH:mm:ss").format(new Date());
    public static Logger logger = CustomLogger.getCustomLogger();
    public static ArrayList<Incident> incidents = new ArrayList<>();

    public static TerminalFileStalker ftw = new TerminalFileStalker();

    public static ArrayBlockingQueue<Vehicle> onCustomsVehicles = new ArrayBlockingQueue<>(2);

    public static Object pauseObject = new Object();

    public static int successVehclesCounter = 0;
    public static int failedVehiclesCounter = 0;

    public static void populateVehicles() {
        for(int i = 0; i < 35; i++) {   
            tmpVehicles.add(new PersonalVehicle());
        }

        for(int i = 0; i < 10; i++) {
            tmpVehicles.add(new Truck());
        }

        for(int i = 0; i < 5; i++) {
            tmpVehicles.add(new Bus());
        }


        Collections.shuffle(tmpVehicles);
        vehicles.addAll(tmpVehicles);
    }

    public static void showIncidents() {
        for(Incident incident : incidents) {
            System.out.println(incident);
        }
    }

    public static void simulationRun() {
        // populateVehicles();
        for(Vehicle vehicle : vehicles) {
            System.out.println(vehicle.getClass().getName() + " " + vehicle.getVehicleID());
            vehicle.setDaemon(true);
            vehicle.start();
        }
        
        ftw.setDaemon(true);
        ftw.start();
    }
    
}
