/** 
 * @file ResetPassword.java
 * @brief This class is used to send email 
 * @version 1.0
 * @date  24-June-2016
 * @author TEL
 * 
 */

package com.tbu.dao;

import java.util.Properties;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import org.apache.log4j.Logger;

import com.tbu.constant.Utility;
import com.tbu.domain.ResetPasswordRequest;

public class ResetPassword {

	private static final Logger logger = Logger.getLogger(ResetPassword.class);

	Utility utilityObj = new Utility();
	/**
	 * method to reset password for sign in the application
	 */

	public void generateEMail(final ResetPasswordRequest resetpwdreq, String pin) {

		// code to send password reset link
		logger.info("IN generateEMail Method==========");
		// set properties
		Properties props = new Properties();
		props.put("mail.smtp.auth", "true");
		props.put("mail.smtp.starttls.enable", "true");
		props.put("mail.smtp.host", Utility.SMTP_HOST);
		props.put("mail.smtp.port", Utility.SMTP_PORT);

		logger.info("SET Session==========");
		logger.info("USERNAME==========" + utilityObj.getUSERNAME_OFFICE());
		logger.info("PASSWORD==========" + utilityObj.getPASSWORD_OFFICE());
		Session session = Session.getInstance(props, new javax.mail.Authenticator() {
			protected PasswordAuthentication getPasswordAuthentication() {
				return new PasswordAuthentication(utilityObj.getUSERNAME_OFFICE(), utilityObj.getPASSWORD_OFFICE());
			}
		});
		try {
			logger.info("IN Message Build ==========");
			// craete message object to send mail
			Message message = new MimeMessage(session);
			// set from address
			message.setFrom(new InternetAddress(utilityObj.getUSERNAME_OFFICE()));
			// set to address
			message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(resetpwdreq.getEmailid().trim()));
			// set subject
			message.setSubject("Find My Car App Reset Password");
			// method to genearate 4 digit number
			// if update success
			message.setText(" Your password has been reset.The New password is: : " + pin);
			// send mail to reset password
			Transport.send(message);

			System.out.println("Sent");
			logger.info("Message sent ==========");

		} catch (MessagingException e) {
			e.printStackTrace();
		}

	}

	/**
	 * method to generate random 4 digit pin
	 * 
	 * @return
	 */
	public String generateSecurityKey() {
		String sec_pin = "" + ((int) (Math.random() * 9000) + 1000);
		return sec_pin;
	}
}
