package co.workamerica.functionality.candidates.resume;

import co.workamerica.entities.candidates.Candidates;
import com.itextpdf.text.DocumentException;
import com.itextpdf.text.pdf.AcroFields;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.PdfStamper;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;

/**
 * Servlet implementation class ResumeGeneratorServlet
 */
@WebServlet("/ResumeGeneratorServlet")
public class ResumeGeneratorServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

    public ResumeGeneratorServlet() {
        super();
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		HttpSession session = request.getSession();
		
		//Gets full path to the template file
		String inPath = this.getServletContext().getRealPath("/WEB-INF/template/resumeTemplate.pdf");
		
		//Gets full path to the Servlet temporary directory
		File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
		
		//Creates a temporary file to store the generated resume in the temporary directory
		File tempFile = File.createTempFile("xxx", ".pdf", tempDir);
				
		//Pulls Candidates object from local session
		Candidates user = (Candidates) session.getAttribute("user");
		
		try {			
			//iText
			PdfReader reader = new PdfReader(inPath);
			FileOutputStream fileOut = new FileOutputStream(tempFile);
			PdfStamper stamper = new PdfStamper(reader, fileOut);
			AcroFields form = stamper.getAcroFields();
			
			//form.getFields().keySet().forEach(name -> System.out.println(name));
			form.removeXfa();
			form.setField("Text1", "TESTESTSTS");
			
			stamper.close();
			reader.close();			
		} catch (DocumentException e) {
			e.printStackTrace();
		}
		
		//Converts the spreadsheet to a byte array
		File file = new File(tempFile.getAbsolutePath());
		byte [] array = new byte [(int)file.length()];
		FileInputStream fileInputStream = null;
		
		try {
		    fileInputStream = new FileInputStream(file);
		    fileInputStream.read(array);
		    fileInputStream.close();
		} catch(Exception e){
        	e.printStackTrace();
        	System.out.println("Error converting to byte array");
        }
		
		//Tells web-page to download a PDF 
		response.setContentType(this.getServletContext().getMimeType(tempFile.getAbsolutePath()));
		response.setCharacterEncoding("UTF-8");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename=" + user.getFirstName() + "_" + user.getLastName() + "_" + "Resume.pdf"); 
		
		byte[] output = new byte[4096];
		ServletOutputStream out = response.getOutputStream();
		InputStream in = new ByteArrayInputStream(array);
		int bytesRead = -1;

		try {
			while ((bytesRead = in.read(output)) != -1) {
				out.write(output, 0, bytesRead);
			}
			in.close();
			out.flush();
			out.close();

		} catch (Exception e) {
			e.printStackTrace();
			System.out.println("Error downloading pipeline");
		}	
		
		response.sendRedirect("candidate-landing.jsp");
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
