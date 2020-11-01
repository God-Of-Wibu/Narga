package com.godofwibu.narga.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@WebFilter(filterName = "security", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    public SecurityFilter() { }

	
    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain)
			throws IOException, ServletException { chain.doFilter(req, res); }
    
	public void init(FilterConfig fConfig) throws ServletException { }
	
	public void destroy() { }
}
