/** 
 * @file CommonDao.java
 * @brief This is an interface for data accessing object for consuming the web Services
 * @version 1.0
 * @date  24-June-2016
 * @author TEL
 * 
 */

package com.tbu.dao;

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
 * 
 * This is an interface for data accessing object to consume web services
 * 
 * @author TEL
 * 
 **/
public interface CommonDao {

	public User registerUser(User user);

	public HeadUnit registerVehicle(HeadUnit hdunit);

	public Location getLocationByID(int userid);

	public Location uploadLocation(Location location);

	public boolean isUserAuthenticate(String username, String password);

	public List<User> getallUserDetail();

	public List<HeadUnit> getallHeadUnitDetail();
	
	public List<Epb> getallEpbDetail();

	public boolean isdbConfigured(SystemAdmin sysadmin);

	public User requestVehicleControl(long userid, String featuretype, String featurestatus);

	public HeadUnit updateControlStatus(long headunitid, String featuretype, String featurestatus);

	public User requestVehicleACControl(long userid, String acfeaturetype, String acstatus, String actemp,
			String acfantemp, String rearactemp);

	public HeadUnit updateACControlStatus(long headunitid, String acfeaturetype, String acstatus, String actemp,
			String acfantemp, String rearactemp);

	public boolean checkdbconfig();

	public boolean deleteUser(int userid);
	
	public boolean deleteHeadunit(int userid);

	public UserProfileResponse updategcmKey(User user);

	public boolean validate(final String ip);
	
	public UserProfileResponse updateProfile(User user);
	
	public UserProfileResponse updategcmkeyhdUnit (HeadUnit hdunitId);
	
	public TripUserRes registerTripUser(final TripUser tripuser);
	
	public TripAppDataRes uploadTripData(final TripAppData tripappdata);
	
	public ForgotPasswordResponse forgotPassword(ResetPasswordRequest resetpwdreq);
	
	public CheckEmailResponse checkemail (ResetPasswordRequest resetpwdreq);
	
	public List<TripDetailResponse> getTripappData(TripDetailRequest trequest);
	
	public List<TripDetailResponse> getAllTripappData(TripDetailRequest trequest);
	
	public CheckEmailResponse updatepassword(UpdatePasswordRequest uppwdreq);
	
	public SignInResponse signinUser(SignInRequest sgninreq);
	
	
	
	

}
