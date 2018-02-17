/** 
 * @file CommomServiceImpl.java
 * @brief This class implements CommomService interface
 * @version 1.0
 * @date  24-Jun-2016
 * @author TEL
 * 
 */

package com.tbu.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;

import com.tbu.dao.CommonDao;
import com.tbu.domain.CheckEmailResponse;
import com.tbu.domain.Epb;
import com.tbu.domain.ForgotPasswordResponse;
import com.tbu.domain.HeadUnit;
import com.tbu.domain.Location;
import com.tbu.domain.ResetPasswordRequest;
import com.tbu.domain.SignInRequest;
import com.tbu.domain.SignInResponse;
import com.tbu.domain.SystemAdmin;
import com.tbu.domain.TripAppData;
import com.tbu.domain.TripAppDataRes;
import com.tbu.domain.TripDetailRequest;
import com.tbu.domain.TripDetailResponse;
import com.tbu.domain.TripUser;
import com.tbu.domain.TripUserRes;
import com.tbu.domain.UpdatePasswordRequest;
import com.tbu.domain.User;
import com.tbu.domain.UserProfileResponse;

public class CommomServiceImpl implements CommomService {
	/**
	 * This class implements CommomService interface an provides implementation
	 * for its methods
	 */
	@Autowired
	CommonDao transactiondao;

	/**
	 * gets called when new user is to be registered
	 * 
	 */
	@Override
	public User registerUser(User user) {
		return transactiondao.registerUser(user);
	}

	/**
	 * gets called when new vehicle is to be registered
	 */

	@Override
	public HeadUnit registerVehicle(HeadUnit hdunit) {
		return transactiondao.registerVehicle(hdunit);
	}

	/**
	 * gets called when user id provided and location of the vehicle is to be
	 * traced from mobile app
	 */
	@Override
	public Location getLocationByID(int userid) {
		return transactiondao.getLocationByID(userid);
	}

	/**
	 * gets called when location details are to be uploaded from vehicle
	 */

	@Override
	public Location uploadLocation(Location location) {
		return transactiondao.uploadLocation(location);
	}

	/**
	 * gets called when authentication is required while login
	 */
	@Override
	public boolean isUserAuthenticate(String username, String password) {
		return transactiondao.isUserAuthenticate(username, password);
	}

	/**
	 * gets called when GCM message for vehicle control actions as door ,
	 * headlight ON/OFF, Car Trunk OPEN/CLOSE need to be sent
	 * 
	 * @param userId,
	 *            featureType, featureStatus
	 */
	@Override
	public User requestVehicleControl(long userId, String featureType, String featureStatus) {

		return transactiondao.requestVehicleControl(userId, featureType, featureStatus);
	}

	/**
	 * gets called when action control status needs to be sent from head unit to
	 * mobile app
	 * 
	 * @param headUnitId,
	 *            featureType,featureStatus
	 */
	@Override
	public HeadUnit updateControlStatus(long headUnitId, String featureType, String featureStatus) {
		return transactiondao.updateControlStatus(headUnitId, featureType, featureStatus);
	}

	/**
	 * gets called when GCM message for vehicle control actions as AC ON/OFF
	 * with temperature control needs to be sent to vehicle
	 * 
	 * @param userId,
	 *            ACfeatureType, ACStatus, ACtemp, ACFanSpeed
	 * @returns User
	 */

	@Override
	public User requestVehicleACControl(long userId, String ACfeatureType, String ACStatus, String ACtemp,
			String ACFanSpeed, String rearACTemp) {
		return transactiondao.requestVehicleACControl(userId, ACfeatureType, ACStatus, ACtemp, ACFanSpeed, rearACTemp);
	}

	/**
	 * gets called when GCM message as status for vehicle control actions as AC
	 * ON/OFF with temperature control needs to be sent back to mobile app
	 * 
	 * @param headUnitId,
	 *            ACfeatureType, ACStatus, ACtemp, ACFanSpeed
	 * @returns HeadUnit
	 */
	@Override
	public HeadUnit updateACControlStatus(long headUnitId, String ACfeatureType, String ACStatus, String ACtemp,
			String ACFanSpeed, String rearACTemp) {
		return transactiondao.updateACControlStatus(headUnitId, ACfeatureType, ACStatus, ACtemp, ACFanSpeed,
				rearACTemp);
	}

	/***
	 * Return all Registered User details from DAO Layer of Application ie From
	 * Database
	 */
	@Override
	public List<User> getallUserDetail() {
		return transactiondao.getallUserDetail();
	}

	/***
	 * Return all Registered Headunit details from DAO Layer of Application ie
	 * From Database
	 */
	@Override
	public List<HeadUnit> getallHeadUnitDetail() {
		return transactiondao.getallHeadUnitDetail();
	}

	/**
	 * configure the DB for user with credentials
	 * 
	 * @param adminusername,adminpwd,dbname
	 * @returns true
	 */
	@Override
	public boolean isdbConfigured(SystemAdmin sysadmin) {
		return transactiondao.isdbConfigured(sysadmin);
	}

	/**
	 * Check db is configured or not
	 * 
	 * @returns true
	 */

	@Override
	public boolean checkdbconfig() {
		return transactiondao.checkdbconfig();
	}

	/**
	 * delete the user using userid
	 * 
	 * @param userid
	 * @returns true
	 */
	@Override
	public boolean deleteUser(int userid) {
		return transactiondao.deleteUser(userid);
	}

	/**
	 * Update GCM key using user_id
	 * 
	 * @param user
	 * @return
	 */

	@Override
	public UserProfileResponse updategcmKey(User user) {
		return transactiondao.updategcmKey(user);
	}

	/**
	 * This Method validate IP Address
	 * 
	 * @return boolean
	 */
	@Override
	public boolean validate(String ip) {
		return transactiondao.validate(ip);
	}

	/**
	 * This method is used to update the existing user profile
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public UserProfileResponse updateProfile(User user) {
		return transactiondao.updateProfile(user);
	}

	/**
	 * delete the headunit using uesrid
	 * 
	 * @param userid
	 * @return
	 */
	@Override
	public boolean deleteHeadunit(int userid) {

		return transactiondao.deleteHeadunit(userid);
	}

	/**
	 * update the gcmkey
	 * 
	 * @param hdunit
	 * @return
	 */

	@Override
	public UserProfileResponse updategcmKeyhdUnit(HeadUnit hdunit) {

		return transactiondao.updategcmkeyhdUnit(hdunit);
	}

	/**
	 * This Method is used to register the user
	 * 
	 * @param tripuser
	 * @return
	 */
	@Override
	public TripUserRes registerTripUser(TripUser tripuser) {

		return transactiondao.registerTripUser(tripuser);
	}

	/**
	 * This method is used to upload trip details on server
	 * 
	 * @param tripappdata
	 * @return
	 */
	@Override
	public TripAppDataRes uploadTripData(TripAppData tripappdata) {

		return transactiondao.uploadTripData(tripappdata);
	}

	/**
	 * This method is used get all trip data on the basis of date
	 * 
	 * @param trequest
	 * @return
	 */
	@Override
	public List<TripDetailResponse> getTripappData(TripDetailRequest trequest) {
		return transactiondao.getTripappData(trequest);
	}

	/**
	 * This method is used get all trip data
	 * 
	 * @param trequest
	 * @return
	 */

	@Override
	public List<TripDetailResponse> getAllTripappData(TripDetailRequest trequest) {
		return transactiondao.getAllTripappData(trequest);
	}

	/**
	 * This method is used to check the email is valid or not
	 * 
	 * @param resetpwdreq
	 * @return
	 */
	@Override
	public CheckEmailResponse checkemail(ResetPasswordRequest resetpwdreq) {
		return transactiondao.checkemail(resetpwdreq);
	}

	/**
	 * update the password using emaild
	 * 
	 * @param uppwdreq
	 * @return
	 */
	@Override
	public CheckEmailResponse updatepassword(UpdatePasswordRequest uppwdreq) {

		return transactiondao.updatepassword(uppwdreq);
	}

	/**
	 * sign in user using emaild and pwd
	 * 
	 * @param sgninreq
	 * @return
	 */
	@Override
	public SignInResponse signinUser(SignInRequest sgninreq) {

		return transactiondao.signinUser(sgninreq);
	}

	/**
	 * This method is used to reset password functionality integrate with
	 * emailid
	 * 
	 * @param resetpwdreq
	 * @return
	 */
	@Override
	public ForgotPasswordResponse forgotPassword(ResetPasswordRequest resetpwdreq) {

		return transactiondao.forgotPassword(resetpwdreq);
	}

	@Override
	public List<Epb> getallEpbDetail() {
		// TODO Auto-generated method stub
		return transactiondao.getallEpbDetail();
	}
}
