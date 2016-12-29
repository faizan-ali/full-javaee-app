package co.workamerica.functionality.persistence;

import co.workamerica.entities.administrators.Administrators;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.CustomUtilities;

import javax.persistence.*;

public class AdministratorPersistence {

	private static final CustomUtilities custom = new CustomUtilities();

	public static Administrators login (String email, String password)
			throws NoResultException, NonUniqueResultException, PersistenceException{
		if (email != null && password != null && !email.isEmpty() && !password.isEmpty()) {
			EntityManager em = EMFUtil.getEMFactory().createEntityManager();
			EntityTransaction trans = em.getTransaction();
			String loginQuery = "SELECT admin FROM Administrators admin WHERE admin.username = :username";
			TypedQuery<Administrators> qLogin = em.createQuery(loginQuery, Administrators.class);
			qLogin.setParameter("username", email);
			
			Administrators administrator = qLogin.getSingleResult();
			em.close();
			
			if (administrator != null && custom.hashPassword(password, custom.hexToBytes(administrator.getSalt()))[0]
					.equals(administrator.getPassword())) {
				return administrator;
			}
		}
		return null;
	}
}
