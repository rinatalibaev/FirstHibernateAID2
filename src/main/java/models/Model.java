package models;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.MappedSuperclass;
import javax.persistence.TransactionRequiredException;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import controllers.HibernateUtil;

@MappedSuperclass
public abstract class Model implements Serializable {

	private static final long serialVersionUID = -7828323635299624004L;

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	@Column
	private int id;

	public Model() {

	}

	public Model(int id) {
		this.id = id;
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public void delete(String hql) {
		Session session = sessionExtracting();

		try {
			session.beginTransaction();
			Query query = session.createQuery(hql);
			query.setParameter("id", this.getId());
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (TransactionRequiredException tre) {
			tre.printStackTrace();
		} finally {
			// session.close();
			// sessionFactory.close();
		}
	}

	public static Session sessionExtracting() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		return session;
	}

}
