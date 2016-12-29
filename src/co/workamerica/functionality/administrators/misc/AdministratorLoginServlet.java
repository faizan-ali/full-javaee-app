package co.workamerica.functionality.administrators.misc;

import co.workamerica.entities.administrators.Administrators;
import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.companies.Companies;
import co.workamerica.entities.criteria.Certifications;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.criteria.States;
import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.functionality.persistence.*;

import javax.persistence.NoResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/AdministratorLoginServlet")
public class AdministratorLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String email = request.getParameter("email").toLowerCase().trim();
		String password = request.getParameter("password");
		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(5 * 60 * 60);

		if (email.isEmpty() || password.isEmpty()) {
			session.setAttribute("error", "Please enter both e-mail and password");
			response.sendRedirect("team.jsp");
		} else {

			try {
				Administrators administrator = AdministratorPersistence.login(email, password);

				if (administrator != null) {

					List<Clients> clientList = ClientPersistence.getAll();
					List<Candidates> totalCandidates = CandidatePersistence.getAll();
					List<States> stateList = StateDataAccessObject.getAll();
					List<Fields> fieldList = FieldPersistence.getAll();
					List<Schools> schoolList = SchoolDataAccessObject.getAll();
					List<Certifications> certificationList = CertificationPersistence.getAll();
					List<SchoolAccounts> schoolAccountList = SchoolAccountDataAccessObject.getAll();
					List<Candidates> approvalList = CandidatePersistence.getApprovalList();
					List<Companies> companyList = CompanyDataAccessObject.getAll();
					List<String> mondayList = new ArrayList<String>();

					
					//Removes candidates from totalCandidates that have not been approved and created a new list: listCandidates
					List<Candidates> listCandidates = new ArrayList<Candidates>(totalCandidates);
					for (Candidates c : totalCandidates) {
						if (c.getApproved() == null) {
							listCandidates.remove(c);
						} else if (c.getApproved().equals("No") || c.getApproved().isEmpty()) {
							listCandidates.remove(c);
						}
					}

                    DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
                    LocalDate startDate = LocalDate.parse("03/01/2016", dtf);

                    for (LocalDate date = startDate; date.isBefore(LocalDate.now().plusDays(1)); date = date.plusDays(1)) {
                        if (date.getDayOfWeek().toString().equals("MONDAY")) {
							mondayList.add(date.format(dtf));
                        }
                    }

                    session.setAttribute("companyList", companyList);
                    session.setAttribute("fridayList", mondayList);
					session.setAttribute("administratorID", administrator.getAdministratorID());
					session.setAttribute("administrator", administrator);
					session.setAttribute("clientList", clientList);
					session.setAttribute("listCandidates", listCandidates);
					session.setAttribute("fieldList", fieldList);
					session.setAttribute("stateList", stateList);
					session.setAttribute("schoolList", schoolList);
					session.setAttribute("certificationList", certificationList);
					session.setAttribute("user", administrator);
					session.setAttribute("approvalList", approvalList);
					session.setAttribute("schoolAccountList", schoolAccountList);
					session.setAttribute("totalCandidates", totalCandidates);
					response.sendRedirect("admin/administration.jsp");
				}

				else {
					session.setAttribute("error", "Invalid login details");
					response.sendRedirect("team.jsp");
				}
			} catch (NoResultException e) {
				e.printStackTrace();
				session.setAttribute("error", "Invalid login details");
				response.sendRedirect("team.jsp");
			}  catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("error",
						"Your account is currently experiencing technical difficulties. Code: " + e);
				response.sendRedirect("team.jsp");
			} 
		}
	}

}
