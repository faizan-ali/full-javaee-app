package co.workamerica.functionality.schools;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.logs.schools.SchoolActivityLogs;
import co.workamerica.entities.logs.schools.SchoolLoginLogs;
import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.SchoolAccountDataAccessObject;
import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Servlet implementation class SchoolCandidateUpdateServlet
 */
@WebServlet("/SchoolCandidateUpdateServlet")
public class SchoolCandidateUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public SchoolCandidateUpdateServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Candidates candidate = CandidatePersistence.getCandidateByID((Integer) session.getAttribute("candidateID"));
		
		SchoolAccounts account = (SchoolAccounts) session.getAttribute("user");
		
		int schoolID = account.getSchoolID();

		CustomUtilities custom = new CustomUtilities();
		
		HashMap<String, String> candidateDetails = new HashMap<String, String>();
		candidateDetails.put("firstName", custom.capitalizeFirstLetter(request.getParameter("firstName")));
		candidateDetails.put("lastName", custom.capitalizeFirstLetter(request.getParameter("lastName")));
		candidateDetails.put("city", custom.capitalizeFirstLetter(request.getParameter("city")));
		candidateDetails.put("state", request.getParameter("state"));
		candidateDetails.put("phone", request.getParameter("phone").replaceAll("[^0-9]+", "").trim());
		candidateDetails.put("alternatePhone", request.getParameter("alternatePhone").replaceAll("[^0-9]+", "").trim());
		candidateDetails.put("school", request.getParameter("school"));
		candidateDetails.put("completionDate", request.getParameter("completionDate"));
		candidateDetails.put("workExperience", request.getParameter("workExperience"));
		candidateDetails.put("veteran", request.getParameter("veteran"));
		candidateDetails.put("employed", request.getParameter("employed"));
		candidateDetails.put("relocate", request.getParameter("relocate"));
		candidateDetails.put("additionalInformation", request.getParameter("additionalInformation"));
		candidateDetails.put("pastEducation", request.getParameter("pastEducation"));
		candidateDetails.put("validDriversLicense", request.getParameter("validDriversLicense"));
		candidateDetails.put("zip", request.getParameter("zip"));
		candidateDetails.put("fields", request.getParameter("field"));
		candidateDetails.put("anticipatedCertifications", request.getParameter("certification"));
		candidateDetails.put("pastFields", request.getParameter("pastField"));
		candidateDetails.put("obtainedCertifications", request.getParameter("obtainedCertification"));
		candidateDetails.put("schoolID", schoolID + "");
		candidateDetails.put("password", request.getParameter("password"));
		
		try {
			candidate = CandidatePersistence.updateCandidate(candidateDetails, candidate);
			List<Candidates> listCandidates = SchoolAccountDataAccessObject.getCandidates(account);	
			session.setAttribute("listCandidates", listCandidates);
			session.setAttribute("resume", "false");
			session.setAttribute("candidate", candidate);
			
			// For logging activity
			SchoolLoginLogs loginLog = (SchoolLoginLogs) session.getAttribute("loginLog");
			SchoolActivityLogs activityLog = new SchoolActivityLogs();
			activityLog.setSchoolLoginLogID(loginLog.getSchoolLoginLogID());
			activityLog.setTime(Clock.getCurrentTime());
			activityLog.setCandidateID(candidate.getCandidateID());
			activityLog.setCandidate(candidate);
			activityLog.setSchoolActivity("Updated Profile");
			SchoolAccountDataAccessObject.persistActivityLog(activityLog);
			
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("adminError", "An error occured. Profile not created.");
		} finally {
			response.sendRedirect("schools/school-candidate-profile.jsp");
		}
	}

}
