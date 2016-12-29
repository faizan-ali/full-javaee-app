package co.workamerica.functionality.candidates.misc;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.sendgrid.API.SendGridObject;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import co.workamerica.functionality.twilio.API.Twilio;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Random;

/**
 * Servlet implementation class ForgotPasswordServlet
 */
@WebServlet(name = "ForgotPasswordServlet", urlPatterns = {"/forgot-servlet"})
public class ForgotPasswordServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String username = request.getParameter("email") != null ? request.getParameter("email").trim() : "";
        Candidates candidate = new Candidates();

        Random random = new Random();
        String password = "succeed" + random.nextInt(10) + random.nextInt(10) + random.nextInt(10);
        boolean sent = false;

        try {
            if ((candidate = CandidatePersistence.getCandidateByEmail(username)) != null) {
                sent = SendGridObject.sendEmail(username, password, candidate.getFirstName(), "Forgot");
            } else if ((candidate = CandidatePersistence.getCandidateByPhone(username)) != null) {
                username = CustomUtilities.cleanNumber(username);
                sent = true;
                Twilio.sendText(username, username, password, candidate.getFirstName(), "Forgot");
            } else {
                response.sendRedirect("http://www.workamerica.co/forgot-password.html?invalidEmail=true");
            }

            if (sent) {
                CandidatePersistence.updatePassword(candidate, password);
                response.sendRedirect("http://www.workamerica.co/forgot-password.html?success=true");
            } else {
                response.sendRedirect("http://www.workamerica.co/forgot-password.html?error=true");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
