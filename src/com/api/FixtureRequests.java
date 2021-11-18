package com.api;

import org.apache.http.HttpResponse;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.cedarsoftware.util.io.JsonWriter;

public class FixtureRequests extends Requests{

	public FixtureRequests() {
	}

	public String Get(String url) throws Exception {
		final HttpGet get = new HttpGet(url);

		final HttpResponse response = HttpClientBuilder.create().build().execute(get);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		final String niceFormattedJson = JsonWriter.formatJson(responseBody);

		return niceFormattedJson;
	}

	public String Get(String url, String toFile) throws Exception {
		final HttpGet get = new HttpGet(url);

		final HttpResponse response = HttpClientBuilder.create().build().execute(get);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		final String niceFormattedJson = JsonWriter.formatJson(responseBody);
		(new JsonHandle()).toFile(niceFormattedJson, toFile);
		return niceFormattedJson;
	}

	public String Get(String url, String headerUserName, String userName, String headerProfileId, String profileId,
			String headerContentType, String contentType) throws Exception {
		final HttpGet get = new HttpGet(url);
		if (!headerProfileId.isEmpty())
			get.setHeader(headerProfileId, profileId);
		if (!headerContentType.isEmpty())
			get.setHeader(headerContentType, contentType);
		if (!headerUserName.isEmpty())
			get.setHeader(headerUserName, userName);
		final HttpResponse response = HttpClientBuilder.create().build().execute(get);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		final String niceFormattedJson = JsonWriter.formatJson(responseBody);

		return niceFormattedJson;
	}

	public String GetToFile(String url, String headerUserName, String userName, String headerProfileId,
			String profileId, String headerContentType, String contentType, String toFile) throws Exception {
		final HttpGet get = new HttpGet(url);
		if (!headerProfileId.isEmpty())
			get.setHeader(headerProfileId, profileId);
		if (!headerContentType.isEmpty())
			get.setHeader(headerContentType, contentType);
		if (!headerUserName.isEmpty())
			get.setHeader(headerUserName, userName);
		final HttpResponse response = HttpClientBuilder.create().build().execute(get);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		final String niceFormattedJson = JsonWriter.formatJson(responseBody);
		(new JsonHandle()).toFile(niceFormattedJson, toFile);
		return niceFormattedJson;
	}

	public String GetToFile(String url, String toFile) throws Exception {
		final HttpGet get = new HttpGet(url);
		;
		final HttpResponse response = HttpClientBuilder.create().build().execute(get);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		final String niceFormattedJson = JsonWriter.formatJson(responseBody);
		(new JsonHandle()).toFile(niceFormattedJson, toFile);
		return niceFormattedJson;
	}

	public String Patch(String url, String headerUserName, String userName, String headerProfileId, String profileId,
			String headerContentType, String contentType, String body) throws Exception {
		final HttpPatch patch = new HttpPatch(url);
		// add header
		if (!headerProfileId.isEmpty())
			patch.setHeader(headerProfileId, profileId);
		if (!headerUserName.isEmpty())
			patch.setHeader(headerUserName, userName);
		if (!headerContentType.isEmpty())
			patch.setHeader(headerContentType, contentType);
		final StringEntity strEntity = new StringEntity(body);
		patch.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(patch);

		final int responseCode = response.getStatusLine().getStatusCode();
		return String.valueOf(responseCode);

	}

	public String Patch(String url, String body) throws Exception {
		final HttpPatch patch = new HttpPatch(url);
		// add header
		final StringEntity strEntity = new StringEntity(body);
		patch.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(patch);

		final int responseCode = response.getStatusLine().getStatusCode();
		return String.valueOf(responseCode);

	}

	public String Post(String url, String headerUserName, String userName, String headerProfileId, String profileId,
			String headerContentType, String contentType, String body) throws Exception {
		final HttpPost post = new HttpPost(url);
		// add header
		if (!headerProfileId.isEmpty())
			post.setHeader(headerProfileId, profileId);
		if (!headerUserName.isEmpty())
			post.setHeader(headerUserName, userName);
		if (!headerContentType.isEmpty())
			post.setHeader(headerContentType, contentType);
		final StringEntity strEntity = new StringEntity(body);
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		final String niceFormattedJson = JsonWriter.formatJson(responseBody);
		return niceFormattedJson;
	}

	public String Post(String url, String body) throws Exception {
		final HttpPost post = new HttpPost(url);
		// add header
		final StringEntity strEntity = new StringEntity(body);
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		final String niceFormattedJson = JsonWriter.formatJson(responseBody);
		return niceFormattedJson;
	}

	public String PostWithDataFile(String url, String headerUserName, String userName, String headerProfileId,
			String profileId, String headerContentType, String contentType, String fileName) throws Exception {
		final HttpPost post = new HttpPost(url);
		// add header
		if (!headerProfileId.isEmpty())
			post.setHeader(headerProfileId, profileId);
		if (!headerUserName.isEmpty())
			post.setHeader(headerUserName, userName);
		if (!headerContentType.isEmpty())
			post.setHeader(headerContentType, contentType);
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fileName));
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		return JsonWriter.formatJson(responseBody);
	}

	public String PostWithDataFile(String url, String fileName) throws Exception {
		final HttpPost post = new HttpPost(url);
		// add header
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fileName));
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		return JsonWriter.formatJson(responseBody);
	}

	public String PostWithTextFile(String url, String headerUserName, String userName, String headerProfileId,
			String profileId, String headerContentType, String contentType, String fileName) throws Exception {
		final HttpPost post = new HttpPost(url);
		// add header
		if (!headerProfileId.isEmpty())
			post.setHeader(headerProfileId, profileId);
		if (!headerUserName.isEmpty())
			post.setHeader(headerUserName, userName);
		if (!headerContentType.isEmpty())
			post.setHeader(headerContentType, contentType);
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fileName));
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		return JsonWriter.formatJson(responseBody);
	}

	public String PostWithTextFile(String url, String fileName) throws Exception {
		final HttpPost post = new HttpPost(url);
		// add header
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fileName));
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		return JsonWriter.formatJson(responseBody);
	}

	public String PostWithTextFileToFile(String url, String fromFileName, String toFile) throws Exception {
		final HttpPost post = new HttpPost(url);
		// add header
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fromFileName));
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		(new JsonHandle()).toFile(JsonWriter.formatJson(responseBody), toFile);
		return JsonWriter.formatJson(responseBody);
	}

	

}
