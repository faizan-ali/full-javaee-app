package co.workamerica.functionality.administrators.candidates;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/AdministratorViewCandidateProfileServlet")
public class AdministratorViewCandidateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdministratorViewCandidateProfileServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		int candidateID = Integer.parseInt(request.getParameter("candidateID"));
		Candidates candidate = CandidatePersistence.getCandidateByID(candidateID);
		session.setAttribute("resume", "false");
		session.setAttribute("candidate", candidate);
		session.setAttribute("candidateID", candidateID);
		response.sendRedirect("admin/admin-candidate-profile.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
