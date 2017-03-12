package controllers;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;

public class InfoWindowController {

	@FXML
	private ResourceBundle resources;

	@FXML
	private URL location;

	@FXML
	private TextArea infoText;

	@FXML
	private Button okButton;

	@FXML
	void initialize() {
		assert infoText != null : "fx:id=\"infoText\" was not injected: check your FXML file 'Untitled'.";
		assert okButton != null : "fx:id=\"okButton\" was not injected: check your FXML file 'Untitled'.";
	}

	public TextArea getInfoText() {
		return infoText;
	}

	public void setInfoText(TextArea infoText) {
		this.infoText = infoText;
	}

	public Button getOkButton() {
		return okButton;
	}

	public void setOkButton(Button okButton) {
		this.okButton = okButton;
	}
}