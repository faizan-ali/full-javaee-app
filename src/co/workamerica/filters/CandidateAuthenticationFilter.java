package co.workamerica.filters;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebFilter(filterName = "CandidateAuthenticationFilter", urlPatterns = { "/candidate-account.jsp",
		"/candidate-job-results.jsp", "/candidate-landing.jsp" })
public class CandidateAuthenticationFilter implements Filter {

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
			throws IOException, ServletException {

		HttpSession session = ((HttpServletRequest) request).getSession();

		if (session.getAttribute("user") == null) {
			session.setAttribute("error",
					"You have been logged out due to inactivity or invalid authentication. Please login again.");
			((HttpServletResponse) response).sendRedirect("candidate-login.jsp");
		} else {
			chain.doFilter(request, response);
		}

	}

	public void init(FilterConfig fConfig) throws ServletException {

	}

	public void destroy() {

	}
}
