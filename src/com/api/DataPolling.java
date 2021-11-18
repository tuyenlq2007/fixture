package com.api;
import java.util.Date;
public class DataPolling {
	private String id;
	private String enviroment;
	private String host;
	private String testcasename;
	private String excluding;
	private String request;	
	private String body;
	private String actualresult;
	private String expectedresult;
	private String comparision;
	private String status;
	private Date starttime;
	private Date endtime;
	// Constructor
	public DataPolling(String id, String enviroment, String host, String testcasename, String excluding, String request,
			String body, String actualresult, String expectedresult, String comparision, String status, Date starttime,
			Date endtime) {
		super();
		this.id = id;
		this.enviroment = enviroment;
		this.host = host;
		this.testcasename = testcasename;
		this.excluding = excluding;
		this.request = request;
		this.body = body;
		this.actualresult = actualresult;
		this.expectedresult = expectedresult;
		this.comparision = comparision;
		this.status = status;
		this.starttime = starttime;
		this.endtime = endtime;
	}
	public DataPolling() {
		// TODO Auto-generated constructor stub
	}
	// Getter Setter
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getEnviroment() {
		return enviroment;
	}
	public void setEnviroment(String enviroment) {
		this.enviroment = enviroment;
	}
	public String getHost() {
		return host;
	}
	public void setHost(String host) {
		this.host = host;
	}
	public String getTestcasename() {
		return testcasename;
	}
	public void setTestcasename(String testcasename) {
		this.testcasename = testcasename;
	}
	public String getExcluding() {
		return excluding;
	}
	public void setExcluding(String excluding) {
		this.excluding = excluding;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getBody() {
		return body;
	}
	public void setBody(String body) {
		this.body = body;
	}
	public String getActualresult() {
		return actualresult;
	}
	public void setActualresult(String actualresult) {
		this.actualresult = actualresult;
	}
	public String getExpectedresult() {
		return expectedresult;
	}
	public void setExpectedresult(String expectedresult) {
		this.expectedresult = expectedresult;
	}
	public String getComparision() {
		return comparision;
	}
	public void setComparision(String comparision) {
		this.comparision = comparision;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public Date getStarttime() {
		return starttime;
	}
	public void setStarttime(Date starttime) {
		this.starttime = starttime;
	}
	public Date getEndtime() {
		return endtime;
	}
	public void setEndtime(Date endtime) {
		this.endtime = endtime;
	}	
	@Override
	public String toString() {
		StringBuffer s = new StringBuffer();
		s.append("\nID:" + this.id + "\nEnviroment: " + this.enviroment + "\nHost:" + this.host 
				+ "\nTestcase name: "+ this.testcasename + "\nExcluding:" + this.excluding 
				+ "\nRequest:" + this.request + "\nBody: " + this.body + "\nActual result: " 
				+ this.actualresult + "\nExpected result:" + this.expectedresult + "\nComparision" + this.comparision
				+ "\nStatus: "+ this.status + "\nStart time: " + this.starttime.toString() 
				+ "\nEnd time: " + this.endtime.toString());
		return s.toString();
	}
}
