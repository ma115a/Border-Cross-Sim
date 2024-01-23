package utils;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.BufferedWriter;
import java.io.EOFException;
import java.io.FileWriter;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;

import incidents.Incident;
import main.Simulation;
import passengers.*;
public class FileUtil {




    public static synchronized void writeToTextFile(Incident incident) {
        File file = new File("output" + File.separator + "customs_incidents");
        if(!file.exists()) {
            file.mkdirs();
        }

        String fileName = "output" + File.separator + "customs_incidents" + File.separator + Simulation.simulationDateAndTime + ".txt";


        try {
            BufferedWriter bw = new BufferedWriter(new FileWriter(fileName, true));
            bw.write(incident.incidentToString() + "\n");
            bw.close();
        }
        catch (Exception e) {
            Simulation.logger.severe(e.getMessage());
        }


    }

    public static Incident getIncidentFromLine(String line) {
        String[] data = line.split("#");
        Boolean isSevere = Boolean.parseBoolean(data[0]);
        String dateAndTime = data[1];
        String iconName = data[2];
        int vehicleID = Integer.parseInt(data[3]);
        String terminalName = data[4];
        String message = data[5];
        ArrayList<Passenger> passengers = new ArrayList<>();
        for(int i = 6; i < data.length; i++) {
            passengers.add(new Passenger(data[i].split(" ")[0], data[i].split(" ")[1],Integer.parseInt(data[i].split(" ")[2]), false));
        }

        return new Incident(passengers, terminalName, iconName, vehicleID, message, isSevere, dateAndTime);

    }

    public static synchronized ArrayList<Incident> readIncidentsFromTextFile() {
        File filePath = new File("output" + File.separator + "customs_incidents" + File.separator + Simulation.simulationDateAndTime + ".txt");
        if(!filePath.exists()) {
            return null;
        }
        ArrayList<Incident> incidents = new ArrayList<>();
        try {
            BufferedReader br = new BufferedReader(new FileReader(filePath));
            String line;
            while((line = br.readLine()) != null) {

                incidents.add(getIncidentFromLine(line));
            }
        } catch (Exception e) {
            Simulation.logger.severe(e.getMessage());
        }
        return incidents;
    }


    public static synchronized void writeToBinaryFile(Incident incident) {
        File file = new File("output" + File.separator + "police incidents");
        if(!file.exists()) {
            file.mkdirs();
        }

        File filePath = new File("output" + File.separator + "police incidents" + File.separator + Simulation.simulationDateAndTime + ".ser");
        if(!filePath.exists()) {
            try {
                ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(filePath));
                oos.writeObject(incident);
                oos.close();
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }
        }
        else {

            try {
                AppendableObjectOutputStream aoos = new AppendableObjectOutputStream(new FileOutputStream(filePath, true));
                aoos.writeObject(incident);
                aoos.close();
                
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }  

        }

    }


    public static synchronized ArrayList<Incident> readFromBinaryFile() {
        File filePath = new File("output" + File.separator + "police incidents" + File.separator + Simulation.simulationDateAndTime + ".ser");
        if(!filePath.exists()) {
            return null;
        }
        ArrayList<Incident> incidents = new ArrayList<>();
        try {

            ObjectInputStream ois = new ObjectInputStream(new FileInputStream(filePath));

            while(true) {
                try {
                    Incident incident = (Incident) ois.readObject();
                    incidents.add(incident);
                } catch (EOFException e) {
                    break;
                }
            }
            ois.close();

        } catch (Exception e) {
            Simulation.logger.severe(e.getMessage());
        }



        return incidents;

    }
    
}
