package controllers;

import java.io.IOException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Employee;

public class DBEmployeeWindowController extends DatabaseViewingWindowController {

	@FXML
	TableView<Employee> DBEmpTable;

	@FXML
	TableColumn<Employee, String> DBEmpTableId;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn1;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn2;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn3;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn4;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn5;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn6;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn7;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn8;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn9;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn10;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn11;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn12;

	@FXML
	TableColumn<Employee, String> DBEmpTableColumn13;

	private String delete_hql_query = "DELETE FROM Employee WHERE id = :id";

	@FXML
	public void initialize() {
		DBEmpTableId.setCellValueFactory(new PropertyValueFactory<Employee, String>("id"));
		DBEmpTableColumn1.setCellValueFactory(new PropertyValueFactory<Employee, String>("empSurname"));
		DBEmpTableColumn2.setCellValueFactory(new PropertyValueFactory<Employee, String>("empFirstname"));
		DBEmpTableColumn3.setCellValueFactory(new PropertyValueFactory<Employee, String>("empFathername"));
		DBEmpTableColumn4.setCellValueFactory(new PropertyValueFactory<Employee, String>("empPosition"));
		DBEmpTableColumn5.setCellValueFactory(new PropertyValueFactory<Employee, String>("empEmail"));
		DBEmpTableColumn6.setCellValueFactory(new PropertyValueFactory<Employee, String>("empPhone"));
		DBEmpTableColumn7.setCellValueFactory(new PropertyValueFactory<Employee, String>("empPhoneAdditionalDigits"));
		DBEmpTableColumn8.setCellValueFactory(new PropertyValueFactory<Employee, String>("empRegion"));
		DBEmpTableColumn9.setCellValueFactory(new PropertyValueFactory<Employee, String>("empCity"));
		DBEmpTableColumn10.setCellValueFactory(new PropertyValueFactory<Employee, String>("empStreet"));
		DBEmpTableColumn11.setCellValueFactory(new PropertyValueFactory<Employee, String>("empHouse"));
		DBEmpTableColumn12.setCellValueFactory(new PropertyValueFactory<Employee, String>("empHousePart"));
		DBEmpTableColumn13.setCellValueFactory(new PropertyValueFactory<Employee, String>("empOffice"));
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ObservableList<Employee> employees = null;
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			employees = FXCollections.observableList(criteria.list());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		DBEmpTable.setItems(employees);
		DBEmpTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						editingEntry(mouseEvent);
					}
				}
			}

		});
	}

	@Override
	public void start(Stage primarystage) {
		stageBuilder("../views/DBEmployeeWindow.fxml", 1100, 285, "АС административного сопровождения - База данных - Сотрудники", 1100, 285, 125, 200);
	}

	@FXML
	public void addingEntry() {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Stage stage = new Stage();
			Scene scene = null;
			DBEmployeeEditingController dbEmployeeEditingController;
			Parent fxmlEdit;
			fxmlLoader.setLocation(getClass().getResource("../views/DBEmployeeEditing.fxml"));
			fxmlEdit = fxmlLoader.load();
			dbEmployeeEditingController = fxmlLoader.getController();
			dbEmployeeEditingController.parentController = this;
			dbEmployeeEditingController.okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					dbEmployeeEditingController.add(event);
				}
			});
			scene = new Scene(fxmlEdit);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void editingEntry(Event mouseEvent) {
		Employee selectedEmployee = (Employee) DBEmpTable.getSelectionModel().getSelectedItem();
		Window parentWindow = ((Node) mouseEvent.getSource()).getScene().getWindow();

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Stage stage = new Stage();
			Scene scene = null;
			DBEmployeeEditingController dbEmployeeEditingController;
			Parent fxmlEdit;
			fxmlLoader.setLocation(getClass().getResource("../views/DBEmployeeEditing.fxml"));
			fxmlEdit = fxmlLoader.load();
			dbEmployeeEditingController = fxmlLoader.getController();
			dbEmployeeEditingController.parentController = this;
			scene = new Scene(fxmlEdit);
			dbEmployeeEditingController.setEmployee(selectedEmployee);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void DBEmployeeDeleting() {
		Employee selectedEmployee = (Employee) DBEmpTable.getSelectionModel().getSelectedItem();
		selectedEmployee.delete(delete_hql_query);
		initialize();
		DBEmpTable.refresh();
	}

	public void closeDBEmployeeWindow(ActionEvent actionEvent) {
		((Node) actionEvent.getSource()).getScene().getWindow().hide();
	}
}
