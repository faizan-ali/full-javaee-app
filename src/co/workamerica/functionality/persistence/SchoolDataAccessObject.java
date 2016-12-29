package co.workamerica.functionality.persistence;

import co.workamerica.entities.criteria.Schools;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class SchoolDataAccessObject {
	
	public static List<Schools> getAll ()  {
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();

		try {
			String schoolQuery = "SELECT s FROM Schools s ORDER BY s.name ASC";
			TypedQuery<Schools> qSchool = em.createQuery(schoolQuery, Schools.class);
			return qSchool.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			em.close();
		}
		return null;
	}

	public static List<String> getAllNames () {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
		String query = "SELECT s.name FROM Schools s";
		TypedQuery<String> q = em.createQuery(query, String.class);
		List<String> list = q.getResultList();
        em.close();
        return list;
	}
	
	public static Schools getSchoolByID (int schoolID) {
        if (schoolID > 0) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            Schools school = em.find(Schools.class, schoolID);
            em.close();
            return school;
        }
        return null;
	}
	
	public static Schools getSchoolByName (String name) {
		if (name != null && !name.isEmpty()) {
			EntityManager em = EMFUtil.getEMFactory().createEntityManager();
			String query = "SELECT school from Schools school where school.name = :school";
			TypedQuery<Schools> q = em.createQuery(query, Schools.class);

			try {
				q.setParameter("school", name);
				return q.getSingleResult();
			} catch (Exception e) {
				e.printStackTrace();
			} finally {
				em.close();
			}
		}
		return null;
	}
	
	public static int getSchoolIDByName (String school) {
		if (school != null && !school.isEmpty()) {
			Schools entity = getSchoolByName(school);
            if (entity != null) {
                return entity.getSchoolID();
            }
		}
		return 0;
	}
}
