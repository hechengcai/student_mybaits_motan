package com.student.room.server.base.impl;

import com.github.pagehelper.PageHelper;
import com.student.room.server.mapper.UserMapper;
import com.student.room.server.service.IService;
import com.student.room.service.table.model.UserInfo;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.stereotype.Service;
import tk.mybatis.mapper.entity.Example;
import tk.mybatis.mapper.util.StringUtil;

import java.util.List;

/**
 * @author liuzh_3nofxnp
 * @since 2015-09-19 17:17
 */
@Service 
public class UserImpl extends BaseService<UserInfo> {
		public int getid()
		{
			return 0;
		}

		@Override
		public List<UserInfo> selectBypage(UserInfo t, int page, int rows) {
			// TODO Auto-generated method stub
			return null;
		}
}
