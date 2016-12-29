package co.workamerica.functionality.persistence;

import co.workamerica.entities.statistics.WeeklyStats;
import co.workamerica.functionality.shared.EMFUtil;
import co.workamerica.functionality.shared.utilities.Clock;

import javax.persistence.EntityManager;
import javax.persistence.EntityTransaction;
import javax.persistence.TypedQuery;
import java.util.List;

public class WeeklyStatsDataAccessObject {
	
	public static List<WeeklyStats> getAll () {
		EntityManager em = EMFUtil.getEMFactory().createEntityManager();
		String statsQuery = "SELECT stats FROM WeeklyStats stats ORDER BY stats.weeklyStatsID DESC";
		TypedQuery<WeeklyStats> qStats = em.createQuery(statsQuery, WeeklyStats.class);
		List<WeeklyStats> list = qStats.getResultList();
        em.close();
        return list;
	}
	
	public static WeeklyStats getLatest () {
        EntityManager em = EMFUtil.getEMFactory().createEntityManager();
		String statsQuery = "SELECT stats FROM WeeklyStats stats ORDER BY stats.weeklyStatsID DESC";
		TypedQuery<WeeklyStats> qStats = em.createQuery(statsQuery, WeeklyStats.class).setMaxResults(1);
		WeeklyStats stats = qStats.getResultList().get(0);
        em.close();
        return stats;
	}
	
	public static WeeklyStats merge (WeeklyStats stats) {
		if (stats != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                stats = em.merge(stats);
                trans.commit();
                return stats;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
		}
		return null;
	}
	
	public static WeeklyStats persist (WeeklyStats stats) {
		if (stats != null) {
            EntityManager em = EMFUtil.getEMFactory().createEntityManager();
            EntityTransaction trans = em.getTransaction();
            try {
                trans.begin();
                em.persist(stats);
                trans.commit();
                return stats;
            } catch (Exception e) {
                e.printStackTrace();
                trans.rollback();
            } finally {
                em.close();
            }
		}
		return null;
	}
	
	public static WeeklyStats updateTotalProfiles (WeeklyStats stats, int amount) {
		if (stats != null && amount >= 0) {
			stats.setTotalProfiles(amount);
			stats.setTotalCompleteProfiles(CandidatePersistence.getAllCompleteProfiles(CandidatePersistence.getAll()).size());
			return merge(stats);
		}
		return null;
	}
	
	public static WeeklyStats createWeeklyStats (int allProfiles, int completeProfiles) {
		if (allProfiles > 0) {
			WeeklyStats stats = new WeeklyStats();
			stats.setTotalProfiles(allProfiles);
			stats.setTotalCompleteProfiles(completeProfiles);
			stats.setDate(Clock.getCurrentDate());
			return persist(stats);
		}
		return null;
	}

	public static WeeklyStats getByID (int weeklyStatsID) {
		if (weeklyStatsID > 0) {
			EntityManager em = EMFUtil.getEMFactory().createEntityManager();
			WeeklyStats stats = em.find(WeeklyStats.class, weeklyStatsID);
			em.close();
			return stats;
		}
		return null;
	}
}
