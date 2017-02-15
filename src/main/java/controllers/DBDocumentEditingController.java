package controllers;

import java.io.File;
import java.io.IOException;
import java.time.LocalDateTime;

import javax.persistence.TransactionRequiredException;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.Query;
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
import javafx.scene.control.TextField;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import javafx.stage.Window;
import models.DocTypes;
import models.Documents;
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
	DatePicker docDateDatePicker;
	@FXML
	TextField docInsertedDateTextField;
	@FXML
	TextField docNameTextField;
	@FXML
	TextField docStatusTextField;
	@FXML
	TextField docInsertedEmployeeTextField;
	@FXML
	TextField z1TextField;
	@FXML
	TextField z2TextField;
	@FXML
	TextField z3TextField;
	@FXML
	TextField z4TextField;
	@FXML
	TextField z5TextField;
	@FXML
	TextField z6TextField;

	@FXML
	Button okButton;

	ObservableList<String> DocTypesFull = null;
	ObservableList<DocTypes> DocTypesAll = null;
	ObservableList<String> DocTypes = null;
	@FXML
	TextField choosedDocumentTextField;

	File file = null;

	@FXML
	public void initialize() {
		try {
			Session session = sessionExtracting();
			Criteria criteria = session.createCriteria(DocTypes.class);
			System.out.println(criteria.list().size());
			DocTypesFull = FXCollections.observableList(criteria.list());
			for (int i = 0; i < DocTypesFull.size(); i++) {
				DocTypesFull.set(i, ((DocTypes) criteria.list().get(i)).getDocTypeName());
				System.out.println(DocTypesFull.get(i));
			}
			DocTypes = DocTypesFull;
			DocTypesAll = FXCollections.observableList(criteria.list());
			docTypeComboBox.setItems(DocTypes);
		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	public void add(Event mouseEvent) {
		Session session = sessionExtracting();

		try {
			session.beginTransaction();
			String sql = "INSERT INTO documents (DocType, DocDate, DocInsertedDate, DocName, DocStatus, DocInsertedEmployee) VALUES (?,?,?,?,?,?)";
			SQLQuery query = session.createSQLQuery(sql);
			int id = 777;
			for (int i = 0; i < DocTypesAll.size(); i++) {
				if (((DocTypes) DocTypesAll.get(i)).getDocTypeName() == docTypeComboBox.getValue()) {
					id = i;
					break;
				}
			}
			query.setParameter(0, id);
			query.setParameter(1, docDateDatePicker.getValue());
			query.setParameter(2, LocalDateTime.now());
			query.setParameter(3, docNameTextField.getText());
			query.setParameter(4, docStatusTextField.getText());
			query.setParameter(5, docInsertedEmployeeTextField.getText());
			query.executeUpdate();
			session.getTransaction().commit();
			Server server = new Server();
			server.setFILE_NAME(file.getName());
			Thread serverThread = new Thread(server);
			serverThread.start();
			Client client = new Client();
			try {
				client.sendFile(file);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			serverThread.stop();
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

	public void update() {
		Session session = sessionExtracting();

		try {
			session.beginTransaction();
			String hql = "UPDATE Documents set DocType = :DocType, DocDate = :DocDate, DocInsertedDate = :DocInsertedDate, DocName = :DocName, DocStatus = :DocStatus, DocInsertedEmployee = :DocInsertedEmployee WHERE id = :document_id";
			Query query = session.createQuery(hql);
			query.setParameter("DocType", docTypeComboBox.getValue());
			query.setParameter("DocDate", docDateDatePicker.getPromptText());
			query.setParameter("DocInsertedDate", docInsertedDateTextField.getText());
			query.setParameter("DocName", docNameTextField.getText());
			query.setParameter("DocStatus", docStatusTextField.getText());
			query.setParameter("DocInsertedEmployee", docInsertedEmployeeTextField.getText());
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
		docTypeComboBox.setValue(selectedDocument.getDocType());
		docDateDatePicker.setPromptText(selectedDocument.getDocDate());
		docInsertedDateTextField.setText(selectedDocument.getDocInsertedDate());
		docNameTextField.setText(selectedDocument.getDocName());
		docStatusTextField.setText(selectedDocument.getDocStatus());
		docInsertedEmployeeTextField.setText(selectedDocument.getDocInsertedEmployee());
	}

	@FXML
	public void close(ActionEvent event) {
	}

	public void closeDBEmployeeWindow(ActionEvent actionEvent) {
		((Node) actionEvent.getSource()).getScene().getWindow().hide();
	}

	@FXML
	public void choosedDocument(ActionEvent event) {
		FileChooser fileChooser = new FileChooser();
		fileChooser.setTitle("Выбор документа для добавления");
		Window window = ((Node) event.getSource()).getScene().getWindow();
		file = fileChooser.showOpenDialog(window);
		System.out.println(file.length());
		choosedDocumentTextField.setText(file.getAbsolutePath());
	}
}
