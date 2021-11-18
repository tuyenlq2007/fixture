package com.api;

import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPatch;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;

import com.cedarsoftware.util.io.JsonWriter;

public class RestRequests extends Requests {

	public void execute(String url) throws Exception {
		new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(new HttpGet(url + "?test")));
	}
	
	public String Get(String url) throws Exception {
		return JsonWriter.formatJson(new BasicResponseHandler()
				.handleResponse(HttpClientBuilder.create().build().execute(new HttpGet(url))));
	}

	public void Get(String url, String toFile) throws Exception {
		(new JsonHandle()).toFile(JsonWriter.formatJson(new BasicResponseHandler()
				.handleResponse(HttpClientBuilder.create().build().execute(new HttpGet(url)))), toFile);
	}

	public void Get(String url, String headerParams, String toFile) throws Exception {
		(new JsonHandle()).toFile(JsonWriter.formatJson(new BasicResponseHandler().handleResponse(HttpClientBuilder
				.create().build().execute(addHeaderParamsToHttpRequest(new HttpGet(url), headerParams)))), toFile);
	}

	public void Patch(String url, String fromBodyFileName, String toFile) throws Exception {
		final HttpPatch patch = new HttpPatch(url);
		patch.setEntity(new StringEntity(new JsonHandle().getFile(fromBodyFileName)));
		(new JsonHandle()).toFile(
				JsonWriter.formatJson(
						new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(patch))),
				toFile);
	}

	public void Patch(String url, String headerParams, String fromBodyFileName, String toFile) throws Exception {
		final HttpPatch patch = (HttpPatch) addHeaderParamsToHttpRequest(new HttpPatch(url), headerParams);
		patch.setEntity(new StringEntity(new JsonHandle().getFile(fromBodyFileName)));
		(new JsonHandle()).toFile(
				JsonWriter.formatJson(
						new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(patch))),
				toFile);
	}

	public void Post(String url, String headerParams, String fromBodyFileName, String toFile) throws Exception {
		final HttpPost post = (HttpPost) addHeaderParamsToHttpRequest(new HttpPost(url), headerParams);
		post.setEntity(new StringEntity(new JsonHandle().getFile(fromBodyFileName)));
		(new JsonHandle()).toFile(
				JsonWriter.formatJson(
						new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(post))),
				toFile);
	}

	public void Post(String url, String fromBodyFileName, String toFile) throws Exception {
		final HttpPost post = new HttpPost(url);
		post.setEntity(new StringEntity(new JsonHandle().getFile(fromBodyFileName)));
		(new JsonHandle()).toFile(
				JsonWriter.formatJson(
						new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(post))),
				toFile);
	}

	public void Put(String url, String headerParams, String fromBodyFileName, String toFile) throws Exception {
		final HttpPut put = (HttpPut) addHeaderParamsToHttpRequest(new HttpPut(url), headerParams);
		put.setEntity(new StringEntity(new JsonHandle().getFile(fromBodyFileName)));
		(new JsonHandle()).toFile(
				JsonWriter.formatJson(
						new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(put))),
				toFile);
	}

	public void Put(String url, String fromBodyFileName, String toFile) throws Exception {
		final HttpPut put = new HttpPut(url);
		put.setEntity(new StringEntity(new JsonHandle().getFile(fromBodyFileName)));
		(new JsonHandle()).toFile(
				JsonWriter.formatJson(
						new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(put))),
				toFile);
	}
}
