package com.api;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

public class Sending {
	private final List<List<List<String>>> getContent;

	public Sending() throws Exception {
		String expectedresult = "";
		getContent = new ArrayList<List<List<String>>>();
		HashMap<String, String> processed = getMap(System.getenv("ITS") + "/lmdb.dat");
		HashMap<String, String> sending = getMap(System.getenv("ITS") + "/request.dat");
		HashMap<String, String> attach = getMap(System.getenv("ITS") + "/attach.dat");

		Iterator it = sending.entrySet().iterator();
		while (it.hasNext()) {
			Map.Entry pair = (Map.Entry) it.next();
			if (!processed.containsKey(pair.getKey())) {
				getContent.add(Arrays.asList(
						Arrays.asList("id", pair.getKey().toString()),
						Arrays.asList("request", getvalue(sending,pair.getKey().toString())),
						Arrays.asList("attach", getvalue(attach,pair.getKey().toString()))
								));
			}
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