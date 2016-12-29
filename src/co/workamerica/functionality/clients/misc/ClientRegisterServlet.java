package co.workamerica.functionality.clients.misc;

import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.companies.Companies;
import co.workamerica.entities.criteria.Certifications;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.criteria.States;
import co.workamerica.entities.logs.clients.ClientLoginLogs;
import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.functionality.persistence.*;
import co.workamerica.functionality.sendgrid.API.SendGridObject;
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
import java.util.List;

/**
 * Servlet implementation class ClientRegisterServlet
 */
@WebServlet("/ClientRegisterServlet")
public class ClientRegisterServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	public ClientRegisterServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		session.removeAttribute("signupError");

		String firstName = request.getParameter("firstName").trim(), lastName = request.getParameter("lastName").trim(),
				email = request.getParameter("email").trim(), password = request.getParameter("password"),
				repeatPassword = request.getParameter("repeatPassword"), companyName = request.getParameter("company"),
				code = request.getParameter("code"), error = "";

		boolean codeExists = SchoolAccountDataAccessObject.existsByCode(code);

		HashMap<String, String> details = new HashMap<String, String>();

		if (!password.equals(repeatPassword)) {
			error += "Passwords do not match";
			session.setAttribute("signupError", error);
			session.setAttribute("firstName", firstName);
			session.setAttribute("lastName", lastName);
			session.setAttribute("company", companyName);
			session.setAttribute("email", email);
			response.sendRedirect("login.jsp");
		} else if (firstName.isEmpty() || lastName.isEmpty()) {
			session.setAttribute("signupError", "Please fill out your first and last names");
			session.setAttribute("email", email);
			session.setAttribute("company", companyName);
			response.sendRedirect("login.jsp");
		} else if (ClientPersistence.checkIfExistsByEmail(email)) {
			session.setAttribute("signupError", "An account with that e-mail address already exists");
			session.setAttribute("firstName", firstName);
			session.setAttribute("lastName", lastName);
			session.setAttribute("company", companyName);
			response.sendRedirect("login.jsp");
		} else if (!code.isEmpty() && !codeExists) {
			session.setAttribute("signupError", "Please enter a valid authentication code or leave empty");
			session.setAttribute("firstName", firstName);
			session.setAttribute("lastName", lastName);
			session.setAttribute("email", email);
			session.setAttribute("company", companyName);
			response.sendRedirect("login.jsp");
		} else  {

			try {
				Companies company = new Companies();
				company.setName(companyName);
				company.setGeoLimit("All");
				company.setSchoolAccountID(0);

				details.put("firstName", firstName);
				details.put("lastName", lastName);
				details.put("email", email);
				details.put("password", password);
                details.put("approved", "No");

                SchoolAccounts account = null;
                if (codeExists) {
                    account = SchoolAccountDataAccessObject.getAccountByCode(code);
                    company.setSchoolAccountID(account.getSchoolAccountID());
					company.setViewLimit(0);
                    details.put("approved", "Yes");
                }

                company = CompanyDataAccessObject.persist(company);
				company.setSchoolAccount(account);
				details.put("companyID", company.getCompanyID() + "");
				Clients client = ClientPersistence.createClient(details);

                try {
                    SendGridObject.emailMike(client);
                    SendGridObject.emailClient(client, "newClient");
                } catch (Exception e) {
                    logger.error("Client registration bug", e);
                    e.printStackTrace();
                }

                ClientLoginLogs loginLog = ClientPersistence.addLoginLog(client);
                List<States> stateList = StateDataAccessObject.getAll();
                List<Fields> fieldList = FieldPersistence.getAll();
                List<Schools> schoolList = SchoolDataAccessObject.getAll();
                List<Certifications> certificationList = CertificationPersistence.getAll();

                session.setAttribute("user", client);
                session.setAttribute("loginLog", loginLog);
                session.setAttribute("fieldList", fieldList);
                session.setAttribute("stateList", stateList);
                session.setAttribute("schoolList", schoolList);
                session.setAttribute("certificationList", certificationList);
                session.setAttribute("clientID", client.getClientID());
				response.sendRedirect("landing.jsp");
			} catch (Exception e) {
				logger.error("Client registration bug", e);
				e.printStackTrace();
				session.setAttribute("signupError", "An error occured. Please try again soon.");
				response.sendRedirect("login.jsp");
			}
		}
	}
}
