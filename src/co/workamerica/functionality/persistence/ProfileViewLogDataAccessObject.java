package co.workamerica.functionality.persistence;

import co.workamerica.entities.logs.clients.ProfileViewLogs;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by Faizan on 5/11/2016.
 */
public class ProfileViewLogDataAccessObject {

    public static ProfileViewLogs create (int clientLoginLogsID, int candidateID, String time) {
        if (clientLoginLogsID > 0 && candidateID > 0) {
            time = time == null || time.isEmpty() ? Clock.getCurrentTime() : time;
            ProfileViewLogs log = new ProfileViewLogs();
            log.setClientLoginLogsID(clientLoginLogsID);
            log.setCandidateID(candidateID);
            log.setTime(time);
            persist(log);
            return log;
        }
        return null;
    }

    public static ProfileViewLogs merge (ProfileViewLogs log) {
        if (log != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                log = em.merge(log);
                trans.commit();
                return log;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static ProfileViewLogs persist (ProfileViewLogs log) {
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
            } finally {
                em.close();
            }
        }
        return null;
    }
}
