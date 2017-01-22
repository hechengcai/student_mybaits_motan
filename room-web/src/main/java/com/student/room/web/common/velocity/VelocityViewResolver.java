package com.student.room.web.common.velocity;

import java.util.Map;

import javax.servlet.ServletContext;

import org.apache.velocity.app.VelocityEngine;
import org.apache.velocity.runtime.log.CommonsLogLogChute;
import org.apache.velocity.runtime.resource.loader.ClasspathResourceLoader;
import org.apache.velocity.runtime.resource.loader.FileResourceLoader;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.ApplicationContext;
import org.springframework.web.servlet.view.AbstractTemplateViewResolver;
import org.springframework.web.servlet.view.AbstractUrlBasedView;

import com.student.room.web.common.velocity.parse.WebUberspector;

/**
 * Velocity 视图解释器
 * 
 * 
 * @author maomh
 *
 */
public class VelocityViewResolver extends AbstractTemplateViewResolver implements InitializingBean {
	// 固定且唯一的 Velocity 引擎
	private final VelocityEngine ve =new VelocityEngine();
	private Map<String, Object> velocityConfigs;
	private String encoding ="UTF-8";
	
	private boolean enableLayout;
	private String layoutKey ="layout";
	private String screenContentKey ="screen_content";
	private String defaultLayout ="layout.vm";
	private String emptyLayout ="empty";
	
	/**
	 * Velocity 配置项
	 * 
	 * @param velocityConfigs
	 */
	public void setVelocityConfigs(Map<String, Object> velocityConfigs) {
		this.velocityConfigs =velocityConfigs;
	}
	
	/**
	 * VelocityEngine在读取模板时所采用的字符编码 默认是 UTF-8
	 * 
	 * @param encoding
	 */
	public void setEncoding(String encoding) {
		this.encoding =encoding;
	}
	
	/**
	 * 是否启用 Layout（布局），如果此项为false，则关于 layout 所有属性自动失效
	 * 
	 * @param enableLayout
	 */
	public void setEnableLayout(boolean enableLayout) {
		this.enableLayout =enableLayout;
	}
	
	/**
	 * 在模板上下文中控制布局文件的属性名  默认是 layout
	 * 
	 * @param layoutKey
	 */
	public void setLayoutKey(String layoutKey) {
		this.layoutKey =layoutKey;
	}
	
	/**
	 * 在布局文件上下文中表示已解释完的模板内容的属性名 默认是 screen_content
	 * 
	 * @param screenContentKey
	 */
	public void setScreenContentKey(String screenContentKey) {
		this.screenContentKey =screenContentKey;
	}
	
	/**
	 * 设置默认的布局文件 相对于 ServletContext.contextPath 或 ClassPath
	 * 
	 * @param defaultLayout
	 */
	public void setDefaultLayout(String defaultLayout) {
		this.defaultLayout =defaultLayout;
	}
	
	/**
	 * 设置 空布局（手动控制某个页面禁用布局功能）名称
	 * 
	 * @param emptyLayout
	 */
	public void setEmptyLayout(String emptyLayout) {
		this.emptyLayout =emptyLayout;
	}
	
	public VelocityViewResolver() {
		this.setViewClass(requiredViewClass());
		this.setExposeSpringMacroHelpers(true);
		this.setExposeRequestAttributes(true);
		this.setAllowRequestOverride(true);
		this.setCache(true);
		this.setCacheLimit(512);
		this.setContentType("text/html;charset=UTF-8");
		this.setOrder(100);
		this.setRequestContextAttribute("rctx");
	}
	
	@Override
	protected Class<?> requiredViewClass() {
		return VelocityView.class;
	}
	
	protected void addVelocityPropertyIfNecessary(String key, Object value) {
		Object obj = ve.getProperty(key);
		if (obj == null || "".equals(obj)) {
			ve.addProperty(key, value);
		}
	}
	
	public void afterPropertiesSet() throws Exception {
		// VelocityEngine Application Attribute
		ve.setApplicationAttribute(ApplicationContext.class.getName(), getApplicationContext());
		ve.setApplicationAttribute(ServletContext.class.getName(), getServletContext());
		
		// 配置属性
		if (velocityConfigs != null && !velocityConfigs.isEmpty()) {
			for (String key : velocityConfigs.keySet()) {
				ve.addProperty(key, velocityConfigs.get(key));
			}
		}
		// 定制属性
		addVelocityPropertyIfNecessary("runtime.log.logsystem", new CommonsLogLogChute());
		addVelocityPropertyIfNecessary("input.encoding", encoding);
		addVelocityPropertyIfNecessary("output.encoding", encoding);
		addVelocityPropertyIfNecessary("resource.loader", "file, classpath");
		addVelocityPropertyIfNecessary("classpath.resource.loader.class", ClasspathResourceLoader.class.getName());
		addVelocityPropertyIfNecessary("file.resource.loader.class", FileResourceLoader.class.getName());
		addVelocityPropertyIfNecessary("file.resource.loader.path", getServletContext().getRealPath("/"));
		addVelocityPropertyIfNecessary("file.resource.loader.cache", isCache());
		addVelocityPropertyIfNecessary("resource.manager.defaultcache.size", getCacheLimit());
		addVelocityPropertyIfNecessary("velocimacro.library.autoreload", !isCache());
		addVelocityPropertyIfNecessary("runtime.introspector.uberspect", 
				"org.apache.velocity.util.introspection.UberspectImpl, " +WebUberspector.class.getName());
		// 初始化 VelocityEngine
		ve.init();
	}
	
	@Override
	protected AbstractUrlBasedView buildView(String viewName) throws Exception {
		VelocityView view =(VelocityView) super.buildView(viewName);
		view.setVelocityEngine(ve);
		view.setEncoding(encoding);
		if (enableLayout) {
			view.setEnableLayout(enableLayout);
			view.setLayoutKey(layoutKey);
			view.setScreenContentKey(screenContentKey);
			view.setDefaultLayout(defaultLayout);
			view.setEmptyLayout(emptyLayout);
		}
		return view;
	}
}
