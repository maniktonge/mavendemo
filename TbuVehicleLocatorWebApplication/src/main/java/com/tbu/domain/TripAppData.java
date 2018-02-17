/**
 * @file TripAppData.java
 * @brief This class is used to store all details
 * about Trip APP
 * @version 1.0
 * @date  26-July-2016
 * @author TEL
 **/

package com.tbu.domain;
/**
 * This class is used to store all details
 * about Trip APP
 **/

public class TripAppData {

	/**
	 * 
	 * @return
	 */
	public int getTripid() {
		return tripid;
	}
/**
 * 
 * @param tripid
 */
	public void setTripid(int tripid) {
		this.tripid = tripid;
	}

/**
 * 
 * @return
 */
	public String getMaxspeed() {
		return maxspeed;
	}
/**
 * 
 * @param maxspeed
 */
	public void setMaxspeed(String maxspeed) {
		this.maxspeed = maxspeed;
	}
/**
 * 
 * @return
 */
	public String getMaxrpm() {
		return maxrpm;
	}
/**
 * 
 * @param maxrpm
 */
	public void setMaxrpm(String maxrpm) {
		this.maxrpm = maxrpm;
	}
/**
 * 
 * @return
 */
	public String getEngineruntime() {
		return engineruntime;
	}
/**
 * 
 * @param engineruntime
 */
	public void setEngineruntime(String engineruntime) {
		this.engineruntime = engineruntime;
	}
/**
 * 
 * @return
 */
	public String getFuellevelstart() {
		return fuellevelstart;
	}
	/**
	 * 
	 * @param fuellevelstart
	 */

	public void setFuellevelstart(String fuellevelstart) {
		this.fuellevelstart = fuellevelstart;
	}
/**
 * 
 * @return
 */
	public String getFuellevelend() {
		return fuellevelend;
	}
	/**
	 * 
	 * @param fuellevelend
	 */

	public void setFuellevelend(String fuellevelend) {
		this.fuellevelend = fuellevelend;
	}
/**
 * 
 * @return
 */
	public String getStartdistance() {
		return startdistance;
	}
/**
 * 
 * @param startdistance
 */
	public void setStartdistance(String startdistance) {
		this.startdistance = startdistance;
	}
/**
 * 
 * @return
 */
	public String getEnddistance() {
		return enddistance;
	}
/**
 * 
 * @param enddistance
 */
	public void setEnddistance(String enddistance) {
		this.enddistance = enddistance;
	}
/**
 * 
 * @return
 */
	public String getStartlatitude() {
		return startlatitude;
	}
/**
 * 
 * @param startlatitude
 */
	public void setStartlatitude(String startlatitude) {
		this.startlatitude = startlatitude;
	}
/**
 * 
 * @return
 */
	public String getEndlatitude() {
		return endlatitude;
	}
/**
 * 
 * @param endlatitude
 */
	public void setEndlatitude(String endlatitude) {
		this.endlatitude = endlatitude;
	}
/**
 * 
 * @return
 */
	public String getStartlongitude() {
		return startlongitude;
	}
/**
 * 
 * @param startlongitude
 */
	public void setStartlongitude(String startlongitude) {
		this.startlongitude = startlongitude;
	}
/**
 * 
 * @return
 */
	public String getEndlongitude() {
		return endlongitude;
	}
/**
 * 
 * @param endlongitude
 */
	public void setEndlongitude(String endlongitude) {
		this.endlongitude = endlongitude;
	}
/**
 * 
 * @return
 */
	public String getStartlocation() {
		return startlocation;
	}
/**
 * 
 * @param startlocation
 */
	public void setStartlocation(String startlocation) {
		this.startlocation = startlocation;
	}
/**
 * 
 * @return
 */
	public String getEndlocation() {
		return endlocation;
	}
/**
 * 
 * @param endlocation
 */
	public void setEndlocation(String endlocation) {
		this.endlocation = endlocation;
	}

	
	public String getTuuid() {
		return tuuid;
	}
	
	public void setTuuid(String tuuid) {
		this.tuuid = tuuid;
	}

	
	public String getTstartdatetime() {
		return tstartdatetime;
	}
	public void setTstartdatetime(String tstartdatetime) {
		this.tstartdatetime = tstartdatetime;
	}
	public String getTenddatetime() {
		return tenddatetime;
	}
	public void setTenddatetime(String tenddatetime) {
		this.tenddatetime = tenddatetime;
	}
	private int tripid;
	private String tstartdatetime;
	private String tenddatetime;
	private String maxspeed;
	private String maxrpm;
	private String engineruntime;
	private String fuellevelstart;
	private String fuellevelend;
	private String startdistance;
	private String enddistance;
	private String startlatitude;
	private String endlatitude;
	private String startlongitude;
	private String endlongitude;
	private String startlocation;
	private String endlocation;
	private String tuuid;
	
	
}
