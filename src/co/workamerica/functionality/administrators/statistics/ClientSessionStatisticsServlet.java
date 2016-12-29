package co.workamerica.functionality.administrators.statistics;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.logs.clients.ClientLoginLogs;
import co.workamerica.entities.logs.clients.PipelineChangeLogs;
import co.workamerica.entities.logs.clients.ProfileViewLogs;
import co.workamerica.entities.logs.clients.SearchLogs;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@WebServlet("/ClientSessionStatisticsServlet")
public class ClientSessionStatisticsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// Creates EntityManager to query database
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		// Creates a session object and retrieves entities
		HttpSession session = request.getSession();

		// For tracking session length
		String startTime = "", endTime = "";
		SimpleDateFormat timeFormat = new SimpleDateFormat("hh:mm a zzz");
		Date temp = null, startAsDate = null, endAsDate = null;

		// Retrieves ID of required log from session along with relevant Clients
		// entity
		int logID = Integer.parseInt(request.getParameter("logID"));
		Clients client = (Clients) session.getAttribute("client");

		// Retrieves ClientsLoginLog entity from database
		ClientLoginLogs loginLog = em.find(ClientLoginLogs.class, logID);
		startTime = loginLog.getTime();
		endTime = loginLog.getTime();
		try {
			startAsDate = timeFormat.parse(startTime);
			endAsDate = timeFormat.parse(endTime);
		} catch (java.text.ParseException e1) {
			e1.printStackTrace();
			response.sendRedirect("admin/admin-client-logs");
		}
		
		List<Candidates> profilesViewed = new ArrayList<Candidates>();
		HashMap<Candidates, String> profilesChanged = new HashMap<Candidates, String>();

		// Purely for tracking session length
		for (SearchLogs searchLog : loginLog.getSearchLog()) {
			if (searchLog != null) {
				try {
					temp = timeFormat.parse(searchLog.getTime());
				} catch (java.text.ParseException e) {
					e.printStackTrace();
					System.out.println("Error parsing time at search logs");
					response.sendRedirect("admin/admin-client-logs");
				}

				if (temp.after(endAsDate)) {
					endTime = searchLog.getTime();
				}
			}
		}
		
		// Retrieves Candidates entities by obtaining candidateID from each
		// ProfileViewLogs entity and tracks session length
		for (ProfileViewLogs viewLog : loginLog.getProfileViewLog()) {
			if (viewLog != null) {
				try {
					endAsDate = timeFormat.parse(endTime);
					temp = timeFormat.parse(viewLog.getTime());
				} catch (java.text.ParseException e) {
					e.printStackTrace();
					System.out.println("Error parsing time at view logs");
					response.sendRedirect("admin/admin-client-logs");
				}

				if (temp.after(endAsDate)) {
					endTime = viewLog.getTime();
				}

				profilesViewed.add(em.find(Candidates.class, viewLog.getCandidateID()));
			}
		}

		for (PipelineChangeLogs changeLog : loginLog.getPipelineChangeLog()) {
			if (changeLog != null) {
				try {
					endAsDate = timeFormat.parse(endTime);
					temp = timeFormat.parse(changeLog.getTime());
				} catch (java.text.ParseException e) {
					e.printStackTrace();
					System.out.println("Error parsing time at change logs");
					response.sendRedirect("admin/admin-client-logs");
				}

				if (temp.after(endAsDate)) {
					endTime = changeLog.getTime();
				}
				profilesChanged.merge(em.find(Candidates.class, changeLog.getCandidateID()),
						changeLog.getPipelineChange(), String::concat);
			}
		}

		long sessionLength = (endAsDate.getTime() - startAsDate.getTime()) / 60000;

		session.setAttribute("sessionLength", sessionLength);
		session.setAttribute("profilesViewed", profilesViewed);
		session.setAttribute("profilesChanged", profilesChanged);
		response.sendRedirect("admin/admin-client-session-logs.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
