package co.workamerica.functionality.persistence;

import co.workamerica.entities.texts.Conversations;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

public class ConversationDataAccessObject {

    public static Conversations create (int clientID, int candidateID)  {
        if (clientID > 0 && candidateID > 0) {
            Conversations conversation = new Conversations();
            conversation.setClientID(clientID);
            conversation.setCandidateID(candidateID);
            return persist(conversation);
        }
        return null;
    }

    public static Conversations createIfNone(int clientID, int candidateID)  {
        if (clientID > 0 && candidateID > 0) {
            try {
                return getByClientAndCandidateID(clientID, candidateID);
            } catch (NoResultException e) {
                return create(clientID, candidateID);
            } catch (Exception e) {
                e.printStackTrace();
                return null;
            }
        }
        return null;
    }

    public static Conversations getByClientAndCandidateID(int clientID, int candidateID) throws NoResultException {
        if (clientID > 0 && candidateID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT c FROM Conversations c WHERE c.clientID = :clientID AND c.candidateID = :candidateID";
            try {
                TypedQuery<Conversations> q = em.createQuery(query, Conversations.class);
                q.setParameter("clientID", clientID);
                q.setParameter("candidateID", candidateID);
                Conversations conversation = q.getSingleResult();
                em.close();
                return conversation;
            } finally {
                if (em.isOpen()) {
                    em.close();
                }
            }
        }
        return null;
    }

    public static Conversations getByID (int conversationID) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            Conversations conversation = em.find(Conversations.class, conversationID);
            em.close();
            return conversation;
    }
    public static Conversations merge (Conversations conversation) {
        if (conversation != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                conversation = em.merge(conversation);
                trans.commit();
                return conversation;
            } catch (Exception e) {
                trans.rollback();
                e.printStackTrace();
                return null;
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static Conversations persist (Conversations conversation) {
        if (conversation != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(conversation);
                trans.commit();
                return conversation;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static boolean delete(int conversationID) {
        if (conversationID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            Conversations conversation = em.find(Conversations.class, conversationID);
            if (conversation != null) {
                EntityTransaction trans = em.getTransaction();
                try {
                    trans.begin();
                    em.remove(conversation);
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

