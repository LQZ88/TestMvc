package com.mvn.system.filter;

import java.io.IOException;
import java.util.StringTokenizer;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.google.gson.JsonObject;
import com.mvn.system.model.UserInfo;
import com.mvn.utils.CommUtils;
import com.mvn.utils.JSONUtil;
import com.mvn.utils.PropertiesUtil;

public class LoginValidityCheck implements Filter {
    protected static final Log logger = LogFactory.getLog(LoginValidityCheck.class);
    private static final String XHR_OBJECT_NAME = "XMLHttpRequest";
    private static final String HEADER_REQUEST_WITH = "x-requested-with";
    private FilterConfig filterConfig = null;
    private boolean isSingleLogin = false;// 是否只能在一个地方登录默认false
    private boolean isOtherLogin = false;
    private String[] freePages;//过滤访问
    private String toPage;//没有登陆返回
    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        String isLogin = PropertiesUtil.getPropertyValues("isLogin");//获取配置的是否 只能在一个地方登陆
        isSingleLogin = "0".equals(isLogin)?false:true;//将字符转换为boolean类型
        this.filterConfig = filterConfig;
        String strPages = null;
        StringTokenizer strTokenizer = null;
        toPage = this.filterConfig.getInitParameter("toPage");
        strPages = this.filterConfig.getInitParameter("freePages");
        if (toPage == null || strPages == null || toPage.trim().length() == 0 || strPages.trim().length() == 0) {
            throw new ServletException("web.xml toPage,freePages");
        }
        strTokenizer = new StringTokenizer(strPages, ";");
        freePages = new String[strTokenizer.countTokens()];
        int iIndex = 0;
        while (strTokenizer.hasMoreTokens()) {
            freePages[iIndex++] = strTokenizer.nextToken();
        }
        if (!isFreePage(toPage)) {
            throw new ServletException("web.xml freePage toPage");
        } else {
            return;
        }
    }
    @Override
    public void destroy() {
        this.filterConfig = null;
    }
    @Override
    public void doFilter(ServletRequest sRequest, ServletResponse sResponse,
            FilterChain chain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) sRequest;
        HttpServletResponse response = (HttpServletResponse) sResponse;
        String strRequestURI = null;
        strRequestURI = request.getRequestURI();
        boolean doFilter = true;
        if (!isFreePage(strRequestURI) && !isValidSession(sRequest)) {
            try {
                // 登录页面url
                String strToPageURL = request.getContextPath() + toPage;
                logger.debug("strToPageURL:"+strToPageURL);
                boolean isajax = isAjaxSubmit(request);
                if (isajax) {//ajax
                    doFilter = false;
                    JsonObject jo = new JsonObject();
                    jo.addProperty("PERMISSION", "NO");
                    // 不加下面2个属性，在datagrid load 时会报错
                    jo.addProperty("rows", "");
                    jo.addProperty("total", 0);
                    JSONUtil.writeResult(response, jo);
                } else if (strRequestURI.indexOf(CommUtils.strType[1]) != -1 ||strRequestURI.indexOf(CommUtils.strType[0])!=-1) {
                    doFilter = false;
                    StringBuffer outString = new StringBuffer();
                    outString.append("<script type=\"text/javascript\">");
                    if (isOtherLogin) {
                        outString.append("alert(\"注意：你的账号已在其它地方登录！\");");
                    }
                    outString.append("top.top.location.href=\"");
                    outString.append(strToPageURL).append("\";");
                    outString.append("</script>");
                    response.addHeader("Cache-Control", "no-cache");
                    response.setCharacterEncoding("utf-8");
                    response.getWriter().println(outString.toString());
                } else {
                    response.encodeRedirectURL(strToPageURL);
                    response.sendRedirect(strToPageURL);
                }
            } catch (IOException ex) {
                logger.error("登录效验跳转错误：" + ex);
                ex.printStackTrace();
            }
        }
        if (!response.isCommitted() && doFilter) {
            chain.doFilter(sRequest, sResponse);
        }
    }
    /**
     * 判断访问类型
     * @param requestURI
     * @return
     */
    private boolean isFreePage(String requestURI) {
        int iLength = freePages.length;
        for (int i = 0; i < iLength; i++) {
            if (requestURI.endsWith(freePages[i])) {
                return true;
            }
            if (requestURI.lastIndexOf(".") != -1) {
                String postfix = requestURI.substring(requestURI.lastIndexOf(".") + 1);
                if (postfix.equalsIgnoreCase("js")|| postfix.equalsIgnoreCase("gif")|| postfix.equalsIgnoreCase("css")) {
                    return true;
                }
            }
        }
        return false;
    }

    private boolean isValidSession(ServletRequest request) {
        HttpServletRequest objHttpRequest = (HttpServletRequest) request;
        String strIsLogin = (String) objHttpRequest.getSession().getAttribute("ISLOGIN_KEY");
        boolean bIsValid = "LOGIN".equals(strIsLogin);
        UserInfo currentSessionLoginUser = (UserInfo) objHttpRequest.getSession().getAttribute("userInfo");
        // session 无用户
        if (null == currentSessionLoginUser) {
            return false;
        } else {
        	UserInfo onLineUser = OnLineUserListener.onLineUserMap.get(currentSessionLoginUser.getId());
            if (null == onLineUser) {//需要在用户禁用功能增加清除在线用户 判断才能生效 为空表示登录后被管理员禁用了
                return false;
            } else {// session 中用户的登录时间
                long loginTime = currentSessionLoginUser.getLoginTime();
                // 时间不相等,并且只能在一处登录为true 判断登录时间是否相等，如果不相等 踢出不相等的登录
                if (isSingleLogin && onLineUser.getLoginTime() != loginTime) {
                    bIsValid = false;
                    isOtherLogin = true;
                }
            }
        }
        return bIsValid;
    }

    /**
     * 判断请求是不是 ajax
     * 
     * @param request
     * @return
     */
    private boolean isAjaxSubmit(HttpServletRequest request) {
        String hqw = request.getHeader(HEADER_REQUEST_WITH);
        if (hqw != null && hqw.equalsIgnoreCase(XHR_OBJECT_NAME)) {
            return true;
        }
        return false;
    }
}
