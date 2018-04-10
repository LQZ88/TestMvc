package com.mvn.utils;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtil {

    private static Properties properties = new Properties();

    private static PropertiesUtil propertiesUtil;

    private PropertiesUtil() {
    }
    /**
     * 加载配置文件中键与值
     * @param filename 文件名称
     */
    private static void loadFile(String filename) {
        try {
            properties.load(PropertiesUtil.class.getResourceAsStream("/"+ filename));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    /**
     * 返回配置文件中键与值对象
     * @param filename 文件名称
     * @return
     */
    public static synchronized PropertiesUtil createPropertiesUtil(String filename) {
        if (propertiesUtil == null) {
            propertiesUtil = new PropertiesUtil();
        }
        loadFile(filename);
        return propertiesUtil;
    }
    /**
     * 根据传入的键得到值
     * @param key 传入的键
     * @return
     */
    public String getProperty(String key) {
        return properties.getProperty(key);
    }
	public Properties getProperties() {
		return properties;
	}
	/**
	 * 传入参数获取值 
	 * 默认在 utils.properties 配置文件中
	 * @param parameter 参数
	 * @return 值
	 */
	public static String getPropertyValues(String parameter){
		PropertiesUtil pu = PropertiesUtil.createPropertiesUtil("utils.properties");
		return pu.getProperty(parameter);
	}
}
