/** 
 * @file WebServiceController.java
 * @brief This class acts as a Controller for all the web Services
 * this class includes web service call for
 * register new user 
 * register new vehicle
 * upload new location to database from vehicle
 * get location on app
 * requesting new vehicle control features to vehicle
 * and updating back to app  
 * @version 1.0
 * @date  24-June-2016
 * @author TEL
 * 
 */

package com.tbu.controller;

import java.io.IOException;
import java.util.List;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import com.tbu.domain.CheckEmailResponse;
import com.tbu.domain.ForgotPasswordResponse;
import com.tbu.domain.HeadUnit;
import com.tbu.domain.Location;
import com.tbu.domain.ResetPasswordRequest;
import com.tbu.domain.SignInRequest;
import com.tbu.domain.SignInResponse;
import com.tbu.domain.TripAppData;
import com.tbu.domain.TripAppDataRes;
import com.tbu.domain.TripDetailRequest;
import com.tbu.domain.TripDetailResponse;
import com.tbu.domain.TripUser;
import com.tbu.domain.TripUserRes;
import com.tbu.domain.UpdatePasswordRequest;
import com.tbu.domain.User;
import com.tbu.domain.UserProfileResponse;
import com.tbu.services.CommomService;

/**
 * This class acts as a controller for all the web service functions
 * 
 */
@RestController
public class WebServiceController {

	@Autowired
	CommomService commonservice;

	private static final Logger logger = Logger.getLogger(WebServiceController.class);

	/**
	 * this request maps for registering new user
	 * 
	 * @param user
	 * 
	 * @returns user
	 */
	@RequestMapping(value = "/registeruser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody User registerUser(@RequestBody User user) {

		logger.info("Calling Register User ");
		return commonservice.registerUser(user);
	}

	/**
	 * this request maps for registering new vehicle
	 * 
	 * @param vehicle
	 * 
	 * @returns vehicle
	 */
	@RequestMapping(value = "/registervehicle", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody HeadUnit registerVehicle(@RequestBody HeadUnit hdunit) {
		logger.info("Calling Register Vehicle ");
		return commonservice.registerVehicle(hdunit);
	}

	/**
	 * this request maps for getting location on mobile app
	 * 
	 * @param userId
	 * 
	 * @returns location
	 */
	@RequestMapping(value = "/getlocation/{userid}")
	public @ResponseBody Location locationbyid(@PathVariable int userid) {
		logger.info("Calling Register getlocation ");

		return commonservice.getLocationByID(userid);
	}

	/**
	 * this request maps for uploading the vehicle location from headUnit
	 * 
	 * @param hdUnit
	 * 
	 * @returns hdUnit
	 */
	@RequestMapping(value = "/uploadlocation", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody Location uploadVehicleLocation(@RequestBody Location location) {

		logger.info("Calling Register uploadlocation ");
		return commonservice.uploadLocation(location);
	}

	/**
	 * this request maps for requesting vehicle control actions such as
	 * Head lights ON/OFF, Door LOCK/UNLOCK , TRUNK OPEN/CLOSE
	 * 
	 * @param (long userId, String fetaureType, String featureStatus)
	 * 
	 * @returns user
	 */
	@RequestMapping(value = "/requestvehiclecontrol", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody User requestVehicleControl(@RequestBody User user) {

		logger.info("Calling Register requestvehiclecontrol ");

		return commonservice.requestVehicleControl(user.getUserid(), user.getFeaturetype(), user.getFeaturestatus());
	}

	/**
	 * this request maps for maps for updating vehicle control actions status
	 * such as Head lights ON/OFF, Door LOCK/UNLOCK, TRUNK OPEN/CLOSE back to
	 * mobile app
	 * 
	 * @param (long
	 *            hdUnitID, String fetaureType, String featureStatus)
	 * 
	 * @returns hdUnit
	 */
	@RequestMapping(value = "/updatecontrolstatus", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody HeadUnit updateControlStatus(@RequestBody HeadUnit hdunit) {
		logger.info("Calling Register updatecontrolstatus ");
		return commonservice.updateControlStatus(hdunit.getHeadunitid(), hdunit.getFeaturetype(),
				hdunit.getFeaturestatus());
	}

	/**
	 * this request maps for maps for updating vehicle control actions status
	 * such as , AC ON/OFF,with AC temperature control and fan speed control
	 * back on mobile app
	 * 
	 * @param (long
	 *            hdUnitID, String fetaureType, String featureStatus)
	 * 
	 * @returns hdUnit
	 */

	@RequestMapping(value = "/requestvehicleaccontrol", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody User requestVehicleACControl(@RequestBody User user) {

		logger.info("Calling Register requestvehicleaccontrol ");

		return commonservice.requestVehicleACControl(user.getUserid(), user.getFeaturetype(), user.getFeaturestatus(),
				user.getActemp(), user.getAcfanspeed(), user.getRearactemp());
	}

	/**
	 * This request maps for maps for updating vehicle control actions status
	 * such as AC ON/OFF,with AC temperature control and fan speed control back
	 * on mobile app
	 * 
	 * @param (long
	 *            hdUnitID, String fetaureType, String featureStatus)
	 * 
	 * @returns hdUnit
	 * 
	 */
	@RequestMapping(value = "/updateaccontrolstatus", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody HeadUnit updateACControlStatus(@RequestBody HeadUnit hdunit) {
		logger.info("Calling Register updateaccontrolstatus ");

		return commonservice.updateACControlStatus(hdunit.getHeadunitid(), hdunit.getFeaturetype(),
				hdunit.getFeaturestatus(), hdunit.getActemp(), hdunit.getAcfanspeed(), hdunit.getRearactemp());
	}

	/**
	 * This request maps for  updating gcm key using userid to database
	 * @param (User user)
	 * 
	 * @returns GcmupdateResponse
	 * 
	 */
	@RequestMapping(value = "/gcmkeyupdate", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody UserProfileResponse updateGCMforuser(@RequestBody User user) {
		logger.info("Calling updateGCMforuser ");
		return commonservice.updategcmKey(user);

	}
	
	/**
	 * This request maps for  updating user profile using userid to database
	 * @param (User user)
	 * 
	 * @returns UserProfileResponse
	 * 
	 */
	@RequestMapping(value = "/profileupdate", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody UserProfileResponse updateUserProfile(@RequestBody User user) {
		logger.info("Calling updateUserProfile======== ");
		return commonservice.updateProfile(user);
	}
	/**
	 * This request maps for  updating gcm key using hdunitId to database
	 * @param ()
	 * 
	 * @returns GcmupdateResponse
	 * 
	 */
	@RequestMapping(value = "/gcmkeyupdatehdunit", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody UserProfileResponse updateGCMforhdunit(@RequestBody HeadUnit hdUnit) {
		logger.info("Calling updateGCMforHdUnit ");
		return commonservice.updategcmKeyhdUnit(hdUnit);
	}
	/**
	 * This request maps for  registering trip app
	 * @param
	 * 
	 * @returns TripUserRes
	 * 
	 */
	@RequestMapping(value = "/registertripuser", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody TripUserRes registerTripapp(@RequestBody TripUser tripuser) {
		logger.info("Calling Trip App Registration================");
		return commonservice.registerTripUser(tripuser);
	}
	
	/**
	 * This request maps for  uploading trip app data
	 * @param
	 * 
	 * @returns TripUserRes
	 * 
	 */
	
	@RequestMapping(value = "/uploadtripData", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody  TripAppDataRes uploadTripData(@RequestBody TripAppData tripappdata) {
		logger.info("Calling Trip App Registration================");
		return commonservice.uploadTripData(tripappdata);
	}
	
	/**
	 * This request maps for  resetting password to sign in to database
	 * @param ()
	 * 
	 * @returns {@link ForgotPasswordResponse}
	 * 
	 */
	@RequestMapping(value = "/forgotpassword", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody ForgotPasswordResponse forgotPassword(@RequestBody ResetPasswordRequest resetpwdreq) {
			logger.info("Calling forgotpassword method "+resetpwdreq.getEmailid());
		return commonservice.forgotPassword(resetpwdreq);

	}
	
	
	/**
	 * This request maps for  check email is present in our record or not
	 * @param ()
	 * 
	 * @returns {@link CheckEmailResponse}
	 * 
	 */
	@RequestMapping(value = "/checkemail", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody CheckEmailResponse checkemail(@RequestBody ResetPasswordRequest resetpwdreq) {
			logger.info("Calling checkemail method "+resetpwdreq.getEmailid());
		return commonservice.checkemail(resetpwdreq);

	}
	
	/**
	 * This request maps for  update the password using emaild
	 * @param ()
	 * 
	 * @returns {@link CheckEmailResponse}
	 * 
	 */
	@RequestMapping(value = "/updatepassword", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody CheckEmailResponse updatepassword(@RequestBody UpdatePasswordRequest uppwdreq) {
			logger.info("Calling updatepassword method "+uppwdreq.getEmailid());
		return commonservice.updatepassword(uppwdreq);

	} 
	
	/**
	 * This request maps for  signin user 
	 * @param ()
	 * 
	 * @returns {@link SignInResponse}
	 * 
	 */
	@RequestMapping(value = "/signin", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody SignInResponse signinUser(@RequestBody SignInRequest sgninreq) {
			logger.info("Calling signin method "+sgninreq.getEmailid());
		     return commonservice.signinUser(sgninreq);

	} 
	
	/**
	 * This request maps for getting all details of tripapp
	 * @param trequest
	 * @return
	 */
	@RequestMapping(value = "/gettripappdata", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List <TripDetailResponse> getTripappData(@RequestBody TripDetailRequest trequest) {
		logger.info("Calling gettripappdata method ");
		return commonservice.getTripappData(trequest);
	}

	
	/**
	 * This request maps for getting all details of tripapp
	 *
	 */
	@RequestMapping(value = "/getalltripappdetail", method = RequestMethod.POST, produces = "application/json")
	public @ResponseBody List <TripDetailResponse> getAlltripappdetail(@RequestBody TripDetailRequest trequest) throws IOException {
		logger.info("Calling getAlltripappdetail method ");
		return commonservice.getAllTripappData(trequest);
	}
}
