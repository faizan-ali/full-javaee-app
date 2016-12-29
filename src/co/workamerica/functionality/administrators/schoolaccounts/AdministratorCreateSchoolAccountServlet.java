package co.workamerica.functionality.administrators.schoolaccounts;

import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.functionality.persistence.SchoolAccountDataAccessObject;
import co.workamerica.functionality.persistence.SchoolDataAccessObject;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.List;

@WebServlet("/AdministratorCreateSchoolAccountServlet")
public class AdministratorCreateSchoolAccountServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AdministratorCreateSchoolAccountServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        String email = request.getParameter("email").trim(), password = request.getParameter("password"),
                code = request.getParameter("code");
        int schoolID = Integer.parseInt(request.getParameter("school"));

        if (email.isEmpty() || !email.contains("@") || password.isEmpty()) {
            session.setAttribute("schoolError", "Please enter a valid e-mail and password.");
            response.sendRedirect("admin/admin-school-list.jsp");
        } else {
            Schools school = SchoolDataAccessObject.getSchoolByID(schoolID);

            if (school == null) {
                session.setAttribute("schoolError", "Error adding account. Code: NULLSCHOOL");
                response.sendRedirect("admin/admin-school-list.jsp");
            } else {
                String[] passwordArray = CustomUtilities.hashPassword(password, null);
                SchoolAccounts account = new SchoolAccounts();
                account.setEmail(email);
                account.setPassword(passwordArray[0]);
                account.setSalt(passwordArray[1]);
                account.setSchoolID(schoolID);
                account.setSchool(school);
                school.addSchoolAccount(account);

                if (code.isEmpty()) {
                    account.setEmployerCode(CustomUtilities.randomStringGenerator(4));
                } else {
                    account.setEmployerCode(code);
                }

                try {
                    SchoolAccountDataAccessObject.persist(account);
                    List<SchoolAccounts> schoolAccountList = SchoolAccountDataAccessObject.getAll();
                    session.setAttribute("schoolAccountList", schoolAccountList);
                    response.sendRedirect("admin/admin-school-list.jsp");
                } catch (Exception e) {
                    e.printStackTrace();
                    session.setAttribute("schoolError", "Error adding account. Code: " + e);
                    response.sendRedirect("admin/admin-school-list.jsp");
                }
            }
        }
    }

}
