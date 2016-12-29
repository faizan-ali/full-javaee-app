package co.workamerica.functionality.persistence;

import co.workamerica.entities.companies.Companies;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

/**
 * Created by Faizan on 7/13/2016.
 */
public class CompanyDataAccessObject {

    public static Companies persist(Companies company) {
        if (company != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(company);
                trans.commit();
                return company;
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

    public static Companies merge(Companies company) {
        if (company != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                company = em.merge(company);
                trans.commit();
                return company;
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

    public static Companies getByName(String name) {
        if (name != null && !name.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            String query = "SELECT company FROM Companies company where company.name = :name";
            TypedQuery<Companies> q = em.createQuery(query, Companies.class);
            q.setParameter("name", name);
            try {
                Companies company = q.getSingleResult();
                em.close();
                return company;
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return null;
    }

    public static Companies getByID (int companyID) {
        if (companyID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            Companies company = em.find(Companies.class, companyID);
            em.close();
            return company;
        }
        return null;
    }

    public static Companies createCompany (String name, int viewLimit, String geoLimit, int schoolAccountID) {
        if (name != null || !name.isEmpty()) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            Companies company = new Companies();
            company.setName(name);
            company.setGeoLimit(geoLimit);
            company.setViewLimit(viewLimit);
            company.setSchoolAccountID(schoolAccountID);
            company = persist(company);
            return company;
        }
        return null;
    }

    public static List<Companies> getAll () {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
        String query = "SELECT company from Companies company";
        TypedQuery<Companies> q = em.createQuery(query, Companies.class);
        try {
            List<Companies> list = q.getResultList();
            return list;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            em.close();
        }
        return null;
    }
}
