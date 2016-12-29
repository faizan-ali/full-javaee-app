package co.workamerica.functionality.persistence;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.candidates.jsonModels.accountOrigin.AccountOrigin;
import co.workamerica.entities.logs.candidates.CandidateActivityLogs;
import co.workamerica.entities.logs.candidates.CandidateLoginLogs;
import co.workamerica.functionality.google.api.Geocode;
import co.workamerica.functionality.sendgrid.API.SendGridContacts;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;

public class CandidatePersistence {

    // Takes a String array of dates in MM/DD/yyyy format and returns a list of Candidates accounts created on those dates
    public static List<Candidates> getByDates(String[] dates) {
        if (dates != null && dates.length > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT candidate from Candidates candidate where candidate.dateCreated = ";

            for (int i = 0; i < dates.length; i++) {
                if (i == dates.length - 1) {
                    query += "'" + dates[i] + "'";
                } else {
                    query += "'" + dates[i] + "'" + " OR candidate.dateCreated = ";
                }
            }

            try {
                TypedQuery<Candidates> q = em.createQuery(query, Candidates.class);
                return q.getResultList();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static Candidates getCandidateByPhone(String phone) {
        if (phone != null && CustomUtilities.isValidNumber(phone)) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT candidate FROM Candidates candidate WHERE candidate.phone =:phone OR candidate.alternatePhone = :phone";
            try {
                TypedQuery<Candidates> q = em.createQuery(query, Candidates.class);
                q.setParameter("phone", phone);
                return q.getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static Candidates getCandidateByEmail(String email) {
        if (email != null && !email.isEmpty() && CustomUtilities.isValidEmail(email)) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT candidate FROM Candidates candidate WHERE candidate.email =:email";
            try {
                TypedQuery<Candidates> q = em.createQuery(query, Candidates.class);
                q.setParameter("email", email);
                return q.getSingleResult();
            } catch (Exception e) {

            } finally {
                em.close();
            }
        }
        return null;
    }

    public static Candidates updatePassword(Candidates candidate, String password) {
        if (candidate != null && password != null && password.length() > 3) {
            String[] passwordArray = CustomUtilities.hashPassword(password, null);
            candidate.setPassword(passwordArray[0]);
            candidate.setSalt(passwordArray[1]);
            return merge(candidate);
        }
        return null;
    }

    public static List<Candidates> getApprovalList() {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        String query = "SELECT candidate FROM Candidates candidate WHERE candidate.approved = 'No' OR " +
                "candidate.approved IS NULL";
        TypedQuery<Candidates> qCandidate = em.createQuery(query, Candidates.class);
        List<Candidates> candidates = qCandidate.getResultList();
        em.close();
        return candidates;
    }

    public static boolean checkIfExistsByEmail(String email) {
        if (email != null && !email.isEmpty() && email.contains("@")) {
            return getCandidateByEmail(email) != null;
        }
        return false;
    }

    public static List<Candidates> getAll() {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        String candidateQuery = "SELECT candidate FROM Candidates candidate";
        TypedQuery<Candidates> qCandidate = em.createQuery(candidateQuery, Candidates.class);
        List<Candidates> candidates = qCandidate.getResultList();
        em.close();
        return candidates;
    }

    public static Candidates getCandidateByID(String candidateID) {
        if (candidateID != null && !candidateID.isEmpty()) {
            try {
                int parsedID = Integer.parseInt(candidateID);
                return getCandidateByID(parsedID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Candidates getCandidateByID(int candidateID) {
        if (candidateID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            Candidates candidate = em.find(Candidates.class, candidateID);
            em.close();
            return candidate;
        }
        return null;
    }

    public static Candidates login(String username, String password) {
        if (username != null && password != null && !username.isEmpty() && !password.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query;
            TypedQuery<Candidates> q;

            try {
                if (username.contains("@")) {
                    query = "SELECT c FROM Candidates c WHERE c.email = :email";
                    q = em.createQuery(query, Candidates.class);
                    q.setParameter("email", username.toLowerCase().trim());
                } else {
                    query = "SELECT c FROM Candidates c WHERE c.phone = :phone";
                    q = em.createQuery(query, Candidates.class);
                    q.setParameter("phone", CustomUtilities.cleanNumber(username));
                }
                Candidates candidate = q.getSingleResult();
                if (candidate != null && CustomUtilities.hashPassword(password, CustomUtilities.hexToBytes(candidate.getSalt()))[0].equals(candidate.getPassword())) {
                    CandidateLoginLogs loginLog = addLoginLog(candidate);
                    candidate.getLoginLog().add(loginLog);
                    return candidate;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static CandidateLoginLogs addLoginLog(Candidates candidate) {
        if (candidate != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            CandidateLoginLogs loginLog = new CandidateLoginLogs(candidate.getCandidateID(), Clock.getCurrentDate(),
                    Clock.getCurrentTime());
            loginLog.setCandidateID(candidate.getCandidateID());
            try {
                trans.begin();
                em.persist(loginLog);
                trans.commit();
                loginLog.setCandidate(candidate);
                return loginLog;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            }
        }
        return null;
    }

    public static Candidates persist(Candidates candidate) {
        if (candidate != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(candidate);
                trans.commit();
                return candidate;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static Candidates merge(Candidates candidate) {
        if (candidate != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                candidate = em.merge(candidate);
                trans.commit();
                return candidate;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static boolean delete(int candidateID) {
        if (candidateID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            Candidates candidate = em.find(Candidates.class, candidateID);
            if (candidate != null) {
                EntityTransaction trans = em.getTransaction();
                try {
                    trans.begin();
                    em.remove(candidate);
                    trans.commit();
                    return true;
                } catch (Exception e) {
                    e.printStackTrace();
                    trans.rollback();
                } finally {
                    em.close();
                }
            }
        }
        return false;
    }

    public static Candidates updateCandidate(HashMap<String, String> map, Candidates candidate) {
        return merge(buildCandidate(map, candidate));
    }

    public static Candidates createCandidate(HashMap<String, String> map) {
        return persist(buildCandidate(map, null));
    }

    public static String newPasswordGenerator(HashMap<String, String> map) {
        String password = "";
        if (map != null && !map.isEmpty()) {
            if (map.get("firstName") != null && !map.get("firstName").isEmpty()) {
                password = map.get("firstName");
            } else if (map.get("lastName") != null && !map.get("lastName").isEmpty()) {
                password = map.get("lastName");
            } else {
                password = "Stark";
            }
            if (map.get("phone") == null || map.get("phone").isEmpty()) {
                String email = map.get("email");
                password += email.length() + "" + email.length() * 2;
            } else {
                password += map.get("phone").substring(6, 10);
            }
            return password;
        }
        return null;
    }

    private static Candidates accountOriginGenerator(Candidates candidate, HashMap<String, String> map) {
        try {
            if (candidate != null && map != null && !map.isEmpty()) {

                AccountOrigin origin = new AccountOrigin();

                String date = map.get("accountOriginDate") != null ? map.get("accountOriginDate").trim() : Clock.getCurrentDate(),
                        time = map.get("accountOriginTime") != null ? map.get("accountOriginTime").trim() : "N/A",
                        sourceID = map.get("accountOriginSourceID") != null ? map.get("accountOriginSourceID") : "",
                        source = map.get("accountOriginSource") != null ? map.get("accountOriginSource") : "",
                        sourceType = map.get("accountOriginSourceType") != null ? map.get("accountOriginSourceType") : "",
                        workAmericaCreated = map.get("workAmericaCreated") != null ? map.get("workAmericaCreated") : "",
                        adminConsoleMethod = map.get("accountOriginAdminConsoleMethod") != null ? map.get("accountOriginAdminConsoleMethod") : "",
                        teamMember = map.get("accountOriginTeamMember") != null ? map.get("accountOriginTeamMember") : "",
                        keywords = map.get("accountOriginKeywords") != null ? map.get("accountOriginKeywords") : "",
                        device = map.get("accountOriginDevice") != null ? map.get("accountOriginDevice") : "",
                        deviceType = map.get("accountOriginDeviceType") != null ? map.get("accountOriginDeviceType") : "",
                        deviceVendor = map.get("accountOriginDeviceVendor") != null ? map.get("accountOriginDeviceVendor") : "",
                        os = map.get("accountOriginOS") != null ? map.get("accountOriginOS") : "",
                        osVersion = map.get("accountOriginOSVersion") != null ? map.get("accountOriginOSVersion") : "",
                        browser = map.get("accountOriginBrowser") != null ? map.get("accountOriginBrowser") : "",
                        browserVersion = map.get("accountOriginBrowserVersion") != null ? map.get("accountOriginBrowserVersion") : "",
                        marketingCampaign = map.get("accountOriginMarketingCampaign") != null ? map.get("accountOriginMarketingCampaign") : "";

                device = (device.isEmpty() || device.equalsIgnoreCase("undefined")) &&
                        (!deviceType.equalsIgnoreCase("mobile") && !deviceType.equalsIgnoreCase("tablet")) ? "" : device;
                deviceType = deviceType == null || deviceType.equalsIgnoreCase("undefined") ? "Computer" : deviceType;
                deviceVendor = deviceVendor.equalsIgnoreCase("undefined") ? "" : deviceVendor;

                origin.setDateAcquired(date);
                origin.setTimeAcquired(time);

                origin.setSource(source);
                origin.setSourceID(sourceID);
                origin.setSourceType(sourceType);
                origin.setAdminConsoleMethod(adminConsoleMethod);
                origin.setTeamMember(teamMember);
                origin.setWorkAmericaCreated(workAmericaCreated);
                origin.setSearchKeywords(keywords);
                origin.setDevice(device);
                origin.setDeviceType(deviceType);
                origin.setDeviceVendor(deviceVendor);
                origin.setOs(os);
                origin.setOsVersion(osVersion);
                origin.setBrowser(browser);
                origin.setBrowserVersion(browserVersion);
                origin.setMarketingCampaign(marketingCampaign);

                candidate.setAccountOrigin(origin);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return candidate;
    }

    public static Candidates buildCandidate(HashMap<String, String> map, Candidates candidate) {
        Candidates newCandidate = new Candidates();
        String password = map.get("password") != null && map.get("password").length() > 3 ? map.get("password") : null;
        String[] latLong = new String[]{"", ""};

        if (candidate != null) {
            newCandidate = candidate;
        } else {
            newCandidate.setDateCreated(Clock.getCurrentDate());
            newCandidate.setTimeCreated(Clock.getCurrentTime());

            if (map.get("authorized") != null) {
                newCandidate.setAuthorized(map.get("authorized"));
            } else {
                newCandidate.setAuthorized("Yes");
            }
            if (map.get("approved") != null) {
                newCandidate.setApproved(map.get("approved"));
            } else {
                newCandidate.setApproved("No");
            }
            if (map.get("workAmericaCreated") != null) {
                newCandidate.setWorkAmericaCreated(map.get("workAmericaCreated"));
            }
            if (password == null || password.isEmpty()) {
                password = newPasswordGenerator(map);
                String[] passwordArray = CustomUtilities.hashPassword(password, null);
                newCandidate.setPassword(passwordArray[0]);
                newCandidate.setSalt(passwordArray[1]);
            }
            newCandidate = accountOriginGenerator(newCandidate, map);
        }

        try {
            latLong = Geocode.toGeoCodeHelper(map.get("zip"), map.get("city"), map.get("state"));
        } catch (Exception e) {
            e.printStackTrace();
        }


        if (password != null && !password.isEmpty()) {
            String[] passwordArray = CustomUtilities.hashPassword(password, null);
            newCandidate.setPassword(passwordArray[0]);
            newCandidate.setSalt(passwordArray[1]);
        }

        String firstName = map.get("firstName") != null ? CustomUtilities.capitalizeFirstLetter(map.get("firstName").trim()) : "",
                lastName = map.get("lastName") != null ? CustomUtilities.capitalizeFirstLetter(map.get("lastName").trim()) : "",
                city = map.get("city") != null ? CustomUtilities.capitalizeFirstLetter(map.get("city").trim()) : "", state = map.get("state") != null ? map.get("state") : "",
                zip = map.get("zip") != null ? map.get("zip") : "", phone = map.get("phone") != null ? CustomUtilities.cleanNumber(map.get("phone").trim()) : "",
                alternatePhone = map.get("alternatePhone") != null ? map.get("alternatePhone").trim() : "",
                pastEducation = map.get("pastEducation") != null ? map.get("pastEducation") : "",
                workExperience = map.get("workExperience") != null ? map.get("workExperience") : "",
                veteran = map.get("veteran") != null ? map.get("veteran") : "", employed = map.get("employed") != null ? map.get("employed") : "",
                relocate = map.get("relocate") != null ? map.get("relocate") : "",
                additionalInformation = map.get("additionalInformation") != null ? map.get("additionalInformation") : "",
                school = map.get("school") != null ? map.get("school") : "", fields = map.get("fields") != null ? map.get("fields") : "",
                anticipatedCertifications = map.get("anticipatedCertifications") != null ? map.get("anticipatedCertifications") : "",
                pastFields = map.get("pastFields") != null ? map.get("pastFields") : "",
                obtainedCertifications = map.get("obtainedCertifications") != null ? map.get("obtainedCertifications") : "",
                completionDate = map.get("completionDate") != null ? map.get("completionDate").trim() : "",
                validDriversLicense = map.get("validDriversLicense") != null ? map.get("validDriversLicense") : "",
                email = map.get("email") != null ? map.get("email").toLowerCase().trim() : "";

        int schoolID = map.get("schoolID") != null ? Integer.parseInt(map.get("schoolID").trim()) : SchoolDataAccessObject.getSchoolIDByName(school);

        if (candidate != null) {
            SendGridContacts.update(email, firstName, lastName, zip, school, candidate.getCandidateID(), fields);
        }

        if (!firstName.isEmpty()) {
            newCandidate.setFirstName(firstName);
        }

        if (!lastName.isEmpty()) {
            newCandidate.setLastName(lastName);
        }

        if (!email.isEmpty() && (CustomUtilities.isValidEmail(email))) {
            newCandidate.setEmail(email);
        }

        if (!city.isEmpty()) {
            newCandidate.setCity(city);
        }

        if (!state.isEmpty()) {
            newCandidate.setState(state);
        }

        if (!zip.isEmpty() && (CustomUtilities.isValidZip(zip))) {
            newCandidate.setZip(zip);
        }

        if (!phone.isEmpty() && (CustomUtilities.isValidNumber(phone))) {
            newCandidate.setPhone(phone);
        }

        if (!alternatePhone.isEmpty() && (CustomUtilities.isValidNumber(alternatePhone))) {
            newCandidate.setAlternatePhone(alternatePhone);
        }

        if (!pastEducation.isEmpty()) {
            newCandidate.setPastEducation(pastEducation);
        }

        if (!workExperience.isEmpty()) {
            newCandidate.setWorkExperience(workExperience);
        }

        if (!veteran.isEmpty()) {
            newCandidate.setVeteran(veteran);
        }

        if (!employed.isEmpty()) {
            newCandidate.setEmployed(employed);
        }

        if (!relocate.isEmpty()) {
            newCandidate.setRelocate(relocate);
        }

        if (!additionalInformation.isEmpty()) {
            newCandidate.setAdditionalInformation(additionalInformation);
        }

        if (!school.isEmpty()) {
            newCandidate.setSchool(school);
        }

        if (!fields.isEmpty()) {
            newCandidate.setField(fields);
        }

        if (!anticipatedCertifications.isEmpty()) {
            newCandidate.setAnticipatedCertification(anticipatedCertifications);
        }

        if (!pastFields.isEmpty()) {
            newCandidate.setPastField(pastFields);
        }

        if (!obtainedCertifications.isEmpty()) {
            newCandidate.setObtainedCertification(obtainedCertifications);
        }

        if (!completionDate.isEmpty()) {
            newCandidate.setCompletionDate(completionDate);
        }

        if (!validDriversLicense.isEmpty()) {
            newCandidate.setValidDriversLicense(validDriversLicense);
        }

        newCandidate.setLatitude(latLong[0]);
        newCandidate.setLongitude(latLong[1]);
        newCandidate.setSchoolID(schoolID);

        return newCandidate;
    }

    public static CandidateActivityLogs addActivityLog(CandidateActivityLogs activityLog) {
        if (activityLog != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(activityLog);
                trans.commit();
                return activityLog;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static CandidateActivityLogs buildActivityLog(HttpServletRequest request, Candidates candidate,
                                                         CandidateLoginLogs loginLog) {
        if (request != null && candidate != null && loginLog != null) {
            String firstName = CustomUtilities.capitalizeFirstLetter(request.getParameter("firstName")),
                    lastName = CustomUtilities.capitalizeFirstLetter(request.getParameter("lastName")),
                    city = CustomUtilities.capitalizeFirstLetter(request.getParameter("city")),
                    state = request.getParameter("state"),
                    phone = request.getParameter("phone").replaceAll("[^0-9]+", "").trim(),
                    alternatePhone = request.getParameter("alternatePhone").replaceAll("[^0-9]+", "").trim(),
                    school = request.getParameter("school"), completionDate = request.getParameter("completionDate"),
                    workExperience = request.getParameter("workExperience"), veteran = request.getParameter("veteran"),
                    employed = request.getParameter("employed"), relocate = request.getParameter("relocate"),
                    additionalInformation = request.getParameter("additionalInformation"),
                    pastEducation = request.getParameter("pastEducation"),
                    validDriversLicense = request.getParameter("validDriversLicense"), zip = request.getParameter("zip");

            String fields = request.getParameter("field"), certifications = request.getParameter("certification"),
                    pastFields = request.getParameter("pastField"),
                    obtainedCertifications = request.getParameter("obtainedCertification");

            String profileChange = "";
            CandidateActivityLogs activityLog = new CandidateActivityLogs();
            activityLog.setCandidateLoginLogsID(loginLog.getCandidateLoginLogsID());
            activityLog.setTime(Clock.getCurrentTime());

            if (candidate.getFirstName() != null && !candidate.getFirstName().equals(firstName)) {
                profileChange += "Updated First Name from " + candidate.getFirstName() + " to " + firstName + " | ";
            }
            if (candidate.getLastName() != null && !candidate.getLastName().equals(lastName)) {
                profileChange += "Updated Last Name from " + candidate.getLastName() + " to " + lastName + " | ";
            }
            if (candidate.getCity() != null && !candidate.getCity().equals(city)) {
                profileChange += "Updated City from " + candidate.getCity() + " to " + city + " | ";
            }
            if (candidate.getState() != null && !candidate.getState().equals(state)) {
                profileChange += "Updated State from " + candidate.getState() + " to " + state + " | ";
            }
            if (candidate.getZip() != null && !candidate.getZip().equals(zip)) {
                profileChange += "Updated Zip from " + candidate.getZip() + " to " + zip + " | ";
            }
            if (candidate.getPhone() != null && !candidate.getPhone().equals(phone)) {
                profileChange += "Updated Phone from " + candidate.getPhone() + " to " + phone + " | ";
            }
            if (candidate.getAlternatePhone() != null && !candidate.getAlternatePhone().equals(alternatePhone)) {
                profileChange += "Updated Alternate Phone from " + candidate.getAlternatePhone() + " to " + alternatePhone
                        + " | ";
            }
            if (candidate.getSchool() != null && !candidate.getSchool().equals(school)) {
                profileChange += "Updated School from " + candidate.getSchool() + " to " + school + " | ";
            }

            if (candidate.getCompletionDate() != null && !candidate.getCompletionDate().equals(completionDate)) {
                profileChange += "Updated Completion Date from " + candidate.getCompletionDate() + " to " + completionDate
                        + " | ";
            }
            if (candidate.getWorkExperience() != null && !candidate.getWorkExperience().equals(workExperience)) {
                profileChange += "Updated Work Experience from " + candidate.getWorkExperience() + " to " + workExperience
                        + " | ";
            }
            if (candidate.getVeteran() != null && !candidate.getVeteran().equals(veteran)) {
                profileChange += "Updated Veteran Status from " + candidate.getVeteran() + " to " + veteran + " | ";
            }
            if (candidate.getEmployed() != null && !candidate.getEmployed().equals(employed)) {
                profileChange += "Updated Currently Employed from " + candidate.getEmployed() + " to " + employed + " | ";
            }
            if (candidate.getRelocate() != null && !candidate.getRelocate().equals(relocate)) {
                profileChange += "Updated Relocate Preference from " + candidate.getRelocate() + " to " + relocate + " | ";
            }
            if (candidate.getAdditionalInformation() != null
                    && !candidate.getAdditionalInformation().equals(additionalInformation)) {
                profileChange += "Updated Additional Information from " + candidate.getAdditionalInformation() + " to "
                        + additionalInformation + " | ";
            }
            if (candidate.getPastEducation() != null && !candidate.getPastEducation().equals(pastEducation)) {
                profileChange += "Updated Past Education from " + candidate.getPastEducation() + " to " + pastEducation
                        + " | ";
            }
            if (candidate.getField() != null && !candidate.getField().equals(fields)) {
                profileChange += "Updated Current Field from " + candidate.getField() + " to " + fields + " | ";
            }
            if (candidate.getAnticipatedCertification() != null
                    && !candidate.getAnticipatedCertification().equals(certifications)) {
                profileChange += "Updated Anticipated Certifications from " + candidate.getAnticipatedCertification()
                        + " to " + certifications + " | ";
            }
            if (candidate.getObtainedCertification() != null
                    && !candidate.getObtainedCertification().equals(obtainedCertifications)) {
                profileChange += "Updated Obtained Certifications from " + candidate.getObtainedCertification() + " to "
                        + obtainedCertifications + " | ";
            }
            if (candidate.getPastField() != null && !candidate.getPastField().equals(pastFields)) {
                profileChange += "Updated Past Training from " + candidate.getPastField() + " to " + pastFields + " | ";
            }
            if (candidate.getValidDriversLicense() != null && candidate.getValidDriversLicense().length() > 1
                    && !candidate.getValidDriversLicense().equals(validDriversLicense)) {
                profileChange += "Updated Valid Driver's License from " + candidate.getValidDriversLicense() + " to "
                        + validDriversLicense + " | ";
            }

            activityLog.setProfileUpdates(profileChange);
            return activityLog;
        }
        return null;
    }

    public static Candidates refresh(Candidates candidate) {
        if (candidate != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            em.refresh(candidate);
            return candidate;
        }
        return null;
    }

    public static boolean isIncomplete(Candidates candidate) {
        if (candidate != null) {
            if ((candidate.getField() == null || candidate.getField().isEmpty()) &&
                    (candidate.getAnticipatedCertification() == null || candidate.getAnticipatedCertification().isEmpty()) &&
                    (candidate.getPastField() == null || candidate.getPastField().isEmpty()) &&
                    (candidate.getObtainedCertification() == null || candidate.getObtainedCertification().isEmpty())) {
                return true;
            }
        }
        return false;
    }

    public static List<Candidates> removeIncompleteProfiles(List<Candidates> list) {
        if (list != null && list.size() > 1) {
            for (Iterator<Candidates> iterator = list.iterator(); iterator.hasNext(); ) {
                Candidates candidate = iterator.next();
                if (isIncomplete(candidate)) {
                    iterator.remove();
                }
            }
        }
        return list;
    }

    public static List<Candidates> getAllCompleteProfiles(List<Candidates> list) {
        return removeIncompleteProfiles(list);
    }
}
