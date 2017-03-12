package models;

import java.sql.Timestamp;
import java.time.LocalDate;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class MailOrder extends Model {

	private static final long serialVersionUID = 1L;

	@Column
	private LocalDate mailOrdToSendDate;

	@Column
	private Timestamp mailOrdSentDate;

	@Column
	private Timestamp mailOrdCreateDate;

	@Column
	private Timestamp mailOrdVizovKurieraDate;

	@Column
	private Timestamp mailOrdReceivedDate;

	@ManyToOne
	@JoinColumn(name = "mailOrdStatus")
	private MailOrderStatuses MailOrdStatus;

	@ManyToOne
	@JoinColumn(name = "mailOrdSenderNo")
	private Employee mailOrdSenderNo;

	@ManyToOne
	@JoinColumn(name = "mailOrdReceiverNo")
	private Employee mailOrdReceiverNo;

	@ManyToOne
	@JoinColumn(name = "mailOrdEndReceiverNo")
	private Employee mailOrdEndReceiverNo;

	@Column
	private String mailOrdDocuments;

	public MailOrder() {
		super();
	}

	public MailOrder(int id) {
		super(id);
	}

	public static long getSerialversionuid() {
		return serialVersionUID;
	}

	public LocalDate getMailOrdToSendDate() {
		return mailOrdToSendDate;
	}

	public Timestamp getMailOrdSentDate() {
		return mailOrdSentDate;
	}

	public Timestamp getMailOrdCreateDate() {
		return mailOrdCreateDate;
	}

	public void setMailOrdToSendDate(LocalDate mailOrdToSendDate) {
		this.mailOrdToSendDate = mailOrdToSendDate;
	}

	public void setMailOrdSentDate(Timestamp mailOrdSentDate) {
		this.mailOrdSentDate = mailOrdSentDate;
	}

	public void setMailOrdCreateDate(Timestamp mailOrdCreateDate) {
		this.mailOrdCreateDate = mailOrdCreateDate;
	}

	public Employee getMailOrdReceiverNo() {
		return mailOrdReceiverNo;
	}

	public void setMailOrdReceiverNo(Employee mailOrdReceiverNo) {
		this.mailOrdReceiverNo = mailOrdReceiverNo;
	}

	public Employee getMailOrdSenderNo() {
		return mailOrdSenderNo;
	}

	public void setMailOrdSenderNo(Employee mailOrdSenderNo) {
		this.mailOrdSenderNo = mailOrdSenderNo;
	}

	public MailOrderStatuses getMailOrdStatus() {
		return MailOrdStatus;
	}

	public void setMailOrdStatus(MailOrderStatuses mailOrdStatus) {
		MailOrdStatus = mailOrdStatus;
	}

	public Employee getMailOrdEndReceiverNo() {
		return mailOrdEndReceiverNo;
	}

	public void setMailOrdEndReceiverNo(Employee mailOrdEndReceiverNo) {
		this.mailOrdEndReceiverNo = mailOrdEndReceiverNo;
	}

	public String getMailOrdDocuments() {
		return mailOrdDocuments;
	}

	public void setMailOrdDocuments(String mailOrdDocuments) {
		this.mailOrdDocuments = mailOrdDocuments;
	}

	public Timestamp getMailOrdVizovKurieraDate() {
		return mailOrdVizovKurieraDate;
	}

	public void setMailOrdVizovKurieraDate(Timestamp mailOrdVizovKurieraDate) {
		this.mailOrdVizovKurieraDate = mailOrdVizovKurieraDate;
	}

	public Timestamp getMailOrdReceivedDate() {
		return mailOrdReceivedDate;
	}

	public void setMailOrdReceivedDate(Timestamp mailOrdReceivedDate) {
		this.mailOrdReceivedDate = mailOrdReceivedDate;
	}

}
