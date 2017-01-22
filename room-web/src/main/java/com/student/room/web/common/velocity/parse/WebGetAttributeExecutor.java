package com.student.room.web.common.velocity.parse;

import java.lang.reflect.InvocationTargetException;

import javax.servlet.ServletContext;
import javax.servlet.ServletRequest;
import javax.servlet.http.HttpSession;

import org.apache.velocity.exception.VelocityException;
import org.apache.velocity.runtime.log.Log;
import org.apache.velocity.runtime.parser.node.AbstractExecutor;

/**
 * getAttribute() 解析
 * 
 * @author maomh
 *
 */
public class WebGetAttributeExecutor extends AbstractExecutor {
	private final String property;
	
	public WebGetAttributeExecutor(final Log log, final Class<?> clazz, final String property) {
		this.log =log;
		this.property =property;
		
		if (ServletRequest.class.isAssignableFrom(clazz)
				|| HttpSession.class.isAssignableFrom(clazz)
				|| ServletContext.class.isAssignableFrom(clazz)) {
			try {
				setMethod(clazz.getMethod("getAttribute", String.class));
			} catch (Exception e) {
				throw new VelocityException("无法获取 " + clazz.getSimpleName() +".getAttribute(String) 方法对象！");
			}
		}
	}

	@Override
	public Object execute(Object o) throws IllegalAccessException, InvocationTargetException {
		return isAlive() ? getMethod().invoke(o, property) : null;
	}

}
