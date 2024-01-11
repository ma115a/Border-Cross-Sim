package utils;


import java.io.File;

import incidents.Incident;
public class FileUtil {




    public static synchronized void writeToTextFile(Incident incident) {
        File file = new File("output" + File.separator + "police incidents");
        if(!file.exists()) {
            file.mkdirs();
        }


    }
    
}
