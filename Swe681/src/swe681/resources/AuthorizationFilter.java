package swe681.resources;

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

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class AuthorizationFilter implements Filter {

	private String[] authWhitelist  = {"/Welcome.xhtml", "/Error.xhtml", "javax.faces.resource", "/CreateAccount.xhtml"};
	
	public AuthorizationFilter() {
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);

			String reqURI = reqt.getRequestURI();
			if (requestIsAuthWhitelist(reqURI) || userIsLoggedIn(ses))
				chain.doFilter(request, response);
			else
				resp.sendRedirect(reqt.getContextPath() + "/Welcome.xhtml");
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
	
	private boolean userIsLoggedIn(HttpSession ses) {
		return (ses != null && ses.getAttribute("user") != null);
	}
	
	private boolean requestIsAuthWhitelist(String reqURI) {
		for(String str : authWhitelist){
			if(reqURI.indexOf(str) >= 0) {
				return true;
			}
		}
		return false;
	}

	@Override
	public void destroy() {

	}
}