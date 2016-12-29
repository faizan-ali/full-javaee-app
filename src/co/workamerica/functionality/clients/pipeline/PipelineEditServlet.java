package co.workamerica.functionality.clients.pipeline;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.ClientPipelines;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.logs.clients.ClientLoginLogs;
import co.workamerica.entities.logs.clients.PipelineChangeLogs;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class PipelineEditServlet
 */
@WebServlet("/PipelineEditServlet")
public class PipelineEditServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Creates EntityManager to query database
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		// Transactions are employed to roll back changes in case of error and
		// ensure data integrity
		EntityTransaction trans = em.getTransaction();

		// Creates a session object and retrieves entities
		HttpSession session = request.getSession();

		// Retrieves clientID of client
		int clientID = (int) session.getAttribute("clientID");

		// Retrieves candidateID and pipelineID of pipeline candidate to be
		// updated
		int candidateID = Integer.parseInt(request.getParameter("candidateID"));
		int pipelineID = Integer.parseInt(request.getParameter("pipelineID"));
		Clients user = em.find(Clients.class, clientID);
		Candidates candidate = em.find(Candidates.class, candidateID);
		ClientPipelines elt = em.find(ClientPipelines.class, pipelineID);

		String newRating = request.getParameter("rating"), newStatus = request.getParameter("status");

		if (elt.getStatus().equals(newStatus) && elt.getRating().equals(newRating)) {
			response.sendRedirect("candidate-profile.jsp");
		} else {

			ClientLoginLogs loginLog = user.getLastLoginLog();

			/* For logging pipeline changes */
			String pipelineChange = "Updated " + candidate.getFirstName() + " " + candidate.getLastName() + ". ";
			PipelineChangeLogs changeLog = new PipelineChangeLogs();
			changeLog.setClientLoginLog(loginLog);
			changeLog.setClientLoginLogsID(loginLog.getClientLoginLogsID());
			changeLog.setCandidateID(candidateID);
			changeLog.setTime(Clock.getCurrentTime());
			changeLog.setPipelineAction("Update");

			if (!elt.getRating().equals(newRating)) {
				pipelineChange += "Updated rating from " + elt.getRating() + " to " + newRating;
				user.getClientPipelineByCandidateID(candidateID).setRating(request.getParameter("rating"));
				elt.setRating(newRating);
			}

			if (!elt.getStatus().equals(newStatus)) {
				if (pipelineChange.contains("rating")) {
					pipelineChange += " and ";
				}
				pipelineChange += "Updated status from " + elt.getStatus() + " to " + newStatus;
				elt.setStatus(newStatus);
				user.getClientPipelineByCandidateID(candidateID).setStatus(request.getParameter("status"));

				// To update pipeline status of candidate in results without a
				// new search
				elt.getCandidate().setPipelineStatus(newStatus);
				@SuppressWarnings("unchecked")
				List<Candidates> candidateList = (List<Candidates>) session
						.getAttribute("listCandidates");
				if (candidateList != null) {
					for (Candidates candidateElt : candidateList) {
						if (candidateElt.getCandidateID() == candidateID) {
							candidateElt.setPipelineStatus(newStatus);
							break;
						}
					}
				}
			}

			pipelineChange += ".";
			changeLog.setPipelineChange(pipelineChange);

			try {
				trans.begin();
				em.persist(changeLog);
				em.merge(elt);
				user = em.merge(user);
				trans.commit();

				if (user != null) {
					session.setAttribute("user", user);
				}
				session.setAttribute("candidatePipelineEntity", elt);
				response.sendRedirect("candidate-profile.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("Pipeline edit bug", e);
				trans.rollback();
				response.sendRedirect("candidate-profile.jsp");
			} finally {
				em.close();
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
