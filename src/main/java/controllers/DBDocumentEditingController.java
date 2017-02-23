package controllers;

import java.awt.Desktop;
import java.io.File;
import java.io.IOException;
import java.net.Socket;
import java.time.LocalDateTime;

import javax.persistence.TransactionRequiredException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.DocStatuses;
import models.DocTypes;
import models.Documents;
import models.Employee;
import networking.Client;
import networking.Server;

public class DBDocumentEditingController extends DatabaseEditingWindowController {

	Documents document;
	Stage stage;
	DBDocumentWindowController parentController = null;

	@FXML
	TextField docNoTextField;
	@FXML
	ComboBox<String> docTypeComboBox = new ComboBox<String>();
	@FXML
	ComboBox<String> docStatusComboBox = new ComboBox<String>();
	@FXML
	ComboBox<String> docInsertedEmployeeComboBox = new ComboBox<String>();
	@FXML
	DatePicker docDateDatePicker;
	@FXML
	TextField docInsertedDateTextField;
	@FXML
	Label docInsertDateLabel;
	@FXML
	TextField docNameTextField;
	@FXML
	TextField docInsertedEmployeeTextField;
	@FXML
	TextField choosedDocumentTextField;
	@FXML
	Button okButton;
	@FXML
	Button docChooseButton;
	File file = null;

	ObservableList<String> DocTypesFull = null;
	ObservableList<DocTypes> DocTypesAll = null;
	ObservableList<String> DocStatusesFull = null;
	ObservableList<DocStatuses> DocStatusesAll = null;
	ObservableList<String> DocInsertedEmployeeFull = null;
	ObservableList<Employee> DocInsertedEmployeeAll = null;

	@FXML
	public void initialize() {
		try {
			Session session = sessionExtracting();
			Criteria criteriaDocTypes = session.createCriteria(DocTypes.class);
			Criteria criteriaDocStatuses = session.createCriteria(DocStatuses.class);
			Criteria criteriaInsertedEmployee = session.createCriteria(Employee.class);
			DocTypesFull = FXCollections.observableList(criteriaDocTypes.list());
			DocStatusesFull = FXCollections.observableList(criteriaDocStatuses.list());
			DocInsertedEmployeeFull = FXCollections.observableList(criteriaInsertedEmployee.list());
			for (int i = 0; i < DocTypesFull.size(); i++) {
				DocTypesFull.set(i, ((DocTypes) criteriaDocTypes.list().get(i)).getDocTypeName());
			}
			for (int i = 0; i < DocStatusesFull.size(); i++) {
				DocStatusesFull.set(i, ((DocStatuses) criteriaDocStatuses.list().get(i)).getDocStatusName());
			}
			for (int i = 0; i < DocInsertedEmployeeFull.size(); i++) {
				DocInsertedEmployeeFull.set(i, ((Employee) criteriaInsertedEmployee.list().get(i)).toString());
			}
			DocTypesAll = FXCollections.observableList(criteriaDocTypes.list());
			DocStatusesAll = FXCollections.observableList(criteriaDocStatuses.list());
			DocInsertedEmployeeAll = FXCollections.observableList(criteriaInsertedEmployee.list());
			docTypeComboBox.setItems(DocTypesFull);
			docStatusComboBox.setItems(DocStatusesFull);
			docInsertedEmployeeComboBox.setItems(DocInsertedEmployeeFull);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	@SuppressWarnings("deprecation")
	public void add(Event mouseEvent) {
		Session session = sessionExtracting();
		String docServerPath = null;
		try {
			session.beginTransaction();
			String sql = "INSERT INTO documents (DocType, DocDate, DocInsertedDate, DocName, docServerPath, DocStatusName, DocInsertedEmployee) VALUES (?,?,?,?,?,?,?)";
			SQLQuery query = session.createSQLQuery(sql);
			int docType = 777;
			for (int i = 0; i < DocTypesAll.size(); i++) {
				if (((DocTypes) DocTypesAll.get(i)).getDocTypeName() == docTypeComboBox.getValue()) {
					docType = DocTypesAll.get(i).getId();
					break;
				}
			}
			int docStatus = 777;
			for (int i = 0; i < DocStatusesAll.size(); i++) {
				if (((DocStatuses) DocStatusesAll.get(i)).getDocStatusName() == docStatusComboBox.getValue()) {
					docStatus = DocStatusesAll.get(i).getId();
					break;
				}
			}
			int docInsertedEmployee = 777;
			for (int i = 0; i < DocInsertedEmployeeAll.size(); i++) {
				System.out.println(((Employee) DocInsertedEmployeeAll.get(i)).toString());
				System.out.println(docInsertedEmployeeComboBox.getValue());
				if (((Employee) DocInsertedEmployeeAll.get(i)).toString().equals(docInsertedEmployeeComboBox.getValue())) {
					docInsertedEmployee = DocInsertedEmployeeAll.get(i).getId();
					break;
				}
			}

			Server server = new Server();
			server.setFILE_NAME(file.getName());
			server.setServerMethod("receive");
			Thread serverThread = new Thread(server);
			serverThread.start();
			Client client = new Client();
			try {
				client.sendFile(file);
				docServerPath = server.getFILE_TO_RECEIVED();
				file = null;
				client = null;
				server = null;
			} catch (IOException e) {
				e.printStackTrace();
			}

			query.setParameter(0, docType);
			query.setParameter(1, docDateDatePicker.getValue());
			query.setParameter(2, LocalDateTime.now());
			query.setParameter(3, docNameTextField.getText());
			query.setParameter(4, docServerPath);
			query.setParameter(5, docStatus);
			query.setParameter(6, docInsertedEmployee);
			query.executeUpdate();
			// serverThread.stop();
			session.getTransaction().commit();
			parentController.initialize();
			parentController.DBDocumentTable.refresh();
			okButton.getScene().getWindow().hide();
		} catch (TransactionRequiredException tre) {
			tre.printStackTrace();
		} finally {
			// session.close();
			// sessionFactory.close();
		}
	}

	@SuppressWarnings("deprecation")
	public void update() {
		Session session = sessionExtracting();
		// document.getDocStatus().setDocStatusName(docStatusComboBox.getValue());

		try {
			session.beginTransaction();
			String sql = "UPDATE documents set docStatusName = :DocStatusName WHERE id = :document_id";
			SQLQuery query = session.createSQLQuery(sql);
			int docStatus = 777;
			for (int i = 0; i < DocStatusesAll.size(); i++) {
				if (((DocStatuses) DocStatusesAll.get(i)).getDocStatusName() == docStatusComboBox.getValue()) {
					docStatus = DocStatusesAll.get(i).getId();
					break;
				}
			}
			// query.setParameter("DocType", docTypeComboBox.getValue());
			// query.setParameter("DocDate", docDateDatePicker.getPromptText());
			// query.setParameter("DocInsertedDate",
			// docInsertedDateTextField.getText());
			// query.setParameter("DocName", docNameTextField.getText());
			query.setParameter("DocStatusName", docStatus);
			// query.setParameter("DocInsertedEmployee",
			// docInsertedEmployeeComboBox.getValue());
			query.setParameter("document_id", document.getId());
			query.executeUpdate();
			session.getTransaction().commit();
			parentController.initialize();
			parentController.DBDocumentTable.refresh();
			okButton.getScene().getWindow().hide();
		} catch (TransactionRequiredException tre) {
			tre.printStackTrace();
		} finally {
			// session.close();
			// sessionFactory.close();
		}
	}

	public void setDocument(Documents selectedDocument) {
		System.out.println("In setDocument()");
		document = selectedDocument;
		docTypeComboBox.setValue(selectedDocument.getDocType().getDocTypeName());
		docDateDatePicker.setPromptText(selectedDocument.getDocDate());
		docInsertedDateTextField.setText(selectedDocument.getDocInsertedDate());
		docNameTextField.setText(selectedDocument.getDocName());
		docStatusComboBox.setValue(selectedDocument.getDocStatus().getDocStatusName());
		docInsertedEmployeeComboBox.setValue((selectedDocument.getDocInsertedEmployee().getEmpSurname()));
	}

	@FXML
	public void close(ActionEvent event) {
	}

	public void closeDBEmployeeWindow(ActionEvent actionEvent) {
		((Node) actionEvent.getSource()).getScene().getWindow().hide();
	}

	@FXML
	public void choosedDocument(ActionEvent event) {
		if (docChooseButton.getText().equals("Выбрать")) {
			FileChooser fileChooser = new FileChooser();
			fileChooser.setTitle("Выбор документа для добавления");
			Window window = ((Node) event.getSource()).getScene().getWindow();
			file = fileChooser.showOpenDialog(window);
			if (file != null) {
				System.out.println(file.length());
				choosedDocumentTextField.setText(file.getAbsolutePath());
			}
		}
		if (docChooseButton.getText() == "Открыть") {
			File file = null;
			Desktop desktop = Desktop.getDesktop();
			Server server = new Server();
			Socket sock = null;
			server.setServerMethod("sendFile");
			server.setFileToSend(choosedDocumentTextField.getText());
			Thread serverThread = new Thread(server);
			serverThread.start();
			Client client = new Client();
			try {
				file = new File(client.receiveFile((new File(choosedDocumentTextField.getText()).getName())));
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			try {
				desktop.open(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// try {
			// desktop.open(file);
			// } catch (IOException e) {
			// e.printStackTrace();
			// }

		}
		System.out.println(docChooseButton.getText());

	}
}
