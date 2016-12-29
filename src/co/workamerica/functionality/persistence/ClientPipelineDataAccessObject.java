package co.workamerica.functionality.persistence;

import co.workamerica.entities.clients.ClientPipelines;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;

/**
 * Created by Faizan on 5/11/2016.
 */
public class ClientPipelineDataAccessObject {

    public static ClientPipelines getByClientAndCandidateID (int clientID, int candidateID) {
        if (clientID > 0 && candidateID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            try {
                String query = "SELECT elt FROM ClientPipelines elt WHERE elt.clientID = :clientID AND elt.candidateID = :candidateID";
                TypedQuery<ClientPipelines> q = em.createQuery(query, ClientPipelines.class);
                q.setParameter("clientID", clientID);
                q.setParameter("candidateID", candidateID);
                return q.getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static ClientPipelines persist (ClientPipelines elt) {
        if (elt != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(elt);
                trans.commit();
                return elt;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            }
        }
        return null;
    }
}
