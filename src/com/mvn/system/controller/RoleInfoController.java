package com.mvn.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.github.pagehelper.PageHelper;
import com.mvn.system.model.DicInfo;
import com.mvn.system.model.DicRoleInfo;
import com.mvn.system.model.RoleInfo;
import com.mvn.system.service.DicInfoService;
import com.mvn.system.service.RoleInfoService;
import com.mvn.system.service.UserInfoService;
import com.mvn.utils.JSONUtil;
import com.mvn.utils.OtherUtil;
import com.mvn.utils.UUIDGenerator;

import net.sf.json.JSONArray;

/**
 * 角色操作方法
 * @author LQZ
 *
 */
@Controller
@RequestMapping("/roleInfoController")
public class RoleInfoController extends MultiActionController {
    protected static Log log = LogFactory.getLog(RoleInfoController.class);
    @Resource
    private UserInfoService userInfoService;
    @Resource
    private DicInfoService dicInfoService;
    @Resource
    private RoleInfoService roleInfoService;
    /**
     * 首页获取数据list
     * @param request
     * @param response
     * @param model
     */
	@RequestMapping("/getRoleInfoList.do")
    public void getUserInfoList(HttpServletRequest request, HttpServletResponse response,RoleInfo model){
		Integer page = Integer.valueOf(request.getParameter("page"));
		Integer rows = Integer.valueOf(request.getParameter("rows"));
		PageHelper.startPage(page, rows, false);
		int count = 0;
		List<RoleInfo> list = new ArrayList<RoleInfo>();
		try {
			list = roleInfoService.selectRoleInfoByList(model);
			count = roleInfoService.selectRoleInfoByCount(model);
		} catch (Exception e) {
			e.printStackTrace();
		}
		JSONUtil.jsonObjectResult(response, list, count);
    }
	/**
	 * 保存角色信息
	 * @param request
	 * @param response
	 */
	@RequestMapping("/saveRoleInfoDatas.do")
    public void saveRoleInfoDatas(HttpServletRequest request, HttpServletResponse response){
		try {
			RoleInfo model = new RoleInfo();
			String rolename = request.getParameter("rolename");
			if (OtherUtil.measureNotNull(rolename)){
				model.setRolename(rolename);
			}
			String state = request.getParameter("state");
			if (OtherUtil.measureNotNull(state)) {
				model.setState(OtherUtil.isBoolean(state));
			}
			String rolenote = request.getParameter("rolenote");
			if (OtherUtil.measureNotNull(rolenote)){
				model.setRolenote(rolenote);
			}
			String roleId = UUIDGenerator.getUUID();
			model.setId(roleId);
			model.setCreatetime(new Date());
			roleInfoService.insertRoleInfo(model);
			String dicIds = request.getParameter("dicIds");
			if (OtherUtil.measureNotNull(dicIds)){
				DicRoleInfo bean = new DicRoleInfo();
				dicIds = dicIds.substring(0, dicIds.lastIndexOf(","));
				bean.setId(UUIDGenerator.getUUID());
				bean.setRoleid(roleId);
				bean.setCreatetime(new Date());
				roleInfoService.deleteDicRoleInfoByKey(bean);
				String[] dicId = dicIds.split(",");
				for(String dicid : dicId){
					bean.setDicid(dicid);
					roleInfoService.insertDicRoleInfo(bean);
					bean.setId(UUIDGenerator.getUUID());
				}
			}
			JSONUtil.strToJson(response, "msg", true);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.strToJson(response, "msg", false);
		}
    }
	/**
	 * 到增加修改角色信息界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toeditRoleInfoPage.do")
    public ModelAndView toeditRoleInfoPage(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mav = new ModelAndView("/view/system/role/roleInfoEdit");
		try {
			String roleId = request.getParameter("roleId");
			if(OtherUtil.measureNotNull(roleId)){
				RoleInfo bean = new RoleInfo();
				bean.setId(roleId);
				RoleInfo roleInfo = roleInfoService.selectRoleInfoByInfo(bean);
				mav.addObject("roleInfo", roleInfo);
			}else{
				mav.setViewName("/view/system/role/roleInfoAdd");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		return mav;
	}
	@RequestMapping("/getTreeMenu")
    public void getTreeMenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String roleId = request.getParameter("roleId");
        List<String> list = new ArrayList<String>();
        List<DicInfo> dicList = dicInfoService.getDicInfoByAll();
        if(OtherUtil.measureNotNull(roleId)){
	        DicRoleInfo model = new DicRoleInfo();
	        model.setRoleid(roleId);
	        List<DicRoleInfo> roleList = roleInfoService.selectDicRoleInfo(model);
	        list = OtherUtil.dicTreeData(dicList, roleList);
        }else{
        	list = OtherUtil.dicTreeData(dicList, null);
        }
        JSONUtil.Print(response, JSONArray.fromObject(list));
    }
	/**
	 * 修改角色信息和角色菜单数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("/editRoleInfoDatas.do")
    public void editRoleInfoDatas(HttpServletRequest request, HttpServletResponse response){
		try {
			RoleInfo model = new RoleInfo();
			String roleId = request.getParameter("roleId");
			if (OtherUtil.measureNotNull(roleId)){
				model.setId(roleId);
			}
			String rolename = request.getParameter("rolename");
			if (OtherUtil.measureNotNull(rolename)){
				model.setRolename(rolename);
			}
			String state = request.getParameter("state");
			if (OtherUtil.measureNotNull(state)) {
				model.setState(OtherUtil.isBoolean(state));
			}
			String rolenote = request.getParameter("rolenote");
			if (OtherUtil.measureNotNull(rolenote)){
				model.setRolenote(rolenote);
			}
			roleInfoService.updateRoleInfo(model);
			String dicIds = request.getParameter("dicIds");
			if (OtherUtil.measureNotNull(dicIds)){
				DicRoleInfo bean = new DicRoleInfo();
				dicIds = dicIds.substring(0, dicIds.lastIndexOf(","));
				bean.setId(UUIDGenerator.getUUID());
				bean.setRoleid(roleId);
				bean.setCreatetime(new Date());
				roleInfoService.deleteDicRoleInfoByKey(bean);
				String[] dicId = dicIds.split(",");
				for(String dicid : dicId){
					bean.setDicid(dicid);
					roleInfoService.insertDicRoleInfo(bean);
					bean.setId(UUIDGenerator.getUUID());
				}
			}
			JSONUtil.strToJson(response, "msg", true);
		} catch (Exception e) {
			e.printStackTrace();
			JSONUtil.strToJson(response, "msg", false);
		}
    }
	
	
	
}
