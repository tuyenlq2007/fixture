package com.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class PollingByRequestId {
	private final List<List<List<String>>> getContent;

	public PollingByRequestId(String RequestId, String pollingTime, String timeout) throws Exception {
		getContent = new ArrayList<List<List<String>>>();
		HashMap<String, String> processed = getMap(System.getenv("ITS") + File.separator + "lmdb.dat");
		HashMap<String, String> environment = getMap(System.getenv("ITS") + File.separator + "environment.dat");
		
		Integer iTimeout = Integer.parseInt(timeout) ;
		Integer iPollingTime = Integer.parseInt(pollingTime);
		while (!processed.containsKey(RequestId) && (iTimeout > 0)) {
			iTimeout = iTimeout - iPollingTime;
			Thread.sleep(iPollingTime);	
			processed = getMap(System.getenv("ITS") + File.separator + "lmdb.dat");
		}

		if (processed.containsKey(RequestId)) {
			getContent.add(Arrays.asList(Arrays.asList("Environment/Endpoint", getvalue(environment, RequestId)),
					Arrays.asList("TestCaseID", ""),
					Arrays.asList("Request ID", RequestId), 
					Arrays.asList("Request Content", ""),
					Arrays.asList("Attachment Content", ""), 
					Arrays.asList("Expected Response", ""),
					Arrays.asList("Actual Response", ""), 
					Arrays.asList("Status", ""),
					Arrays.asList("Response Times (ms)", ""),
					Arrays.asList("Comparison of Results", "")
					));
		}else
		{
			getContent.add(Arrays.asList(Arrays.asList("Environment/Endpoint", getvalue(environment, RequestId)),
					Arrays.asList("TestCaseID", ""),
					Arrays.asList("Request ID", ""), 
					Arrays.asList("Request Content", ""),
					Arrays.asList("Attachment Content", ""), 
					Arrays.asList("Expected Response", ""),
					Arrays.asList("Actual Response", ""), 
					Arrays.asList("Status", ""),
					Arrays.asList("Response Times (ms)", ""),
					Arrays.asList("Comparison of Results", "")
					));
		}
	}

	private String getvalue(HashMap<String, String> map, String key) {
		String value = map.get(key);
		return value == null ? "" : value;
	}


	private HashMap<String, String> getMap(String db) throws IOException, ClassNotFoundException {
		final File file = new File(db);
		FileInputStream f = new FileInputStream(file);
		final ObjectInputStream input = new ObjectInputStream(f);
		// Reads the first object in
		final Object readObject = input.readObject();
		input.close();
		return (HashMap<String, String>) readObject;
	}

	public List<List<List<String>>> query() {
		return getContent;
	}
}