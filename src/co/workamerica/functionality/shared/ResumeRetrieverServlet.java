package co.workamerica.functionality.shared;

//This servlet downloads the candidate's resume 
import co.workamerica.entities.candidates.Candidates;
import org.apache.tika.detect.DefaultDetector;
import org.apache.tika.detect.Detector;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.mime.MimeType;
import org.apache.tika.mime.MimeTypes;

import javax.persistence.EntityManager;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

/**
 * Servlet implementation class ResumeRetrieverServlet
 */
@WebServlet("/ResumeRetrieverServlet")
public class ResumeRetrieverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		HttpSession session = request.getSession();

		String source = request.getParameter("source");
		
		// Pulls the candidateID of the candidate to retrieve resume
		int candidateID;

		if (session.getAttribute("candidateID") == null) {
			candidateID = Integer.parseInt(request.getParameter("candidateID"));
		} else {
			candidateID = (int) session.getAttribute("candidateID");
		}

		// Creates EntityManager to query database
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		// Retrieves user from database based on userID
		Candidates candidate = em.find(Candidates.class, candidateID);

		// Retrieves resume from candidate's profile.
		byte[] resumeBlob = candidate.getResume();

		// Checks for lack of resume
		if (resumeBlob == null) {
			System.out.print("No Resume");
			session.setAttribute("resumeError", "Candidate resume unavailable");
			if (source == null) {
				response.sendRedirect("candidate-profile.jsp");
			} else if (source.equals("admin")) {
				response.sendRedirect("admin/admin-candidate-profile.jsp");
			} else if (source.equals("school"))  {
				response.sendRedirect("school/school-candidate-profile.jsp");
			} else if (source.isEmpty() || source.equals("client")) {
				response.sendRedirect("candidate-profile.jsp");
			} else if (source.equals("candidate")) {
				response.sendRedirect("candidate-landing.jsp");
			}
		} else {

			//Uses APACHE Tika api to obtain MIMETYPE
			String mimeType = "";
			MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
			final Detector DETECTOR = new DefaultDetector(allTypes);
			MimeType extension = null;

			TikaInputStream tikaIS = null;
			try {
				tikaIS = TikaInputStream.get(resumeBlob);
				final Metadata metadata = new Metadata();
				mimeType = DETECTOR.detect(tikaIS, metadata).toString();
				extension = allTypes.forName(mimeType);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error getting MIME type");
			}

			response.setContentType(mimeType);
			response.setHeader("Content-Disposition", "attachment;filename=" + candidate.getFirstName() + "_"
					+ candidate.getLastName() + "_Resume" + extension.getExtension());

			byte[] buffer = new byte[4096];
			int bytesRead = -1;
			OutputStream out = response.getOutputStream();
			InputStream in = new ByteArrayInputStream(resumeBlob);

			try {
				while ((bytesRead = in.read(buffer)) != -1 ) {
					out.write(buffer, 0, bytesRead);
				}
				
				in.close();
				out.flush();
				out.close();

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error downloading resume");
			}

		}

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}

}
