package com.mvn.utils;

import java.util.UUID;
/**
 * 
 * @author Admin
 *
 */
public class UUIDGenerator {
	/**
     * 描述： 生产单个UUID
     * 
     * @return
     */
    public static String getUUID() {
        UUID uuid = UUID.randomUUID();
        String str = uuid.toString();
        str = str.replaceAll("-", "");
        return str;
    }

    /**
     * 描述： 生产number个UUID
     * 
     * @param number 个数
     * @return
     */
    public static String[] getUUID(int number) {
        String[] ss = new String[number];
        for (int i = 0; i < number; i++) {
            ss[i] = getUUID();
        }
        return ss;
    }
    public static int getRandom(){
	    java.util.Random r=new java.util.Random(); 
	    return r.nextInt(); 
	}
}
