package co.workamerica.functionality.persistence;

import co.workamerica.entities.clients.Clients;
import co.workamerica.entities.logs.clients.ClientLoginLogs;
import co.workamerica.entities.logs.clients.PipelineChangeLogs;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;
import co.workamerica.functionality.twilio.API.TwilioNumberPurchaser;
import com.twilio.sdk.TwilioRestException;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;

public class ClientPersistence {

    public static PipelineChangeLogs addPipelineChangeLog(PipelineChangeLogs log) {
        if (log != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(log);
                trans.commit();
                return log;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            }
        }
        return null;
    }

    public static Clients getByID(String clientID) {
        if (clientID != null && !clientID.isEmpty()) {
            try {
                int parsedID = Integer.parseInt(clientID);
                return getByID(parsedID);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Clients getByID(int clientID) {
        if (clientID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            Clients client = em.find(Clients.class, clientID);
            em.close();
            return client;
        }
        return null;
    }

    public static Clients getByPhone(String phone) throws NoResultException, NonUniqueResultException {
        if (phone != null && !phone.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT client FROM Clients client WHERE client.assignedNumber = :phone";
            TypedQuery<Clients> q = em.createQuery(query, Clients.class);
            q.setParameter("phone", phone);
            Clients client = q.getSingleResult();
            em.close();
            return client;
        }
        return null;
    }

    public static Clients login(String email, String password) {

        if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT c FROM Clients c WHERE c.email = :email and c.approved = 'Yes'";
            TypedQuery<Clients> qClient = em.createQuery(query, Clients.class);

            try {
                qClient.setParameter("email", email.toLowerCase());

                Clients client = qClient.getSingleResult();

                if (client != null && CustomUtilities.hashPassword(password, CustomUtilities.hexToBytes(client.getSalt()))[0].equals(client.getPassword())) {
                    //Sets monthly profiles viewed to 0 if beginning of the month
                    LocalDate dateToday = LocalDate.now();
                    if (dateToday.getDayOfMonth() == 1) {
                        client.setProfilesViewedThisMonth(0);

                        if (client.getCompany() != null) {
                            client.getCompany().setProfilesViewedThisMonth(0);
                        }

                        client = ClientPersistence.merge(client);
                    }

                    ClientLoginLogs loginLog = ClientPersistence.addLoginLog(client);
                    client.getLoginLog().add(loginLog);
                    return client;
                }
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static ClientLoginLogs addLoginLog(Clients client) {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        EntityTransaction trans = em.getTransaction();
        ClientLoginLogs loginLog = new ClientLoginLogs(client.getClientID(), Clock.getCurrentDate(),
                Clock.getCurrentTime());
        try {
            trans.begin();
            em.persist(loginLog);
            trans.commit();
            loginLog.setClient(client);
            return loginLog;
        } catch (Exception e) {
            trans.rollback();
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public static List<Clients> getAll() {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        String clientQuery = "select c FROM Clients c";
        TypedQuery<Clients> qClient = em.createQuery(clientQuery, Clients.class);
        List<Clients> list = qClient.getResultList();
        em.close();
        return list;
    }

    public static List<String> getAllByField(String field) {
        if (field != null && !field.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            try {
                String clientQuery = "SELECT client." + field + " FROM Clients client";
                TypedQuery<String> qClient = em.createQuery(clientQuery, String.class);
                return qClient.getResultList();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static boolean checkIfExistsByEmail(String email) {
        if (email != null && !email.isEmpty()) {
            List<String> emailList = getAllByField("email");
            return emailList != null && emailList.contains(email);
        }
        return false;
    }

    public static Clients persist(Clients client) {
        if (client != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(client);
                trans.commit();
                return client;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
                return null;
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static Clients merge(Clients client) {
        if (client != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                client = em.merge(client);
                trans.commit();
                return client;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
                return null;
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static Clients refresh(Clients client) {
        if (client != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            em.refresh(client);
            return client;
        }
        return null;
    }

    public static boolean delete(int clientID) {
        if (clientID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            Clients client = em.find(Clients.class, clientID);
            if (client != null) {
                EntityTransaction trans = em.getTransaction();
                try {
                    trans.begin();
                    em.remove(client);
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

    public static Clients buildClient(HashMap<String, String> map, Clients client) throws TwilioRestException {
        Clients newClient = new Clients();
        String password = (map.get("password") != null && map.get("password").length() > 3) ? map.get("password") : "Stark";

        if (client != null) {
            newClient = client;
        } else {
            newClient.setDateCreated(Clock.getCurrentDate());
            newClient.setProfilesViewed(0);
        }

        if (!password.equals("Unchanged")) {
            String[] passwordArray = CustomUtilities.hashPassword(password, null);
            newClient.setPassword(passwordArray[0]);
            newClient.setSalt(passwordArray[1]);
        }

        String firstName = map.get("firstName") != null ? map.get("firstName") : "",
                lastName = map.get("lastName") != null ? map.get("lastName") : "",
                company = map.get("company") != null ? map.get("company") : "",
                email = map.get("email") != null ? map.get("email") : "",
                geoLimit = map.get("geoLimit") != null ? map.get("geoLimit") : "All",
                assignNumber = map.get("assignNumber") != null ? map.get("assignNumber") : "No",
                approved = map.get("approved") != null ? map.get("approved") : "Yes";


        int schoolAccountID = map.get("schoolAccountID") != null ? Integer.parseInt(map.get("schoolAccountID")) : 0,
                viewLimit = map.get("viewLimit") != null ? Integer.parseInt(map.get("viewLimit")) : 3,
                companyID = map.get("companyID") != null ? Integer.parseInt(map.get("companyID")) : 0;

        if (assignNumber.equals("Yes")) {
            newClient.setAssignedNumber(TwilioNumberPurchaser.getNewNumber());
        } else {
            newClient.setAssignedNumber(null);
        }

        newClient.setFirstName(firstName);
        newClient.setLastName(lastName);
        newClient.setCompanyID(companyID);
        newClient.setEmail(email);
        newClient.setSchoolAccountID(schoolAccountID);
        newClient.setViewLimit(viewLimit);
        newClient.setGeoLimit(geoLimit);
        newClient.setApproved(approved);

        return newClient;
    }

    public static Clients createClient(HashMap<String, String> map) throws TwilioRestException {
        return persist(buildClient(map, null));
    }

    public static Clients update(HashMap<String, String> map, Clients client) throws TwilioRestException {
        return merge(buildClient(map, client));
    }


}
