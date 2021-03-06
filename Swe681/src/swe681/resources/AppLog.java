package swe681.resources;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppLog {
	
	private final String LOGFILE = "C:\\logfile.log";

	private static volatile Logger LOGGER;
	
	public synchronized static Logger getLogger() {
		if(LOGGER == null) {
			LOGGER = Logger.getLogger("AppLogger");
			//configLogger();
		}
		return LOGGER;
	}
	
	//TODO: set log file if you want to store logs on server, this is turned off for testing purposes here
    private void configLogger() {
        FileHandler fh;  

        try { 
            // This block configure the logger with handler and formatter  
            fh = new FileHandler(LOGFILE);  
            LOGGER.addHandler(fh);
            SimpleFormatter formatter = new SimpleFormatter();  
            fh.setFormatter(formatter);   

        } catch (SecurityException e) {  
            e.printStackTrace();  
        } catch (IOException e) {  
            e.printStackTrace();  
        }  
    }
}
