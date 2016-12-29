package co.workamerica.functionality.persistence;

import co.workamerica.entities.texts.ReceivedTexts;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by Faizan on 5/10/2016.
 */
public class ReceivedTextDataAccessObject {

    private final static CustomUtilities custom = new CustomUtilities();

    public static ReceivedTexts persist(ReceivedTexts text) {
        if (text != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(text);
                trans.commit();
                return text;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static ReceivedTexts merge(ReceivedTexts text) {
        if (text != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                text = em.merge(text);
                trans.commit();
                return text;
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
