package co.workamerica.functionality.clients.misc;

import co.workamerica.entities.clients.Clients;
import co.workamerica.functionality.shared.EMFUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.*;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class ClientPasswordResetServlet
 */
@WebServlet("/ClientPasswordResetServlet")
public class ClientPasswordResetServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}


	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Creates EntityManager to query database
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		// Transactions are employed to roll back changes in case of error and
		// ensure data integrity
		EntityTransaction trans = em.getTransaction();

		// Acquires registration details from candidate-reset.jsp request
		String email = (String) (request.getParameter("email"));
		String password = (String) (request.getParameter("password"));

		HttpSession session = request.getSession();

		// Gets user from database based on given email
		String query = "select c from Clients c where c.email = :email";
		TypedQuery<Clients> q = em.createQuery(query, Clients.class);
		q.setParameter("email", email);

		// Updates the user's password with the given password. If the user's
		// email is invalid then a String reset is set to say so and stored in
		// the current session. The user is then redirected to the same page
		// which displays the String.
		try {
			Clients user = q.getSingleResult();
			user.setPassword(password);
			trans.begin();
			em.merge(user);
			trans.commit();
			response.sendRedirect("login.jsp");
		} catch (NoResultException e) {
			e.printStackTrace();
			logger.error("Password reset bug for " + email, e);
			System.out.println("No such user");
			String reset = "Invalid e-mail address";
			session.setAttribute("reset", reset);
			response.sendRedirect("reset.jsp");
		} catch (NonUniqueResultException e) {
			e.printStackTrace();
			System.out.println("He's not the only one..");
			response.sendRedirect("reset.jsp");
		} finally {
			em.close();
		}
	}

}
