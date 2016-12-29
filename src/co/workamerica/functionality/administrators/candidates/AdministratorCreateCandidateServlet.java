package co.workamerica.functionality.administrators.candidates;

import co.workamerica.entities.administrators.Administrators;
import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.CertificationPersistence;
import co.workamerica.functionality.persistence.FieldPersistence;
import co.workamerica.functionality.persistence.SchoolDataAccessObject;
import co.workamerica.functionality.sendgrid.API.SendGridContacts;
import co.workamerica.functionality.sendgrid.API.SendGridObject;
import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import co.workamerica.functionality.twilio.API.Twilio;

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
 * Servlet implementation class AdministratorCreateCandidateServlet
 */
@WebServlet("/AdministratorCreateCandidateServlet")
public class AdministratorCreateCandidateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
        Administrators administrator = (Administrators) session.getAttribute("user");

		String adminError = "";
		CustomUtilities custom = new CustomUtilities();

		String email = request.getParameter("email").toLowerCase().trim(), firstName = custom.capitalizeFirstLetter(request.getParameter("firstName")).trim(),
				lastName = custom.capitalizeFirstLetter(request.getParameter("lastName")).trim(), school = request.getParameter("school").trim(), phone = custom.cleanNumber(request.getParameter("phone").trim()),
				zip = request.getParameter("zip").trim(), field = request.getParameter("field").trim();
		if (email.isEmpty()) {
			session.setAttribute("adminError", "Please enter an e-mail address");
			response.sendRedirect("admin/admin-new-profile.jsp");
		} else if (CandidatePersistence.checkIfExistsByEmail(email)) {
			session.setAttribute("adminError", "An account with that e-mail address already exists");
			response.sendRedirect("admin/admin-new-profile.jsp");
		} else {


		int schoolID = SchoolDataAccessObject.getSchoolIDByName(school);

		HashMap<String, String> candidateDetails = new HashMap<String, String>();
		candidateDetails.put("firstName", firstName);
		candidateDetails.put("lastName", lastName);
        candidateDetails.put("email", email);
		candidateDetails.put("city", custom.capitalizeFirstLetter(request.getParameter("city")));
		candidateDetails.put("state", request.getParameter("state"));
		candidateDetails.put("phone", phone);
		candidateDetails.put("alternatePhone", request.getParameter("alternatePhone").replaceAll("[^0-9]+", "").trim());
		candidateDetails.put("school", school);
		candidateDetails.put("completionDate", request.getParameter("completionDate"));
		candidateDetails.put("workExperience", request.getParameter("workExperience"));
		candidateDetails.put("veteran", request.getParameter("veteran"));
		candidateDetails.put("employed", request.getParameter("employed"));
		candidateDetails.put("relocate", request.getParameter("relocate"));
		candidateDetails.put("additionalInformation", request.getParameter("additionalInformation"));
		candidateDetails.put("pastEducation", request.getParameter("pastEducation"));
		candidateDetails.put("validDriversLicense", request.getParameter("validDriversLicense"));
		candidateDetails.put("zip", zip);
		candidateDetails.put("fields", field);
		candidateDetails.put("anticipatedCertifications", request.getParameter("certification"));
		candidateDetails.put("pastFields", request.getParameter("pastField").trim());
		candidateDetails.put("obtainedCertifications", request.getParameter("obtainedCertification"));
		candidateDetails.put("schoolID", schoolID + "");
		candidateDetails.put("authorized", "Yes");
		candidateDetails.put("approved", "Yes");
		candidateDetails.put("workAmericaCreated", "Yes");

        candidateDetails.put("accountOriginDate", Clock.getCurrentDate());
		candidateDetails.put("accountOriginTime", "N/A");
        candidateDetails.put("accountOriginAdminConsoleMethod", "SingleUpload");
        candidateDetails.put("accountOriginTeamMember", administrator.getUsername());
        candidateDetails.put("accountOriginSourceType", "");
		candidateDetails.put("accountOriginSource", school);
        candidateDetails.put("accountOriginSourceID", schoolID + "");

		try {
			Candidates newCandidate = CandidatePersistence.createCandidate(candidateDetails);
			newCandidate.updateCertificationsByListAndDate(CertificationPersistence.getIDListByCommaSeparatedNames(request.getParameter("certification")), request.getParameter("completionDate").trim());
			newCandidate.updateCertificationsByListAndDate(CertificationPersistence.getIDListByCommaSeparatedNames(request.getParameter("obtainedCertification")), "");
			newCandidate.updateFieldsByList(FieldPersistence.getIDListByCommaSeparatedNames(field), "Yes");
			newCandidate.updateFieldsByList(FieldPersistence.getIDListByCommaSeparatedNames(request.getParameter("pastField").trim()), "No");
			newCandidate = CandidatePersistence.merge(newCandidate);
			session.setAttribute("adminSuccess", "Profile added: " + newCandidate.getFirstName() + " " + newCandidate.getLastName());
			List<Candidates> listCandidates = CandidatePersistence.getAll();
			session.setAttribute("listCandidates", listCandidates);
			session.setAttribute("resume", "false");

					// Adding to SendGrid
					try {
						SendGridContacts.add(email, firstName, lastName, zip, Clock.getCurrentDate(), school, "Yes", Clock.getCurrentDate(),
								"AdminConsole", "None", newCandidate.getCandidateID(), field, request.getParameter("pastField").trim());
					} catch (Exception e) {
						e.printStackTrace();
						adminError += "|Failed to add to SendGrid|";
						session.setAttribute("adminError", adminError);
					}

					// Twilio text
					try {
						boolean sent = Twilio.sendText("+1" + phone, email, CandidatePersistence.newPasswordGenerator(candidateDetails), candidateDetails.get("firstName"), "Welcome");
                        if (!sent) {
                            adminError += "|Failed to send text|";
                        }
					} catch (Exception e) {
						e.printStackTrace();
						adminError += "|Failed to send text|";
						session.setAttribute("adminError", adminError);
					}

					// SendGrid welcome e-mail
					try {
						boolean sent = false;
							sent = SendGridObject.sendEmail(email, CandidatePersistence.newPasswordGenerator(candidateDetails), firstName, "Welcome");
						if (!sent) {
							adminError += "|Failed to send welcome e-mail|";
							session.setAttribute("adminError", adminError);
						}
					} catch (Exception e) {
						e.printStackTrace();
						adminError += "|Failed to send welcome e-mail|";
						session.setAttribute("adminError", adminError);
						response.sendRedirect("admin/admin-new-profile.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					adminError += "|Failed to add profile|";
					session.setAttribute("adminError", adminError);
					response.sendRedirect("admin/admin-new-profile.jsp");
				}
				response.sendRedirect("admin/admin-new-profile.jsp");
			}
		}

}
