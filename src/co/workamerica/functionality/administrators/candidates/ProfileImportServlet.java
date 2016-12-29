package co.workamerica.functionality.administrators.candidates;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.candidates.jsonModels.accountOrigin.AccountOrigin;
import co.workamerica.functionality.google.api.Geocode;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.CertificationPersistence;
import co.workamerica.functionality.persistence.FieldPersistence;
import co.workamerica.functionality.persistence.SchoolDataAccessObject;
import co.workamerica.functionality.sendgrid.API.SendGridContacts;
import co.workamerica.functionality.sendgrid.API.SendGridObject;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import co.workamerica.functionality.twilio.API.Twilio;
import com.twilio.sdk.TwilioRestException;
import org.apache.commons.fileupload.FileItemIterator;
import org.apache.commons.fileupload.FileItemStream;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.apache.commons.io.IOUtils;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.util.*;

/**
 * For testing, comment out entire MailChimp try/catch block
 */
@WebServlet("/ProfileImportServlet")
@MultipartConfig
public class ProfileImportServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public ProfileImportServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doPost(request, response);
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {

        HttpSession session = request.getSession();
        session.removeAttribute("invalidCandidates");

        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();

        // Records whether a candidate has erroneous information
        boolean error = false;

        // Error strings
        String formatError = "Please upload a file in .xslm format. Code: Format-Error",
                genericError = "An error occured, please contact developer. Code: ",
                headerError = "Your template file has invalid headers (First row)";

        // Request parameters
        String sourceType = request.getParameter("type"), teamMember = request.getParameter("teamMember"),
                date = request.getParameter("date").trim();
        // Contains first and last name of candidates mapped to list of
        // erroneous information
        HashMap<String, ArrayList<String>> invalidCandidates = new HashMap<String, ArrayList<String>>();

        // Candidates that were successfully added to database
        ArrayList<Candidates> importedCandidates = new ArrayList<Candidates>();

        CustomUtilities custom = new CustomUtilities();

        // Contains uploaded spreadsheet
        byte[] byteArray = null;

        XSSFWorkbook workbook = null;

        try {
            // Loads criteria lists from session for data validation then creates
            // new lists based on criteria names
            List<String> schoolNames = SchoolDataAccessObject.getAllNames();
            List<String> fieldNames = FieldPersistence.getAllNames();
            List<String> certificationNames = CertificationPersistence.getAllNames();

            /**** Reading in uploaded spreadsheet ****/
            ServletFileUpload upload = new ServletFileUpload();

            FileItemIterator iterator = upload.getItemIterator(request);

            while (iterator.hasNext()) {
                FileItemStream item = iterator.next();
                if (item.getName() != null) {
                    if (item.getName().contains(".xlsm")) {
                        byteArray = IOUtils.toByteArray(item.openStream());
                    } else {
                        session.setAttribute("importError", formatError);
                        response.sendRedirect("admin/admin-profile-import.jsp");
                    }
                }
            }

            // Opening inputstream to read byte array containing spreadsheet
            // into Apache POI
            Part filePart = request.getPart("spreadsheet");
            InputStream inputStream = filePart.getInputStream();

            // Get the workbook instance for XLS file
            workbook = new XSSFWorkbook(inputStream);

            // Get first sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            /**** Checking if headers are present for validation ****/

            // Arraylist contains all headers
            ArrayList<String> headers = new ArrayList<String>(Arrays.asList("First Name", "Last Name", "City", "State",
                    "ZIP", "E-mail", "Phone", "Alternate Phone", "School", "Field", "Anticipated Certification",
                    "Completion Date", "Past Field", "Obtained Certification", "Past Education", "Work Experience",
                    "Veteran", "Employed", "Relocate", "Additional Information", "Ethnicity", "Authorized",
                    "Driver's License"));

            // Check first row (contains headers)
            Row firstRow = sheet.getRow(0);
            Iterator<Cell> firstRowIterator = firstRow.cellIterator();

            // Iterating through first row. Throws exception if invalid header
            // encountered.
            while (firstRowIterator.hasNext()) {
                Cell cell = firstRowIterator.next();

                if (!headers.contains(cell.getStringCellValue())) {
                    throw new UnsupportedOperationException();
                }
            }

            /**** Iterating through rows in spreadsheet ****/

                // Get iterator to all the rows in current sheet
                Iterator<Row> rowIterator = sheet.iterator();

                // Moves past row containing headers
                rowIterator.next();

                // Iterating through rows
                while (rowIterator.hasNext()) {
                    error = false;

                    Row row = rowIterator.next();

                    if (row.getCell(0) == null) {
                        break;
                    }

                DataFormatter format = new DataFormatter();

                // Populates Strings with their values from each cell in the row
                String firstName = row.getCell(0, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        lastName = row.getCell(1, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        city = row.getCell(2, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        state = row.getCell(3, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        zip = format.formatCellValue(row.getCell(4, Row.CREATE_NULL_AS_BLANK)),
                        email = row.getCell(5, Row.CREATE_NULL_AS_BLANK).getStringCellValue().toLowerCase().trim(),
                        phone = format.formatCellValue(row.getCell(6, Row.CREATE_NULL_AS_BLANK)),
                        alternatePhone = format.formatCellValue(row.getCell(7, Row.CREATE_NULL_AS_BLANK)),
                        school = row.getCell(8, Row.CREATE_NULL_AS_BLANK).getStringCellValue(), fields = row.getCell(9, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        anticipatedCertifications = row.getCell(10, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        completionDate = format.formatCellValue(row.getCell(11, Row.CREATE_NULL_AS_BLANK)),
                        pastFields = row.getCell(12, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        obtainedCertifications = row.getCell(13, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        pastEducation = row.getCell(14, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        workExperience = row.getCell(15, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        veteran = row.getCell(16, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        employed = row.getCell(17, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        relocate = row.getCell(18, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        additionalInformation = row.getCell(19, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        authorized = row.getCell(20, Row.CREATE_NULL_AS_BLANK).getStringCellValue(),
                        license = row.getCell(21, Row.CREATE_NULL_AS_BLANK).getStringCellValue();


                // Splits Strings with potential multiple comma seperated fields
                // into String Arrays
                String[] fieldArray = fields.split(","), pastFieldArray = pastFields.split(","),
                        obtainedCertificationArray = obtainedCertifications.split(","),
                        anticipatedCertificationArray = anticipatedCertifications.split(","),
                        pastEducationArray = pastEducation.split(",");

                String[] latLong = new String[2];

                if (phone != null && !phone.isEmpty()) {
                    phone = CustomUtilities.cleanNumber(phone);
                }

                if (alternatePhone != null && !alternatePhone.isEmpty()) {
                    alternatePhone = CustomUtilities.cleanNumber(alternatePhone);
                }

                if (lastName == null) {
                    lastName = "";
                }

                if (workExperience.isEmpty()) {
                    workExperience = "No relevant work experience";
                }
                // Transforming single response answers
                if (veteran.contains("Y") || veteran.contains("y")) {
                    veteran = "Yes";
                } else if (veteran.contains("N") || veteran.contains("n")) {
                    veteran = "No";
                } else if (veteran.isEmpty()) {
                    veteran = "No";
                } else {
                    veteran = "X";
                }
                if (employed.contains("Y") || employed.contains("y")) {
                    employed = "Yes";
                } else if (employed.contains("N") || employed.contains("n")) {
                    employed = "No";
                } else if (employed.isEmpty()) {
                    employed = "No";
                } else {
                    employed = "X";
                }
                if (relocate.contains("Y") || relocate.contains("y")) {
                    relocate = "Yes";
                } else if (relocate.contains("N") || relocate.contains("n")) {
                    relocate = "No";
                } else if (relocate.isEmpty()) {
                    relocate = "";
                } else {
                    relocate = "X";
                }
                if (authorized.contains("Y") || authorized.contains("y") || authorized.isEmpty()) {
                    authorized = "Yes";
                } else if (authorized.contains("N") || authorized.contains("n")) {
                    authorized = "No";
                } else {
                    authorized = "X";
                }
                if (license.contains("Y") || license.contains("y")) {
                    license = "Yes";
                } else if (license.contains("N") || license.contains("n")) {
                    license = "No";
                } else if (license.isEmpty()) {
                    license = "";
                } else {
                    license = "X";
                }

                // Building lists for validation
                ArrayList<String> workExperienceList = new ArrayList<String>(
                        Arrays.asList("No relevant work experience", "Less than 1 year of work experience",
                                "1-3 years of work experience", "3-5 years of work experience",
                                "More than 5 years of work experience"));
                ArrayList<String> pastEducationList = new ArrayList<String>(
                        Arrays.asList("Some high school; no diploma", "High school diploma or GED",
                                "Some trade/technical/vocational training; no certificate",
                                "Completed trade/technical/vocational training certificate or diploma",
                                "Some college credit; no degree", "Associates degree", "Bachelors degree or more"));

                // Populates hashMap with empty ArrayList
                invalidCandidates.put(firstName + " " + lastName, new ArrayList<String>());

                // Data validation

                // Duplicate check
                if (email.length() > 2 && CandidatePersistence.checkIfExistsByEmail(email)) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Candidate exists: " + email);
                }

                if (firstName.length() < 2) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid name: " + firstName);
                }
                if (city != null && !city.isEmpty() && city.length() < 2) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid city: " + city);
                } else if (city == null || city.isEmpty()) {
                    city = "";
                }
                if (state.length() != 2) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid state: " + state);
                } else if (state.isEmpty()) {
                    state = "";
                }
                if (zip.length() > 1 && zip.length() < 5) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid ZIP: " + zip);
                }
                if (!email.isEmpty() && !email.contains("@")) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid e-mail: " + email);
                }
                if (phone.length() != 0 && phone.length() != 10) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid phone: " + phone);
                }
                if (alternatePhone.length() != 0 && alternatePhone.length() != 10) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid alternate phone: " + alternatePhone);
                }
                if (school.length() < 2 || !schoolNames.contains(school)) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid school: " + school);
                }
                if (completionDate.length() < 2 || !completionDate.matches("\\d{1,2}/\\d{1,2}/\\d{2,4}")) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid date: " + completionDate);
                }
                if (veteran.equals("X")) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid veteran status: " + veteran);
                }

                if (employed.equals("X")) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid employed status: " + employed);
                }
                if (relocate.equals("X")) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid relocate status: " + relocate);
                }
                if (authorized.equals("X")) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid authorized status: " + authorized);
                }
                if (license.equals("X")) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid license status: " + license);
                }

                if (fields.length() != 0) {
                    for (String s : fieldArray) {
                        if (!fieldNames.contains(s.trim())) {
                            error = true;
                            invalidCandidates.get(firstName + " " + lastName)
                                    .add("Invalid field: " + s);
                        }
                    }
                }

                if (pastFields.length() != 0) {
                    for (String s : fieldArray) {
                        if (!fieldNames.contains(s.trim())) {
                            error = true;
                            invalidCandidates.get(firstName + " " + lastName)
                                    .add("Invalid past field: " + s);
                        }
                    }
                }

                if (anticipatedCertifications.length() != 0) {
                    for (String s : anticipatedCertificationArray) {
                        if (!certificationNames.contains(s.trim())) {
                            error = true;
                            invalidCandidates.get(firstName + " " + lastName)
                                    .add("Invalid anticipated certification: " + s);
                        }
                    }
                }
                if (obtainedCertifications.length() != 0) {
                    for (String s : obtainedCertificationArray) {
                        if (!certificationNames.contains(s.trim())) {
                            error = true;
                            invalidCandidates.get(firstName + " " + lastName)
                                    .add("Invalid obtained certification: " + s);
                        }
                    }
                }

                if (!pastEducation.isEmpty() && !pastEducationList.containsAll(Arrays.asList(pastEducationArray))) {
                    for (String s : pastEducationArray) {
                        if (!pastEducationList.contains(s.trim())) {
                            error = true;
                            invalidCandidates.get(firstName + " " + lastName).add("Invalid past education: " + s);
                        }
                    }
                }
                if (!workExperienceList.contains(workExperience)) {
                    error = true;
                    invalidCandidates.get(firstName + " " + lastName).add("Invalid work experience");
                }

                if (workExperience != null && workExperience.length() < 3) {
                    workExperience = "No relevant work experience";
                }

                // Creates Candidates entity and persists if no info. error
                if (!error) {
                    int schoolID = SchoolDataAccessObject.getSchoolIDByName(school);
                    AccountOrigin origin = new AccountOrigin();

                    origin.setDateAcquired(Clock.getCurrentDate());
                    origin.setTimeAcquired("N/A");
                    origin.setWorkAmericaCreated("Yes");
                    origin.setSourceID(schoolID + "");
                    origin.setSource(school);
                    origin.setAdminConsoleMethod("BulkUpload");
                    origin.setTeamMember(teamMember);
                    origin.setSourceType(sourceType);

                    Candidates candidate = new Candidates();
                    candidate.setFirstName(firstName);
                    candidate.setLastName(lastName);
                    candidate.setCity(city);
                    candidate.setState(state);
                    candidate.setZip(zip);
                    candidate.setEmail(email);
                    candidate.setPhone(phone);
                    candidate.setAlternatePhone(alternatePhone);
                    candidate.setPastEducation(pastEducation.replace(", ", ","));
                    candidate.setWorkExperience(workExperience);
                    candidate.setVeteran(veteran);
                    candidate.setEmployed(employed);
                    candidate.setRelocate(relocate);
                    candidate.setAdditionalInformation(additionalInformation);
                    candidate.setAuthorized(authorized);
                    candidate.setSchool(school);
                    candidate.setSchoolID(schoolID);

                    candidate.setDateCreated(Clock.getCurrentDate());
                    candidate.setTimeCreated(Clock.getCurrentTime());
                    candidate.setApproved("Yes");
                    candidate.setWorkAmericaCreated("Yes");
                    candidate.setValidDriversLicense(license);

                    candidate.setField(fields.replace(", ", ","));
                    candidate.setAnticipatedCertification(anticipatedCertifications.replace(", ", ","));
                    candidate.setPastField(pastFields.replace(", ", ","));
                    candidate.setObtainedCertification(obtainedCertifications.replace(", ", ","));
                    candidate.setCompletionDate(completionDate);

                    candidate.setAccountOrigin(origin);

                    String password = firstName;

                    if (phone.isEmpty()) {
                        password += "5555";
                    } else {
                        password += phone.substring(6, 10);
                    }

                    String[] passwordArray = custom.hashPassword(password, null);
                    candidate.setPassword(passwordArray[0]);
                    candidate.setSalt(passwordArray[1]);

                    // Geocode
                    try {
                        if (zip != null && !zip.isEmpty()) {
                            latLong = Geocode.toGeocode(zip);

                            if (latLong[0] == null || latLong[1] == null || latLong[0].isEmpty()
                                    || latLong[1].isEmpty()) {
                                latLong = Geocode
                                        .toGeocode(city.replaceAll("\\s", "+") + "+" + state.replaceAll("\\s", "+"));
                            }
                        } else if (city != null && city.length() > 2 && state != null && state.length() > 1) {
                            latLong = Geocode
                                    .toGeocode(city.replaceAll("\\s", "+") + "+" + state.replaceAll("\\s", "+"));
                        } else {
                            latLong[0] = "";
                            latLong[1] = "";
                        }

                    } catch (Exception e) {
                        latLong[0] = "";
                        latLong[1] = "";
                        e.printStackTrace();
                    }
                    candidate.setLatitude(latLong[0]);
                    candidate.setLongitude(latLong[1]);
                    importedCandidates.add(candidate);
                }
            }
            inputStream.close();
        } catch (UnsupportedOperationException e) {
            e.printStackTrace();
            System.out.println("Invalid headers");
            session.setAttribute("importError", headerError);
            response.sendRedirect("admin/admin-profile-import.jsp");
            return;
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println("File not found");
            session.setAttribute("importError", "File not found. Code: " + e.getClass().getSimpleName());
            response.sendRedirect("admin/admin-profile-import.jsp");
            return;
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println("IOException");
            session.setAttribute("importError", "Error reading file. Code: " + e.getClass().getSimpleName());
            response.sendRedirect("admin/admin-profile-import.jsp");
            return;
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("Error uploading file");
            session.setAttribute("importError", genericError + e.getClass().getSimpleName());
            response.sendRedirect("admin/admin-profile-import.jsp");
            return;
        }

        for (Candidates candidate : importedCandidates) {
            String firstName = candidate.getFirstName(), lastName = candidate.getLastName(),
                    email = candidate.getEmail(), phone = candidate.getPhone(), password = "",
                    school = candidate.getSchool();

            if (!firstName.isEmpty()) {
                password = firstName;
            } else if (!lastName.isEmpty()) {
                password = lastName;
            } else {
                password = "Stark";
            }

            if (phone == null) {
                password += "5555";
            } else if (phone.isEmpty()) {
                password += "5555";
            } else {
                password += phone.substring(6, 10);
            }

            try {
                trans.begin();
                em.persist(candidate);
                trans.commit();


                try {
                    if (email.length() > 5) {
                        SendGridContacts.add(email, firstName, lastName, candidate.getZip(), Clock.getCurrentDate(), school, "Yes",
                                Clock.getCurrentDate(), "AdminConsole", "None", candidate.getCandidateID(), candidate.getField(), candidate.getPastEducation().trim()
                        );
                    }


                    // Twilio text
                    try {
                        boolean sent = Twilio.sendText(phone, email, password, firstName, "Welcome");
                        if (!sent) {
                            invalidCandidates.get(firstName + " " + lastName)
                                    .add("Failed to send text but profile imported: " + phone);
                        }
                    } catch (TwilioRestException e) {
                        e.printStackTrace();
                        invalidCandidates.get(firstName + " " + lastName)
                                .add("Failed to send text but profile imported: " + phone);
                    } catch (Exception e) {
                        e.printStackTrace();
                        invalidCandidates.get(firstName + " " + lastName)
                                .add("Failed to send text but profile imported: " + phone);
                    }

                    // SendGrid welcome e-mail
                    try {
                        if (email.length() > 5) {
                            boolean sent = SendGridObject.sendEmail(email, password, firstName, "Welcome");
                            if (!sent) {
                                invalidCandidates.get(firstName + " " + lastName)
                                        .add("Failed to send welcome e-mail but profile imported: " + email);
                            }
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                        invalidCandidates.get(firstName + " " + lastName)
                                .add("Failed to send welcome e-mail but profile imported: " + email);
                    }

                } catch (Exception e) {
                    e.printStackTrace();
                    invalidCandidates.get(firstName + " " + lastName)
                            .add("Failed to add to MailChimp, SendGrid, Twilio but profile imported: " + email);
                }

            } catch (Exception e) {
                e.printStackTrace();
                invalidCandidates.get(firstName + " " + lastName)
                        .add("Failed to import. No text/e-mail sent. Code: " + e);
            }

        }
        System.out.println(importedCandidates.size());
        session.setAttribute("invalidCandidates", invalidCandidates);
        session.setAttribute("importedCandidates", importedCandidates);
        response.sendRedirect("admin/admin-profile-import.jsp");
    }
}
