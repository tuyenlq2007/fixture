package com.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class Status {
	private final List<List<List<String>>> getContent;

	public Status(String rows) throws Exception {
		int row = 0;
		String expectedresult = "";
		getContent = new ArrayList<List<List<String>>>();
		HashMap<String, String> processed = getMap(System.getenv("ITS") + "/lmdb.dat");
		HashMap<String, String> sending = getMap(System.getenv("ITS") + "/request.dat");
		HashMap<String, String> attach = getMap(System.getenv("ITS") + "/attach.dat");
		HashMap<String, String> expect = getMap(System.getenv("ITS") + "/expect.dat");

		Iterator it = processed.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			row++;
			getContent.add(Arrays.asList(
					Arrays.asList("TestCaseID", new Integer(row).toString()),
					Arrays.asList("Request ID", pair.getKey().toString()),
					Arrays.asList("Request Content", getFile(getvalue(sending, pair.getKey().toString()))),
					Arrays.asList("Attachment Content", getvalue(attach, pair.getKey().toString())),
					Arrays.asList("Status", "PROCESSED")
					));
		}

		Iterator its = sending.entrySet().iterator();
		while (its.hasNext()) {			
			Map.Entry pair = (Map.Entry) its.next();
			if (!processed.containsKey(pair.getKey())) {
				row++;
				getContent.add(Arrays.asList(
						Arrays.asList("TestCaseID", new Integer(row).toString()),
						Arrays.asList("Request ID", pair.getKey().toString()),
						Arrays.asList("Request Content", getFile(getvalue(sending, pair.getKey().toString()))),
						Arrays.asList("Attachment Content", getvalue(attach, pair.getKey().toString())),
						Arrays.asList("Status", "PENDING")
						));
			}
		}
		
		while (row < Integer.parseInt(rows)) {
			row++;
			getContent.add(Arrays.asList(
					Arrays.asList("TestCaseID", new Integer(row).toString()), 
					Arrays.asList("Request ID", ""),
					Arrays.asList("Request Content", ""), 
					Arrays.asList("Attachment Content", ""), 
					Arrays.asList("Status", "")
					));
		}

	}

	private String getFile(String file) throws FileNotFoundException{
		return new Scanner(new File(file)).useDelimiter("\\Z").next();	
	}
	
	private String getxml(String text, String tag){
		String content = text.replace("=\r", "").replace("\n", "").replace("=3D", "=");
		return content.substring(content.indexOf("<" + tag), content.indexOf("</" + tag +">")+tag.length()+3);
		
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