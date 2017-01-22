package com.student.room.server.base.impl;


import org.springframework.beans.factory.annotation.Autowired;

import com.github.pagehelper.PageHelper;
import com.student.room.server.service.IService;

import tk.mybatis.mapper.common.Mapper;
import tk.mybatis.mapper.entity.Example;

import java.util.List;

/**
 * Created by liuzh on 2014/12/11.
 */
public abstract class BaseService<T> implements IService<T> {

    @Autowired
    protected Mapper<T> mapper;

    public Mapper<T> getMapper() {
        return mapper;
    }

    @Override
    public T selectByKey(Object key) {
        return mapper.selectByPrimaryKey(key);
    }

    public int save(T entity) {
        return mapper.insert(entity);
    }

    public int delete(Object key) {
        return mapper.deleteByPrimaryKey(key);
    }

    public int updateAll(T entity) {
        return mapper.updateByPrimaryKey(entity);
    }

    public int updateNotNull(T entity) {
        return mapper.updateByPrimaryKeySelective(entity);
    }

    public List<T> selectByExample(Object example) {
        return mapper.selectByExample(example);
    }
    
    public List<T> selectBypage(Example example, int page, int rows) {
    	//Example example = new Example(t.class);
        Example.Criteria criteria = example.createCriteria();
//        if (StringUtil.isNotEmpty(t.getCountryname())) {
//            criteria.andLike("countryname", "%" + country.getCountryname() + "%");
//        }
//        if (StringUtil.isNotEmpty(country.getCountrycode())) {
//            criteria.andLike("countrycode", "%" + country.getCountrycode() + "%");
//        }
//        if (country.getId() != null) {
//            criteria.andEqualTo("id", country.getId());
//        }
        //分页查询        
        PageHelper.startPage(page, rows);
        return selectByExample(example);
       
    }

    //TODO 其他...
}
