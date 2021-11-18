
package com.page;

import java.util.ArrayList;
import java.util.List;

import org.configuration.SetUp;
import org.exception.StopTest;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.selenium.session.DriverPool;

import com.api.ListUtility;

public class FixtureVerifyPageElements {
	public List doTable(List<List<String>> table) {
		List<List<List<String>>> getResults = new ArrayList<List<List<String>>>();
		List rollResults = ListUtility.list("pass1.1", "pass1.2");	
		  for (int row = 0; row < table.size(); row++) {		
			  List elementResults = ListUtility.list("", "");	
			  elementResults.set(0, "");
			  elementResults.set(1, evaluateElement(table.get(row).get(0)) );			 
			  getResults.add(elementResults);
			  }	
		return getResults;
	}

	private String evaluateElement(String elementEvaluated) {
		return findElement(elementEvaluated) ? "" : elementEvaluated;
	}



public boolean findElement(String xpathExpression) {
	return (DriverPool.INSTANCE.getDriver(SetUp.getSetup().getDriver()).findElements(By.xpath(xpathExpression)).size()!=0);
}

}