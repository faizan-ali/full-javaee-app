package co.workamerica.functionality.schools;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.logs.schools.SchoolActivityLogs;
import co.workamerica.entities.logs.schools.SchoolLoginLogs;
import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.SchoolAccountDataAccessObject;
import co.workamerica.functionality.sendgrid.API.SendGridObject;
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
 * Servlet implementation class SchoolCreateCandidateServlet
 */
@WebServlet("/SchoolCreateCandidateServlet")
public class SchoolCreateCandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		SchoolAccounts account = (SchoolAccounts) session.getAttribute("user");

		String firstName = request.getParameter("firstName").trim(), lastName = request.getParameter("lastName").trim(),
				school = account.getSchool().getName(), email = request.getParameter("email").trim(),
				phone = request.getParameter("phone").replaceAll("[^0-9]+", "").trim(),
				password = request.getParameter("password");

		if (email.isEmpty()) {
			session.setAttribute("adminError", "Please enter an e-mail address");
			response.sendRedirect("schools/admin-new-profile.jsp");
		} else if (CandidatePersistence.checkIfExistsByEmail(email)) {
			session.setAttribute("adminError", "A profile with this e-mail already exists");
			response.sendRedirect("schools/school-new-profile.jsp");
		} else {

			int schoolID = account.getSchoolID();

			HashMap<String, String> candidateDetails = new HashMap<String, String>();
			candidateDetails.put("firstName", firstName);
			candidateDetails.put("lastName", lastName);
			candidateDetails.put("city", CustomUtilities.capitalizeFirstLetter(request.getParameter("city")));
			candidateDetails.put("state", request.getParameter("state"));
			candidateDetails.put("phone", phone);
			candidateDetails.put("alternatePhone",
					request.getParameter("alternatePhone").replaceAll("[^0-9]+", "").trim());
			candidateDetails.put("school", school);
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
            candidateDetails.put("school", account.getSchool().getName());
			candidateDetails.put("authorized", "Yes");
			candidateDetails.put("approved", "No");
			candidateDetails.put("workAmericaCreated", "No");
            candidateDetails.put("email", request.getParameter("email").toLowerCase().trim());

			try {
				Candidates newCandidate = new Candidates();
				
				try {
					newCandidate = CandidatePersistence.createCandidate(candidateDetails);
					session.setAttribute("adminSuccess", "Profile added!");
					List<Candidates> listCandidates = SchoolAccountDataAccessObject
							.getCandidates(account);
					session.setAttribute("listCandidates", listCandidates);
					session.setAttribute("resume", "false");
				} catch (Exception e) {
					e.printStackTrace();
					session.setAttribute("adminError", "An error occurred. Profile not created.");
					response.sendRedirect("schools/school-new-profile.jsp");
				}

				// For logging activity
				SchoolLoginLogs loginLog = (SchoolLoginLogs) session.getAttribute("loginLog");
				SchoolActivityLogs activityLog = new SchoolActivityLogs();
				if (loginLog != null) {
					activityLog.setSchoolLoginLogID(loginLog.getSchoolLoginLogID());
					activityLog.setTime(Clock.getCurrentTime());
					activityLog.setCandidateID(newCandidate.getCandidateID());
					activityLog.setCandidate(newCandidate);
					activityLog.setSchoolActivity("Created Profile");
					SchoolAccountDataAccessObject.persistActivityLog(activityLog);
				}

				try {
					SendGridObject.sendEmail(email, password, firstName, "School");
				} catch (Exception e) {
					e.printStackTrace();
				}

			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				response.sendRedirect("schools/school-new-profile.jsp");
			}
		}
	}
}
