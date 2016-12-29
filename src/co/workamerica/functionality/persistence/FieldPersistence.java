package co.workamerica.functionality.persistence;

import co.workamerica.entities.criteria.Fields;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class FieldPersistence {

    public static List<Fields> getAll() {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();

        try {
            String fieldQuery = "SELECT f FROM Fields f ORDER BY f.name ASC";
            TypedQuery<Fields> qField = em.createQuery(fieldQuery, Fields.class);
            return qField.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public static List<String> getAllNames() {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        String query = "SELECT field.name FROM Fields field";

        try {
            TypedQuery<String> q = em.createQuery(query, String.class);
            return q.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }

        return null;
    }

    public static int getIDByName(String name) {
        Fields field = getByName(name);
        if (field != null) {
            return field.getFieldID();
        }
        return 0;
    }

    public static Fields getByName(String name) {
        if (name != null && !name.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT field from Fields field where field.name = :name";

            try {
                TypedQuery<Fields> q = em.createQuery(query, Fields.class);
                q.setParameter("name", name);
                return q.getSingleResult();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }
        return null;
    }

    public static ArrayList<Integer> getIDListByCommaSeparatedNames(String fieldNames) {
        if (fieldNames != null && !fieldNames.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            String[] nameList = fieldNames.split(",");
            for (String name : nameList) {
                int fieldID = getIDByName(name);
                if (fieldID > 0) {
                    list.add(fieldID);
                }
            }
            return list;
        }
        return null;
    }

    public static List<String> getFieldNamesByCategory (String category) {
        if (category != null && (category.equals("Allied Health") || category.equals("Skilled Trades") ||
            category.equals("Business/Computer IT"))) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT field.name from Fields field where field.category = :category";
            TypedQuery<String> q = em.createQuery(query, String.class);
            q.setParameter("category", category);

            try {
                return q.getResultList();
            } catch (Exception e) {
                e.printStackTrace();
            } finally {
                em.close();
            }
        }

        return null;
    }
}
