package co.workamerica.functionality.administrators.schoolaccounts;

import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.functionality.persistence.SchoolAccountDataAccessObject;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class AdministratorSchoolAccountUpdateServlet
 */
@WebServlet("/AdministratorSchoolAccountUpdateServlet")
public class AdministratorSchoolAccountUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdministratorSchoolAccountUpdateServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		String email = request.getParameter("email").trim(), password = request.getParameter("password");
		int schoolAccountID = Integer.parseInt(request.getParameter("schoolAccountID"));
		SchoolAccounts account = SchoolAccountDataAccessObject.getByID(schoolAccountID);

		if (!email.contains("@")) {
			session.setAttribute("schoolError", "Please enter a valid e-mail.");
			response.sendRedirect("admin/admin-school-list.jsp");
		} else if (account == null) {
			session.setAttribute("schoolError", "Error updating account. Code: NULL");
			response.sendRedirect("admin/admin-school-list.jsp");
		} else {

			if (password.length() > 3) {
				String[] passwordArray = CustomUtilities.hashPassword(password, null);
				account.setPassword(passwordArray[0]);
				account.setSalt(passwordArray[1]);
			}
			account.setEmail(email);
			
			try {
				SchoolAccountDataAccessObject.merge(account);
				List<SchoolAccounts> schoolAccountList = SchoolAccountDataAccessObject.getAll();
				response.sendRedirect("admin/admin-school-list.jsp");
				session.setAttribute("schoolAccountList", schoolAccountList);
			} catch (Exception e) {
				e.printStackTrace();
				session.setAttribute("schoolError", "Error adding account. Code: " + e);
				response.sendRedirect("admin/admin-school-list.jsp");
			}
		}
	}
}
