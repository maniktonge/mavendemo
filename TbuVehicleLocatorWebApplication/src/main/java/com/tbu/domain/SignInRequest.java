/*** 
 * @file SignInRequest.java
 * @brief This class is SignInRequest structure
 * @version 1.0
 * @date  24-Aug-2016
 * @author TEL
 * 
 */
package com.tbu.domain;

public class SignInRequest {
	private String emailid;
	public String password;
	public String gcmregistartionkey;

	/**
	 * @return
	 */
	public String getPassword() {
		return password;
	}

	/**
	 * @param password
	 */
	public void setPassword(String password) {
		this.password = password;
	}

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
	/**
	 * 
	 * @return
	 */
	public String getGcmregistartionkey() {
		return gcmregistartionkey;
	}
/**
 * 
 * @param gcmregistartionkey
 */
	public void setGcmregistartionkey(String gcmregistartionkey) {
		this.gcmregistartionkey = gcmregistartionkey;
	}

}
