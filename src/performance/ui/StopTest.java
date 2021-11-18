package performance.ui;

import java.sql.SQLException;

import org.configuration.SetUp;
import org.selenium.session.DriverPool;

public class StopTest extends Exception {
		
	public StopTest(String message) {
        super(message);
        // TODO Auto-generated constructor stub
    }
	
    public StopTest(Throwable cause) {
        super(cause);
        // TODO Auto-generated constructor stub
    }

    public StopTest(String message, Throwable cause) {
        super(message, cause);
        // TODO Auto-generated constructor stub
    }

    public StopTest(String userid, String stepid, String message, Throwable cause) throws ClassNotFoundException, SQLException {
    	//customize message
    	 super(message, cause);
    }
    
    public StopTest(String userid, String stepid, String message) throws ClassNotFoundException, SQLException {
    	//customize message
    	 super(message);
    	//log db
    	 new Dao().insertDurationTable(userid, stepid, "", message);
    	//quit selenium
    	DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).quit();
    }
}
