package utils;

public class KeyValue {
	String value;

	public KeyValue(String in) {
		// TODO Auto-generated constructor stub
		value = in;
	}

	public String getkey() {
		return value.split("=")[0];
	}

	public String getvalue() {
		return value.split("=")[1];
	}

}