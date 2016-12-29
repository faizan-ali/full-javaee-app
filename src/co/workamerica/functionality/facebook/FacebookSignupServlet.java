package co.workamerica.functionality.facebook;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * Servlet implementation class FacebookSignupServlet
 */
@WebServlet("/FacebookSignupServlet")
public class FacebookSignupServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private final String appID = "";
	private final String permissions = "public_profile,email,user_friends";

	public FacebookSignupServlet() {
		super();
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		StringBuffer callbackURL = request.getRequestURL();
		int index = callbackURL.lastIndexOf("/");
		callbackURL.replace(index, callbackURL.length(), "").append("/FacebookCallbackServlet");
		response.sendRedirect(
				"https://www.facebook.com/dialog/oauth?"
				+ "client_id=" + appID + "&"
				+ "scope=" + permissions + "&" 
				+ "redirect_uri=" + callbackURL.toString());
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		doGet(request, response);
	}


	public String getOauthUrl(String clientId, String callbackUrl, String scope) {
		return "https://graph.facebook.com/oauth/authorize?client_id="
				+ clientId + "&display=page&redirect_uri="
				+ callbackUrl + "&scope=" + scope;
	}

}
