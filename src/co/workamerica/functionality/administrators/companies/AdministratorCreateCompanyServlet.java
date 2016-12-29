package co.workamerica.functionality.administrators.companies;

import co.workamerica.entities.companies.Companies;
import co.workamerica.functionality.persistence.CompanyDataAccessObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

/**
 * Created by Faizan on 7/14/2016.
 */
@WebServlet(name = "AdministratorCreateCompanyServlet", urlPatterns = {"/AdministratorCreateCompanyServlet"})
public class AdministratorCreateCompanyServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        String name = request.getParameter("name") != null ? request.getParameter("name").trim() : "",
                viewLimit = request.getParameter("viewLimit") != null ? request.getParameter("viewLimit").trim() : "";

        if (name.isEmpty() || viewLimit.isEmpty()) {
            session.setAttribute("companyError", "Please enter a name and profile view limit");
        } else {
            Companies company = new Companies();
            company.setName(name);
            company.setViewLimit(Integer.parseInt(viewLimit));
            company.setProfilesViewed(0);
            company.setProfilesViewedThisMonth(0);

            try {
                company = CompanyDataAccessObject.persist(company);
                List<Companies> companyList = CompanyDataAccessObject.getAll();
                session.setAttribute("companyList", companyList);
            } catch (Exception e) {
                session.setAttribute("companyError", "An error occurred");
                e.printStackTrace();
            }
        }
        response.sendRedirect("admin/admin-company-list.jsp");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
