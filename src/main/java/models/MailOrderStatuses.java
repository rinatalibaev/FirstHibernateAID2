package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class MailOrderStatuses extends Model {

	private static final long serialVersionUID = -6861941658032674129L;

	@Column
	private String MailOrderStatus;

	@OneToMany(mappedBy = "MailOrdStatus")
	private Set<MailOrder> mailOrders_with_status = new HashSet<MailOrder>();

	public MailOrderStatuses() {
		super();
		// TODO Auto-generated constructor stub
	}

	public MailOrderStatuses(int id) {
		super(id);
		// TODO Auto-generated constructor stub
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public String getMailOrderStatus() {
		return MailOrderStatus;
	}

	public Set<MailOrder> getMailOrders_with_status() {
		return mailOrders_with_status;
	}

	public void setMailOrderStatus(String mailOrderStatus) {
		MailOrderStatus = mailOrderStatus;
	}

	public void setMailOrders_with_status(Set<MailOrder> mailOrders_with_status) {
		this.mailOrders_with_status = mailOrders_with_status;
	}

	@Override
	public String toString() {
		return MailOrderStatus;

	}
}
