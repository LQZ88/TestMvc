package com.mvn.utils;

import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.mvn.system.model.DicInfo;
import com.mvn.system.model.DicRoleInfo;

public class OtherUtil {
    /**
     * 判断内容是否不为空
     * @param o 内容
     * @return
     */
    public static boolean measureNotNull(Object o) {
        return o != null && !o.equals("") && !o.equals("undefined");
    }

    /**
     * 格式化数字保留两位小数
     * @param o值
     * @return
     */
    public static BigDecimal formatBigDecimal(Object o) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.00");
        return new BigDecimal(df.format(o));
    }
    /**
     * 格式化保留两位小数
     * @param o值
     * @return
     */
    public static String formatString(Object o) {
        DecimalFormat df = new DecimalFormat();
        df.applyPattern("0.00");
        return df.format(o);
    }
    /***
     * 将数值格式化
     * 小数部分允许的最大位数2
     * 小数部分允许的最小位数2
     * @param o 值
     * @return
     */
    public static String numberBigDecimal(Object o) {
        NumberFormat nf = NumberFormat.getInstance();
        nf.setMinimumFractionDigits(2);
        nf.setMaximumFractionDigits(2);
        return nf.format(o);
    }
    private final static Pattern pt=Pattern.compile("\\|", Pattern.CASE_INSENSITIVE);
    /**
     * 判断“|”在字符串中出现个数
     * @param str内容
     * @return
     */
    public static int numCharString(String str) {
        int num = 0;
        Matcher mc = pt.matcher(str);
        while (mc.find()){
            num++;
        }
        return num;
    }
    /**
     * 找到指定字符在字符串中第几次出现位置
     * 返回的为值数值从0开始数起
     * @param str 内容
     * @param location 次数
     * @return
     */
    public static int indexStr(String str, int location) {
        return str.indexOf("|", location + 1);
    }
    /**
     * 去掉字符串最后出现到末尾的字符
     * @param str 内容
     * @param contantStr 要去掉的字符
     * @return
     */
    public static String subStrEnd(String str,String contantStr){
    	return str.substring(0, str.lastIndexOf(contantStr));
    }
    public static Boolean isBoolean(String str){
    	boolean flag = false;
    	if(str.contains(CommUtils.strNumber[1])||CommUtils.strNumber[1].equals(str)){
    		flag = true;
    	}
    	return flag;
    }
    
    /**
	 * 将菜单数据转为JSON格式数据
	 * @param dicList 资源数据
	 * @param roleList 已经授权的资源id
	 * @return
	 */
    public static List<String> dicTreeData(List<DicInfo> dicList,List<DicRoleInfo> roleList) {
        List<String> list = new ArrayList<String>();
        StringBuffer tempStr = null;
        if (null == dicList) {
            tempStr = new StringBuffer();
            tempStr.append("{id:\"" + 1 + "\",pId:\"\", name:\"信息管理\"}");
            list.add(tempStr.toString());
        } else {
            for (DicInfo res : dicList) {
                tempStr = new StringBuffer();
                tempStr.append("{");
                tempStr.append("id: \"" + res.getId() + "\"");
                tempStr.append(", pId: \"" + res.getParId() + "\"");
                tempStr.append(", name: \"" + res.getName().replaceAll("\"", "＂") + "\"");
                if (res.getTarget() != null && !"".equals(res.getTarget())) {
                    tempStr.append(", target: \"resIframe\"");
                }
                tempStr.append(", open: \"true\"");
                if(roleList!=null&&roleList.size()>0){
                	for (DicRoleInfo dic : roleList) {
                		Integer dicId = Integer.valueOf(dic.getDicid());
                		if(dicId==res.getId()){
                			tempStr.append(", checked: \"true\"");
                		}
                	}
                }
                tempStr.append("}");
                list.add(tempStr.toString());
            }
        }
        return list;
    }
}
