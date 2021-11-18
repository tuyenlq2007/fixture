package org.page.driver;

import java.net.URI;
import java.net.URISyntaxException;

import org.api.PageApi;
import org.exception.StopTest;

public class Open extends PageApi {
	private String url;

	public Open(String url) {
		super();
		this.url = url;
	}

	@Override
	public String execute() {
		final String result = "SUCCESS";
		if (!url.contains("://")) {
			String baseURL = driver.getCurrentUrl();
			if (!baseURL.isEmpty() && baseURL.charAt(baseURL.length() - 1) != '/') {
				baseURL += "/";
			}
			try {
				url = new URI(baseURL).resolve(url).toASCIIString();
			} catch (final URISyntaxException e) {
				// log
				// screen shot
				takeScreenShot();
				try {
					throw new StopTest(
							takeScreenShot() + " Invalid URL: baseURL=[" + baseURL + "] / parameter=[" + url + "]", e);
				} catch (final Exception e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}
			}
		}

		driver.navigate().to(url);
		return result;
	}

}