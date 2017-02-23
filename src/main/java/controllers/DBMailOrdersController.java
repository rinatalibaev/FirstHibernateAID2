package controllers;

import java.io.IOException;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.Event;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.stage.Modality;
import javafx.stage.Stage;
import models.Documents;
import models.MailOrder;

public class DBMailOrdersController extends DatabaseViewingWindowController {

	private String fxmlResource = "../views/DBMailOrdersWindow.fxml";

	private String title = "АС административного сопровождения - База данных - Заказы курьера";

	private String delete_hql_query = "DELETE FROM MailOrder WHERE id = :id";

	@FXML
	TableView<MailOrder> DBMailOrderTable;

	@FXML
	TableColumn<MailOrder, String> DBMailOrdTableId;

	@FXML
	TableColumn<MailOrder, String> DBMailOrdTableColumn1;

	@FXML
	TableColumn<MailOrder, String> DBMailOrdTableColumn2;

	@FXML
	TableColumn<MailOrder, String> DBMailOrdTableColumn3;

	@FXML
	TableColumn<MailOrder, String> DBMailOrdTableColumn4;

	@FXML
	TableColumn<MailOrder, String> DBMailOrdTableColumn5;

	@FXML
	TableColumn<MailOrder, String> DBMailOrdTableColumn6;

	@FXML
	public void initialize() {
		DBMailOrdTableId.setCellValueFactory(new PropertyValueFactory<MailOrder, String>("id"));
		DBMailOrdTableColumn1.setCellValueFactory(new PropertyValueFactory<MailOrder, String>("mailOrdStatus"));
		DBMailOrdTableColumn2.setCellValueFactory(new PropertyValueFactory<MailOrder, String>("mailOrdSenderNo"));
		DBMailOrdTableColumn3.setCellValueFactory(new PropertyValueFactory<MailOrder, String>("mailOrdReceiverNo"));
		DBMailOrdTableColumn4.setCellValueFactory(new PropertyValueFactory<MailOrder, String>("mailOrdSentDate"));
		DBMailOrdTableColumn5.setCellValueFactory(new PropertyValueFactory<MailOrder, String>("mailOrdCreateDate"));
		DBMailOrdTableColumn6.setCellValueFactory(new PropertyValueFactory<MailOrder, String>("mailOrdToSendDate"));
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		ObservableList<MailOrder> mailorders = null;
		try {
			Criteria criteria = session.createCriteria(MailOrder.class);
			mailorders = FXCollections.observableList(criteria.list());
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		DBMailOrderTable.setItems(mailorders);
		DBMailOrderTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
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
	public void addingEntry() {
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			Stage stage = new Stage();
			Scene scene = null;
			DBMailOrderEditingController dbMailOrdersEditing;
			Parent fxmlEdit;
			fxmlLoader.setLocation(getClass().getResource("../views/DBMailOrderEditing.fxml"));
			fxmlEdit = fxmlLoader.load();
			dbMailOrdersEditing = fxmlLoader.getController();
			dbMailOrdersEditing.parentController = this;
			dbMailOrdersEditing.sentDateDatePicker.setVisible(false);
			dbMailOrdersEditing.createDateDatePicker.setVisible(false);
			dbMailOrdersEditing.mailOrderInsertDateLabel.setVisible(false);
			dbMailOrdersEditing.mailOrderSentDateLabel.setVisible(false);
			dbMailOrdersEditing.okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					dbMailOrdersEditing.add(event);
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

	@Override
	public void editingEntry(Event event) {
		System.out.println("in DBMailOrderWindowController.editingEntry()");
		MailOrder mailOrder = DBMailOrderTable.getSelectionModel().getSelectedItem();
		DBMailOrderEditingController dbMailOrderEditingController;
		Session session = sessionExtracting();
		session.beginTransaction();
		// Criteria criteriaEmployee = session.createCriteria(Employee.class);
		// List<Employee> listEmployee = criteriaEmployee.list();
		Criteria criteriaDocuments = session.createCriteria(Documents.class);
		List<Documents> listDocuments = criteriaDocuments.list();
		Parent panel;
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../views/DBMailOrderEditing.fxml"));
		System.out.println("after fxml-loading");
		try {
			Stage stage = new Stage();
			panel = fxmlLoader.load();
			dbMailOrderEditingController = fxmlLoader.getController();
			dbMailOrderEditingController.senderComboBox.setValue(mailOrder.getMailOrdSenderNo().toString());
			dbMailOrderEditingController.receiverComboBox.setValue(mailOrder.getMailOrdReceiverNo().toString());
			dbMailOrderEditingController.endReceiverComboBox.setValue(mailOrder.getMailOrdEndReceiverNo().toString());
			dbMailOrderEditingController.toSendDateDatePicker.setValue(mailOrder.getMailOrdToSendDate());
			ObservableList<Documents> documentList = FXCollections.observableArrayList();
			String docNumber = "";
			if (mailOrder.getMailOrdDocuments() != null) {
				for (int i = 0; i < mailOrder.getMailOrdDocuments().length(); i++) {
					if (!mailOrder.getMailOrdDocuments().substring(i, i + 1).equals(",")) {
						System.out.println("in if");
						docNumber = docNumber + mailOrder.getMailOrdDocuments().substring(i, i + 1);
						if (i == mailOrder.getMailOrdDocuments().length() - 1) {
							for (Documents document : listDocuments) {
								if (document.getId() == Integer.parseInt(docNumber)) {
									documentList.add(document);
								}
							}
							docNumber = "";
						}
					} else {
						System.out.println("in else");
						for (Documents document : listDocuments) {
							if (document.getId() == Integer.parseInt(docNumber)) {
								documentList.add(document);
							}
						}
						docNumber = "";
					}
				}
			}
			dbMailOrderEditingController.DBMailOrderDocumentTable.setItems(documentList);
			dbMailOrderEditingController.DBMailOrderDocumentTable.refresh();
			dbMailOrderEditingController.DBMailOrderDocumentTable.setOnMouseClicked(new EventHandler<MouseEvent>() {

				@Override
				public void handle(MouseEvent event) {
					if (event.getButton().equals(MouseButton.PRIMARY)) {
						if (event.getClickCount() == 2) {
							DBDocumentWindowController dbDocumentWindowController = new DBDocumentWindowController();
							dbDocumentWindowController.editingEntry(dbMailOrderEditingController.DBMailOrderDocumentTable.getSelectionModel().getSelectedItem());
						}
					}
				}

			});
			Scene scene = new Scene(panel);
			stage.setScene(scene);
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primarystage) {
		stageBuilder("../views/DBMailOrdersWindow.fxml", 1100, 285, "АС административного сопровождения - База данных - Заказы курьера", 1100, 285, 125, 200);
	}

	public void DBMailOrderDeleting() {
		MailOrder selectedDocument = (MailOrder) DBMailOrderTable.getSelectionModel().getSelectedItem();
		selectedDocument.delete(delete_hql_query);
		initialize();
		DBMailOrderTable.refresh();
	}
}
