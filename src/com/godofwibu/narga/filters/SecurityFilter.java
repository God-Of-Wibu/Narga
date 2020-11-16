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

import com.godofwibu.narga.entities.Role;
import com.godofwibu.narga.entities.User;

@WebFilter(filterName = "security", urlPatterns = {"/*"})
public class SecurityFilter implements Filter {

    public SecurityFilter() { }

	
    public void doFilter(ServletRequest _req, ServletResponse _res, FilterChain chain)
			throws IOException, ServletException { 
    	
    	HttpServletRequest req = (HttpServletRequest) _req;
    	HttpServletResponse res = (HttpServletResponse) _res;
    	
    	if (isAllow(req)) 
    		chain.doFilter(req, res); 
    	else 
    		req.getRequestDispatcher("/login")
    			.forward(req, res);
    }
    
	public void init(FilterConfig fConfig) throws ServletException { }
	
	public void destroy() { }
	
	private String getURL(HttpServletRequest req) {
		return req.getRequestURI().substring(req.getServletPath().length() - 1);
	}
	
	private boolean isAllow(HttpServletRequest req) {
		String URL = getURL(req);
    	
    	HttpSession session = req.getSession(false);
    	
    	if (URL.startsWith("/admin")) {
    		if (session != null) {
    			User user = (User) session.getAttribute("user");
    			return (user != null && user.getRole() == Role.ADMIN);
    		} else {
    			return false;
    		}
    	} else if (URL.startsWith("/book")) {
    		return session != null && session.getAttribute("user") != null;
    	}
    	return true;
	}
}
