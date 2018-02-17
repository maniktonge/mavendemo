/** 
 * @file SystemAdmin.java
 * @brief This class is used as SystemAdmin structure
 * @version 1.0
 * @date  24-Jun-2016
 * @author TEL
 * 
 */

package com.tbu.domain;

import java.util.Date;

/***
 * This class is used as SystemAdmin structure
 */
public class SystemAdmin {
	/**
	 * date at which database is created
	 */
	private Date createddate;
	/**
	 * username credentials
	 */
	private String username;
	/**
	 * password for creating database
	 */
	private String password;
	/**
	 * helptext to guide
	 */
	private String helptext;
	/**
	 * ID for system admin
	 */
	private int systemadmin_id;
	/***
	 * databsename
	 */
	private String databasename;

	public String getDatabasename() {
		return databasename;
	}

	public void setDatabasename(String databasename) {
		this.databasename = databasename;
	}

	public String getPort() {
		return port;
	}

	public void setPort(String port) {
		this.port = port;
	}

	public String getServerip() {
		return serverip;
	}

	public void setServerip(String serverip) {
		this.serverip = serverip;
	}

	/***
	 * port no
	 */
	private String port;

	/***
	 * serverip
	 */
	private String serverip;

	// getter setter functions
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
	 * @return the password
	 */
	public String getPassword() {
		return password;
	}

	/***
	 * @param password
	 *            the password to set
	 */
	public void setPassword(String password) {
		this.password = password;
	}

	/***
	 * @return the helptext
	 */
	public String getHelptext() {
		return helptext;
	}

	/***
	 * @param helptext
	 *            the helptext to set
	 */
	public void setHelptext(String helptext) {
		this.helptext = helptext;
	}

	/***
	 * @return the systemadmin_id
	 */
	public int getSystemadmin_id() {
		return systemadmin_id;
	}

	/***
	 * @param systemadmin_id
	 *            the systemadmin_id to set
	 */
	public void setSystemadmin_id(int systemadmin_id) {
		this.systemadmin_id = systemadmin_id;
	}

}
