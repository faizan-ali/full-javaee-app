package co.workamerica.functionality.candidates.misc;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.criteria.Certifications;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.criteria.States;
import co.workamerica.entities.logs.candidates.CandidateLoginLogs;
import co.workamerica.functionality.persistence.*;
import co.workamerica.functionality.sendgrid.API.SendGridContacts;
import co.workamerica.functionality.sendgrid.API.SendGridObject;
import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

@WebServlet(name = "CandidateRegisterServlet", urlPatterns = {"/candidate-register", "/CandidateRegisterServlet"})
public class CandidateRegisterServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final Logger logger = LoggerFactory.getLogger(HttpServlet.class);

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.removeAttribute("signupError");

        String firstName = request.getParameter("firstName") != null ? request.getParameter("firstName") : "",
                lastName = request.getParameter("lastName") != null ? request.getParameter("lastName") : "",
                email = request.getParameter("email") != null ? request.getParameter("email") : "",
                password = request.getParameter("password") != null ? request.getParameter("password") : "",
                referral = request.getParameter("referral"), os = request.getParameter("os"), osVersion = request.getParameter("osVersion"),
                browser = request.getParameter("browser"), browserVersion = request.getParameter("browserVersion"),
                device = request.getParameter("device"), deviceType = request.getParameter("deviceType"),
                deviceVendor = request.getParameter("deviceVendor"),
                phone = request.getParameter("phone") != null ? CustomUtilities.cleanNumber(request.getParameter("phone").trim()) : "",
                zip = request.getParameter("zip") != null ? request.getParameter("zip").trim() : "";

        HashMap<String, String> details = new HashMap<String, String>();


        if (firstName.isEmpty() || lastName.isEmpty()) {
            response.sendRedirect("http://www.workamerica.co/join-now.html?invalidName=true&firstName=" +
                    firstName + "&lastName=" + lastName + "&email=" + email + "&phone=" + phone + "&zip=" + zip);
        } else if (!email.contains("@") || !email.contains(".")) {
            response.sendRedirect("http://www.workamerica.co/join-now.html?invalidEmail=true&firstName=" +
                    firstName + "&lastName=" + lastName + "&email=" + email + "&phone=" + phone + "&zip=" + zip);
        } else if (CandidatePersistence.checkIfExistsByEmail(email)) {
            response.sendRedirect("http://www.workamerica.co/join-now.html?exists=true&firstName=" +
                    firstName + "&lastName=" + lastName + "&email=" + email + "&phone=" + phone + "&zip=" + zip);
        } else if (!CustomUtilities.isValidZip(zip)) {
            response.sendRedirect("http://www.workamerica.co/join-now.html?invalidZip=true&firstName=" +
                    firstName + "&lastName=" + lastName + "&email=" + email + "&phone=" + phone + "&zip=" + zip);
        } else if (!CustomUtilities.isValidNumber(phone)) {
            response.sendRedirect("http://www.workamerica.co/join-now.html?invalidPhone=true&firstName=" +
                    firstName + "&lastName=" + lastName + "&email=" + email + "&phone=" + phone + "&zip=" + zip);
        } else {
            try {
                int sourceID = SchoolDataAccessObject.getSchoolIDByName("Website");
                details.put("firstName", firstName);
                details.put("lastName", lastName);
                details.put("email", email);
                details.put("referral", referral);
                details.put("approved", "No");
                details.put("authorized", "Yes");
                details.put("password", password);

                details.put("zip", zip);
                details.put("phone", phone);

                details.put("workAmericaCreated", "No");
                details.put("accountOriginMarketingCampaign", "");
                details.put("accountOriginKeywords", "");
                details.put("accountOriginSourceID", sourceID + "");
                details.put("accountOriginDevice", "");
                details.put("accountOriginBrowser", "");
                details.put("accountOriginDate", Clock.getCurrentDate());
                details.put("accountOriginTime", Clock.getCurrentTime());
                details.put("accountOriginBrowser", browser);
                details.put("accountOriginBrowserVersion", browserVersion);
                details.put("accountOriginOS", os);
                details.put("accountOriginOSVersion", osVersion);
                details.put("accountOriginDevice", device);
                details.put("accountOriginDeviceType", deviceType);
                details.put("accountOriginDeviceVendor", deviceVendor);

                Candidates user = CandidatePersistence.createCandidate(details);

                List<States> stateList = StateDataAccessObject.getAll();
                List<Fields> fieldList = FieldPersistence.getAll();
                List<Schools> schoolList = SchoolDataAccessObject.getAll();
                List<Certifications> certificationList = CertificationPersistence.getAll();

                CandidateLoginLogs loginLog = CandidatePersistence.addLoginLog(user);

                try {
                    SendGridContacts.add(email, firstName, lastName, zip, Clock.getCurrentDate(), "", "No", Clock.getCurrentDate(), "Website", "None",
                            user.getCandidateID(), "", "");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                // SendGrid welcome e-mail
                try {
                    SendGridObject.sendEmail(email, password, firstName, "Campaign");
                } catch (Exception e) {
                    e.printStackTrace();
                }

                session.setAttribute("candidateID", user.getCandidateID());
                session.setAttribute("loginLog", loginLog);
                session.setAttribute("fieldList", fieldList);
                session.setAttribute("stateList", stateList);
                session.setAttribute("schoolList", schoolList);
                session.setAttribute("certificationList", certificationList);
                session.setAttribute("user", user);
                response.sendRedirect("candidate-landing.jsp");
            } catch (Exception e) {
                e.printStackTrace();
                logger.error("Candidate registration bug", e);
                session.setAttribute("signupError", "An error occured. Please try again soon.");
                response.sendRedirect("http://www.workamerica.co/join-now.html?error=true&firstName=" +
                        firstName + "&lastName=" + lastName + "&email=" + email);
            }
        }
    }
}
