/*** 
 * @file CheckEmailResponse.java
 * @brief This class is used as CheckEmailResponse structure
 * @version 1.0
 * @date  24-Aug-2016
 * @author TEL
 * 
 */
package com.tbu.domain;

public class CheckEmailResponse {

	public boolean status;
	public String message;

	/**
	 * @return the status
	 */
	public boolean isStatus() {
		return status;
	}

	/**
	 * @param status
	 *            the status to set
	 */
	public void setStatus(boolean status) {
		this.status = status;
	}

	/**
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/**
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

}
