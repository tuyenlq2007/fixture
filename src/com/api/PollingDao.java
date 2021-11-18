package com.api;

import java.io.FileNotFoundException;
import java.io.IOException;

public interface PollingDao {
	/**
	 * This is the method to be used to create a record in the Polling table.
	 * @throws FileNotFoundException 
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public void createPolling(DataPolling p) throws FileNotFoundException, IOException, ClassNotFoundException, InterruptedException;
	/**
	 * This is the method to be used to update a record in the Polling table.
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public void updatePolling(String id, DataPolling p) throws IOException, ClassNotFoundException, InterruptedException;
	/**
	 * This is the method to be used to check a polling exist in the Polling table.
	 * @throws IOException 
	 * @throws InterruptedException 
	 * @throws ClassNotFoundException 
	 */
	public boolean checkExistPolling(String id) throws IOException, ClassNotFoundException, InterruptedException;
	void updatePollingActualResult(String id, String actualResult) throws IOException, ClassNotFoundException, InterruptedException;
	DataPolling queryPolling(String id) throws FileNotFoundException, IOException;
	DataPolling queryPolling() throws FileNotFoundException, IOException;
}
