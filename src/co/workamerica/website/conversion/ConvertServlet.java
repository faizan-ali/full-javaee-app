package co.workamerica.website.conversion;

import co.workamerica.functionality.persistence.FieldPersistence;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * Created by Faizan on 8/2/2016.
 */
@WebServlet(name = "ConvertServlet", urlPatterns = {"/candidateonboard"})
public class ConvertServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String category = request.getParameter("category");
        List<String> fields = FieldPersistence.getFieldNamesByCategory(category);


    }
}
