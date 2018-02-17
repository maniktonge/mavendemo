/** 
 * @file Utility.java
 * @brief This class is used to set All Required Constant value
 * @version 1.0
 * @date  6-June-2016
 * @author TEL
 * 
 */

package com.tbu.constant;

import java.io.File;

/**
 * 
 * This class use to set common variables:MySql server JDBC url : to connect
 * with MySql database through, DB username and password Web service,
 * GCM_API_KEY : to send push notifications
 * 
 * @author TEL
 * 
 **/
public class Utility {
	/**
	 * predefined username and password credentials
	 * 
	 */
	public static String USERNAME = "admin";
	public static String PASSWORD = "admin";
	
	/**
	 * database.properties for database credentials
	 */
	public static String DB_PATH = "WEB-INF" + File.separator + "classes" + File.separator + "database" + File.separator
			+ "database.properties";
	
	/**
	 * vehiclelocator.sql for table creation
	 */
	
	public static String SQL_PATH = "WEB-INF" + File.separator + "classes" + File.separator + "database" + File.separator
			+ "vehiclelocator.sql";
	
	/**
	 * GCM Server key For Vehicle Locator App and Simulator App
	 * 
	 */
	private final String GCM_API_KEY_VEHICLELOCATORAPP = "AIzaSyCaW5CrPXfFTURQ2SLka6k22SlQb9MxW9M";
	private final String GCM_API_KEY_SIMUAPP = "AIzaSyDhVrIBBM5767wdAOlTbC-O3yrpKJEfX8I";

	/**
	 * This method is used to get the gcm key value for Vehicle Locator App
	 * 
	 * @return
	 */
	public String getGcmApiKeyVehiclelocatorapp() {
		return GCM_API_KEY_VEHICLELOCATORAPP;
	}

	/**
	 * This method is used to get the gcm key value for Simulator App
	 * 
	 * @return
	 */
	public String getGcmApiKeySimuapp() {
		return GCM_API_KEY_SIMUAPP;
	}
	/**
	 * defined outlook iD details for sending emailID
	 * 
	 */
	private final String USERNAME_OFFICE = "xxxxxx@tataelxsi.co.in";
	/**
	 * @return the uSERNAME_OFFICE
	 */
	public String getUSERNAME_OFFICE() {
		return USERNAME_OFFICE;
	}

	/**
	 * @return the pASSWORD_OFFICE
	 */
	public String getPASSWORD_OFFICE() {
		return PASSWORD_OFFICE;
	}

	
	

	private final String PASSWORD_OFFICE = "xxxxxx";
	
	public static String SMTP_HOST = "smtp.office365.com";
	public static String SMTP_PORT = "587";

	/**
	 * defined index To access all database values From User
	 */
	public static int USER_NAME_INDEX = 1;
	public static int USER_EMAIL_INDEX = 2;
	public static int USER_MBNO_INDEX = 3;
	public static int USER_UUID_INDEX = 4;
	public static int USER_APPV_INDEX = 5;
	public static int USER_GCM_INDEX = 6;
	public static int USER_CDT_INDEX = 7;
	public static int USER_PWD_INDEX = 8;

	/**
	 * defined index To access all database values From Headunit
	 */
	public static int HDU_UID_INDEX = 1;
	public static int HDU_CRN_INDEX = 2;
	public static int HDU_HDUUID_INDEX = 3;
	public static int HDU_APPV_INDEX = 4;
	public static int HDU_GCM_INDEX = 5;
	public static int HDU_CDT_INDEX = 6;
	
	/**
	 * defined index To access all database values From Location
	 */
	public static int LOC_HUID_INDEX = 1;
	public static int LOC_LAT_INDEX = 2;
	public static int LOC_LOGT_INDEX = 3;
	public static int LOC_ALT_INDEX = 4;
	public static int LOC_ADD_INDEX = 5;
	public static int LOC_CDT_INDEX = 6;
	
	/**
	 * defined index To access all database values From TRIPUSER
	 */
	public static int TUSER_UUID_INDEX = 1;
	public static int TUSER_CDT_INDEX = 2;

	/**
	 * defined index To access all database values From TRIP APP DATA
	 */
	public static int TRAPP_ID_INDEX = 1;
	public static int TRAPP_UUID_INDEX = 2;
	public static int TRAPP_MXSPD_INDEX = 3;
	public static int TRAPP_MXRPM_INDEX = 4;
	public static int TRAPP_STLOC_INDEX = 5;
	public static int TRAPP_ENDLOC_INDEX = 6;
	public static int TRAPP_STDTT_INDEX = 7;
	public static int TRAPP_ENDDTT_INDEX = 8;
	public static int TRAPP_ENGRNT_INDEX = 9;
	public static int TRAPP_STFUL_INDEX = 10;
	public static int TRAPP_ENDFUL_INDEX = 11;
	public static int TRAPP_STDIST_INDEX = 12;
	public static int TRAPP_ENDIST_INDEX = 13;
	public static int TRAPP_STLAT_INDEX = 14;
	public static int TRAPP_ENLAT_INDEX = 15;
	public static int TRAPP_STLONG_INDEX = 16;
	public static int TRAPP_ENLONG_INDEX = 17;
	public static int TRAPP_CDT_INDEX = 18;
	
	
	
}
