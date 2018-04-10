package com.mvn.system.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.mvn.utils.JSONUtil;
@Controller
@RequestMapping("/uploadFileController")
public class UploadFileController extends MultiActionController {
    protected static final Log logger = LogFactory.getLog(UploadFileController.class);
    
    @RequestMapping("saveuploadUserFile.do")
    public void saveuploadUserFile(HttpServletRequest request,HttpServletResponse response){
    	String userImage = request.getParameter("userImage");
    	String guid = request.getParameter("guid");
    	System.out.println("\n\n"+userImage+"\n\n"+guid);
    	JSONUtil.strToJson(response, "msg", true);
    }
}