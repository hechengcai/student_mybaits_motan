package com.student.room.server.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.ibatis.session.SqlSession;
import org.junit.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.student.room.server.base.impl.UserImpl;
import com.student.room.server.mapper.ColumnsMapper;
import com.student.room.server.mapper.MybatisHelper;
import com.student.room.server.mapper.UserMapper;
import com.student.room.service.api.IUser;
import com.student.room.service.model.ColumnsInfo;
import com.student.room.service.table.model.UserInfo;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

@Service
public class User implements IUser {
	// @Autowired
	// private UserMapper mapper;

	@Autowired
	protected UserMapper mapper;

	public UserInfo getbyid(int id) {
		return mapper.selectByPrimaryKey(id);
	}

	@Override
	public PageInfo<UserInfo> selectBypage(UserInfo t, int page, int rows) {
		//t.setId(2);
		Example example = new Example(UserInfo.class);
		//
		//mapper.selectByExample(example)
		// 分页查询
		PageHelper.startPage(page, rows,"id");
		//List<UserInfo> u= mapper.selectByExample(example);
		//System.out.println(mapper.selectByExample(example));
		List<UserInfo> p=mapper.select(t);		
		PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(p);
		//PageInfo<UserInfo> pageInfo = new PageInfo<UserInfo>(p);
	    //System.out.println(pageInfo.getList());
	    //System.out.println(p.getPageSize());
	    //System.out.println(p.getResult().size());
	    //System.out.println(p.getResult().get(1));
	     return pageInfo;
	}

}
