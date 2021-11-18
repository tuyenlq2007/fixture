package com.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.HttpResponseException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.entity.mime.HttpMultipartMode;
import org.apache.http.entity.mime.MultipartEntityBuilder;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import com.cedarsoftware.util.io.JsonWriter;

public class RequestsWithAuthentication extends Requests {

	public String postBasicAuthentication(String url, String headerParams, String userName, String passWord)
			throws ClientProtocolException, IOException {
		final HttpPost httppost = new HttpPost(url);
		List<String> theHeaderParams = new ArrayList<>(Arrays.asList(headerParams.split(",")));
		ArrayList<NameValuePair> postParameters = new ArrayList<>();
		int index = 0;
		while (index < theHeaderParams.size() - 1) {
			postParameters.add(
					new BasicNameValuePair(theHeaderParams.get(index).trim(), theHeaderParams.get(index + 1).trim()));
			index = index + 2;
		}
		httppost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
		CredentialsProvider provider = new BasicCredentialsProvider();
		provider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(userName, passWord));
		return JsonWriter.formatJson(new BasicResponseHandler().handleResponse(
				HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build().execute(httppost)));

	}

	public void get(String url, String headerParams, String toResponseFile) throws Exception {
		(new JsonHandle()).toFile(JsonWriter.formatJson(new BasicResponseHandler().handleResponse(HttpClientBuilder
				.create().build().execute(addHeaderParamsToHttpRequest(new HttpGet(url), headerParams)))), toResponseFile);

	}

	public String get(String url, String headerParams)
			throws HttpResponseException, ClientProtocolException, IOException {
		return JsonWriter.formatJson(new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build()
				.execute(addHeaderParamsToHttpRequest(new HttpGet(url), headerParams))));
	}

	public String post(String url, String headerParams, String fromBodyFileName) throws Exception {
		final HttpPost post = (HttpPost) addHeaderParamsToHttpRequest(new HttpPost(url), headerParams);
		post.setEntity(new StringEntity((new JsonHandle()).getFile(fromBodyFileName)));
		return JsonWriter.formatJson(
				new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(post)));
	}

	public void post(String url, String headerParams, String fromBodyFileName, String toResponseFile) throws Exception {
		final HttpPost post = (HttpPost) addHeaderParamsToHttpRequest(new HttpPost(url), headerParams);
		post.setEntity(new StringEntity((new JsonHandle()).getFile(fromBodyFileName)));
		(new JsonHandle()).toFile(
				JsonWriter.formatJson(
						new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(post))),
				toResponseFile);
	}

	public void delete(String url, String headerParams) throws Exception {
		final HttpDelete delete = (HttpDelete) addHeaderParamsToHttpRequest(new HttpDelete(url), headerParams);
		HttpClientBuilder.create().build().execute(delete);
	}
	
	public void postattachment(String url, String headerParams, String binaryBodyParams, String textBodyParams,
			String toResponseFile) throws Exception {
		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {
			MultipartEntityBuilder multipartdata = MultipartEntityBuilder.create()
					.setMode(HttpMultipartMode.BROWSER_COMPATIBLE);

			MultipartEntityBuilder theUpdatedMultipartdata = addTextBodyToMultipartdata(
					addBinaryBodyToMultipartdata(multipartdata, binaryBodyParams), textBodyParams);

			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			(new JsonHandle()).toFile(JsonWriter.formatJson(httpclient.execute(
					addHeaderParamsToHttpUriRequest(
							RequestBuilder.post(url).setEntity(theUpdatedMultipartdata.build()).build(), headerParams),
					responseHandler)), toResponseFile);
		}
	}

	public String put(String url, String headerParams, String fromBodyFileName) throws Exception {
		final HttpPut put = (HttpPut) addHeaderParamsToHttpRequest(new HttpPut(url), headerParams);
		put.setEntity(new StringEntity((new JsonHandle()).getFile(fromBodyFileName)));
		return JsonWriter
				.formatJson(new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(put)));
	}

	public void put(String url, String headerParams, String fromBodyFileName, String toResponseFile) throws Exception {
		final HttpPut put = (HttpPut) addHeaderParamsToHttpRequest(new HttpPut(url), headerParams);
		put.setEntity(new StringEntity((new JsonHandle()).getFile(fromBodyFileName)));
		put.setEntity(new StringEntity((new JsonHandle()).getFile(fromBodyFileName)));
		(new JsonHandle()).toFile(
				JsonWriter.formatJson(
						new BasicResponseHandler().handleResponse(HttpClientBuilder.create().build().execute(put))),
				toResponseFile);
	}

}
