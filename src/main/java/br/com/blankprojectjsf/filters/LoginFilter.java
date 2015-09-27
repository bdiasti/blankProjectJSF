package br.com.blankprojectjsf.filters;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;

import br.com.blankprojectsf.backing.LoginBean;


public class LoginFilter implements Filter {

	
	final static Logger logger = Logger.getLogger(LoginFilter.class);
	
	
	
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		
	try{
		// Get the LoginBacking from session attribute
		LoginBean loginBean = (LoginBean)((HttpServletRequest)request).getSession().getAttribute("loginBean");
		
		// For the first application request there is no LoginBacking in the session so user needs to log in
		// For other requests LoginBacking is present but we need to check if user has logged in successfully
		if (loginBean == null || !loginBean.isLoggedIn()) {
			String contextPath = ((HttpServletRequest)request).getContextPath();
			((HttpServletResponse)response).sendRedirect(contextPath + "/login.xhtml");
		}
		
		chain.doFilter(request, response);
	}catch(Exception e ){
		logger.error("Erro ao executar o filtro ", e);
	}
			
	}

	public void init(FilterConfig config) throws ServletException {
		// Nothing to do here!
	}

	public void destroy() {
		// Nothing to do here!
	}	
	
}
