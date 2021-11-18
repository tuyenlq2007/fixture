package performance.ui;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.List;

import com.api.ListUtility;

public class Execute {
	public List doTable(List<List<String>> table) throws InterruptedException, FileNotFoundException, IOException {
		final List rollResults = ListUtility.list("","");
		final List display = ListUtility.list();

		
		new FileOutputStream("C:\\ITS\\logging.log").close();		
		for (int ithread = 1; ithread <= Integer.parseInt(table.get(0).get(1)); ithread++){
			//display.add(rollResults);
			//RunnableDemo object = new RunnableDemo(table.get(0).get(0) + "?test&thread=" +ithread); 
            //object.start(); 
			run(table.get(0).get(0) + "?test&thread=" +ithread);
            //Thread.sleep(1000);
		}
		 		
		return display;
	}
	
	private void run(String url) {
		int retry = 0;
		boolean delay = false;
		HttpURLConnection con = null;
		do {
			
			try {
				if (delay) {
		            Thread.sleep(1000);
		        }
				con = (HttpURLConnection) new URL(url).openConnection();
				con.setRequestMethod("GET");
				switch (con.getResponseCode()) {
				case HttpURLConnection.HTTP_OK:				
					StringBuilder content;
					try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {
						String line;
						content = new StringBuilder();
						while ((line = in.readLine()) != null) {
							content.append(line);
							content.append(System.lineSeparator());
						}
					}
					retry = 5;
					break;
				case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
					break;// retry
				case HttpURLConnection.HTTP_UNAVAILABLE:
					break;// retry, server is unstable
				default:
					break; // abort
				}
				delay = true;
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} finally {
				con.disconnect();
			}
		} while (retry < 5);
	}
}
