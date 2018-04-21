package com.mvn.test;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.github.pagehelper.PageHelper;
import com.mvn.system.model.UserInfo;
import com.mvn.system.service.UserInfoService;
/**
 * 表示继承了SpringJUnit4ClassRunner类  
 * @author Admin
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = {"classpath:/config/spring-config/bean*.xml"})  
public class MyBatisTest {
	private static Logger logger = Logger.getLogger(MyBatisTest.class);
	@Resource
    private UserInfoService userInfoService;
	
	@Test
	public void getTextList(){
		UserInfo model = new UserInfo();
		PageHelper.startPage(0, 20);
		List<UserInfo> list = new ArrayList<UserInfo>();
		try {
			list = userInfoService.getUserInfoByInfo(model);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("");
		}
		System.out.println(list.size());
	}
}
