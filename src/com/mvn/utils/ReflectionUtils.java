package com.mvn.utils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

public class ReflectionUtils {
	private static Log logger = LogFactory.getLog(ReflectionUtils.class);

    /**
     * 直接读取对象属性值,无视private/protected修饰符,不经过getter函数.
     */
    public static Object getFieldValue(final Object object,
            final String fieldName) {
        Field field = getDeclaredField(object, fieldName);
        if (field == null){
            throw new IllegalArgumentException("Could not find field ["
                    + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        Object result = null;
        try {
            result = field.get(object);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常{}", e);
        }
        return result;
    }

    /**
     * 直接设置对象属性值,无视private/protected修饰符,不经过setter函数.
     */
    public static void setFieldValue(final Object object,
            final String fieldName, final Object value) {
        Field field = getDeclaredField(object, fieldName);

        if (field == null){
            throw new IllegalArgumentException("Could not find field [" + fieldName + "] on target [" + object + "]");
        }
        makeAccessible(field);
        try {
            field.set(object, value);
        } catch (IllegalAccessException e) {
            logger.error("不可能抛出的异常:{}", e);
        }
    }

    /**
     * 循环向上转型,获取对象的DeclaredField.
     */
    protected static Field getDeclaredField(final Object object,
            final String fieldName) {
        Assert.notNull(object);
        return getDeclaredField(object.getClass(), fieldName);
    }
    
    /**
     * 循环向上转型,获取类的DeclaredField.
     */
    protected static Field getDeclaredField(final Class<?> clazz,
            final String fieldName) {
        Assert.notNull(clazz);
        Assert.hasText(fieldName);
        for (Class<?> superClass = clazz; superClass != Object.class; superClass = superClass.getSuperclass()) {
            try {
                return superClass.getDeclaredField(fieldName);
            } catch (NoSuchFieldException e) {
                // Field不在当前类定义,继续向上转型
            }
        }
        return null;
    }

    /**
     * 强制转换fileld可访问.
     */
    protected static void makeAccessible(Field field) {
        if (!Modifier.isPublic(field.getModifiers()) || !Modifier.isPublic(field.getDeclaringClass().getModifiers())) {
            field.setAccessible(true);
        }
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public UserDao extends HibernateDao<User>
     */
    public static Class<Object> getSuperClassGenricType(Class<?> clazz) {
        return getSuperClassGenricType(clazz, 0);
    }

    /**
     * 通过反射,获得定义Class时声明的父类的范型参数的类型. 如public UserDao extends HibernateDao<User,Long>
     */
    @SuppressWarnings("unchecked")
	public static Class<Object> getSuperClassGenricType(Class<?> clazz, int index) {
        Type genType = clazz.getGenericSuperclass();

        if (!(genType instanceof ParameterizedType)) {
            logger.warn(clazz.getSimpleName() + "'s superclass not ParameterizedType");
            return Object.class;
        }

        Type[] params = ((ParameterizedType) genType).getActualTypeArguments();

        if (index >= params.length || index < 0) {
            logger.warn("Index: " + index + ", Size of " + clazz.getSimpleName() + "'s Parameterized Type: " + params.length);
            return Object.class;
        }
        if (!(params[index] instanceof Class)) {
            logger.warn(clazz.getSimpleName() + " not set the actual class on superclass generic parameter");
            return Object.class;
        }
        return (Class<Object>) params[index];
    }

    /**
     * 提取集合中的对象的属性,组合成List.
     * 
     * @param collection 来源集合.
     * @param propertityName 要提取的属性名.
     */
    public static List<Object> fetchElementPropertyToList(final Collection<?> collection,
            final String propertyName) throws Exception {
        List<Object> list = new ArrayList<Object>();
        for (Object obj : collection) {
            list.add(PropertyUtils.getProperty(obj, propertyName));
        }
        return list;
    }

    /**
     * 提取集合中的对象的属性,组合成由分割符分隔的字符串.
     * 
     * @param collection 来源集合.
     * @param propertityName 要提取的属性名.
     * @param separator 分隔符.
     */
    public static String fetchElementPropertyToString(
            final Collection<?> collection, final String propertyName,
            final String separator) throws Exception {
        List<?> list = fetchElementPropertyToList(collection, propertyName);
        return StringUtils.join(list, separator);
    }
}
