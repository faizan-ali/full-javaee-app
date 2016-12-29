package co.workamerica.functionality.candidates.profile;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.candidates.UploadedCertifications;
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
import java.util.ArrayList;
import java.util.List;

import static co.workamerica.functionality.shared.utilities.CustomUtilities.fileItemStreamToByteArray;
import static co.workamerica.functionality.shared.utilities.CustomUtilities.getFileType;

/**
 * Created by Faizan on 6/20/2016.
 */

@WebServlet( name = "CertificationUploadServlet", urlPatterns = {"/candidate-certification"})
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1224, maxFileSize=1024*1224*5, maxRequestSize=1024*1224*5*5)
public class CertificationUploadServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        HttpSession session = request.getSession();
        int candidateID = (int) session.getAttribute("candidateID");
        Candidates candidate = CandidatePersistence.getCandidateByID(candidateID);
        session.setAttribute("resume", "true");
        ServletFileUpload upload = new ServletFileUpload();


        try {
            FileItemIterator iterator = upload.getItemIterator(request);
            FileItemStream item = iterator.next();
            byte[] data = fileItemStreamToByteArray(item, 3);
            UploadedCertifications certification = new UploadedCertifications();
            certification.setCandidateID(candidateID);
            certification.setFileFormat(getFileType(item));
            certification.setData(data);
            List<UploadedCertifications> userCertifications = candidate.getUploadedCertifications();
            if (userCertifications == null) {
                userCertifications = new ArrayList<UploadedCertifications>();
            }
            userCertifications.add(certification);
            candidate.setUploadedCertifications(userCertifications);
            candidate = CandidatePersistence.merge(candidate);
            session.setAttribute("resumeSuccess", "File uploaded!");
            session.setAttribute("resumeError", "");
            session.setAttribute("user", candidate);
        } catch (InvalidFormatException e) {
            session.setAttribute("resumeSuccess", "");
            session.setAttribute("resumeError", "Please upload a file in PDF, PNG or JPG/JPEG format.");
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("resumeSuccess", "");
            session.setAttribute("resumeError", "General Error");
        } finally {
            response.sendRedirect("candidate-certification.jsp");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request, response);
    }
}
