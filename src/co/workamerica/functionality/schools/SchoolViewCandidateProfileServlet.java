package co.workamerica.functionality.schools;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.logs.schools.SchoolActivityLogs;
import co.workamerica.entities.logs.schools.SchoolLoginLogs;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@WebServlet("/SchoolViewCandidateProfileServlet")
public class SchoolViewCandidateProfileServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SchoolViewCandidateProfileServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		Candidates candidate = new Candidates();

		// Retrieves candidateID of candidate who's profile needs to be
		// displayed
		int candidateID = Integer.parseInt(request.getParameter("candidateID"));

		// Creates EntityManager to query database
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();

		// Retrieves user from database based on userID
		candidate = em.find(Candidates.class, candidateID);

		session.setAttribute("candidate", candidate);
		session.setAttribute("candidateID", candidateID);

		// For logging activity
		SchoolLoginLogs loginLog = (SchoolLoginLogs) session.getAttribute("loginLog");
		SchoolActivityLogs activityLog = new SchoolActivityLogs();
		activityLog.setSchoolLoginLogID(loginLog.getSchoolLoginLogID());
		activityLog.setLoginLog(loginLog);
		activityLog.setTime(Clock.getCurrentTime());
		activityLog.setCandidateID(candidateID);
		activityLog.setCandidate(candidate);
		activityLog.setSchoolActivity("Viewed Profile");

		try {
			trans.begin();
			em.persist(activityLog);
			trans.commit();
			session.setAttribute("loginLog", loginLog);
		} catch (Exception e) {
			trans.rollback();
			e.printStackTrace();
		} finally {
			em.close();
			response.sendRedirect("schools/school-candidate-profile.jsp");
		}
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
