package co.workamerica.functionality.clients.pipeline;

import co.workamerica.entities.clients.ClientPipelines;
import co.workamerica.entities.clients.Clients;
import co.workamerica.functionality.shared.EMFUtil;
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
 * Servlet implementation class NotesServlet
 */
@WebServlet("/NotesServlet")
public class NotesServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Creates EntityManager to query database
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		// Transactions are employed to roll back changes in case of error and
		// ensure data integrity
		EntityTransaction trans = em.getTransaction();

		// Accesses the session object and retrieves entities
		HttpSession session = request.getSession();

		// Retrieves clientID of client
		int clientID = (int) session.getAttribute("clientID");

		int candidateID = Integer.parseInt(request.getParameter("candidateID"));
		int pipelineID = Integer.parseInt(request.getParameter("pipelineID"));
		Clients user = em.find(Clients.class, clientID);
		ClientPipelines elt = em.find(ClientPipelines.class, pipelineID);
		String notes = request.getParameter("notes");

		elt.setNotes(notes);
		user.getClientPipelineByCandidateID(candidateID).setNotes(notes);

		try {
			trans.begin();
			em.merge(user);
			trans.commit();

			if (user != null) {
				session.setAttribute("user", user);
			}
			session.setAttribute("candidatePipelineEntity", elt);
			response.sendRedirect("candidate-profile.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("Notes update bug", e);
			trans.rollback();
            response.sendRedirect("candidate-profile.jsp");
		} finally {
			em.close();
		}

	}

}
