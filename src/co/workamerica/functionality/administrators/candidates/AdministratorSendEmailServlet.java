package co.workamerica.functionality.administrators.candidates;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.sendgrid.API.SendGridObject;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Faizan on 6/21/2016.
 */
@WebServlet("/AdministratorSendEmailServlet")
public class AdministratorSendEmailServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        if (request.getParameter("candidateID") != null) {
            int candidateID = Integer.parseInt(request.getParameter("candidateID"));
            Candidates candidate = CandidatePersistence.getCandidateByID(candidateID);
            String password = "";

            if (candidate != null) {

                if (candidate.getFirstName() != null && !candidate.getFirstName().isEmpty()) {
                    password = candidate.getFirstName();
                } else if (candidate.getLastName() != null && !candidate.getLastName().isEmpty()) {
                    password = candidate.getLastName();
                } else {
                    password = "Stark";
                }
                if (candidate.getPhone() == null || candidate.getPhone().isEmpty()) {
                    password += "5555";
                } else {
                    password += candidate.getPhone().substring(6, 10);
                }

                String[] passwordArray = CustomUtilities.hashPassword(password, null);
                candidate.setPassword(passwordArray[0]);
                candidate.setSalt(passwordArray[1]);

                try {
                    CandidatePersistence.merge(candidate);
                    SendGridObject.sendEmail(candidate.getEmail(), password, candidate.getFirstName(), "Welcome");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        response.sendRedirect("admin/admin-candidate-list.jsp");
    }
}
