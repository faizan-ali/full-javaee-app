package co.workamerica.website.contact;

import co.workamerica.functionality.sendgrid.API.SendGridObject;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Created by Faizan on 7/20/2016.
 */
@WebServlet(name = "Footer", urlPatterns = {"/contact/footer"})
public class contact extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String firstName = request.getParameter("firstName") != null ? CustomUtilities.capitalizeFirstLetter(request.getParameter("firstName").trim()) : "",
        lastName = request.getParameter("lastName") != null ? CustomUtilities.capitalizeFirstLetter(request.getParameter("lastName").trim()) : "",
        email = request.getParameter("email") != null ? request.getParameter("email").trim() : "",
        phone = request.getParameter("phone") != null ? CustomUtilities.cleanNumber(request.getParameter("phone").trim()) : "",
        message = request.getParameter("message") != null ? request.getParameter("message").trim() : "";

        SendGridObject.emailLaura(firstName + " " + lastName, email, phone, message);

        response.sendRedirect("http://www.workamerica.co/thank-you.html?contact=true");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
