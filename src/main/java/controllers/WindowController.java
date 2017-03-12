package controllers;

import java.io.IOException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.application.Application;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.DocStatuses;
import models.DocTypes;
import models.Employee;

public class WindowController extends Application {

	private static ObservableList<String> employeeFull = null;
	private static ObservableList<Employee> employeeAll = null;
	private static ObservableList<String> DocTypesFull = null;
	private static ObservableList<DocTypes> DocTypesAll = null;
	private static ObservableList<String> DocStatusesFull = null;
	private static ObservableList<DocStatuses> DocStatusesAll = null;

	static {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		Criteria criteriaDocTypes = session.createCriteria(DocTypes.class);
		Criteria criteriaDocStatuses = session.createCriteria(DocStatuses.class);
		Criteria criteriaEmployees = session.createCriteria(Employee.class);
		DocTypesFull = FXCollections.observableList(criteriaDocTypes.list());
		DocStatusesFull = FXCollections.observableList(criteriaDocStatuses.list());
		employeeFull = FXCollections.observableList(criteriaEmployees.list());
		for (int i = 0; i < DocTypesFull.size(); i++) {
			DocTypesFull.set(i, ((DocTypes) criteriaDocTypes.list().get(i)).getDocTypeName());
		}
		for (int i = 0; i < DocStatusesFull.size(); i++) {
			DocStatusesFull.set(i, ((DocStatuses) criteriaDocStatuses.list().get(i)).getDocStatusName());
		}
		for (int i = 0; i < employeeFull.size(); i++) {
			employeeFull.set(i, ((Employee) criteriaEmployees.list().get(i)).toString());
		}
		DocTypesAll = FXCollections.observableList(criteriaDocTypes.list());
		DocStatusesAll = FXCollections.observableList(criteriaDocStatuses.list());
		employeeAll = FXCollections.observableList(criteriaEmployees.list());
		session.close();
	}

	public void stageBuilder(String fxmlResource, int width, int height, String title, int minWidth, int minHeight, int x, int y) {
		try {
			Stage stage = new Stage();
			Parent panel;
			panel = FXMLLoader.load(getClass().getResource(fxmlResource));
			Scene scene = new Scene(panel, width, height);
			stage.setTitle(title);
			stage.setMinWidth(minWidth);
			stage.setMinHeight(minHeight);
			stage.setScene(scene);
			stage.setX(x);
			stage.setY(y);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Session sessionExtracting() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		return session;
	}

	public static Session sessionExtractingforDeleting() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		return session;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	public void close(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

	public void showInfo(String info) {
		InfoWindowController infoWindowController = new InfoWindowController();
		String fxmlResource = "../views/InfoWindow.fxml";
		FXMLLoader fxmlLoader = new FXMLLoader();
		Parent panel = null;
		Stage stage = new Stage();
		try {
			fxmlLoader.setLocation(getClass().getResource(fxmlResource));
			panel = fxmlLoader.load();
			infoWindowController = fxmlLoader.getController();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		infoWindowController.getInfoText().setText(info);
		infoWindowController.getOkButton().setOnMouseClicked(new EventHandler<Event>() {

			@Override
			public void handle(Event event) {
				stage.hide();
			}
		});
		Scene scene = new Scene(panel);
		stage.setScene(scene);
		stage.initModality(Modality.APPLICATION_MODAL);
		stage.show();
	}

	public static ObservableList<String> getEmployeeFull() {
		return employeeFull;
	}

	public static ObservableList<Employee> getEmployeeAll() {
		return employeeAll;
	}

	public static void setEmployeeFull(ObservableList<String> employeeFull) {
		WindowController.employeeFull = employeeFull;
	}

	public static void setEmployeeAll(ObservableList<Employee> employeeAll) {
		WindowController.employeeAll = employeeAll;
	}

	public static ObservableList<String> getDocTypesFull() {
		return DocTypesFull;
	}

	public static ObservableList<DocTypes> getDocTypesAll() {
		return DocTypesAll;
	}

	public static ObservableList<String> getDocStatusesFull() {
		return DocStatusesFull;
	}

	public static ObservableList<DocStatuses> getDocStatusesAll() {
		return DocStatusesAll;
	}

	public static void setDocTypesFull(ObservableList<String> docTypesFull) {
		DocTypesFull = docTypesFull;
	}

	public static void setDocTypesAll(ObservableList<DocTypes> docTypesAll) {
		DocTypesAll = docTypesAll;
	}

	public static void setDocStatusesFull(ObservableList<String> docStatusesFull) {
		DocStatusesFull = docStatusesFull;
	}

	public static void setDocStatusesAll(ObservableList<DocStatuses> docStatusesAll) {
		DocStatusesAll = docStatusesAll;
	}
}
