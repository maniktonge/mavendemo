/** 
 * @file SignInResponse.java
 * @brief This class is used to give customized response for signin rest api
 * @version 1.1
 * @date  23-Aug-2016
 * @author TEL
 * 
 */
package com.tbu.domain;



public class SignInResponse {
	/**
	 * mobile no for registering user
	 */
	private String mobileno;
	/**
	 * user Id of registered user
	 */
	private long userid;
	/**
	 * uuid for user
	 */
	private String uuid;
	/**
	 * current version of the installed app
	 */
	private String appversion;

	/**
	 * gcm registration key for sending push notification
	 */
	private String gcmregistartionkey;

	/**
	 * username for registering
	 */
	private String username;
	/**
	 * emaild
	 */

	private String emailid;

	/**
	 * status
	 */
	public boolean status;
	
	/**
	 * Head unit configuration details present
	 */
	private boolean headunitIdPresent;
	
	/**
	 * Headunit value is present
	 */
	private int headunitId;
	
	/**
	 * 
	 * @return
	 */
	public boolean isHeadunitIdPresent() {
		return headunitIdPresent;
	}

	/**
	 * Set the head unit detail present
	 * @param headunitIdPresent
	 */
	public void setHeadunitIdPresent(boolean headunitIdPresent) {
		this.headunitIdPresent = headunitIdPresent;
	}

	/**
	 * Get the id of head unit
	 * @return
	 */
	public int getHeadunitId() {
		return headunitId;
	}

	/**
	 * Set the head unit id
	 * @param headunitId
	 */
	public void setHeadunitId(int headunitId) {
		this.headunitId = headunitId;	}
	
	

	/**
	 * 
	 * @return mobileno
	 */

	public String getMobileno() {
		return mobileno;
	}

	/**
	 * 
	 * @param mobileno
	 */
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	/**
	 * 
	 * @return userid
	 */

	public long getUserid() {
		return userid;
	}

	/**
	 * 
	 * @param userid
	 */
	public void setUserid(long userid) {
		this.userid = userid;
	}

	/**
	 * 
	 * @return uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/**
	 * 
	 * @param uuid
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/**
	 * 
	 * @return appversion
	 */
	public String getAppversion() {
		return appversion;
	}

	/**
	 * 
	 * @param appversion
	 */
	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	/**
	 * 
	 * @return gcmregistartionkey
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

	/**
	 * 
	 * @return username
	 */
	public String getUsername() {
		return username;
	}

	/**
	 * 
	 * @param username
	 */
	public void setUsername(String username) {
		this.username = username;
	}

	/**
	 * 
	 * @return emailid
	 */
	public String getEmailid() {
		return emailid;
	}

	/**
	 * 
	 * @param emailid
	 */
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	
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
}
