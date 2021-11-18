package com.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.mime.MultipartEntityBuilder;

import com.fasterxml.jackson.core.JsonProcessingException;

import performance.ui.StopTest;

public abstract class Requests {
	public String ExtractContent(String json, String property) throws Exception {
		return (new JsonHandle()).ExtractContent(json, property);
	}

	public void ExtractContent(String json, String property, String tofileName) throws Exception {
		(new JsonHandle()).ExtractContent(json, property, tofileName);
	}

	public void ExtractContentToFile(String json, String property, String tofileName) throws Exception {
		(new JsonHandle()).ExtractContentToFile(json, property, tofileName);
	}

	public String ExtractJsonElementFromFile(String filename, String jsonPath) throws Exception {
		return (new JsonHandle()).ExtractJsonElementFromFile(filename, jsonPath);
	}

	public void ExtractJsonElementFromFile(String filename, String jsonPath, String tofileName) throws Exception {
		(new JsonHandle()).ExtractJsonElementFromFile(filename, jsonPath, tofileName);
	}

	public void ExtractJsonElementFromFileToFile(String filename, String jsonPath, String tofileName) throws Exception {
		(new JsonHandle()).ExtractJsonElementFromFileToFile(filename, jsonPath, tofileName);
	}

	public String ExtractArrayElementFromFile(String filename, String jsonPath) throws Exception {
		return (new JsonHandle()).ExtractArrayElementFromFile(filename, jsonPath);
	}
	
	public String getDiff(String jsonSource, String jsonTarget) throws JsonProcessingException, IOException {
		return (new JsonHandle()).getDiff(jsonSource, jsonTarget);
	}

	public String getDiffFromFiles(String fileNameSource, String fileNameTarget)
			throws JsonProcessingException, IOException {
		return (new JsonHandle()).getDiffFromFiles(fileNameSource, fileNameTarget);
	}

	public boolean compareJsonFileWithFile(String fileNameSource, String fileNameTarget) throws IOException {
		return (new JsonHandle()).compareJsonFileWithFile(fileNameSource, fileNameTarget);
	}

	public boolean compareJsonWithJson(String jsonSource, String jsonTarget) throws IOException {
		return (new JsonHandle()).compareJsonWithJson(jsonSource, jsonTarget);
	}

	public String updateJsonFromFileToDoc(String fileName, String jsonPath, String key, String newvalue)
			throws IOException {
		return (new JsonHandle()).updateJsonFromFileToDoc(fileName, jsonPath, key, newvalue);
	}

	public void updateJsonFromFileToFile(String fromfileName, String jsonPath, String key, String newvalue,
			String tofileName) throws IOException {
		(new JsonHandle()).updateJsonFromFileToFile(fromfileName, jsonPath, key, newvalue, tofileName);
	}
	
	public String getIndex(String filename, String delimeter, String search) throws IOException {
		return (new JsonHandle()).dataIndex(filename, delimeter, search).get(0);
	}
	
	protected HttpRequestBase addHeaderParamsToHttpRequest(HttpRequestBase httpRequest , String headerParams)
	{
		List<String> theHeaderParams = new ArrayList<>(Arrays.asList(headerParams.split(",")));
		int index = 0;
		while (index < theHeaderParams.size() - 1) {
			httpRequest.setHeader(theHeaderParams.get(index).trim(), theHeaderParams.get(index + 1).trim());
			index = index + 2;
		}
		return httpRequest;	
	}
	
	protected HttpUriRequest addHeaderParamsToHttpUriRequest(HttpUriRequest httpRequest , String headerParams)
	{
		List<String> theHeaderParams = new ArrayList<>(Arrays.asList(headerParams.split(",")));
		int index = 0;
		while (index < theHeaderParams.size() - 1) {
			httpRequest.setHeader(theHeaderParams.get(index).trim(), theHeaderParams.get(index + 1).trim());
			index = index + 2;
		}
		return httpRequest;	
	}
	
	protected MultipartEntityBuilder addBinaryBodyToMultipartdata(MultipartEntityBuilder multipartdata, String binaryBodyParams){
		List<String> theBinaryBodyParams = new ArrayList<>(Arrays.asList(binaryBodyParams.split(",")));
		int index = 0;
		while (index < theBinaryBodyParams.size() - 1) {
			File file = new File(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
					+ File.separator + theBinaryBodyParams.get(index + 1).trim());
			multipartdata.addBinaryBody(theBinaryBodyParams.get(index).trim(), file, ContentType.DEFAULT_BINARY,
					file.getName());
			index = index + 2;
		}
		return multipartdata;
	}
	
	protected MultipartEntityBuilder addTextBodyToMultipartdata(MultipartEntityBuilder multipartdata, String textBodyParams) throws IOException{
		List<String> theTextBodyParams = new ArrayList<>(Arrays.asList(textBodyParams.split(",")));
		int index = 0;
		while (index < theTextBodyParams.size() - 1) {
			multipartdata.addTextBody(theTextBodyParams.get(index),
					(new JsonHandle()).getFile(theTextBodyParams.get(index + 1).trim()), ContentType.DEFAULT_BINARY);
			index = index + 2;
		}
		return multipartdata;
	}
	
	public boolean stringComparison(String firstString, String regx) {
	    return firstString.matches(regx);
	  }
	
	public boolean pause(final String miliseconds) {
		if (StringUtils.isBlank(miliseconds))
			return true;
		try {
			Thread.sleep(Long.parseLong(miliseconds));
		} catch (NumberFormatException e) {
			return true;
		} catch (InterruptedException e) {
			return true;
		}
		return true;

	}

}
