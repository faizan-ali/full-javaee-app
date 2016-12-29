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
 * Servlet implementation class ResumeServlet
 */
@WebServlet("/ResumeUploadServlet")
@MultipartConfig
public class ResumeUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        // Creates a session object and retrieves entities
        HttpSession session = request.getSession();
        int candidateID;
        boolean admin = false;

        try {
            candidateID = (int) session.getAttribute("candidateID");
        } catch (Exception e) {
            candidateID = Integer.parseInt(request.getParameter("candidateID"));
            admin = true;
        }

        Candidates user = CandidatePersistence.getCandidateByID(candidateID);
        ServletFileUpload upload = new ServletFileUpload();
        session.setAttribute("resume", "true");

        try {
            FileItemIterator iterator = upload.getItemIterator(request);
            FileItemStream item = iterator.next();
            byte[] file = fileItemStreamToByteArray(item, 2);
            user.setResume(file);
            user = CandidatePersistence.merge(user);
            session.setAttribute("resumeSuccess", "Resume uploaded!");
            session.setAttribute("user", user);
        } catch (InvalidFormatException e) {
            session.setAttribute("resumeError", "Please upload a resume in PDF or DOC/DOCX format.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("resumeError", "General Error");
        } finally {
            if (admin) {
                response.sendRedirect("admin/admin-candidate-profile.jsp");
            } else {
                response.sendRedirect("candidate-landing.jsp");
            }
        }
    }
}
