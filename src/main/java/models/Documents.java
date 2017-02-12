package models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
public class Documents extends Model {

	private static final long serialVersionUID = -3489222817012355083L;

	@Column
	private String docType;

	@Column
	private String docDate;

	@Column
	private String docInsertedDate;

	@Column
	private String docName;

	@Column
	private String docStatus;

	@Column
	private String docInsertedEmployee;

	// @OneToMany(mappedBy = "mailOrdReceiverNo")
	// private Set<MailOrder> receiver_in_MailOrders = new HashSet<MailOrder>();
	// @OneToMany(mappedBy = "mailOrdSenderNo")
	// private Set<MailOrder> sender_in_MailOrders = new HashSet<MailOrder>();

	public Documents() {

	}

	public String getDocType() {
		return docType;
	}

	public String getDocDate() {
		return docDate;
	}

	public String getDocInsertedDate() {
		return docInsertedDate;
	}

	public String getDocName() {
		return docName;
	}

	public String getDocStatus() {
		return docStatus;
	}

	public String getDocInsertedEmployee() {
		return docInsertedEmployee;
	}

	public void setDocType(String docType) {
		this.docType = docType;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public void setDocInsertedDate(String docInsertedDate) {
		this.docInsertedDate = docInsertedDate;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public void setDocStatus(String docStatus) {
		this.docStatus = docStatus;
	}

	public void setDocInsertedEmployee(String docInsertedEmployee) {
		this.docInsertedEmployee = docInsertedEmployee;
	}

	// public Set<MailOrder> getReceiver_in_MailOrders() {
	// return receiver_in_MailOrders;
	// }
	//
	// public Set<MailOrder> getSender_in_MailOrders() {
	// return sender_in_MailOrders;
	// }
	//
	// public void setReceiver_in_MailOrders(Set<MailOrder>
	// receiver_in_MailOrders) {
	// this.receiver_in_MailOrders = receiver_in_MailOrders;
	// }
	//
	// public void setSender_in_MailOrders(Set<MailOrder> sender_in_MailOrders)
	// {
	// this.sender_in_MailOrders = sender_in_MailOrders;
	// }

	@Override
	public String toString() {
		return super.getId() + " | " + getDocType() + " | " + getDocDate() + " | " + getDocInsertedDate() + " | " + getDocName() + " | " + getDocStatus() + " | " + getDocInsertedEmployee();
	}
}
