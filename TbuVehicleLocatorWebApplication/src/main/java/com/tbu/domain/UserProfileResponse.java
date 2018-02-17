/*** 
 * @file UserProfileResponse.java
 * @brief This class is used as UserProFile Response
 * @version 1.0
 * @date  14-Jul-2016
 * @author TEL
 * 
 */

package com.tbu.domain;

public class UserProfileResponse {
	
	/**
	 * status to display success or failure response
	 */
	private boolean status;
	/**
	 * message to display response
	 */
	private String message;
	
	/***
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/***
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}
	
	/***
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/***
	 * @param message
	 * 
	 * the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}
	
}
