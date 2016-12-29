package co.workamerica.functionality.candidates.misc;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class CandidateAccountServlet
 */
@WebServlet("/CandidateAccountServlet")
public class CandidateAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String email = request.getParameter("email").toLowerCase().trim(), password = request.getParameter("password"),
                authorized = request.getParameter("authorized");

        HttpSession session = request.getSession();

        if (email.isEmpty() || password.isEmpty()) {
            session.setAttribute("error", "Please enter a valid e-mail and password.");
            response.sendRedirect("candidate-account.jsp");
            return;
        } else if (password.length() < 4) {
            session.setAttribute("error", "Password must be greater than 3 characters/");
            response.sendRedirect("candidate-account.jsp");
            return;
        }

        if (session.getAttribute("user") != null) {
            Candidates candidate = (Candidates) session.getAttribute("user");

            try {
                int candidateID = candidate.getCandidateID();
                candidate = CandidatePersistence.getCandidateByID(candidateID);
                CustomUtilities custom = new CustomUtilities();
                String[] passwordArray = custom.hashPassword(password, null);
                candidate.setPassword(passwordArray[0]);
                candidate.setSalt(passwordArray[1]);
                candidate.setEmail(email);
                candidate.setAuthorized(authorized);
                CandidatePersistence.merge(candidate);
                session.setAttribute("user", candidate);
                session.setAttribute("error", "Details updated");
                response.sendRedirect("candidate-account.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "Your account is currently experiencing technical difficulties, please try again soon.");
                response.sendRedirect("candidate-account.jsp");
                return;
            }
        } else {
            session.setAttribute("error", "Your account is currently experiencing technical difficulties, please try again soon.");
            response.sendRedirect("candidate-account.jsp");
        }
    }
}
