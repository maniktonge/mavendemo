/** 
 * @file CommomService.java
 * @brief This is an interface for consuming web service
 * @version 1.0
 * @date  24-Jun-2016
 * @author TEL
 * 
 */

package com.tbu.services;

import java.util.List;
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

/**
 * This is an interface for consuming web service registering new user and
 * vehicle upload new location from vehicle and receiving at mobile app
 * requesting vehicle feature and updating with GCM push notification
 */
public interface CommomService {

	public User registerUser(User user);

	public HeadUnit registerVehicle(HeadUnit hdunit);

	public Location getLocationByID(int userid);

	public Location uploadLocation(Location location);

	public boolean isUserAuthenticate(String username, String password);

	public User requestVehicleControl(long userId, String featureType, String featureStatus);

	public HeadUnit updateControlStatus(long headUnitId, String featureType, String featureStatus);

	public User requestVehicleACControl(long userId, String ACfeatureType, String ACStatus, String ACtemp,
			String ACFanSpeed, String rearACTemp);

	public HeadUnit updateACControlStatus(long headUnitId, String ACfeatureType, String ACStatus, String ACtemp,
			String ACFanSpeed, String rearACTemp);

	public List<User> getallUserDetail();

	public List<HeadUnit> getallHeadUnitDetail();
	
	public List<Epb> getallEpbDetail();

	public boolean isdbConfigured(SystemAdmin sysadmin);

	public boolean checkdbconfig();

	public boolean deleteUser(int userid);
	
	public boolean deleteHeadunit(int userid);

	public UserProfileResponse updategcmKey(User user);
	
	public UserProfileResponse updateProfile(User user);

	public boolean validate(String ip);
	
	public UserProfileResponse updategcmKeyhdUnit (HeadUnit hdunit);
	
	public TripUserRes registerTripUser(final TripUser tripuser);
	
	public TripAppDataRes uploadTripData(final TripAppData tripappdata);
	
	public ForgotPasswordResponse forgotPassword (ResetPasswordRequest resetpwdreq);
	
	public CheckEmailResponse updatepassword(UpdatePasswordRequest uppwdreq);

	public List<TripDetailResponse> getTripappData(TripDetailRequest trequest);
	
	public List<TripDetailResponse> getAllTripappData(TripDetailRequest trequest);
	
	public CheckEmailResponse checkemail (ResetPasswordRequest resetpwdreq);
	
	public SignInResponse signinUser( SignInRequest sgninreq);
	
	
}
