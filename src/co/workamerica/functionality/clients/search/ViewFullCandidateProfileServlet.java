package co.workamerica.functionality.clients.search;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.ClientPipelines;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.clients.ProfileViews;
import co.workamerica.entities.companies.Companies;
import co.workamerica.entities.logs.clients.ClientLoginLogs;
import co.workamerica.entities.logs.clients.ProfileViewLogs;
import co.workamerica.functionality.persistence.*;
import co.workamerica.functionality.shared.utilities.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;

/**
 * Servlet implementation class ViewFullCandidateProfileServlet
 */
@WebServlet("/ViewFullCandidateProfileServlet")
public class ViewFullCandidateProfileServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();

        Clients client = (Clients) session.getAttribute("user");
        ClientLoginLogs loginLog = client.getLastLoginLog();
        Candidates candidate = null;
        ClientPipelines elt = null;
        Companies company = new Companies();

        int candidateID = 0;
        if (session.getAttribute("candidateID") != null) {
            candidateID = Integer.parseInt(request.getParameter("candidateID"));
        } else if (request.getParameter("candidateID") != null && !request.getParameter("candidateID").isEmpty()) {
            candidateID = Integer.parseInt(request.getParameter("candidateID"));
        } else {
            response.sendRedirect("results.jsp");
        }

        try {
            candidate = CandidatePersistence.getCandidateByID(candidateID);
        } catch (Exception e) {
            logger.error("Candidate profile view bug", e);
            e.printStackTrace();
            response.sendRedirect("results.jsp");
        }

        if (candidate == null || client == null) {
            response.sendRedirect("results.jsp");
        } else {
            company = client.getCompany();

            // Profile views limit check
            if (company != null && company.getViewLimit() != 0 && company.getProfilesViewedThisMonth() >=
                    company.getViewLimit() && !client.profileViewsExists(candidateID)) {
                response.sendRedirect("view-limit.jsp");
            } else if (client.getViewLimit() != 0 && client.getProfilesViewedThisMonth() >= client.getViewLimit()
                    && !client.profileViewsExists(candidateID)) {
                response.sendRedirect("view-limit.jsp");
            }
            // School limit check
            else if (company != null && company.getSchoolAccount() != null
                    && candidate.getSchoolID() != company.getSchoolAccount().getSchoolID()) {
                response.sendRedirect("school-limit.jsp");
            }

            // Approval check
            else if (client.getApproved() != null && client.getApproved().equals("No")) {
                response.sendRedirect("unapproved.jsp");
            }
            else {

                // Increments profile views on Client
                if (!client.profileViewsExists(candidateID)) {
                    client.setProfilesViewed(client.getProfilesViewed() + 1);
                    client.setProfilesViewedThisMonth(client.getProfilesViewedThisMonth() + 1);

                    if (company != null) {
                        company.setProfilesViewed(client.getCompany().getProfilesViewed() + 1);
                        company.setProfilesViewedThisMonth(client.getCompany().getProfilesViewedThisMonth() + 1);
                        company = CompanyDataAccessObject.merge(company);
                    }
                }

                try {

                    // For profile viewing logs
                    ProfileViewLogs viewLog = ProfileViewLogDataAccessObject.create(loginLog.getClientLoginLogsID(), candidateID, Clock.getCurrentTime());

                    // For Profile Views table
                    if (!client.profileViewsExists(candidateID)) {
                        ProfileViews views = ProfileViewDataAccessObject.create(client.getClientID(), candidateID);
                        client.addToProfileViews(views);
                    }

                    ArrayList<Integer> viewList = new ArrayList<Integer>();
                    for (ProfileViews view : client.getProfileViews()) {
                        viewList.add(view.getCandidateID());
                    }
                    if (viewList.size() > 0) {
                        session.setAttribute("viewList", viewList);
                    }

                    // Checks if candidate already exists in client pipeline to display
                    // alternate profile page
                    if (client.getClientPipelineByCandidateID(candidateID) != null) {
                        session.setAttribute("existsInPipeline", "true");
                    } else {
                        session.setAttribute("existsInPipeline", "false");
                    }
                    elt = ClientPipelineDataAccessObject.getByClientAndCandidateID(client.getClientID(), candidateID);
                    client = ClientPersistence.merge(client);

                    if (company != null) {
                        client.setCompany(company);
                    }
                    response.sendRedirect("candidate-profile.jsp");
                } catch (Exception e) {
                    logger.error("Candidate profile view bug", e);
                    e.printStackTrace();
                    response.sendRedirect("results.jsp");
                }

                session.setAttribute("user", client);
                session.setAttribute("resume", "false");
                session.setAttribute("candidatePipelineEntity", elt);
                session.setAttribute("candidate", candidate);
                session.setAttribute("candidateID", candidateID);

                // This session object determines what page to go back to from
                // candidate-profile.jsp
                if (request.getParameter("pipelineJSP") != null && request.getParameter("pipelineJSP").equals("true")) {
                    session.setAttribute("backToPipeline", "true");
                    session.removeAttribute("backToResults");
                } else {
                    session.setAttribute("backToResults", "true");
                    session.removeAttribute("backToPipeline");
                }
            }
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
