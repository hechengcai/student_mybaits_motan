package com.student.room.web.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.student.room.service.api.IUser;
import com.student.room.service.table.model.UserInfo;
import com.student.room.web.RetultData;
import com.weibo.api.motan.config.springsupport.annotation.MotanReferer;



@RestController
@Component
@RequestMapping("/user")  
public class UserController {
	
	@MotanReferer(basicReferer = "motantestClientBasicConfig")		
    private IUser u; 
    
    @RequestMapping("/{id}")  
    public RetultData testview(@PathVariable("id") int id) {   
    	RetultData result=new RetultData();
    	Map m=new HashMap();
    	result.addData("userinfo", u.getbyid(id));
        return result;  
    }   
    
    @RequestMapping("/list")  
    public RetultData listview() {   
    	RetultData result=new RetultData();
    	UserInfo info=new UserInfo();
    	info.setUsername("test");
    	PageInfo<UserInfo> U=u.selectBypage(info, 1, 2);
    	System.out.println(U.getTotal());
    	if (U==null)
    	{}
    	result.addData("userinfo", U.getList());
    	
        return result;  
    }    
}
