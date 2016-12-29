package co.workamerica.functionality.candidates.misc;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.criteria.Certifications;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.criteria.States;
import co.workamerica.functionality.candidates.jobs.RecommendedJobsRetriever;
import co.workamerica.functionality.persistence.*;
import co.workamerica.jobs.jsonModels.Global.GlobalJobResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class CandidateLoginServlet
 */
@WebServlet("/CandidateLoginServlet")
public class CandidateLoginServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        String username = request.getParameter("email").toLowerCase().trim();
        String password = request.getParameter("password");
        HttpSession session = request.getSession();

        if (!username.contains("@") && username.replaceAll("[^0-9]+", "").trim().length() != 10) {
            session.setAttribute("error", "Invalid email or phone number");
            response.sendRedirect("candidate-login.jsp");
        } else if (password.isEmpty()) {
            session.setAttribute("error", "Please enter a password");
            response.sendRedirect("candidate-login.jsp");
        } else {

            try {
                Candidates user = CandidatePersistence.login(username, password);
                if (user != null) {
                    List<States> stateList = StateDataAccessObject.getAll();
                    List<Fields> fieldList = FieldPersistence.getAll();
                    List<Schools> schoolList = SchoolDataAccessObject.getAll();
                    List<Certifications> certificationList = CertificationPersistence.getAll();

                    // Pulls jobs for recommended jobs feed
                    GlobalJobResponseObject jobResponse = RecommendedJobsRetriever.getRecommendedJobs(user);
                    session.setAttribute("jobResponse", jobResponse);


                    session.setAttribute("loginLog", user.getLoginLog());
                    session.setAttribute("resume", "false");
                    session.setAttribute("fieldList", fieldList);
                    session.setAttribute("stateList", stateList);
                    session.setAttribute("schoolList", schoolList);
                    session.setAttribute("certificationList", certificationList);
                    session.setAttribute("user", user);
                    session.setAttribute("currentSchool", user.getSchool());
                    session.setAttribute("candidateID", user.getCandidateID());

                    response.sendRedirect("candidate-landing.jsp");
                } else {
                    System.out.println("Incorrect password");
                    session.setAttribute("error", "Invalid login details");
                    response.sendRedirect("candidate-login.jsp");
                }
            } catch (NoResultException e) {
                e.printStackTrace();
                logger.error("No user for Candidate " + username + " login", e);
                session.setAttribute("error", "Invalid login details");
                response.sendRedirect("candidate-login.jsp");
            } catch (NonUniqueResultException e) {
                logger.error("Multiple users for Candidate " + username, e);
                session.setAttribute("error",
                        "Your account is currently experiencing technical difficulties. Please try again soon. Code: Mult1");
                response.sendRedirect("candidate-login.jsp");
                e.printStackTrace();
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Candidate login error for " + username, e);
                session.setAttribute("error",
                        "Your account is currently experiencing technical difficulties. Please try again soon. ");
                response.sendRedirect("candidate-login.jsp");
            }
        }
    }
}
