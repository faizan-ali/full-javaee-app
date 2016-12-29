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
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Faizan on 6/13/2016.
 */
@WebServlet("/AdministratorUnapproveCandidateServlet")
public class AdministratorUnapproveCandidateServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Candidates candidate = CandidatePersistence.getCandidateByID(Integer.parseInt(request.getParameter("candidateID")));

        if (candidate != null) {
            candidate.setApproved("No");
            try {
                CandidatePersistence.merge(candidate);
                List<Candidates> approvalList = CandidatePersistence.getApprovalList();
                List<Candidates> totalCandidates = CandidatePersistence.getAll();
                List<Candidates> listCandidates = new ArrayList<Candidates>(totalCandidates);

                for (Candidates c : totalCandidates) {
                    if (c.getApproved() == null) {
                        listCandidates.remove(c);
                    } else if (c.getApproved().equals("No") || c.getApproved().isEmpty()) {
                        listCandidates.remove(c);
                    }
                }

                session.setAttribute("listCandidates", listCandidates);
                session.setAttribute("approvalList", approvalList);
                response.sendRedirect("admin/admin-candidate-list.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                response.sendRedirect("admin/admin-candidate-list.jsp");
            }
        } else {
            response.sendRedirect("admin/admin-candidate-list.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
            doPost(request, response);
    }
}
