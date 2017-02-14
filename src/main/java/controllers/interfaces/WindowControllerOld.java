package controllers.interfaces;

import java.io.IOException;

import javafx.event.ActionEvent;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import models.Employee;

public interface WindowControllerOld {

	public void add(ActionEvent actionevent);

	public void edit();

	public void delete();

	public default void stageBuilder(String fxmlResource, int width, int height, String title, int minWidth, int minHeight, int x, int y) {
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public default void stageBuilder(int width, int height, String title, int minWidth, int minHeight, int x, int y, Employee employee, Stage stage, Parent fxmlEdit) {
		// Stage stage = new Stage();
		// panel = FXMLLoader.load(getClass().getResource(fxmlResource));
		Scene scene = new Scene(fxmlEdit, width, height);
		stage.setTitle(title);
		stage.setMinWidth(minWidth);
		stage.setMinHeight(minHeight);
		stage.setScene(scene);
		stage.setX(x);
		stage.setY(y);
		stage.show();

	}

}
