package co.workamerica.functionality.schools;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.logs.schools.SchoolActivityLogs;
import co.workamerica.entities.logs.schools.SchoolLoginLogs;
import co.workamerica.functionality.persistence.SchoolAccountDataAccessObject;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

/**
 * Servlet implementation class PictureUploadServlet
 */
@WebServlet("/SchoolPictureUploadServlet")
@MultipartConfig
public class SchoolPictureUploadServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        HttpSession session = request.getSession();
        int candidateID = (int) session.getAttribute("candidateID");

        Candidates candidate = em.find(Candidates.class, candidateID);

        ServletFileUpload upload = new ServletFileUpload();

        try {
            FileItemIterator iterator = upload.getItemIterator(request);

            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                if (item.getName() != null) {
                    if (item.getName().toLowerCase().contains(".jpg") || item.getName().toLowerCase().contains(".jpeg")
                            || item.getName().toLowerCase().contains(".png")) {
                        byte[] file = IOUtils.toByteArray(item.openStream());
                        candidate.setPhoto(file);
                        trans.begin();
                        candidate = em.merge(candidate);
                        trans.commit();

                        session.setAttribute("resume", "true");
                        session.setAttribute("resumeSuccess", "Picture uploaded!");
                        session.setAttribute("candidate", candidate);

                        // For logging activity
                        SchoolLoginLogs loginLog = (SchoolLoginLogs) session.getAttribute("loginLog");
                        if (loginLog != null) {
                            SchoolActivityLogs activityLog = new SchoolActivityLogs();
                            activityLog.setSchoolLoginLogID(loginLog.getSchoolLoginLogID());
                            activityLog.setTime(Clock.getCurrentTime());
                            activityLog.setCandidateID(candidateID);
                            activityLog.setCandidate(candidate);
                            activityLog.setSchoolActivity("Uploaded Picture");
                            SchoolAccountDataAccessObject.persistActivityLog(activityLog);
                        }

                        session.setAttribute("resume", "true");
                        session.setAttribute("resumeSuccess", "Picture uploaded!");
                        session.setAttribute("candidate", candidate);
                        response.sendRedirect("schools/school-candidate-profile.jsp");
                    } else {
                        String resumeError = "Please upload a picture in PNG or JPG/JPEG format.";
                        session.setAttribute("resume", "true");
                        session.setAttribute("resumeError", resumeError);
                        response.sendRedirect("schools/school-candidate-profile.jsp");
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            session.setAttribute("resumeError", "An error occurred.");
            response.sendRedirect("schools/school-candidate-profile.jsp");
        } finally {
            em.close();
        }
    }

}
