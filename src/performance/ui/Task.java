package performance.ui;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.concurrent.TimeUnit;

class Tasks implements Runnable
{
  String testRun;
   public Tasks(String testRun) {
		// TODO Auto-generated constructor stub
	   this.testRun = testRun;
	}

@Override
   public void run()
   {
      try
      {
         TimeUnit.SECONDS.sleep(2);
         executeTest(this.testRun);
      } catch (InterruptedException | NumberFormatException | IOException e)
      {
         e.printStackTrace();
      }
   }
   
   private boolean executeTest(final String url) throws NumberFormatException, InterruptedException, IOException {
		HttpURLConnection con = (HttpURLConnection) new URL(url).openConnection();
		try {

			con.setRequestMethod("GET");

			StringBuilder content;

			try (BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()))) {

				String line;
				content = new StringBuilder();

				while ((line = in.readLine()) != null) {
					content.append(line);
					content.append(System.lineSeparator());
				}
			}

			System.out.println(content.toString());

		} finally {

			con.disconnect();
		}
		return true;
	}
}