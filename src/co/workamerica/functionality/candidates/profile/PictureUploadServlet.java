package co.workamerica.functionality.candidates.profile;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.functionality.persistence.CandidatePersistence;
import org.apache.commons.compress.archivers.dump.InvalidFormatException;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

import static co.workamerica.functionality.shared.utilities.CustomUtilities.fileItemStreamToByteArray;

/**
 * Servlet implementation class PictureUploadServlet
 */
@WebServlet("/PictureUploadServlet")
@MultipartConfig
public class PictureUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Creates a session object and retrieves entities
        HttpSession session = request.getSession();
        int candidateID = (int) session.getAttribute("candidateID");
        Candidates user = CandidatePersistence.getCandidateByID(candidateID);
        ServletFileUpload upload = new ServletFileUpload();
        session.setAttribute("resume", "true");

        try {
            FileItemIterator iterator = upload.getItemIterator(request);
            FileItemStream item = iterator.next();
            byte[] file = fileItemStreamToByteArray(item, 1);
            user.setPhoto(file);
            user = CandidatePersistence.merge(user);
            session.setAttribute("resumeSuccess", "Picture uploaded!");
            session.setAttribute("user", user);
        } catch (InvalidFormatException e) {
            session.setAttribute("resumeError", "Please upload a picture in PNG or JPG/JPEG format.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("resumeError", "General Error");
        } finally {
            response.sendRedirect("candidate-landing.jsp");
        }
    }
}
