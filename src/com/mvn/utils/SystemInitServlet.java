package com.mvn.utils;

import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;

public class SystemInitServlet extends HttpServlet {
	private static final long serialVersionUID = -2997009340063830645L;

	private static ServletContext context;

	private static String webApp;
	/**
	 * Destruction of the servlet.
	 */
	public void destroy() {
		super.destroy();
	}

	/**
	 * Initialization of the servlet. 
	 * @throws ServletException
	 */
	public void init(ServletConfig config) throws ServletException {
		ServletContext context = config.getServletContext();
		if (SystemInitServlet.context == null) {
			SystemInitServlet.context = context;
		}
		if (SystemInitServlet.webApp == null) {
			SystemInitServlet.webApp = SystemInitServlet.context.getContextPath();
		}
	}

	/**
	 * @return context
	 */
	public static ServletContext getContext() {
		return context;
	}

	/**
	 * @param context
	 */
	public static void setContext(ServletContext context) {
		SystemInitServlet.context = context;
	}

	/**
	 * @return webApp
	 */
	public static String getWebApp() {
		return webApp;
	}

	/**
	 * @param webApp
	 */
	public static void setWebApp(String webApp) {
		SystemInitServlet.webApp = webApp;
	}
	
}
