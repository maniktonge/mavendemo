/** 
 * @file PasswordEncryptionUtil.java
 * @brief This class is used to encrypt the password field * 
 * @version 1.0
 * @date  08-08-2016
 * @author TEL
 * 
 */


package com.tbu.constant;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class PasswordEncryptionUtil {
	
	/**
	 * This Method is Used to generate encrypted password
	 * @param password
	 * @return
	 */
	public static String encryptPassword(String password){
		MessageDigest mdEnc;
		try {
		mdEnc = MessageDigest.getInstance("MD5");
		mdEnc.update(password.getBytes(), 0, password.length());
		String encryptedPwd = new BigInteger(1, mdEnc.digest()).toString(16);
		return encryptedPwd;
		} catch (NoSuchAlgorithmException e) {
		e.printStackTrace();
		return e.getMessage();
		} 
		}
	
	/**
	 * get  Password
	 * @return
	 */
		public static String getPassword() {
		return generatePassword();
		}
  
		/**
         * Generate the Password
         * @return
         */
		private static String generatePassword() {
		java.util.Random r = new java.util.Random();
		int i = 1, n = 0;
		char c;
		String str = "";
		for (int t = 0; t < 3; t++) {
		while (true) {
		i = r.nextInt(10);
		if (i > 5 && i < 10) {

		if (i == 9) {
		i = 90;
		n = 90;
		break;
		}
		if (i != 90) {
		n = i * 10 + r.nextInt(10);
		while (n < 65) {
		n = i * 10 + r.nextInt(10);
		}
		}

		break;
		}
		}
		c = (char) n;

		str = String.valueOf(c) + str;
		}
		while (true) {
		i = r.nextInt(10000000);
		if (i > 999999)
		break;
		}
		str = str + i;

		// check for duplications

		return str;
		}



}
