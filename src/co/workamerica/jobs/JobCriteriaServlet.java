package co.workamerica.jobs;

import co.workamerica.entities.criteria.Certifications;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.States;
import co.workamerica.filters.LoggingFilter;
import co.workamerica.functionality.shared.EMFUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class JobCriteriaServlet
 */
@WebServlet("/JobCriteriaServlet")
public class JobCriteriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(LoggingFilter.class);

    public JobCriteriaServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		HttpSession session = request.getSession();
		session.setMaxInactiveInterval(5*60*60);

		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		String certificationQuery = "select certs from Certifications certs ORDER BY certs.certification ASC";
		String stateQuery = "select state from States state ORDER BY state.name ASC";
		String fieldQuery = "select f from Fields f ORDER BY f.name ASC";

		TypedQuery<Fields> qField = em.createQuery(fieldQuery, Fields.class);
		TypedQuery<States> qState = em.createQuery(stateQuery, States.class);
		TypedQuery<Certifications> qCertification = em.createQuery(certificationQuery, Certifications.class);

		try {

			List<States> stateList = qState.getResultList();
			List<Fields> fieldList = qField.getResultList();
			List<Certifications> certificationList = qCertification.getResultList();

			session.setAttribute("fieldList", fieldList);
			session.setAttribute("stateList", stateList);
			session.setAttribute("certificationList", certificationList);

			response.sendRedirect("job-search.jsp");
		} catch (Exception e) {
			logger.error("Exception at JobCriteriaServlet!", e);
			e.printStackTrace();
		} finally {
			em.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
