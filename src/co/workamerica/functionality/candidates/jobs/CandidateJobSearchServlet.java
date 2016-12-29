package co.workamerica.functionality.candidates.jobs;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class CandidateJobSearchServlet
 */
@WebServlet("/CandidateJobSearchServlet")
public class CandidateJobSearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    public CandidateJobSearchServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		/*

		Indeed indeed = new Indeed(request, "25");
		
		IndeedResponse searchResponse = indeed.getResponse();
		
		//Building list of locations
		HashSet<String> locationList = new HashSet<String>();
		for (IndeedResult result : searchResponse.getResults()) {
			locationList.add(result.getFormattedLocation());
		}

		session.setAttribute("keywords", request.getParameter("keywords"));
		session.setAttribute("location", request.getParameter("location"));
		session.setAttribute("locationList", locationList);
		session.setAttribute("searchResponse", searchResponse);
		response.sendRedirect("candidate-job-results.jsp");
		*/
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
