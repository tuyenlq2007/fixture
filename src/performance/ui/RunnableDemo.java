package performance.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class RunnableDemo extends Thread {
	String url = "";

	public RunnableDemo(String inUrl) {
		// TODO Auto-generated constructor stub
		url = inUrl;
	}

	public void run() {
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

	private HttpURLConnection getConnection(URL entries) throws InterruptedException, IOException {
		int retry = 0;
		do {
			HttpURLConnection connection = (HttpURLConnection) entries.openConnection();
			switch (connection.getResponseCode()) {
			case HttpURLConnection.HTTP_OK:
				return connection; // **EXIT POINT** fine, go on
			case HttpURLConnection.HTTP_GATEWAY_TIMEOUT:
				break;// retry
			case HttpURLConnection.HTTP_UNAVAILABLE:
				break;// retry, server is unstable
			default:
				break; // abort
			}
			// we did not succeed with connection (or we would have returned the
			// connection).
			connection.disconnect();
			// retry
			retry++;

		} while (retry < 5);
		return null;

	}

}