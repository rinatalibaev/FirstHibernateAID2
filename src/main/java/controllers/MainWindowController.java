package controllers;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.Employee;

public class MainWindowController extends WindowController {

	@FXML
	ImageView idImagesZayavki = new ImageView();

	@FXML
	ImageView empImageView = new ImageView();

	@FXML
	TableView<Employee> mainTable1;

	@FXML
	TableColumn<Employee, String> mainTable1Column1;

	@FXML
	TableColumn<Employee, String> mainTable1Column2;

	@FXML
	TableColumn<Employee, String> mainTable1Column3;

	@FXML
	TableColumn<Employee, String> mainTable1Column4;

	@FXML
	TableColumn<Employee, String> mainTable1Column5;

	@FXML
	TableColumn<Employee, String> mainTable1Column6;

	@FXML
	TableColumn<Employee, String> mainTable1Column7;

	@FXML
	TableColumn<Employee, String> mainTable1Column8;

	@FXML
	TableColumn<Employee, String> mainTable1Column9;

	@FXML
	TableColumn<Employee, String> mainTable1Column10;

	@FXML
	TableColumn<Employee, String> mainTable1Column11;

	@FXML
	TableColumn<Employee, String> mainTable1Column12;

	@FXML
	TableColumn<Employee, String> mainTable1Column13;

	@FXML
	public void initialize() {
		mainTable1Column1.setCellValueFactory(new PropertyValueFactory<Employee, String>("empSurname"));
		mainTable1Column2.setCellValueFactory(new PropertyValueFactory<Employee, String>("empFirstname"));
		mainTable1Column3.setCellValueFactory(new PropertyValueFactory<Employee, String>("empFathername"));
		mainTable1Column4.setCellValueFactory(new PropertyValueFactory<Employee, String>("empPosition"));
		mainTable1Column5.setCellValueFactory(new PropertyValueFactory<Employee, String>("empEmail"));
		mainTable1Column6.setCellValueFactory(new PropertyValueFactory<Employee, String>("empPhone"));
		mainTable1Column7.setCellValueFactory(new PropertyValueFactory<Employee, String>("empPhoneAdditionalDigits"));
		mainTable1Column8.setCellValueFactory(new PropertyValueFactory<Employee, String>("empRegion"));
		mainTable1Column9.setCellValueFactory(new PropertyValueFactory<Employee, String>("empCity"));
		mainTable1Column10.setCellValueFactory(new PropertyValueFactory<Employee, String>("empStreet"));
		mainTable1Column11.setCellValueFactory(new PropertyValueFactory<Employee, String>("empHouse"));
		mainTable1Column12.setCellValueFactory(new PropertyValueFactory<Employee, String>("empHousePart"));
		mainTable1Column13.setCellValueFactory(new PropertyValueFactory<Employee, String>("empOffice"));
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ObservableList<Employee> employees = null;
		try {
			Criteria criteria = session.createCriteria(Employee.class);
			employees = FXCollections.observableList(criteria.list());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		mainTable1.setItems(employees);
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		stageBuilder("../views/MainWindow.fxml", 1300, 640, "АС административного сопровождения - ТЭЦ-3 - Гатин Ильдар Рашитович", 1000, 640, 20, 20);
	}

	public void openDBEmployees(MouseEvent mouseEvent) {
		DBEmployeeWindowController dbEmployeeWindowController = new DBEmployeeWindowController();
		Window parentWindow = ((Node) mouseEvent.getSource()).getScene().getWindow();
		try {
			dbEmployeeWindowController.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void openDBDocuments(MouseEvent event) {
		DBDocumentWindowController dbDocumentWindowController = new DBDocumentWindowController();
		Window parentWindow = ((Node) event.getSource()).getScene().getWindow();
		try {
			dbDocumentWindowController.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void openDBMailOrders(MouseEvent event) {
		DBMailOrdersController dbMailOrdersController = new DBMailOrdersController();
		try {
			dbMailOrdersController.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
