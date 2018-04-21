package com.mvn.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.Random;
/**
 * 
 * @author Admin
 *
 */
public class NumberUtil {
	public static Integer toInteger(Object obj) {
		try {
			return !BaseStringUtils.isSpaceEmptys(obj) ? Integer.valueOf(obj.toString()) : null;
		} catch (Exception arg1) {
			return null;
		}
	}

	public static BigDecimal toBigDecimal(Object obj) {
		try {
			if (!BaseStringUtils.isSpaceEmptys(obj)) {
				BigDecimal e = new BigDecimal(obj.toString());
				return (new BigDecimal((new DecimalFormat("0.000")).format(e))).setScale(2, 1);
			} else {
				return null;
			}
		} catch (Exception arg1) {
			return null;
		}
	}

	public static String randomSix() {
		int max = 999999;
		int min = 100000;
		Random rand = new Random();
		int tmp = Math.abs(rand.nextInt());
		int s = tmp % max + min;
		return String.valueOf(s);
	}

	public static BigDecimal multiply(BigDecimal b1, BigDecimal b2) {
		return toBigDecimal((new DecimalFormat("0.00000")).format(b1.multiply(b2).setScale(2, 1)));
	}

	public static BigDecimal subtract(BigDecimal b1, BigDecimal b2) {
		return toBigDecimal(b1.subtract(b2));
	}

	public static BigDecimal add(BigDecimal b1, BigDecimal b2) {
		return toBigDecimal(b1.add(b2));
	}

	public static BigDecimal formatTwo(BigDecimal b1) {
		return toBigDecimal(b1);
	}

}
