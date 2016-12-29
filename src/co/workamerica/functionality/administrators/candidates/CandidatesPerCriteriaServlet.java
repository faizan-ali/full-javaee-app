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
import java.util.HashMap;
import java.util.List;

/**
 * Created by Faizan on 6/2/2016.
 */
@WebServlet("/CandidatesPerCriteriaServlet")
public class CandidatesPerCriteriaServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        try {
            List<Candidates> candidates = CandidatePersistence.getAll();

            HashMap<String, Integer> fieldMap = new HashMap<String, Integer>();
            HashMap<String, Integer> certificationMap = new HashMap<String, Integer>();

            for (Candidates candidate : candidates) {

                if (candidate.getAnticipatedCertification() != null && !candidate.getAnticipatedCertification().isEmpty()) {
                    for (String cert : candidate.getAnticipatedCertification().split(",")) {
                        if (certificationMap.putIfAbsent(cert, 1) != null) {
                            certificationMap.put(cert, certificationMap.get(cert) + 1);
                        }
                    }
                }

                if (candidate.getObtainedCertification() != null && !candidate.getObtainedCertification().isEmpty()) {
                    for (String cert : candidate.getObtainedCertification().split(",")) {
                        if (certificationMap.putIfAbsent(cert, 1) != null) {
                            certificationMap.put(cert, certificationMap.get(cert) + 1);
                        }
                    }
                }

                if (candidate.getField() != null && !candidate.getField().isEmpty()) {
                    for (String field : candidate.getField().split(",")) {
                        if (fieldMap.putIfAbsent(field, 1) != null) {
                            fieldMap.put(field, fieldMap.get(field) + 1);
                        }
                    }
                }

                if (candidate.getPastField() != null && !candidate.getPastField().isEmpty()) {
                    for (String field : candidate.getPastField().split(",")) {
                        if (fieldMap.putIfAbsent(field, 1) != null) {
                            fieldMap.put(field, fieldMap.get(field) + 1);
                        }
                    }
                }
            }
            session.setAttribute("fieldMap", fieldMap);
            session.setAttribute("certificationMap", certificationMap);
        } catch (Exception e) {
            e.printStackTrace();
        }
        response.sendRedirect("admin/admin-candidates-criteria.jsp");
    }
}
