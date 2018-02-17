/*** 
 * @file ResetPasswordRequest.java
 * @brief This class is used as ResetPasswordRequest structure
 * @version 1.0
 * @date  24-Aug-2016
 * @author TEL
 * 
 */

package com.tbu.domain;

public class ResetPasswordRequest {

	private String emailid;

	/**
	 * @return the emailid
	 */
	public String getEmailid() {
		return emailid;
	}

	/**
	 * @param emailid
	 *            the emailid to set
	 */
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}
}
