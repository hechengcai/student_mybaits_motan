package com.student.room.server.mapper;

import com.student.room.service.model.ColumnsInfo;

import tk.mybatis.mapper.common.IdsMapper;
import tk.mybatis.mapper.common.Mapper;

public interface ColumnsMapper extends Mapper<ColumnsInfo>,IdsMapper<ColumnsInfo> {
}
