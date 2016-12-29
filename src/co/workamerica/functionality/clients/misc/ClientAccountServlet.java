package co.workamerica.functionality.clients.misc;


import co.workamerica.entities.clients.Clients;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class ClientAccountServlet
 */
@WebServlet("/ClientAccountServlet")
public class ClientAccountServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);
       
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// Creates EntityManager to query database
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		// Transactions are employed to roll back changes in case of error and
		// ensure data integrity
		EntityTransaction trans = em.getTransaction();
		
		//Creates a session object and retrieves entities
		HttpSession session = request.getSession();	
		int clientID = (int) session.getAttribute("clientID");
		Clients user = em.find(Clients.class, clientID);
		String password = request.getParameter("password");
		
		//Updates entities based on input parameters
		CustomUtilities custom = new CustomUtilities();
		String [] passwordArray = custom.hashPassword(password, null);
		
		user.setFirstName(request.getParameter("firstName"));
		user.setLastName(request.getParameter("lastName"));
		user.setPassword(passwordArray[0]);
		user.setSalt(passwordArray[1]);
		
		try {			
			trans.begin();
			em.merge(user);
			trans.commit();
			response.sendRedirect("landing.jsp");
		} catch (IllegalStateException e) {
			e.printStackTrace();
			logger.error("Candidate account update bug", e);
			trans.rollback();
		} finally {
			em.close();
		}
	}

}
