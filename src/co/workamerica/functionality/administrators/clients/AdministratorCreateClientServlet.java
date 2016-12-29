package co.workamerica.functionality.administrators.clients;

import co.workamerica.entities.clients.Clients;
import co.workamerica.functionality.persistence.ClientPersistence;
import com.twilio.sdk.TwilioRestException;

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
 * Servlet implementation class AdministratorCreateClientServlet
 */
@WebServlet("/AdministratorCreateClientServlet")
public class AdministratorCreateClientServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdministratorCreateClientServlet() {
		super();
	}


	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

        HttpSession session = request.getSession();

        if (!ClientPersistence.checkIfExistsByEmail(request.getParameter("email").trim().toLowerCase())) {

           String viewLimit = "0";

           if (!request.getParameter("viewLimit").isEmpty()) {
                viewLimit = request.getParameter("viewLimit").trim();
           }

            HashMap<String, String> details = new HashMap<String, String>();
            details.put("firstName", request.getParameter("firstName").trim());
            details.put("lastName", request.getParameter("lastName").trim());
            details.put("companyID", request.getParameter("companyID"));
            details.put("email", request.getParameter("email").trim().toLowerCase());
            details.put("password", request.getParameter("password"));
            details.put("assignNumber", request.getParameter("assignNumber"));
            details.put("viewLimit", viewLimit);
            details.put("geoLimit", request.getParameter("geoLimit").trim());
            details.put("approved", "Yes");

            try {
                Clients client = ClientPersistence.createClient(details);
                List<Clients> clientList = ClientPersistence.getAll();
                session.setAttribute("clientList", clientList);
            } catch (TwilioRestException e) {
                session.setAttribute("clientError", "Failed to create account. Code: TWILRESTEXC");
            }
            catch (Exception e) {
                session.setAttribute("clientError", "Failed to create account. Code:" + e);
                e.printStackTrace();
            }
            response.sendRedirect("admin/admin-client-list.jsp");
        } else {
            session.setAttribute("clientError", "Employer already exists");
            response.sendRedirect("admin/admin-client-list.jsp");
        }
    }
}
