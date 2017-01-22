package com.student.room.web.controller;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.student.room.service.api.IUser;
import com.student.room.service.table.model.UserInfo;
import com.weibo.api.motan.config.ProtocolConfig;
import com.weibo.api.motan.config.RefererConfig;
import com.weibo.api.motan.config.RegistryConfig;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;

import org.springframework.stereotype.Service;

@Controller
@Component
public class IndexController {
	//@Autowired
	@MotanReferer(basicReferer = "motantestClientBasicConfig")		
	private IUser motanDemoReferer1;	
	@RequestMapping(path="/", method=RequestMethod.GET)
	public String index(HttpServletRequest request,HttpServletResponse response ) {					
	
		request.setAttribute("list2","222");	
		if (motanDemoReferer1==null)
		{
			System.out.println("s12");	
		}	
		else
		{
			System.out.println("aabb");
			UserInfo u=motanDemoReferer1.getbyid(1);
			if (u==null)
				System.out.println("cc");
			else
			{
				System.out.println("dd");
				System.out.println(u.getUsername());
			}
			//request.setAttribute("aa", motanDemoReferer1.getbyid(1).getUsername());		
		}
	
		
		//request.setAttribute("aa", motanDemoReferer1.getbyid(55).getId());		
		//System.out.println("sss");
		
		return "index.html";
	}
	
	

}
