package com.student.room.web;

import java.io.IOException;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

import org.apache.commons.lang.StringUtils;
import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.FileSystemResource;
import org.springframework.core.io.Resource;
import org.springframework.web.context.support.ServletContextResource;

/**
 * Log4j Configuration 
 * 
 * @author maomh
 *
 */
public class Log4jConfigListener implements ServletContextListener {
	private static final String LOG4J_PROPERTIES_LOCATION ="log4j.properties.location";
	private static final String DEFAULT_LOG4J_PROPERTY_LOCATION="classpath:META-INF/assets/log4j.properties";
	

	@Override
	public void contextInitialized(ServletContextEvent sce) {
		ServletContext sc = sce.getServletContext();
		String location = StringUtils.trim(sc.getInitParameter(LOG4J_PROPERTIES_LOCATION));
		// -- 若 location 没有值，则先试探 classpath:log4j.properties，否则采用默认配置
		if (StringUtils.isEmpty(location)) {
			Resource res =new ClassPathResource("log4j.properties");
			if (res.exists()) {
				Logger logger =Logger.getLogger(getClass());
				if (logger.isInfoEnabled()) {
					logger.info("log4j 配置文件位置为：" +res.toString());
				}
				return;
			}
			location =DEFAULT_LOG4J_PROPERTY_LOCATION;
		}
		Resource res =null;
		if (location.startsWith("classpath:")) {
			res =new ClassPathResource(StringUtils.trim(location.substring(10)));
		} else if (location.startsWith("file:")) {
			res =new FileSystemResource(StringUtils.trim(location.substring(5)));
		} else {
			res =new ServletContextResource(sc, location);
		}
		try {
			PropertyConfigurator.configure(res.getInputStream().toString());
			LoggerFactory.getLogger(getClass()).info("成功加载 Log4j 配置文件 {}", res.toString());
		} catch (IOException e) {
			throw new RuntimeException("无法加载 Log4j 的配置文件 " +res.toString());
		}
	}

	@Override
	public void contextDestroyed(ServletContextEvent sce) {}
	
}
