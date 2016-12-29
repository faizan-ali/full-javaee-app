package co.workamerica.functionality.persistence;

import co.workamerica.entities.clients.ProfileViews;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by Faizan on 5/11/2016.
 */
public class ProfileViewDataAccessObject {

    public static ProfileViews create (int clientID, int candidateID) {
        if (clientID > 0 && candidateID > 0) {
            ProfileViews view = new ProfileViews();
            view.setClientID(clientID);
            view.setCandidateID(candidateID);
            persist(view);
            return view;
        }
        return null;
    }

    public static ProfileViews merge (ProfileViews view) {
        if (view != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                view = em.merge(view);
                trans.commit();
                return view;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static ProfileViews persist (ProfileViews view) {
        if (view != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(view);
                trans.commit();
                return view;
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
