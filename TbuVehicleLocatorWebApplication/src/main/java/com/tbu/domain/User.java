/*** 
 * @file User.java
 * @brief This class is used as User structure
 * @version 1.0
 * @date  24-Jun-2016
 * @author TEL
 * 
 */

package com.tbu.domain;

import java.util.Date;

/**
 * This class is used as User structure
 */
public class User {
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
	 * date at which user is registered
	 */
	private Date createddate;
	/**
	 * username for registering
	 */
	private String username;
	
	/**
	 * password for registering
	 */
	private String password;
	
	

	/**
	 * status to display success or failure response
	 */
	private boolean status;
	/**
	 * feature type AC/DOOR/HEADLIGHT
	 */
	private String featuretype;
	/**
	 * feature status ON/OFF
	 */
	private String featurestatus;
	/**
	 * message to display response
	 */
	private String message;
	/**
	 * emailId for registering user unique
	 */
	private String emailid;

	private long headunitid;
	/**
	 * AC temperature for vehicle action
	 */
	private String actemp;

	/**
	 * AC FAN temperature for vehcicle action
	 */
	private String acfanspeed;

	private String rearactemp;

	/***
	 * @return the mobileno
	 */
	public String getMobileno() {
		return mobileno;
	}

	/***
	 * @param mobileno
	 *            the mobileno to set
	 */
	public void setMobileno(String mobileno) {
		this.mobileno = mobileno;
	}

	/***
	 * @return the userid
	 */
	public long getUserid() {
		return userid;
	}

	/***
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(long userid) {
		this.userid = userid;
	}

	/***
	 * @return the uuid
	 */
	public String getUuid() {
		return uuid;
	}

	/***
	 * @param uuid
	 *            the uuid to set
	 */
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}

	/***
	 * @return the appversion
	 */
	public String getAppversion() {
		return appversion;
	}

	/***
	 * @param appversion
	 *            the appversion to set
	 */
	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	/***
	 * @return the gcmregistartionkey
	 */
	public String getGcmregistartionkey() {
		return gcmregistartionkey;
	}

	/***
	 * @param gcmregistartionkey
	 *            the gcmregistartionkey to set
	 */
	public void setGcmregistartionkey(String gcmregistartionkey) {
		this.gcmregistartionkey = gcmregistartionkey;
	}

	/***
	 * @return the createddate
	 */
	public Date getCreateddate() {
		return createddate;
	}

	/***
	 * @param createddate
	 *            the createddate to set
	 */
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
	}

	/***
	 * @return the username
	 */
	public String getUsername() {
		return username;
	}

	/***
	 * @param username
	 *            the username to set
	 */
	public void setUsername(String username) {
		this.username = username;
	}

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
	 * @return the featuretype
	 */
	public String getFeaturetype() {
		return featuretype;
	}

	/***
	 * @param featuretype
	 *            the featuretype to set
	 */
	public void setFeaturetype(String featuretype) {
		this.featuretype = featuretype;
	}

	/***
	 * @return the featurestatus
	 */
	public String getFeaturestatus() {
		return featurestatus;
	}

	/***
	 * @param featurestatus
	 *            the featurestatus to set
	 */
	public void setFeaturestatus(String featurestatus) {
		this.featurestatus = featurestatus;
	}

	/***
	 * @return the message
	 */
	public String getMessage() {
		return message;
	}

	/***
	 * @param message
	 *            the message to set
	 */
	public void setMessage(String message) {
		this.message = message;
	}

	/***
	 * @return the emailid
	 */
	public String getEmailid() {
		return emailid;
	}

	/***
	 * @param emailid
	 *            the emailid to set
	 */
	public void setEmailid(String emailid) {
		this.emailid = emailid;
	}

	/***
	 * @return the headunitid
	 */
	public long getHeadunitid() {
		return headunitid;
	}

	/***
	 * @param headunitid
	 *            the headunitid to set
	 */
	public void setHeadunitid(long headunitid) {
		this.headunitid = headunitid;
	}

	/***
	 * @return the actemp
	 */
	public String getActemp() {
		return actemp;
	}

	/***
	 * @param actemp
	 *            the actemp to set
	 */
	public void setActemp(String actemp) {
		this.actemp = actemp;
	}

	/***
	 * @return the acfanspeed
	 */
	public String getAcfanspeed() {
		return acfanspeed;
	}

	/***
	 * @param acfanspeed
	 *            the acfanspeed to set
	 */
	public void setAcfanspeed(String acfanspeed) {
		this.acfanspeed = acfanspeed;
	}

	/***
	 * @return the rearactemp
	 */
	public String getRearactemp() {
		return rearactemp;
	}

	/***
	 * @param rearactemp
	 *            the rearactemp to set
	 */
	public void setRearactemp(String rearactemp) {
		this.rearactemp = rearactemp;
	}
	/**
	 * 
	 * @return
	 */
	public String getPassword() {
		return password;
	}
/**
 * 
 * @param password
 */
	public void setPassword(String password) {
		this.password = password;
	}
}
