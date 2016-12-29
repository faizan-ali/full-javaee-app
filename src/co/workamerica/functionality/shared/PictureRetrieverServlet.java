package co.workamerica.functionality.shared;

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
import java.io.IOException;
/**
 * Servlet implementation class PictureRetrieverServlet
 */
@WebServlet("/images/*")
public class PictureRetrieverServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		HttpSession session = request.getSession();

		// Pulls the candidateID of the candidate to retrieve photo of
		int candidateID = (int) session.getAttribute("candidateID");

		// Creates EntityManager to query database
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		// Retrieves user from database based on userID
		Candidates candidate = em.find(Candidates.class, candidateID);

		// Retrieves resume from candidate's profile.
		byte[] pictureBlob = candidate.getPhoto();

		// If photo exists
		if (pictureBlob != null) {

			// Uses APACHE Tika api to obtain MIMETYPE
			String mimeType = "";
			MimeTypes allTypes = MimeTypes.getDefaultMimeTypes();
			final Detector DETECTOR = new DefaultDetector(allTypes);
			MimeType extension = null;

			TikaInputStream tikaIS = null;
			try {
				tikaIS = TikaInputStream.get(pictureBlob);
				final Metadata metadata = new Metadata();
				mimeType = DETECTOR.detect(tikaIS, metadata).toString();
				extension = allTypes.forName(mimeType);
			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("Error getting MIME type");
			}

			// Tells web-page to prepare and download a picture file
			response.setContentType(mimeType);
			response.setContentLength(pictureBlob.length);
			response.getOutputStream().write(pictureBlob);
			response.setHeader("Content-Disposition", "attachment;filename=" + candidateID + extension);
		}
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
