package co.workamerica.functionality.administrators.criteria;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.criteria.Certifications;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

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
import java.util.ArrayList;
import java.util.List;

/**
 * Servlet implementation class AdministratorCriteriaUpdateServlet
 */
@WebServlet("/AdministratorCriteriaUpdateServlet")
public class AdministratorCriteriaUpdateServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();
		EntityTransaction trans = em.getTransaction();
		CustomUtilities custom = new CustomUtilities();

		String candidateQuery = "SELECT c from Candidates c";
		TypedQuery<Candidates> q = em.createQuery(candidateQuery, Candidates.class);
		List<Candidates> listCandidates = new ArrayList<Candidates>();
		List<Candidates> toUpdate = new ArrayList<Candidates>();

		try {
			listCandidates = q.getResultList();
		} catch (Exception e) {
			session.setAttribute("criteriaError", "Error. Code: " + e);
			response.sendRedirect("admin/admin-criteria-list.jsp");
		}

		if (request.getParameter("field") != null && !request.getParameter("field").isEmpty()) {
			@SuppressWarnings("unchecked")
			List<Fields> fieldList = (List<Fields>) session.getAttribute("fieldList");

			String fieldName = request.getParameter("fieldName");
			if (fieldName == null || request.getParameter("fieldID") == null) {
				session.setAttribute("criteriaError", "Error updating field. Code: NUL");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else if (fieldName.isEmpty() || request.getParameter("fieldID").isEmpty()) {
				session.setAttribute("criteriaError", "Error updating field. Code: EMP");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else {
				int fieldID = Integer.parseInt(request.getParameter("fieldID"));
				Fields field = em.find(Fields.class, fieldID);

				if (field == null || fieldID == 0) {
					session.setAttribute("criteriaError", "Error updating field. Code: NULENT");
					response.sendRedirect("admin/admin-criteria-list.jsp");
				} else {
					for (Candidates c : listCandidates) {

						if (c.getField() != null && !c.getField().isEmpty()) {
							boolean update = false;
							String[] fieldArray = c.getField().split(",");
							for (int i = 0; i < fieldArray.length; i++) {
								if (fieldArray[i].trim().equals(field.getName().trim())) {
									fieldArray[i] = fieldName;
									update = true;
								}
							}
							if (update) {
								c.setField(custom.arrayAsString(fieldArray));
								toUpdate.add(c);
							}
						}

						if (c.getPastField() != null && !c.getPastField().isEmpty()) {
							boolean update = false;
							String[] fieldArray = c.getPastField().split(",");
							for (int i = 0; i < fieldArray.length; i++) {
								if (fieldArray[i].trim().equals(field.getName().trim())) {
									fieldArray[i] = fieldName;
									update = true;
								}
							}
							if (update) {
								c.setPastField(custom.arrayAsString(fieldArray));
								toUpdate.add(c);
							}
						}
					}

					fieldList.remove(field);
					field.setName(fieldName);
					try {
						trans.begin();
						for (Candidates c : toUpdate) {
							em.merge(c);
						}
						em.merge(field);
						trans.commit();

						fieldList.add(field);
						session.setAttribute("fieldList", fieldList);
						session.setAttribute("criteriaSuccess", "Successfully updated field: " + fieldName);
						response.sendRedirect("admin/admin-criteria-list.jsp");
					} catch (Exception e) {
						trans.rollback();
						session.setAttribute("criteriaError", "Error updating field. Code: " + e);
						response.sendRedirect("admin/admin-criteria-list.jsp");
					}
				}
			}

		} else if (request.getParameter("certification") != null && !request.getParameter("certification").isEmpty()) {
			@SuppressWarnings("unchecked")
			List<Certifications> certificationList = (List<Certifications>) session.getAttribute("certificationList");

			String certificationName = request.getParameter("certificationName");
			if (certificationName == null || request.getParameter("certificationID") == null) {
				session.setAttribute("criteriaError", "Error updating cert. Code: NUL");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else if (certificationName.isEmpty() || request.getParameter("certificationID").isEmpty()) {
				session.setAttribute("criteriaError", "Error updating cert. Code: EMP");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else {
				int certID = Integer.parseInt(request.getParameter("certificationID"));

				Certifications cert = em.find(Certifications.class, certID);

				if (cert == null || certID == 0) {
					session.setAttribute("criteriaError", "Error updating cert. Code: NULENT");
					response.sendRedirect("admin/admin-criteria-list.jsp");
				} else {
					for (Candidates c : listCandidates) {

						if (c.getObtainedCertification() != null && !c.getObtainedCertification().isEmpty()) {
							boolean update = false;
							String[] certArray = c.getObtainedCertification().split(",");
							for (int i = 0; i < certArray.length; i++) {
								if (certArray[i].trim().equals(cert.getCertification().trim())) {
									certArray[i] = certificationName;
									update = true;
								}
							}
							if (update) {
								c.setObtainedCertification(custom.arrayAsString(certArray));
								toUpdate.add(c);
							}
						}

						if (c.getAnticipatedCertification() != null && !c.getAnticipatedCertification().isEmpty()) {
							boolean update = false;
							String[] certArray = c.getAnticipatedCertification().split(",");
							for (int i = 0; i < certArray.length; i++) {
								if (certArray[i].trim().equals(cert.getCertification().trim())) {
									certArray[i] = certificationName;
									update = true;
								}
							}
							if (update) {
								c.setAnticipatedCertification(custom.arrayAsString(certArray));
								toUpdate.add(c);
							}
						}
					}

					certificationList.remove(cert);
					cert.setName(certificationName);

					try {
						trans.begin();
						for (Candidates c : toUpdate) {
							em.merge(c);
						}
						em.merge(cert);
						trans.commit();

						certificationList.add(cert);
						session.setAttribute("certificationList", certificationList);
						session.setAttribute("criteriaSuccess", "Successfully updated cert: " + certificationName);
						response.sendRedirect("admin/admin-criteria-list.jsp");
					} catch (Exception e) {
						session.setAttribute("criteriaError", "Error updating cert. Code: " + e);
						response.sendRedirect("admin/admin-criteria-list.jsp");
					}
				}
			}

		} else if (request.getParameter("school") != null && !request.getParameter("school").isEmpty()) {
			@SuppressWarnings("unchecked")
			List<Schools> schoolList = (List<Schools>) session.getAttribute("schoolList");

			String schoolName = request.getParameter("schoolName");
			String schoolState = request.getParameter("schoolState");

			if (schoolName == null || schoolState == null || request.getParameter("schoolID") == null) {
				session.setAttribute("criteriaError", "Error updating school. Code: NUL");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else if (schoolName.isEmpty() || schoolState.isEmpty() || request.getParameter("schoolID").isEmpty()) {
				session.setAttribute("criteriaError", "Error updating school. Code: EMP");
				response.sendRedirect("admin/admin-criteria-list.jsp");
			} else {
				int schoolID = Integer.parseInt(request.getParameter("schoolID"));
				Schools school = em.find(Schools.class, schoolID);

				if (school == null || schoolID == 0) {
					session.setAttribute("criteriaError", "Error updating school. Code: NULENT");
					response.sendRedirect("admin/admin-criteria-list.jsp");
				} else {
					for (Candidates c : listCandidates) {

						if (c.getSchool() != null && !c.getSchool().isEmpty()) {
							if (c.getSchool().trim().equals(school.getName().trim())) {
								c.setSchool(schoolName);
								toUpdate.add(c);
							}
						}
					}

					schoolList.remove(school);
					school.setName(schoolName);
					school.setState(schoolState);

					try {
						trans.begin();
						for (Candidates c : toUpdate) {
							em.merge(c);
						}
						em.merge(school);
						trans.commit();
						schoolList.add(school);
						session.setAttribute("schoolList", schoolList);
						session.setAttribute("criteriaSuccess",
								"Successfully updated school: " + schoolName + ", " + schoolState);
						response.sendRedirect("admin/admin-criteria-list.jsp");
					} catch (Exception e) {
						session.setAttribute("criteriaError", "Error updating school. Code: " + e);
						response.sendRedirect("admin/admin-criteria-list.jsp");
					}
				}
			}

		} else {
			session.setAttribute("criteriaError", "Error. Code: NOCRITE");
			response.sendRedirect("admin/admin-criteria-list.jsp");
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
