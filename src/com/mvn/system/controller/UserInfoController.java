package com.mvn.system.controller;

import java.io.UnsupportedEncodingException;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.github.pagehelper.PageHelper;
import com.mvn.system.model.FileInfo;
import com.mvn.system.model.RoleInfo;
import com.mvn.system.model.UserInfo;
import com.mvn.system.model.UserLoginInfo;
import com.mvn.system.model.UserRoleInfo;
import com.mvn.system.service.FileInfoService;
import com.mvn.system.service.RoleInfoService;
import com.mvn.system.service.UserInfoService;
import com.mvn.utils.DateUtils;
import com.mvn.utils.BaseJsonUtil;
import com.mvn.utils.MyPassUtils;
import com.mvn.utils.OtherUtil;
import com.mvn.utils.UUIDGenerator;

/**
 * 用户登录操作方法
 * @author LQZ
 *
 */
@Controller
@RequestMapping("/userInfoController")
public class UserInfoController extends MultiActionController {
    protected static Log log = LogFactory.getLog(UserInfoController.class);
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private FileInfoService fileInfoService;
    @Resource
    private RoleInfoService roleInfoService;
    /**
     * 首页获取数据list
     * @param request
     * @param response
     * @param model
     */
	@RequestMapping("/getUserInfoList.do")
    public void getUserInfoList(HttpServletRequest request, HttpServletResponse response,UserInfo model){
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		PageHelper.startPage(page, rows, false);
		int count = 0;
		List<UserInfo> list = new ArrayList<UserInfo>();
		try {
			list = userInfoService.getUserInfoByInfo(model);
			count = userInfoService.getUserInfoByInfoCount(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		BaseJsonUtil.jsonObjectResult(response, list, count);
    }
	/**
	 * 验证编号是否存在
	 * @param request
	 * @param response
	 * @param model
	 */
	@RequestMapping("/getUserInfoCode.do")
    public void getUserInfoCode(HttpServletRequest request, HttpServletResponse response,UserInfo model){
		UserInfo userInfo = userInfoService.getUserInfo(model);
	    if (OtherUtil.measureNotNull(userInfo)) {
	    	BaseJsonUtil.printInfo(response, false);
	    } else {
	    	BaseJsonUtil.printInfo(response, true);
	    }
	}
	/**
	 * 保存用户信息和用户登录信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/saveUserInfoDatas.do")
    public void saveUserInfoDatas(HttpServletRequest request, HttpServletResponse response){
		try {
			UserInfo model = new UserInfo();
			UserLoginInfo bean = new UserLoginInfo();
			FileInfo fileInfo = new FileInfo();
			String usercode = request.getParameter("usercode");
			if (OtherUtil.measureNotNull(usercode)){
				model.setUsercode(usercode);
			}
			String username = request.getParameter("username");
			if (OtherUtil.measureNotNull(username)){
				model.setUsername(username);
			}
			String sex = request.getParameter("sex");
			if (OtherUtil.measureNotNull(sex)){
				model.setSex(sex);
			}
			String age = request.getParameter("age");
			if (OtherUtil.measureNotNull(age)){
				model.setAge(Integer.parseInt(age));
			}
			String birthday = request.getParameter("birthday");
			if (OtherUtil.measureNotNull(birthday)){
				model.setBirthday(DateUtils.convertStrToDate(birthday));
			}
			String phone = request.getParameter("phone");
			if (OtherUtil.measureNotNull(phone)){
				model.setPhone(phone);
			}
			String telephone = request.getParameter("telephone");
			if (OtherUtil.measureNotNull(telephone)){
				model.setTelephone(telephone);
			}
			String emil = request.getParameter("emil");
			if (OtherUtil.measureNotNull(emil)){
				model.setEmil(emil);
			}
			String address = request.getParameter("address");
			if (OtherUtil.measureNotNull(address)){
				model.setAddress(address);
			}String state = request.getParameter("state");
			if (OtherUtil.measureNotNull(state)) {
				model.setState(OtherUtil.isBoolean(state));
				bean.setState(OtherUtil.isBoolean(state));
			}
			String loginname = request.getParameter("loginname");
			if (OtherUtil.measureNotNull(loginname)){
				bean.setLoginname(loginname);
			}
			String loginpass = request.getParameter("loginpass");
			if (OtherUtil.measureNotNull(loginpass)) {
				String passcode = String.valueOf(UUIDGenerator.getRandom());
				String md5pas = MyPassUtils.encryptMD5(loginpass, passcode);
				bean.setLoginpass(md5pas);
				bean.setPasscode(passcode);
			}
			String fileid = request.getParameter("fileid");
			model.setFileid(fileid);
			String userId = UUIDGenerator.getUUID();
			model.setId(userId);
			bean.setUserid(userId);
			model.setCreatetime(new Date());
			bean.setCreatetime(new Date());
			bean.setId(UUIDGenerator.getUUID());
			//添加用户信息
			userInfoService.saveUserInfoData(model);
			//添加用户登录信息
			userInfoService.saveLoginUserInfoData(bean);
			if(OtherUtil.measureNotNull(fileid)){
				String filetext = request.getParameter("filetext");
				fileInfo.setId(UUIDGenerator.getUUID());
				fileInfo.setFileid(userId);
				fileInfo.setFiletext(filetext.getBytes("UTF8"));
				//保存头像信息
				fileInfoService.saveFileInfo(fileInfo);
			}
			BaseJsonUtil.strToJson(response, "msg", true);
		} catch (Exception e) {
			e.printStackTrace();
			BaseJsonUtil.strToJson(response, "msg", false);
		}
    }
	/**
	 * 到修改用户信息界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toeditUserInfoPage.do")
    public ModelAndView toeditUserInfoPage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/view/system/user/userInfoEdit");
		try {
			String userId = request.getParameter("userId");
			UserInfo userInfo = userInfoService.getUserInfoByInfoData(userId);
			FileInfo info = new FileInfo();
			info.setFileid(userId);
			FileInfo fileInfo = fileInfoService.selectByFileInfo(info);
			if(fileInfo!=null){
				String filetexts=new String(fileInfo.getFiletext(),"UTF8");
				fileInfo.setFiletexts(filetexts);
			}
			mav.addObject("userInfo", userInfo);
			mav.addObject("fileInfo", fileInfo);
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return mav;
	}
	/**
	 * 修改用户信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/editUserInfoDatas.do")
    public void editUserInfoDatas(HttpServletRequest request, HttpServletResponse response){
		try {
			UserInfo model = new UserInfo();
			String username = request.getParameter("username");
			if(OtherUtil.measureNotNull(username)){
				model.setUsername(username);
			}
			String sex = request.getParameter("sex");
			if(OtherUtil.measureNotNull(sex)){
				model.setSex(sex);
			}
			String age = request.getParameter("age");
			if(OtherUtil.measureNotNull(age)){
				model.setAge(Integer.parseInt(age));
			}
			String birthday = request.getParameter("birthday");
			if(OtherUtil.measureNotNull(birthday)){
				model.setBirthday(DateUtils.convertStrToDate(birthday));
			}
			String phone = request.getParameter("phone");
			if(OtherUtil.measureNotNull(phone)){
				model.setPhone(phone);
			}
			String telephone = request.getParameter("telephone");
			if(OtherUtil.measureNotNull(telephone)){
				model.setTelephone(telephone);
			}
			String emil = request.getParameter("emil");
			if(OtherUtil.measureNotNull(emil)){
				model.setEmil(emil);
			}
			String address = request.getParameter("address");
			if(OtherUtil.measureNotNull(address)){
				model.setAddress(address);
			}
			String userId = request.getParameter("userId");
			if(OtherUtil.measureNotNull(userId)){
				model.setId(userId);
				//添加用户信息
				userInfoService.editUserInfoData(model);
			}
			BaseJsonUtil.strToJson(response, "msg", true);
		} catch (Exception e) {
			e.printStackTrace();
			BaseJsonUtil.strToJson(response, "msg", false);
		}
    }
	/**
	 * 主页获取头像
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getUserFileInfoImage.do")
    public void getUserFileInfoImage(HttpServletRequest request,
            HttpServletResponse response) {
		String filetexts = "";
		try {
			HttpSession session = request.getSession();
			UserInfo user = (UserInfo) session.getAttribute("userInfo");
			FileInfo info = new FileInfo();
			info.setFileid(user.getId());
			FileInfo fileInfo = fileInfoService.selectByFileInfo(info);
			if(fileInfo!=null){
				filetexts = new String(fileInfo.getFiletext(),"UTF8");
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		BaseJsonUtil.printInfo(response, filetexts);
	}
	/**
	 * 启用禁用用户
	 * @param request
	 * @param response
	 */
	@RequestMapping("/editStateInfoData.do")
    public void editStateInfoData(HttpServletRequest request,
            HttpServletResponse response) {
		try {
			String userId = request.getParameter("userId");
			String longinId = request.getParameter("longinId");
			String state = request.getParameter("state");
			UserInfo user = new UserInfo();
			UserLoginInfo info = new UserLoginInfo();
			user.setId(userId);
			user.setState(OtherUtil.isBoolean(state));
			info.setId(longinId);
			info.setUserid(userId);
			info.setState(OtherUtil.isBoolean(state));
			userInfoService.editUserInfoData(user);
			userInfoService.editLoginUserInfoData(info);
			BaseJsonUtil.strToJson(response, "msg", true);
		} catch (Exception e) {
			logger.error("修改失败");
			BaseJsonUtil.strToJson(response, "msg", false);
		}
	}
	/**
	 * 到修改权限页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/touserRoleInfoEditPage.do")
    public ModelAndView touserRoleInfoEditPage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/view/system/user/userRoleInfoEdit");
		List<RoleInfo> list = new ArrayList<RoleInfo>();
		try {
			//获的用户id
			String userId = request.getParameter("userId");
			RoleInfo model = new RoleInfo();
			model.setState(true);
			//获取所有的角色信息
			list = roleInfoService.selectRoleInfoByList(model);
			if(OtherUtil.measureNotNull(userId)){
				UserRoleInfo bean = new UserRoleInfo();
				bean.setUserid(userId);
				//获取用户角色信息
				List<UserRoleInfo> roleInfos = userInfoService.selectByUserRoleInfo(bean);
				for(UserRoleInfo roleuserinfo : roleInfos){
					String roleId = roleuserinfo.getRoleid();
					for(RoleInfo info : list){
						String id = info.getId();
						if(id.equals(roleId)){
							info.setIsCheck(true);
						}
					}
				}
			}
			mav.addObject("roleList", list);
			mav.addObject("userId", userId);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	/**
	 * 保存用户角色关系信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/editRoleInfoDatas.do")
    public void editRoleInfoDatas(HttpServletRequest request, HttpServletResponse response){
		try {
			//得到用户id
			String userId = request.getParameter("userId");
			//得到选中的角色id
			String[] roleIds = request.getParameterValues("roleId");
			if(OtherUtil.measureNotNull(userId)){
				UserRoleInfo bean = new UserRoleInfo();
				bean.setUserid(userId);
				//根据用户id删除原来的分配角色
				userInfoService.deleteUserRoleByKey(bean);
				if(OtherUtil.measureNotNull(roleIds)){
					bean.setId(UUIDGenerator.getUUID());
					bean.setCreatetime(new Date());
					bean.setState(true);
					for(String roleId : roleIds){
						bean.setRoleid(roleId);
						//重新添加分配角色信息
						userInfoService.insertUserRoleInfo(bean);
						bean.setId(UUIDGenerator.getUUID());
					}
				}
			}
			BaseJsonUtil.strToJson(response, "msg", true);
		} catch (Exception e) {
			logger.error("修改失败");
			BaseJsonUtil.strToJson(response, "msg", false);
		}
	}
	/**
	 * 重置密码
	 * @param request
	 * @param response
	 */
	@RequestMapping("/editUserPassInfoData.do")
    public void editUserPassInfoData(HttpServletRequest request, HttpServletResponse response) {
		try {
			String userId = request.getParameter("userId");
			String loginId = request.getParameter("loginId");
			String loginpass = request.getParameter("loginpass");
			if (OtherUtil.measureNotNull(userId)&&OtherUtil.measureNotNull(loginId)&&OtherUtil.measureNotNull(loginpass)) {
				UserLoginInfo bean = new UserLoginInfo();
				String passcode = String.valueOf(UUIDGenerator.getRandom());
				String md5pas = MyPassUtils.encryptMD5(loginpass, passcode);
				bean.setId(loginId);
				bean.setUserid(userId);
				bean.setLoginpass(md5pas);
				bean.setPasscode(passcode);
				userInfoService.editLoginUserInfoData(bean);
			}
			BaseJsonUtil.strToJson(response, "msg", true);
		} catch (Exception e) {
			BaseJsonUtil.strToJson(response, "msg", false);
		}
	}
}
