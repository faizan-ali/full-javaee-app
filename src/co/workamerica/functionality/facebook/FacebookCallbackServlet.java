
package co.workamerica.functionality.facebook;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.criteria.Certifications;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.criteria.States;
import co.workamerica.entities.logs.candidates.CandidateLoginLogs;
import co.workamerica.functionality.persistence.*;
import co.workamerica.functionality.sendgrid.API.SendGridContacts;
import co.workamerica.functionality.sendgrid.API.SendGridObject;
import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import com.restfb.DefaultFacebookClient;
import com.restfb.FacebookClient;
import com.restfb.Parameter;
import com.restfb.types.User;
import org.json.JSONObject;

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
 * Servlet implementation class FacebookCallbackServlet
 */
@WebServlet("/FacebookCallbackServlet")
public class FacebookCallbackServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String appID = "";
	private final String appSecret = "";

	public FacebookCallbackServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		StringBuffer callbackURL = request.getRequestURL();
		int index = callbackURL.lastIndexOf("/");
		callbackURL.replace(index, callbackURL.length(), "").append("/FacebookCallbackServlet");



		String oauthCode = request.getParameter("code");
		String token = CustomUtilities.RequestToString("https://graph.facebook.com/v2.3/oauth/access_token?" + "client_id=" + appID + "&redirect_uri=" + callbackURL.toString() + "&client_secret=" + appSecret + "&code=" + oauthCode);

		JSONObject json = new JSONObject(token);

		String accessToken = json.getString("access_token");
		FacebookClient facebook = new DefaultFacebookClient(accessToken, com.restfb.Version.VERSION_2_3);
		User user = facebook.fetchObject("me", User.class, Parameter.with("fields", "email,first_name,last_name"));

		if (CandidatePersistence.checkIfExistsByEmail(user.getEmail())) {
			session.setAttribute("signupError", "An account with that e-mail address already exists");
			response.sendRedirect("candidate-login.jsp");
		} else {

			try {
				String firstName = user.getFirstName(), lastName = user.getLastName(), email = user.getEmail();
				HashMap<String, String> details = new HashMap<String, String>();
				details.put("firstName", firstName);
				details.put("lastName", lastName);
				details.put("email", email);
				details.put("workAmericaCreated", "Yes");

				Candidates candidate = CandidatePersistence.createCandidate(details);

				List<States> stateList = StateDataAccessObject.getAll();
				List<Fields> fieldList = FieldPersistence.getAll();
				List<Schools> schoolList = SchoolDataAccessObject.getAll();
				List<Certifications> certificationList = CertificationPersistence.getAll();

				CandidateLoginLogs loginLog = CandidatePersistence.addLoginLog(candidate);

				// Adding to SendGrid
				try {
					SendGridContacts.add(email, firstName, lastName, "0", Clock.getCurrentDate(), "", "No", Clock.getCurrentDate(), "FacebookButton", "None", candidate.getCandidateID(), "", "");
				} catch (Exception e) {
					e.printStackTrace();
				}

				// SendGrid welcome e-mail
				try {
					SendGridObject.sendEmail(email, CandidatePersistence.newPasswordGenerator(details), firstName,
							"Register");
				} catch (Exception e) {
					e.printStackTrace();
				}

				session.setAttribute("candidateID", candidate.getCandidateID());
				session.setAttribute("loginLog", loginLog);
				session.setAttribute("fieldList", fieldList);
				session.setAttribute("stateList", stateList);
				session.setAttribute("schoolList", schoolList);
				session.setAttribute("certificationList", certificationList);
				session.setAttribute("user", candidate);
				response.sendRedirect("candidate-landing.jsp");
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("signupError", "An error occured. Please try again soon.");
				response.sendRedirect("candidate-login.jsp");
			}
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}