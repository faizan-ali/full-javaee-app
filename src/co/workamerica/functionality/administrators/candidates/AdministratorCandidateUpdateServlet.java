package co.workamerica.functionality.administrators.candidates;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.CertificationPersistence;
import co.workamerica.functionality.persistence.FieldPersistence;
import co.workamerica.functionality.persistence.SchoolDataAccessObject;
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
 * Servlet implementation class AdministratorCandidateUpdateServlet
 */
@WebServlet("/AdministratorCandidateUpdateServlet")
public class AdministratorCandidateUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		Candidates candidate = CandidatePersistence.getCandidateByID((Integer) session.getAttribute("candidateID"));
		
		int schoolID = SchoolDataAccessObject.getSchoolIDByName(request.getParameter("school"));

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
		candidateDetails.put("email", request.getParameter("email"));
		
		try {
			candidate = CandidatePersistence.updateCandidate(candidateDetails, candidate);
			candidate.updateCertificationsByListAndDate(CertificationPersistence.getIDListByCommaSeparatedNames(request.getParameter("certification")), request.getParameter("completionDate").trim());
			candidate.updateCertificationsByListAndDate(CertificationPersistence.getIDListByCommaSeparatedNames(request.getParameter("obtainedCertification")), "");
            candidate.updateFieldsByList(FieldPersistence.getIDListByCommaSeparatedNames(request.getParameter("field").trim()), "Yes");
            candidate.updateFieldsByList(FieldPersistence.getIDListByCommaSeparatedNames(request.getParameter("pastField").trim()), "No");

			candidate = CandidatePersistence.merge(candidate);

			List<Candidates> listCandidates = CandidatePersistence.getAll();
			
			session.setAttribute("listCandidates", listCandidates);
			session.setAttribute("resume", "false");
			session.setAttribute("candidate", candidate);
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("adminError", "An error occured. Profile not created.");
		} finally {
			response.sendRedirect("admin/admin-candidate-profile.jsp");
		}
	}
}
