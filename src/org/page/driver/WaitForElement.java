package org.page.driver;

import org.api.PageApi;
import org.exception.StopTest;

public class WaitForElement extends PageApi {
	private String theTimeout;

	public WaitForElement(String object_repository, String theTimeout) {
		super(object_repository);
		this.theTimeout = theTimeout;
	}

	@Override
	public String execute() throws StopTest {
		boolean wrong = true;
		int iTimeout = Integer.parseInt(theTimeout);
		do
		{
			try
			{
				Thread.sleep(3000);
				validateElement();
				wrong = false;

			}
			catch (Exception e)
			{
				if (iTimeout - 3000 > 0)
				{				
					iTimeout = iTimeout - 3000;
				}
				else
				{
					wrong = false;
				}
			}

		} while (wrong);

		try {
			validateElement();
		} catch (final Exception e) {
			throw new StopTest(takeScreenShot(), e);
		}

		return "SUCCESS";
	}
}
