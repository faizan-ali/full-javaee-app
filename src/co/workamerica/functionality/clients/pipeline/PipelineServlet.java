package co.workamerica.functionality.clients.pipeline;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.ClientPipelines;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.logs.clients.ClientLoginLogs;
import co.workamerica.entities.logs.clients.PipelineChangeLogs;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.ClientPersistence;
import co.workamerica.functionality.persistence.ClientPipelineDataAccessObject;
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
import java.util.List;

/**
 * Servlet implementation class PipelineServlet
 */
@WebServlet("/PipelineServlet")
public class PipelineServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        int candidateID = Integer.parseInt(request.getParameter("candidateID"));
        int clientID = (Integer) session.getAttribute("clientID");
        String status = request.getParameter("status"), rating = request.getParameter("rating");

        Clients user = ClientPersistence.getByID(clientID);
        Candidates candidate = CandidatePersistence.getCandidateByID(candidateID);

        // Boolean ensures duplicate entities are not added to pipeline
        boolean exists = false;

        ClientPipelines newElt = new ClientPipelines(user.getClientID(), candidateID, rating, status,
               Clock.getCurrentDate());
        ClientLoginLogs loginLog = user.getLastLoginLog();
        exists = user.existsInPipelineByCandidateID(candidateID);

        if (!exists) {
            try {
                if (loginLog != null) {
                    PipelineChangeLogs changeLog = new PipelineChangeLogs();
                    changeLog.setClientLoginLog(loginLog);
                    changeLog.setClientLoginLogsID(loginLog.getClientLoginLogsID());
                    changeLog.setCandidateID(candidateID);
                    changeLog.setPipelineAction("Add");
                    changeLog.setPipelineChange("Added " + candidate.getFirstName() + " " + candidate.getLastName() + " to pipeline with Status: " + status +
                            " and Rating: " + rating);
                    changeLog.setTime(Clock.getCurrentTime());
                    ClientPersistence.addPipelineChangeLog(changeLog);
                }
                newElt = ClientPipelineDataAccessObject.persist(newElt);
                candidate = CandidatePersistence.getCandidateByID(candidateID);
                user = ClientPersistence.getByID(clientID);

                user.addToClientPipeline(newElt);
                candidate.addToClientPipeline(newElt);
                newElt.getCandidate().setPipelineStatus(status);

                List<Candidates> candidateList = CandidatePersistence.getAll();

                session.setAttribute("listCandidates", candidateList);
                session.setAttribute("existsInPipeline", "true");
                session.setAttribute("candidatePipelineEntity", newElt);
                session.setAttribute("candidate", candidate);
                session.setAttribute("user", user);
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Pipeline add bug", e);
            }
        }
        response.sendRedirect("candidate-profile.jsp");
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}