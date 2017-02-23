package models;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table
public class Documents extends Model {

	private static final long serialVersionUID = -3489222817012355083L;

	@Column
	private String docDate;

	@Column
	private String docInsertedDate;

	@Column
	private String docName;

	@Column
	private String docServerPath;

	@ManyToOne
	@JoinColumn(name = "docType")
	private DocTypes docType;

	@ManyToOne
	@JoinColumn(name = "docInsertedEmployee")
	private Employee docInsertedEmployee;

	@ManyToOne
	@JoinColumn(name = "mailOrdDocuments")
	private MailOrder mailOrdDocuments;

	@ManyToOne(cascade = { CascadeType.REFRESH })
	@JoinColumn(name = "DocStatusName")
	private DocStatuses DocStatusName;

	//////////////////////

	public String getDocDate() {
		return docDate;
	}

	public String getDocInsertedDate() {
		return docInsertedDate;
	}

	public String getDocName() {
		return docName;
	}

	public String getDocServerPath() {
		return docServerPath;
	}

	////////////////////////////////

	public DocTypes getDocType() {
		return docType;
	}

	public DocStatuses getDocStatus() {
		return DocStatusName;
	}

	public Employee getDocInsertedEmployee() {
		return docInsertedEmployee;
	}

	//////////////////////////////////

	public String getDocTypeName() {
		return docType.getDocTypeName();
	}

	public String getDocInsertedEmployeeSurname() {
		return docInsertedEmployee.getEmpSurname();
	}

	public String getDocStatusName() {
		return DocStatusName.getDocStatusName();
	}

	public void setDocServerPath(String docServerPath) {
		this.docServerPath = docServerPath;
	}
	/////////////////////////////

	public void setDocStatusName(DocStatuses docStatusName) {
		this.DocStatusName = docStatusName;
	}

	public void setDocType(DocTypes docType) {
		this.docType = docType;
	}

	public void setDocInsertedEmployee(Employee docInsertedEmployee) {
		this.docInsertedEmployee = docInsertedEmployee;
	}

	////////////////////////////

	public void setDocInsertedDate(String docInsertedDate) {
		this.docInsertedDate = docInsertedDate;
	}

	public void setDocDate(String docDate) {
		this.docDate = docDate;
	}

	public void setDocName(String docName) {
		this.docName = docName;
	}

	public MailOrder getMailOrdDocuments() {
		return mailOrdDocuments;
	}

	public void setMailOrdDocuments(MailOrder mailOrdDocuments) {
		this.mailOrdDocuments = mailOrdDocuments;
	}

	@Override
	public String toString() {
		return super.getId() + " | " + getDocType() + " | " + getDocDate() + " | " + getDocInsertedDate() + " | " + getDocName() + " | " + getDocStatus() + " | " + getDocInsertedEmployee();
	}
}
