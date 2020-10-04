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

	
	public void destroy() { }
	
	public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {
		
		HttpServletResponse _res = (HttpServletResponse) res;
		HttpServletRequest _req = (HttpServletRequest) req;
		String ctxPath = req.getServletContext().getContextPath();
		String uri = _req.getRequestURI();
		HttpSession session = _req.getSession(false);
		String url = uri.substring(ctxPath.length());
		
		
		if (needLogin(url)) {
			if (isLogedIn(session)) {
				if (isAllow(url, session))
					chain.doFilter(_req, _res);
				else 
					_res.sendError(HttpServletResponse.SC_UNAUTHORIZED);
			}
			else 
				_req.getRequestDispatcher("/login")
				.forward(_req, res);
		} else {
			chain.doFilter(_req, _res);
		}
	}
	

	public void init(FilterConfig fConfig) throws ServletException { }
	
	private boolean needLogin(String url) {
		if (url.startsWith("/static") || url.startsWith("/login") || url.startsWith("/register"))
			return false;
		return false;
	}
	
	private boolean isAllow(String url, HttpSession session) {
		return true;
	}
	
	private boolean isLogedIn(HttpSession session) {
		return session != null && session.getAttribute("user") != null;
	}

}
