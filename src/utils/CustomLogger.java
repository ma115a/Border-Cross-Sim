package utils;

import java.io.File;
import java.util.logging.*;
import main.Simulation;

public class CustomLogger {
    

    public static Logger getCustomLogger() {
        Logger logger = Logger.getLogger(Simulation.class.getName());

        File logDirectory = new File("log");
        if(!logDirectory.exists()) logDirectory.mkdir();

        try {
            FileHandler fh = new FileHandler("log" + File.separator + Simulation.simulationDateAndTime + ".log");
            SimpleFormatter sf = new SimpleFormatter();
            fh.setFormatter(sf);
            logger.addHandler(fh);
        } catch (Exception e) {
            e.printStackTrace();
        }

        
        return logger;
    }
    
    
}
