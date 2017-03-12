package controllers;

import javax.persistence.TransactionRequiredException;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import models.Employee;

public class DBEmployeeEditingController extends DatabaseEditingWindowController {

	Employee employee;
	Stage stage;
	DBEmployeeWindowController parentController = null;

	@FXML
	TextField empSurnameTextField;

	@FXML
	TextField empFirstnameTextField;

	@FXML
	TextField empFathernameTextField;

	@FXML
	TextField empPositionTextField;

	@FXML
	TextField empEmailTextField;

	@FXML
	TextField empPhoneTextField;

	@FXML
	TextField empPhoneAddTextField;

	@FXML
	TextField empRegionTextField;

	@FXML
	TextField empCityTextField;

	@FXML
	TextField empStreetTextField;

	@FXML
	TextField empHouseTextField;

	@FXML
	TextField empHousePartTextField;

	@FXML
	TextField empOfficeTextField;

	@FXML
	Button okButton;

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	}

	public void add(Event mouseEvent) {
		Session session = sessionExtracting();

		try {
			session.beginTransaction();
			String sql = "INSERT INTO Employee (empSurname, empFirstname, empFathername, empPosition, empEmail, empPhone, empPhoneAdditionalDigits, empRegion, empCity, empStreet, empHouse, empHousePart, empOffice) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter(0, empSurnameTextField.getText());
			query.setParameter(1, empFirstnameTextField.getText());
			query.setParameter(2, empFathernameTextField.getText());
			query.setParameter(3, empPositionTextField.getText());
			query.setParameter(4, empEmailTextField.getText());
			query.setParameter(5, empPhoneTextField.getText());
			query.setParameter(6, empPhoneAddTextField.getText());
			query.setParameter(7, empRegionTextField.getText());
			query.setParameter(8, empCityTextField.getText());
			query.setParameter(9, empStreetTextField.getText());
			query.setParameter(10, empHouseTextField.getText());
			query.setParameter(11, empHousePartTextField.getText());
			query.setParameter(12, empOfficeTextField.getText());
			query.executeUpdate();
			session.getTransaction().commit();
			parentController.initialize();
			parentController.DBEmpTable.refresh();
			okButton.getScene().getWindow().hide();
		} catch (TransactionRequiredException tre) {
			tre.printStackTrace();
		} finally {
			session.close();
			// sessionFactory.close();
		}

	}

	public void update() {
		Session session = sessionExtracting();

		try {
			session.beginTransaction();
			String hql = "UPDATE Employee set empSurname = :empSurname, empFirstname = :empFirstname, empFathername = :empFathername, empPosition = :empPosition, empEmail = :empEmail, empPhone = :empPhone, empPhoneAdditionalDigits = :empPhoneAdditionalDigits, empRegion = :empRegion, empCity = :empCity, empStreet = :empStreet, empHouse = :empHouse, empHousePart = :empHousePart, empOffice = :empOffice WHERE id = :employee_id";
			Query query = session.createQuery(hql);
			query.setParameter("empSurname", empSurnameTextField.getText());
			query.setParameter("empFirstname", empFirstnameTextField.getText());
			query.setParameter("empFathername", empFathernameTextField.getText());
			query.setParameter("empPosition", empPositionTextField.getText());
			query.setParameter("empEmail", empEmailTextField.getText());
			query.setParameter("empPhone", empPhoneTextField.getText());
			query.setParameter("empPhoneAdditionalDigits", empPhoneAddTextField.getText());
			query.setParameter("empRegion", empRegionTextField.getText());
			query.setParameter("empCity", empCityTextField.getText());
			query.setParameter("empStreet", empStreetTextField.getText());
			query.setParameter("empHouse", empHouseTextField.getText());
			query.setParameter("empHousePart", empHousePartTextField.getText());
			query.setParameter("empOffice", empOfficeTextField.getText());
			query.setParameter("employee_id", employee.getId());
			query.executeUpdate();
			session.getTransaction().commit();
			parentController.initialize();
			parentController.DBEmpTable.refresh();
			okButton.getScene().getWindow().hide();
		} catch (TransactionRequiredException tre) {
			tre.printStackTrace();
		} finally {
			session.close();
			// sessionFactory.close();
		}
	}

	public void setEmployee(Employee selectedEmployee) {
		employee = selectedEmployee;
		empSurnameTextField.setText(selectedEmployee.getEmpSurname());
		empFirstnameTextField.setText(selectedEmployee.getEmpFirstname());
		empFathernameTextField.setText(selectedEmployee.getEmpFathername());
		empPositionTextField.setText(selectedEmployee.getEmpPosition());
		empEmailTextField.setText(selectedEmployee.getEmpEmail());
		empPhoneTextField.setText(selectedEmployee.getEmpPhone());
		empPhoneAddTextField.setText(selectedEmployee.getEmpPhoneAdditionalDigits());
		empRegionTextField.setText(selectedEmployee.getEmpRegion());
		empCityTextField.setText(selectedEmployee.getEmpCity());
		empStreetTextField.setText(selectedEmployee.getEmpStreet());
		empHouseTextField.setText(selectedEmployee.getEmpHouse());
		empHousePartTextField.setText(selectedEmployee.getEmpHousePart());
		empOfficeTextField.setText(selectedEmployee.getEmpOffice());
	}
}
