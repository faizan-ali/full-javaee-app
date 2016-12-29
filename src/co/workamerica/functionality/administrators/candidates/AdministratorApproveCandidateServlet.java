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

@WebServlet("/AdministratorApproveCandidateServlet")
public class AdministratorApproveCandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();

            Candidates candidate = CandidatePersistence.getCandidateByID(Integer.parseInt(request.getParameter("candidateID")));

            if (candidate != null) {
                candidate.setApproved("Yes");
                try {
                    CandidatePersistence.merge(candidate);
                    List<Candidates> approvalList = CandidatePersistence.getApprovalList();
                    List<Candidates> listCandidates = CandidatePersistence.getAll();
                    session.setAttribute("listCandidates", listCandidates);
                    session.setAttribute("approvalList", approvalList);
                    response.sendRedirect("admin/admin-approve.jsp");
                } catch (Exception e) {
                    e.printStackTrace();
                    response.sendRedirect("admin/admin-approve.jsp");
                }
            } else {
                response.sendRedirect("admin/admin-approve.jsp");
            }

	}

}
