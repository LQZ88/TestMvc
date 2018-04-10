package com.mvn.system.controller;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.google.gson.Gson;
import com.mvn.system.filter.OnLineUserListener;
import com.mvn.system.model.TreeModel;
import com.mvn.system.model.UserInfo;
import com.mvn.system.model.UserLoginInfo;
import com.mvn.system.service.TreeInfoService;
import com.mvn.system.service.UserInfoService;
import com.mvn.utils.JSONUtil;
import com.mvn.utils.MyPassUtils;
import com.mvn.utils.PropertiesUtil;

/**
 * 用户登录操作方法
 * @author LQZ
 *
 */
@Controller
@RequestMapping("/userLoginController")
public class UserLoginController extends MultiActionController {
    protected static Log log = LogFactory.getLog(UserLoginController.class);
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private TreeInfoService treeInfoService;
    /**
     * 登录系统进行验证判断后进入主界面
     * @param request
     * @param response
     * @param model
     */
	@RequestMapping("/getLoginUserInfo.do")
    public void getLoginUserInfo(HttpServletRequest request,
			HttpServletResponse response,UserLoginInfo model){
		int msg =0;
		try {
			//将获取到的 loginPass 加密后存入MODEL中
	    	UserLoginInfo usermodel = userInfoService.getLoginUserInfo(model.getLoginname());
	    	if(usermodel!=null){
	    		model.setLoginpass(MyPassUtils.encryptMD5(model.getLoginpass(),usermodel.getPasscode()));
	    		if(usermodel.getLoginpass().equals(model.getLoginpass())){
		    		// 是否强行登陆
		    		String isLogin = request.getParameter("isLogin");
		    		// 设置登录用户是否存在
		            boolean isExist = false;
		            if ("0".equals(isLogin)) {// 判断用户是否存在
		            	UserInfo userExist = OnLineUserListener.onLineUserMap.get(usermodel.getUserid());
		                if (null != userExist) {
		                	msg=2;// 已有用户
		                    isExist = true;
		                }
		            }
		            if (!isExist) {
		            	UserInfo bean = new UserInfo();bean.setId(usermodel.getUserid());
		            	UserInfo userInfo = userInfoService.getUserInfo(bean);
		            	userInfo.setLoginTime(new Date().getTime());
		            	//设置session
		            	request.getSession().setAttribute("ISLOGIN_KEY", "LOGIN");
		            	request.getSession().setAttribute("userInfo", userInfo);
		                msg=1;
		            }
		    	}
	    	}
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONUtil.strToJson(response, "msg", msg);
    }
	/**
	 * 退出系统 清除session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/setLoginUserOut.do")
    public String setLoginUserOut(HttpServletRequest request,
            HttpServletResponse response) {
        HttpSession session = request.getSession();
        if (session.getAttribute("userInfo") != null) {
        	UserInfo loginUser = (UserInfo) session.getAttribute("userInfo");
            session.setAttribute("loginTime", loginUser.getLoginTime());
            session.setAttribute("removeUserId", loginUser.getId());
            session.removeAttribute("userInfo");
            session.removeAttribute("removeUserId");
            session.removeAttribute("ISLOGIN_KEY");
        }
        return "/view/loginPage";
    }
	/**
	 * 获取登录用户菜单资源信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getUserDicInfoTree.do")
    public void getUserDicInfoTree(HttpServletRequest request,
            HttpServletResponse response) {
        String resourceJson = "";
        HttpSession session = request.getSession();
        UserInfo user = (UserInfo) session.getAttribute("userInfo");
        if (user == null) {
            resourceJson = "";
        } else {
            Gson gson = new Gson();
            List<TreeModel> list = new ArrayList<TreeModel>();
            boolean isDevelop = false;
            if (org.apache.commons.lang.StringUtils.isNotEmpty(PropertiesUtil.getPropertyValues("SysModel"))) {
                isDevelop = "develop".equals(PropertiesUtil.getPropertyValues("SysModel"));
            }
            if (isDevelop) {
                list = treeInfoService.getAllDicInfo();
            } else {
                list = treeInfoService.getUserDicInfo(user.getId());
            }
            for (TreeModel sm : list) {
                // 展开根节点
                if (sm.getParentId() == -1) {
                    sm.setOpen(true);
                }
                if (sm.getUrl() != null && !"".equals(sm.getUrl())) {
                    sm.setClick("addTabPage('" + sm.getName() + "','" + sm.getUrl() + "','" + sm.getId() + "')");
                    sm.setUrl(null);
                }
            }
            resourceJson = gson.toJson(list);
            
        }
        JSONUtil.writeResult(response, resourceJson);
    }
}
