package co.workamerica.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet Filter implementation class ClientAuthenticationFilter
 */
@WebFilter(filterName = "ClientAuthenticationFilter", urlPatterns = { "/account.jsp", "/invalid.jsp", "/landing.jsp", "/pipeline.jsp", 
		"/results.jsp", "/candidate-profile.jsp", "/view-limit.jsp", "/geo-limit.jsp"})
public class ClientAuthenticationFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();

		if (session.getAttribute("user") == null) {
			session.setAttribute("error", "You have been logged out due to inactivity or invalid authentication. Please login again.");
			((HttpServletResponse) response).sendRedirect("login.jsp");
		} else {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {
		
	}

	public void destroy() {
		
	}
}
