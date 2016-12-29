package co.workamerica.functionality.persistence;

import co.workamerica.entities.criteria.States;
import co.workamerica.functionality.shared.EMFUtil;

import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import java.util.List;

public class StateDataAccessObject {

	public static List<States> getAll () {
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();
		String stateQuery = "SELECT state FROM States state ORDER BY state.name ASC";
		try {
			TypedQuery<States> qState = em.createQuery(stateQuery, States.class);
            return qState.getResultList();
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
            em.close();
        }
		return null;
	}
}
