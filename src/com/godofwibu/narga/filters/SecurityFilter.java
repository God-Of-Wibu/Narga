package com.godofwibu.narga.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
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
    	
    	HttpSession session = req.getSession(false);
    	String url = getURL(req);
    	if (url.startsWith("/admin")) {
    		if (session == null) {
    			req.getRequestDispatcher("/login").forward(req, res);
    		} else {
    			User user = (User) session.getAttribute("user");
    			if (user == null) {
    				req.setAttribute("requestedPage", url);
    				req.getRequestDispatcher("/login").forward(req, res);
    			} else {
    				if (user.getRole() == Role.ADMIN)
    					chain.doFilter(req, res);
    				else 
    					res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    			}
    		}
    	} else  if (url.startsWith("/book")){
    		if (session == null) {
    			req.getRequestDispatcher("/login").forward(req, res);
    		} else {
    			User user = (User) session.getAttribute("user");
    			if (user == null) {
    				req.getRequestDispatcher("/login").forward(req, res);
    			} else {
    				if (user.getRole() == Role.MEMBER || user.getRole() == Role.ADMIN)
    					chain.doFilter(req, res);
    				else 
    					res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
    			}
    		}
    	} else {
    		chain.doFilter(req, res);
    	}
    }
    
	public void init(FilterConfig fConfig) throws ServletException { }
	
	public void destroy() { }
	
	private String getURL(HttpServletRequest req) {
		return req.getRequestURI().substring(req.getContextPath().length());
	}
}
