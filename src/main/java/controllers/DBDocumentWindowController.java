package controllers;

import java.io.IOException;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import controllers.interfaces.WindowController;
import javafx.application.Application;
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
import models.Documents;

public class DBDocumentWindowController extends Application implements WindowController {

	@FXML
	TableView<Documents> DBDocumentTable;
	@FXML
	TableColumn<Documents, String> DBDocumentTableId;
	@FXML
	TableColumn<Documents, String> DBDocumentTableColumn1;
	@FXML
	TableColumn<Documents, String> DBDocumentTableColumn2;
	@FXML
	TableColumn<Documents, String> DBDocumentTableColumn3;
	@FXML
	TableColumn<Documents, String> DBDocumentTableColumn4;
	@FXML
	TableColumn<Documents, String> DBDocumentTableColumn5;
	@FXML
	TableColumn<Documents, String> DBDocumentTableColumn6;

	@FXML
	public void initialize() {
		DBDocumentTableId.setCellValueFactory(new PropertyValueFactory<Documents, String>("id"));
		DBDocumentTableColumn1.setCellValueFactory(new PropertyValueFactory<Documents, String>("docType"));
		DBDocumentTableColumn2.setCellValueFactory(new PropertyValueFactory<Documents, String>("docDate"));
		DBDocumentTableColumn3.setCellValueFactory(new PropertyValueFactory<Documents, String>("docInsertedDate"));
		DBDocumentTableColumn4.setCellValueFactory(new PropertyValueFactory<Documents, String>("docName"));
		DBDocumentTableColumn5.setCellValueFactory(new PropertyValueFactory<Documents, String>("docStatus"));
		DBDocumentTableColumn6.setCellValueFactory(new PropertyValueFactory<Documents, String>("docInsertedEmployee"));
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ObservableList<Documents> documents = null;
		try {
			Criteria criteria = session.createCriteria(Documents.class);
			documents = FXCollections.observableList(criteria.list());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		DBDocumentTable.setItems(documents);
		DBDocumentTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

			@Override
			public void handle(MouseEvent mouseEvent) {
				if (mouseEvent.getButton().equals(MouseButton.PRIMARY)) {
					if (mouseEvent.getClickCount() == 2) {
						DBDocumentEditing(mouseEvent);
					}
				}
			}
		});
	}

	@Override
	public void add(ActionEvent actionevent) {
	}

	@Override
	public void edit() {
	}

	@Override
	public void delete() {
	}

	@Override
	public void start(Stage primarystage) {
		stageBuilder("../views/DBDocumentWindow.fxml", 1100, 285, "АС административного сопровождения - База данных - Документы", 1100, 285, 125, 200);
	}

	@FXML
	public void DBDocumentAdding() {

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Stage stage = new Stage();
			Scene scene = null;
			DBDocumentEditingController dbDocumentEditingController;
			Parent fxmlEdit;
			fxmlLoader.setLocation(getClass().getResource("../views/DBDocumentEditing.fxml"));
			fxmlEdit = fxmlLoader.load();
			dbDocumentEditingController = fxmlLoader.getController();
			dbDocumentEditingController.parentController = this;
			dbDocumentEditingController.okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					dbDocumentEditingController.addDocument(event);
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
	public void DBDocumentEditing(Event event) {
		System.out.println("First line in DBDocumentEditing()");
		Documents selectedDocument = (Documents) DBDocumentTable.getSelectionModel().getSelectedItem();
		Window parentWindow = ((Node) event.getSource()).getScene().getWindow();
		System.out.println("Before try in DBDocumentEditing()");

		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Stage stage = new Stage();
			Scene scene = null;
			DBDocumentEditingController dbDocumentEditingController;
			Parent fxmlEdit;
			fxmlLoader.setLocation(getClass().getResource("../views/DBDocumentEditing.fxml"));
			fxmlEdit = fxmlLoader.load();
			dbDocumentEditingController = fxmlLoader.getController();
			dbDocumentEditingController.parentController = this;
			scene = new Scene(fxmlEdit);
			System.out.println("Before setDocument()");
			dbDocumentEditingController.setDocument(selectedDocument);
			stage.setScene(scene);
			stage.initModality(Modality.APPLICATION_MODAL);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void DBDocumentDeleting() {
		Documents selectedDocument = (Documents) DBDocumentTable.getSelectionModel().getSelectedItem();
		DBDocumentEditingController.deleteDocument(selectedDocument);
		initialize();
		DBDocumentTable.refresh();
	}

	public void closeDBEmployeeWindow(ActionEvent actionEvent) {
		((Node) actionEvent.getSource()).getScene().getWindow().hide();
	}
}
