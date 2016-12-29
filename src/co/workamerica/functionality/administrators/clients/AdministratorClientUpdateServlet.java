package co.workamerica.functionality.administrators.clients;

import co.workamerica.entities.clients.Clients;
import co.workamerica.functionality.persistence.ClientPersistence;

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
 * Servlet implementation class AdministratorClientUpdateServlet
 */
@WebServlet("/AdministratorClientUpdateServlet")
public class AdministratorClientUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();
		int clientID = Integer.parseInt(request.getParameter("clientID"));

		HashMap<String, String> details = new HashMap<String, String>();
		details.put("firstName", request.getParameter("firstName").trim());
		details.put("lastName", request.getParameter("lastName").trim());
		details.put("companyID", request.getParameter("companyID"));
		details.put("email", request.getParameter("email").trim().toLowerCase());
		details.put("password", request.getParameter("password").length() < 4 ? "Unchanged" : request.getParameter("password"));
		details.put("viewLimit", request.getParameter("viewLimit").trim());
		details.put("geoLimit", request.getParameter("geoLimit").trim());
		details.put("approved", request.getParameter("approved"));

		try {
			Clients client = ClientPersistence.getByID(clientID);
			client = ClientPersistence.update(details, client);
			List<Clients> clientList = ClientPersistence.getAll();
			session.setAttribute("clientList", clientList);
			response.sendRedirect("admin/admin-client-list.jsp");
		} catch (Exception e) {
			session.setAttribute("clientError", "Failed to create account. Code:" + e);
			response.sendRedirect("admin/admin-client-list.jsp");
		}
	}
}
