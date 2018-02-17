/** 
 * @file CommonDaoImpl.java implements CommonDao
 * @brief This class implements CommonDao interface
 * and provides definition to all the methods of interface Commomdao 
 * @version 1.0
 * @date  24-Jun-2016
 * @author TEL
 * 
 */

package com.tbu.dao;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Properties;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletContext;

import org.apache.log4j.Logger;
import org.json.simple.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.PreparedStatementCreator;
import org.springframework.jdbc.core.ResultSetExtractor;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.datasource.SimpleDriverDataSource;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;

import com.google.android.gcm.server.Message;
import com.google.android.gcm.server.Result;
import com.google.android.gcm.server.Sender;
import com.tbu.constant.PasswordEncryptionUtil;
import com.tbu.constant.Utility;
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
 * This class implements CommonDao interface and provides definition for
 * unimplemented methods registering new user registering new vehicle uploading
 * location from vehicle and getting it on mobile app and requesting vehicle
 * features FEATURE_TYPE_AC = "1"; FEATURE_TYPE_DOOR = "2";
 * FEATURE_TYPE_HEADLIGHT = "3"; FEATURE_TYPE_TRUNK = "4";
 * @author TEL
 *
 */
public class CommonDaoImpl implements CommonDao {
	@Autowired
	ServletContext context;
	private JdbcTemplate jdbcTemplate;
	SimpleDriverDataSource datasource;
	private static final Logger logger = Logger.getLogger(CommonDaoImpl.class);
	private Message gcm_message;
	private static final String MESSAGE_KEY = "gcm_msg";
	private static String PIN = "";
	Utility utilityObj = new Utility();

	// Pattern For valid IP address
	private static final String IPADDRESS_PATTERN = "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\." + "([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\."
			+ "([01]?\\d\\d?|2[0-4]\\d|25[0-5])$";

	private int requestedHeadunitId = 0;
	private boolean headunitDetailsPresent = false;
	
	/**
	 * Function which returns current timeStamp
	 * 
	 * @return date
	 */

	private static java.sql.Timestamp getCurrentDate() {

		java.sql.Timestamp date = new java.sql.Timestamp(new java.util.Date().getTime());
		return date;
	}

	/**
	 * Function to register new user
	 * 
	 * @param user
	 * 
	 * @return user
	 * 
	 * @see com.tbu.dao.CommonDao#registerUser(com.tbu.domain.User)
	 */

	public User registerUser(final User user) {
		// datasource is used to connect with database
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		// check if user exists in database with emailId
		String sql = "select count(1) from user where emailid = ? ";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { user.getEmailid() }, Integer.class);
		// if user exists, return userId
		if (result > 0) {
			String sql_userid = "select user_id from user where emailid = ? ";
			int userid = jdbcTemplate.queryForObject(sql_userid, new Object[] { user.getEmailid() }, Integer.class);
			user.setUserid(userid);
			user.setMessage("Email Id Already Registered");
			user.setStatus(false);
			logger.info(" EMAILID ALREADY REGISTERED WITH USERID  :" + userid);
			;
		} else {

			if (user.getGcmregistartionkey().isEmpty()) {
				user.setMessage("GCM KEY SHOULD NOT BE EMPTY ");
				user.setStatus(false);
				logger.info("GCM KEY SHOULD NOT BE EMPTY ");
			} else {

				// if not, create new entry in database
				final PreparedStatementCreator psc = new PreparedStatementCreator() {
					@Override
					public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
						final PreparedStatement ps = connection.prepareStatement(
								"INSERT INTO user (username,emailid,mobileno,uuid,appversion,gcmregistartionkey,createddate,password)"
										+ " values (?,?,?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
						// set user parameters
						ps.setString(Utility.USER_NAME_INDEX, user.getUsername());
						ps.setString(Utility.USER_EMAIL_INDEX, user.getEmailid());
						ps.setString(Utility.USER_MBNO_INDEX, user.getMobileno());
						ps.setString(Utility.USER_UUID_INDEX, user.getUuid());
						ps.setString(Utility.USER_APPV_INDEX, user.getAppversion());
						ps.setString(Utility.USER_GCM_INDEX, user.getGcmregistartionkey());
						ps.setTimestamp(Utility.USER_CDT_INDEX, getCurrentDate());
						//encrypted password to store in db
						ps.setString(Utility.USER_PWD_INDEX,
						 PasswordEncryptionUtil.encryptPassword(user.getPassword()));
						//ps.setString(Utility.USER_PWD_INDEX, user.getPassword());
						return ps;
					}
				};
				// return auto generated userId to user
				final KeyHolder holder = new GeneratedKeyHolder();

				jdbcTemplate.update(psc, holder);

				final long userid = holder.getKey().longValue();
				// if user created, set status
				if (userid > 0) {
					user.setStatus(true);
				}
				// set userId, date and message
				user.setUserid(userid);
				user.setCreateddate(getCurrentDate());
				user.setMessage("NEW USER REGISTERED WITH USER ID : " + userid);
				logger.info("NEW USER REGISTERED WITH USER ID : " + userid);
			}

		}
		return user;
	}

	/**
	 * Function to register new vehicle
	 * 
	 * @param hdUnit
	 * 
	 * @returns hdUnit
	 * 
	 * @see com.tbu.dao.CommonDao#registerVehicle(com.tbu.domain.HeadUnit)
	 */
	@Override
	public HeadUnit registerVehicle(final HeadUnit hdunit) {
		// datasource is used to connect with database
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		// check user exists based on hduuid
		String sql = "select count(1) from headunit where hduuid = ? ";

		int result = jdbcTemplate.queryForObject(sql, new Object[] { hdunit.getHduuid() }, Integer.class);

		if (result > 0) {
			// if head unit already registered , return headUnitId
			// if record exists, update gcm key 
			String update_gcmkey = "UPDATE headunit SET gcmregistartionkey = ? WHERE hduuid =? ";
			
			int rowcount = jdbcTemplate.update(update_gcmkey,hdunit.getGcmregistartionkey(),hdunit.getHduuid()); 
			logger.info("GCM Updated "+rowcount);
			// get headunit_id where hduuid
			String sql_headUnitid = "select headunit_id from headunit where hduuid = ? ";
			int headunit_id = jdbcTemplate.queryForObject(sql_headUnitid, new Object[] { hdunit.getHduuid() },
					Integer.class);
			// return registered headUnit_id
			//hdunit.setStatus(false);
			//change false to true bcoz of flow change to avoide errror during pairing
			hdunit.setStatus(true);
			hdunit.setHeadunitid(headunit_id);
			hdunit.setMessage("PROVIDED HDUUID IS ALREADY REGISTERED WITH HEAD UNIT ID :" + headunit_id
					+ " FOR USER ID : " + hdunit.getUserid() + " HDUUID SHOULD BE UNIQUE ");
			logger.info("PROVIDED HDUUID IS ALREADY REGISTERED WITH HEAD UNIT ID :" + headunit_id + " FOR USER ID : "
					+ hdunit.getUserid() + " HDUUID SHOULD BE UNIQUE ");

		} else {

			if (hdunit.getGcmregistartionkey().isEmpty()) {
				hdunit.setStatus(false);
				hdunit.setMessage("GCM KEY SHOULD NOT BE EMPTY ");
				logger.info("GCM KEY SHOULD NOT BE EMPTY ");
			} else {

				// if not, register new vehicle
				final PreparedStatementCreator psc = new PreparedStatementCreator() {
					@Override
					// insert values in database
					public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
						final PreparedStatement ps = connection.prepareStatement(
								"INSERT INTO headunit (user_id,carname,hduuid,appversion,gcmregistartionkey,createddate)"
										+ " values (?,?,?,?,?,?)",
								Statement.RETURN_GENERATED_KEYS);
						// set parameters
						ps.setInt(Utility.HDU_UID_INDEX, hdunit.getUserid());
						ps.setString(Utility.HDU_CRN_INDEX, hdunit.getCarname());
						ps.setString(Utility.HDU_HDUUID_INDEX, hdunit.getHduuid());
						ps.setString(Utility.HDU_APPV_INDEX, hdunit.getAppversion());
						ps.setString(Utility.HDU_GCM_INDEX, hdunit.getGcmregistartionkey());
						ps.setTimestamp(Utility.HDU_CDT_INDEX, getCurrentDate());
						return ps;
					}
				};
				// return auto generated hdunitid
				final KeyHolder holder = new GeneratedKeyHolder();

				jdbcTemplate.update(psc, holder);
				// set status
				final long hdunitid = holder.getKey().longValue();
				if (hdunitid > 0) {
					hdunit.setStatus(true);
				}
				// set hdunitid , craetedDate, message
				hdunit.setHeadunitid(hdunitid);
				hdunit.setCreateddate(getCurrentDate());
				hdunit.setMessage("NEW HEAD UNIT REGISTERED WITH HEAD UNIT ID :" + hdunitid + " FOR USERID  : "
						+ hdunit.getUserid());

				logger.info("NEW HEAD UNIT REGISTERED WITH HEAD UNIT ID :" + hdunitid + " FOR USERID  : "
						+ hdunit.getUserid());
			}
		}
		return hdunit;
	}

	/**
	 * Function to get location on mobile app
	 * 
	 * @param userid
	 * 
	 * @return Location
	 * 
	 * @see com.tbu.dao.CommonDao#getLocationByID(int)
	 */

	@Override
	public Location getLocationByID(final int userid) {
		// datasource is used to connect with database
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		
		// check if location record exists for given userid
		String check_query = "select count(1) from location where headunit_id=(Select headunit_id from headunit where User_id= ?)";

		int result = jdbcTemplate.queryForObject(check_query, new Object[] { userid }, Integer.class);
		Location location = new Location();

		if (result > 0) {
			// if record exists, return location
			String sql = "select * from location where headunit_id= (Select headunit_id from headunit where User_id="
					+ userid + ")";

			return jdbcTemplate.query(sql, new ResultSetExtractor<Location>() {

				@Override
				public Location extractData(ResultSet rs) throws SQLException, DataAccessException {
					// set parameters
					if (rs.last()) {
						Location loc = new Location();
						loc.setLocation_id(rs.getInt("location_id"));
						loc.setAltitude(rs.getFloat("altitude"));
						loc.setLongitude(rs.getFloat("longitude"));
						loc.setLatitude(rs.getFloat("latitude"));
						loc.setAddress(rs.getString("address"));
						loc.setCreateddate(rs.getDate("createddate"));
						loc.setHeadunit_id(rs.getInt("headunit_id"));
						loc.setMessage("LAST UPLOADED RECORD FOR THE ID : " + userid);
						loc.setStatus(true);

						return loc;
					}

					return null;
				}

			});
		} else {
			// if no record found, set message and status to false

			location.setMessage("NO LOCATION RECORD FOUND FOR ID : : " + userid);
			location.setStatus(false);
		}
		return location;
	}

	/**
	 * Function to upload location details from vehicle
	 * 
	 * @param location
	 * 
	 * @return Location (non-Javadoc)
	 * 
	 * @see com.tbu.dao.CommonDao#uploadLocation(com.tbu.domain.Location)
	 */
	@Override
	public Location uploadLocation(final Location location) {
		// datasource is used to connect with database
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		
		// Check if provided headUnitId is registered or not
		String sql = "select count(1) from headunit where headunit_id = ? ";

		int result = jdbcTemplate.queryForObject(sql, new Object[] { location.getHeadunit_id() }, Integer.class);
		
		if (result > 0) {
		
			// if headUnitId is registered, upload location details
			final PreparedStatementCreator psc = new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
					final PreparedStatement ps = connection.prepareStatement(
							"INSERT INTO location(headunit_id,latitude,longitude,altitude,address,createddate) "
									+ "Values (?,?,?,?,?,?)",
							Statement.RETURN_GENERATED_KEYS);
					// set parameters
					ps.setLong(Utility.LOC_HUID_INDEX, location.getHeadunit_id());
					ps.setFloat(Utility.LOC_LAT_INDEX, location.getLatitude());
					ps.setFloat(Utility.LOC_LOGT_INDEX, location.getLongitude());
					ps.setFloat(Utility.LOC_ALT_INDEX, location.getAltitude());
					ps.setString(Utility.LOC_ADD_INDEX, location.getAddress());
					ps.setTimestamp(Utility.LOC_CDT_INDEX, getCurrentDate());

					return ps;
				}
			};
			// return auto generated locationID
			final KeyHolder holder = new GeneratedKeyHolder();

			jdbcTemplate.update(psc, holder);

			final long locationid = holder.getKey().longValue();
			if (locationid > 0) {
				// set message, status
				location.setLocation_id(locationid);
				location.setStatus(true);
				location.setMessage("NEW LOCATION RECORD UPLOADED FOR HEAD UNIT ID :" + location.getHeadunit_id());
			}
			logger.info("NEW LOCATION RECORD UPLOADED FOR HEAD UNIT ID :" + location.getHeadunit_id());
			
			//Send GCM notification
			//call function for the same
			sendGCMNotificationForNewLocation(location.getHeadunit_id(), location);			
			
		} else {
			// given headUnitId is not registered, set status and message
			location.setStatus(false);
			location.setMessage("NO DATA FOUND FOR HEAD UNIT ID :" + location.getHeadunit_id());
			logger.info("NO DATA FOUND FOR HEAD UNIT ID :" + location.getHeadunit_id());
		}

		return location;
	}

	/**
	 * This function will generate the GCM notification whenever the vehicle uploads the 
	 * new location
	 */
	@SuppressWarnings("unchecked")
	private void sendGCMNotificationForNewLocation(int headUnitId, Location locationByVehicle)
	{
		//Query for database		
		String check_query = "select count(1) from user where "
				+ "user_id = (SELECT user_id from headunit where headunit_id = ?)";
		
		//Query the database
		int result = jdbcTemplate.queryForObject(check_query, new Object[] { headUnitId }, Integer.class);
		
		if (result > 0) {
			// if userID is available, send the status
			// if userID is available, send the status
			// get GCM key
			String gcm_key_query = "select gcmregistartionkey from user where "
					+ "user_id = (SELECT user_id from headunit where headunit_id = ?)";
			
			String query_result = (String) jdbcTemplate.queryForObject(gcm_key_query, new Object[] { headUnitId },
					String.class);
			
			System.out.println("GCM KEY ============================= " + query_result);
			
			// if GCM key not null or empty
			// prepare message
			if (query_result != null && !query_result.isEmpty()) {
				
				Sender sender = new Sender(utilityObj.getGcmApiKeyVehiclelocatorapp());
				// build message based on location details
				JSONObject returnObj = new JSONObject();
				try {
					returnObj.put("featuretype", "newLocation");					
					returnObj.put("latitide", locationByVehicle.getLatitude());
					returnObj.put("longitude", locationByVehicle.getLongitude());
					returnObj.put("altitude", locationByVehicle.getAltitude());
					returnObj.put("address", locationByVehicle.getAddress());
					returnObj.put("currenttimestamp",getCurrentDate().toString());
					
				} catch (Exception e1) {
					logger.error("error in generating the GCM message- sendGCMNotificationForNewLocation function");
					e1.printStackTrace();
				}
				
				String prepare_msg = returnObj.toJSONString();
				
				gcm_message = new Message.Builder().timeToLive(30).delayWhileIdle(true)
						.addData(MESSAGE_KEY, prepare_msg).build();
				
				Result gcm_result = null;
				try {
					// get gcm_message and gcm_register key
					// send message to that gcm_register_key
					// set message and status
					gcm_result = sender.send(gcm_message, query_result, 1);
					logger.info("IN sendGCMNotificationForNewLocation fun: " + gcm_result.toString());					
					
				} catch (IOException e) {
					e.printStackTrace();
					logger.error("IN sendGCMNotificationForNewLocation fun: " + e.getMessage());
				}
			} else {
				logger.error("NO GCM KEY FOUND");
			}

		} else {
			// no userID data found for given headUnitID
			// set message and status			
			logger.info("NO USER DATA FOUND FOR  HEAD UNIT ID  : " + headUnitId);
		}
	}
	
	/**
	 * Function to check if user is authenticated
	 * 
	 * @param username,password
	 * @return boolean
	 * 
	 */

	@Override
	public boolean isUserAuthenticate(String username, String password) {
		// validate username and pwd with predefined credentials
		if (username.equals(Utility.USERNAME) && password.equals(Utility.PASSWORD)) {
			return true;

		} else {
			return false;
		}
	}

	/**
	 * Function to update the vehicle control status such as head light, door or
	 * car trunk status to mobile app this function send GCM push message to
	 * mobile app with registered GCM_REG_KEY from headUnit
	 * 
	 * @param headUnitId,featureType,featureStatus
	 * 
	 * @returns HeadUnit
	 */

	@SuppressWarnings("unchecked")
	@Override
	public HeadUnit updateControlStatus(long headUnitId, String featureType, String featureStatus) {
		// check if given headUnitId is registered with any userID
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		logger.info("Send updateControlStatus From Simulator To Find My Car For HeadLight-Door-Trunk ");
		HeadUnit hdunit = new HeadUnit();
		String check_query = "select count(1) from user where "
				+ "user_id = (SELECT user_id from headunit where headunit_id = ?)";

		int result = jdbcTemplate.queryForObject(check_query, new Object[] { headUnitId }, Integer.class);

		if (result > 0) {
			// if userID is available, send the status
			// get GCM key
			String gcm_key_query = "select gcmregistartionkey from user where "
					+ "user_id = (SELECT user_id from headunit where headunit_id = ?)";

			String query_result = (String) jdbcTemplate.queryForObject(gcm_key_query, new Object[] { headUnitId },
					String.class);

			System.out.println("GCM KEY ============================= " + query_result);
			// if GCM key not null or empty
			// prepare message
			if (query_result != null && !query_result.isEmpty()) {
				Sender sender = new Sender(utilityObj.getGcmApiKeyVehiclelocatorapp());
				logger.info(
						"IN updateControlStatus Create Sender Using : " + utilityObj.getGcmApiKeyVehiclelocatorapp());
				// create JSON object to send notification in json format
				JSONObject obj = new JSONObject();
				// build message based on featureType and featurestatus
				obj.put("featuretype", featureType);
				obj.put("featurestatus", featureStatus);
				String prepare_msg = obj.toJSONString();
				gcm_message = new Message.Builder().timeToLive(30).delayWhileIdle(true)
						.addData(MESSAGE_KEY, prepare_msg).build();
				Result gcm_result = null;
				try {
					// get gcm_message and gcm_register key
					// send message to that gcm_register_key
					// set message and status
					gcm_result = sender.send(gcm_message, query_result, 1);
					logger.info("In updateControlStatus GCM Result From GOOGLE API : " + gcm_result.toString());
					hdunit.setGcmregistartionkey(query_result);
					hdunit.setHeadunitid(headUnitId);
					hdunit.setStatus(true);
					hdunit.setMessage(prepare_msg);
					hdunit.setFeaturetype(featureType);
					hdunit.setFeaturestatus(featureStatus);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				// GCM key null
				hdunit.setMessage("NO GCM KEY FOUND");
			}

		} else {
			// no userID data found for given headUnitID
			// set message and status
			hdunit.setStatus(false);
			hdunit.setMessage("NO USER DATA FOUND FOR  HEAD UNIT ID  : " + headUnitId);
			logger.info("NO USER DATA FOUND FOR  HEAD UNIT ID  : " + headUnitId);
		}
		return hdunit;
	}

	/**
	 * Function to request vehicle action feature such as head light, door, car
	 * trunk status to head unit This function sends GCM message to headUnit
	 * with registered GCM_REG_KEY from mobile app
	 * 
	 * @param userId,featureType,featureStatus
	 * 
	 * @return User
	 */

	@SuppressWarnings("unchecked")
	@Override
	public User requestVehicleControl(long userId, String featureType, String featureStatus) {
		logger.info("Call requestVehicleControl===========================");
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		User user = new User();
		// Check if head Unit is registered with given userID
		String check_query = "select count(1) from headunit where user_id = ?";

		int result = jdbcTemplate.queryForObject(check_query, new Object[] { userId }, Integer.class);

		if (result > 0) {

			// check if record exists for userID
			// get GCM key
			String gcm_key_query = "select gcmregistartionkey from headunit where user_id = ?";

			String query_result = (String) jdbcTemplate.queryForObject(gcm_key_query, new Object[] { userId },
					String.class);

			System.out.println("GCM KEY ============================= " + query_result);
			// if GCM key available
			// preapre message
			if (query_result != null && !query_result.isEmpty()) {
				Sender sender = new Sender(utilityObj.getGcmApiKeySimuapp());
				JSONObject obj = new JSONObject();
				// build message based on featureType and featurestatus
				obj.put("featuretype", featureType);
				obj.put("featurestatus", featureStatus);
				String prepare_msg = obj.toJSONString();
				gcm_message = new Message.Builder().timeToLive(30).delayWhileIdle(true)
						.addData(MESSAGE_KEY, prepare_msg).build();
				logger.info("IN requestVehicleControl GCM reg key==========" + query_result);
				Result gcm_result = null;
				// get gcm_message and gcm_register key
				// send message to that gcm_register_key
				// set message and status
				try {
					gcm_result = sender.send(gcm_message, query_result, 1);
					logger.info("IN requestVehicleControl GCM SEND RESULT FROM GOOLGE API " + gcm_result.toString());
				} catch (IOException e) {
					e.printStackTrace();
				}
				user.setUserid(userId);
				user.setStatus(true);
				user.setGcmregistartionkey(query_result);
				user.setMessage(prepare_msg);
				user.setFeaturetype(featureType);
				user.setFeaturestatus(featureStatus);
			} else {
				user.setMessage("NO GCM KEY FOUND ");
			}

		} else {
			// if head unit registered
			// set meassage and status
			user.setStatus(false);
			user.setMessage("NO HEAD UNIT REGISTERED FOR GIVEN USERID : " + userId);

		}
		return user;
	}

	/**
	 * This Method is used extract all registered user details
	 * 
	 * @param
	 * @return List<User>
	 */

	@Override
	public List<User> getallUserDetail() {
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		// select all from table user
		String sql = "SELECT * FROM user";
		List<User> listuser = jdbcTemplate.query(sql, new RowMapper<User>() {

			@Override
			public User mapRow(ResultSet rs, int rowNum) throws SQLException {
				User user = new User();
				// set parameters
				user.setUserid(rs.getLong("user_id"));
				user.setEmailid(rs.getString("emailid"));
				user.setAppversion(rs.getString("appversion"));
				user.setGcmregistartionkey(rs.getString("gcmregistartionkey"));
				user.setMobileno(rs.getString("mobileno"));
				user.setUuid(rs.getString("uuid"));
				user.setUsername(rs.getString("username"));
				user.setCreateddate(rs.getDate("createddate"));
				return user;
			}

		});

		return listuser;

	}

	/**
	 * This Method is used extract all registered headunit details
	 * 
	 * @return list
	 */

	@Override
	public List<HeadUnit> getallHeadUnitDetail() {
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "SELECT * FROM headunit";
		List<HeadUnit> listHeadunit = jdbcTemplate.query(sql, new RowMapper<HeadUnit>() {
			@Override
			public HeadUnit mapRow(ResultSet rs, int rowNum) throws SQLException {
				// set parameters
				HeadUnit headunit = new HeadUnit();
				headunit.setHeadunitid(rs.getLong("headunit_id"));
				headunit.setUserid(rs.getInt("user_id"));
				headunit.setCarname(rs.getString("carname"));
				headunit.setHduuid(rs.getString("hduuid"));
				headunit.setAppversion(rs.getString("appversion"));
				headunit.setGcmregistartionkey(rs.getString("gcmregistartionkey"));
				headunit.setCreateddate(rs.getDate("createddate"));
				return headunit;
			}
		});
		return listHeadunit;
	}

	/**
	 * method to configure the database with db credentials
	 * @param String
	 *            adminusername,String adminpwd,String dbname
	 * @return
	 * @see com.tbu.dao.CommonDao#isdbConfigured(java.lang.String,
	 *      java.lang.String, java.lang.String)
	 */
	@Override
	public boolean isdbConfigured(SystemAdmin sysadmin) {

		readPropertiesfileForDB(sysadmin);
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "CREATE DATABASE " + sysadmin.getDatabasename();
		int result = jdbcTemplate.update(sql);
		if (result > 0) {
			// read database properties file and set user given database
			// detailes
			readPropertiesfileForcrud(sysadmin);
			// set datasources to connect to database
			datasource = getDataSource();
			jdbcTemplate = new JdbcTemplate(datasource);
			// read sql file to dump tables into databae
			String path = context.getRealPath("/");
			String SQLPATH = path + Utility.SQL_PATH;
			String s = new String();
			StringBuffer sb = new StringBuffer();

			try {
				// read sql file from path
				FileReader fr = new FileReader(new File(SQLPATH));

				BufferedReader br = new BufferedReader(fr);

				while ((s = br.readLine()) != null) {
					sb.append(s);
				}
				br.close();

				// here is our splitter | We use ";" as a delimiter for each
				// request
				// then we are sure to have well formed statements
				String[] inst = sb.toString().split(";");
				for (int i = 0; i < inst.length; i++) {
					// we ensure that there is no spaces before or after the
					// request string
					// in order to not execute empty statements
					if (!inst[i].trim().equals("")) {
						jdbcTemplate.update(inst[i]);
						logger.info(">>" + inst[i]);
					}
				}

			} catch (Exception e) {
				logger.error("*** Error : " + e.toString());
				logger.error(" *** ");
				logger.error(" *** Error : ");
				logger.error("################################################");
				logger.error(sb.toString());
			}

			return true;

		} else {

			return false;
		}
	}

	/**
	 * Modify and set url in properties file to create database
	 * 
	 * @param SystemAdmin
	 */

	public void readPropertiesfileForDB(SystemAdmin sysadmin) {
		String path = context.getRealPath("/");
		String dbpath = path + Utility.DB_PATH;
		logger.info("=========================DB PATH=========================" + dbpath);
		FileInputStream fileInputStreamSystemSettings = null;
		OutputStream output = null;
		Properties prop = new Properties();
		try {
			// String Dbrealpath=dbpath
			fileInputStreamSystemSettings = new FileInputStream(dbpath);
			try {
				prop.load(fileInputStreamSystemSettings);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStreamSystemSettings.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			String URL = "jdbc:mysql://" + sysadmin.getServerip() + ":" + sysadmin.getPort();
			// set the properties new value
			prop.setProperty("jdbc.url", URL);
			prop.setProperty("jdbc.username", sysadmin.getUsername());
			prop.setProperty("jdbc.password", sysadmin.getPassword());
			prop.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
			output = new FileOutputStream(dbpath);
			logger.info("=========================NEW PROPERTIES=========================");
			logger.info("  jdbc.driver " +prop.getProperty("jdbc.driver"));
			logger.info("  jdbc.url   " +prop.getProperty("jdbc.url"));
			logger.info("  jdbc.username   " +prop.getProperty("jdbc.username"));
			logger.info("  jdbc.password   " +prop.getProperty("jdbc.password"));

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			prop.store(output, null);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Modify and set current db name in properties file
	 * 
	 * @param SystemAdmin
	 */

	public void readPropertiesfileForcrud(SystemAdmin sysadmin) {
		String path = context.getRealPath("/");
		String dbpath = path + Utility.DB_PATH;
		System.out.println("================path===========" + dbpath);
		FileInputStream fileInputStreamSystemSettings = null;
		OutputStream output = null;
		Properties prop = new Properties();
		try {
			// String Dbrealpath=dbpath
			fileInputStreamSystemSettings = new FileInputStream(dbpath);
			try {
				prop.load(fileInputStreamSystemSettings);
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStreamSystemSettings.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			String URL = "jdbc:mysql://" + sysadmin.getServerip() + ":" + sysadmin.getPort() + "/"
					+ sysadmin.getDatabasename();

			// set the properties new value
			prop.setProperty("jdbc.url", URL);
			prop.setProperty("jdbc.username", sysadmin.getUsername());
			prop.setProperty("jdbc.password", sysadmin.getPassword());
			prop.setProperty("jdbc.driver", "com.mysql.jdbc.Driver");
			prop.setProperty("jdbc.dbconfig", "Y");
			output = new FileOutputStream(dbpath);
			logger.info("=========================NEW PROPERTIES=========================");
			logger.info(" jdbc.driver " + prop.getProperty("jdbc.driver"));
			logger.info("  jdbc.url   " + prop.getProperty("jdbc.url"));
			logger.info("  jdbc.username   " + prop.getProperty("jdbc.username"));
			logger.info("  jdbc.password   " + prop.getProperty("jdbc.password"));

		} catch (FileNotFoundException e1) {
			e1.printStackTrace();
		}
		try {
			prop.store(output, null);
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				output.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

	}

	/**
	 * Set datasource for JdbcTemplate connection
	 * 
	 */

	public SimpleDriverDataSource getDataSource() {
		String path = context.getRealPath("/");
		String dbpath = path + Utility.DB_PATH;
		SimpleDriverDataSource dataSource = new SimpleDriverDataSource();
		FileInputStream fileInputStreamSystemSettings = null;
		Properties prop = new Properties();
		try {
			fileInputStreamSystemSettings = new FileInputStream(dbpath);
			try {
				prop.load(fileInputStreamSystemSettings);
				try {
					// set datasource parameters
					// set driver
					dataSource.setDriver(new com.mysql.jdbc.Driver());
					// set username
					dataSource.setUsername(prop.getProperty("jdbc.username"));
					// set password
					dataSource.setPassword(prop.getProperty("jdbc.password"));
					// set jdbc url
					dataSource.setUrl(prop.getProperty("jdbc.url"));

				} catch (SQLException e) {
					e.printStackTrace();
				}

			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStreamSystemSettings.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return dataSource;
	}

	/**
	 * Function to request vehicle action feature such as AC to head unit This
	 * function sends GCM message to headUnit with registered GCM_REG_KEY from
	 * mobile app
	 * 
	 * @param userId,featureType,
	 *            featureStatus, actemp, acfantemp
	 * 
	 * @return User
	 */

	@SuppressWarnings("unchecked")
	@Override
	public User requestVehicleACControl(long userId, String ACfeatureType, String ACStatus, String ACtemp,
			String ACFanSpeed, String rearACTemp) {
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		logger.info("IN requestVehicleACControl  ");
		User user = new User();
		// Check if head Unit is registered with given userID
		String check_query = "select count(1) from headunit where user_id = ?";

		int result = jdbcTemplate.queryForObject(check_query, new Object[] { userId }, Integer.class);

		if (result > 0) {
			// check if record exists for userID
			// get GCM key
			String gcm_key_query = "select gcmregistartionkey from headunit where user_id = ?";

			String query_result = (String) jdbcTemplate.queryForObject(gcm_key_query, new Object[] { userId },
					String.class);

			System.out.println("GCM KEY ============================= " + query_result);
			// if GCM key available
			// preapre message
			if (query_result != null && !query_result.isEmpty()) {
				Sender sender = new Sender(utilityObj.getGcmApiKeySimuapp());
				// build message based on featureType and featurestatus
				JSONObject obj = new JSONObject();
				obj.put("featuretype", ACfeatureType);
				obj.put("featurestatus", ACStatus);
				obj.put("actemp", ACtemp);
				obj.put("acfanspeed", ACFanSpeed);
				obj.put("rearactemp", rearACTemp);
				String prepare_msg = obj.toJSONString();
				gcm_message = new Message.Builder().timeToLive(30).delayWhileIdle(true)
						.addData(MESSAGE_KEY, prepare_msg).build();
				Result gcm_result = null;
				// get gcm_message and gcm_register key
				// send message to that gcm_register_key
				// set message and status
				try {
					gcm_result = sender.send(gcm_message, query_result, 1);
					logger.info(" IN REQUEST VEHICLE AC CONTROL SERVICE  GCM REASULT : " + gcm_result.toString());

				} catch (IOException e) {
					e.printStackTrace();
				}
				user.setUserid(userId);
				user.setFeaturetype(ACfeatureType);
				user.setFeaturestatus(ACStatus);
				user.setStatus(true);
				user.setMessage(prepare_msg);
				user.setActemp(ACtemp);
				user.setAcfanspeed(ACFanSpeed);
				user.setRearactemp(rearACTemp);
				user.setGcmregistartionkey(query_result);
			} else {
				user.setMessage("NO GCM KEY FOUND");
			}
		} else {
			// if head unit registered
			// set meassage and status
			user.setStatus(false);
			user.setMessage("NO HEAD UNIT REGISTERED FOR GIVEN USERID : " + userId);
			logger.info("NO HEAD UNIT REGISTERED FOR GIVEN USERID : " + userId);
		}
		return user;
	}

	/**
	 * Function to request status of vehicle action such as AC to mobile app
	 * This function sends GCM message to headUnit with registered GCM_REG_KEY
	 * from mobile app
	 * 
	 * @param headUnitId,featureType,
	 *            featureStatus, actemp, acfantemp.
	 * @return HeadUnit.
	 */

	@SuppressWarnings("unchecked")
	@Override
	public HeadUnit updateACControlStatus(long headUnitId, String ACfeatureType, String ACStatus, String ACTemp,
			String ACFanTemp, String rearACTemp) {
		
		// check if given headUnitId is registered with any userID
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		
		logger.info("IN updateACControlStatus  ");
		
		HeadUnit hdunit = new HeadUnit();
		
		String check_query = "select count(1) from user where "
				+ "user_id = (SELECT user_id from headunit where headunit_id = ?)";
		
		int result = jdbcTemplate.queryForObject(check_query, new Object[] { headUnitId }, Integer.class);
		if (result > 0) {
			// if userID is available, send the status
			// if userID is available, send the status
			// get GCM key
			String gcm_key_query = "select gcmregistartionkey from user where "
					+ "user_id = (SELECT user_id from headunit where headunit_id = ?)";
			
			String query_result = (String) jdbcTemplate.queryForObject(gcm_key_query, new Object[] { headUnitId },
					String.class);
			
			System.out.println("GCM KEY ============================= " + query_result);
			
			// if GCM key not null or empty
			// prepare message
			if (query_result != null && !query_result.isEmpty()) {
				Sender sender = new Sender(utilityObj.getGcmApiKeyVehiclelocatorapp());
				// build message based on featureType and featurestatus
				JSONObject obj = new JSONObject();
				obj.put("featuretype", ACfeatureType);
				obj.put("featurestatus", ACStatus);
				obj.put("actemp", ACTemp);
				obj.put("acfanspeed", ACFanTemp);
				obj.put("rearactemp", rearACTemp);
				String prepare_msg = obj.toJSONString();
				gcm_message = new Message.Builder().timeToLive(30).delayWhileIdle(true)
						.addData(MESSAGE_KEY, prepare_msg).build();
				Result gcm_result = null;
				try {
					// get gcm_message and gcm_register key
					// send message to that gcm_register_key
					// set message and status
					gcm_result = sender.send(gcm_message, query_result, 1);
					logger.info("IN Update AC CONTROL STATUS : " + gcm_result.toString());
					hdunit.setStatus(true);
					hdunit.setMessage(prepare_msg);
					hdunit.setFeaturetype(ACfeatureType);
					hdunit.setFeaturestatus(ACStatus);
					hdunit.setActemp(ACTemp);
					hdunit.setAcfanspeed(ACFanTemp);
					hdunit.setRearactemp(rearACTemp);
					hdunit.setGcmregistartionkey(query_result);
				} catch (IOException e) {
					e.printStackTrace();
				}
			} else {
				hdunit.setMessage("NO GCM KEY FOUND");
			}

		} else {
			// no userID data found for given headUnitID
			// set message and status
			hdunit.setStatus(false);
			hdunit.setMessage("NO USER DATA FOUND FOR  HEAD UNIT ID  : " + headUnitId);
			logger.info("NO USER DATA FOUND FOR  HEAD UNIT ID  : " + headUnitId);
		}

		return hdunit;
	}

	/**
	 * check database is already configured or not. To show the dbconfig page or
	 * login page is decided by this method.
	 * 
	 * @return
	 */
	@Override
	public boolean checkdbconfig() {
		// get path
		String path = context.getRealPath("/");
		String dbpath = path + Utility.DB_PATH;
		String dbconfig = "";
		FileInputStream fileInputStreamSystemSettings = null;
		Properties prop = new Properties();
		try {
			fileInputStreamSystemSettings = new FileInputStream(dbpath);
			try {
				prop.load(fileInputStreamSystemSettings);
				dbconfig = prop.getProperty("jdbc.dbconfig");
			} catch (IOException e) {
				e.printStackTrace();
			}

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			try {
				fileInputStreamSystemSettings.close();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		if (dbconfig.equalsIgnoreCase("Y")) {
			return true;
		} else {
			return false;
		}
	}

	/**
	 * Delete the user using user id
	 * 
	 * @param userid
	 * @return
	 */
	@Override
	public boolean deleteUser(int userid) {
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		logger.info("IN DELETE METHOD" + userid);
		// Check if head Unit is registered with given userID
		String check_query = "select count(1) from user where user_id = ?";
		int result = jdbcTemplate.queryForObject(check_query, new Object[] { userid }, Integer.class);
		logger.info("Result ======= " + result);
		if (result > 0) {
			String query = "delete from user where user_id= ?";
			int rowdelete = jdbcTemplate.update(query, userid);
			logger.info("Row Delete ======= " + rowdelete);
			if (rowdelete > 0) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	/**
	 * Delete the Headunit using user id
	 * @param userid
	 * @return
	 */
	@Override
	public boolean deleteHeadunit(int userid) {
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		logger.info("IN DELETE METHOD" + userid);
		// Check if head Unit is registered with given userID
		String check_query = "select count(1) from user where user_id = ?";
		int result = jdbcTemplate.queryForObject(check_query, new Object[] { userid }, Integer.class);
		logger.info("Result ======= " + result);
		if (result > 0) {
			String query = "delete from user where user_id= ?";
			int rowdelete = jdbcTemplate.update(query, userid);
			logger.info("Row Delete ======= " + rowdelete);
			if (rowdelete > 0) {
				return true;
			} else {
				return false;
			}

		} else {
			return false;
		}

	}

	/**
	 * Update GCM key using user_id
	 * 
	 * @param user
	 * @return
	 */

	@Override
	public UserProfileResponse updategcmKey(User user) {
		// datasource is used to connect with database
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		UserProfileResponse gcmres = new UserProfileResponse();
		logger.info("IN updategcmKey METHOD ");
		// check if user exists in database with emailId
		String sql = "select count(1) from user where user_id = ? ";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { user.getUserid() }, Integer.class);
		// if user exists, return userId
		logger.info("Query Count : " + result);
		if (result > 0) {
			String update_gcm_query = "Update user set gcmregistartionkey = ?  where user_id = ?";
			int rowcount = jdbcTemplate.update(update_gcm_query, user.getGcmregistartionkey(), user.getUserid());
			logger.info("Update Row Count : " + rowcount);
			if (rowcount > 0) {
				gcmres.setStatus(true);
				gcmres.setMessage("GCM Key Is Updated Successfully For User Id:" + user.getUserid());
				logger.info("GCM Key Is Updated Successfully");
			} else {
				gcmres.setStatus(false);
				gcmres.setMessage("GCM Key Is Not Updated Successfully For User Id:" + user.getUserid());
				logger.info("GCM Key Is Not Updated Successfully");
			}

		} else {
			gcmres.setStatus(false);
			gcmres.setMessage("GCM Key Is Not Updated Successfully For User Id:" + user.getUserid());
			logger.info("GCM Key Is Not Updated Successfully");
		}
		return gcmres;
	}

	/**
	 * This Method validate IP Address
	 * 
	 * @param ip
	 * @return boolean
	 */
	@Override
	public boolean validate(String ip) {
		Pattern pattern = Pattern.compile(IPADDRESS_PATTERN);
		Matcher matcher = pattern.matcher(ip);
		return matcher.matches();
	}

	/**
	 * This method is used to update the existing user profile
	 * 
	 * @param user
	 * @return
	 */
	@Override
	public UserProfileResponse updateProfile(User user) {
		// datasource is used to connect with database
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		UserProfileResponse gcmres = new UserProfileResponse();
		logger.info("IN updateProfile METHOD ");
		// check if user exists in database with user_id
		String sql = "select count(1) from user where user_id = ? ";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { user.getUserid() }, Integer.class);
		// if user exists, return userId
		logger.info("Query Count : " + result);
		if (result > 0) {
			String update_gcm_query = "Update user set emailid = ?, mobileno = ?, username = ?, uuid = ?, appversion = ?, gcmregistartionkey = ?  where user_id = ?";
			int rowcount = jdbcTemplate.update(update_gcm_query, user.getEmailid(), user.getMobileno(),
					user.getUsername(), user.getUuid(), user.getAppversion(), user.getGcmregistartionkey(),
					user.getUserid());
			logger.info("updateProfile Update Row Count : " + rowcount);
			if (rowcount > 0) {
				gcmres.setStatus(true);
				gcmres.setMessage("User Profile IS Updated Successfully For User Id:" + user.getUserid());
				logger.info("User Profile IS Updated Successfully");
			} else {
				gcmres.setStatus(false);
				gcmres.setMessage("User Profile Is Not Updated Successfully For User Id:" + user.getUserid());
				logger.info("User Profile Is Not Updated Successfully For User Id:" + user.getUserid());
			}

		} else {
			gcmres.setStatus(false);
			gcmres.setMessage("User Profile Is Not Updated Successfully For User Id:" + user.getUserid());
			logger.info("User Profile Is Not Updated Successfully For User Id:" + user.getUserid());
		}

		return gcmres;

	}

	/**
	 * This Method is used to update gcm key
	 * 
	 * @param hdunit
	 * @return
	 */

	@Override
	public UserProfileResponse updategcmkeyhdUnit(HeadUnit hdunit) {

		// datasource is used to connect with database
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		UserProfileResponse gcmres = new UserProfileResponse();
		logger.info("IN updategcmKey METHOD ");
		// check if user exists in database with emailId
		String sql = "select count(1) from headunit where headunit_id = ? ";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { hdunit.getHeadunitid() }, Integer.class);
		// if user exists, return userId
		logger.info("Query Count : " + result);
		if (result > 0) {
			String update_gcm_query = "Update headunit set gcmregistartionkey = ?  where headunit_id = ?";
			int rowcount = jdbcTemplate.update(update_gcm_query, hdunit.getGcmregistartionkey(),
					hdunit.getHeadunitid());
			logger.info("Update Row Count : " + rowcount);
			if (rowcount > 0) {
				gcmres.setStatus(true);
				gcmres.setMessage("GCM Key Is Updated Successfully For User Id:" + hdunit.getHeadunitid());
				logger.info("GCM Key Is Updated Successfully");
			} else {
				gcmres.setStatus(false);
				gcmres.setMessage("GCM Key Is Not Updated Successfully For User Id:" + hdunit.getHeadunitid());
				logger.info("GCM Key Is Not Updated Successfully");
			}

		} else {
			gcmres.setStatus(false);
			gcmres.setMessage("GCM Key Is Not Updated Successfully For HdUnit Id:" + hdunit.getHeadunitid());
			logger.info("GCM Key Is Not Updated Successfully");
		}

		return gcmres;
	}

	/**
	 * This Method is used to register the trip App
	 * 
	 * @param tripuser
	 * @return
	 * 
	 */

	@Override
	public TripUserRes registerTripUser(final TripUser tripuser) {
		// datasource is used to connect with database
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		TripUserRes tripuserres = new TripUserRes();
		// check if user exists in database with emailId
		String sql = "select count(1) from tripuser where tuuid = ? ";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { tripuser.getTuuid() }, Integer.class);
		if (result > 0) {
			tripuserres.setMessage(tripuser.getTuuid() + " : UUID ALREADY REGISTER ON SERVER");
			tripuserres.setStatus(false);
		} else {

			final PreparedStatementCreator psc = new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
					final PreparedStatement ps = connection.prepareStatement(
							"INSERT INTO tripuser(tuuid,createddate) " + "Values (?,?)",
							Statement.RETURN_GENERATED_KEYS);
					// set parameters
					ps.setString(Utility.TUSER_UUID_INDEX, tripuser.getTuuid());
					ps.setTimestamp(Utility.TUSER_CDT_INDEX, getCurrentDate());

					return ps;
				}
			};
			// return auto generated tripuerid
			final KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(psc, holder);
			final long tripuserid = holder.getKey().longValue();
			if (tripuserid > 0) {
				tripuserres.setMessage(tripuser.getTuuid() + " TRIP UUID HAS BEEN REGISTERED SUCCESSFULLY");
				tripuserres.setStatus(true);
			}
		}

		return tripuserres;
	}

	/**
	 * This Method is used to store trip data on server.
	 * 
	 * @param tripappdata
	 * @return
	 */
	@Override
	public TripAppDataRes uploadTripData(final TripAppData tripappdata) {
		// datasource is used to connect with database
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		TripAppDataRes tripappdatares = new TripAppDataRes();
		String sql = "select count(1) from tripappdata where tripid = ? ";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { tripappdata.getTripid() }, Integer.class);
		if (result > 0) {
			// update the details
			String update_tripdate = "UPDATE `tripappdata` SET `tuuid` = ?," + "`maxspeed` = ?," + "`maxrpm` = ?,"
					+ "`startlocation` = ?," + "`endlocation` = ?," + "`tripstartdatetime` = ?,"
					+ "`tripenddatetime` = ?," + "`engineruntime` = ?," + "`fuellevelstart` = ?,"
					+ "`fuellevelend` = ?," + "`startdistance` = ?," + "`enddistance` = ?," + "`startlatitude` = ?,"
					+ "`endlatitude` = ?," + "`startlongitude` = ?," + "`endlongitude` = ? " + "WHERE `tripid` = ?";

			int rowcount = jdbcTemplate.update(update_tripdate, tripappdata.getTuuid(), tripappdata.getMaxspeed(),
					tripappdata.getMaxrpm(), tripappdata.getStartlocation(), tripappdata.getEndlocation(),
					tripappdata.getTstartdatetime(), tripappdata.getTenddatetime(), tripappdata.getEngineruntime(),
					tripappdata.getFuellevelstart(), tripappdata.getFuellevelend(), tripappdata.getStartdistance(),
					tripappdata.getEnddistance(), tripappdata.getStartlatitude(), tripappdata.getEndlatitude(),
					tripappdata.getStartlongitude(), tripappdata.getEndlongitude(), tripappdata.getTripid());

			if (rowcount > 0) {
				tripappdatares.setStatus(true);
				tripappdatares
						.setMessage("TRIP DATA HAS BEEN UPDATED SUCCESSFULLY FOR TRIP ID :" + tripappdata.getTripid());
			} else {
				tripappdatares.setStatus(false);
				tripappdatares
						.setMessage("TRIP DATA HAS NOT UPDATED SUCCESSFULLY FOR TRIP ID :" + tripappdata.getTripid());
			}

		} else {
			// add details
			final PreparedStatementCreator psc = new PreparedStatementCreator() {
				@Override
				public PreparedStatement createPreparedStatement(final Connection connection) throws SQLException {
					final PreparedStatement ps = connection
							.prepareStatement(
									"INSERT INTO `tripappdata`(`tripid`,`tuuid`,`maxspeed`,`maxrpm`,"
											+ "`startlocation`,`endlocation`,`tripstartdatetime`,`tripenddatetime`,"
											+ "`engineruntime`,`fuellevelstart`,`fuellevelend`,`startdistance`,"
											+ "`enddistance`,`startlatitude`,`endlatitude`,`startlongitude`,"
											+ "`endlongitude`,`createddate`) "
											+ "VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?);",
									Statement.RETURN_GENERATED_KEYS);
					// set parameters
					ps.setLong(Utility.TRAPP_ID_INDEX, tripappdata.getTripid());
					ps.setString(Utility.TRAPP_UUID_INDEX, tripappdata.getTuuid());
					ps.setString(Utility.TRAPP_MXSPD_INDEX, tripappdata.getMaxspeed());
					ps.setString(Utility.TRAPP_MXRPM_INDEX, tripappdata.getMaxrpm());
					ps.setString(Utility.TRAPP_STLOC_INDEX, tripappdata.getStartlocation());
					ps.setString(Utility.TRAPP_ENDLOC_INDEX, tripappdata.getEndlocation());
					ps.setString(Utility.TRAPP_STDTT_INDEX, tripappdata.getTstartdatetime());
					ps.setString(Utility.TRAPP_ENDDTT_INDEX, tripappdata.getTenddatetime());
					ps.setString(Utility.TRAPP_ENGRNT_INDEX, tripappdata.getEngineruntime());
					ps.setString(Utility.TRAPP_STFUL_INDEX, tripappdata.getFuellevelstart());
					ps.setString(Utility.TRAPP_ENDFUL_INDEX, tripappdata.getFuellevelend());
					ps.setString(Utility.TRAPP_STDIST_INDEX, tripappdata.getStartdistance());
					ps.setString(Utility.TRAPP_ENDIST_INDEX, tripappdata.getEnddistance());
					ps.setString(Utility.TRAPP_STLAT_INDEX, tripappdata.getStartlatitude());
					ps.setString(Utility.TRAPP_ENLAT_INDEX, tripappdata.getEndlatitude());
					ps.setString(Utility.TRAPP_STLONG_INDEX, tripappdata.getStartlongitude());
					ps.setString(Utility.TRAPP_ENLONG_INDEX, tripappdata.getEndlongitude());
					ps.setTimestamp(Utility.TRAPP_CDT_INDEX, getCurrentDate());

					return ps;
				}
			};
			// return auto generated tripuerid
			final KeyHolder holder = new GeneratedKeyHolder();
			jdbcTemplate.update(psc, holder);
			final long tripappdataid = holder.getKey().longValue();
			if (tripappdataid > 0) {
				tripappdatares
						.setMessage(" TRIP APP DATA INSERTED SUCCESFULLY FOR TRIP ID : " + tripappdata.getTripid());
				tripappdatares.setStatus(true);
			} else {
				tripappdatares.setMessage(" TRIP APP DATA NOT INSERTED  FOR TRIP ID : " + tripappdata.getTripid());
				tripappdatares.setStatus(false);
			}

		}
		return tripappdatares;
	}

	/**
	 * This method is used to configure the email functionality and generate
	 * four digit password. Generated password is email to the registered
	 * emailid.
	 * 
	 * @param resetpwdreq
	 * @return
	 */
	@Override
	public ForgotPasswordResponse forgotPassword(ResetPasswordRequest resetpwdreq) {
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		// get reg emailid from db
		logger.info("IN resetPassword Method==========");
		// Response object
		ForgotPasswordResponse reset_pass_obj = new ForgotPasswordResponse();
		logger.info("CALLING  COUNT ");
		String sql = "select count(1) from user where emailid = ? ";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { resetpwdreq.getEmailid() }, Integer.class);
		logger.info("RESULT QUERY " + result);
		if (result > 0) {
			// to call email func
			ResetPassword resetpass_obj = new ResetPassword();
			// generate 4 digit PIN
			// method to genearate 4 digit number
			PIN = resetpass_obj.generateSecurityKey();
			logger.info("PIN : " + PIN);
			//encrypted password
			// update generated pin to database using emailid
			String update_pin = "UPDATE user SET password = ? WHERE emailid =? ";
			int rowcount = jdbcTemplate.update(update_pin, PasswordEncryptionUtil.encryptPassword(PIN), resetpwdreq.getEmailid().trim());
			logger.info(" DB Updated =========== " + rowcount);
			if (rowcount > 0) {
				// db updated, send email with generated PIN
				resetpass_obj.generateEMail(resetpwdreq, PIN);
				reset_pass_obj.setStatus(true);
				reset_pass_obj.setMessage(" EMAIL SENT WITH TEMPORARY PASSWORD ");
				reset_pass_obj.setPassword(PIN);
			} else {
				// DB not updated
				reset_pass_obj.setMessage(" EMAIL IS NOT SEND ");
				reset_pass_obj.setStatus(false);
				reset_pass_obj.setPassword("NA");
			}
		} else {
			// provided email ID doesnt exist
			reset_pass_obj.setMessage("PLEASE PROVIDE REGISTERED EMAILID");
			reset_pass_obj.setStatus(false);
			reset_pass_obj.setPassword("NA");
		}
		return reset_pass_obj;
	}

	/**
	 * This Method is used extract all trip app data details using trip start
	 * date
	 * 
	 * @param trequest
	 * @return List<TripDetailResponse>
	 */

	@Override
	public List<TripDetailResponse> getTripappData(TripDetailRequest trequest) {
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		String sqlcount = "SELECT count(1) FROM tbu.tripappdata where cast( tripstartdatetime as date )= ? and tuuid=?";
		int result = jdbcTemplate.queryForObject(sqlcount,
				new Object[] { trequest.getTstartdatetime().trim(), trequest.getTuuid() }, Integer.class);
		logger.info(" RESULT QUERY  " + result);
		List<TripDetailResponse> triplist = new ArrayList<TripDetailResponse>();
		if (result > 0) {
			// select all from table user
			String sql = "SELECT * FROM tbu.tripappdata where cast( tripstartdatetime as date )= '"
					+ trequest.getTstartdatetime().trim() + "'" + " and tuuid='" + trequest.getTuuid() + "'";
			List<TripDetailResponse> listtripdata = jdbcTemplate.query(sql, new RowMapper<TripDetailResponse>() {

				@Override
				public TripDetailResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					TripDetailResponse res = new TripDetailResponse();
					// set parameters
					res.setTripid(rs.getInt("tripid"));
					res.setTuuid(rs.getString("tuuid"));
					res.setMaxspeed(rs.getString("maxspeed"));
					res.setMaxrpm(rs.getString("maxrpm"));
					res.setStartlocation(rs.getString("startlocation"));
					res.setEndlocation(rs.getString("endlocation"));
					res.setTstartdatetime(rs.getString("tripstartdatetime"));
					res.setTenddatetime(rs.getString("tripenddatetime"));
					res.setEngineruntime(rs.getString("engineruntime"));
					res.setFuellevelstart(rs.getString("fuellevelstart"));
					res.setFuellevelend(rs.getString("fuellevelend"));
					res.setStartdistance(rs.getString("startdistance"));
					res.setEnddistance(rs.getString("enddistance"));
					res.setStartlatitude(rs.getString("startlatitude"));
					res.setEndlatitude(rs.getString("endlatitude"));
					res.setStartlongitude(rs.getString("startlongitude"));
					res.setEndlongitude(rs.getString("endlongitude"));
					return res;
				}

			});
			return listtripdata;
		} else {

			return triplist;
		}

	}

	/**
	 * This Method is used extract all trip app data details
	 * 
	 * @param
	 * @return List<TripDetailResponse>
	 */

	public List<TripDetailResponse> getAllTripappData(TripDetailRequest trequest) {
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);

		String sqlcount = "SELECT count(1) FROM tripappdata where  tuuid=?";
		int result = jdbcTemplate.queryForObject(sqlcount, new Object[] { trequest.getTuuid() }, Integer.class);
		logger.info(" RESULT QUERY  " + result);
		List<TripDetailResponse> triplist = new ArrayList<TripDetailResponse>();
		if (result > 0) {
			// select all from table user
			String sql = "SELECT * FROM tripappdata where tuuid ='" + trequest.getTuuid() + "'";
			List<TripDetailResponse> listtripdata = jdbcTemplate.query(sql, new RowMapper<TripDetailResponse>() {

				@Override
				public TripDetailResponse mapRow(ResultSet rs, int rowNum) throws SQLException {
					TripDetailResponse res = new TripDetailResponse();
					// set parameters
					res.setTripid(rs.getInt("tripid"));
					res.setTuuid(rs.getString("tuuid"));
					res.setMaxspeed(rs.getString("maxspeed"));
					res.setMaxrpm(rs.getString("maxrpm"));
					res.setStartlocation(rs.getString("startlocation"));
					res.setEndlocation(rs.getString("endlocation"));
					res.setTstartdatetime(rs.getString("tripstartdatetime"));
					res.setTenddatetime(rs.getString("tripenddatetime"));
					res.setEngineruntime(rs.getString("engineruntime"));
					res.setFuellevelstart(rs.getString("fuellevelstart"));
					res.setFuellevelend(rs.getString("fuellevelend"));
					res.setStartdistance(rs.getString("startdistance"));
					res.setEnddistance(rs.getString("enddistance"));
					res.setStartlatitude(rs.getString("startlatitude"));
					res.setEndlatitude(rs.getString("endlatitude"));
					res.setStartlongitude(rs.getString("startlongitude"));
					res.setEndlongitude(rs.getString("endlongitude"));
					return res;
				}

			});
			return listtripdata;

		} else {

			return triplist;

		}

	}

	/**
	 * This Method is used to check email is exist or not.
	 * 
	 * @param resetpwdreq
	 * @return
	 */

	public CheckEmailResponse checkemail(ResetPasswordRequest resetpwdreq) {
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		// get reg emailid from db
		logger.info("In checkemail Method==========");
		// Response object
		CheckEmailResponse obj = new CheckEmailResponse();
		String sql = "select count(1) from user where emailid = ? ";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { resetpwdreq.getEmailid() }, Integer.class);
		logger.info("Result Query :" + result);
		if (result > 0) {
			obj.setStatus(true);
			obj.setMessage("EMAIL EXIST");
			return obj;
		} else {
			obj.setStatus(false);
			obj.setMessage("EMAIL DOES NOT EXIST");
			return obj;
		}

	}

	/**
	 * This Method is used to update password using emailid
	 * 
	 * @param uppwdreq
	 * @return
	 */

	@Override
	public CheckEmailResponse updatepassword(UpdatePasswordRequest uppwdreq) {

		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		// get reg emailid from db
		logger.info("IN updatepassword Method==========");
		// Response object
		CheckEmailResponse reset_pass_obj = new CheckEmailResponse();
		logger.info("CALLING  COUNT ");
		String sql = "select count(1) from user where emailid = ? ";
		int result = jdbcTemplate.queryForObject(sql, new Object[] { uppwdreq.getEmailid() }, Integer.class);
		logger.info("RESULT QUERY " + result);
		if (result > 0) {
			// update generated pin to database using emailid
			String update_pin = "UPDATE user SET password = ? WHERE emailid =? ";
			//Encrypted the Pwd
			int rowcount = jdbcTemplate.update(update_pin,PasswordEncryptionUtil.encryptPassword(uppwdreq.getPassword()), uppwdreq.getEmailid().trim());
			logger.info(" DB Updated =========== " + rowcount);
			if (rowcount > 0) {
				// db updated, send email with generated PIN
				reset_pass_obj.setStatus(true);
				reset_pass_obj.setMessage(" NEW PASSWORD UPDATED SUCCESSFULLY ");

			} else {
				// DB not updated
				reset_pass_obj.setMessage(" NEW PASSWORD NOT UPDATED SUCCESSFULLY PROVIDE VALID EMAIL ID");
				reset_pass_obj.setStatus(false);
			}
		} else {
			// provided email ID doesnt exist
			reset_pass_obj.setMessage("NEW PASSWORD NOT UPDATED SUCCESSFULLY PROVIDE VALID EMAIL ID");
			reset_pass_obj.setStatus(false);
		}
		return reset_pass_obj;
	}

	/**
	 * Signin user using emailid and pwd.
	 */

//	@Override
//	public SignInResponse signinUser(SignInRequest sgninreq) {
//
//		datasource = getDataSource();
//		jdbcTemplate = new JdbcTemplate(datasource);
//		// get reg emailid from db
//		logger.info("IN signinUser Method==========");
//		// Response object
//		SignInResponse snres = new SignInResponse();
//		logger.info("CALLING  COUNT ");
//		String sql = "select count(1) from user where emailid = ? and password= ? ";
//		int result = jdbcTemplate.queryForObject(sql, new Object[] { sgninreq.getEmailid(), PasswordEncryptionUtil.encryptPassword(sgninreq.getPassword()) },
//				Integer.class);
//		logger.info("RESULT QUERY " + result);
//		if (result > 0) {
//			// if record exists, update gcm key 
//			String update_gcmkey = "UPDATE user SET gcmregistartionkey = ? WHERE emailid =? ";
//			
//			int rowcount = jdbcTemplate.update(update_gcmkey,sgninreq.getGcmregistartionkey(),sgninreq.getEmailid().trim());
//			logger.info(" DB Updated =========== " + rowcount);
//			String sql_query = "select * from user where emailid ='" + sgninreq.getEmailid().trim() + "' and password='"
//					+ PasswordEncryptionUtil.encryptPassword(sgninreq.getPassword().trim()) + "'";
//			return jdbcTemplate.query(sql_query, new ResultSetExtractor<SignInResponse>() {
//				@Override
//				public SignInResponse extractData(ResultSet rs) throws SQLException, DataAccessException {
//					// set parameters
//					if (rs.next()) {
//						SignInResponse sres = new SignInResponse();
//						sres.setUserid(rs.getLong("User_id"));
//						sres.setAppversion(rs.getString("appversion"));
//						sres.setEmailid(rs.getString("emailid"));
//						sres.setGcmregistartionkey(rs.getString("gcmregistartionkey"));
//						sres.setMobileno(rs.getString("mobileno"));
//						sres.setUsername(rs.getString("username"));
//						sres.setUuid(rs.getString("uuid"));
//						sres.setStatus(true);
//						return sres;
//					}
//
//					return null;
//				}
//
//			});
//		} else {
//			snres.setStatus(false);
//			return snres;
//		}
//
//	}
//	
	/**
	 * Signin user using emailid and pwd.
	 * This web service is called when the user selects the sign in option
	 * from mobile end
	 */
	@Override
	public SignInResponse signinUser(SignInRequest sgninreq) {

		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);

		// get reg emailid from db
		logger.info("IN signinUser Method==========");

		// Response object
		SignInResponse snres = new SignInResponse();

		logger.info("CALLING  COUNT ");

		// MySQL query
		String sql = "select count(1) from user where emailid = ? and password= ? ";
		//String sql = "select user_id from user where emailid = ? and password= ? ";

		// Query database
		int userId = jdbcTemplate.queryForObject(sql,
				new Object[] { sgninreq.getEmailid(), PasswordEncryptionUtil.encryptPassword(sgninreq.getPassword()) },
				Integer.class);

		logger.info("RESULT QUERY " + userId);

		// if record (User registration details already) exists, update gcm key and 
		//return the required details
		if (userId > 0) {
			
			//Read the value (Result is having value of user id)
			
			//Update the GCM registration key
			String update_gcmkey = "UPDATE user SET gcmregistartionkey = ? WHERE emailid =? ";

			int rowcount = jdbcTemplate.update(update_gcmkey, sgninreq.getGcmregistartionkey(),
					sgninreq.getEmailid().trim());
			
			logger.info(" DB Updated =========== " + rowcount);
				
			String userIdQuery = "select user_id from user where emailid = ? and password= ? ";
			
			// Query database
			int userUniqId = jdbcTemplate.queryForObject(userIdQuery,
					new Object[] { sgninreq.getEmailid(), PasswordEncryptionUtil.encryptPassword(sgninreq.getPassword()) },
					Integer.class);
			
			//MySQL query to get the headunit details
			String mySQlSelectHeadUnitId = "select headunit_id from headunit where "
					+ "user_id = '" + userUniqId + "'";
			
			logger.info(" SQL query  =========== " + mySQlSelectHeadUnitId);
	
			//Query for headunit id
			int headunitId = 0;
			try {
				headunitId = jdbcTemplate.queryForObject(mySQlSelectHeadUnitId, 
						Integer.class);
			} catch (DataAccessException e) {
				
				logger.info("Head unit id is not found -> signinUser function");
			}
								
			//If headunit 
			if(headunitId != 0)
			{
				//Setting the values for headunit details present and 
				//headunt id 
				headunitDetailsPresent = true;
				requestedHeadunitId = headunitId;
			}
			
			//MySQL query to read the user details
			String sql_query = "select * from user where emailid ='" + sgninreq.getEmailid().trim() + "' and password='"
					+ PasswordEncryptionUtil.encryptPassword(sgninreq.getPassword().trim()) + "'";
									
			return jdbcTemplate.query(sql_query, new ResultSetExtractor<SignInResponse>() {
				@Override
				public SignInResponse extractData(ResultSet rs) throws SQLException, DataAccessException {
					// set parameters
					if (rs.next()) {
						SignInResponse sres = new SignInResponse();
						
						sres.setUserid(rs.getLong("User_id"));
						sres.setAppversion(rs.getString("appversion"));
						sres.setEmailid(rs.getString("emailid"));
						sres.setGcmregistartionkey(rs.getString("gcmregistartionkey"));
						sres.setMobileno(rs.getString("mobileno"));
						sres.setUsername(rs.getString("username"));
						sres.setUuid(rs.getString("uuid"));
						if(headunitDetailsPresent == true)
						{
							sres.setHeadunitIdPresent(true);
							sres.setHeadunitId(requestedHeadunitId);
						}
						else
						{
							sres.setHeadunitIdPresent(false);
							sres.setHeadunitId(0);
						}
						
						sres.setStatus(true);
						return sres;
					}

					return null;
				}

			});
		} else {
			snres.setStatus(false);
			return snres;
		}
	}
	
	
	
	@Override
	public List<Epb> getallEpbDetail() {
		datasource = getDataSource();
		jdbcTemplate = new JdbcTemplate(datasource);
		String sql = "SELECT * FROM epb_detail";
		List<Epb> listepb = jdbcTemplate.query(sql, new RowMapper<Epb>() {
			@Override
			public Epb mapRow(ResultSet rs, int rowNum) throws SQLException {
				// set parameters
				Epb epb = new Epb();
				epb.setEpb_detail_id("epb_detail_id");
				epb.setHeader("header");
				epb.setMT("MT");
				epb.setIMEI("IMEI");
				epb.setPT("PT");
				epb.setDate("date");
				epb.setGpsvalidity("gpsvalidity");
				epb.setLattitude("Lattitude");
				epb.setLongitude("Longitude");
				epb.setLatdir("Latdir");
				epb.setLongdir("Longdir");
				epb.setAlt("alt");
				epb.setAlt("spedd");
				epb.setAlt("VRN");
				epb.setAlt("provider");
				return epb;
			}
		});
		return listepb;
	}


	
}
