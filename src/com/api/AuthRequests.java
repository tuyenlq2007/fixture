package com.api;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpDelete;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.client.methods.RequestBuilder;
import org.apache.http.entity.ContentType;
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

public class AuthRequests extends Requests {

	public String postBasicAuthentication(String url, String params, String userName, String passWord)
			throws ClientProtocolException, IOException {
		final HttpPost httppost = new HttpPost(url);
		List<String> myParams = new ArrayList<>(Arrays.asList(params.split(",")));
		ArrayList<NameValuePair> postParameters = new ArrayList<>();
		int index = 0;
		while (index < myParams.size() - 1) {

			postParameters.add(new BasicNameValuePair(myParams.get(index).trim(), myParams.get(index + 1).trim()));

			index = index + 2;
		}

		httppost.setEntity(new UrlEncodedFormEntity(postParameters, "UTF-8"));
		CredentialsProvider provider = new BasicCredentialsProvider();
		UsernamePasswordCredentials credentials = new UsernamePasswordCredentials(userName, passWord);
		provider.setCredentials(AuthScope.ANY, credentials);
		HttpResponse response = HttpClientBuilder.create().setDefaultCredentialsProvider(provider).build()
				.execute(httppost);
		return JsonWriter.formatJson(new BasicResponseHandler().handleResponse(response));

	}

	public String GetWithAuthen(String url, String tokenType, String finalToken) throws Exception {
		final HttpGet get = new HttpGet(url);
		if (tokenType.equalsIgnoreCase("bearer"))
			get.setHeader("Authorization", "Bearer" + finalToken);
		final HttpResponse response = HttpClientBuilder.create().build().execute(get);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		final String niceFormattedJson = JsonWriter.formatJson(responseBody);

		return niceFormattedJson;
	}

	public String GetWithAuthen(String url, String tokenType, String finalToken, String toFile) throws Exception {
		final HttpGet get = new HttpGet(url);
		if (tokenType.equalsIgnoreCase("bearer"))
			get.setHeader("Authorization", tokenType + finalToken);
		final HttpResponse response = HttpClientBuilder.create().build().execute(get);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		final String niceFormattedJson = JsonWriter.formatJson(responseBody);
		(new JsonHandle()).toFile(niceFormattedJson, toFile);
		return niceFormattedJson;
	}

	public String GetWithAuthen(String url, String headerContentType, String contentType, String tokenType,
			String finalToken, String toFile) throws Exception {
		final HttpGet get = new HttpGet(url);
		if (!headerContentType.isEmpty())
			get.setHeader(headerContentType, contentType);
		if (tokenType.equalsIgnoreCase("bearer"))
			get.setHeader("Authorization", "Bearer" + finalToken);
		final HttpResponse response = HttpClientBuilder.create().build().execute(get);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		final String niceFormattedJson = JsonWriter.formatJson(responseBody);
		(new JsonHandle()).toFile(niceFormattedJson, toFile);
		return niceFormattedJson;
	}

	public String PostWithAuthen(String url, String tokenType, String finalToken, String body) throws Exception {
		final HttpPost post = new HttpPost(url);
		if (tokenType.equalsIgnoreCase("bearer"))
			post.setHeader("Authorization", "Bearer" + finalToken);
		final StringEntity strEntity = new StringEntity(body);
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		final String niceFormattedJson = JsonWriter.formatJson(responseBody);
		return niceFormattedJson;
	}

	public String PostFileWithAuthen(String url, String tokenType, String finalToken, String fileName)
			throws Exception {
		final HttpPost post = new HttpPost(url);
		if (tokenType.equalsIgnoreCase("bearer"))
			post.setHeader("Authorization", "Bearer" + finalToken);
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fileName));
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		return JsonWriter.formatJson(responseBody);
	}

	public String PostFileWithAuthen(String url, String tokenType, String finalToken, String fromFileName,
			String toFile) throws Exception {
		final HttpPost post = new HttpPost(url);
		if (tokenType.equalsIgnoreCase("bearer"))
			post.setHeader("Authorization", "Bearer" + finalToken);
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fromFileName));
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		(new JsonHandle()).toFile(JsonWriter.formatJson(responseBody), toFile);
		return JsonWriter.formatJson(responseBody);
	}

	public String PostFileWithAuthen(String url, String headerContentType, String contentType, String tokenType,
			String finalToken, String fileName) throws Exception {
		final HttpPost post = new HttpPost(url);
		if (!headerContentType.isEmpty())
			post.setHeader(headerContentType, contentType);
		if (tokenType.equalsIgnoreCase("bearer"))
			post.setHeader("Authorization", "Bearer" + finalToken);
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fileName));
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		return JsonWriter.formatJson(responseBody);
	}

	public void delete(String url, String headerParams) throws Exception {
		final HttpDelete delete = (HttpDelete) addHeaderParamsToHttpRequest(new HttpDelete(url), headerParams);
		HttpClientBuilder.create().build().execute(delete);
	}
	
	public String PostFileWithAuthen(String url, String headerContentType, String contentType, String tokenType,
			String finalToken, String fromFileName, String toFile) throws Exception {
		final HttpPost post = new HttpPost(url);
		if (!headerContentType.isEmpty())
			post.setHeader(headerContentType, contentType);
		if (tokenType.equalsIgnoreCase("bearer"))
			post.setHeader("Authorization", "Bearer" + finalToken);
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fromFileName));
		post.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(post);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		(new JsonHandle()).toFile(JsonWriter.formatJson(responseBody), toFile);
		return JsonWriter.formatJson(responseBody);
	}

	public String PostFileWithAuthenAttach(String url, String headerContentType, String contentType, String tokenType,
			String finalToken, String BinaryBody, String attachedfile, String TextBody, String attachedmessagefile,
			String toFile) throws Exception {

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			File file = new File(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
					+ File.separator + attachedfile);

			HttpEntity data = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.addBinaryBody(BinaryBody, file, ContentType.DEFAULT_BINARY, file.getName())
					.addTextBody(TextBody, (new JsonHandle()).getFile(attachedmessagefile), ContentType.DEFAULT_BINARY)
					.build();

			HttpUriRequest post = RequestBuilder.post(url).setEntity(data).build();
			if (!headerContentType.isEmpty())
				post.setHeader(headerContentType, contentType);
			if (tokenType.equalsIgnoreCase("bearer"))
				post.setHeader("Authorization", "Bearer" + finalToken);
			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			(new JsonHandle()).toFile(JsonWriter.formatJson(httpclient.execute(post, responseHandler)), toFile);
			return "";
		}
	}

	public String PostFileWithAuthenAttach(String url, String tokenType, String finalToken, String BinaryBody,
			String attachedfile, String TextBody, String attachedmessagefile, String toFile) throws Exception {

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			File file = new File(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
					+ File.separator + attachedfile);

			HttpEntity data = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.addBinaryBody(BinaryBody, file, ContentType.DEFAULT_BINARY, file.getName())
					.addTextBody(TextBody, (new JsonHandle()).getFile(attachedmessagefile), ContentType.DEFAULT_BINARY)
					.build();

			HttpUriRequest post = RequestBuilder.post(url).setEntity(data).build();
			if (tokenType.equalsIgnoreCase("bearer"))
				post.setHeader("Authorization", "Bearer" + finalToken);
			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			(new JsonHandle()).toFile(JsonWriter.formatJson(httpclient.execute(post, responseHandler)), toFile);
			return "";
		}
	}

	public String PutWithAuthen(String url, String tokenType, String finalToken, String body) throws Exception {
		final HttpPut put = new HttpPut(url);
		if (tokenType.equalsIgnoreCase("bearer"))
			put.setHeader("Authorization", "Bearer" + finalToken);
		final StringEntity strEntity = new StringEntity(body);
		put.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(put);

		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		final String niceFormattedJson = JsonWriter.formatJson(responseBody);
		return niceFormattedJson;
	}

	public String PutFileWithAuthen(String url, String tokenType, String finalToken, String fileName) throws Exception {
		final HttpPut put = new HttpPut(url);
		if (tokenType.equalsIgnoreCase("bearer"))
			put.setHeader("Authorization", "Bearer" + finalToken);
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fileName));
		put.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(put);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		return JsonWriter.formatJson(responseBody);
	}

	public String PutFileWithAuthen(String url, String tokenType, String finalToken, String fromFileName, String toFile)
			throws Exception {
		final HttpPut put = new HttpPut(url);
		if (tokenType.equalsIgnoreCase("bearer"))
			put.setHeader("Authorization", "Bearer" + finalToken);
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fromFileName));
		put.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(put);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		(new JsonHandle()).toFile(JsonWriter.formatJson(responseBody), toFile);
		return JsonWriter.formatJson(responseBody);
	}

	public String PutFileWithAuthen(String url, String headerContentType, String contentType, String tokenType,
			String finalToken, String fileName) throws Exception {
		final HttpPut put = new HttpPut(url);
		if (!headerContentType.isEmpty())
			put.setHeader(headerContentType, contentType);
		if (tokenType.equalsIgnoreCase("bearer"))
			put.setHeader("Authorization", "Bearer" + finalToken);
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fileName));
		put.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(put);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);

		return JsonWriter.formatJson(responseBody);
	}

	public String PutFileWithAuthen(String url, String headerContentType, String contentType, String tokenType,
			String finalToken, String fromFileName, String toFile) throws Exception {
		final HttpPut put = new HttpPut(url);
		if (!headerContentType.isEmpty())
			put.setHeader(headerContentType, contentType);
		if (tokenType.equalsIgnoreCase("bearer"))
			put.setHeader("Authorization", "Bearer" + finalToken);
		final StringEntity strEntity = new StringEntity((new JsonHandle()).getFile(fromFileName));
		put.setEntity(strEntity);
		final HttpResponse response = HttpClientBuilder.create().build().execute(put);
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String responseBody = handler.handleResponse(response);
		(new JsonHandle()).toFile(JsonWriter.formatJson(responseBody), toFile);
		return JsonWriter.formatJson(responseBody);
	}

	public String PutFileWithAuthenAttach(String url, String headerContentType, String contentType, String tokenType,
			String finalToken, String attachedfile, String attachedmessagefile, String fromFileName, String toFile)
			throws Exception {

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			File file = new File(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
					+ File.separator + attachedfile);

			HttpEntity data = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.addBinaryBody("upfile", file, ContentType.DEFAULT_BINARY, file.getName())
					.addTextBody("text", (new JsonHandle()).getFile(attachedmessagefile), ContentType.DEFAULT_BINARY)
					.build();

			HttpUriRequest put = RequestBuilder.put(url).setEntity(data).build();
			if (!headerContentType.isEmpty())
				put.setHeader(headerContentType, contentType);
			if (tokenType.equalsIgnoreCase("bearer"))
				put.setHeader("Authorization", "Bearer" + finalToken);
			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			(new JsonHandle()).toFile(JsonWriter.formatJson(httpclient.execute(put, responseHandler)), toFile);
			return "";
		}
	}

	public String PutFileWithAuthenAttach(String url, String tokenType, String finalToken, String attachedfile,
			String attachedmessagefile, String fromFileName, String toFile) throws Exception {

		try (CloseableHttpClient httpclient = HttpClients.createDefault()) {

			File file = new File(System.getenv("ITS") + File.separator + "FitNesseRoot" + File.separator + "files"
					+ File.separator + attachedfile);

			HttpEntity data = MultipartEntityBuilder.create().setMode(HttpMultipartMode.BROWSER_COMPATIBLE)
					.addBinaryBody("upfile", file, ContentType.DEFAULT_BINARY, file.getName())
					.addTextBody("text", (new JsonHandle()).getFile(attachedmessagefile), ContentType.DEFAULT_BINARY)
					.build();

			HttpUriRequest put = RequestBuilder.put(url).setEntity(data).build();
			if (tokenType.equalsIgnoreCase("bearer"))
				put.setHeader("Authorization", "Bearer" + finalToken);
			ResponseHandler<String> responseHandler = response -> {
				int status = response.getStatusLine().getStatusCode();
				if (status >= 200 && status < 300) {
					HttpEntity entity = response.getEntity();
					return entity != null ? EntityUtils.toString(entity) : null;
				} else {
					throw new ClientProtocolException("Unexpected response status: " + status);
				}
			};
			(new JsonHandle()).toFile(JsonWriter.formatJson(httpclient.execute(put, responseHandler)), toFile);
			return "";
		}
	}

}
