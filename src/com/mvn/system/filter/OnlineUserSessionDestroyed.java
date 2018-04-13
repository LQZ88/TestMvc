package com.mvn.system.filter;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.mvn.system.model.UserInfo;

public class OnlineUserSessionDestroyed implements HttpSessionListener {
    protected static final Log logger = LogFactory.getLog(OnlineUserSessionDestroyed.class);

    /**
     * 类方法说明：
     * 
     * @param event
     */
    @Override
    public void sessionCreated(HttpSessionEvent event) {

    }
    /**
     * 类方法说明：
     * 
     * @param event
     */
    @Override
    public void sessionDestroyed(HttpSessionEvent event) {
        UserInfo outUser = (UserInfo) event.getSession().getAttribute("userInfo");
        if (null != outUser) {
            OnLineUserListener.onLineUserMap.remove(outUser.getId());
        }
        logger.info("当前用户数：" + OnLineUserListener.onLineUserMap.size());
    }
}
