package com.student.room.web;

import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.util.Enumeration;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServlet;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.beanutils.PropertyUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * HttpServlet 拓展基类
 * 
 * @author maomh
 *
 */
@SuppressWarnings("serial")
public abstract class BaseHttpServlet extends HttpServlet {
	protected final Logger logger =LoggerFactory.getLogger(getClass());

	@Override
	public void init(ServletConfig config) throws ServletException {
		super.init(config);

		//-- 自动扫描 init-parameter 并采用适当的 setter 进行赋值
		Enumeration<String> names = config.getInitParameterNames();
		while (names.hasMoreElements()) {
			String name =names.nextElement();
			if (PropertyUtils.isWriteable(this, name)) {
				try {
					BeanUtils.setProperty(this, name, config.getInitParameter(name));
				} catch (IllegalAccessException | InvocationTargetException e) { //
					throw new ServletException("无法给给[" +name +"]自动赋值！", e);
				}
			}
		}
		
		// -- 自定义的初始化操作
		afterAutoAssign(config);
	}
	
	/**
	 * 在初始化参数自动扫描注入之后执行的自定义方法
	 * 
	 * @param config
	 */
	public abstract void afterAutoAssign(ServletConfig config);
	
	
	/**
	 * 请求转发
	 * 
	 * @param path
	 * @param request
	 * @param response
	 * @throws ServletException
	 * @throws IOException
	 */
	protected void forward(String path, ServletRequest request, ServletResponse response) throws ServletException, IOException {
		getServletContext().getRequestDispatcher(path).forward(request, response);
	}
}
