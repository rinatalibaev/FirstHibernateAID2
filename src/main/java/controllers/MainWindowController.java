package controllers;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import javafx.stage.Window;

public class MainWindowController extends WindowController {

	@FXML
	public void initialize() {
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		stageBuilder("../views/MainWindow.fxml", 1300, 640, "АС административного сопровождения - ТЭЦ-3 - Гатин Ильдар Рашитович", 1000, 640, 20, 20);
	}

	public void openDBEmployees(MouseEvent mouseEvent) {
		DBEmployeeWindowController dbEmployeeWindowController = new DBEmployeeWindowController();
		Window parentWindow = ((Node) mouseEvent.getSource()).getScene().getWindow();
		try {
			dbEmployeeWindowController.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void openDBDocuments(MouseEvent event) {
		DBDocumentWindowController dbDocumentWindowController = new DBDocumentWindowController();
		Window parentWindow = ((Node) event.getSource()).getScene().getWindow();
		try {
			dbDocumentWindowController.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@FXML
	public void openDBMailOrders(MouseEvent event) {
		DBMailOrdersController dbMailOrdersController = new DBMailOrdersController();
		try {
			dbMailOrdersController.start(new Stage());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
