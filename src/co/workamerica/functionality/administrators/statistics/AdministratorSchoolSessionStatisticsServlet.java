package co.workamerica.functionality.administrators.statistics;

import co.workamerica.entities.logs.schools.SchoolActivityLogs;
import co.workamerica.entities.logs.schools.SchoolLoginLogs;
import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.functionality.persistence.SchoolAccountDataAccessObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Faizan on 6/3/2016.
 */
@WebServlet("/AdministratorSchoolSessionStatisticsServlet")
public class AdministratorSchoolSessionStatisticsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int logID = Integer.parseInt(request.getParameter("logID"));
        SchoolAccounts account = (SchoolAccounts) session.getAttribute("schoolAccount");
        SchoolLoginLogs loginLog = SchoolAccountDataAccessObject.getLoginLogByID(logID);

        if (loginLog != null) {
            List<SchoolActivityLogs> activityLogs = loginLog.getActivityLog();
            session.setAttribute("schoolActivityLogs", activityLogs);
        }
        response.sendRedirect("admin/admin-school-logs.jsp");
    }
}
