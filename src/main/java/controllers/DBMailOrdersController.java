package controllers;

import java.io.IOException;
import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
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

	private MailOrder mailOrder = null;

	public MailOrder getMailOrder() {
		return mailOrder;
	}

	public void setMailOrder(MailOrder mailOrder) {
		this.mailOrder = mailOrder;
	}

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
			dbMailOrdersEditing.dataVizovaKurieraDatePicker.setVisible(false);
			dbMailOrdersEditing.receivedDateDatePicker.setVisible(false);
			dbMailOrdersEditing.mailOrderInsertDateLabel.setVisible(false);
			dbMailOrdersEditing.mailOrderSentDateLabel.setVisible(false);
			dbMailOrdersEditing.dataVizovaKurieraDateLabel.setVisible(false);
			dbMailOrdersEditing.receivedDateDateLabel.setVisible(false);
			dbMailOrdersEditing.statusChangeButton.setVisible(false);
			dbMailOrdersEditing.okButton.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (dbMailOrdersEditing.toSendDateDatePicker.getValue().isBefore(LocalDate.now().plusDays(1L)) || (dbMailOrdersEditing.toSendDateDatePicker.getValue().getDayOfWeek() == DayOfWeek.SATURDAY)
							|| (dbMailOrdersEditing.toSendDateDatePicker.getValue().getDayOfWeek() == DayOfWeek.SUNDAY)) {
						showInfo("Вы не можете заказать курьера ранее, чем на завтра");
						return;
					}
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
		mailOrder = DBMailOrderTable.getSelectionModel().getSelectedItem();
		DBMailOrderEditingController dbMailOrderEditingController;
		Parent panel;
		FXMLLoader fxmlLoader = new FXMLLoader();
		fxmlLoader.setLocation(getClass().getResource("../views/DBMailOrderEditing.fxml"));
		try {
			panel = fxmlLoader.load();
			dbMailOrderEditingController = fxmlLoader.getController();
			dbMailOrderEditingController.parentController = this;
			Scene scene = new Scene(panel);
			Stage stage = new Stage();
			if (mailOrder.getMailOrdStatus().getId() == 1) {
				setStatusChangeButtonFunctionality("Вызвать курьера", "UPDATE MailOrder SET mailOrdVizovKurieraDate = ?, mailOrdStatus = ?  WHERE id = ?", 2, dbMailOrderEditingController, stage, mailOrder);
			}
			if (mailOrder.getMailOrdStatus().getId() == 2) {
				dbMailOrderEditingController.statusChangeButton.setOnAction(null);
				setStatusChangeButtonFunctionality("Курьер забрал заказ", "UPDATE MailOrder SET mailOrdSentDate = ?, mailOrdStatus = ?  WHERE id = ?", 3, dbMailOrderEditingController, stage, mailOrder);
			}
			if (mailOrder.getMailOrdStatus().getId() == 3) {
				dbMailOrderEditingController.statusChangeButton.setOnAction(null);
				setStatusChangeButtonFunctionality("Заказ выполнен", "UPDATE MailOrder SET mailOrdReceivedDate = ?, mailOrdStatus = ?  WHERE id = ?", 4, dbMailOrderEditingController, stage, mailOrder);
			}
			if (mailOrder.getMailOrdStatus().getId() == 4) {
				dbMailOrderEditingController.statusChangeButton.setOnAction(null);
				setStatusChangeButtonFunctionality("", "", 0, dbMailOrderEditingController, stage, mailOrder);
			}
			dbMailOrderEditingController.senderComboBox.setValue(mailOrder.getMailOrdSenderNo().toString());
			dbMailOrderEditingController.receiverComboBox.setValue(mailOrder.getMailOrdReceiverNo().toString());
			dbMailOrderEditingController.toSendDateDatePicker.setValue(mailOrder.getMailOrdToSendDate());
			dbMailOrderEditingController.createDateDatePicker.setValue(mailOrder.getMailOrdCreateDate().toLocalDateTime().toLocalDate());
			dbMailOrderEditingController.okButton.setDisable(true);
			dbMailOrderEditingController.DBMailOrderDocumentTable.setItems(getDocumentsFromMailOrder(mailOrder));
			dbMailOrderEditingController.DBMailOrderDocumentTable.setOnMouseClicked(new EventHandler<MouseEvent>() {
				@Override
				public void handle(MouseEvent event) {
					if (event.getButton().equals(MouseButton.PRIMARY) && !(dbMailOrderEditingController.DBMailOrderDocumentTable.getSelectionModel().getSelectedItem() == null)) {
						if (event.getClickCount() == 2) {
							DBDocumentWindowController dbDocumentWindowController = new DBDocumentWindowController();
							dbDocumentWindowController.editingEntry(dbMailOrderEditingController.DBMailOrderDocumentTable.getSelectionModel().getSelectedItem());
						}
					}
				}
			});
			stage.setScene(scene);
			System.out.println("Before stage.show() in DBMailOrdersController.editingEntry(). Time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_TIME));
			stage.show();
			System.out.println("After stage.show() in DBMailOrdersController.editingEntry(). Time: " + LocalDateTime.now().format(DateTimeFormatter.ISO_TIME));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	public void start(Stage primarystage) {
		stageBuilder("../views/DBMailOrdersWindow.fxml", 1100, 285, "АС административного сопровождения - База данных - Заказы курьера", 1100, 285, 125, 200);
	}

	public void DBMailOrderDeleting() {
		if (DBMailOrderTable.getSelectionModel().getSelectedItem().getMailOrdStatus().getId() != 1) {
			showInfo("Удаление невозможно, так как курьер заказан или заказ выполнен");
			return;
		}
		MailOrder selectedMailOrder = (MailOrder) DBMailOrderTable.getSelectionModel().getSelectedItem();
		selectedMailOrder.delete(delete_hql_query);
		initialize();
		DBMailOrderTable.refresh();
	}

	public ObservableList<Documents> getDocumentsFromMailOrder(MailOrder mailOrder) {
		Session session = sessionExtracting();
		session.beginTransaction();
		Criteria criteriaDocuments = session.createCriteria(Documents.class);
		List<Documents> listDocuments = criteriaDocuments.list();
		ObservableList<Documents> documentList = FXCollections.observableArrayList();
		String docNumber = "";
		if (mailOrder.getMailOrdDocuments() != null) {
			for (int i = 0; i < mailOrder.getMailOrdDocuments().length(); i++) {
				if (!mailOrder.getMailOrdDocuments().substring(i, i + 1).equals(",")) {
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
					for (Documents document : listDocuments) {
						if (document.getId() == Integer.parseInt(docNumber)) {
							documentList.add(document);
						}
					}
					docNumber = "";
				}
			}
		}
		return documentList;
	}

	public void setStatusChangeButtonFunctionality(String buttonText, String sqlQuery, int mailOrdStatus, DBMailOrderEditingController dbMailOrderEditingController, Stage stage, MailOrder mailOrder) {
		dbMailOrderEditingController.statusChangeButton.setText(buttonText);
		if (mailOrder.getMailOrdCreateDate() != null) {
			dbMailOrderEditingController.dataVizovaKurieraDatePicker.setVisible(false);
			dbMailOrderEditingController.dataVizovaKurieraDateLabel.setVisible(false);
			dbMailOrderEditingController.sentDateDatePicker.setVisible(false);
			dbMailOrderEditingController.mailOrderSentDateLabel.setVisible(false);
			dbMailOrderEditingController.receivedDateDatePicker.setVisible(false);
			dbMailOrderEditingController.receivedDateDateLabel.setVisible(false);
		}
		if (mailOrder.getMailOrdVizovKurieraDate() != null) {
			dbMailOrderEditingController.dataVizovaKurieraDatePicker.setValue(mailOrder.getMailOrdVizovKurieraDate().toLocalDateTime().toLocalDate());
			dbMailOrderEditingController.sentDateDatePicker.setVisible(false);
			dbMailOrderEditingController.mailOrderSentDateLabel.setVisible(false);
			dbMailOrderEditingController.dataVizovaKurieraDatePicker.setVisible(true);
			dbMailOrderEditingController.dataVizovaKurieraDateLabel.setVisible(true);
		}
		if (mailOrder.getMailOrdSentDate() != null) {
			dbMailOrderEditingController.sentDateDatePicker.setValue(mailOrder.getMailOrdSentDate().toLocalDateTime().toLocalDate());
			dbMailOrderEditingController.docChooseButton.setVisible(false);
			dbMailOrderEditingController.docDeleteButton.setVisible(false);
			dbMailOrderEditingController.sentDateDatePicker.setVisible(true);
			dbMailOrderEditingController.mailOrderSentDateLabel.setVisible(true);
			dbMailOrderEditingController.toSendDateDatePicker.setVisible(false);
			dbMailOrderEditingController.toSendDateLabel.setVisible(false);
		}
		if (mailOrder.getMailOrdReceivedDate() != null) {
			dbMailOrderEditingController.receivedDateDatePicker.setValue(mailOrder.getMailOrdReceivedDate().toLocalDateTime().toLocalDate());
			dbMailOrderEditingController.receivedDateDatePicker.setVisible(true);
			dbMailOrderEditingController.receivedDateDateLabel.setVisible(true);
			dbMailOrderEditingController.toSendDateDatePicker.setVisible(false);
			dbMailOrderEditingController.toSendDateLabel.setVisible(false);
		}
		if (!buttonText.equals("")) {
			dbMailOrderEditingController.statusChangeButton.setOnMouseClicked(new EventHandler<Event>() {

				@Override
				public void handle(Event event) {
					Session session = sessionExtracting();
					session.beginTransaction();
					SQLQuery query = session.createSQLQuery(sqlQuery);
					query.setParameter(0, LocalDateTime.now());
					query.setParameter(1, mailOrdStatus);
					query.setParameter(2, mailOrder.getId());
					query.executeUpdate();
					session.getTransaction().commit();
					session.close();
					initialize();
					DBMailOrderTable.refresh();
					stage.hide();

				}
			});
		} else {
			dbMailOrderEditingController.statusChangeButton.setVisible(false);
		}
	}
}
