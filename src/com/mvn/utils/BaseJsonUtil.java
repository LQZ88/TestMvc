package com.mvn.utils;

import java.beans.IntrospectionException;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.io.IOException;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;
import net.sf.json.JsonConfig;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * 
 * @author Admin
 *
 */
public class BaseJsonUtil {
    private static Log log = LogFactory.getLog(BaseJsonUtil.class);
    /**
     * 将Object包含 \ \ 格式的内容转为JSON类型的String
     * @param obj
     * @return
     */
    public static String object2json(Object obj) {
        StringBuilder json = new StringBuilder();
        if (obj == null) {
            json.append("\"\"");
        } else if (obj instanceof String || obj instanceof Integer
                || obj instanceof Float || obj instanceof Boolean
                || obj instanceof Short || obj instanceof Double
                || obj instanceof Long || obj instanceof BigDecimal
                || obj instanceof BigInteger || obj instanceof Byte) {
            json.append("\"").append(string2json(obj.toString())).append("\"");
        } else if (obj instanceof Object[]) {
            json.append(array2json((Object[]) obj));
        } else if (obj instanceof List) {
            json.append(list2json((List<?>) obj));
        } else if (obj instanceof Map) {
            json.append(map2json((Map<?, ?>) obj));
        } else if (obj instanceof Set) {
            json.append(set2json((Set<?>) obj));
        } else {
            json.append(bean2json(obj));
        }
        return json.toString();
    }
    /**
     * 将Object包含 { : , : }格式的内容转为JSON类型的String
     * @param bean
     * @return
     */
    public static String bean2json(Object bean) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        PropertyDescriptor[] props = null;
        try {
            props = Introspector.getBeanInfo(bean.getClass(), Object.class)
                    .getPropertyDescriptors();
        } catch (IntrospectionException e) {
        }
        if (props != null) {
            for (int i = 0; i < props.length; i++) {
                try {
                    String name = object2json(props[i].getName());
                    String value = object2json(props[i].getReadMethod().invoke(bean));
                    json.append(name);
                    json.append(":");
                    json.append(value);
                    json.append(",");
                } catch (Exception e) {
                }
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }
    /**
     *  将list集合包含 [ , ]格式的内容转为JSON类型的String
     * @param list
     * @return
     */
    public static String list2json(List<?> list) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (list != null && list.size() > 0) {
            for (Object obj : list) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }
    /**
     * 将数组包含 [ , ]格式的内容转为JSON类型的String
     * @param array
     * @return
     */
    public static String array2json(Object[] array) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (array != null && array.length > 0) {
            for (Object obj : array) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }
    /**
     * 将map集合包含 { : , : }格式的内容转为JSON类型的String
     * @param map
     * @return
     */
    public static String map2json(Map<?, ?> map) {
        StringBuilder json = new StringBuilder();
        json.append("{");
        if (map != null && map.size() > 0) {
            for (Object key : map.keySet()) {
                json.append(object2json(key));
                json.append(":");
                json.append(object2json(map.get(key)));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, '}');
        } else {
            json.append("}");
        }
        return json.toString();
    }
    /**
     * 将set包含 [ , ]格式的内容转为JSON类型的String
     * @param set
     * @return
     */
    public static String set2json(Set<?> set) {
        StringBuilder json = new StringBuilder();
        json.append("[");
        if (set != null && set.size() > 0) {
            for (Object obj : set) {
                json.append(object2json(obj));
                json.append(",");
            }
            json.setCharAt(json.length() - 1, ']');
        } else {
            json.append("]");
        }
        return json.toString();
    }
    /**
     * 
     * @param s
     * @return
     */
    public static String string2json(String s) {
        if (s == null){
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < s.length(); i++) {
            char ch = s.charAt(i);
            switch (ch) {
            case '"':
                sb.append("\\\"");
                break;
            case '\\':
                sb.append("\\\\");
                break;
            case '\b':
                sb.append("\\b");
                break;
            case '\f':
                sb.append("\\f");
                break;
            case '\n':
                sb.append("\\n");
                break;
            case '\r':
                sb.append("\\r");
                break;
            case '\t':
                sb.append("\\t");
                break;
            case '/':
                sb.append("\\/");
                break;
            default:
                if (ch >= '\u0000' && ch <= '\u001F') {
                    String ss = Integer.toHexString(ch);
                    sb.append("\\u");
                    for (int k = 0; k < CommUtils.INT_UNMBER4 - ss.length(); k++) {
                        sb.append('0');
                    }
                    sb.append(ss.toUpperCase());
                } else {
                    sb.append(ch);
                }
            }
        }
        return sb.toString();
    }
    /**
     * 返回utf-8格式内容 转为String类型
     * @param response
     * @param obj 传入的内容
     */
    public static void writeResult(HttpServletResponse response, Object obj) {
        response.addHeader("Cache-Control", "no-cache");
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(obj.toString());
        } catch (IOException e) {
        	log.error(e);
            e.printStackTrace();
        }
    }

    /**
     * 返回json格式的数据以及条数
     * 
     * @param response
     * @param list 数据
     * @param count 数据总条数
     */
    public static void jsonObjectResult(HttpServletResponse response, Object list, int count) {
        JSONObject json = new JSONObject();
        json.put("rows", list);
        json.put("total", count);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
    }
    /**
     * 返回 页面datagrid的数据分页的
     * 
     * @param response
     * @param list 数据
     * @param total 数据总条数
     * @param config
     */
    public static void jsonObjectResult(HttpServletResponse response,
            Object list, int count, JsonConfig config) {
        JSONArray subMsgs = JSONArray.fromObject(list, config);
        JSONObject json = new JSONObject();
        json.put("rows", subMsgs.toString());
        json.put("total", count);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
    }
    /**
     * 返回 页面datagrid的数据不分页
     * @param response
     * @param list 数据信息
     */
    public static void jsonObjectResult(HttpServletResponse response,
            Object list) {
        JSONObject json = new JSONObject();
        json.put("rows", list);
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(json.toString());
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
    }
    /**
     * 返回JSONObject 格式内容
     * @param response
     * @param jsonObj 完整的JSONObject格式的内容
     */
    public static void jsonObjectResult(HttpServletResponse response,
            JSONObject jsonObj) {
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(jsonObj.toString());
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
    }
    /**
     * 返回JSONArray 格式内容
     * @param response
     * @param jsonArr
     */
    public static void jsonArrayResult(HttpServletResponse response,
            JSONArray jsonArr) {
        response.setCharacterEncoding("utf-8");
        try {
            response.getWriter().write(jsonArr.toString());
        } catch (IOException e) {
            log.error(e);
            e.printStackTrace();
        }
    }
    /**
     * 返回 JSONObject 格式内容 
     * @param response
     * @param str 输入的键
     * @param obj 输入的值
     */
    public static void strToJson(HttpServletResponse response, String str, Object obj) {
        response.setCharacterEncoding("utf-8");
        JSONObject jsonObj = new JSONObject();
        jsonObj.put(str, obj);
        try {
            response.getWriter().write(jsonObj.toString());
        } catch (IOException e) {
        	log.error(e);
            e.printStackTrace();
        }
    }

    public static <T> org.json.JSONArray listToJSON(List<T> list,
            Class<T> classT) {
        Field[] fields = classT.getDeclaredFields();
        org.json.JSONArray datas = new org.json.JSONArray();
        try {
            Field.setAccessible(fields, true);
            if (list != null){
                for (T bean : list) {
                    datas.put(beanToJSON(bean, classT));
                }
            }
        } catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
        }
        return datas;
    }

    public static <T> JSONObject beanToJSON(T bean, Class<T> classT) {
        Field[] fields = classT.getDeclaredFields();
        JSONObject o = new JSONObject();
        try {
            Field.setAccessible(fields, true);
            for (int i = 0; i < fields.length; i++) {
                if (bean != null) {
                    o.put(fields[i].getName(), fields[i].get(bean));
                }
            }
        } catch (Exception e) {
        	log.error(e);
            e.printStackTrace();
        }
        return o;
    }
    /**
     * 直接返回任意格式的结果
     * 字符格式为utf-8
     * @param response
     * @param obj
     */
    public static void printInfo(HttpServletResponse response, Object obj) {
		response.setCharacterEncoding("utf-8");
		try {
			response.getWriter().print(obj);
		} catch (IOException e) {
			log.error(e);
			e.printStackTrace();
		}
	}
}
