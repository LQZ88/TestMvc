package com.mvn.system.controller;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
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

import com.mvn.system.model.DicDigInfo;
import com.mvn.system.model.DicInfo;
import com.mvn.system.service.DicDigInfoService;
import com.mvn.system.service.DicInfoService;
import com.mvn.utils.CommUtils;
import com.mvn.utils.BaseJsonUtil;
import com.mvn.utils.OtherUtil;

import net.sf.json.JSONArray;

/**
 * 资源菜单管理方法
 * @author LQZ
 *
 */
@Controller
@RequestMapping("/dicInfoController")
public class DicInfoController extends MultiActionController {
    protected static Log log = LogFactory.getLog(DicInfoController.class);
    @Resource
    private DicInfoService dicInfoService;
    @Resource
    private DicDigInfoService dicDigInfoService;
    /**
     * 加载选中菜单下的所有资源数据
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getDicInfoList.do")
    public void getDicInfoList(HttpServletRequest request, HttpServletResponse response) {
		String dicId = request.getParameter("dicId");
		List<DicInfo> dicList = dicInfoService.getDicInfoNextById(Integer.parseInt(dicId));
        BaseJsonUtil.jsonObjectResult(response, dicList);
    }
	/**
	 * 获取树形菜单数据
	 * @param request
	 * @param response
	 * @throws IOException
	 */
	@RequestMapping("/getTreeMenu")
    public void getTreeMenu(HttpServletRequest request, HttpServletResponse response) throws IOException {
        List<DicInfo> resourceList = dicInfoService.getDicInfoByAll();
        List<String> list = OtherUtil.dicTreeData(resourceList, null);
        BaseJsonUtil.printInfo(response, JSONArray.fromObject(list));
    }
	
	/**
	 * 获取资源类型集合
	 * @param reuqest
	 * @param response
	 */
	@RequestMapping("/findDicdigByTypeJson.do")
	public void findDicdigByTypeJson(HttpServletRequest reuqest, HttpServletResponse response){
		List<DicDigInfo> sysDicList = new ArrayList<DicDigInfo>();
		try{
			DicDigInfo bean = new DicDigInfo();
			bean.setDicCode("-1");bean.setValue("请选择......");
			sysDicList = dicDigInfoService.findDicdigByTypeAlias("SYS_RES_TYPE");
			sysDicList.add(0, bean);
		}catch(Exception e){
			log.error("获取资源类型集合异常", e);
		}
		BaseJsonUtil.jsonArrayResult(response, JSONArray.fromObject(sysDicList));
	}
	/**
	 * 获取所有菜单资源数据的id和name值数组
	 * @param reuqest
	 * @param response
	 */
	@RequestMapping("/findDicInfoByNameJson.do")
	public void findDicInfoByNameJson(HttpServletRequest reuqest, HttpServletResponse response){
		List<HashMap<String, Object>> dicList = new ArrayList<HashMap<String, Object>>();
		try{
			HashMap<String, Object> map = new HashMap<String, Object>(CommUtils.DEFAULT_HASHMAP_SIZE);
			map.put("id", -1);map.put("name", "请选择......");
			dicList = dicInfoService.findDicInfoByNameJson();
			dicList.add(0, map);
		}catch(Exception e){
			log.error("获取所有菜单资源数据的id和name值数组集合异常", e);
		}
		BaseJsonUtil.jsonArrayResult(response, JSONArray.fromObject(dicList));
	}
	/**
	 * 到添加菜单资源数据页面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toAddDicInfo.do")
    public ModelAndView toAddDicInfo(HttpServletRequest request, HttpServletResponse response) {
        ModelAndView mav = new ModelAndView("/view/system/dic/dicInfoFrom");
        String dicId = request.getParameter("dicId");
        DicInfo model = new DicInfo();
        model.setIsLevel("0");
        model.setTarget("0");
        model.setIspermissions("1");
        model.setIsavaliable("1");
        mav.addObject("dicId", dicId);
        mav.addObject("dicInfo", model);
        return mav;
    }
	/**
	 * 到修改菜单资源数据界面
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/toEditDicInfo.do")
    public ModelAndView toEditDicInfo(HttpServletRequest request, HttpServletResponse response) {
		ModelAndView mav = new ModelAndView("/view/system/dic/dicInfoFrom");
        String dicId = request.getParameter("dicId");
        if (OtherUtil.measureNotNull(dicId)) {
        	DicInfo model = dicInfoService.getDicInfoById(Integer.parseInt(dicId));
            mav.addObject("dicInfo", model);
            mav.addObject("dicId", model.getParId());
        }
        return mav;
    }
	/**
	 * 保存修改数据
	 * @param request
	 * @param response
	 * @param dicInfo
	 */
	@RequestMapping("/editDicInfo.do")
    public void editDicInfo(HttpServletRequest request, HttpServletResponse response,DicInfo dicInfo) {
        String oper = request.getParameter("oper");
        try {
            if (OtherUtil.measureNotNull(oper)) {
                if (CommUtils.SAVE.equals(oper)) {
                	dicInfoService.saveDicInfo(dicInfo);
                } else if (CommUtils.UPDATE.equals(oper)) {
                	dicInfoService.updateDicInfo(dicInfo);
                    return;
                }
            }
            BaseJsonUtil.printInfo(response, "true");
        } catch (Exception e) {
        	BaseJsonUtil.printInfo(response, "false");
        }
    }
	/**
	 *  验证编号是否重复
	 * @param request
	 * @param response
	 */
	@RequestMapping("/getdicInfoCode.do")
	public void getdicInfoCode(HttpServletRequest request, HttpServletResponse response) {
		try {
			String dicInfoCode = request.getParameter("dicInfoCode");
			DicInfo dicInfo = dicInfoService.getdicInfoCode(dicInfoCode);
		    if (OtherUtil.measureNotNull(dicInfo)) {
		    	BaseJsonUtil.printInfo(response, false);
		    } else {
		    	BaseJsonUtil.printInfo(response, true);
		    }
		} catch (Exception e) {
        	logger.error(e);
        }
	}
}
