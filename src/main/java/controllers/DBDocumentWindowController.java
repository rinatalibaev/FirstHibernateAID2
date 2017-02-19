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
import models.DocTypes;
import models.Documents;
import networking.Server;

public class DBDocumentWindowController extends DatabaseViewingWindowController {

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

	private String delete_hql_query = "DELETE FROM Documents WHERE id = :id";

	@FXML
	public void initialize() {
		DBDocumentTableId.setCellValueFactory(new PropertyValueFactory<Documents, String>("id"));
		DBDocumentTableColumn1.setCellValueFactory(new PropertyValueFactory<Documents, String>("docTypeName"));
		DBDocumentTableColumn2.setCellValueFactory(new PropertyValueFactory<Documents, String>("docName"));
		DBDocumentTableColumn3.setCellValueFactory(new PropertyValueFactory<Documents, String>("docStatusName"));
		DBDocumentTableColumn4.setCellValueFactory(new PropertyValueFactory<Documents, String>("docDate"));
		DBDocumentTableColumn5.setCellValueFactory(new PropertyValueFactory<Documents, String>("docInsertedDate"));
		DBDocumentTableColumn6.setCellValueFactory(new PropertyValueFactory<Documents, String>("docInsertedEmployeeSurname"));
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ObservableList<Documents> documents = null;
		ObservableList<DocTypes> docTypes = null;
		try {
			Criteria criteriaDocuments = session.createCriteria(Documents.class);
			documents = FXCollections.observableList(criteriaDocuments.list());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		DBDocumentTable.setItems(documents);

		DBDocumentTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
		stageBuilder("../views/DBDocumentWindow.fxml", 1100, 285, "АС административного сопровождения - База данных - Документы", 1100, 285, 125, 200);
	}

	@FXML
	public void addingEntry() {

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
			dbDocumentEditingController.docInsertedDateTextField.setVisible(false);
			dbDocumentEditingController.docInsertDateLabel.setVisible(false);
			dbDocumentEditingController.okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					dbDocumentEditingController.add(event);
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
	public void editingEntry(Event event) {
		System.out.println("First line in DBDocumentEditing()");
		Documents selectedDocument = (Documents) DBDocumentTable.getSelectionModel().getSelectedItem();
		// Window parentWindow = ((Node)
		// event.getSource()).getScene().getWindow();
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
			dbDocumentEditingController.docInsertedDateTextField.setDisable(true);
			dbDocumentEditingController.docInsertedEmployeeComboBox.setDisable(true);
			dbDocumentEditingController.docTypeComboBox.setDisable(true);
			dbDocumentEditingController.docNameTextField.setDisable(true);
			dbDocumentEditingController.docDateDatePicker.setDisable(true);
			dbDocumentEditingController.choosedDocumentTextField.setText(selectedDocument.getDocServerPath());
			dbDocumentEditingController.choosedDocumentTextField.setDisable(true);
			dbDocumentEditingController.docChooseButton.setText("Открыть");
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
		selectedDocument.delete(delete_hql_query);
		initialize();
		DBDocumentTable.refresh();
		Server server = new Server();
		server.setFileToDelete(selectedDocument.getDocServerPath());
		server.setServerMethod("deleteFile");
		Thread thread = new Thread(server);
		thread.start();
	}

	public void closeDBEmployeeWindow(ActionEvent actionEvent) {
		((Node) actionEvent.getSource()).getScene().getWindow().hide();
	}
}
