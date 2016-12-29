package co.workamerica.functionality.persistence;

import co.workamerica.entities.criteria.Certifications;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.ArrayList;
import java.util.List;

public class CertificationPersistence {

    public static List<Certifications> getAll() {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        String certificationQuery = "SELECT certs FROM Certifications certs ORDER BY certs.certification ASC";
        try {
            TypedQuery<Certifications> qCertification = em.createQuery(certificationQuery, Certifications.class);
            return qCertification.getResultList();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }

    public static List<String> getAllNames() {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        String query = "SELECT cert.certification FROM Certifications cert";
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

    public static ArrayList<Integer> getIDListByCommaSeparatedNames(String certificationNames) {
        if (certificationNames != null && !certificationNames.isEmpty()) {
            ArrayList<Integer> list = new ArrayList<>();
            String [] nameList = certificationNames.split(",");
            for (String name : nameList) {
                int certificationID = getIDByName(name);
                if (certificationID > 0) {
                    list.add(certificationID);
                }
            }
            return list;
        }
        return null;
    }

    public static int getIDByName(String name) {
        Certifications certification = getByName(name);
        if (certification != null) {
            return certification.getCertificationID();
        }
        return 0;
    }

    public static Certifications getByName(String name) {
        if (name != null && !name.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT cert from Certifications cert where cert.certification = :name";

            try {
                TypedQuery<Certifications> q = em.createQuery(query, Certifications.class);
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

    public static List<Certifications> getListByIDs(ArrayList<Integer> certificationIDs) {
        if (certificationIDs != null && !certificationIDs.isEmpty()) {
            ArrayList<Certifications> list = new ArrayList<>();
            for (int id : certificationIDs) {
                Certifications certification = getByID(id);
                if (certification != null) {
                    list.add(certification);
                }
            }
            return list;
        }
        return null;
    }

    public static Certifications getByID(int certificationID) {
        if (certificationID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            Certifications certification = em.find(Certifications.class, certificationID);
            em.close();
            return certification;
        }
        return null;
    }
}
