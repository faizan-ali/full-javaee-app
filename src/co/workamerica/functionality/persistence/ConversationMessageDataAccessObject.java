package co.workamerica.functionality.persistence;

import co.workamerica.entities.texts.ConversationMessages;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;

/**
 * Created by Faizan on 5/11/2016.
 */
public class ConversationMessageDataAccessObject {

    public static ConversationMessages create (int conversationID, String date, String time, String recipient, String body, String success) {
            if (conversationID > 0 && body != null) {
                ConversationMessages message = new ConversationMessages();
                message.setConversationID(conversationID);
                message.setDate(date);
                message.setTime(time);
                message.setRecipient(recipient);
                message.setBody(body);
                message.setSuccess(success);
                message = persist(message);
                return message;
            }
        return null;
    }

    public static ConversationMessages merge (ConversationMessages message) {
        if (message != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                message = em.merge(message);
                trans.commit();
                return message;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static ConversationMessages persist (ConversationMessages message) {
        if (message != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(message);
                trans.commit();
                return message;
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static boolean delete(int messageID) {
        if (messageID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            ConversationMessages message = em.find(ConversationMessages.class, messageID);
            if (message != null) {
                EntityTransaction trans = em.getTransaction();
                try {
                    trans.begin();
                    em.remove(message);
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

}
