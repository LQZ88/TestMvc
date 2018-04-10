package com.mvn.utils;


import org.springframework.security.authentication.encoding.Md5PasswordEncoder;

/**
 * 文本加密算法
 * 
 * @author LQZ
 *
 */
public class MyPassUtils {
	private static Md5PasswordEncoder passwordEncoder = new Md5PasswordEncoder();

	/**
	 * MD5加密
	 * @param pasword
	 * @return
	 * @throws Exception
	 */
	public static String encryptMD5(String pasword, String passcode) throws Exception {
		return passwordEncoder.encodePassword(pasword, "TestMvc"+passcode);
	}

}
