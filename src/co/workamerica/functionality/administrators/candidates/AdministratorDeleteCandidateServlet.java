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
import java.util.List;

@WebServlet("/AdministratorDeleteCandidateServlet")
public class AdministratorDeleteCandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Candidates candidate = CandidatePersistence.getCandidateByID(Integer.parseInt(request.getParameter("candidateID")));

		if (candidate != null) {
			try {
				CandidatePersistence.delete(candidate.getCandidateID());
				List<Candidates> listCandidates = CandidatePersistence.getAll();
				List<Candidates> approvalList = CandidatePersistence.getApprovalList();
				session.setAttribute("listCandidates", listCandidates);
				session.setAttribute("approvalList", approvalList);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}
		if (request.getParameter("approveList") != null && request.getParameter("approveList").equals("true")) {
			response.sendRedirect("admin/admin-approve.jsp");
		} else {
			response.sendRedirect("admin/admin-candidate-list.jsp");
		}
	}

}
