/**
 * @file TripAppDataRes.java
 * @brief This class is used to give response to calling client
 * about Trip APP
 * @version 1.0
 * @date  26-July-2016
 * @author TEL
 **/
package com.tbu.domain;

public class TripAppDataRes {
	/**
	 * status to display success or failure response
	 */
	private boolean status;
	
	/**
	 * message to display response
	 */
	private String message;
	/**
	 * 
	 * @return
	 */
	public boolean isStatus() {
		return status;
	}
	/**
	 * 
	 * @param status
	 */

	public void setStatus(boolean status) {
		this.status = status;
	}
	/**
	 * 
	 * @return
	 */

	public String getMessage() {
		return message;
	}
/**
 * 
 * @param message
 */
	public void setMessage(String message) {
		this.message = message;
	}

	
}
