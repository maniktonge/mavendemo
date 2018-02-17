/**
 * @file Location.java
 * @brief This class is used as location structure
 * @version 1.0
 * @date  24-Jun-2016
 * @author TEL
 * 
 */

package com.tbu.domain;

import java.util.Date;
/**
 * This class is used as location structure
 */
public class Location {
   
/**
 * location id of the uploaded location    
 */
private long location_id;
/** 
 * latitude , longitude and altitude co-ordinates of the uploaded location
 */  
private float	latitude;
private float	altitude;
private float	longitude;
/**
 * head unit Id of the vehicle
 */
private int	headunit_id;
/** 
 * address of the vehicle
 */
private String	address;
/***
 * @return the location_id
 */
public long getLocation_id() {
	return location_id;
}
/**
 * @param location_id the location_id to set
 */
public void setLocation_id(long location_id) {
	this.location_id = location_id;
}
/**
 * @return the latitude
 */
public float getLatitude() {
	return latitude;
}
/**
 * @param latitude the latitude to set
 */
public void setLatitude(float latitude) {
	this.latitude = latitude;
}
/**
 * @return the altitude
 */
public float getAltitude() {
	return altitude;
}
/**
 * @param altitude the altitude to set
 */
public void setAltitude(float altitude) {
	this.altitude = altitude;
}
/**
 * @return the longitude
 */
public float getLongitude() {
	return longitude;
}
/**
 * @param longitude the longitude to set
 */
public void setLongitude(float longitude) {
	this.longitude = longitude;
}
/**
 * @return the headunit_id
 */
public int getHeadunit_id() {
	return headunit_id;
}
/**
 * @param headunit_id the headunit_id to set
 */
public void setHeadunit_id(int headunit_id) {
	this.headunit_id = headunit_id;
}
/**
 * @return the address
 */
public String getAddress() {
	return address;
}
/**
 * @param address the address to set
 */
public void setAddress(String address) {
	this.address = address;
}
/**
 * @return the createddate
 */
public Date getCreateddate() {
	return createddate;
}
/**
 * @param createddate the createddate to set
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
 * @param status the status to set
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
 * @param message the message to set
 */
public void setMessage(String message) {
	this.message = message;
}
/**
 * date at which location details are uploaded
 */
private Date	createddate;
/**
 * message and status 
 */
private boolean status;
private String message;




}
