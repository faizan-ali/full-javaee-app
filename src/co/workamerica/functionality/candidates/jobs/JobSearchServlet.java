package co.workamerica.functionality.candidates.jobs;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class JobSearchServlet
 */
@WebServlet("/JobSearchServlet")
public class JobSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public JobSearchServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/*
		HttpSession session = request.getSession();

		Indeed indeed = new Indeed(request, "25");

		IndeedResponse searchResponse = indeed.getResponse();

		// Building list of locations
		HashSet<String> locationList = new HashSet<String>();
		for (IndeedResult result : searchResponse.getResults()) {
			locationList.add(result.getFormattedLocation());
		}

		// Building list of filters
		HashMap<String, String> filterMap = new HashMap<String, String>();
		String salary = request.getParameter("salary"), location = request.getParameter("location"),
				jobType = request.getParameter("jobType"), sort = request.getParameter("sort");

		if (location != null) {
			filterMap.put("location", location);
		}

		if (salary != null && !salary.equals("")) {
			filterMap.put("salary", salary);
		}

		if (jobType != null && !jobType.equals("")) {
			filterMap.put("jobType", jobType);
		}

		if (sort != null && !sort.equals("")) {
			filterMap.put("sort", sort);
		}

		// Pagination code
		double paginationEnd = Math.ceil((float) Integer.parseInt(searchResponse.getTotalResults()) / (float) 25);

		double currentPage = 1;

		if (request.getParameter("start") != null && !request.getParameter("start").equals("")) {
			currentPage = Math.ceil((float) Integer.parseInt(request.getParameter("start")) / (float) 25) + 1;
		}

		session.setAttribute("currentPage", (int) currentPage);
		session.setAttribute("paginationEnd", (int) paginationEnd);
		session.setAttribute("keywords", request.getParameter("keywords"));
		session.setAttribute("location", request.getParameter("location"));
		session.setAttribute("jobType", request.getParameter("jobType"));
		session.setAttribute("salary", request.getParameter("salary"));
		session.setAttribute("sort", request.getParameter("sort"));
		session.setAttribute("filterMap", filterMap);
		session.setAttribute("locationList", locationList);
		session.setAttribute("searchResponse", searchResponse);
		response.sendRedirect("candidate-job-results.jsp");
		*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
