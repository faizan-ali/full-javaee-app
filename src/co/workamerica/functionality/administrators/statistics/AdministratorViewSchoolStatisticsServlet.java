package co.workamerica.functionality.administrators.statistics;

import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.functionality.persistence.SchoolAccountDataAccessObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Faizan on 6/3/2016.
 */
@WebServlet("/AdministratorViewSchoolStatisticsServlet")
public class AdministratorViewSchoolStatisticsServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();

        int schoolAccountID = Integer.parseInt(request.getParameter("schoolAccountID"));
        SchoolAccounts account = SchoolAccountDataAccessObject.getByID(schoolAccountID);

        session.setAttribute("schoolAccount", account);
        response.sendRedirect("admin/admin-school-logs.jsp");
    }
}
