package co.workamerica.functionality.persistence;

import co.workamerica.entities.candidates.Candidates;
import co.workamerica.entities.logs.schools.SchoolActivityLogs;
import co.workamerica.entities.logs.schools.SchoolLoginLogs;
import co.workamerica.entities.schools.SchoolAccounts;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

import javax.persistence.*;
import java.util.List;

public class SchoolAccountDataAccessObject {

    private final static CustomUtilities custom = new CustomUtilities();

    public static boolean existsByCode(String code) {
        if (code != null && !code.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT account.employerCode FROM SchoolAccounts account";
            TypedQuery<String> q = em.createQuery(query, String.class);
            try {
                List<String> list = q.getResultList();
                return list.contains(code);
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
        return false;
    }

    public static SchoolAccounts getAccountByCode(String code) {
        if (code != null && !code.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT account FROM SchoolAccounts account where account.employerCode = :code";
            try {
                TypedQuery<SchoolAccounts> q = em.createQuery(query, SchoolAccounts.class);
                q.setParameter("code", code);
                return q.getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static SchoolLoginLogs getLoginLogByID(int schoolLoginLogID) {
        if (schoolLoginLogID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            SchoolLoginLogs log = em.find(SchoolLoginLogs.class, schoolLoginLogID);
            em.close();
            return log;
        }
        return null;
    }

    public static SchoolAccounts login(String email, String password)
            throws NoResultException, NonUniqueResultException {

        if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();

            String loginQuery = "SELECT s FROM SchoolAccounts s WHERE s.email = :email";
            TypedQuery<SchoolAccounts> qLogin = em.createQuery(loginQuery, SchoolAccounts.class);
            qLogin.setParameter("email", email);

            SchoolAccounts account = qLogin.getSingleResult();
            em.close();

            if (account != null && custom.hashPassword(password, custom.hexToBytes(account.getSalt()))[0].equals(account.getPassword())) {
                return account;
            }
        }
        return null;
    }

    public static SchoolLoginLogs addLoginLog(SchoolAccounts account) {
        if (account != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            SchoolLoginLogs loginLog = new SchoolLoginLogs(account.getSchoolAccountID(), Clock.getCurrentDate(),
                    Clock.getCurrentTime());
            try {
                trans.begin();
                em.persist(loginLog);
                trans.commit();
                return loginLog;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static List<Candidates> getCandidates(SchoolAccounts account) {
        if (account != null && account.getSchoolID() > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String candidateQuery = "SELECT candidate FROM Candidates candidate WHERE candidate.schoolID = :schoolID";
            TypedQuery<Candidates> qCandidate = em.createQuery(candidateQuery, Candidates.class);
            qCandidate.setParameter("schoolID", account.getSchoolID());
            List<Candidates> list = qCandidate.getResultList();
            em.close();
            return list;
        }
        return null;
    }

    public static SchoolAccounts persist(SchoolAccounts account) {
        if (account != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(account);
                trans.commit();
                return account;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static SchoolAccounts merge(SchoolAccounts account) {
        if (account != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                account = em.merge(account);
                trans.commit();
                return account;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static SchoolActivityLogs persistActivityLog(SchoolActivityLogs activityLog) {
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

    public static List<SchoolAccounts> getAll() {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        String schoolAccountsQuery = "SELECT s FROM SchoolAccounts s";
        TypedQuery<SchoolAccounts> qSchoolAccount = em.createQuery(schoolAccountsQuery, SchoolAccounts.class);
        List<SchoolAccounts> list = qSchoolAccount.getResultList();
        em.close();
        return list;
    }

    public static SchoolAccounts getByID(int schoolAccountID) {
        if (schoolAccountID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            SchoolAccounts account = em.find(SchoolAccounts.class, schoolAccountID);
            em.close();
            return account;
        }
        return null;
    }
}
