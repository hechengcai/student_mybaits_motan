package com.student.room.web.common.velocity;

import java.io.PrintWriter;
import java.lang.reflect.Proxy;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang.StringUtils;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.context.Context;
import org.apache.velocity.exception.ParseErrorException;
import org.apache.velocity.exception.ResourceNotFoundException;
import org.apache.velocity.exception.VelocityException;
import org.springframework.util.ClassUtils;
import org.springframework.web.servlet.view.AbstractTemplateView;

import com.student.room.web.LazyHttpSessionInvocationHandler;

/**
 * Velocity 视图
 * 
 * @author maomh
 *
 */
public class VelocityView extends AbstractTemplateView {
	private VelocityEngine ve;
	private String encoding;
	
	private boolean enableLayout;
	private String layoutKey;
	private String screenContentKey;
	private String defaultLayout;
	private String emptyLayout;
	
	public void setVelocityEngine(VelocityEngine ve) {
		this.ve =ve;
	}
	
	public void setEncoding(String encoding) {
		this.encoding =encoding;
	}
	
	public void setEnableLayout(boolean enableLayout) {
		this.enableLayout =enableLayout;
	}
	
	public void setLayoutKey(String layoutKey) {
		this.layoutKey =layoutKey;
	}
	
	public void setScreenContentKey(String screenContentKey) {
		this.screenContentKey =screenContentKey;
	}
	
	public void setDefaultLayout(String defaultLayout) {
		this.defaultLayout =defaultLayout;
	}
	
	public void setEmptyLayout(String emptyLayout) {
		this.emptyLayout =emptyLayout;
	}
	
	protected Template getTemplate(String path) {
		try {
			return ve.getTemplate(path, encoding);
		} catch (ResourceNotFoundException e) {
			String emsg ="找不到模板 [" +path +"]";
			logger.error(emsg, e);
			throw new VelocityException(emsg, e);
		} catch (ParseErrorException e) {
			String emsg ="解析模板 [" +path +"] 失败";
			logger.error(emsg, e);
			throw new VelocityException(emsg, e);
		}
	}
	
	@Override
	protected void renderMergedTemplateModel(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Template contentTemplate =getTemplate(getUrl());
		Context context =createVelocityContext(model, request, response);
		if (!enableLayout) {
			contentTemplate.merge(context, response.getWriter());
		} else {
			// 启用了布局
			StringBuilderWriter sbw=new StringBuilderWriter();
			contentTemplate.merge(context, sbw);
			String layout = StringUtils.trim((String) context.get(layoutKey));
			if (StringUtils.isEmpty(layout)) {
				layout =defaultLayout;
			}
			// 若 layout 的值是 “空布局”，则直接将解析后的内容输出
			if (StringUtils.equalsIgnoreCase(emptyLayout, layout)) {
				PrintWriter pw = response.getWriter();
				pw.println(sbw.toString());
				pw.flush();
				return;
			}
			// 准备 布局
			try {
				Template layoutTemplate = getTemplate(layout);
				context.put(screenContentKey, sbw.toString());
				layoutTemplate.merge(context, response.getWriter());
			} catch (ResourceNotFoundException e) {
				
			}
		}
	}

	/**
	 * 创建 {@link Context}
	 * 
	 * @param model
	 * @param request
	 * @param response
	 * @return
	 */
	protected Context createVelocityContext(Map<String, Object> model, HttpServletRequest request,
			HttpServletResponse response) {
		VelocityContext vc =new VelocityContext(model);
		// 放入 request
		vc.put("request", request);
		// 放入懒加载的 HttpSession --> 只有用到才会创建
		Object session =request.getSession(false);
		if (session == null) {
			ClassLoader loader =ClassUtils.getDefaultClassLoader();
			Class<?>[] interfaces =new Class<?>[] {HttpSession.class};
			LazyHttpSessionInvocationHandler handler = new LazyHttpSessionInvocationHandler(request);
			session =Proxy.newProxyInstance(loader, interfaces, handler);
		}
		vc.put("session", session);
		vc.put("application", getServletContext());
		vc.put("SpringRoot", getApplicationContext());
		return vc;
	}
}
