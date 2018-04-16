package swe681.resources;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

public class AppLog {

	private static Logger LOGGER;
	
	public static Logger getLogger() {
		if(LOGGER == null) {
			LOGGER = Logger.getLogger("AppLogger");
			//configLogger();
		}
		return LOGGER;
	}
	
    private void configLogger() {
        FileHandler fh;  

        try {  

            // This block configure the logger with handler and formatter  
            fh = new FileHandler("C:\\logfile.log");  
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
