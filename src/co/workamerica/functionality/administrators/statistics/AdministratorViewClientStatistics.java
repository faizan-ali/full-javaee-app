package co.workamerica.functionality.administrators.statistics;


import co.workamerica.entities.clients.Clients;
import co.workamerica.functionality.persistence.ClientPersistence;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class AdministratorViewClientStatistics
 */
@WebServlet("/AdministratorViewClientStatistics")
public class AdministratorViewClientStatistics extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();

		int clientID = Integer.parseInt(request.getParameter("clientID"));
		Clients client = ClientPersistence.getByID(clientID);

		session.setAttribute("client", client);
		response.sendRedirect("admin/admin-client-logs.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
