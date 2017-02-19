package models;

import java.util.HashSet;
import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table
public class Employee extends Model {

	private static final long serialVersionUID = 9010402571140471816L;

	@Column
	private String empSurname;

	@Column
	private String empFirstname;

	@Column
	private String empFathername;

	@Column
	private String empPosition;

	@Column
	private String empEmail;

	@Column
	private String empPhone;

	@Column
	private String empPhoneAdditionalDigits;

	@Column
	private String empRegion;

	@Column
	private String empCity;

	@Column
	private String empStreet;

	@Column
	private String empHouse;

	@Column
	private String empOffice;

	@Column
	private String empHousePart;

	@OneToMany(mappedBy = "mailOrdReceiverNo")
	private Set<MailOrder> receiver_in_MailOrders = new HashSet<MailOrder>();
	@OneToMany(mappedBy = "mailOrdSenderNo")
	private Set<MailOrder> sender_in_MailOrders = new HashSet<MailOrder>();

	@OneToMany(mappedBy = "docInsertedEmployee")
	private Set<Documents> insertedEmployeeSurname_in_Documents = new HashSet<Documents>();

	public Employee() {

	}

	public String getEmpSurname() {
		return empSurname;
	}

	public String getEmpFirstname() {
		return empFirstname;
	}

	public String getEmpFathername() {
		return empFathername;
	}

	public String getEmpPosition() {
		return empPosition;
	}

	public String getEmpEmail() {
		return empEmail;
	}

	public String getEmpPhone() {
		return empPhone;
	}

	public String getEmpPhoneAdditionalDigits() {
		return empPhoneAdditionalDigits;
	}

	public String getEmpRegion() {
		return empRegion;
	}

	public String getEmpCity() {
		return empCity;
	}

	public String getEmpStreet() {
		return empStreet;
	}

	public String getEmpHouse() {
		return empHouse;
	}

	public String getEmpOffice() {
		return empOffice;
	}

	public String getEmpHousePart() {
		return empHousePart;
	}

	public void setEmpSurname(String newEmpSurname) {
		this.empSurname = newEmpSurname;
	}

	public void setEmpFirstname(String empFirstname) {
		this.empFirstname = empFirstname;
	}

	public void setEmpFathername(String empFathername) {
		this.empFathername = empFathername;
	}

	public void setEmpPosition(String empPosition) {
		this.empPosition = empPosition;
	}

	public void setEmpEmail(String empEmail) {
		this.empEmail = empEmail;
	}

	public void setEmpPhone(String empPhone) {
		this.empPhone = empPhone;
	}

	public void setEmpPhoneAdditionalDigits(String empPhoneAdditionalDigits) {
		this.empPhoneAdditionalDigits = empPhoneAdditionalDigits;
	}

	public void setEmpRegion(String empRegion) {
		this.empRegion = empRegion;
	}

	public void setEmpCity(String empCity) {
		this.empCity = empCity;
	}

	public void setEmpStreet(String empStreet) {
		this.empStreet = empStreet;
	}

	public void setEmpHouse(String empHouse) {
		this.empHouse = empHouse;
	}

	public void setEmpOffice(String empOffice) {
		this.empOffice = empOffice;
	}

	public void setEmpHousePart(String empHousePart) {
		this.empHousePart = empHousePart;
	}

	public Set<MailOrder> getReceiver_in_MailOrders() {
		return receiver_in_MailOrders;
	}

	public Set<MailOrder> getSender_in_MailOrders() {
		return sender_in_MailOrders;
	}

	public void setReceiver_in_MailOrders(Set<MailOrder> receiver_in_MailOrders) {
		this.receiver_in_MailOrders = receiver_in_MailOrders;
	}

	public void setSender_in_MailOrders(Set<MailOrder> sender_in_MailOrders) {
		this.sender_in_MailOrders = sender_in_MailOrders;
	}

	@Override
	public String toString() {
		return super.getId() + " | " + getEmpSurname() + " | " + getEmpFirstname() + " | " + getEmpFathername() + " | " + getEmpCity() + " | " + getEmpEmail() + " | " + getEmpHouse() + " | " + getEmpOffice() + " | " + getEmpPhone() + " | "
				+ getEmpPosition() + " | " + getEmpRegion() + " | " + getEmpStreet() + " | " + getEmpPhoneAdditionalDigits() + " | " + getEmpHousePart();
	}
}
