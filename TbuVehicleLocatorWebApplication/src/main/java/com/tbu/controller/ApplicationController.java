/**
 * @file ApplicationController.java
 * @brief This is controller for controlling the w
 * Web Application methods
 * @version 1.0
 * @date  26-Jun-2016
 * @author TEL
 */
package com.tbu.controller;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import com.tbu.domain.Epb;
import com.tbu.domain.HeadUnit;
import com.tbu.domain.SystemAdmin;
import com.tbu.domain.User;
import com.tbu.services.CommomService;

/**
 * This class acts as a controller for web application methods such user
 * authentication
 */
@Controller
public class ApplicationController {
	@Autowired
	CommomService commonservice;

	private static final Logger logger = Logger.getLogger(ApplicationController.class);

	/**
	 * Check Db Configured or not if configured then redirect to login page if
	 * not then show dbconfig page
	 * 
	 * @return
	 */

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public ModelAndView pageShow() {
		boolean isdbconfig = commonservice.checkdbconfig();
		if (isdbconfig) {
			return new ModelAndView("index");
		} else {
			return new ModelAndView("dbconfig");
		}

	}

	/**
	 * this request maps checks for authenticated user at the time of login
	 * 
	 * @param user
	 * 
	 * @returns user
	 * 
	 */

	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public ModelAndView userAuthentication(HttpServletRequest req, HttpServletResponse res) {
		ModelAndView model = null;
		try {
			// check credentials
			String username = req.getParameter("username").trim();
			String password = req.getParameter("password").trim();
			// validate user by checking credentials
			boolean isValidUser = commonservice.isUserAuthenticate(username, password);
			if (isValidUser) {
				model = new ModelAndView("success");
			} else {
				model = new ModelAndView("index");
				model.addObject("message", "Invalid credentials!!");
			}

		} catch (Exception e) {
			e.printStackTrace();
		}

		return model;

	}

	/**
	 * This method is used to map the request and call the getallUserDetail
	 * method from service layer and return the user details
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */
	@RequestMapping(value = "/getuserdetail", method = RequestMethod.GET)
	public ModelAndView getUserDetail(ModelAndView model) throws IOException {
		List<User> listUser = commonservice.getallUserDetail();
		int sizeOfList = listUser.size();
		if (sizeOfList > 0) {
			model.addObject("listUser",listUser);
			model.setViewName("userdetail");
			return model;
		} else {
			model.setViewName("userdetail");
			model.addObject("message", "No User Data Found !!");
			return model;
		}
	}
	

	/**
	 * This method is used to map the request and call the getallHeadUnitDetail
	 * method from service layer and return the headunit details
	 * 
	 * @param model
	 * @return
	 * @throws IOException
	 */

	@RequestMapping(value = "/getheadunitdetail",method = RequestMethod.GET)
	public ModelAndView getHeadUnitDetail(ModelAndView model) throws IOException {
		List<HeadUnit> listheadunit = commonservice.getallHeadUnitDetail();
		int sizeOfList = listheadunit.size();
		if (sizeOfList > 0) {
			model.addObject("listheadunit", listheadunit);
			model.setViewName("headunitdetail");
			return model;
		} else {
			model.setViewName("headunitdetail");
			model.addObject("message", "No Head Unit Data Found !!");
			return model;
		}
	}
	
	@RequestMapping(value = "/getepbdetail",method = RequestMethod.GET)
	public ModelAndView getepbdetail(ModelAndView model) throws IOException {
		List<Epb> listepb = commonservice.getallEpbDetail();
		int sizeOfList = listepb.size();
		if (sizeOfList > 0) {
			model.addObject("listepb", listepb);
			model.setViewName("epbdetail");
			return model;
		} else {
			model.setViewName("epbdetail");
			model.addObject("message", " No EPB  Data Found !! ");
			return model;
		}
	}
	
	
	

	/**
	 * This method is used configured database
	 * 
	 * @param req
	 * @param res
	 * @return
	 */
	@RequestMapping(value = "/dbcreation", method = RequestMethod.POST)
	public ModelAndView Createdb(HttpServletRequest req, HttpServletResponse res, ModelAndView model) {
		// capture username,password,serverip,port,databasename From user input
		// For database creation
		String adminusername = req.getParameter("username").trim();
		String adminpassword = req.getParameter("password").trim();
		String databasebname = req.getParameter("databasename").trim();
		String serverip = req.getParameter("serverip").trim();
		String port = req.getParameter("port").trim();
		// check username field,if blank show error
		if (adminusername == null || adminusername.trim().isEmpty()) {
			model.setViewName("dbconfig");
			model.addObject("message", "username is Mandatory !!");
			return model;
		}
		// check databasebname field,if blank show error
		if (databasebname == null || databasebname.trim().isEmpty()) {
			model.setViewName("dbconfig");
			model.addObject("message", "DataBase Name is Mandatory !!");
			return model;
		}
		// check serverip field,if blank show error
		if (serverip == null || serverip.trim().isEmpty()) {
			model.setViewName("dbconfig");
			model.addObject("message", "Serevr IP is Mandatory !!");
			return model;
		}
		// check port field,if blank show error
		if (port == null || port.trim().isEmpty()) {
			model.setViewName("dbconfig");
			model.addObject("message", "Port is Mandatory !!");
			return model;
		}
		// check valid server ip,if not valid Show error
		if (serverip != null) {
			boolean isvalidip = commonservice.validate(serverip);
			if (isvalidip) {
				// do nothing
			} else {
				model.setViewName("dbconfig");
				model.addObject("message", "Please Enter Valid IP");
				return model;
			}

		}
		// check valid Port,if not valid Show error
		if (port != null) {
			if (port.length() != 4) {
				model.setViewName("dbconfig");
				model.addObject("message", "Please Enter Valid Port");
				return model;

			}

		}
		// create Systemadmin Object and set all values using setters
		SystemAdmin sysadmin = new SystemAdmin();
		sysadmin.setUsername(adminusername);
		sysadmin.setPassword(adminpassword);
		sysadmin.setDatabasename(databasebname);
		sysadmin.setServerip(serverip);
		sysadmin.setPort(port);
		// call isdbConfigured() to configured dataBase if db exception occured
		// controlled goes to @ExceptionHandler
		if (databasebname != null && adminusername != null && serverip != null && port != null) {
			boolean isdbcrete = commonservice.isdbConfigured(sysadmin);
			if (isdbcrete) {
				model.setViewName("index");
				return model;
			} else {
				model.setViewName("fail");
				return model;
			}

		}
		return model;

	}

	/**
	 * Call Logout functionality and timeout the session
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/logout", method = RequestMethod.GET)
	public ModelAndView logout(HttpSession session) {
		session.invalidate();
		return new ModelAndView("index");

	}

	/**
	 * This method is used to handle exception during configuration
	 * 
	 * @param ex
	 * @return
	 */

	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "1.Entered Database Already Exist 2.Please Check Provided Database Details 3.Mysql User Should have Admin Privileges")
	@ExceptionHandler(org.springframework.jdbc.UncategorizedSQLException.class)
	public ModelAndView handleException(Exception ex) {
		ModelAndView model = new ModelAndView("database_error");
		model.addObject("exception", ex.getMessage());
		return model;
	}

	/**
	 * This method is used to handle exception during configuration
	 * 
	 * @param ex
	 * @return
	 */
	@ResponseStatus(value = HttpStatus.CONFLICT, reason = "1.Entered Database Already Exist 2.Please Check Provided Database Details 3.Mysql User Should have Admin Privileges")
	@ExceptionHandler(org.springframework.jdbc.CannotGetJdbcConnectionException.class)
	public ModelAndView handleSQLException(Exception ex) {
		ModelAndView model = new ModelAndView("database_error");
		model.addObject("exception", ex.getMessage());

		return model;
	}

	/**
	 * Call Logout functionality and timeout the session
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public ModelAndView home() {
		return new ModelAndView("success");

	}

	/**
	 * Call Delete functionality and Delete the user. If user entry present in
	 * Headunit then delete user entry From Headunit If Headunit present in
	 * Location then delete Headunit entry from Location.
	 * 
	 * @param session
	 * @return
	 */
	@RequestMapping(value = "/deleteUser",method = RequestMethod.GET)
	public ModelAndView deleteUser(HttpServletRequest request) {
		logger.info("Calling deleteUser =========== ");
		int userid = Integer.parseInt(request.getParameter("id").trim());
		logger.info("User ID  =========== " + userid);
		boolean isUser = commonservice.deleteUser(userid);
		if (isUser) {
			ModelAndView model = new ModelAndView("redirect:/getuserdetail");
			model.addObject("message", "User Record Deleted Successfully");

			return model;

		} else {
			ModelAndView model = new ModelAndView("userdetail");
			model.addObject("message", "No User Data Found");
			return model;

		}

	}
	
	/**
	 * Call Delete functionality and Delete the user. If user entry present in
	 * Headunit then delete user entry From Headunit If Headunit present in
	 * Location then delete Headunit entry from Location.
	 * 
	 * @param session
	 * @return
	 */
	
	@RequestMapping(value = "/deletehdu", method = RequestMethod.GET)
	public ModelAndView deleteHeadunit(HttpServletRequest request) {
		logger.info("Calling deleteHeadunit =========== ");
		int userid = Integer.parseInt(request.getParameter("id").trim());
		logger.info("User ID  =========== " + userid);
		boolean isUser = commonservice.deleteHeadunit(userid);
		if (isUser) {
			ModelAndView model = new ModelAndView("redirect:/getheadunitdetail");
			model.addObject("message", "User Record Deleted Successfully");
			return model;

		} else {
			ModelAndView model = new ModelAndView("headunitdetail");
			model.addObject("message", "No User Data Found");
			return model;

		}

	}
	
	
	

}
