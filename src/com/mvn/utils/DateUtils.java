package com.mvn.utils;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
/**
 * 
 * @author Admin
 *
 */
public class DateUtils {
	/**
	 * 日期类型：yyyy-MM-dd
	 */
	public static final String DATE_DAY = "yyyy-MM-dd";
	/**
	 * 日期类型：yyyy/MM/dd
	 */
	public static final String DATE_DAYS = "yyyy/MM/dd";
	/**
	 * 日期类型：yyyy-MM-dd HH:ss
	 */
	public static final String DATE_MINUTE = "yyyy-MM-dd HH:ss";
	/**
	 * 日期类型：yyyy-MM-dd HH:mm:ss
	 */
	public static final String DATE_SECOND = "yyyy-MM-dd HH:mm:ss";
	/**
	 * 默认日期格式类
	 */
	private static final DateFormat DEFAULT_FORMAT = new SimpleDateFormat(DATE_DAY);

	public static Date convertStrToDate(String dateStr) {
		Date date = null;
		if (null == dateStr || "".equals(dateStr.trim())) {
			return null;
		}
		if (dateStr.length() > (CommUtils.INT_UNMBER1+CommUtils.INT_UNMBER9)) {
			throw new java.lang.IllegalArgumentException("字符串格式错误(yyyy-MM-dd)" + dateStr);
		}
		try {
			date = DEFAULT_FORMAT.parse(dateStr);
		} catch (ParseException e) {
			throw new java.lang.IllegalArgumentException("字符串格式错误(yyyy-MM-dd)" + dateStr);
		}
		return date;
	}
	
	

	public static String getZhDate() {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_MINUTE);
		return sdf.format(new Date());
	}

	public static String toCustTime(String times) {
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_MINUTE);
		return sdf.format(Long.valueOf((new Date(Long.valueOf(times).longValue())).getTime()));
	}

	public static String getCustFormat(String format) {
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(new Date());
	}

	public static String getTime() {
		return String.valueOf(System.currentTimeMillis());
	}

	public static Long getTime(String timeStr) {
		timeStr = timeStr.split(":")[2].length() > 2 ? timeStr.substring(0, timeStr.length() - 1) : timeStr;
		SimpleDateFormat sdf = new SimpleDateFormat(DATE_MINUTE);

		try {
			return Long.valueOf(sdf.parse(timeStr).getTime());
		} catch (ParseException arg2) {
			return Long.valueOf(System.currentTimeMillis());
		}
	}

	public static Timestamp getMssqlTime() {
		return new Timestamp(System.currentTimeMillis());
	}

	public static Timestamp getMssqlTime(String d) {
		try {
			SimpleDateFormat e = new SimpleDateFormat(DATE_DAY);
			Date date = e.parse(d);
			return new Timestamp(date.getTime());
		} catch (Exception arg2) {
			return new Timestamp(System.currentTimeMillis());
		}
	}

	public static Timestamp getMssqlTimeFull(String d) {
		try {
			SimpleDateFormat e = new SimpleDateFormat(DATE_SECOND);
			Date date = e.parse(d);
			return new Timestamp(date.getTime());
		} catch (Exception arg2) {
			return new Timestamp(System.currentTimeMillis());
		}
	}

	public static Long getMssqlTimeToLong(String d) {
		try {
			SimpleDateFormat e = new SimpleDateFormat(DATE_DAY);
			Date date = e.parse(d);
			return Long.valueOf(date.getTime());
		} catch (Exception arg2) {
			return Long.valueOf(System.currentTimeMillis());
		}
	}
}
