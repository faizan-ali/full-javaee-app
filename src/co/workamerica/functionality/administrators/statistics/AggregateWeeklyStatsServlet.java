package co.workamerica.functionality.administrators.statistics;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.candidates.jsonModels.accountOrigin.AccountOrigin;
import co.workamerica.entities.criteria.Fields;
import co.workamerica.entities.criteria.Schools;
import co.workamerica.entities.criteria.States;
import co.workamerica.functionality.persistence.CandidatePersistence;
import co.workamerica.functionality.persistence.FieldPersistence;
import co.workamerica.functionality.persistence.SchoolDataAccessObject;
import co.workamerica.functionality.persistence.StateDataAccessObject;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;

@WebServlet("/AggregateWeeklyStatsServlet")
public class AggregateWeeklyStatsServlet extends HttpServlet {
    private static final long serialVersionUID = 1L;

    public AggregateWeeklyStatsServlet() {
        super();
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        HttpSession session = request.getSession();

        int profilesUpTillLastMonth = 0, completeProfilesUpTillLastMonth = 0, profilesAddedLastMonth = 0, completeProfilesAddedLastMonth = 0;

        // Gets date to generate a report up to and including that day
        String dateString = request.getParameter("date").trim();

        // New candidates signed up/added the week prior to the chosen date
        List<Candidates> newCandidatesThisWeek = new ArrayList<Candidates>();

        // All candidates added to the platform up till and including the date
        List<Candidates> allCandidatesTillToday = new ArrayList<>();

        // All candidates on the platform ever
        List<Candidates> allCandidates = CandidatePersistence.getAll();

        // Criteria lists
        List<States> stateList = StateDataAccessObject.getAll();
        List<Fields> fieldList = FieldPersistence.getAll();
        List<Schools> schoolList = SchoolDataAccessObject.getAll();

        try {
            // Takes the dates from the current and previous WeeklyStats
            // entities to determine what candidates created accounts in that
            // week
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
            LocalDate endDate = LocalDate.parse(dateString, dtf);
            LocalDate startDate = endDate.minusDays(6);

            for (Candidates candidate : allCandidates) {
                // Building a list of candidates added in the past week
                for (LocalDate date = startDate; date.isBefore(endDate.plusDays(1)); date = date.plusDays(1)) {
                    if (candidate.getDateCreated() != null && candidate.getDateCreated().trim().equals(date.format(dtf))) {
                        newCandidatesThisWeek.add(candidate);
                    }
                }

                // Getting number of candidates added last month
                if (candidate.getDateCreated() != null && LocalDate.parse(candidate.getDateCreated().trim(), dtf).isAfter(endDate.minusDays(29))) {
                    if (!CandidatePersistence.isIncomplete(candidate)) {
                        completeProfilesAddedLastMonth++;
                    }
                    profilesAddedLastMonth++;
                }

                // Building a list of candidates added to the platform since launch till the date
                if (candidate.getDateCreated() == null || LocalDate.parse(candidate.getDateCreated(), dtf).isBefore(endDate.plusDays(1))) {
                    allCandidatesTillToday.add(candidate);
                }

                // Getting number of candidates up till a month before date
                if (candidate.getDateCreated() == null || LocalDate.parse(candidate.getDateCreated(), dtf).isBefore(endDate.minusDays(27))) {
                    if (!CandidatePersistence.isIncomplete(candidate)) {
                        completeProfilesUpTillLastMonth++;
                    }
                    profilesUpTillLastMonth++;
                }
            }


            // Build HashMap for profiles/state. Key = State name, Value =
            // Array[TotalProfilesInState, ProfilesInStateGrowth]
            // HashMap for profiles/school. Key = School name, Value =
            // Array[TotalProfiles in School, ProfilesInSchoolGrowth]
            // HashMap for profiles/fields. Key = Field name, Value =
            // Array[TotalProfiles in Field, ProfilesInFieldGrowth]
            // Array for resumes [Yes, No]
            HashMap<String, int[]> statesMap = new HashMap<String, int[]>();
            HashMap<String, int[]> schoolsMap = new HashMap<String, int[]>();
            HashMap<String, int[]> fieldsMap = new HashMap<String, int[]>();
            int[] resumeCheck = new int[]{0, 0};

            for (Candidates candidate : allCandidatesTillToday) {

                for (States state : stateList) {
                    String stateName = state.getAbbreviation().trim();
                    statesMap.putIfAbsent(stateName, new int[]{0, 0});
                    if (candidate.getState() != null && candidate.getState().trim().equals(stateName)) {
                        statesMap.get(stateName)[0]++;
                    }
                }

                for (Schools school : schoolList) {
                    String schoolName = school.getName().trim();
                    schoolsMap.putIfAbsent(schoolName, new int[]{0, 0});
                    if (candidate.getSchool() != null && candidate.getSchool().trim().equals(schoolName)) {
                        schoolsMap.get(schoolName)[0]++;
                    }
                }
                for (Fields field : fieldList) {
                    String fieldName = field.getName().trim();
                    fieldsMap.putIfAbsent(fieldName, new int[]{0, 0});
                    if (candidate.getField() != null
                            && candidate.getField().trim().equals(fieldName)) {
                        fieldsMap.get(fieldName)[0]++;
                    }
                }

                if (candidate.getResume() != null && candidate.getResume().length > 5) {
                    resumeCheck[0]++;
                } else {
                    resumeCheck[1]++;
                }
            }


            // Map of user or workamerica created accounts
            HashMap<String, Integer> accountMap = new HashMap<>();

            // Creates a map of source type (String) with value being the number of those sources (int)
            HashMap<String, Integer> sourceMap = new HashMap<>();

            // Marketing campaigns (SocialMedia, CredentialingInstitute etc)
            HashMap<String, Integer> campaignMap = new HashMap<>();

            // Device, Browser, OS
            HashMap<String, Integer> deviceMap = new HashMap<>();
            HashMap<String, HashMap<String, Integer>> browserMap = new HashMap<>();
            HashMap<String, HashMap<String, Integer>> osMap = new HashMap<>();

            accountMap.put("WorkAmerica Created", 0);
            accountMap.put("User Created", 0);
            accountMap.put("Unknown", 0);

            campaignMap.put("Unknown", 0);

            deviceMap.put("Unknown", 0);
            deviceMap.put("Computer", 0);
            deviceMap.put("Mobile", 0);
            deviceMap.put("Tablet", 0);

            for (Candidates newCandidate : newCandidatesThisWeek) {

                AccountOrigin origin = newCandidate.getAccountOrigin();
                if (origin != null) {
                    try {
                        // Marketing campaigns
                        if (origin.getMarketingCampaign() == null || origin.getMarketingCampaign().isEmpty()) {
                            campaignMap.put("Unknown", campaignMap.get("Unknown") + 1);
                        } else {
                            campaignMap.putIfAbsent(origin.getMarketingCampaign(), 0);
                            campaignMap.put(origin.getMarketingCampaign(), campaignMap.get(origin.getMarketingCampaign()) + 1);
                        }

                        // Device
                        if (origin.getDeviceType() != null && !origin.getDeviceType().isEmpty()) {
                            if (origin.getDeviceType().equalsIgnoreCase("Computer")) {
                                deviceMap.put("Computer", deviceMap.get("Computer") + 1);
                            } else if (origin.getDeviceType().equalsIgnoreCase("Mobile")) {
                                deviceMap.put("Mobile", deviceMap.get("Mobile") + 1);
                            } else if (origin.getDeviceType().equalsIgnoreCase("Tablet")) {
                                deviceMap.put("Tablet", deviceMap.get("Tablet") + 1);
                            } else {
                                deviceMap.put("Unknown", deviceMap.get("Unknown") + 1);
                            }
                        } else {
                            deviceMap.put("Unknown", deviceMap.get("Unknown") + 1);
                        }

                        // Browser
                        if (origin.getBrowser() != null && !origin.getBrowser().isEmpty()) {
                            browserMap.putIfAbsent(origin.getBrowser(), new HashMap<String, Integer>());

                            if (origin.getBrowserVersion() != null && !origin.getBrowserVersion().isEmpty()) {
                                browserMap.get(origin.getBrowser()).putIfAbsent(origin.getBrowserVersion(), 0);
                                browserMap.get(origin.getBrowser()).put(origin.getBrowserVersion(),
                                        browserMap.get(origin.getBrowser()).get(origin.getBrowserVersion()) + 1);
                            } else {
                                browserMap.get(origin.getBrowser()).putIfAbsent("Unknown", 0);
                                browserMap.get(origin.getBrowser()).put("Unknown",
                                        browserMap.get(origin.getBrowser()).get("Unknown") + 1);
                            }

                            if (browserMap.get(origin.getBrowser()).get("Total") == null) {
                                browserMap.get(origin.getBrowser()).putIfAbsent("Total", 1);
                            } else {
                                browserMap.get(origin.getBrowser()).put("Total", browserMap.get(origin.getBrowser()).get("Total") + 1);
                            }
                        }

                        // OS
                        if (origin.getOs() != null && !origin.getOs().isEmpty()) {
                            osMap.putIfAbsent(origin.getOs(), new HashMap<String, Integer>());

                            if (origin.getOsVersion() != null && !origin.getOsVersion().isEmpty()) {
                                osMap.get(origin.getOs()).putIfAbsent(origin.getOsVersion(), 0);
                                osMap.get(origin.getOs()).put(origin.getOsVersion(),
                                        osMap.get(origin.getOs()).get(origin.getOsVersion()) + 1);
                            } else {
                                osMap.get(origin.getOs()).putIfAbsent("Unknown", 0);
                                osMap.get(origin.getOs()).put("Unknown",
                                        osMap.get(origin.getOs()).get("Unknown") + 1);
                            }

                            if (osMap.get(origin.getOs()).get("Total") == null) {
                                osMap.get(origin.getOs()).putIfAbsent("Total", 1);
                            } else {
                                osMap.get(origin.getOs()).put("Total", osMap.get(origin.getOs()).get("Total") + 1);
                            }
                        }

                        String workAmericaCreated = origin.getWorkAmericaCreated();
                        if (workAmericaCreated != null) {
                            if (workAmericaCreated.equalsIgnoreCase("Yes")) {
                                accountMap.put("WorkAmerica Created", accountMap.get("WorkAmerica Created") + 1);
                            } else if (workAmericaCreated.equalsIgnoreCase("No")) {
                                accountMap.put("User Created", accountMap.get("User Created") + 1);
                            } else {
                                accountMap.put("Unknown", accountMap.get("Unknown") + 1);
                            }
                        } else {
                            accountMap.put("Unknown", accountMap.get("Unknown") + 1);
                        }

                        String sourceType = origin.getSourceType();
                        if (sourceType != null && !sourceType.isEmpty()) {
                            sourceMap.putIfAbsent(sourceType, 0);
                            sourceMap.put(sourceType, sourceMap.get(sourceType) + 1);
                        }


                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }

                if (newCandidate.getState() != null && !newCandidate.getState().isEmpty()) {
                    for (States state : stateList) {
                        String stateName = state.getAbbreviation().trim();
                        if (newCandidate.getState().trim().equals(stateName)) {
                            statesMap.get(stateName)[1]++;
                            break;
                        }
                    }
                }

                if (newCandidate.getSchool() != null && !newCandidate.getSchool().isEmpty()) {
                    for (Schools school : schoolList) {
                        String schoolName = school.getName().trim();
                        if (newCandidate.getSchool().trim().equals(schoolName)) {
                            schoolsMap.get(schoolName)[1]++;
                            break;
                        }
                    }
                }

                if (newCandidate.getField() != null && !newCandidate.getField().isEmpty()) {
                    for (Fields field : fieldList) {
                        String fieldName = field.getName().trim();
                        if (newCandidate.getField().trim().equals(fieldName)) {
                            fieldsMap.get(fieldName)[1]++;
                            break;
                        }
                    }
                }
            }

            List<Candidates> totalCompleteProfiles = CandidatePersistence.removeIncompleteProfiles(new ArrayList<Candidates>(allCandidatesTillToday));
            List<Candidates> newCompleteProfiles = CandidatePersistence.removeIncompleteProfiles(new ArrayList<Candidates>(newCandidatesThisWeek));

            ListIterator<Candidates> iterator = allCandidatesTillToday.listIterator();

            while (iterator.hasNext()) {
                Candidates candidate = iterator.next();
                if (candidate != null && candidate.getDateCreated() != null) {
                    LocalDate tempDate = LocalDate.parse(candidate.getDateCreated(), dtf);
                    if (tempDate.isAfter(endDate)) {
                        iterator.remove();
                    }
                }
            }

            session.setAttribute("accountMap", accountMap);
            session.setAttribute("sourceMap", sourceMap);
            session.setAttribute("campaignMap", campaignMap);
            session.setAttribute("deviceMap", deviceMap);
            session.setAttribute("browserMap", browserMap);
            session.setAttribute("osMap", osMap);

            session.setAttribute("totalProfiles", allCandidatesTillToday.size());
            session.setAttribute("totalProfilesLastWeek", allCandidatesTillToday.size() - newCandidatesThisWeek.size());
            session.setAttribute("totalNewProfiles", newCandidatesThisWeek.size());

            session.setAttribute("totalCompleteProfiles", totalCompleteProfiles.size());
            session.setAttribute("totalCompleteProfilesLastWeek", totalCompleteProfiles.size() - newCompleteProfiles.size());
            session.setAttribute("totalNewCompleteProfiles", newCompleteProfiles.size());
            session.setAttribute("profilesUpTillLastMonth", profilesUpTillLastMonth);
            session.setAttribute("completeProfilesUpTillLastMonth", completeProfilesUpTillLastMonth);
            session.setAttribute("profilesAddedLastMonth", profilesAddedLastMonth);
            session.setAttribute("completeProfilesAddedLastMonth", completeProfilesAddedLastMonth);

            session.setAttribute("date", dateString);
            session.setAttribute("resumeCheck", resumeCheck);
            session.setAttribute("fieldsMap", fieldsMap);
            session.setAttribute("schoolsMap", schoolsMap);
            session.setAttribute("statesMap", statesMap);
            session.setAttribute("newCandidates", newCandidatesThisWeek);

            response.sendRedirect("admin/admin-aggregated-statistics.jsp");
        } catch (Exception e) {
            e.printStackTrace();
            response.sendRedirect("admin/admin-statistics-list.jsp");
        }
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        doGet(request, response);
    }

}
