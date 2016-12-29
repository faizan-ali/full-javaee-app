package co.workamerica.functionality.clients.pipeline;

import co.workamerica.entities.clients.ClientPipelines;
import co.workamerica.entities.clients.Clients;
import co.workamerica.functionality.shared.EMFUtil;
import net.sf.jett.transform.ExcelTransformer;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.persistence.EntityManager;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.*;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * Servlet implementation class ExportServlet
 */
@WebServlet("/ExportServlet")
public class ExportServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();
		HttpSession session = request.getSession();
		int clientID = (int) session.getAttribute("clientID");
		Clients client = em.find(Clients.class, clientID);
		Set<ClientPipelines> pipeline = client.getPipeline();
		
		//Gets full path to the template file
		String inPath = this.getServletContext().getRealPath("/WEB-INF/template/template.xls");
		
		//Gets full path to the Servlet temporary directory
		File tempDir = (File) getServletContext().getAttribute("javax.servlet.context.tempdir");
		
		//Creates a temporary file to store the pipeline spreadsheet in the temporary directory
		File tempFile = File.createTempFile("xxx", ".xls", tempDir);
		
		String outPath = ServletContext.TEMPDIR;
		Map<String, Object> beans = new HashMap<String, Object>();
		beans.put("pipeline", pipeline);
				
		//Saves the employer's Pipeline to a spreadsheet in the WEB-INF folder
		FileOutputStream fileOut = null;
		try	{
		   fileOut = new FileOutputStream(tempFile);
		}
		catch (IOException e)	{
		   System.err.println("IOException opening " + outPath + ": " + e.getMessage());
		}
		InputStream fileIn = null;
		try	{
		   fileIn = new BufferedInputStream(new FileInputStream(inPath));
		   ExcelTransformer transformer = new ExcelTransformer();
		   Workbook workbook = transformer.transform(fileIn, beans);
		   workbook.write(fileOut);
		   fileOut.close();
		} catch (Exception e){
			e.printStackTrace();
			logger.error("Pipeline export bug", e);
		}
		
		//Tells web-page to download an Excel file 
		response.setContentType(this.getServletContext().getMimeType(tempFile.getAbsolutePath()));
		response.setCharacterEncoding("UTF-8");
		response.setContentLength((int) tempFile.length());
		response.setHeader("Content-Disposition", "attachment; filename="+client.getCompany()+"_"+client.getLastName()+"_Pipeline.xls"); 
		
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
			logger.error("Pipeline export bug", e);
        }
		
		//Prepares byte array for download
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
			logger.error("Pipeline export bug", e);
		}	
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}