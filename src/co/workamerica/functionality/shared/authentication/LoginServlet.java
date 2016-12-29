package co.workamerica.functionality.shared.authentication;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.clients.ProfileViews;
import co.workamerica.functionality.candidates.jobs.RecommendedJobsRetriever;
import co.workamerica.functionality.persistence.*;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import co.workamerica.jobs.jsonModels.Global.GlobalJobResponseObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by Faizan on 7/15/2016.
 */
@WebServlet(name = "LoginServlet", urlPatterns = {"/login"})
public class LoginServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String username = request.getParameter("email") != null ? request.getParameter("email").toLowerCase().trim() : "";
        String password = request.getParameter("password") != null ? request.getParameter("password") : "";

        Clients client;
        Candidates candidate;

        if ((username.contains("@") || CustomUtilities.isValidNumber(username)) && password.length() > 3) {

            try {
                session.setAttribute("fieldList", FieldPersistence.getAll());
                session.setAttribute("stateList", StateDataAccessObject.getAll());
                session.setAttribute("schoolList", SchoolDataAccessObject.getAll());
                session.setAttribute("certificationList", CertificationPersistence.getAll());

                if ((client = ClientPersistence.login(username, password)) != null) {

                    // Allows previously viewed profiles to be highlighted in
                    // search results
                    ArrayList<Integer> viewList = new ArrayList<Integer>();
                    for (ProfileViews view : client.getProfileViews()) {
                        viewList.add(view.getCandidateID());
                    }

                    session.setAttribute("user", client);
                    session.setAttribute("clientID", client.getClientID());
                    session.setAttribute("loginLog", client.getLoginLog());
                    session.setAttribute("viewList", viewList);
                    response.sendRedirect("landing.jsp");
                } else if ((candidate = CandidatePersistence.login(username, password)) != null) {
                    // Pulls jobs for recommended jobs feed
                    GlobalJobResponseObject jobResponse = RecommendedJobsRetriever.getRecommendedJobs(candidate);

                    session.setAttribute("user", candidate);
                    session.setAttribute("loginLog", candidate.getLoginLog());
                    session.setAttribute("resume", "false");
                    session.setAttribute("candidateID", candidate.getCandidateID());
                    session.setAttribute("currentSchool", candidate.getSchool());
                    session.setAttribute("jobResponse", jobResponse);
                    response.sendRedirect("candidate-landing.jsp");
                } else {
                    session.setAttribute("error", "Invalid login details");
                    response.sendRedirect("http://www.workamerica.co/login.html?invalid=true");
                }
            } catch (Exception e) {
                e.printStackTrace();
                session.setAttribute("error", "An error occurred");
                response.sendRedirect("http://www.workamerica.co/login.html?error=true");
            }
        } else {
            session.setAttribute("error", "Invalid login details");
            response.sendRedirect("http://www.workamerica.co/login.html?invalid=true");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
