package co.workamerica.functionality.clients.texts;

import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.texts.Conversations;
import co.workamerica.functionality.persistence.ConversationDataAccessObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Created by Faizan on 5/11/2016.
 */
@WebServlet("/ViewConversationServlet")
public class ViewConversationServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request, response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        Clients client = (Clients) session.getAttribute("user");

        if (client == null || request.getParameter("candidateID") == null || request.getParameter("candidateID").isEmpty()) {
            response.sendRedirect("outreach.jsp");
        } else {
            int candidateID = Integer.parseInt(request.getParameter("candidateID"));
            try {
                Conversations conversation = ConversationDataAccessObject.getByClientAndCandidateID(client.getClientID(), candidateID);
                session.setAttribute("conversation", conversation);
            } catch (Exception e) {
                e.printStackTrace();
            }
            response.sendRedirect("outreach.jsp");
        }
    }
}
