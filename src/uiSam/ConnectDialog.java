package uiSam;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.*;
import javafx.stage.Stage;

public class ConnectDialog extends GridPane {
	@FXML private TextField hostField, portField;
	@FXML private Button hostButton, cancelButton, okButton;
	
	@FXML
	public void connectButtonClick() {
		
		String host = hostField.getText();
		int port = Integer.parseInt(portField.getText());
		
		System.out.printf("Connecting to: %s:%d\n", host, port);
		
		
		Parent root;
		try {
			FXMLLoader loader = new FXMLLoader(getClass().getResource("MainGameBoard.fxml"));
			root = (Parent) loader.load();
			Stage stage = new Stage();
			stage.setTitle("My New Stage Title");
			stage.setScene(new Scene(root));
			
			Stage s = (Stage) okButton.getScene().getWindow();
			s.close();
			
//			BoardGameWindow boardGame = loader.getController();
//			boardGame.connect(host, port);
			
			stage.show();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	@FXML
	public void cancelButtonClick() {
		System.out.println("Cancel");
		Stage s = (Stage) cancelButton.getScene().getWindow();
		s.close();
	}
	
	@FXML
	public void hostButtonClick() {
		
	}
	
}
