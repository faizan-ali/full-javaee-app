package co.workamerica.functionality.shared;

import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import java.util.HashMap;
import java.util.Map;

/* 
 * This class provides a thread-safe method to obtain entity managers on a non-full Java EE server
 */

public class EMFUtil {

	private static Map<String, String> env = System.getenv();
	private static Map<String, Object> configOverrides = new HashMap<String, Object>();

    private static EntityManagerFactory emf = buildFactory();

	public static EntityManagerFactory getEMFactory() {
		return emf;
	}

    private static EntityManagerFactory buildFactory () {
        configOverrides.put("javax.persistence.jdbc.user", System.getProperty("DATABASE_USERNAME"));
        configOverrides.put("javax.persistence.jdbc.password", System.getProperty("DATABASE_PASSWORD"));
        configOverrides.put("javax.persistence.jdbc.url", System.getProperty("DATABASE_CONNECTION"));
        return Persistence.createEntityManagerFactory("FinalPlatform", configOverrides);
    }
}
