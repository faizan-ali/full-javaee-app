package co.workamerica.functionality.schools;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.criteria.Certifications;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.criteria.States;
import co.workamerica.entities.logs.schools.SchoolLoginLogs;
import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.functionality.persistence.*;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/SchoolLoginServlet")
public class SchoolLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public SchoolLoginServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		
		String email = request.getParameter("email").toLowerCase().trim(), password = request.getParameter("password");
		
		try {
			SchoolAccounts schoolUser = SchoolAccountDataAccessObject.login(email, password);

			if (schoolUser != null) {
				List<Candidates> listCandidates = SchoolAccountDataAccessObject.getCandidates(schoolUser);
				SchoolLoginLogs loginLog = SchoolAccountDataAccessObject.addLoginLog(schoolUser);

				List<States> stateList = StateDataAccessObject.getAll();
				List<Fields> fieldList = FieldPersistence.getAll();
				List<Schools> schoolList = SchoolDataAccessObject.getAll();
				List<Certifications> certificationList = CertificationPersistence.getAll();

                session.setAttribute("fieldList", fieldList);
                session.setAttribute("stateList", stateList);
                session.setAttribute("schoolList", schoolList);
                session.setAttribute("certificationList", certificationList);
				session.setAttribute("loginLog", loginLog);
				session.setAttribute("user", schoolUser);
				session.setAttribute("listCandidates", listCandidates);
				response.sendRedirect("schools/school-landing.jsp");
			} else {
				session.setAttribute("schoolLoginError", "Invalid login details");
				response.sendRedirect("schools.jsp");
			}
		} catch (NoResultException e) {
			e.printStackTrace();
			session.setAttribute("schoolLoginError", "Invalid login details");
			response.sendRedirect("schools.jsp");
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			session.setAttribute("schoolLoginError",
					"We are currently experiencing technical difficulties. Please try again soon.");
			response.sendRedirect("schools.jsp");
		} catch (Exception e) {
			e.printStackTrace();
			session.setAttribute("schoolLoginError",
					"We are currently experiencing technical difficulties. Please try again soon.");
			response.sendRedirect("schools.jsp");
		}
	}
}
