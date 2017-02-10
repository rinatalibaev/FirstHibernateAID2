package controllers;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
	private static SessionFactory sessionFactory = null;

	static {
		Configuration cfg = new Configuration().configure();
		// Из-за этого Hibernate не создавал таблицу в MySQL
		// StandardServiceRegistryBuilder builder = new
		// StandardServiceRegistryBuilder().applySettings(cfg.getProperties());
		// sessionFactory = cfg.buildSessionFactory(builder.build());
		sessionFactory = cfg.buildSessionFactory();
	}

	public static SessionFactory getSessionFactory() {
		return sessionFactory;
	}

}
