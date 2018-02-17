/** 
 * @file HeadUnit.java
 * @brief This class is used as HeadUnit structure
 * @version 1.0
 * @date  24-Jun-2015
 * @author TEL
 * 
 */

package com.tbu.domain;

import java.util.Date;

public class HeadUnit {
	/**
	 * user ID of user
	 */
	private int userid;
	/**
	 * name of registered vehicle
	 */
	private String carname;
	/**
	 * hduuid of vehicle
	 */
	private String hduuid;
	/**
	 * version of the installed app
	 */
	private String appversion;
	/**
	 * gcm registration key of the registered vehicle
	 */
	private String gcmregistartionkey;
	/**
	 * created date
	 */
	private Date createddate;
	private boolean status;
	/**
	 * feature type AC/DOOR/HEADLIGHT
	 */
	private String featuretype;
	/**
	 * feature Status ON/OFF
	 */
	private String featurestatus;
	/**
	 * message
	 */
	private String message;
	/**
	 * head unit id of the registred vehicle
	 */
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

	/**
	 * @return the userid
	 */
	public int getUserid() {
		return userid;
	}

	/**
	 * @param userid
	 *            the userid to set
	 */
	public void setUserid(int userid) {
		this.userid = userid;
	}

	/**
	 * @return the carname
	 */
	public String getCarname() {
		return carname;
	}

	/**
	 * @param carname
	 *            the carname to set
	 */
	public void setCarname(String carname) {
		this.carname = carname;
	}

	/**
	 * @return the hduuid
	 */
	public String getHduuid() {
		return hduuid;
	}

	/**
	 * @param hduuid
	 *            the hduuid to set
	 */
	public void setHduuid(String hduuid) {
		this.hduuid = hduuid;
	}

	/**
	 * @return the appversion
	 */
	public String getAppversion() {
		return appversion;
	}

	/**
	 * @param appversion
	 *            the appversion to set
	 */
	public void setAppversion(String appversion) {
		this.appversion = appversion;
	}

	/**
	 * @return the gcmregistartionkey
	 */
	public String getGcmregistartionkey() {
		return gcmregistartionkey;
	}

	/**
	 * @param gcmregistartionkey
	 *            the gcmregistartionkey to set
	 */
	public void setGcmregistartionkey(String gcmregistartionkey) {
		this.gcmregistartionkey = gcmregistartionkey;
	}

	/**
	 * @return the createddate
	 */
	public Date getCreateddate() {
		return createddate;
	}

	/**
	 * @param createddate
	 *            the createddate to set
	 */
	public void setCreateddate(Date createddate) {
		this.createddate = createddate;
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

	/**
	 * @return the featuretype
	 */
	public String getFeaturetype() {
		return featuretype;
	}

	/**
	 * @param featuretype
	 *            the featuretype to set
	 */
	public void setFeaturetype(String featuretype) {
		this.featuretype = featuretype;
	}

	/**
	 * @return the featurestatus
	 */
	public String getFeaturestatus() {
		return featurestatus;
	}

	/**
	 * @param featurestatus
	 *            the featurestatus to set
	 */
	public void setFeaturestatus(String featurestatus) {
		this.featurestatus = featurestatus;
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

	/**
	 * @return the headunitid
	 */
	public long getHeadunitid() {
		return headunitid;
	}

	/**
	 * @param headunitid
	 *            the headunitid to set
	 */
	public void setHeadunitid(long headunitid) {
		this.headunitid = headunitid;
	}

	/**
	 * @return the actemp
	 */
	public String getActemp() {
		return actemp;
	}

	/**
	 * @param actemp
	 *            the actemp to set
	 */
	public void setActemp(String actemp) {
		this.actemp = actemp;
	}

	/**
	 * @return the acfanspeed
	 */
	public String getAcfanspeed() {
		return acfanspeed;
	}

	/**
	 * @param acfanspeed
	 *            the acfanspeed to set
	 */
	public void setAcfanspeed(String acfanspeed) {
		this.acfanspeed = acfanspeed;
	}

	/**
	 * @return the rearactemp
	 */
	public String getRearactemp() {
		return rearactemp;
	}

	/**
	 * @param rearactemp
	 *            the rearactemp to set
	 */
	public void setRearactemp(String rearactemp) {
		this.rearactemp = rearactemp;
	}

}
