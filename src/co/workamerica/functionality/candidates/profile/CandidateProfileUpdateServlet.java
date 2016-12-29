package co.workamerica.functionality.candidates.profile;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.logs.candidates.CandidateActivityLogs;
import co.workamerica.entities.logs.candidates.CandidateLoginLogs;
import co.workamerica.functionality.candidates.jobs.RecommendedJobsRetriever;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.CertificationPersistence;
import co.workamerica.functionality.persistence.FieldPersistence;
import co.workamerica.functionality.persistence.SchoolDataAccessObject;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import co.workamerica.jobs.jsonModels.Global.GlobalJobResponseObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;

@WebServlet("/CandidateProfileUpdateServlet")
public class CandidateProfileUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		Candidates user = CandidatePersistence.getCandidateByID((Integer) session.getAttribute("candidateID"));

		int schoolID = SchoolDataAccessObject.getSchoolIDByName(request.getParameter("school"));

		String email = request.getParameter("email").length() > 5 ? request.getParameter("email").toLowerCase().trim() : user.getEmail();
		String anticipatedCertifications = request.getParameter("certification").contains("12") ? "Other" : request.getParameter("certification");
		String fields = request.getParameter("field").contains("12") ? "Other" : request.getParameter("field");
		String pastFields = request.getParameter("pastField").contains("12") ? "Other" : request.getParameter("pastField");
		String obtainedCertifications = request.getParameter("obtainedCertification").contains("12") ? "Other" : request.getParameter("obtainedCertification");

		HashMap<String, String> candidateDetails = new HashMap<String, String>();
		candidateDetails.put("firstName", CustomUtilities.capitalizeFirstLetter(request.getParameter("firstName")));
		candidateDetails.put("lastName", CustomUtilities.capitalizeFirstLetter(request.getParameter("lastName")));
		candidateDetails.put("city", CustomUtilities.capitalizeFirstLetter(request.getParameter("city")));
		candidateDetails.put("state", request.getParameter("state"));
		candidateDetails.put("email", email);
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
		candidateDetails.put("fields", fields);
		candidateDetails.put("anticipatedCertifications", anticipatedCertifications);
		candidateDetails.put("pastFields", pastFields);
		candidateDetails.put("obtainedCertifications", obtainedCertifications);
		candidateDetails.put("schoolID", schoolID + "");

		try {
			CandidateLoginLogs loginLog = user.getLastLoginLog();
			if (loginLog != null) {
				CandidateActivityLogs activityLog = CandidatePersistence.buildActivityLog(request, user, loginLog);
				user = CandidatePersistence.updateCandidate(candidateDetails, user);
				user.updateCertificationsByListAndDate(CertificationPersistence.getIDListByCommaSeparatedNames(anticipatedCertifications), request.getParameter("completionDate").trim());
				user.updateCertificationsByListAndDate(CertificationPersistence.getIDListByCommaSeparatedNames(obtainedCertifications), "");
				user.updateFieldsByList(FieldPersistence.getIDListByCommaSeparatedNames(request.getParameter("field")), "Yes");
				user.updateFieldsByList(FieldPersistence.getIDListByCommaSeparatedNames(request.getParameter("pastField")), "No");
				user = CandidatePersistence.merge(user);
				activityLog = CandidatePersistence.addActivityLog(activityLog);
			}

			// Update recommended jobs
			GlobalJobResponseObject jobResponse = RecommendedJobsRetriever.getRecommendedJobs(user);

			session.setAttribute("jobResponse", jobResponse);

			session.setAttribute("resume", "false");
			session.setAttribute("user", user);
		} catch (Exception e) {
            logger.error("Candidate profile update bug", e);
			e.printStackTrace();
		} finally {
			response.sendRedirect("candidate-landing.jsp");
		}
	}

}
