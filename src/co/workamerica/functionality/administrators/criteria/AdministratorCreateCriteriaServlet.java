package co.workamerica.functionality.administrators.criteria;

import co.workamerica.entities.criteria.Certifications;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Servlet implementation class AdministratorCreateCriteriaServlet
 */
@WebServlet("/AdministratorCreateCriteriaServlet")
public class AdministratorCreateCriteriaServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	public AdministratorCreateCriteriaServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();

		if (request.getParameter("field") != null && !request.getParameter("field").isEmpty()) {
			@SuppressWarnings("unchecked")
			List<Fields> fieldList = (List<Fields>) session.getAttribute("fieldList");
			
			String fieldName = request.getParameter("fieldName");
			if (fieldName == null) {
				session.setAttribute("criteriaError", "Error creating field. Code: NUL");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else if (fieldName.isEmpty()) {
				session.setAttribute("criteriaError", "Error creating field. Code: EMP");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else {
				Fields field = new Fields();
				field.setName(fieldName);

				try {
					trans.begin();
					em.persist(field);
					trans.commit();
					fieldList.add(field);
					session.setAttribute("fieldList", fieldList);
					session.setAttribute("criteriaSuccess", "Successfully created field: " + fieldName);
					response.sendRedirect("admin/admin-criteria-list.jsp");
				} catch (Exception e) {
					session.setAttribute("criteriaError", "Error creating field. Code: " + e);
					response.sendRedirect("admin/admin-criteria-list.jsp");
				}
			}

		} else if (request.getParameter("certification") != null && !request.getParameter("certification").isEmpty()) {
			@SuppressWarnings("unchecked")
			List<Certifications> certificationList = (List<Certifications>) session.getAttribute("certificationList");
			
			String certificationName = request.getParameter("certificationName");
			if (certificationName == null) {
				session.setAttribute("criteriaError", "Error creating cert. Code: NUL");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else if (certificationName.isEmpty()) {
				session.setAttribute("criteriaError", "Error creating cert. Code: EMP");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else {
				Certifications cert = new Certifications();
				cert.setName(certificationName);

				try {
					trans.begin();
					em.persist(cert);
					trans.commit();
					certificationList.add(cert);
					session.setAttribute("certificationList", certificationList);
					session.setAttribute("criteriaSuccess", "Successfully created cert: " + certificationName);
					response.sendRedirect("admin/admin-criteria-list.jsp");
				} catch (Exception e) {
					session.setAttribute("criteriaError", "Error creating cert. Code: " + e);
					response.sendRedirect("admin/admin-criteria-list.jsp");
				}
			}

		} else if (request.getParameter("school") != null && !request.getParameter("school").isEmpty()) {
			@SuppressWarnings("unchecked")
			List<Schools> schoolList = (List<Schools>) session.getAttribute("schoolList");
			
			String schoolName = request.getParameter("schoolName");
			String schoolState = request.getParameter("schoolState");

			if (schoolName == null || schoolState == null) {
				session.setAttribute("criteriaError", "Error creating school. Code: NUL");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else if (schoolName.isEmpty() || schoolState.isEmpty()) {
				session.setAttribute("criteriaError", "Error creating school. Code: EMP");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else {
				Schools school = new Schools();
				school.setName(schoolName);
				school.setState(schoolState);

				try {
					trans.begin();
					em.persist(school);
					trans.commit();
					schoolList.add(school);
					session.setAttribute("schoolList", schoolList);
					session.setAttribute("criteriaSuccess", "Successfully created school: " + schoolName);
					response.sendRedirect("admin/admin-criteria-list.jsp");
				} catch (Exception e) {
					session.setAttribute("criteriaError", "Error creating school. Code: " + e);
					response.sendRedirect("admin/admin-criteria-list.jsp");
				}
			}

		} else {
			session.setAttribute("criteriaError", "Error. Code: NOCRITE");
			response.sendRedirect("admin/admin-criteria-list.jsp");
		}
	}

}
