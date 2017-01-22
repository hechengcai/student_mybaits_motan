package com.student.room.web;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpServletResponseWrapper;

import org.apache.commons.io.output.StringBuilderWriter;
import org.apache.commons.lang.StringUtils;

import com.student.room.web.FrameUtils;


/**
 * 支持JSP布局的 Servlet组件
 * 
 * @author maomh
 *
 */
public class LayoutJspServlet extends BaseHttpServlet {
	public static final String SC_ATTRIBUTE_LAYOUT_KEY =LayoutJspServlet.class.getName() +".layoutKey";
	public static final String SC_ATTRIBUTE_SCREEN_CONTENT_KEY =LayoutJspServlet.class.getName() +".screenContentKey";
	
	
	private static final long serialVersionUID = -950291450144132368L;
	
	private String layoutKey ="layout";
	private String screenContentKey ="screen_content";
	private String defaultLayout ="default";
	private String suffix =".jsp";
	private String emptyLayout ="empty";
	private String layoutLocation ="/WEB-INF/layouts/";
	
	private String jspServletName ="jsp";

	@Override
	public void afterAutoAssign(ServletConfig config) {
		config.getServletContext().setAttribute(SC_ATTRIBUTE_LAYOUT_KEY, layoutKey);
		config.getServletContext().setAttribute(SC_ATTRIBUTE_SCREEN_CONTENT_KEY, screenContentKey);
		
		if (logger.isInfoEnabled()) {
			String[] info =new String[] {
				"layoutKey:[" +layoutKey +"]",
				"screenContentKey:[" +screenContentKey +"]",
				"defaultLayout:[" +defaultLayout +"]",
				"suffix:[" +suffix +"]",
				"emptyLayout:[" +emptyLayout +"]",
				"layoutLocation:[" +layoutLocation +"]",
				"jspServletName:[" +jspServletName +"]"
			};
			logger.info(FrameUtils.surroundLines("启用JSP布局支持！", info));
		}
	}

	
	@Override
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// 重复响应检查
		if (response.isCommitted()) {
			return;
		}
		
		// 内容渲染
		ScreenContent content =new ScreenContent(response);
		getServletContext().getNamedDispatcher(jspServletName).include(request, content);
		
		// 确定 layout 
		String layout =StringUtils.trimToNull((String) request.getAttribute(layoutKey));
		if (layout == null) {
			layout =defaultLayout;
		}
		
		// 判断是否为禁用布局 标识
		if (StringUtils.equalsIgnoreCase(layout, emptyLayout)) {
			PrintWriter pw = response.getWriter();
			pw.println(content.sbw.toString());
			pw.flush();
			return;
		}
		
		// 存储 screenContentKey
		request.setAttribute(screenContentKey, content.sbw.toString());
		
		// 确定模板
		String layoutPath =layoutLocation +layout +suffix;
		if (!new File(getServletContext().getRealPath(layoutPath)).exists()) {
			throw new ServletException("没有找到布局模板 [" +layoutPath +"]!");
		}
		forward(layoutPath, request, response);
	}
	
	
	public String getLayoutKey() {
		return layoutKey;
	}

	public void setLayoutKey(String layoutKey) {
		this.layoutKey = layoutKey;
	}

	public String getScreenContentKey() {
		return screenContentKey;
	}

	public void setScreenContentKey(String screenContentKey) {
		this.screenContentKey = screenContentKey;
	}

	public String getDefaultLayout() {
		return defaultLayout;
	}

	public void setDefaultLayout(String defaultLayout) {
		this.defaultLayout = defaultLayout;
	}

	public String getSuffix() {
		return suffix;
	}

	public void setSuffix(String suffix) {
		this.suffix = suffix;
	}

	public String getEmptyLayout() {
		return emptyLayout;
	}

	public void setEmptyLayout(String emptyLayout) {
		this.emptyLayout = emptyLayout;
	}

	public String getLayoutLocation() {
		return layoutLocation;
	}

	public void setLayoutLocation(String layoutLocation) {
		this.layoutLocation = layoutLocation;
	}

	public String getJspServletName() {
		return jspServletName;
	}

	public void setJspServletName(String jspServletName) {
		this.jspServletName = jspServletName;
	}


	private static class ScreenContent extends HttpServletResponseWrapper {
		private StringBuilderWriter sbw;
		private PrintWriter pw;

		public ScreenContent(HttpServletResponse response) {
			super(response);
			sbw =new StringBuilderWriter();
			pw =new PrintWriter(sbw, true);
		}
		
		@Override
		public PrintWriter getWriter() throws IOException {
			return pw;
		}
		
		@Override
		public ServletOutputStream getOutputStream() throws IOException {
			throw new UnsupportedOperationException("不支持此方法！");
		}
	}
}
