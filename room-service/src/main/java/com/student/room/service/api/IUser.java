package com.student.room.service.api;

import java.util.ArrayList;
import java.util.List;

import com.github.pagehelper.Page;
import com.github.pagehelper.PageInfo;
import com.student.room.service.table.model.UserInfo;

public interface IUser {
	public UserInfo getbyid(int id);
	PageInfo<UserInfo> selectBypage(UserInfo t, int page, int rows) ;
}
