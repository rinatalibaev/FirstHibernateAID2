package controllers;

import java.io.IOException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowController extends Application {

	public void stageBuilder(String fxmlResource, int width, int height, String title, int minWidth, int minHeight, int x, int y) {
		try {
			Stage stage = new Stage();
			Parent panel;
			panel = FXMLLoader.load(getClass().getResource(fxmlResource));
			Scene scene = new Scene(panel, width, height);
			stage.setTitle(title);
			stage.setMinWidth(minWidth);
			stage.setMinHeight(minHeight);
			stage.setScene(scene);
			stage.setX(x);
			stage.setY(y);
			stage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public Session sessionExtracting() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		return session;
	}

	public static Session sessionExtractingforDeleting() {
		SessionFactory sessionFactory = HibernateUtil.getSessionFactory();
		Session session = sessionFactory.openSession();
		return session;
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
	}

	public void close(ActionEvent event) {
		((Node) event.getSource()).getScene().getWindow().hide();
	}

}
