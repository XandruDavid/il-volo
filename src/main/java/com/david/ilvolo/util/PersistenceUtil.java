package com.david.ilvolo.util;

import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class PersistenceUtil {

	private static EntityManagerFactory entityManagerFactory;

	public static void buildEntityManagerFactory() {
		if (entityManagerFactory != null) {
			return;
		}
		
		Map<String, String> env = System.getenv();
		Map<String, Object> configOverrides = new HashMap<String, Object>();
		for (String envName : env.keySet()) {
		    if (envName.contains("JDBC_DATABASE_URL")) {
		        configOverrides.put("hibernate.connection.url", env.get(envName));    
		    }
		    if (envName.contains("JDBC_DATABASE_USERNAME")) {
		        configOverrides.put("hibernate.connection.username", env.get(envName));    
		    }
		    if (envName.contains("JDBC_DATABASE_PASSWORD")) {
		        configOverrides.put("hibernate.connection.password", env.get(envName));    
		    }
		}
		
		entityManagerFactory = Persistence.createEntityManagerFactory("ilvolo", configOverrides);
	}

	public static EntityManager getEntityManager() {
		if(entityManagerFactory == null)  {
			buildEntityManagerFactory();
		}
		return entityManagerFactory.createEntityManager();
	}

	public static void killEntityManagerFactory() {
		if (entityManagerFactory != null) {
			entityManagerFactory.close();
		}
	}

}