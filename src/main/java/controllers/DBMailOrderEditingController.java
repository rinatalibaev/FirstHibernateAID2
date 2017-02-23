package controllers;

import java.io.IOException;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.Criteria;
import org.hibernate.HibernateException;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import models.Documents;
import models.Employee;

@Entity
@Table
public class DBMailOrderEditingController extends DatabaseEditingWindowController {

	DBMailOrdersController parentController = null;

	@FXML
	ComboBox<String> senderComboBox;
	@FXML
	ComboBox<String> receiverComboBox;
	@FXML
	ComboBox<String> endReceiverComboBox;
	@FXML
	DatePicker sentDateDatePicker;
	@FXML
	TableView<Documents> DBMailOrderDocumentTable;
	@FXML
	TableColumn<Documents, String> id;
	@FXML
	TableColumn<Documents, String> docType;
	@FXML
	TableColumn<Documents, String> docName;
	@FXML
	TableColumn<Documents, String> docStatus;
	@FXML
	TableColumn<Documents, String> docDate;
	@FXML
	TableColumn<Documents, String> docInsertedDate;
	@FXML
	TableColumn<Documents, String> docInsertedEmployee;
	@FXML
	DatePicker createDateDatePicker;
	@FXML
	DatePicker toSendDateDatePicker;
	@FXML
	Button docDeleteButton;
	@FXML
	Button escButton;
	@FXML
	Button okButton;
	@FXML
	Button docChooseButton;
	@FXML
	Label mailOrderInsertDateLabel;
	@FXML
	Label mailOrderSentDateLabel;

	ObservableList<String> employeeFull = null;
	ObservableList<Employee> employeeAll = null;
	ObservableList<Documents> selectedDocuments = null;

	@FXML
	public void initialize() {
		try {
			id.setCellValueFactory(new PropertyValueFactory<Documents, String>("id"));
			docType.setCellValueFactory(new PropertyValueFactory<Documents, String>("docTypeName"));
			docName.setCellValueFactory(new PropertyValueFactory<Documents, String>("docName"));
			docStatus.setCellValueFactory(new PropertyValueFactory<Documents, String>("docStatusName"));
			docDate.setCellValueFactory(new PropertyValueFactory<Documents, String>("docDate"));
			docInsertedDate.setCellValueFactory(new PropertyValueFactory<Documents, String>("docInsertedDate"));
			docInsertedEmployee.setCellValueFactory(new PropertyValueFactory<Documents, String>("docInsertedEmployeeSurname"));
			Session session = sessionExtracting();
			Criteria criteriaEmployees = session.createCriteria(Employee.class);
			employeeFull = FXCollections.observableList(criteriaEmployees.list());
			for (int i = 0; i < employeeFull.size(); i++) {
				employeeFull.set(i, ((Employee) criteriaEmployees.list().get(i)).toString());
			}
			employeeAll = FXCollections.observableList(criteriaEmployees.list());
			senderComboBox.setItems(employeeFull);
			receiverComboBox.setItems(employeeFull);
			endReceiverComboBox.setItems(employeeFull);

		} catch (HibernateException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@FXML
	public void chooseDocument(ActionEvent event) {
		DBDocumentWindowController dbDocumentWindowController;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Stage stage = new Stage();
			fxmlLoader.setLocation((getClass().getResource("../views/DBDocumentWindow.fxml")));
			Parent panel = fxmlLoader.load();
			dbDocumentWindowController = fxmlLoader.getController();
			dbDocumentWindowController.DBDocumentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			dbDocumentWindowController.okButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {

					System.out.println("in handle");
					if (dbDocumentWindowController.DBDocumentTable.getSelectionModel().getSelectedItems() != null) {
						if (selectedDocuments == null) {
							selectedDocuments = dbDocumentWindowController.DBDocumentTable.getSelectionModel().getSelectedItems();
						} else {
							selectedDocuments = FXCollections.concat(selectedDocuments, dbDocumentWindowController.DBDocumentTable.getSelectionModel().getSelectedItems());
						}
					}
					DBMailOrderDocumentTable.setItems(selectedDocuments);
					DBMailOrderDocumentTable.refresh();
					close(event);
				}
			});
			Scene scene = new Scene(panel, 1100, 285);
			stage.setTitle("Выбор документа для отправки");
			stage.setMinWidth(1100);
			stage.setMinHeight(285);
			stage.setScene(scene);
			stage.setX(125);
			stage.setY(200);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void update(MouseEvent event) {
	}

	@SuppressWarnings("deprecation")
	@Override
	public void add(Event event) {
		String documents = "";
		for (Documents document : DBMailOrderDocumentTable.getItems()) {
			System.out.println(document.toString());
			if (documents.equals("")) {
				documents = String.valueOf((document.getId()));
			} else {
				documents = documents + "," + document.getId();
			}
		}

		Session session = sessionExtracting();
		session.beginTransaction();
		SQLQuery sqlQuery = session.createSQLQuery("INSERT into mailorder (mailOrdSenderNo, mailOrdStatus, mailOrdReceiverNo, mailOrdEndReceiverNo, mailOrdCreateDate, mailOrdToSendDate, mailOrdSentDate, mailOrdDocuments) VALUES (?,?,?,?,?,?,?,?)");
		int senderNo = 0;
		int receiverNo = 0;
		int endReceiverNo = 0;
		for (int i = 0; i < employeeFull.size(); i++) {
			if ((employeeFull.get(i)).toString().equals(senderComboBox.getValue())) {
				senderNo = employeeAll.get(i).getId();
				break;
			}
		}
		for (int i = 0; i < employeeFull.size(); i++) {
			if ((employeeFull.get(i)).toString().equals(receiverComboBox.getValue())) {
				receiverNo = employeeAll.get(i).getId();
				break;
			}
		}
		for (int i = 0; i < employeeFull.size(); i++) {
			if ((employeeFull.get(i)).toString().equals(endReceiverComboBox.getValue())) {
				endReceiverNo = employeeAll.get(i).getId();
				break;
			}
		}

		sqlQuery.setParameter(0, senderNo);
		sqlQuery.setParameter(1, 1);
		sqlQuery.setParameter(2, receiverNo);
		sqlQuery.setParameter(3, endReceiverNo);
		sqlQuery.setParameter(4, createDateDatePicker.getValue());
		sqlQuery.setParameter(5, toSendDateDatePicker.getValue());
		sqlQuery.setParameter(6, sentDateDatePicker.getValue());
		sqlQuery.setParameter(7, documents);
		sqlQuery.executeUpdate();
		session.getTransaction().commit();
		parentController.initialize();
		parentController.DBMailOrderTable.refresh();
		okButton.getScene().getWindow().hide();
	}

	@Override
	public void update() {
		// TODO Auto-generated method stub

	}

	public TableView<Documents> getDBMailOrderDocumentTable() {
		return DBMailOrderDocumentTable;
	}

	public void setDBMailOrderDocumentTable(TableView<Documents> dBMailOrderDocumentTable) {
		DBMailOrderDocumentTable = dBMailOrderDocumentTable;
	}

}
