package utils;


import java.io.File;
import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.BufferedReader;
import java.io.FileReader;
import java.util.HashMap;
import java.util.Map;

import main.Simulation;


public class TerminalFileStalker extends Thread {

    private static final String filename = "terminal controller" + File.separator + "control.txt";

    private Map<String, Boolean> statuses;


    
    public TerminalFileStalker() {

        this.statuses = new HashMap<>();
        
        File dir = new File("terminal controller");
        if(!dir.exists()) {
            dir.mkdir();
        }

        File file = new File(filename);
        if(file.exists() && file.isFile()) {
            System.out.println("EXISTS");

            try {
                BufferedReader br = new BufferedReader(new FileReader(filename));
                String line;
                while((line = br.readLine()) != null) {
                    this.statuses.put(line.split(":")[0], Boolean.parseBoolean(line.split(":")[1]));
                }
                br.close();
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }

        } 
        else {

            try {
                BufferedWriter bw = new BufferedWriter(new FileWriter(filename));
                for(int i = 0; i < 3; i++) {
                    bw.write("P" + (i + 1) + ":true\n");
                    this.statuses.put("P" + (i + 1), true);
                }
                for(int i = 0; i < 2; i++) {
                    bw.write("C" + (i + 1) + ":true\n");
                    this.statuses.put("C" + (i + 1), true);
                }

                bw.close();
    
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }
        }
    }

    private void readFromFile() {
        try {
            BufferedReader br = new BufferedReader(new FileReader(filename));
            String line;
            while((line = br.readLine()) != null) {
                this.statuses.put(line.split(":")[0], Boolean.parseBoolean(line.split(":")[1]));
            }
            br.close();
        } catch (Exception e) {
            Simulation.logger.severe(e.getMessage());
        }
     }


    public void printStatuses() {
        System.out.println(this.statuses.entrySet());
    }

    private void updateTerminals() {
        Simulation.policeTerminal1.setWorking(this.statuses.get("P1"));
        Simulation.policeTerminal2.setWorking(this.statuses.get("P2"));
        Simulation.policeTerminal3.setWorking(this.statuses.get("P3"));
        Simulation.customsTerminal1.setWorking(this.statuses.get("C1"));
        Simulation.customsTerminal3.setWorking(this.statuses.get("C2"));
    }


    @Override
    public void run() {
        while(true) {

            this.readFromFile();
            this.updateTerminals();
            // this.printStatuses();
            try {
                sleep(1000);
            } catch (Exception e) {
                Simulation.logger.severe(e.getMessage());
            }

        }
    }

    
}
