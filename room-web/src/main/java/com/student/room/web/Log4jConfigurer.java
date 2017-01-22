package com.student.room.web;

import java.io.IOException;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;

public class Log4jConfigurer {
	
	public static void refresh(String location) {
		location =StringUtils.trim(location);
		if (StringUtils.isEmpty(location)) {
			return;
		}
		
		Resource res =null;
		if (StringUtils.startsWithIgnoreCase(location, "classpath:")) {
			res =new ClassPathResource(StringUtils.trim(location.substring(10)));
		} else if (StringUtils.startsWithIgnoreCase(location, "file:")) {
			res =new FileSystemResource(StringUtils.trim(location.substring(5)));
		}
		
		if (res == null) {
			res =new ClassPathResource(location);
			if (!res.exists()) {
				res =null;
			}
		}
		
		if (res == null) {
			res =new FileSystemResource(location);
			if (!res.exists()) {
				res =null;
			}
		}
		
		if (res == null) {
			throw new RuntimeException("无法加载 [" +location +"]");
		}
		
		try {
			PropertyConfigurator.configure(res.getInputStream().toString());
			LoggerFactory.getLogger(Log4jConfigurer.class).info("加载 [{}]，刷新日志配置信息", res);
		} catch (IOException e) {
			throw new RuntimeException("无法刷新日志配置信息 [" +res.toString() +"]", e);
		}
	}
}
