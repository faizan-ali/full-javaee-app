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

/**
 * Servlet implementation class DeleteFromPipelineServlet
 */
@WebServlet("/DeleteFromPipelineServlet")
public class DeleteFromPipelineServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Creates EntityManager to query database
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		// Transactions are employed to roll back changes in case of error and
		// to ensure data integrity
		EntityTransaction trans = em.getTransaction();

		// Creates a session object and retrieves entities
		HttpSession session = request.getSession();

		// Retrieves clientID of client who is deleting from pipeline
		int clientID = (int) session.getAttribute("clientID");

		// Retrieves candidateID of candidate to be deleted
		int candidateID = Integer.parseInt(request.getParameter("candidateID"));
		Clients user = em.find(Clients.class, clientID);
		Candidates candidate = em.find(Candidates.class, candidateID);

		ClientLoginLogs loginLog = user.getLastLoginLog();

		/* For logging pipeline changes */
		PipelineChangeLogs changeLog = new PipelineChangeLogs();
		changeLog.setClientLoginLog(loginLog);
		changeLog.setClientLoginLogsID(loginLog.getClientLoginLogsID());
		changeLog.setCandidateID(candidateID);
		changeLog.setPipelineAction("Delete");
		changeLog.setPipelineChange(
				"Removed " + candidate.getFirstName() + " " + candidate.getLastName() + " from pipeline.");
		changeLog.setTime(Clock.getCurrentTime());

		try {
			trans.begin();
			ClientPipelines elt = user.getClientPipelineByCandidateID(candidateID);
			if (elt == null) {
				System.out.println("Candidate does not exist?");
				response.sendRedirect("pipeline.jsp");
			}
			user.removeFromClientPipeline(elt);
			candidate.removeFromClientPipeline(elt);
			em.persist(changeLog);
			em.remove(elt);
			em.merge(candidate);
			em.merge(user);
			trans.commit();

			if (user != null) {
				session.setAttribute("user", user);
			}
			response.sendRedirect("pipeline.jsp");
		} catch (Exception e) {
			logger.error("Pipeline deletion bug", e);
			e.printStackTrace();
			trans.rollback();
		} finally {
			em.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
