package co.workamerica.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet Filter implementation class AdministratorAuthenticationFilter
 */
@WebFilter(filterName = "AdministratorAuthenticationFilter", urlPatterns = { "/admin-aggregated-statistics.jsp",
		"/admin-approve.jsp", "/admin-candidate-list.jsp", "/admin-candidate-profile.jsp", "/admin-client-list.jsp",
		"/admin-client-session-statistics.jsp", "/admin-client-statistics.jsp", "/admin-criteria-list.jsp",
		"/admin-statistics-list.jsp", "/admin-statistics.jsp", "/administration.jsp" })
public class AdministratorAuthenticationFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();

		if (session.getAttribute("user") == null) {
			session.setAttribute("error",
					"You have been logged out due to inactivity or invalid authentication. Please login again.");
			((HttpServletResponse) response).sendRedirect("team.jsp");
		} else {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void destroy() {

	}
}
