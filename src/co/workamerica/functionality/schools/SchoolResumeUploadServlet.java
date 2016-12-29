package co.workamerica.functionality.schools;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.logs.schools.SchoolActivityLogs;
import co.workamerica.entities.logs.schools.SchoolLoginLogs;
import co.workamerica.functionality.persistence.SchoolAccountDataAccessObject;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

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
 * Servlet implementation class ResumeServlet
 */
@WebServlet("/SchoolResumeUploadServlet")
public class SchoolResumeUploadServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

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

		// Creates a session object and retrieves entities
		HttpSession session = request.getSession();
		int candidateID = (int) session.getAttribute("candidateID");
		Candidates candidate = em.find(Candidates.class, candidateID);
		String source = request.getParameter("source");
		
		System.out.println(source);

		ServletFileUpload upload = new ServletFileUpload();

		try {
			FileItemIterator iterator = upload.getItemIterator(request);

			while (iterator.hasNext()) {
				FileItemStream item = iterator.next();
				if (item.getName() != null) {
					if (item.getName().contains(".pdf") || item.getName().contains(".doc")) {
						byte[] file = IOUtils.toByteArray(item.openStream());
						candidate.setResume(file);

                        trans.begin();
                        em.merge(candidate);
                        trans.commit();

						// For logging activity
						SchoolLoginLogs loginLog = (SchoolLoginLogs) session.getAttribute("loginLog");
						if (loginLog != null) {
							SchoolActivityLogs activityLog = new SchoolActivityLogs();
							activityLog.setSchoolLoginLogID(loginLog.getSchoolLoginLogID());
							activityLog.setTime(Clock.getCurrentTime());
							activityLog.setCandidateID(candidateID);
							activityLog.setCandidate(candidate);
							activityLog.setSchoolActivity("Uploaded Resume");
							SchoolAccountDataAccessObject.persistActivityLog(activityLog);
						}
		
						session.setAttribute("resumeSuccess", "Resume uploaded!");
						session.setAttribute("resume", "true");
						session.setAttribute("candidate", candidate);
						session.setAttribute("loginLog", loginLog);
					} else {
						String resumeError = "Please upload a resume in PDF or DOC/DOCX format.";
						session.setAttribute("resume", "true");
						session.setAttribute("resumeError", resumeError);
					}
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error uploading resume");
		} finally {
			em.close();
			response.sendRedirect("schools/school-candidate-profile.jsp");
		}
	}
}
