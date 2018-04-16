package swe681;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletResponse;

public class BaseBean {

	//protected static Logger LOGGER = Logger.getLogger("AppLogger");
	
	public BaseBean() {
		setHeaders();
		//configLogger();
	}
	
    public void setHeaders() {
        HttpServletResponse response = (HttpServletResponse)FacesContext.getCurrentInstance().getExternalContext().getResponse();
        response.setHeader("Cache-Control", "no-cache, no-store");
    }
    
//    public void configLogger() {
//        FileHandler fh;  
//
//        try {  
//
//            // This block configure the logger with handler and formatter  
//            fh = new FileHandler("C:\\logfile.log");  
//            LOGGER.addHandler(fh);
//            SimpleFormatter formatter = new SimpleFormatter();  
//            fh.setFormatter(formatter);   
//
//        } catch (SecurityException e) {  
//            e.printStackTrace();  
//        } catch (IOException e) {  
//            e.printStackTrace();  
//        }  
//    }
    
}
