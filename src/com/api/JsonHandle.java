package com.api;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.flipkart.zjsonpatch.JsonDiff;
import com.google.gson.JsonElement;
import com.google.gson.JsonParser;
import com.jayway.jsonpath.DocumentContext;
import com.jayway.jsonpath.JsonPath;

public class JsonHandle {

	public String ExtractContent(String json, String property) throws Exception {
		final String value = JsonPath.parse(json).read(property).toString();
		return value;
	}

	public void ExtractContent(String json, String property, String tofileName) throws Exception {
		toFile(JsonPath.parse(json).read(property).toString(), tofileName);
	}

	public void ExtractContentToFile(String json, String property, String tofileName) throws Exception {
		toFile(JsonPath.parse(json).read(property).toString(), tofileName);
	}

	public String ExtractJsonElementFromFile(String filename, String jsonPath) throws Exception {
		return JsonPath.parse(getFile(filename)).read(jsonPath).toString();
	}

	public void ExtractJsonElementFromFile(String filename, String jsonPath, String tofileName) throws Exception {
		toFile(JsonPath.parse(getFile(filename)).read(jsonPath).toString(), tofileName);
	}

	public void ExtractJsonElementFromFileToFile(String filename, String jsonPath, String tofileName) throws Exception {
		toFile(JsonPath.parse(getFile(filename)).read(jsonPath).toString(), tofileName);
	}
	
	public String ExtractArrayElementFromFile(String filename, String jsonPath) throws Exception {
		 String result = "";
	     Matcher m = Pattern.compile("\\[([^)]+)\\]").matcher(JsonPath.parse(getFile(filename)).read(jsonPath).toString());
	     while(m.find()) {
	    	 result = m.group(1);    
	     }
		return result.replace("\"", "");
	}

	public String getDiff(String jsonSource, String jsonTarget) throws JsonProcessingException, IOException{
		ObjectMapper jackson = new ObjectMapper(); 
    	JsonNode beforeNode = jackson.readTree(jsonSource); 
    	JsonNode afterNode = jackson.readTree(jsonTarget); 
    	return JsonDiff.asJson(beforeNode, afterNode).toString(); 
	}

	public String getDiffFromFiles(String fileNameSource, String fileNameTarget) throws JsonProcessingException, IOException{
		ObjectMapper jackson = new ObjectMapper(); 
    	JsonNode beforeNode = jackson.readTree(getFile(fileNameSource)); 
    	JsonNode afterNode = jackson.readTree(getFile(fileNameTarget)); 
    	return JsonDiff.asJson(beforeNode, afterNode).toString(); 
	}
	
	public boolean compareJsonFileWithFile(String fileNameSource, String fileNameTarget) throws IOException {
		return getJsonObjectFromFile(fileNameSource).equals(getJsonObjectFromFile(fileNameTarget));
	}

	public boolean compareJsonWithJson(String jsonSource, String jsonTarget) throws IOException {
		return getJsonObjectFromString(jsonSource).equals(getJsonObjectFromString(jsonTarget));
	}

	private JsonElement getJsonObjectFromString(String theString) throws IOException {
		JsonParser parser = new JsonParser();
		return parser.parse(theString);
	}

	private JsonElement getJsonObjectFromFile(String fileName) throws IOException {
		JsonParser parser = new JsonParser();
		return parser.parse(getFile(fileName));
	}

	public String getJsonFile(String fileName) throws IOException {
		JsonParser parser = new JsonParser();
		JsonElement jsonTree = parser.parse(getFile(fileName));
		return jsonTree.getAsJsonObject().toString();
	}

	public String getFile(String filename) throws IOException {
		@SuppressWarnings("resource")
		final BufferedReader br = new BufferedReader(new FileReader(new File(System.getenv("ITS") + File.separator
				+ "FitNesseRoot" + File.separator + "files" + File.separator + filename)));
		String line;
		final StringBuilder sb = new StringBuilder();

		while ((line = br.readLine()) != null) {
			sb.append(line.trim());
		}
		return sb.toString();
	}

	public void toFile(String sContent, String fileName) throws IOException {
		String filePath = System.getenv("ITS") + File.separator
				+ "FitNesseRoot" + File.separator + "files" + File.separator + fileName;
		try{
			File directory = new File(filePath.substring(0, filePath.lastIndexOf(File.separator)));
			if (!directory.exists()){
				directory.mkdirs();
		    }
		}catch (Exception e){
			
		}
		
		final BufferedWriter writer = new BufferedWriter(new FileWriter(filePath));
		writer.write(sContent);
		writer.close();

	}

	public String updateJsonFromFileToDoc(String fileName, String jsonPath, String key, String newvalue)
			throws IOException {
		DocumentContext updateDoc = JsonPath.parse(getJsonFile(fileName)).put(jsonPath, key, newvalue);
		return updateDoc.jsonString().toString();
	}

	public void updateJsonFromFileToFile(String fromfileName, String jsonPath, String key, String newvalue,
			String tofileName) throws IOException {
		DocumentContext updateDoc = JsonPath.parse(getJsonFile(fromfileName)).put(jsonPath, key, newvalue);
		toFile(updateDoc.jsonString().toString(), tofileName);
	}

	public List<String> dataIndex(String filename, String delimeter, String search) throws IOException {
		List<String> index = new ArrayList<>();
		String output = getFile(filename);
		List<String> result = Arrays.asList(output.split(delimeter));

		for (int i = 0; i < result.size(); i++) {
			if (result.get(i).contains(search)) {
				index.add(Integer.toString(i));
			}
		}
		return index;
	}
}
