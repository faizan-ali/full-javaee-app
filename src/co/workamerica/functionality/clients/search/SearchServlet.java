package co.workamerica.functionality.clients.search;

//This servlet carries out a candidate search based on criteria that an employer provides in landing.jsp

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.ClientPipelines;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.logs.clients.ClientLoginLogs;
import co.workamerica.entities.logs.clients.SearchLogs;
import co.workamerica.functionality.google.api.Geocode;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

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
import java.util.*;

/**
 * Servlet implementation class SearchServlet
 */
@WebServlet(urlPatterns = { "/SearchServlet", "/Search.do" })
public class SearchServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		/* Refresh search results in current session */
		session.removeAttribute("listCandidates");

		// Creates EntityManager to query database
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		// Transactions are employed to roll back changes in case of error and
		// to ensure data integrity
		EntityTransaction trans = em.getTransaction();

		// For checking the existence of resultant Candidates in Client Pipeline
		// Updates the Candidates' pipelineStatus String
		Clients client = (Clients) session.getAttribute("user");
		Set<ClientPipelines> pipeline = client.getPipeline();

		ClientLoginLogs loginLog = client.getLastLoginLog();

		String[] states = request.getParameterValues("states"), fields = request.getParameterValues("fields"),
				certifications = request.getParameterValues("certifications"),
				schools = request.getParameterValues("schools"), latLong = new String[] { " ", " " };

		String stateCriteria = "s.state LIKE ", fieldCriteria = "s.field LIKE ",
				obtainedCertificationCriteria = "s.obtainedCertification LIKE ",
				anticipatedCertificationCriteria = "s.anticipatedCertification LIKE ",
				schoolCriteria = "s.school LIKE ", keywordsFromRequest = request.getParameter("keywords"),
				radiusCity = request.getParameter("radiusCity") != null && !request.getParameter("radiusCity").isEmpty()
						? request.getParameter("radiusCity").replaceAll("\\s+", "+") : "",
				zip = request.getParameter("zip") != null && !request.getParameter("zip").isEmpty()
						? request.getParameter("zip") : "";
		System.out.println(radiusCity);

		int radiusDistance = request.getParameter("radiusDistance") != null
				&& !request.getParameter("radiusDistance").isEmpty()
						? Integer.parseInt(request.getParameter("radiusDistance")) : -1;

		String[] keywords = keywordsFromRequest.split("\\s+");

		// Getting lat/long co-ordinates for radius location
		if (!radiusCity.equals("") && !states[0].equals("")) {
			radiusCity.replace(" ", "+");
			latLong = Geocode.toGeocode(radiusCity + "+" + states[0]);
		} else if (!zip.equals("")) {
			latLong = Geocode.toGeocode(zip);
		}

		/* For search logs */
		SearchLogs searchLog = new SearchLogs();
		searchLog.setClientLoginLog(loginLog);
		searchLog.setClientLoginLogsID(loginLog.getClientLoginLogsID());
		searchLog.setSearchKeyword(keywordsFromRequest);
		searchLog.setSearchCriteria(Arrays.toString(states) + ";" + Arrays.toString(schools) + ";"
				+ Arrays.toString(fields) + ";" + Arrays.toString(certifications));
		searchLog.setTime(Clock.getCurrentTime());
		searchLog.setRadius(radiusDistance == -1 ? 0 + ""
				: radiusDistance + "" + ";"
						+ (radiusCity.equals("") ? (zip.equals("") ? "Unspecified City/ZIP" : zip) : radiusCity));

		// If the employer chooses "All" for any criteria then the criteria is
		// set to "%" which is essentially akin to the wildcard "*" but for LIKE
		// queries
		if (states == null || states[0].equals("All") || states[0].isEmpty()) {
			stateCriteria += "'%'";
		} else {
			if (states.length == 1) {
				stateCriteria += "'" + states[0] + "'";
			} else {
				for (int i = 0; i < states.length; i++) {
					stateCriteria += "'" + states[i] + "'";

					if (i != states.length - 1) {
						stateCriteria += " OR s.state LIKE ";
					}
				}
			}
		}

		if (fields == null || fields[0].equals("All")) {
			fieldCriteria += "'%'";
		} else {
			if (fields.length == 1) {
				fieldCriteria += "'%" + fields[0] + "%'";
			} else {
				for (int i = 0; i < fields.length; i++) {
					fieldCriteria += "'%" + fields[i] + "%'";

					if (i != fields.length - 1) {
						fieldCriteria += " OR s.field LIKE ";
					}
				}
			}
		}

		if (certifications == null || certifications[0].equals("All")) {
			obtainedCertificationCriteria += "'%'";
			anticipatedCertificationCriteria += "'%'";
		} else {
			if (certifications.length == 1) {
				obtainedCertificationCriteria += "'%" + certifications[0] + "%'";
				anticipatedCertificationCriteria += "'%" + certifications[0] + "%'";
			} else {
				for (int i = 0; i < certifications.length; i++) {
					obtainedCertificationCriteria += "'%" + certifications[i] + "%'";
					anticipatedCertificationCriteria += "'%" + certifications[i] + "%'";

					if (i != certifications.length - 1) {
						obtainedCertificationCriteria += " OR s.obtainedCertification LIKE ";
						anticipatedCertificationCriteria += " OR s.obtainedCertification LIKE ";
					}
				}
			}
		}

		if (schools == null || schools[0].equals("All")) {
			schoolCriteria += "'%'";
		} else {
			if (schools.length == 1) {
				schools[0] = schools[0].replaceAll("'", "%");
				schoolCriteria += "'%" + schools[0] + "%'";
			} else {
				for (int i = 0; i < schools.length; i++) {
					schools[i] = schools[0].replaceAll("'", "%");
					schoolCriteria += "'%" + schools[i] + "%'";

					if (i != schools.length - 1) {
						schoolCriteria += " OR s.school LIKE ";
					}
				}
			}
		}

		// Query that selects candidates from database based on criteria
		String query = "SELECT s FROM Candidates s WHERE s.authorized = 'Yes' AND s.approved = 'Yes' AND ("
				+ stateCriteria + ") AND (" + fieldCriteria + ") AND " + schoolCriteria + " AND (("
				+ anticipatedCertificationCriteria + ") OR (" + obtainedCertificationCriteria + ")) AND "
				+ "s.authorized = 'Yes' ORDER BY s.dateCreated";

		TypedQuery<Candidates> q = em.createQuery(query, Candidates.class);

		try {
			List<Candidates> listCandidates = q.getResultList();
			trans.begin();
			em.persist(searchLog);
			trans.commit();

			// Narrowing down candidates for radius miles and location (if
			// specified)
			Iterator<Candidates> iterator = listCandidates.iterator();

			while (iterator.hasNext()) {
				Candidates c = iterator.next();
				if (radiusDistance == -1) {

					if (!radiusCity.equals("")) {
						if (!c.getCity().equalsIgnoreCase(radiusCity)) {
							iterator.remove();
						}
					} else if (!zip.equals("")) {
						if (!c.getZip().equals(zip)) {
							iterator.remove();
						}
					}
				} else {

					if (c.getLatitude().length() < 2 || c.getLatitude() == null || c.getLongitude().length() < 2
							|| c.getLongitude() == null) {
						iterator.remove();
					} else {

						int d = Geocode.haversine(Double.parseDouble(latLong[0]), Double.parseDouble(latLong[1]),
								Double.parseDouble(c.getLatitude()), Double.parseDouble(c.getLongitude()));
						if (d > radiusDistance) {
							iterator.remove();
						}
					}
				}
			}

			// Narrowing down candidates for keyword search (if keywords are
			// used)
			ArrayList<ArrayList<Candidates>> masterList = new ArrayList<ArrayList<Candidates>>();
			for (String singleWord : keywords) {
				String keyword = singleWord.toLowerCase();
				ArrayList<Candidates> temp = new ArrayList<Candidates>();
				for (Candidates ed : listCandidates) {
					if (pipeline != null && !pipeline.isEmpty()) {
						for (ClientPipelines elt : pipeline) {
							if (ed.getCandidateID() == elt.getCandidateID()) {
								ed.setPipelineStatus(elt.getStatus());
							}
						}
					}

					if (keywords.length > 0 && keywords[0].length() > 1) {

						if (((ed.getField() != null && ed.getField().length() > 1
								&& ed.getField().toLowerCase().contains(keyword))
								|| (ed.getPastField() != null && ed.getPastField().length() > 1
										&& ed.getPastField().toLowerCase().contains(keyword))
								|| (ed.getAnticipatedCertification() != null
										&& ed.getAnticipatedCertification().length() > 1
										&& ed.getAnticipatedCertification().toLowerCase().contains(keyword))
								|| (ed.getObtainedCertification() != null && ed.getObtainedCertification().length() > 1
										&& ed.getObtainedCertification().toLowerCase().contains(keyword))
								|| (ed.getAdditionalInformation() != null && ed.getAdditionalInformation().length() > 1
										&& ed.getAdditionalInformation().toLowerCase().contains(keyword))
								|| (ed.getCity() != null && ed.getCity().length() > 1
										&& ed.getCity().toLowerCase().contains(keyword))
								|| (ed.getState() != null && ed.getState().length() > 1
										&& ed.getState().toLowerCase().contains(keyword))
								|| (ed.getFirstName().toLowerCase().contains(keyword))
								|| (ed.getLastName().toLowerCase().contains(keyword)))) {
							temp.add(ed);
						}
					}
				}
				masterList.add(temp);
			}

			if (keywords.length > 0 && keywords[0].length() > 1) {
				for (ArrayList<Candidates> list : masterList) {
					listCandidates.retainAll(list);
				}
			}

			session.setAttribute("listCandidates", listCandidates);
			response.sendRedirect("results.jsp");
		}  catch (Exception e) {
            logger.error("Search bug", e);
			e.printStackTrace();
			response.sendRedirect("landing.jsp");
		} finally {
			em.close();
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
