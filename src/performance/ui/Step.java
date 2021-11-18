package performance.ui;

import java.sql.SQLException;
import java.util.List;

import org.exception.StopTest;

import com.api.ListUtility;
import com.page.FixtureFillPage;

public class Step {
	public List doTable(List<List<String>> table)
			throws InterruptedException, ClassNotFoundException, StopTest, SQLException {
		final List rollResults = ListUtility.list("", "");
		final List display = ListUtility.list();
		try {

			long startTime = System.currentTimeMillis();
			for (int row = 2; row < table.size(); row++) {
				//Web actionOnWeb = new Web();
				FixtureFillPage actionOnWeb = new FixtureFillPage();
				switch (table.get(row).get(0).toUpperCase()) {
				case "OPEN":
					actionOnWeb.open(table.get(row).get(1));
					break;
				case "CLICK":
					actionOnWeb.click(table.get(row).get(1));
					break;
				case "TYPE":
					actionOnWeb.typeIn(table.get(row).get(1), table.get(row).get(3));
					break;
				case "I OPEN":
					actionOnWeb.open(table.get(row).get(1));
					break;
				case "I CLICK AT":
					actionOnWeb.click(table.get(row).get(1));
					break;
				case "I INPUT":
					actionOnWeb.IInputInto(table.get(row).get(1), table.get(row).get(3));
					break;
				case "WAIT FOR":
					actionOnWeb.WaitForAsync(table.get(row).get(1), table.get(row).get(3));
					break;
				case "I SELECT":
					actionOnWeb.ISelectByText(table.get(row).get(1), table.get(row).get(3));
					break;
				case "I CLOSE":
					actionOnWeb.IClose();
					break;
				default:
					// code block
				}
			}
			long endTime = System.currentTimeMillis();
			new LogClass().trace(" - Test step: " + table.get(0).get(1) + " - Thread: " + table.get(1).get(1)
					+ " - Execution time: " + (endTime - startTime) + "ms");
		} catch (Exception e) {
			new LogClass().trace("Exception - Test step: " + table.get(0).get(1) + " - Thread: " + table.get(1).get(1) + " - " + e.getMessage() + " - " + e.getCause());
			throw (e);
		}
		return display;
	}
}
