package co.workamerica.functionality.clients.misc;

import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.clients.ProfileViews;
import co.workamerica.entities.criteria.Certifications;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.criteria.States;
import co.workamerica.functionality.persistence.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.NoResultException;
import javax.persistence.NonUniqueResultException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@WebServlet("/ClientLoginServlet")
public class ClientLoginServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String email = request.getParameter("email").trim().toLowerCase();
		String password = request.getParameter("password");

		if (!email.contains("@")) {
			session.setAttribute("error", "Invalid email");
			response.sendRedirect("login.jsp");
		} else if (password.isEmpty()) {
			session.setAttribute("error", "Please enter a password");
			response.sendRedirect("login.jsp");
		} else {

			try {
				Clients user = ClientPersistence.login(email, password);

				if (user != null) {
					List<States> stateList = StateDataAccessObject.getAll();
					List<Fields> fieldList = FieldPersistence.getAll();
					List<Schools> schoolList = SchoolDataAccessObject.getAll();
					List<Certifications> certificationList = CertificationPersistence.getAll();

					// Allows previously viewed profiles to be highlighted in
					// search results
					ArrayList<Integer> viewList = new ArrayList<Integer>();
					for (ProfileViews view : user.getProfileViews()) {
						viewList.add(view.getCandidateID());
					}

					session.setAttribute("viewList", viewList);
					session.setAttribute("user", user);
					session.setAttribute("loginLog", user.getLoginLog());
					session.setAttribute("fieldList", fieldList);
					session.setAttribute("stateList", stateList);
					session.setAttribute("schoolList", schoolList);
					session.setAttribute("certificationList", certificationList);
					session.setAttribute("clientID", user.getClientID());

					response.sendRedirect("landing.jsp");
				} else {
					session.setAttribute("error", "Invalid login details");
					response.sendRedirect("login.jsp");
				}
			} catch (NoResultException e) {
                logger.error("No user for " + email, e);
				e.printStackTrace();
				session.setAttribute("error", "Invalid login details");
				response.sendRedirect("login.jsp");
			} catch (NonUniqueResultException e) {
                logger.error("Multiple users for " + email, e);
				e.printStackTrace();
				session.setAttribute("error",
						"Your account is currently experiencing technical difficulties. Please try again soon. Code: Mult1");
				response.sendRedirect("login.jsp");
			} catch (Exception e) {
                logger.error("Client login error for " + email, e);
				e.printStackTrace();
				session.setAttribute("error",
						"Your account is currently experiencing technical difficulties. Please try again soon.");
				response.sendRedirect("login.jsp");
			}
		}
	}
}
