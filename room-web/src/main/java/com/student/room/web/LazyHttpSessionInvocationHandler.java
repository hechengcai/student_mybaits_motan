package com.student.room.web;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;

import javax.servlet.http.HttpServletRequest;

/**
 * 懒加载 HttpSession 调度器
 * 
 * @author maomh
 *
 */
public class LazyHttpSessionInvocationHandler implements InvocationHandler {
	private final HttpServletRequest request;
	public LazyHttpSessionInvocationHandler(HttpServletRequest request) {
		this.request =request;
	}
	
	
	@Override
	public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
		return method.invoke(request.getSession(true), args);
	}
}
