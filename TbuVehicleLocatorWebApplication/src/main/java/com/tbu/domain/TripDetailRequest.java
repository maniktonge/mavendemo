/**
 * @file TripDetailRequest.java
 * @brief This class is used to recive request from app 
 * @version 1.0
 * @date  26-July-2016
 * @author TEL
 **/
package com.tbu.domain;

public class TripDetailRequest {
	/**
	 * Tstardate holds the date
	 */
	private String tstartdatetime;
	
	/**
	 * tuuid 
	 * trip uuid
	 */
	private String tuuid;

	/**
	 * 
	 * @return tstartdatetime
	 */
	public String getTstartdatetime() {
		return tstartdatetime;
	}
	/**
	 * 
	 * @param tstartdatetime
	 */

	public void setTstartdatetime(String tstartdatetime) {
		this.tstartdatetime = tstartdatetime;
	}
	

	
/**
 * @return
 */
	public String getTuuid() {
		return tuuid;
	}
	
/**
 * @param tuuid
 */
	public void setTuuid(String tuuid) {
		this.tuuid = tuuid;
	}

	
}
