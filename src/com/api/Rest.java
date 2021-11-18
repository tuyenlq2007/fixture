package com.api;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.BasicResponseHandler;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cedarsoftware.util.io.JsonWriter;

public class Rest {
	// convert to json
	@SuppressWarnings("rawtypes")
	private static Object toJSON(Object object) throws JSONException {
		if (object instanceof HashMap) {
			final JSONObject json = new JSONObject();
			final HashMap map = (HashMap) object;
			for (final Object key : map.keySet()) {
				json.put(key.toString(), toJSON(map.get(key)));
			}
			return json;
		} else if (object instanceof Iterable) {
			final JSONArray json = new JSONArray();
			for (final Object value : (Iterable) object) {
				json.put(toJSON(value));
			}
			return json;
		} else {
			return object;
		}
	}

	private final String USER_AGENT = "Mozilla/5.0";
	// private String userName = "";
	// private String pwd = "";
	private HttpClient client = HttpClientBuilder.create().build();
	private String cookies;
	private String token;
	ArrayList<String> arrlist = new ArrayList<String>();

	Map<String, Object> testmap = new HashMap<String, Object>();

	public Rest() {
		// this.userName = userName;
		// this.pwd = pwd;
		client = MySSLSocketFactory.getNewHttpClient();
	}

	public ArrayList<String> AllVariableOfJson() {
		return arrlist;
	}

	public String getCookies() {
		return cookies;
	}

	public List<NameValuePair> getFormParams(String username, String pwd, String sessionId) {
		final List<NameValuePair> nameValuePairs = new ArrayList<NameValuePair>(1);
		nameValuePairs.add(new BasicNameValuePair("j_username", username));
		nameValuePairs.add(new BasicNameValuePair("j_password", pwd));
		nameValuePairs.add(new BasicNameValuePair("sessionId", sessionId));
		return nameValuePairs;
	}

	public String getToken() {
		return token;
	}

	@SuppressWarnings("rawtypes")
	private Object GetValue(Map<String, Object> hmap, Object key) {
		Object newValue = null;
		final Set set = hmap.entrySet();
		final Iterator iterator = set.iterator();
		while (iterator.hasNext()) {
			final Map.Entry me = (Map.Entry) iterator.next();
			if (me.getKey().equals(key)) {
				System.out.print(me.getKey() + ": ");
				System.out.println(me.getValue());
				newValue = me.getValue();
				break;
			}

		}
		return newValue;
	}

	public String IGetCookie(String url, String userName, String pwd) throws Exception {

		final List<NameValuePair> postParams = getFormParams(userName, pwd, "");

		// HttpPost post = new
		// HttpPost("https://20.203.133.141/afa-ba-latest/ife-web-base-agent/j_spring_security_check");
		final HttpPost post = new HttpPost(url);

		// add header
		// post.setHeader("User-Agent", setUp.getUserAgent());
		post.setHeader("User-Agent", USER_AGENT);
		post.setHeader("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,*/*;q=0.8");
		post.setHeader("Accept-Language", "en-US,en;q=0.5");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		post.setEntity(new UrlEncodedFormEntity(postParams));

		final HttpResponse response = client.execute(post);

		final int responseCode = response.getStatusLine().getStatusCode();

		// System.out.println("\nSending 'POST' request to URL : " + url);
		System.out.println("Post parameters : " + postParams);
		System.out.println("Response Code : " + responseCode);

		// set cookies
		setCookies(
				response.getFirstHeader("Set-Cookie") == null ? "" : response.getFirstHeader("Set-Cookie").toString());

		System.out.println("Cookies : " + cookies);
		final String[] arrCookies = cookies.split(";");
		System.out.println("Cookies 1: " + arrCookies[0]);
		final String[] arrSetCookies = arrCookies[0].split(":");
		System.out.println("set_Cookies 1: " + arrSetCookies[1].trim());
		cookies = arrSetCookies[1].trim();
		return cookies;
	}

	public String IGetFileAsString(String file) throws Exception {

		InputStream is;
		is = new FileInputStream(file);
		String jsonTxt = IOUtils.toString(is, "UTF-8");
		jsonTxt = jsonTxt.replaceAll("\n", "\r\n");
		final String niceFormattedJson = JsonWriter.formatJson(jsonTxt);
		// Ensure newline characters meet the HTTP specification formatting
		// requirements.
		return niceFormattedJson;
	}

	public String IGetToken(String url, String cookie) throws Exception {

		final HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		post.setHeader("Accept", "application/json, text/plain, */*");
		post.setHeader("Accept-Language", "en-US,en;q=0.9,vi;q=0.8");
		post.setHeader("Cookie", cookie);
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");

		final HttpResponse response = client.execute(post);

		// int responseCode = response.getStatusLine().getStatusCode();
		// get body from response
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String body = handler.handleResponse(response);

		final JSONObject myObject = new JSONObject(body);
		System.out.println("myObject : " + myObject);

		// get token
		setToken(myObject.getString("accessToken") == null ? "" : myObject.getString("accessToken"));
		return token;
	}

	public String IGetToken(String url, String tokenName, String authorization) throws Exception {

		final HttpPost post = new HttpPost(url);

		// add header
		post.setHeader("Accept",
				"text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,image/apng,*/*;q=0.8");
		post.setHeader("Accept", "application/json, text/plain, */*");
		post.setHeader("Accept-Language", "en-US,en;q=0.9,vi;q=0.8");
		post.setHeader("Connection", "keep-alive");
		post.setHeader("Content-Type", "application/x-www-form-urlencoded");
		post.setHeader("Authorization", authorization);
		final HttpResponse response = client.execute(post);

		final int responseCode = response.getStatusLine().getStatusCode();
		/*
		System.out.println("\nSending 'POST' request to URL : " + url);

		System.out.println("Response Code : " + responseCode);
		System.out.println("Response : " + response.getEntity().getContent());
		// get body from response
		final ResponseHandler<String> handler = new BasicResponseHandler();
		final String body = handler.handleResponse(response);
		System.out.println("body : " + body);

		final JSONObject myObject = new JSONObject(body);
		System.out.println("myObject : " + myObject);

		// get token
		setToken(myObject.getString(tokenName) == null ? "" : myObject.getString(tokenName));*/
		//return token;
		return Integer.toString(responseCode);
	}

	public String ISendPostAndGetResponse(String url, String token) throws Exception {
		String niceFormattedJson = "";
		try {
			final HttpPost post = new HttpPost(url);

			// add header
			post.setHeader("User-Agent", USER_AGENT);
			post.setHeader("Accept", "application/json, text/plain, */*");
			post.setHeader("Accept-Language", "en-US,en;q=0.9,vi;q=0.8");
			post.setHeader("Authorization", "Bearer " + getToken());
			post.setHeader("Connection", "keep-alive");
			post.setHeader("Content-Type", "application/json");

			final HttpResponse response = client.execute(post);

			final int responseCode = response.getStatusLine().getStatusCode();
			System.out.println("URL : " + url);
			System.out.println("Response Code : " + responseCode);

			final ResponseHandler<String> handler = new BasicResponseHandler();
			final String body = handler.handleResponse(response);

			// JSONObject obj = new JSONObject(body);

			// Map<String, Object> map = jsonToMap(obj);
			niceFormattedJson = JsonWriter.formatJson(body);

			// Object test = toJSON(map);

		} catch (final Exception e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}

		return niceFormattedJson;
	}

	public String IUpdateVariable(String body, String var1) throws Exception {
		for (final String value : var1.split(",")) {
			if (!value.isEmpty()) {
				final String[] arr = value.split(":");
				testmap.put(arr[0].trim(), arr[1].trim());
			}
		}

		final JSONObject json = new JSONObject(body);
		final Map<String, Object> map = jsonToMap(json);
		final Object object = toJSON(map);

		return JsonWriter.formatJson(object.toString());

	}

	private Map<String, Object> jsonToMap(JSONObject json) throws JSONException {
		Map<String, Object> retMap = new HashMap<String, Object>();

		if (json != JSONObject.NULL) {
			retMap = toMap(json);
		}
		return retMap;
	}

	public void setCookies(String cookies) {
		this.cookies = cookies;
	}

	public void setToken(String token) {
		this.token = token;
	}

	private List<Object> toList(JSONArray array) throws JSONException {
		final List<Object> list = new ArrayList<Object>();
		for (int i = 0; i < array.length(); i++) {
			Object value = array.get(i);
			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			list.add(value);
		}
		return list;
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> toMap(JSONObject object) throws JSONException {
		final Map<String, Object> map = new HashMap<String, Object>();

		final Iterator<String> keysItr = object.keys();
		while (keysItr.hasNext()) {
			final String key = keysItr.next();
			Object value = object.get(key);

			if (value instanceof JSONArray) {
				value = toList((JSONArray) value);
			}

			else if (value instanceof JSONObject) {
				value = toMap((JSONObject) value);
			}
			// else if(value == JSONObject.NULL ||
			// value.equals("")||!value.equals(""))
			else if (value.toString().contains("${")) {
				/*
				 * value = "${" + key + index +"}"; index++;
				 * arrlist.add(value.toString());
				 */
				value = GetValue(testmap, key);
			}
			map.put(key, value);
		}
		return map;
	}

}