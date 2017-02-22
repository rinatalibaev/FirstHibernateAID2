package controllers;

import java.io.IOException;

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
