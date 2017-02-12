package controllers;

import javax.persistence.TransactionRequiredException;

import org.hibernate.Query;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import controllers.interfaces.WindowController;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Documents;

public class DBDocumentEditingController extends Application implements WindowController {

	Documents document;
	Stage stage;
	DBDocumentWindowController parentController = null;

	@FXML
	TextField docNoTextField;
	@FXML
	TextField docTypeTextField;
	@FXML
	TextField docDateTextField;
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

	@Override
	public void add(ActionEvent actionevent) {

	}

	@Override
	public void edit() {

	}

	@Override
	public void delete() {
		// TODO Auto-generated method stub

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		// TODO Auto-generated method stub
	}

	public void addDocument(MouseEvent mouseEvent) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			String sql = "INSERT INTO documents (DocType, DocDate, DocInsertedDate, DocName, DocStatus, DocInsertedEmployee) VALUES (?,?,?,?,?,?)";
			SQLQuery query = session.createSQLQuery(sql);
			query.setParameter(0, docTypeTextField.getText());
			query.setParameter(1, docDateTextField.getText());
			query.setParameter(2, docInsertedDateTextField.getText());
			query.setParameter(3, docNameTextField.getText());
			query.setParameter(4, docStatusTextField.getText());
			query.setParameter(5, docInsertedEmployeeTextField.getText());
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

	public void updateDocument() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			String hql = "UPDATE Documents set DocType = :DocType, DocType = :DocType, DocInsertedDate = :DocInsertedDate, DocName = :DocName, DocStatus = :DocStatus, DocInsertedEmployee = :DocInsertedEmployee WHERE id = :document_id";
			Query query = session.createQuery(hql);
			query.setParameter("DocType", docTypeTextField.getText());
			query.setParameter("DocDate", docDateTextField.getText());
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
		docTypeTextField.setText(selectedDocument.getDocType());
		docDateTextField.setText(selectedDocument.getDocDate());
		docInsertedDateTextField.setText(selectedDocument.getDocInsertedDate());
		docNameTextField.setText(selectedDocument.getDocName());
		docStatusTextField.setText(selectedDocument.getDocStatus());
		docInsertedEmployeeTextField.setText(selectedDocument.getDocInsertedEmployee());
	}

	@FXML
	public void close(ActionEvent event) {
	}

	public static void deleteDocument(Documents document) {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();

		try {
			session.beginTransaction();
			String hql = "DELETE FROM Documents WHERE id = :document_id";
			Query query = session.createQuery(hql);
			query.setParameter("document_id", document.getId());
			query.executeUpdate();
			session.getTransaction().commit();
		} catch (TransactionRequiredException tre) {
			tre.printStackTrace();
		} finally {
			// session.close();
			// sessionFactory.close();
		}
	}

	public void closeDBEmployeeWindow(ActionEvent actionEvent) {
		((Node) actionEvent.getSource()).getScene().getWindow().hide();
	}

}
