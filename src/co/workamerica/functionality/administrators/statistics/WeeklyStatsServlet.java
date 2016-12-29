package co.workamerica.functionality.administrators.statistics;

import co.workamerica.entities.statistics.WeeklyStats;
import co.workamerica.functionality.persistence.WeeklyStatsDataAccessObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/WeeklyStatsServlet")
public class WeeklyStatsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public WeeklyStatsServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		try {
			List<WeeklyStats> statsList = WeeklyStatsDataAccessObject.getAll();
			session.setAttribute("statsList", statsList);
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			response.sendRedirect("admin/admin-statistics-list.jsp");
		}		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
