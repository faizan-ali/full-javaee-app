package co.workamerica.functionality.administrators.misc;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;

/**
 * Servlet implementation class VisualizationServlet
 */
@WebServlet("/VisualizationServlet")
public class VisualizationServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public VisualizationServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		EntityManager em = EMFUtil.getEMFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();

		String query = "select c from Candidates c";
		TypedQuery<Candidates> q = em.createQuery(query, Candidates.class);

		try {
			List<Candidates> candidateList = q.getResultList();
			HashSet<Candidates> newCandidates = new HashSet<Candidates>();
			HashMap<String, Integer> map = new HashMap<String, Integer>();

			session.setAttribute("candidateList", candidateList);

			String today = Clock.getCurrentDate();

			map.put("Unavailable", 0);
			for (Candidates c : candidateList) {
				if (c.getDateCreated() != null && c.getDateCreated().equals(today)) {
					newCandidates.add(c);

					if (map.get(c.getState()) == null) {
						if (c.getState() == null || c.getState().equals("")) {
							map.put("Unavailable", map.get("Unavailable") + 1);
						} else {
							map.put(c.getState(), 1);
						}
					} else {
						map.put(c.getState(), map.get(c.getState()) + 1);
					}
				}
			}

			session.setAttribute("newCandidateList", newCandidates);
			session.setAttribute("candidatesMap", map);

		} catch (Exception e) {
			e.printStackTrace();
		}
		response.sendRedirect("admin/admin-metrics.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
