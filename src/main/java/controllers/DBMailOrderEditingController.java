package controllers;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;

import javax.persistence.Entity;
import javax.persistence.Table;

import org.hibernate.SQLQuery;
import org.hibernate.Session;

import documentManipulating.DocManipulating;
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
import javafx.stage.Stage;
import models.Documents;
import models.Employee;
import models.MailOrder;

@Entity
@Table
public class DBMailOrderEditingController extends DatabaseEditingWindowController {

	DBMailOrdersController parentController = null;

	MailOrder mailOrder = null;
	ObservableList<Documents> documentList = null;

	@FXML
	ComboBox<String> senderComboBox;
	@FXML
	ComboBox<String> receiverComboBox;
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
	Button statusChangeButton;
	@FXML
	Button docChooseButton;
	@FXML
	Label mailOrderInsertDateLabel;
	@FXML
	Label mailOrderSentDateLabel;

	public Label getToSendDateLabel() {
		return toSendDateLabel;
	}

	public void setToSendDateLabel(Label toSendDateLabel) {
		this.toSendDateLabel = toSendDateLabel;
	}

	ObservableList<Documents> selectedDocuments = FXCollections.observableArrayList();

	@FXML
	DatePicker dataVizovaKurieraDatePicker;

	@FXML
	DatePicker receivedDateDatePicker;

	@FXML
	Label toSendDateLabel;

	@FXML
	Label dataVizovaKurieraDateLabel;

	@FXML
	Label receivedDateDateLabel;

	@FXML
	public void initialize() {
		id.setCellValueFactory(new PropertyValueFactory<Documents, String>("id"));
		docType.setCellValueFactory(new PropertyValueFactory<Documents, String>("docTypeName"));
		docName.setCellValueFactory(new PropertyValueFactory<Documents, String>("docName"));
		docStatus.setCellValueFactory(new PropertyValueFactory<Documents, String>("docStatusName"));
		docDate.setCellValueFactory(new PropertyValueFactory<Documents, String>("docDate"));
		docInsertedDate.setCellValueFactory(new PropertyValueFactory<Documents, String>("docInsertedDate"));
		docInsertedEmployee.setCellValueFactory(new PropertyValueFactory<Documents, String>("docInsertedEmployeeSurname"));
		senderComboBox.setItems(getEmployeeFull());
		receiverComboBox.setItems(getEmployeeFull());
	}

	@FXML
	public void chooseDocument(ActionEvent event) {
		DBDocumentWindowController dbDocumentWindowController;
		try {
			FXMLLoader fxmlLoader = new FXMLLoader();
			fxmlLoader.setLocation((getClass().getResource("../views/DBDocumentWindow.fxml")));
			Parent panel = fxmlLoader.load();
			dbDocumentWindowController = fxmlLoader.getController();
			dbDocumentWindowController.DBDocumentTable.getSelectionModel().setSelectionMode(SelectionMode.MULTIPLE);
			dbDocumentWindowController.okButton.setOnAction(new EventHandler<ActionEvent>() {
				@Override
				public void handle(ActionEvent event) {
					System.out.println("in handle");
					selectedDocuments = DBMailOrderDocumentTable.getItems();
					if (dbDocumentWindowController.DBDocumentTable.getSelectionModel().getSelectedItems() != null) {
						for (Documents document : dbDocumentWindowController.DBDocumentTable.getSelectionModel().getSelectedItems()) {
							boolean documentPresentInTable = false;
							for (int i = 0; i < DBMailOrderDocumentTable.getItems().size(); i++) {
								if (document.getId() == DBMailOrderDocumentTable.getItems().get(i).getId()) {
									documentPresentInTable = Boolean.logicalOr(documentPresentInTable, true);
								}
							}
							if (documentPresentInTable == false) {
								selectedDocuments.add(document);
							}
						}
						for (Documents document : selectedDocuments) {
							System.out.println(document);
						}
					}
					DBMailOrderDocumentTable.setItems(selectedDocuments);
					DBMailOrderDocumentTable.refresh();
					close(event);
				}
			});
			Scene scene = new Scene(panel, 1100, 285);
			Stage stage = new Stage();
			stage.setScene(scene);
			stage.setTitle("Выбор документа для отправки");
			stage.setMinWidth(1100);
			stage.setMinHeight(285);
			stage.setX(125);
			stage.setY(200);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	@FXML
	@SuppressWarnings("deprecation")
	public void update() {
		Session session = sessionExtracting();
		session.beginTransaction();
		SQLQuery sqlQuery = session.createSQLQuery("UPDATE MailOrder SET mailOrdSenderNo = ?, mailOrdStatus = ?, mailOrdReceiverNo = ?, mailOrdCreateDate = ?, mailOrdToSendDate = ?, mailOrdSentDate = ?, mailOrdDocuments = ? WHERE id = ?");
		int senderNo = 0;
		int receiverNo = 0;
		for (int i = 0; i < getEmployeeFull().size(); i++) {
			if ((getEmployeeFull().get(i)).toString().equals(senderComboBox.getValue())) {
				senderNo = getEmployeeAll().get(i).getId();
				break;
			}
		}
		for (int i = 0; i < getEmployeeFull().size(); i++) {
			if ((getEmployeeFull().get(i)).toString().equals(receiverComboBox.getValue())) {
				receiverNo = getEmployeeAll().get(i).getId();
				break;
			}
		}

		sqlQuery.setParameter(0, senderNo);
		sqlQuery.setParameter(1, 1);
		sqlQuery.setParameter(2, receiverNo);
		sqlQuery.setParameter(3, createDateDatePicker.getValue());
		sqlQuery.setParameter(4, toSendDateDatePicker.getValue());
		sqlQuery.setParameter(5, sentDateDatePicker.getValue());
		sqlQuery.setParameter(6, doStringFromDocumentList(DBMailOrderDocumentTable.getItems()));
		sqlQuery.setParameter(7, parentController.getMailOrder().getId());
		sqlQuery.executeUpdate();
		session.getTransaction().commit();
		parentController.initialize();
		parentController.DBMailOrderTable.refresh();
		okButton.getScene().getWindow().hide();
	}

	@SuppressWarnings("deprecation")
	@Override
	public void add(Event event) {
		Session session = sessionExtracting();
		session.beginTransaction();
		SQLQuery sqlQuery = session.createSQLQuery("INSERT into MailOrder (mailOrdSenderNo, mailOrdStatus, mailOrdReceiverNo, mailOrdCreateDate, mailOrdToSendDate, mailOrdSentDate, mailOrdDocuments) VALUES (?,?,?,?,?,?,?)");
		int senderNo = 0;
		int receiverNo = 0;
		for (int i = 0; i < getEmployeeFull().size(); i++) {
			if ((getEmployeeFull().get(i)).toString().equals(senderComboBox.getValue())) {
				senderNo = getEmployeeAll().get(i).getId();
				break;
			}
		}
		for (int i = 0; i < getEmployeeFull().size(); i++) {
			if ((getEmployeeFull().get(i)).toString().equals(receiverComboBox.getValue())) {
				receiverNo = getEmployeeAll().get(i).getId();
				break;
			}
		}

		sqlQuery.setParameter(0, senderNo);
		sqlQuery.setParameter(1, 1);
		sqlQuery.setParameter(2, receiverNo);
		sqlQuery.setParameter(3, LocalDateTime.now());
		sqlQuery.setParameter(4, toSendDateDatePicker.getValue());
		sqlQuery.setParameter(5, sentDateDatePicker.getValue());
		sqlQuery.setParameter(6, doStringFromDocumentList(DBMailOrderDocumentTable.getItems()));
		sqlQuery.executeUpdate();
		session.getTransaction().commit();
		session.close();
		parentController.initialize();
		parentController.DBMailOrderTable.refresh();
		okButton.getScene().getWindow().hide();
	}

	public TableView<Documents> getDBMailOrderDocumentTable() {
		return DBMailOrderDocumentTable;
	}

	public void setDBMailOrderDocumentTable(TableView<Documents> dBMailOrderDocumentTable) {
		DBMailOrderDocumentTable = dBMailOrderDocumentTable;
	}

	@FXML
	public void callCourier(ActionEvent event) {
		String senderCity = "";
		for (Employee employee : getEmployeeAll()) {
			if (employee.toString().equals(senderComboBox.getValue())) {
				senderCity = employee.getEmpCity();
			}
		}
		Path file = Paths.get(String.format("D:\\Ринат\\РАБОТА\\! ПОЧТА\\EMS почта\\%s" + "_" + "%tF" + ".docx", senderCity, new java.util.Date()));
		Path pattern = Paths.get("C:\\Users\\alibaev\\workspace\\FirstHibernateAID2\\src\\main\\java\\documentManipulating\\Шаблон для AERP1.docx");
		Charset charset = Charset.forName(StandardCharsets.UTF_16.name());
		String readPattern = "";
		int current = 0;

		DocManipulating docManipulating = new DocManipulating();
		Employee sender = getEmployee(senderComboBox);
		Employee receiver = getEmployee(receiverComboBox);
		if (sender.getEmpHousePart() == null || sender.getEmpHousePart().equals("")) {
			sender.setEmpHousePart("");
		} else {
			sender.setEmpHousePart("/" + sender.getEmpHousePart());
		}
		if (receiver.getEmpHousePart() == null || receiver.getEmpHousePart().equals("")) {
			receiver.setEmpHousePart("");
		} else {
			receiver.setEmpHousePart("/" + receiver.getEmpHousePart());
		}
		if (sender.getEmpPhoneAdditionalDigits() == null) {
			sender.setEmpPhoneAdditionalDigits("");
		}
		if (receiver.getEmpPhoneAdditionalDigits() == null) {
			receiver.setEmpPhoneAdditionalDigits("");
		}
		if (sender.getEmpOffice() == null || sender.getEmpOffice().equals("0")) {
			sender.setEmpOffice("");
		} else {
			sender.setEmpOffice(", " + sender.getEmpOffice());
		}
		if (receiver.getEmpOffice() == null || sender.getEmpOffice().equals("0")) {
			receiver.setEmpOffice("");
		} else {
			receiver.setEmpOffice(", " + receiver.getEmpOffice());
		}

		docManipulating.fileExtract(toSendDateDatePicker.getValue(), getEmpAdrress(sender), sender.getEmpPhone() + " " + sender.getEmpPhoneAdditionalDigits(), sender.getEmpSurname() + " " + sender.getEmpFirstname() + " " + sender.getEmpFathername(),
				getEmpAdrress(receiver), receiver.getEmpPhone() + " " + receiver.getEmpPhoneAdditionalDigits(), receiver.getEmpSurname() + " " + receiver.getEmpFirstname() + " " + receiver.getEmpFathername());

		try {
			File patt = new File(pattern.toString());
			byte[] mybytearray = new byte[(int) patt.length()];
			FileInputStream fis = new FileInputStream(patt);

			BufferedInputStream bis = new BufferedInputStream(fis);
			int bytesRead = fis.read(mybytearray, 0, mybytearray.length);

			FileOutputStream fos = new FileOutputStream(file.toString());
			BufferedOutputStream bos = new BufferedOutputStream(fos);
			bos.write(mybytearray);

			bos.flush();
		} catch (Exception exc) {
			exc.printStackTrace();
		} finally {

		}

		String fileName = file.toAbsolutePath().toString();
		System.out.println(fileName);
		try {
			ProcessBuilder processBuilder = new ProcessBuilder();
			Process process = processBuilder
					.command("C:\\Program Files (x86)\\Microsoft Office\\Office15\\OUTLOOK.exe", "/a", fileName, "/m", "Петрова Екатерина Витальевна <sale-ufa@emspost.ru>&subject=[Сервионика]<Заявка на прием отправления>&body=" + "Добрый день!\n\n" +

							String.format("Прошу принять отправление в г. %s (заявка во вложении).\n\n", senderCity) + "С уважением,\n" + "Алибаев Ринат\n" + "Технический менеджер\n" + "Отдел регионального обслуживания\n" + "АО «Ай-Теко»\n"
							+ "Тел.: +7 (495) 777-10-95 доб. 6200\n" + "Факс: +7 (495) 777-10-96\n" + "Моб.: (987) 583-84-01\n" + "www.i-teco.ru\n" + "e-mail: alibaev@servionica.ru\n\n"
							+ "Данное сообщение (включая любые вложения) может содержать конфиденциальную информацию\n" + "и быть предназначенным исключительно для личности или организации, которой оно адресовано. \n"
							+ "Если Вы не являетесь надлежащим адресатом, то настоящим Вы уведомлены, что любое раскрытие, " + "копирование, распространение или использование содержания этого сообщения строго запрещено.")
					.start();
			try {
				process.waitFor();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Employee getEmployee(ComboBox<String> comboBox) {
		Employee employee = null;
		for (int i = 0; i < getEmployeeFull().size(); i++) {
			if ((getEmployeeFull().get(i)).toString().equals(comboBox.getValue())) {
				employee = getEmployeeAll().get(i);
				break;
			}
		}
		return employee;
	}

	public String getEmpAdrress(Employee employee) {
		String empAddress = null;
		empAddress = employee.getEmpRegion() + ", " + employee.getEmpCity() + ", " + employee.getEmpStreet() + ", " + employee.getEmpHouse() + employee.getEmpHousePart() + employee.getEmpOffice();
		return empAddress;

	}

	public void deleteDocFromMailOrder() {
		int selectedDocumentId = DBMailOrderDocumentTable.getSelectionModel().getSelectedItem().getId();
		System.out.println("in deleteDocFromMailOrder()");
		documentList = DBMailOrderDocumentTable.getItems();

		for (int i = 0; i < documentList.size(); i++) {
			System.out.println("i: ");
			if (documentList.get(i).getId() == selectedDocumentId) {

				System.out.println("Removed: index=" + i + "id=" + documentList.get(i).getId());
				documentList.remove(i);
			} else {
				System.out.println("Not removed: index=" + i + "id=" + documentList.get(i).getId());
			}
		}
		initialize();
		DBMailOrderDocumentTable.setItems(documentList);
		DBMailOrderDocumentTable.refresh();
	}

	public String doStringFromDocumentList(ObservableList<Documents> documentList) {
		String documents = "";
		for (Documents document : documentList) {
			if (documents.equals("")) {
				documents = String.valueOf((document.getId()));
			} else {
				documents = documents + "," + document.getId();
			}
		}
		return documents;
	}
}
