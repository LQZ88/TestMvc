package com.mvn.utils;

import java.util.UUID;

public abstract class StringUtils {
	public static boolean isEmpty(Object obj) {
		return obj == null || obj.toString().length() == 0;
	}

	public static boolean isSpaceEmptys(Object obj) {
		return obj == null || obj.toString().length() == 0 || obj.toString().replaceAll(" ", "").length() == 0;
	}

	public static boolean isEmptyOrNull(Object obj) {
		return obj == null || obj.toString().length() == 0 || obj.toString().replaceAll(" ", "").length() == 0;
	}

	public static String toString(Object obj) {
		return isSpaceEmptys(obj) ? "" : String.valueOf(obj);
	}

	public static String getOnlyStr() {
		return UUID.randomUUID().toString().toLowerCase().replaceAll("-", "");
	}

	public static String getUUID() {
		return UUID.randomUUID().toString().toLowerCase();
	}

	public static boolean isMobile(String mobile) {
		String metc = "^(13|15|18|17)\\d{9}$";
		return mobile.matches(metc);
	}

	public static String randomStr(int length) {
		String s = UUID.randomUUID().toString().toUpperCase().replaceAll("-", "");
		if (s.length() < length) {
			return s;
		} else {
			s = s.substring(0, length);
			return s;
		}
	}

	public static String bluIdCard(String idc) {
		idc = isEmpty(idc) ? "" : idc.substring(0, 4) + "**********" + idc.substring(idc.length() - 4);
		return idc;
	}

	public static boolean iSIdCardNumber(String idcard) {
		String metc = "^[1-9][0-9]{5}[1-9][0-9]{3}((0[0-9])|(1[0-2]))(([0|1|2][0-9])|3[0-1])[0-9]{3}([0-9]|X|x)$";
		return idcard.matches(metc);
	}
	
	/**
     * <p>Checks if a CharSequence is empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isEmpty(null)      = true
     * StringUtils.isEmpty("")        = true
     * StringUtils.isEmpty(" ")       = false
     * StringUtils.isEmpty("bob")     = false
     * StringUtils.isEmpty("  bob  ") = false
     * </pre>
     *
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is empty or null
     */
    public static boolean isEmpty(final CharSequence cs){
        return cs == null || cs.length() == 0;
    }
    /**
     * <p>Checks if a CharSequence is whitespace, empty ("") or null.</p>
     *
     * <pre>
     * StringUtils.isBlank(null)      = true
     * StringUtils.isBlank("")        = true
     * StringUtils.isBlank(" ")       = true
     * StringUtils.isBlank("bob")     = false
     * StringUtils.isBlank("  bob  ") = false
     * </pre>
     *
     *
     * @param cs the CharSequence to check, may be null
     * @return {@code true} if the CharSequence is null, empty or whitespace
     */
    public static boolean isBlank(final CharSequence cs){
        int strLen;
        if(cs == null || (strLen = cs.length()) == 0){
            return true;
        }

        for(int i=0; i<strLen; i++){
            if(Character.isWhitespace(cs.charAt(i)) == false){
                return false;
            }
        }

        return true;
    }
}
