package com.mvn.system.filter;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionAttributeListener;
import javax.servlet.http.HttpSessionBindingEvent;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mvn.system.model.UserInfo;
import com.mvn.utils.CommUtils;

public class OnLineUserListener implements HttpSessionAttributeListener {
    public static Map<String, UserInfo> onLineUserMap = new HashMap<String, UserInfo>();// 在线用户map
    protected static final Log logger = LogFactory.getLog(OnLineUserListener.class);

    /**
     * 
     * 类方法说明：监听一个session对象的Attribut被Add(一个特定名称的Attribute每一次被设置)
     * 
     * @param event
     */
    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        HttpSession session = event.getSession();
        if (CommUtils.userInfo.equals(attributeName)) {// 添加在线用户
        	UserInfo loginUser = (UserInfo) session.getAttribute("userInfo");
            if (null != loginUser) {
                onLineUserMap.put(loginUser.getId(), loginUser);
            }
        } else if ("removeUserId".equals(attributeName)) {// 删除在线用户
            long loginTime = Long.parseLong(String.valueOf(session.getAttribute("loginTime")));// 退出用户的登录时间
            String removeUserId = (String) session.getAttribute("removeUserId");
            UserInfo outUser = onLineUserMap.get(removeUserId);
            if (loginTime == outUser.getLoginTime()) {
                onLineUserMap.remove(removeUserId);
            }
        }
        logger.error("当前用户共：" + onLineUserMap.size());
        logger.debug("当前用户共：" + onLineUserMap.size());
    }

    /**
     * 
     * 类方法说明：判断用户是否存在
     * 
     * @param userId
     * @return
     */
    public static boolean existUser(String userId) {
        if (null != onLineUserMap.get(userId)) {
            return false;
        }
        return true;
    }

    /**
     * 
     * 类方法说明：remove时的事件
     * 
     * @param event
     */
    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        if (CommUtils.userInfo.equals(attributeName)) {// 删除在线用户
        	UserInfo outUser = (UserInfo) event.getValue();
            onLineUserMap.remove(outUser.getId());
        }
    }

    /**
     * 
     * 类方法说明：replace(已有名称的Attribute的值被重设)
     * 
     * @param event
     */
    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        String attributeName = event.getName();
        HttpSession session = event.getSession();
        if (CommUtils.userInfo.equals(attributeName)) {// 添加在线用户
        	UserInfo loginUser = (UserInfo) session.getAttribute("userInfo");
            if (null != loginUser) {
                onLineUserMap.put(loginUser.getId(), loginUser);
            }
        }
    }
}
