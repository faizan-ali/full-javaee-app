package co.workamerica.website.subscribe;

import co.workamerica.functionality.mailchimp.MailChimpNewsletter;
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
@WebServlet(name = "Newsletter", urlPatterns = {"/subscribe/newsletter"})
public class Newsletter extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String email = request.getParameter("email");

        if (email != null && CustomUtilities.isValidEmail(email)) {
            MailChimpNewsletter.add(email.trim());
        }

        response.sendRedirect("http://www.workamerica.co/thank-you.html?contact=true");
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
