package uiSam;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;

public class ConnectDialog extends GridPane {
	@FXML private TextField hostField, portField;
	@FXML private Button hostButton, cancelButton, okButton;
	
	@FXML
	public void connectButtonClick() {
		System.out.println("Connect");
	}
	
	@FXML
	public void cancelButtonClick() {
		System.out.println("Cancel");
		Stage s = (Stage) cancelButton.getScene().getWindow();
		s.close();
	}
	
	@FXML
	public void hostButtonClick() {
		System.out.println("Host");
	}
	
}
