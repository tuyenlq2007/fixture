package performance.ui;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

public class LogClass {
	   private static Logger logger = Logger.getLogger(LogClass.class);
	   
	   public void trace (String info) {     
	   //public static void main(String[] args) {
		    PropertyConfigurator.configure("C:\\ITS\\resources\\log4j.properties");
	        logger.info(info);        
	   }
}
